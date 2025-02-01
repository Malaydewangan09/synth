package com.paydrop;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    public String createOrder(BigDecimal amount, String currency) throws RazorpayException, RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        org.json.JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount.multiply(new BigDecimal("100"))); // Convert to paise
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", "order_" + 1);

        Order order = razorpay.orders.create(orderRequest);

        HashMap<Long,List<Order>> map = new HashMap<>();
        map.put(1L,getOrder());
        System.out.println(map);
        return order.get("id");
    }

    public List<Order> getOrder() throws RazorpayException, RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        return razorpay.orders.fetchAll();
    }




    public boolean verifyPaymentSignature(String orderId, String paymentId, String signature) {
        try {
            // Create signature verification data
            String data = orderId + "|" + paymentId;

            // Get an hmac_sha256 key from the raw secret key bytes
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(razorpayKeySecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            // Calculate the signature
            byte[] hash = sha256_HMAC.doFinal(data.getBytes());
            String calculatedSignature = Hex.encodeHexString(hash);

            // Verify the signatures
            return signature.equals(calculatedSignature);
        } catch (Exception e) {
            return false;
        }
    }
}