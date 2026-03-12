Feature: Secure Card Payments
  As a registered customer
  I want to pay outstanding invoices using my debit/credit card
  So that I can complete payments instantly and securely

  Background:
    Given the user is logged into the payment portal

  @smoke
  Scenario: Successful card payment
    When the user selects an unpaid invoice and chooses Pay by Card
    And enters valid card details
    Then the payment should be processed successfully
    And the invoice status should be updated to Paid

  Scenario: Payment with expired card
    When the user chooses Pay by Card and enters an expired card
    Then a clear error message about expired card should be displayed

  Scenario: CVV incorrect
    When the user chooses Pay by Card and enters incorrect CVV
    Then a clear error message about incorrect CVV should be displayed
