# Playwright Java 11 Automation Framework

A Java UI automation project built with **Playwright**, **JUnit 5**, and **Gradle**.

The project is primarily a **training framework** for learning browser automation concepts, reusable functions, nested test flows, and parallel execution.

---

## Technologies

* **Java**
* **Playwright for Java**
* **JUnit 5**
* **Gradle**
* **Chromium**

---

## Project Structure

```text
src
└── test
    └── java
        └── org.example
            ├── MainTest.java
            ├── MainParallelTest.java
            ├── Software.java
            ├── JavaSoftware.java
            └── Variables.java
```

### Main Files

| File                    | Purpose                                            |
| ----------------------- | -------------------------------------------------- |
| `MainTest.java`         | Main sequential Playwright test                    |
| `MainParallelTest.java` | Training example for parallel Playwright execution |
| `Software.java`         | Reusable Playwright/browser functions              |
| `JavaSoftware.java`     | Reusable Java utility functions                    |
| `Variables.java`        | XPath selectors and test configuration             |

---

## MainTest.java

`MainTest.java` contains the main sequential test flow.

The test navigates through the website using three levels:

```text
Main Page
   │
   ├── Top Menu
   │      │
   │      ├── Pagination Page
   │      │       │
   │      │       ├── Item 1
   │      │       ├── Item 2
   │      │       └── Item N
   │      │
   │      └── Next Page
   │
   └── Next Menu
```

The purpose is to automatically visit:

* Every top-menu section
* Every pagination page
* Every item on each page

---

## Test Flow

### Open Main Page

The test starts by navigating to the configured main page:

```java
page.navigate(xpath.MAIN_PAGE);
```

### Top Menu Loop

The first loop processes each top-menu element:

```java
for (int menu = 1; menu <= MENU_TOTAL; menu++)
{
    String menuElement =
            xpath.TOP_MENU_ELEMENT + "[" + menu + "]";

    press(page, menuElement);

    nextPage(page);
}
```

Each menu item starts its own pagination process.

### Pagination Loop

`nextPage()` processes all available pages inside the selected menu.

```text
Menu
 │
 ├── Page 1
 ├── Page 2
 ├── Page 3
 └── ...
```

For every page, the test:

* Counts the available items
* Processes the items
* Attempts to click the next-page button
* Continues until there are no more pages

### Item Loop

`pressItems()` processes the individual items on the current page.

```text
Page
 │
 ├── Item 1 → Item Page → Back
 ├── Item 2 → Item Page → Back
 ├── Item 3 → Item Page → Back
 └── Item N → Item Page → Back
```

The method is also responsible for basic retry and reload handling when an element is not immediately available.

---

## Helper Classes

### Software.java

Contains reusable Playwright functions.

Examples include:

* Clicking elements
* Loading/checking elements
* Counting elements
* Reloading or navigating pages
* Other common browser actions

The purpose is to avoid duplicating the same Playwright code throughout the test.

Example:

```java
public static void press(Page page, String selector)
{
    page.locator(selector).click();
}
```

### JavaSoftware.java

Contains general Java utility functions.

For example:

```java
println(...)
```

This keeps generic Java functionality separate from Playwright functionality.

### Variables.java

Contains selectors and configuration used by the tests.

Examples:

```java
xpath.MAIN_PAGE
xpath.TOP_MENU_ELEMENT
xpath.PRODUCT
xpath.NEXT_PAGE
MENU_TOTAL
```

Keeping selectors outside the test makes them easier to maintain and change.

---

## Browser Setup

Playwright is initialized before each test using JUnit 5:

```java
@BeforeEach
void setUp()
{
    playwright = Playwright.create();

    browser = playwright.chromium().launch(
        new BrowserType.LaunchOptions()
            .setHeadless(false));

    context = browser.newContext(
        new Browser.NewContextOptions()
            .setIgnoreHTTPSErrors(true));

    page = context.newPage();
}
```

The browser runs in **headed mode** so the automation can be observed during execution.

Each test receives its own Playwright browser context and page.

---

## Test Cleanup

Playwright resources are closed after each test:

```java
@AfterEach
void tearDown()
{
    context.close();
    browser.close();
    playwright.close();
}
```

This prevents browser and Playwright processes from remaining open after the test finishes.

---

## MainParallelTest.java

`MainParallelTest.java` is a **training file for learning parallel execution**.

It is intentionally kept separate from `MainTest.java`.

The purpose is to experiment with running Playwright work simultaneously without changing the main sequential test.

