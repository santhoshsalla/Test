Test Case ID: TC_PAY_01
Test Priority: High
Module Name: Payment Portal - Card Payments
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify successful card payment (Visa)
Description/Summary: Ensure that a registered customer can pay an invoice using a valid Visa card and the payment is processed successfully.
Pre-condition: User must be logged in and have at least one outstanding invoice. Test card credentials available from gateway sandbox.
Dependencies: Payment gateway sandbox (Stripe/PayPal), Test user with outstanding invoice
Test Steps:
1. Login to payment portal
2. Navigate to Outstanding Invoices
3. Select an invoice and click "Pay Now"
4. Select "Pay by Card"
5. Enter valid Visa card number, expiry, CVV, and cardholder name
6. Confirm payment
Test Data:
- Card Number: 4242 4242 4242 4242 (sandbox)
- Expiry: 12/2030
- CVV: 123
Expected Results: Payment should be processed successfully; user sees success message, transaction reference, invoice status updated to Paid, and receipt email sent.
Post-condition: Invoice marked as Paid; transaction recorded in payment history
Actual Result: Not Executed
Status: Not Executed
Notes: Use gateway sandbox and verify email logs.

---

Test Case ID: TC_PAY_02
Test Priority: High
Module Name: Payment Portal - Card Payments
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify card validation for expired card
Description/Summary: Ensure that payment fails with an expired card and appropriate error message is shown.
Pre-condition: User logged in with outstanding invoice
Dependencies: Payment gateway sandbox
Test Steps:
1. Login and navigate to Outstanding Invoices
2. Select invoice and click "Pay Now"
3. Choose "Pay by Card"
4. Enter expired card details
5. Attempt to confirm payment
Test Data:
- Card Number: 4000 0000 0000 0069 (expired test card)
- Expiry: 01/2019
- CVV: 123
Expected Results: Payment rejected with clear error message indicating expired card; no charge occurs; invoice remains unpaid
Post-condition: No payment recorded
Actual Result: Not Executed
Status: Not Executed
Notes: Confirm gateway returns expected error code/message.

---

Test Case ID: TC_PAY_03
Test Priority: High
Module Name: Payment Portal - Card Payments
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify CVV is required and not logged
Description/Summary: Ensure CVV field is required for card payment and that CVV is not stored or logged by the portal.
Pre-condition: User logged in with outstanding invoice; logging reviewed
Dependencies: Application logging access, gateway sandbox
Test Steps:
1. Login and navigate to Outstanding Invoices
2. Select invoice and click "Pay Now"
3. Choose "Pay by Card"
4. Leave CVV blank and attempt to pay
5. Inspect logs for any CVV data
Test Data:
- Card Number: 4242 4242 4242 4242
- Expiry: 12/2030
- CVV: (blank)
Expected Results: UI validation prevents submission with message "CVV required"; no CVV present in application logs or DB
Post-condition: No payment recorded
Actual Result: Not Executed
Status: Not Executed
Notes: Coordinate with devs to check logs and DB for sensitive data.

---

Test Case ID: TC_PAY_04
Test Priority: Medium
Module Name: Payment Portal - Card Payments
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify support for Mastercard
Description/Summary: Ensure Mastercard payments are accepted and processed similarly to Visa.
Pre-condition: User logged in with outstanding invoice
Dependencies: Gateway sandbox supporting Mastercard
Test Steps:
1. Login and navigate to Outstanding Invoices
2. Select invoice and click "Pay Now"
3. Choose "Pay by Card"
4. Enter valid Mastercard test card details
5. Confirm payment
Test Data:
- Card Number: 5555 5555 5555 4444 (sandbox)
- Expiry: 12/2030
- CVV: 123
Expected Results: Payment processed successfully; invoice updated to Paid
Post-condition: Transaction recorded
Actual Result: Not Executed
Status: Not Executed
Notes: Ensure card brand detection on UI.

---

Test Case ID: TC_PAY_05
Test Priority: High
Module Name: Payment Portal - Card Payments
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify 3D Secure flow (if applicable)
Description/Summary: Ensure 3D Secure authentication challenge is handled and result flows back to portal correctly.
Pre-condition: Gateway and test cards support 3DS in sandbox
Dependencies: Gateway sandbox with 3DS test cases
Test Steps:
1. Login and navigate to Outstanding Invoices
2. Select invoice and click "Pay Now"
3. Choose "Pay by Card"
4. Enter card details that trigger 3DS
5. Complete authentication in the 3DS challenge
Test Data:
- Card Number: 4000 0025 0000 3155 (3DS test card)
- Expiry: 12/2030
- CVV: 123
Expected Results: 3DS challenge displayed; after successful auth, payment completes and invoice updated
Post-condition: Transaction recorded with 3DS flag
Actual Result: Not Executed
Status: Not Executed
Notes: May open a separate pop-up or iframe; validate redirect handling.

---

Test Case ID: TC_PAY_06
Test Priority: Medium
Module Name: Payment Portal - Card Payments
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify partial payment handling
Description/Summary: Ensure user can make a partial payment and invoice balance updates accordingly.
Pre-condition: Invoice allows partial payments (business rule)
Dependencies: Backend support for partial payments
Test Steps:
1. Login and navigate to Outstanding Invoices
2. Select invoice and click "Pay Now"
3. Enter partial amount and choose "Pay by Card"
4. Enter valid card details and confirm
Test Data:
- Partial Amount: 50.00
- Card Number: 4242 4242 4242 4242
Expected Results: Partial payment accepted; invoice balance reduces by amount; transaction recorded as partial
Post-condition: Invoice remains with updated balance
Actual Result: Not Executed
Status: Not Executed
Notes: Verify statements and receipts reflect partial payment.

---

Test Case ID: TC_PAY_07
Test Priority: High
Module Name: Payment Portal - Card Payments
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify error handling for gateway timeout
Description/Summary: Ensure clear error message and retry option shown when the payment gateway times out.
Pre-condition: Simulate gateway timeout in sandbox
Dependencies: Gateway simulation of timeouts
Test Steps:
1. Login and navigate to Outstanding Invoices
2. Select invoice and click "Pay Now"
3. Choose "Pay by Card" and enter valid details
4. Simulate gateway timeout and attempt payment
Test Data:
- Card Number: 4242 4242 4242 4242
Expected Results: User sees clear timeout error message with retry option; no duplicate charge
Post-condition: No payment recorded unless retried successfully
Actual Result: Not Executed
Status: Not Executed
Notes: Verify logs for retry prevention (idempotency).

---

Test Case ID: TC_PAY_08
Test Priority: High
Module Name: Payment Portal - Card Payments
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify transaction record and email receipt
Description/Summary: Ensure transaction details are stored and a receipt email is sent after successful payment.
Pre-condition: User logged in; email service accessible
Dependencies: Email service, DB access
Test Steps:
1. Complete a successful card payment
2. Check payment history for transaction details
3. Check user's email for receipt
Test Data:
- Card Number: 4242 4242 4242 4242
Expected Results: Transaction appears in payment history with reference ID; receipt email delivered with correct details
Post-condition: Transaction persisted and email sent
Actual Result: Not Executed
Status: Not Executed
Notes: Verify fields: amount, reference ID, masked card digits.

---

End of Test Cases for "Enable Secure Card Payments in Payment Portal" user story (SCRUM-1). Please run these in test environment using gateway sandbox credentials.