### SwagLabs Inventory Sorting – Comprehensive Test Plan (Manual + Automation Mapping)

#### Application Overview
The SwagLabs Inventory page (https://www.saucedemo.com/inventory.html) lists products with name, description, price, and an Add to cart button. A sort control (`[data-test="product-sort-container"]`) allows users to change the order of products using these options:
- Name (A to Z)
- Name (Z to A)
- Price (low to high)
- Price (high to low)

This plan validates functional correctness of the sorting control, its persistence during common user actions, and key UX/edge cases.

Assumptions
- Start each scenario from a fresh, logged-in state on the Inventory page unless otherwise noted.
- Test user: `standard_user` with password `secret_sauce`.
- Use public demo environment; visuals are secondary unless affecting function.
- Inventory contains at least the standard 6 products SwagLabs ships with.
- Default sort on first arrival is Name (A to Z).

Automation Context
- Existing Playwright Java artifacts:
  - Tests: `src/test/java/Playwright/project/websites/SwagLabs/tests/InventoryTests.java`
  - Page Objects: `src/test/java/Playwright/project/websites/SwagLabs/pages/LoginPage.java`, `MainPage.java`
  - Utilities: `src/test/java/Playwright/project/websites/SwagLabs/utilities/Base.java`
- Current MainPage helpers: `isOnInventoryPage()`, `productCount()`, and locator for sort control exists (`[data-test='product-sort-container']`).
- Suggested additions for automation:
  - `MainPage.selectSortOption(String visibleTextOrValue)`
  - `MainPage.getProductNames()` returns `List<String>` in current visible order
  - `MainPage.getProductPrices()` returns `List<BigDecimal>` or `List<Double>` matching order
  - Optional: `MainPage.openFirstItemAndBack()` for persistence checks

---

### Verification Heuristics and Data
- Name ascending (A→Z): lexicographic ascending order using standard string comparison.
- Name descending (Z→A): reverse lexicographic order.
- Price low→high: numeric ascending after parsing the price (strip currency symbol, parse as decimal).
- Price high→low: numeric descending.
- For stability: SwagLabs typically has unique names/prices; if duplicates exist, relative order of duplicates is not a hard requirement unless product spec states otherwise.

---

### Test Scenarios

#### 1) Inventory Page Default Sort Is Name (A→Z) [Smoke]
- Mapping: New automation `InventoryTests.defaultSortIsNameAsc` @Smoke
- Assumptions: Fresh login as `standard_user` lands on Inventory.
- Steps:
  1. Log in with valid credentials and land on Inventory.
  2. Confirm sort control is visible.
  3. Read all product names in their current order.
- Expected Results:
  - Sort control default selected option is Name (A to Z).
  - Product names are in ascending alphabetical order.
- Success: UI shows Name (A to Z), and names list equals its own sorted copy (ascending).
- Failure: Different default option or names not in ascending order.

#### 2) Sort by Name (Z→A) Orders Items Alphabetically Descending [Smoke]
- Mapping: New automation `InventoryTests.sortByNameDescending` @Smoke
- Steps:
  1. Change sort to Name (Z to A).
  2. Read displayed product names in order.
- Expected Results:
  - Selected option becomes Name (Z to A).
  - Names are in descending alphabetical order.
- Success: Names list equals its own sorted copy reversed.
- Failure: Any deviation from expected order or option not set.

#### 3) Sort by Price (low→high) Orders By Ascending Price [Smoke]
- Mapping: New automation `InventoryTests.sortByPriceLowToHigh` @Smoke
- Steps:
  1. Change sort to Price (low to high).
  2. Read displayed product prices (parse to decimal numbers).
- Expected Results:
  - Selected option becomes Price (low to high).
  - Prices are strictly non-decreasing.
- Success: Numeric list equals its sorted ascending version.
- Failure: Any price out of order or option mismatch.

#### 4) Sort by Price (high→low) Orders By Descending Price [Smoke]
- Mapping: New automation `InventoryTests.sortByPriceHighToLow` @Smoke
- Steps:
  1. Change sort to Price (high to low).
  2. Read displayed product prices.
- Expected Results:
  - Selected option becomes Price (high to low).
  - Prices are strictly non-increasing.
- Success: Numeric list equals its sorted descending version.
- Failure: Any price out of order or option mismatch.

#### 5) Sort Selection Persists After Page Refresh [Stage1]
- Mapping: New automation `InventoryTests.sortPersistsAfterRefresh` @Stage1
- Steps:
  1. Set sort to Price (high to low).
  2. Verify order is correct.
  3. Refresh the page.
  4. Re-read selected sort and product order.
- Expected Results:
  - Selected option and ordering are preserved after refresh.
- Success: Post-refresh selection and order match pre-refresh state.
- Failure: Sort reverts to default or order changes unexpectedly.

#### 6) Sort Selection Persists When Navigating Into a Product and Back [Stage1]
- Mapping: New automation `InventoryTests.sortPersistsAfterBackFromDetail` @Stage1
- Steps:
  1. Set sort to Name (Z to A) and capture first 3 product names as baseline.
  2. Click the first product to open its details page.
  3. Navigate back to Inventory.
  4. Verify selected sort option and ordering (compare baseline prefix if inventory unchanged).
- Expected Results:
  - Sort choice and order are unchanged after back navigation.
- Success: Selected option remains Z→A; top items match baseline order.
- Failure: Sort reverts or order changes.

#### 7) Sorting Does Not Affect Cart Contents or Buttons [Stage1]
- Mapping: New automation `InventoryTests.sortDoesNotAffectCartState` @Stage1
- Steps:
  1. With default A→Z, add an item (remember its name) to the cart.
  2. Change sort to Price (low to high).
  3. Verify the same item’s card shows “Remove” (or cart state indicator) and cart badge count is unchanged.
  4. Change sort to Name (Z to A) and verify again.
- Expected Results:
  - Cart contents and per-item button states remain correct across sort changes.
- Success: Cart badge and item state consistent through sorting.
- Failure: Badge resets or item state lost.

#### 8) Rapid Toggling Between Sort Options Keeps UI Stable [Regression]
- Mapping: New automation `InventoryTests.sortRapidToggleStability` @Regression
- Steps:
  1. Rapidly switch through all sort options several times (e.g., A→Z, Z→A, Low→High, High→Low, repeat 3 cycles).
  2. After final selection (e.g., High→Low), read prices/names and verify correct order.
- Expected Results:
  - No UI errors; final state matches the final selection.
- Success: Correct order with no console errors (optional capture) and no broken UI.
- Failure: Misordered list, console errors, or frozen controls.

#### 9) Keyboard Accessibility for Sort Control [Regression]
- Mapping: Optional automation `InventoryTests.sortKeyboardAccessibility` @Regression
- Steps:
  1. Focus the sort dropdown via keyboard (Tab).
  2. Open and select an option using arrow keys and Enter.
  3. Verify selected option and ordering.
- Expected Results:
  - Sort can be operated with keyboard and reflects selection correctly.
- Success: Keyboard-only interaction sets correct order.
- Failure: Control not focusable or selection doesn’t apply.

#### 10) Default Reset on Fresh Login [Regression]
- Mapping: New automation `InventoryTests.sortResetsOnFreshLogin` @Regression
- Steps:
  1. While logged in, set sort to Price (high→low).
  2. Log out (e.g., via menu) and log back in as `standard_user`.
  3. Observe initial sort on Inventory.
- Expected Results:
  - Fresh session returns to default Name (A to Z).
- Success: Default A→Z visible after new login.
- Failure: Prior selection persists across sessions unexpectedly.

#### 11) Sorting With Small and Full Product Sets [Regression]
- Mapping: New automation `InventoryTests.sortWithVaryingCounts` @Regression
- Steps:
  1. Record product count; ensure >= 6 (baseline).
  2. If possible (optional), filter-reduce list (not a built-in feature in SwagLabs; skip if unavailable).
  3. Apply each sort and verify order on whatever count is present.
- Expected Results:
  - Sorting logic holds regardless of product count.

---

### Data and Oracles
- Known Product Names (as of public demo):
  - Sauce Labs Backpack, Sauce Labs Bike Light, Sauce Labs Bolt T-Shirt, Sauce Labs Fleece Jacket, Sauce Labs Onesie, Test.allTheThings() T-Shirt (Red)
- Known Product Prices (USD):
  - $29.99, $9.99, $15.99, $49.99, $7.99, $15.99
- Notes:
  - Price duplicates exist ($15.99). Order among equal prices need not be stable unless specified, but verify no inversions across unequal prices.

---

### Negative and Edge Cases
- Network hiccup during option change: dropdown shows selected item but list fails to refresh; detect via stale order.
- Console errors when switching quickly (optional capture).
- Sorting while an item card is partially in viewport; ensure no truncated rendering (visual sanity, optional).

---

### Automation Mapping Summary
- Pages/Methods (proposed):
  - `MainPage`:
    - `selectSortOption(String option)` where option ∈ {"Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)"}
    - `getProductNames()` -> `List<String>`
    - `getProductPrices()` -> `List<BigDecimal>` or `List<Double>` after stripping `$`
    - `getSelectedSortOption()` -> `String`
    - `openProductByIndex(int i)` and `navigateBack()`
  - `LoginPage`:
    - `successfullyLogin()` (exists)
- Tests:
  - Add new tests to `InventoryTests` (or create `SortingTests` if preferred) matching mappings above and tagging with `@Smoke`, `@Stage1`, `@Regression` as indicated.

---

### Quality Gates
- Each scenario includes:
  - Clear steps and expected outcomes (functional checks)
  - Pass/fail criteria
- Minimum coverage to pass Smoke:
  - Scenarios 1–4 validate each option and default behavior
- Stage1 adds persistence guarantees (Scenarios 5–7)
- Regression covers robustness/usability (Scenarios 8–11)

---

### References
- Existing code: `InventoryTests.inventoryPageLoadsCorrectly()` asserts landing and product count.
- Locators used:
  - Sort control: `[data-test='product-sort-container']`
  - Inventory items: `[data-test='inventory-item']`
