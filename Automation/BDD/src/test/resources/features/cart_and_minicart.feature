Feature: Cart and mini-cart behavior
  As a shopper
  I want cart badge and mini-cart to reflect the backend cart state
  So that I can trust my cart contents and totals

  Background:
    Given I am a guest user on the storefront

  @TC-20001 @ui
  Scenario: Add item to cart from PLP updates badge and shows confirmation
    When I navigate to the PLP that lists product "A"
    And I add product "A" to the cart from the PLP
    Then I should see an add-to-cart confirmation message without a full page reload
    And the header cart badge count should increase by 1
    When I open the mini-cart from the header
    Then the mini-cart should contain product "A" with quantity 1
    And the mini-cart subtotal should match the backend cart subtotal

  @TC-20002 @ui
  Scenario: Add item to cart from PDP updates badge and mini-cart
    When I navigate to the PDP for product "B"
    And I add the product to the cart from the PDP
    Then I should see an add-to-cart confirmation message without a full page reload
    And the header cart badge count should increase by 1
    When I open the mini-cart from the header
    Then the mini-cart should contain product "B" with quantity 1
    And the mini-cart subtotal should match the backend cart subtotal

  @TC-20003 @ui
  Scenario: Add item to cart from search results page
    When I search for product "C"
    And I add product "C" to the cart from the search results
    Then I should see an add-to-cart confirmation message
    And the header cart badge count should increase by 1 without a page reload
    When I open the mini-cart from the header
    Then the mini-cart should contain product "C" with quantity 1
    And the mini-cart subtotal should match the backend cart subtotal

  @TC-20004 @ui
  Scenario: Add same product twice increments quantity and totals
    When I navigate to the PLP that lists product "A"
    And I add product "A" to the cart from the PLP
    Then the header cart badge count should increase by 1
    When I add product "A" to the cart from the PLP
    Then the header cart badge count should increase by 1
    When I open the mini-cart from the header
    Then the mini-cart should contain product "A" with quantity 2
    And the mini-cart subtotal should match the backend cart subtotal

  @TC-20005 @ui @negative
  Scenario: Add to cart blocked when product is out of stock
    When I navigate to a product listing where product "D" is visible
    And I attempt to add product "D" to the cart
    Then the add operation should be prevented due to stock constraint
    And I should see a helpful out-of-stock message
    And the header cart badge count should not increase
    When I open the mini-cart from the header
    Then the mini-cart should not contain product "D"

