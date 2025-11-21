### SwagLabs Login – Comprehensive Test Plan (Manual + Automation Mapping)

#### Application Overview
The SwagLabs login page (https://www.saucedemo.com/) provides user authentication for accessing the Inventory page. Key UI elements include username input (`#user-name`), password input (`#password`), login button (`#login-button`), and an error message area (`[data-test="error"]`) with a dismiss button (`[data-test="error-button"]`).

Assumptions
- Start each scenario from a fresh, logged-out state on the login page.
- Use known accounts from SwagLabs test data: `standard_user`, `locked_out_user`, and password `secret_sauce`.
- Network is stable and test environment is the public demo site.
- Visuals are not the primary concern unless they affect usability and functional outcomes.

Automation Context
- Existing Playwright Java tests and pages:
  - Tests: `src/test/java/Playwright/project/websites/SwagLabs/tests/LoginTests.java`
  - Page Objects: `src/test/java/Playwright/project/websites/SwagLabs/pages/LoginPage.java`, `MainPage.java`
  - Base/Setup: `src/test/java/Playwright/project/websites/SwagLabs/utilities/Base.java`
- Current tags in use: `@Smoke`, `@Stage1`

---

### Test Scenarios

#### 1) Login Page Loads Correctly [Smoke]
- Mapping: `LoginTests.loginPageLoadsCorrectly` (exists) `@Smoke`
- Assumptions: Fresh session on https://www.saucedemo.com/
- Steps:
  1. Navigate to the login page URL.
  2. Observe the username field, password field, and login button.
- Expected Results:
  - Username field is visible.
  - Password field is visible.
  - Login button is visible.
- Success: All elements visible as expected.
- Failure: Any element missing or not interactable.

#### 2) Successful Login with Valid Credentials [Smoke]
- Mapping: `LoginTests.successfulLogin` (exists) `@Smoke`
- Steps:
  1. Enter username: `standard_user`.
  2. Enter password: `secret_sauce`.
  3. Click Login.
- Expected Results:
  - Navigates to Inventory page; products are listed; URL contains `/inventory.html`.
- Success: Inventory page detected (e.g., per `MainPage.isOnInventoryPage()`).
- Failure: Remains on login; error message displayed; or unexpected navigation.

#### 3) Error: Valid Username + Invalid Password [Stage1]
- Mapping: `LoginTests.loginFailsWithValidUsernameAndInvalidPassword` (exists) `@Stage1`
- Steps:
  1. Enter username: `standard_user`.
  2. Enter an invalid password, e.g., `definitely_wrong_password`.
  3. Click Login.
- Expected Results:
  - Error visible.
  - Error text: `Epic sadface: Username and password do not match any user in this service`
  - Stay on login page.
- Success: Error matches exactly and no navigation occurs.
- Failure: Different error text or navigation to Inventory.

#### 4) Error: Locked Out User + Valid Password [Stage1]
- Mapping: `LoginTests.loginFailsForLockedOutUserWithValidPassword` (exists) `@Stage1`
- Steps:
  1. Enter username: `locked_out_user`.
  2. Enter password: `secret_sauce`.
  3. Click Login.
- Expected Results:
  - Error visible.
  - Error text: `Epic sadface: Sorry, this user has been locked out.`
  - Stay on login page.
- Success: Error matches exactly and no navigation occurs.
- Failure: Different error or unintended navigation.

#### 5) Error: Missing Password and Missing Username [Stage1]
- Mapping: `LoginTests.userNameAndPasswordRequired` (exists) `@Stage1`
- Steps A (Missing password):
  1. Enter username: `standard_user`.
  2. Leave password empty.
  3. Click Login.
- Expected A:
  - Error visible, text: `Epic sadface: Password is required`
  - Stay on login page.
- Steps B (Missing username):
  1. Dismiss previous error (click the cross button).
  2. Leave username empty.
  3. Enter password: `secret_sauce`.
  4. Click Login.
- Expected B:
  - Error visible, text: `Epic sadface: Username is required`
  - Stay on login page.
- Success: Exact error messages, correct visibility, no navigation.
- Failure: Messages differ or navigation occurs.

#### 6) Error: Invalid Username + Valid Password
- Mapping: New test suggested (not yet automated) `@Regression`
- Steps:
  1. Enter username: `not_a_user`.
  2. Enter password: `secret_sauce`.
  3. Click Login.
- Expected:
  - Error visible, text: `Epic sadface: Username and password do not match any user in this service`
  - Stay on login page.

#### 7) Error: Both Username and Password Invalid
- Mapping: New test suggested (not yet automated) `@Regression`
- Steps:
  1. Enter username: `fake_user`.
  2. Enter password: `fake_pass`.
  3. Click Login.
- Expected:
  - Same generic error as invalid credentials case.

#### 8) Whitespace Handling (Trim Input)
- Mapping: New test suggested (not yet automated) `@Regression`
- Steps:
  1. Enter username with leading/trailing spaces: `  standard_user  `.
  2. Enter password: `  secret_sauce  `.
  3. Click Login.
- Expected:
  - App either trims and logs in successfully, or rejects with credentials error consistently.
  - Define expected behavior; for SwagLabs, most clients expect trimming; verify actual.

#### 9) Case Sensitivity
- Mapping: New test suggested (not yet automated) `@Regression`
- Steps:
  1. Enter username: `STANDARD_USER` (uppercase).
  2. Enter password: `secret_sauce`.
  3. Click Login.
- Expected:
  - If usernames are case-sensitive, expect invalid credentials error; if case-insensitive, successful login. Document observed behavior and standardize expectation.

#### 10) Special Characters and Length Boundaries
- Mapping: New tests suggested (not yet automated) `@Regression`
- Steps:
  A. Username special chars: `user!@#$%^&*()` + valid password.
  B. Password special chars with `standard_user`.
  C. Very long inputs (e.g., 256+ chars) for username/password.
- Expected:
  - Inputs should be accepted by fields; authentication should respond with appropriate errors/success.
  - No client-side crashes or layout breaks.

#### 11) Security: Injection Strings Do Not Break Auth
- Mapping: New tests suggested (not yet automated) `@Security`
- Steps:
  1. Username: `' OR '1'='1`; Password: anything.
  2. Username: `<script>alert(1)</script>`; Password: anything.
  3. Username: `standard_user`; Password: `' OR '1'='1`.
- Expected:
  - No script execution, no SQL errors, and no successful login due to injection.
  - Error message remains the generic invalid credentials message.

#### 12) Error Message UX Behavior
- Mapping: Partially covered in `LoginTests.userNameAndPasswordRequired`; add more checks `@Regression`
- Steps:
  1. Trigger an error (e.g., invalid credentials).
  2. Verify error visibility and exact text.
  3. Click the error dismiss (X) button.
  4. Modify one input (e.g., type in password), observe whether the error clears.
- Expected:
  - Error visible with correct text when triggered.
  - Dismiss button hides the error.
  - Error reappears on subsequent invalid submit; optionally clears on input changes (document actual behavior and assert accordingly).

#### 13) Keyboard Interaction and Accessibility Basics
- Mapping: New tests suggested (not yet automated) `@Accessibility`
- Steps:
  1. Tab order cycles Username → Password → Login.
  2. From Password field, press Enter to submit.
  3. Verify password field masks input characters.
- Expected:
  - Logical tab order.
  - Enter on password triggers submit.
  - Password input type = password (masking enabled).

#### 14) Navigation: Direct Access and Back Button Behavior
- Mapping: New tests suggested (not yet automated) `@Regression`
- Steps A (Direct Access):
  1. From a fresh session, navigate directly to `/inventory.html`.
- Expected A:
  - Access should be blocked without authentication (redirect to login or error). Document the observed behavior and set the expected standard.
- Steps B (Back Button After Login):
  1. Log in successfully.
  2. Click browser Back.
- Expected B:
  - Session persists; either remain authenticated or be properly controlled by app. Ensure no sensitive data leak on back-forward cache in a way that bypasses auth controls.

#### 15) Refresh and Session Persistence
- Mapping: New tests suggested (not yet automated) `@Regression`
- Steps:
  1. Log in successfully, land on `/inventory.html`.
  2. Refresh the page.
- Expected:
  - Session remains valid; still on inventory; no forced logout.

#### 16) Performance Smoke
- Mapping: New test suggested (not yet automated) `@Performance`
- Steps:
  1. Measure time from initial login page navigation until all three core controls (username, password, login) are visible.
  2. Measure time from clicking Login with valid creds to inventory load.
- Expected:
  - Page interactive ≤ 3s on standard network.
  - Login to inventory ≤ 2s (tunable threshold; document observed baseline).

#### 17) Cross-Browser and Viewport Coverage
- Mapping: Execution guidance (automation matrix)
- Suggested minimum desktop coverage:
  - Chromium (Playwright default), Firefox, WebKit; viewport 1500x955 (matches `Base.java`).
- Optional mobile/viewport spot checks:
  - iPhone 12/13, Pixel 5 emulation (orientation portrait) for field visibility and keyboard submit behavior.

---

### Automation Mapping and Suggestions
- Existing Automation Coverage
  - `@Smoke`
    - `loginPageLoadsCorrectly`
    - `successfulLogin`
  - `@Stage1`
    - `loginFailsWithValidUsernameAndInvalidPassword`
    - `loginFailsForLockedOutUserWithValidPassword`
    - `userNameAndPasswordRequired` (covers both missing password and missing username)
- Proposed New Automated Tests (names are suggestions in `LoginTests.java` or a new `LoginAdvancedTests.java`)
  - `invalidUsernameValidPassword_shouldShowGenericError` `@Regression`
  - `bothUsernameAndPasswordInvalid_shouldShowGenericError` `@Regression`
  - `inputsTrimmed_allowLoginOrConsistentError` `@Regression`
  - `usernameCaseSensitivity_behaviorIsConsistent` `@Regression`
  - `specialCharactersAndLengthBoundaries_areHandled` `@Regression`
  - `security_injectionStrings_doNotBypassAuth` `@Security`
  - `errorMessage_dismissAndReappearBehavior` `@Regression`
  - `keyboard_enterSubmitsAndTabOrder_isLogical` `@Accessibility`
  - `navigation_directInventoryBlocked_withoutAuth` `@Regression`
  - `sessionPersists_afterRefresh` `@Regression`
  - `performance_loginTimings_withThresholds` `@Performance`
- Page Object Enhancement Ideas (optional):
  - Add helpers in `LoginPage`: `submitWithEnter()`, `clearUsername()`, `clearPassword()`, `setUsernameAndPassword(String u, String p)`, `isPasswordMasked()`.
  - In `MainPage`: `assertInventoryUrl()`, or use a stronger locator-based check for inventory readiness.

---

### Execution Notes
- Use `Base.java`’s existing `BrowserContext` and viewport; consider parameterizing headless/headed and `slowMo` for CI.
- Tag the new tests appropriately (`@Regression`, `@Security`, `@Accessibility`, `@Performance`) for targeted runs.
- Keep assertions exact for error strings (as current tests do) to avoid false positives.
- Capture screenshots on failure for login scenarios for quick triage.

### Out-of-Scope (for this plan)
- Password reset flows (not present on SwagLabs demo).
- Account creation or MFA.

This test plan is formatted for direct use in manual execution and serves as a backlog for extending your Playwright Java automation suite.
