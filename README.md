# PaymentOptimizer

## Overview

PaymentOptimizer is a simple Java console application designed to optimally assign orders to available payment methods in order to maximize the total discount obtained.

## Features

* **Data Loading** – parses orders and payment methods from JSON files.
* **Optimization Logic** – a greedy algorithm that selects the best payment method for each order (full card payment, full points payment, or mixed payment with 10% in points).
* **Fat JAR** – a runnable JAR file that includes all dependencies.
* **Unit Tests** – key components (parsers and algorithm) covered by JUnit tests.

## Technologies

* Java 21
* Maven 3.8+
* [Gson](https://github.com/google/gson) (JSON parsing)
* [JUnit 5](https://junit.org/junit5/) (testing)

## Prerequisites

* Java 21 (JDK or JRE)
* Maven 3.8 or newer

## Installation

Clone the repository and navigate into it:

```bash
git clone https://github.com/KacperKornas/Kacper_Kornas_Java_Krakow.git
cd Kacper_Kornas_Java_Krakow
```

## Building

Build the project, run tests, and create the fat JAR:

```bash
mvn clean package
```

The resulting JAR will be located at:

```
target/PaymentOptimizer-1.0-SNAPSHOT.jar
```

## Running the Application

Use the fat JAR to quickly run the application in any Java environment without installing additional dependencies. Provide the paths to the JSON files (by default in `data/`):

```bash
java -jar target/PaymentOptimizer-1.0-SNAPSHOT.jar data/orders.json data/paymentmethods.json
```

**Example output:**

```
mZysk 250.00
PUNKTY 150.00
```

You can also specify absolute paths:

```bash
java -jar target/PaymentOptimizer-1.0-SNAPSHOT.jar /full/path/orders.json /full/path/paymentmethods.json
```

## Running Tests

Execute all unit tests:

```bash
mvn test
```

Or run a single test class:

```bash
mvn -Dtest=JsonOrderParserTest test
```

## Project Structure

```
Kacper_Kornas_Java_Krakow/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/kacper/kornas/
│   │   │   ├── Main.java
│   │   │   ├── model/
│   │   │   │   ├── OrderRaw.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── PaymentMethodRaw.java
│   │   │   │   └── PaymentMethod.java
│   │   │   ├── parser/
│   │   │   │   ├── JsonOrderParser.java
│   │   │   │   └── JsonPaymentMethodParser.java
│   │   │   └── service/
│   │   │       └── PromotionOptimizer.java
│   │   └── resources/data/
│   │       ├── orders.json
│   │       └── paymentmethods.json
│   └── test/java/com/kacper/kornas/
│       ├── parser/
│       │   ├── JsonOrderParserTest.java
│       │   └── JsonPaymentMethodParserTest.java
│       └── service/
│           └── PromotionOptimizerTest.java
```

