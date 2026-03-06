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


  @TC-20006 @ui
  Scenario: Cart badge updates without page reload after add
    When I navigate to the PLP that lists product "A"
    And I add product "A" to the cart from the PLP
    Then I should see an add-to-cart confirmation message without a full page reload
    And the header cart badge count should be updated immediately after confirmation
    When I navigate to the home page via site navigation
    Then the header cart badge count should remain correct within the session

  @TC-20007 @ui
  Scenario: Mini-cart opens from header on any page and shows current items
    Given I have at least 1 item in the cart
    When I navigate to the PLP that lists product "A"
    And I open the mini-cart from the header
    Then the mini-cart should show an item list with quantities and subtotal
    When I navigate to the PDP for product "B"
    And I open the mini-cart from the header
    Then the mini-cart should show the latest cart state
    When I navigate to the home page
    And I open the mini-cart from the header
    Then the mini-cart should show the latest cart state

  @TC-20008 @ui
  Scenario: Mini-cart shows backend-authoritative subtotal/taxes/discounts after change
    Given I have at least 1 item in the cart
    When I open the mini-cart from the header
    And I record the displayed mini-cart totals
    And I perform a cart change by adding product "A"
    Then the mini-cart totals should match the backend cart totals (including rounding)

  @TC-20009 @ui
  Scenario: Mini-cart reflects latest cart state after adding item on different page
    When I navigate to the PLP that lists product "A"
    And I add product "A" to the cart from the PLP
    Then the header cart badge count should increase by 1
    When I navigate to the PDP for product "B"
    And I add the product to the cart from the PDP
    Then the header cart badge count should increase by 1
    When I open the mini-cart from the header
    Then the mini-cart should contain product "A" with quantity 1
    And the mini-cart should contain product "B" with quantity 1
    And the mini-cart subtotal should match the backend cart subtotal

  @TC-20010 @ui
  Scenario: Increase quantity via + control updates line total and subtotal (backend)
    Given product "A" is in the cart with quantity 1
    When I open the mini-cart from the header
    Then the mini-cart should contain product "A" with quantity 1
    When I increase quantity for product "A" using the plus control
    Then the mini-cart should contain product "A" with quantity 2
    And the mini-cart totals should match the backend cart totals
    When I close and reopen the mini-cart
    Then the mini-cart should contain product "A" with quantity 2
    And the mini-cart totals should match the backend cart totals

  @TC-20011 @ui @negative
  Scenario: Decrease quantity via - control does not go below minimum valid quantity
    Given product "A" is in the cart with quantity 1
    When I open the mini-cart from the header
    And I decrease quantity for product "A" using the minus control
    Then the mini-cart should contain product "A" with quantity 1
    And the mini-cart totals should match the backend cart totals

  @TC-20014 @ui @negative
  Scenario: Prevent quantity update beyond available stock with max message
    Given product "C" is in the cart with quantity 1
    And product "C" has available stock of 3
    When I open the mini-cart from the header
    And I attempt to set quantity for product "C" to 4
    Then I should see a maximum quantity message indicating 3
    And the mini-cart should contain product "C" with the last confirmed valid quantity
    When I set quantity for product "C" to 3 using the quantity input
    Then the mini-cart should contain product "C" with quantity 3
    And the mini-cart totals should match the backend cart totals

  @TC-20015 @ui
  Scenario: Set quantity exactly to available stock maximum succeeds
    Given product "C" is in the cart with quantity 1
    And product "C" has available stock of 3
    When I open the mini-cart from the header
    And I set quantity for product "C" to 3 using the quantity input
    Then the mini-cart should contain product "C" with quantity 3
    And the mini-cart totals should match the backend cart totals
    When I close and reopen the mini-cart
    Then the mini-cart should contain product "C" with quantity 3

  @TC-20016 @ui
  Scenario: Remove item from mini-cart removes line and updates totals after backend confirm
    Given product "A" is in the cart with quantity 1
    And product "B" is in the cart with quantity 1
    When I open the mini-cart from the header
    And I note the current badge count and subtotal
    And I remove product "A" from the mini-cart
    Then the mini-cart should not contain product "A"
    And the header cart badge count should decrement accordingly
    And the mini-cart totals should match the backend cart totals
    When I close and reopen the mini-cart
    Then the mini-cart should not contain product "A"

  @TC-20017 @ui
  Scenario: Removing last item results in empty cart state and badge zero
    Given product "A" is in the cart with quantity 1
    And the cart contains exactly 1 item
    When I open the mini-cart from the header
    Then the mini-cart should show a single line item
    When I remove product "A" from the mini-cart
    Then the mini-cart should show an empty cart state
    And the header cart badge count should be 0 or hidden
    When I navigate to the cart page
    Then the cart page should show an empty cart state

  @TC-20018 @ui
  Scenario: Cart items persist across page navigation within storefront
    When I navigate to the PLP that lists product "A"
    And I add product "A" to the cart from the PLP
    Then the header cart badge count should increase by 1
    When I navigate across pages PLP to PDP to Home
    Then the cart session state should remain available
    And I open the mini-cart from the header
    And the mini-cart should show the latest cart state
    When I hard refresh the page
    Then the mini-cart should show the latest cart state

  @TC-20012 @ui
  Scenario: Update quantity via direct input (valid integer) recalculates totals
    Given product "B" is in the cart with quantity 1
    When I open the mini-cart from the header
    And I set quantity for product "B" to 3 using the quantity input
    Then the mini-cart should contain product "B" with quantity 3
    And the mini-cart totals should match the backend cart totals
    When I close and reopen the mini-cart
    Then the mini-cart should contain product "B" with quantity 3

  @TC-20013 @ui @negative
  Scenario: Quantity input rejects non-numeric/decimal values and reverts

  @TC-20019 @ui @session
  Scenario: Guest cart persists for active session in same browser
    When I navigate to the PDP for product "B"
    And I add the product to the cart from the PDP
    Then the header cart badge count should increase by 1
    When I close the current storefront tab and open a new tab to the storefront
    And I open the mini-cart from the header
    Then the mini-cart should contain product "B" with quantity 1

  @TC-20020 @ui @negative @session
  Scenario: Guest cart does not persist after session ends (new session)
    When I navigate to the PLP that lists product "A"
    And I add product "A" to the cart from the PLP
    And I open the mini-cart from the header
    Then the mini-cart should contain product "A" with quantity 1
    When I end the browser session and start a new session
    And I navigate to the home page
    And I open the mini-cart from the header
    Then the mini-cart should show an empty cart state

  @TC-20021 @ui @auth
  Scenario: Logged-in cart persists across logout/login on same device
    Given I am logged in as a valid user
    When I navigate to the PLP that lists product "A"
    And I add product "A" to the cart from the PLP
    Then the header cart badge count should increase by 1
    When I log out
    And I log back in as the same user
    And I open the mini-cart from the header
    Then the mini-cart should contain product "A" with the last confirmed quantity

  @TC-20022 @ui @auth @multiDevice
  Scenario: Logged-in cart continuity across devices/browsers
    Given I have two shopper sessions for the same logged-in user
    When in session "device1" I add product "B" to the cart
    Then in session "device2" the mini-cart should contain product "B" with quantity 1
    When in session "device2" I set quantity for product "B" to 2 using the quantity input
    Then in session "device1" the mini-cart should contain product "B" with quantity 2

  @TC-20023 @ui @auth
  Scenario: Merge guest cart into account cart on login (unique items)
    Given the account cart already contains product "A" with quantity 1
    When as a guest I add product "B" to the cart
    And I log in to the account
    And I open the mini-cart from the header
    Then the mini-cart should contain product "A" with quantity 1
    And the mini-cart should contain product "B" with quantity 1
    And the header cart badge count should reflect the merged cart quantity

  @TC-20024 @ui @auth @negative
  Scenario: Merge guest cart with account cart sums duplicates and enforces stock
    Given the account cart already contains product "C" with quantity 2
    And product "C" has available stock of 3
    When as a guest I ensure product "C" quantity is 2
    And I log in to the account
    Then I should see a maximum quantity message indicating 3
    And the mini-cart should contain product "C" with a valid backend-confirmed quantity not exceeding 3

  @TC-20025 @api
  Scenario: Add endpoint supports idempotency key to avoid duplicate adds on retry
    Given the cart is empty via the cart API
    When I call Add Item API for product "A" with idempotency key "K1"
    And I retry the same Add Item API request with idempotency key "K1"
    Then the cart state should be unchanged compared to the first response
    When I fetch the cart via the cart API
    Then the cart should contain product "A" exactly once with quantity per rules
    When I call Add Item API for product "A" with idempotency key "K2"
    Then the cart should reflect only the intended additional add behavior

  @TC-20026 @ui @negative
  Scenario: Add-to-cart failure shows error and UI reverts to last confirmed state
    Given product "A" is in the cart with quantity 1
    And I am able to simulate an add-to-cart API failure
    When I navigate to the PLP that lists product "A"
    And I attempt to add product "A" to the cart while the add API is failing
    Then I should see an add-to-cart error message
    And the header cart badge count should remain at the last confirmed value
    When I open the mini-cart from the header
    Then the mini-cart should contain product "A" with quantity 1
    Given product "A" is in the cart with quantity 1
    When I open the mini-cart from the header
    And I set quantity for product "A" to "abc" using the quantity input
    Then the mini-cart should contain product "A" with quantity 1
    And the mini-cart totals should match the backend cart totals
    When I set quantity for product "A" to "1.5" using the quantity input
    Then the mini-cart should contain product "A" with quantity 1
    And I should see a helpful quantity validation message
