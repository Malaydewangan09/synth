package com.paydrop;

import com.paydrop.entity.PaymentRequest;
import com.paydrop.entity.PaymentVerificationRequest;
import com.paydrop.service.PaymentService;
import com.razorpay.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody PaymentRequest request) {
        try {
            String orderId = paymentService.createOrder(request.getTestamount(), "INR");
            return ResponseEntity.ok(Map.of(
                    "orderId", orderId,
                    "amount", request.getTestamount(),
                    "currency", "INR"
            ));
        } catch (RazorpayException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getPayments() throws RazorpayException {
            return ResponseEntity.ok(paymentService.getOrder());

    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        boolean isValid = paymentService.verifyPaymentSignature(
                request.getOrderId(),
                request.getPaymentId(),
                request.getSignature()
        );

        if (isValid) {
            return ResponseEntity.ok(Map.of("status", "success"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("status", "failed"));
        }
    }
}

