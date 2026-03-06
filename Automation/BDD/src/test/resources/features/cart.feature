Feature: Cart management
  Validates add-to-cart, mini-cart and full cart behaviors including totals, quantity rules and resilience.

  Background:
    Given I open the application

  @TC-CART-001
  Scenario: Add to cart from product listing (in-stock)
    Given I open the PLP
    And I note the current cart badge count
    When I add SKU "SKU-PLP-1" to cart from PLP
    Then I should see add-to-cart success message
    And the cart badge count should increase by 1
    When I open the mini-cart
    Then mini-cart should show SKU "SKU-PLP-1" with quantity 1 and required line fields

  @TC-CART-002
  Scenario: Add to cart from product detail page (PDP)
    Given I open the PDP for SKU "SKU-PDP-1"
    And I select required variant options if present
    And I note the current cart badge count
    When I add product to cart from PDP
    Then I should see add-to-cart success message
    And the cart badge count should increase by 1
    When I navigate to full cart page
    Then cart page should show SKU "SKU-PDP-1" with quantity 1 and totals are consistent

  @TC-CART-003
  Scenario: Add multiple SKUs and verify badge and totals update in real time
    Given I open the PLP
    When I add SKU "SKU-A" to cart from PLP
    And I add SKU "SKU-B" to cart from PLP
    And I add SKU "SKU-B" to cart from PLP
    And I open the mini-cart
    Then mini-cart should show SKU "SKU-A" with quantity 1 and required line fields
    And mini-cart should show SKU "SKU-B" with quantity 2 and required line fields
    And mini-cart totals should be calculated correctly
    And the cart badge count should equal the sum of mini-cart quantities

  @TC-CART-004
  Scenario: Mini-cart shows current items (name, price, qty, line totals, totals)
    Given the cart has at least 1 item
    When I open the mini-cart
    Then each mini-cart line should display name, unit price, quantity, and line total
    And mini-cart totals should be calculated correctly
    When I close the mini-cart
    And I open the mini-cart
    Then mini-cart should reflect the latest accurate state

  @TC-CART-005
  Scenario: Open full cart page and return without losing browsing context
    Given I open the PLP
    And the cart has at least 1 item
    When I open the mini-cart
    And I click View cart in mini-cart
    Then full cart page should open
    And cart contents should match latest actions
    When I navigate back to previous page
    Then I should return to the previous browsing context without losing cart state

  @TC-CART-006
  Scenario: Update quantity in mini-cart updates totals immediately and persists
    Given the cart contains SKU "SKU-QTY-1" with quantity 1
    When I open the mini-cart
    And I set mini-cart quantity for SKU "SKU-QTY-1" to 2
    Then mini-cart line total for SKU "SKU-QTY-1" should update for quantity 2
    And mini-cart total and badge should reflect updated quantities
    When I navigate to another page and refresh
    And I open the mini-cart
    Then mini-cart should show SKU "SKU-QTY-1" with quantity 2 and required line fields

  @TC-CART-007
  Scenario: Update quantity on full cart page with valid and invalid boundary values
    Given the cart contains SKU "SKU-QTY-2" with quantity 1
    When I navigate to full cart page
    And I set cart page quantity for SKU "SKU-QTY-2" to "10" and apply
    Then cart page should show SKU "SKU-QTY-2" with quantity 10 and totals are consistent
    When I refresh the cart page
    Then cart page should show SKU "SKU-QTY-2" with quantity 10 and totals are consistent
    When I set cart page quantity for SKU "SKU-QTY-2" to "0" and apply
    Then a quantity validation message should be displayed and cart should remain valid
    When I set cart page quantity for SKU "SKU-QTY-2" to "-1" and apply
    Then a quantity validation message should be displayed and cart should remain valid
    When I set cart page quantity for SKU "SKU-QTY-2" to "1.5" and apply
    Then a quantity validation message should be displayed and cart should remain valid
    When I set cart page quantity for SKU "SKU-QTY-2" to "abc" and apply
    Then a quantity validation message should be displayed and cart should remain valid
    When I set cart page quantity for SKU "SKU-QTY-2" to "9999" and apply
    Then a quantity validation or stock limit message should be displayed and cart should remain valid

  @TC-CART-008
  Scenario: Remove item from mini-cart updates contents, totals, and badge
    Given the cart has at least 2 items including SKU "SKU-REM-1"
    When I open the mini-cart
    And I record the current badge count and mini-cart total
    And I remove SKU "SKU-REM-1" from mini-cart
    Then SKU "SKU-REM-1" should not be present in mini-cart
    And mini-cart total should be recalculated correctly
    And the cart badge should decrement according to removed quantity

  @TC-CART-009
  Scenario: Remove item from full cart page updates contents, totals, and badge
    Given the cart contains SKU "SKU-REM-2" with quantity 1
    When I navigate to full cart page
    And I record the current badge count and cart total
    And I remove SKU "SKU-REM-2" from cart page
    Then SKU "SKU-REM-2" should not be present on cart page
    And cart totals and badge should update correctly

  @TC-CART-010
  Scenario: Prevent quantity update above available stock (enforce max)
    Given the cart contains SKU "SKU-STOCK-1" with quantity 1
    When I navigate to full cart page
    And I set cart page quantity for SKU "SKU-STOCK-1" to "5" and apply
    Then a quantity validation or stock limit message should be displayed and cart should remain valid
    When I refresh the cart page
    Then cart page should reflect a persisted valid quantity for SKU "SKU-STOCK-1"

  @TC-CART-011
  Scenario: Handle stock decrease after item already in cart (update rejected)
    Given the cart contains SKU "SKU-STOCK-2" with quantity 2
    And inventory for SKU "SKU-STOCK-2" is reduced to 1 via test stub
    When I refresh the cart page
    And I set cart page quantity for SKU "SKU-STOCK-2" to "2" and apply
    Then a quantity validation or stock limit message should be displayed and cart should remain valid

  @TC-CART-012
  Scenario: Add-to-cart network failure shows error and retry and does not increment badge
    Given I open the PLP
    And I note the current cart badge count
    And I block the add-to-cart API
    When I add SKU "SKU-NET-ADD" to cart from PLP
    Then an add-to-cart error message with retry should be displayed
    And the cart badge count should not increase
    When I unblock the add-to-cart API
    And I retry the last add-to-cart action
    Then I should see add-to-cart success message
    And the cart badge count should increase by 1

  @TC-CART-013
  Scenario: Remove item server error keeps UI state stable and allows retry
    Given the cart contains SKU "SKU-NET-REM" with quantity 1
    When I navigate to full cart page
    And I force the remove API to fail
    And I remove SKU "SKU-NET-REM" from cart page
    Then a remove error message with retry should be displayed
    And SKU "SKU-NET-REM" should still be present on cart page
    When I restore the remove API
    And I retry the last remove action
    Then SKU "SKU-NET-REM" should not be present on cart page

  @TC-CART-014
  Scenario: Fetch cart (open mini-cart) API failure shows non-blocking error state
    Given the cart has at least 1 item
    And I force the fetch-cart API to fail
    When I open the mini-cart
    Then a fetch-cart error state should be displayed
    When I restore the fetch-cart API
    And I click Retry on mini-cart error state
    Then mini-cart should reflect the latest accurate state

  @TC-CART-015
  Scenario: Idempotent add-to-cart on double click does not create duplicates
    Given I open the PLP
    When I double click Add to cart for SKU "SKU-IDEMP-1" on PLP
    And I open the mini-cart
    Then mini-cart should show SKU "SKU-IDEMP-1" with quantity 1 and required line fields

  @TC-CART-016
  Scenario: Guest cart persists after refresh and navigation
    Given I am a guest user
    When I open the PLP
    And I add SKU "SKU-GUEST-1" to cart from PLP
    And I add SKU "SKU-GUEST-2" to cart from PLP
    Then the cart badge count should be greater than 0
    When I refresh the page
    And I open the mini-cart
    Then mini-cart should show SKU "SKU-GUEST-1" with quantity 1 and required line fields
    And mini-cart should show SKU "SKU-GUEST-2" with quantity 1 and required line fields
