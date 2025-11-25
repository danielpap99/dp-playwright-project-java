### SwagLabs Add to Cart – Comprehensive Test Plan (Manual + Automation Mapping)

#### Application Overview
The SwagLabs web app allows users to browse products on the Inventory page (https://www.saucedemo.com/inventory.html) and add them to a shopping cart via per-item buttons. Core UI elements involved in add-to-cart include:
- Inventory cards: name, description, price, and a per-item button that toggles between “Add to cart” and “Remove”.
- Product Detail page: shows the same product with an “Add to cart”/“Remove” button.
- Cart icon/link in header `[data-test='shopping-cart-link']` with a numeric badge `[data-test='shopping-cart-badge']` indicating item count.
- Cart page (https://www.saucedemo.com/cart.html) with a list of added items, quantity per line, remove buttons, and navigation controls (Continue Shopping, Checkout).

This plan validates core cart functionality end-to-end: adding/removing items from inventory and product detail pages, badge updates, persistence across navigations/refresh, and synchronization between pages.

Assumptions
- Start each scenario from a fresh, logged-in state as `standard_user` on the Inventory page, unless otherwise noted.
- Credentials: username `standard_user`, password `secret_sauce`.
- Use the public demo environment; visual checks are secondary unless they impact function.
- Baseline inventory includes at least the standard 6 products.

Automation Context
- Existing Playwright Java artifacts:
  - Tests: `src/test/java/Playwright/project/websites/SwagLabs/tests/InventoryTests.java`, `LoginTests.java`
  - Page Objects: `src/test/java/Playwright/project/websites/SwagLabs/pages/LoginPage.java`, `MainPage.java`
  - Utilities: `src/test/java/Playwright/project/websites/SwagLabs/utilities/Base.java`
- Current `MainPage` helpers: `isOnInventoryPage()`, `productCount()`, header/cart/sort locators exist.
- Suggested additions for automation:
  - `MainPage.getCartBadgeCount()` -> `int` (0 if badge not visible)
  - `MainPage.addItemByName(String productName)` -> clicks the matching card’s “Add to cart”
  - `MainPage.removeItemByName(String productName)` -> clicks the matching card’s “Remove”
  - `MainPage.itemButtonLabel(String productName)` -> returns current label: "Add to cart" or "Remove"
  - `MainPage.openProductByName(String productName)` -> navigates to detail
  - `MainPage.openCart()` -> navigate to cart page via header icon/link
  - `CartPage.getItems()` -> `List<String>` of product names in the cart
  - `CartPage.removeItemByName(String productName)`
  - `CartPage.itemQuantity(String productName)` -> `int` (SwagLabs uses quantity 1 per line)

---

### Verification Heuristics and Data
- Cart badge equals the count of distinct product lines in the cart (SwagLabs adds each product once; duplicates not allowed from UI).
- The per-item button text toggles between “Add to cart” and “Remove” reflecting item presence in cart.
- Cart page shows each added item once with quantity 1 and the correct name/price.
- State should persist across:
  - Page refreshes
  - Navigation between Inventory, Product Detail, and Cart pages
  - Sort changes on Inventory
- Logout should clear cart for new session.

---

### Test Scenarios

#### 1) Add a Single Item from Inventory Updates Badge and Button [Smoke]
- Mapping: New automation `InventoryTests.addSingleItemFromInventory` @Smoke
- Steps:
  1. Log in successfully and land on Inventory.
  2. Pick a known item (e.g., "Sauce Labs Backpack"). Ensure its button shows “Add to cart”.
  3. Click “Add to cart”.
  4. Observe cart badge and the item’s button label.
- Expected Results:
  - Badge shows `1`.
  - The item’s button label changes to “Remove”.
- Success: Badge 1 and button toggled to Remove.
- Failure: Badge not present/incorrect, or button label unchanged.

#### 2) Add Multiple Distinct Items Increments Badge Accurately [Smoke]
- Mapping: New automation `InventoryTests.addMultipleItemsFromInventory` @Smoke
- Steps:
  1. Add three different items from the Inventory page.
  2. Observe cart badge after each add.
- Expected Results:
  - Badge increments 1 → 2 → 3 accordingly.
- Success: Final badge = 3.
- Failure: Badge count incorrect or missing.

#### 3) Remove Item from Inventory Decrements Badge and Button Reverts [Smoke]
- Mapping: New automation `InventoryTests.removeItemFromInventory` @Smoke
- Precondition: Two items already in cart (from prior steps or prepare within test).
- Steps:
  1. Click “Remove” on one of the added items.
  2. Observe badge and the item’s button label.
- Expected Results:
  - Badge decrements by 1.
  - Button label reverts to “Add to cart”.

#### 4) Add from Product Detail Page Reflects on Inventory and Badge [Smoke]
- Mapping: New automation `InventoryTests.addFromProductDetail` @Smoke
- Steps:
  1. From Inventory, open a product (e.g., "Sauce Labs Bike Light").
  2. Click “Add to cart” on the detail page.
  3. Confirm badge updates to include this item.
  4. Navigate back to Inventory and locate the same item card.
- Expected Results:
  - Badge increased by 1 for the new item.
  - The item’s inventory button now shows “Remove”.

#### 5) Cart Badge and Contents Persist After Page Refresh [Stage1]
- Mapping: New automation `InventoryTests.cartPersistsAfterRefresh` @Stage1
- Steps:
  1. Add two known items.
  2. Capture badge count and optionally open cart to capture item names.
  3. Refresh the page (if on Inventory) or reload Cart page.
  4. Re-check badge and (if applicable) cart contents.
- Expected Results:
  - Badge and cart contents unchanged after refresh.

#### 6) Cart State Synchronizes Across Inventory and Cart Pages [Stage1]
- Mapping: New automation `InventoryTests.cartStateSyncAcrossPages` @Stage1
- Steps:
  1. Ensure at least one item is in the cart.
  2. Open Cart; verify the item is listed.
  3. Remove the item from the Cart page.
  4. Navigate back to Inventory; locate the same item card.
- Expected Results:
  - Badge decremented/cleared accordingly.
  - The inventory item button shows “Add to cart”.

#### 7) Sort Changes Do Not Affect Cart Contents or Button States [Stage1]
- Mapping: New automation `InventoryTests.cartNotAffectedBySorting` @Stage1
- Steps:
  1. On Inventory, add one item to cart.
  2. Change sort through at least two options (e.g., Name Z→A, Price Low→High).
  3. Observe the same item’s card state and the badge.
- Expected Results:
  - Item remains in cart (button = Remove).
  - Badge count unchanged.

#### 8) Prevent Duplicate Adds of the Same Item [Stage1]
- Mapping: New automation `InventoryTests.preventDuplicateAdds` @Stage1
- Steps:
  1. Add a specific item from Inventory.
  2. Without removing it, attempt to add it again (button should be “Remove” and not add).
- Expected Results:
  - No duplicate added; badge does not increment.
  - Button remains “Remove”.

#### 9) Add on Inventory, Remove on Detail, and Vice Versa [Regression]
- Mapping: New automation `InventoryTests.crossPageAddRemove` @Regression
- Steps:
  1. Add an item from Inventory.
  2. Open the item’s detail page and click “Remove”.
  3. Return to Inventory and verify the button is back to “Add to cart”.
  4. Now add from detail first, then verify Inventory button shows “Remove”.
- Expected Results:
  - State is consistent regardless of where add/remove occurs.

#### 10) Cart Page Lists Accurate Items, Names, and Prices [Regression]
- Mapping: New automation `InventoryTests.cartPageListsAccurately` @Regression
- Steps:
  1. Add 2–3 known items with known prices.
  2. Open Cart and read displayed item names and prices.
- Expected Results:
  - Names and prices match the known inventory data.
  - Quantity per line is 1.

#### 11) Continue Shopping Preserves Cart [Regression]
- Mapping: New automation `InventoryTests.continueShoppingPreservesCart` @Regression
- Steps:
  1. With items in cart, open Cart page.
  2. Click “Continue Shopping”.
  3. Verify you’re back on Inventory and the badge still reflects the items.
- Expected Results:
  - Cart remains unchanged; badge still correct.

#### 12) Rapid Clicking of Add/Remove Does Not Corrupt State [Regression]
- Mapping: New automation `InventoryTests.rapidToggleAddRemove` @Regression
- Steps:
  1. On a single item, click Add then immediately Remove, repeat rapidly a few cycles.
  2. Stop in “Add to cart” state and verify badge.
  3. Then stop in “Remove” state and verify badge.
- Expected Results:
  - Final state and badge consistently match the last visible button state.
  - No console errors (optional capture).

#### 13) Logout Clears Cart for New Session [Regression]
- Mapping: New automation `InventoryTests.cartClearedOnLogout` @Regression
- Steps:
  1. Add items to cart; note badge.
  2. Log out (via the sidebar menu).
  3. Log back in as `standard_user`.
- Expected Results:
  - Badge is empty (no number shown) and cart is empty.

#### 14) Adding Items from Mixed Sources Results in Correct Badge [Regression]
- Mapping: New automation `InventoryTests.mixedSourceAdds` @Regression
- Steps:
  1. Add one item from Inventory, one from Product Detail, and one from Cart (if UI allows add from Cart; otherwise skip and add a third from Inventory).
  2. Verify badge equals 3 and all three items appear in Cart.
- Expected Results:
  - Badge and cart list reflect all distinct items exactly once.

---

### Data and Oracles
- Known Product Names (typical demo set):
  - Sauce Labs Backpack, Sauce Labs Bike Light, Sauce Labs Bolt T-Shirt, Sauce Labs Fleece Jacket, Sauce Labs Onesie, Test.allTheThings() T-Shirt (Red)
- Known Product Prices (USD):
  - $29.99, $9.99, $15.99, $49.99, $7.99, $15.99
- Notes:
  - Prices are displayed with `$` and two decimals; parse numbers by stripping `$`.
  - Duplicate price values exist; cart line items should still reflect correct names/prices.

---

### Negative and Edge Cases
- Network hiccup: button shows “Remove” but badge fails to update (detect mismatch and retry). Optional console error capture.
- Badge visibility: when 0 items, the badge element may be absent; ensure helpers treat absent badge as 0.
- Page layout/scroll: adding items that are outside viewport; ensure robust locating by product name.
- Accessibility: keyboard-only navigation to item buttons toggles state correctly (Tab + Space/Enter).
- Direct navigation to `cart.html` without items: page loads, empty state (no items) and no badge.

---

### Automation Mapping Summary
- `MainPage` (proposed additions):
  - Locators:
    - Inventory items: `[data-test='inventory-item']`
    - Item name within card: `.inventory_item_name`
    - Item price within card: `.inventory_item_price`
    - Per-item add/remove: `[data-test^='add-to-cart']` / `[data-test^='remove']` or by button text
    - Cart link: `[data-test='shopping-cart-link']`
    - Cart badge: `[data-test='shopping-cart-badge']`
  - Methods:
    - `getCartBadgeCount()` -> reads badge text; returns 0 if not visible
    - `addItemByName(String name)` / `removeItemByName(String name)`
    - `itemButtonLabel(String name)` -> String
    - `openProductByName(String name)` -> click card/title
    - `openCart()` -> click cart link
- `CartPage` (new page object suggested):
  - Locators: cart items `[data-test='cart-item']`, item name `.inventory_item_name`, remove button `[data-test^='remove']`
  - Methods: `getItems()`, `removeItemByName(String name)`, `itemQuantity(String name)`
- `LoginPage`:
  - `successfullyLogin()` (exists)
- Tests:
  - Implement new tests in `InventoryTests` or create `CartTests` class, tagging with `@Smoke`, `@Stage1`, `@Regression` as indicated above.

---

### Quality Gates
- Each scenario includes clear steps and expected results with pass/fail criteria.
- Minimum Smoke coverage:
  - Scenarios 1–4 validate basic add/remove on Inventory and Product Detail with badge updates.
- Stage1 enhances reliability:
  - Scenarios 5–8 for persistence, synchronization, and duplicate-prevention.
- Regression focuses on robustness and UX:
  - Scenarios 9–14 for cross-page consistency, performance under rapid toggling, session boundaries, and data accuracy.

---

### References
- Existing code:
  - `InventoryTests.inventoryPageLoadsCorrectly()` asserts landing and product count.
- Useful locators seen/assumed:
  - Cart link: `[data-test='shopping-cart-link']`
  - Cart badge: `[data-test='shopping-cart-badge']`
  - Inventory items: `[data-test='inventory-item']`
  - Item title: `.inventory_item_name`
  - Item price: `.inventory_item_price`
  - Per-item buttons: `[data-test^='add-to-cart']` / `[data-test^='remove']` (or by visible text)
