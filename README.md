# krollresolver-ui-tests
A robust TestNG-based UI automation framework for cross-browser testing using Selenium WebDriver, Maven, Log4j, and ExtentReports.

This is a scalable and modular UI automation framework built using:

- ✅ Selenium WebDriver for browser automation
- ✅ TestNG for test orchestration and parallel execution
- ✅ Maven for build and dependency management
- ✅ ExtentReports for rich HTML reporting
- ✅ Jenkins-ready structure for CI/CD integration

### 🔍 Features

- Cross-browser testing (Chrome, Firefox, Edge)
- Parallel execution via TestNG and Maven profiles
- Retry logic with custom listeners and transformers
- Dynamic logging and screenshot capture on failure
- Parameterized test execution via CLI
- Clean page object model with reusable components

### 🚀 How to Run

```bash
# Default test suite
mvn clean test

# Run regression suite
mvn clean test -PRegression_Tests
