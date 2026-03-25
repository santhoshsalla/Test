Feature: UPI Instant Payment - PIN Authentication Success Path
  As a logged in user with payment PIN enabled
  I want to send money using a payee UPI ID and authenticate with PIN
  So that the payment completes successfully and receipt & notifications are available

  Background:
    Given the user is logged in and has eligible funding account and payment PIN enabled

  Scenario: Verify instant payment via UPI ID with PIN authentication (success path)
    Given user navigates to "Payments" -> "Send Money" -> "UPI ID"
    When user enters UPI ID "merchant1@upi" and validates the payee
    And user enters amount "100.00" and continues
    Then the confirmation screen shows payee "merchant1@upi" amount "100.00" and fees "0.00"
    When user authenticates with PIN "1234"
    Then payment is successful within 5 seconds
    And user can open and download the receipt
    And push/SMS/email notifications are triggered as per preferences
