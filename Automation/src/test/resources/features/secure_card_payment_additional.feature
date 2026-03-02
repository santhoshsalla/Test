Feature: Secure Card Payments - Additional Scenarios
  As a registered customer
  I want additional payment resilience and security behaviors covered
  So that edge cases like 3D Secure, network timeouts, and webhook failures are handled

  Background:
    Given the user is logged into the payment portal

  Scenario: 3D Secure (3DS) authentication flow
    When the user selects an unpaid invoice and chooses Pay by Card
    And enters valid card details that require 3DS
    Then the user should be redirected to 3DS provider and complete authentication
    And payment should be processed successfully after 3DS challenge

  Scenario: Network timeout during payment processing
    When the user selects Pay by Card and enters valid card details
    And the payment processing experiences a network timeout
    Then the UI should display a retry option and not mark invoice as Paid

  Scenario: Webhook failure leading to delayed reconciliation
    When the user completes payment successfully at the gateway
    And the payment gateway webhook delivery fails
    Then the portal should mark transaction as Pending and reconcile once webhook is retried
