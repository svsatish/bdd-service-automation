
# 🧪 BDD Service Automation Framework - Petstore API

This project automates API testing of the [Petstore Swagger API](https://petstore.swagger.io)

---
## ✅ Pre-requisites

Before running this project, make sure you have the following tools installed and configured:

### 1. Java
- **Version:** Java 11 or higher
- **Install:** https://adoptopenjdk.net/ or via SDKMAN (`sdk install java 11.0.11.hs-adpt`)
- **Verify:** Run `java -version` in your terminal

### 2. Maven
- **Version:** Maven 3.6 or higher
- **Install:** https://maven.apache.org/install.html
- **Verify:** Run `mvn -version` in your terminal

### 3. IntelliJ IDEA (Community or Ultimate)
- **Download:** https://www.jetbrains.com/idea/download/

### 4. IntelliJ Plugins (Install via IntelliJ Marketplace)
- ✅ **Cucumber for Java** — Required for step definition recognition and Gherkin syntax
- ✅ **Lombok** — For automatic getters/setters, constructor generation
- ✅ **Gherkin** — Provides syntax highlighting and navigation for `.feature` files
- ✅ **RoboPOJOGenerator** — For generating POJO classes from JSON payloads

---

## 📁 Folder Structure

```
bdd-service-automation/
├── pom.xml                         # Maven build file with all dependencies
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── com.example.api/
│   │   │       ├── stepdefinitions/         # Cucumber step definitions
│   │   │       │   └── UserSteps.java
│   │   │       ├── models/user              # Request/response POJO models
│   │   │       │   └── UserRequest.java
│   │   │       ├── utils/                   # Utility classes
│   │   │       │   └── ConfigManager.java   # Get environment specific configurations
│   │   │       │   └── ReportGenerator.java # Custom code for fancy report generation
│   │   │       │   └── RestUtil.java        # Common methods for API actions
│   │   │       └── testrunner/              # Cucumber TestNG test runner
│   │   │       │   └── TestRunner.java 
│   │   └── resources/
│   │       └── features/                    # Gherkin feature files
│   │           └── create_user.feature
│   │       └── config.properties            # Environment-specific configurations
├── reports/
│   └── cucumber-html-reports/               # Generated HTML and JSON reports
└── README.md                                # Documentation for the project
└── .gitignore                               # Ignore not needed files to remote repository
└── testng.xml                               # Defines test cases to run
```

---

## 📦 Dependencies (from `pom.xml`)

| Dependency                    | Purpose                                      |
|------------------------------|----------------------------------------------|
| `rest-assured`               | Simplifies HTTP request/response handling    |
| `testng`                     | Testing framework used for execution         |
| `cucumber-java`              | Cucumber core for writing Gherkin steps      |
| `cucumber-testng`            | Integration between Cucumber and TestNG      |
| `cucumber-reporting`         | Generates rich HTML reports from JSON output |
| `lombok`                     | Reduces boilerplate in POJOs                 |

---

## 🧪 Test Runner

```java
@CucumberOptions(
  features = "src/test/resources/features",
  glue = "com.example.api.stepdefinitions",
  plugin = {
    "pretty",
    "html:target/cucumber-reports/cucumber-html-report.html",
    "json:target/cucumber-reports/cucumber.json"
  },
  monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
```

---

## 📃 Feature File Example

```gherkin
Feature: Create a new user in Petstore

  Scenario: Create a user with valid data
    Given the Petstore API is available
    When I create a new user with the following details:
      | id    | username | firstName | lastName | email             | password | phone       | userStatus |
      | 1001  | johndoe  | John      | Doe      | john@example.com  | pass123  | 1234567890  | 1          |
    Then the user should be created successfully with:
      | code | type    | message |
      | 200  | unknown | 1001    |
```

---

## 🧰 Utility - ConfigManager

Reads environment-based properties from `config.properties`.

```properties
# config.properties
petstore.base.url=https://petstore.swagger.io/v2
```

---

## 📤 Request Model - POJO

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;
}
```

---

## ✅ Validation Logic in Step Definitions

```java
@Then("the user should be created successfully with:")
public void the_user_should_be_created_successfully_with(DataTable dataTable) {
    Map<String, String> expected = dataTable.asMaps().get(0);

    response.then().statusCode(Integer.parseInt(expected.get("code")));
    assertThat(response.jsonPath().getString("type"), is(expected.get("type")));
    assertThat(response.jsonPath().getString("message"), is(expected.get("message")));
}
```

---

## 🚀 Running the Tests

Run from terminal:
```bash
mvn clean test
```

Or, run the feature file directly using your IDE (like IntelliJ) by right-clicking it.

---

## 📊 Reporting

Reports will be generated under:

- HTML Report: `reports/cucumber-html-report/report-feature.html`
- JSON Report: `reports/cucumber-reports/cucumber.json`

These can be plugged into `cucumber-reporting` for advanced dashboards.

---

# 💡 Design Patterns Used

This section outlines the key software design patterns implemented in the BDD API test automation framework and their purposes.

## 1. Builder Pattern
- **Used In**: POJOs under `model` package.
- **Purpose**: Simplifies construction of complex request payloads using chained methods.
```java
User user = User.builder()
                .id(101)
                .username("apiuser")
                .build();
```

## 2. Factory Pattern
- **Used In**: `UserFactory` (custom utility if created).
- **Purpose**: Centralizes object creation logic (e.g., dynamic users with random or test-specific data).

## 3. Singleton Pattern
- **Used In**: `ConfigManager` for reading properties file.
- **Purpose**: Ensures that only one instance of the configuration reader exists and is reused across the framework.

## 4. Strategy Pattern
- **Used In**: `AssertionUtils` class.
- **Purpose**: Allows switching between different types of assertions (e.g., field match, substring, regex) with minimal code change.

## 5. Page Object Model Equivalent for APIs
- **Used In**: `service` package (e.g., `UserService.java`).
- **Purpose**: Abstracts API call details and organizes them into logical classes like service objects.

## 6. DRY & Separation of Concerns
- **Used Throughout**: Utilities, models, services, and test logic are in their own packages.
- **Purpose**: Avoids duplication and improves maintainability and readability of the code.

## 7. Data-Driven Testing
- **Used In**: `.feature` files using Cucumber DataTables.
- **Purpose**: Allows running the same scenario with multiple data sets.

## 8. Template Method Pattern
- **Used In**: `TestRunner` class extending `AbstractTestNGCucumberTests`.
- **Purpose**: Reuses the execution logic defined in the parent class while allowing specific overrides.