### Training Topics

* Running multiple tests or tasks at the same time
* Creating independent Playwright browser contexts
* Understanding `BrowserContext` isolation
* Understanding `Page` isolation
* Working with multiple browser instances
* Using Java threads or executors where appropriate
* Understanding why Playwright objects should not be incorrectly shared
* Comparing sequential and parallel execution
* Understanding how parallel execution can reduce total execution time

The important concept is that parallel tests should have **independent Playwright state**.

```text
Sequential
─────────────────────────────>

Test 1 ────────┐
               │
Test 2         ├── Total Time
               │
Test 3 ────────┘


Parallel
─────────────────────────────>

Test 1 ────────┐
Test 2 ────────┼── Total Time
Test 3 ────────┘
```

The parallel example is not intended to replace `MainTest.java`.

It is a separate training environment for understanding **how parallel Playwright execution works and what must be isolated between concurrent tasks**.

---

## Item Page

The item-page validation is currently a placeholder:

```java
public static void itemPage()
{
    // Write program to use PLD with this framework
}
```

The future implementation can contain validations such as:

```text
Open Item
   │
   ├── Verify URL
   ├── Verify Title
   ├── Verify Product Information
   ├── Verify Images
   ├── Verify Price
   ├── Verify Buttons
   └── Other Assertions
```

Keeping this functionality separate allows item-page validation to be developed without changing the existing menu and pagination logic.

---

## Handling Dynamic / Flaky Elements

Some helper methods contain retry and reload logic for elements that may not be immediately available.

Example:

```java
if (!loadSingleElement(page2, locator + "[" + i + "]"))
{
    println("Item [" + i + "] not found. Reloading.");
    page2.navigate(URL);
}
```

The test can then retry the element.

If the element still cannot be found, the test can skip it:

```java
println("Item [" + i + "] not found. Skipping.");
continue;
```

This training logic demonstrates how automation can handle pages where elements load dynamically or temporarily fail to appear.

---

## Architecture

The current project separates the main responsibilities:

```text
                    MainTest
                       │
             ┌─────────┴─────────┐
             │                   │
        Top Menu Loop        Helpers
             │                   │
        nextPage()          Software.java
             │                   │
      Pagination Loop       JavaSoftware.java
             │
       pressItems()
             │
        Item Loop
             │
        itemPage()


                  Variables.java
                       │
              Selectors / Config


             MainParallelTest
                       │
              Parallel Training
                       │
          ┌────────────┼────────────┐
          │            │            │
       Task 1        Task 2       Task 3
          │            │            │
       Context       Context      Context
          │            │            │
        Page         Page         Page
```

The goal is to keep:

* **Test flow** in the test classes
* **Reusable browser actions** in `Software.java`
* **Java utilities** in `JavaSoftware.java`
* **Selectors and configuration** in `Variables.java`
* **Parallel-execution experiments** in `MainParallelTest.java`

---

## Current Status

### Implemented

* Playwright browser initialization
* Chromium execution
* JUnit 5 lifecycle
* Main-page navigation
* Top-menu iteration
* Pagination iteration
* Item iteration
* Item clicking
* Returning from item pages
* Basic retry/reload handling
* Reusable helper functions
* Externalized XPath selectors
* Separate parallel-execution training example

### Planned

* Item-page validation
* Assertions
* More reliable pagination detection
* Improved exception handling
* Page Object Model implementation
* Reporting
* Better logging
* Additional test cases
* More advanced parallel execution examples

---

## Running the Tests

The tests can be executed from **IntelliJ IDEA** or using **Gradle**.

### Main Sequential Test

```text
MainTest.menu()
```

Runs the main website-navigation flow.

### Parallel Training Test

```text
MainParallelTest
```

Used to experiment with parallel Playwright execution and compare it with the sequential approach.

---

## Learning Goals

This project is intended as training framework for understanding:

```text
Java
  │
  ├── Classes
  ├── Methods
  ├── Loops
  ├── Exceptions
  └── Threads
       │
       ▼
JUnit 5
  │
  ├── @BeforeEach
  ├── @AfterEach
  └── @Test
       │
       ▼
Playwright
  │
  ├── Browser
  ├── BrowserContext
  ├── Page
  └── Locator
       │
       ▼
Automation Architecture
  │
  ├── Reusable Functions
  ├── Selectors
  ├── Test Flow
  ├── Page Objects
  └── Parallel Execution
```

The project is intentionally structured as a learning environment rather than a production automation framework.
