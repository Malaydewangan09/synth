document.addEventListener('DOMContentLoaded', function () {
    const fetchPaymentsButton = document.getElementById('fetchPaymentsButton');
    const paymentsList = document.getElementById('paymentsList');
    console.log(fetchPaymentsButton);
    fetchPaymentsButton.addEventListener('click', function () {
    console.log("hiisahdiasudas");
        fetchPayments();
    });

    async function fetchPayments() {

        paymentsList.innerHTML = '<div class="spinner-border" role="status"><span class="visually-hidden">Loading...</span></div>';

        try {
            const response = await fetch('/api/payments');
            if (!response.ok) {
                throw new Error('Failed to fetch payments.');
            }

            const payments = await response.json();
            displayPayments(payments);
        } catch (error) {
            console.error('Error fetching payments:', error);
            paymentsList.innerHTML = '<div class="alert alert-danger">Error loading payments</div>';
        }
    }

    function displayPayments(payments) {
        paymentsList.innerHTML = '';

        if (payments.length === 0) {
            paymentsList.innerHTML = '<div class="alert alert-info">No payments found.</div>';
            return;
        }

        const table = document.createElement('table');
        table.className = 'table table-striped';

        const thead = document.createElement('thead');
        thead.innerHTML = `
            <tr>
                <th>ID</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Date</th>
            </tr>
        `;
        table.appendChild(thead);

        const tbody = document.createElement('tbody');
        payments.forEach(payment => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${payment.id}</td>
                <td>${payment.amount}</td>
                <td>${payment.status}</td>
                <td>${new Date(payment.date).toLocaleString()}</td>
            `;
            tbody.appendChild(row);
        });
        table.appendChild(tbody);

        paymentsList.appendChild(table);
    }
});
