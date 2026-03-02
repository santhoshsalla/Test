Test Case ID: TC_CARD_01
Test Priority: High
Module Name: Payments - Card Processing
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify successful card payment flow
Description/Summary: Ensure users can complete payment using valid card details and invoice status updates to Paid
Pre-condition: User logged into payment portal and has an unpaid invoice
Dependencies: Payment gateway sandbox account, HTTPS enabled
Test Steps:
1. Navigate to Outstanding Invoices
2. Select an unpaid invoice
3. Click Pay Now
4. Choose Pay by Card
5. Enter valid card number, expiry, CVV and cardholder name
6. Confirm payment
Test Data:
- Card Number: 4111111111111111
- Expiry: 12/30
- CVV: 123
- Cardholder Name: Test User
Expected Results: Payment processed successfully; invoice status updated to Paid; receipt email sent
Post-condition: Invoice marked Paid and transaction recorded in payment history
Actual Result: 
Status: 
Notes: Verify webhook processing and reconciliation

---

Test Case ID: TC_CARD_02
Test Priority: High
Module Name: Payments - Card Processing
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify expired card is rejected with clear error
Description/Summary: Ensure system shows a clear error message when expired card is used
Pre-condition: User logged in and on payment screen
Dependencies: Payment gateway sandbox
Test Steps:
1. Click Pay Now on unpaid invoice
2. Choose Pay by Card
3. Enter expired card details
4. Confirm payment
Test Data:
- Card Number: 4111111111111111
- Expiry: 01/20
- CVV: 123
- Cardholder Name: Test User
Expected Results: Payment blocked; user sees error message indicating card expired; no invoice update
Post-condition: Invoice remains unpaid
Actual Result: 
Status: 
Notes: Check gateway response and UI error mapping

---

Test Case ID: TC_CARD_03
Test Priority: Medium
Module Name: Payments - Card Processing
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify incorrect CVV handling
Description/Summary: Ensure incorrect CVV returns an appropriate error and prevents charging
Pre-condition: User on payment screen
Dependencies: Payment gateway sandbox
Test Steps:
1. Click Pay Now
2. Choose Pay by Card
3. Enter valid card number and expiry but incorrect CVV
4. Confirm payment
Test Data:
- Card Number: 4111111111111111
- Expiry: 12/30
- CVV: 000
- Cardholder Name: Test User
Expected Results: Gateway returns CVV error; UI displays explanatory message; no invoice update
Post-condition: Invoice remains unpaid
Actual Result: 
Status: 
Notes: Ensure CVV is not logged anywhere

---

Test Case ID: TC_CARD_04
Test Priority: High
Module Name: Payments - Security
Test Designed By: Santhosh Thangavelu
Date of Test Designed: 2026-02-26
Test Executed By: Santhosh Thangavelu
Date of Test Execution: 2026-02-26
Test Title: Verify card details are not persisted in DB
Description/Summary: Ensure card number, CVV are not stored in portal DB; tokenization used
Pre-condition: Payment conducted via gateway
Dependencies: Access to DB logs and staging environment
Test Steps:
1. Perform a card payment
2. Inspect application database and logs for card PAN/CVV
Test Data: Use valid test card per gateway
Expected Results: Raw card data absent; token/reference stored instead; CVV not logged
Post-condition: Token present in payment record
Actual Result: 
Status: 
Notes: Coordinate with backend team for DB access

---

(Test cases continue for edge cases, 3D Secure redirection, network timeout, webhook failure handling, duplicate attempt idempotency, receipt email, partial payments)
