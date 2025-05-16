
# JInputGuard

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jinputguard_jinputguard&metric=alert_status&token=42442b67d269c6a17b4578ba2d87731c92b8922a)](https://sonarcloud.io/summary/new_code?id=jinputguard_jinputguard)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=jinputguard_jinputguard&metric=security_rating&token=42442b67d269c6a17b4578ba2d87731c92b8922a)](https://sonarcloud.io/summary/new_code?id=jinputguard_jinputguard)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=jinputguard_jinputguard&metric=vulnerabilities&token=42442b67d269c6a17b4578ba2d87731c92b8922a)](https://sonarcloud.io/summary/new_code?id=jinputguard_jinputguard)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jinputguard_jinputguard&metric=coverage&token=42442b67d269c6a17b4578ba2d87731c92b8922a)](https://sonarcloud.io/summary/new_code?id=jinputguard_jinputguard)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.jinputguard/jinputguard.svg?color=blue)](https://maven-badges.herokuapp.com/maven-central/io.github.jinputguard/jinputguard/)
[![Javadoc](https://javadoc.io/badge2/io.github.jinputguard/jinputguard/javadoc.svg?color=blue)](https://javadoc.io/doc/io.github.jinputguard/jinputguard)
[![Apache License 2.0](https://img.shields.io/:license-Apache%20License%202.0-blue.svg)](https://github.com/jinputguard/jinputguard/blob/main/LICENSE)

**A Java library to sanitize, transform and validate inputs.**

* [Quick Start](#quick-start)
* [Installation](#installation)
* [Introduction](#introduction)
  * [Definition](#definition)
  * [Why should I use it?](#why-should-i-use-it)
* [Usage](#usage)
  * [Base builder](#base-builder)
  * [Specialized builders](#specialized-builders)
  * [Sanitization](#sanitization)
  * [Mapping](#mapping)
  * [Validation](#validation)
  * [Handling null](#handling-null)
  * [Nesting guards](#nesting-guards)
  * [Testing result](#testing-result)
  * [Getting the value or the failure](#getting-the-value-or-the-failure)
* [Customization](#customization)
  * [Custom specialized builders](#custom-specialized-builders)
  * [Custom exception](#custom-exception)
* [License](#license)
* [Contact](#contact)

# Quick Start
Create input guards and use them at the beginning of any method to process input arguments, particularily when they come from user (e.g. in controller layer).

* Add JInputGuard to your project's dependencies (see [installation](#installation))
* Create your first guard using the discoverable and fluent builder API:
```java
InputGuard<String, Integer> guard = InputGuard.builder()
    .forString()
    .sanitize().strip().replace(".0", "").then()
    .validateThat().isMaxLength(3).then()
    .mapToInteger()
    .validateThat().isPositive().then()
    .sanitize().clamp(10, 100).then()
    .build();
```
* Process some input value and get the result, or throw an exception in case of failure:
```java
Integer resultOK1 = guard.process("  42  ").getOrThrow(); // result = 42
Integer resultOK2 = guard.process("8 ").getOrThrow();     // result = 10
Integer resultOK3 = guard.process(" 150.0").getOrThrow(); // result = 100
Integer resultKO4 = guard.process("12345 ").getOrThrow(); // throw InputGuardFailureException "value is too long"
Integer resultKO5 = guard.process("-8").getOrThrow();     // throw InputGuardFailureException "value must be positive"
Integer resultKO6 = guard.process("abc").getOrThrow();    // throw InputGuardFailureException - NumberFormatException
```

# Installation
To include JInputGuard in your project, add the following dependency to your `pom.xml` if you are using Maven (be sure to use the latest version):
```xml
<dependency>
    <groupId>io.github.jinputguard</groupId>
    <artifactId>jinputguard</artifactId>
    <version>0.0.1</version>
</dependency>
```

For Gradle, add the following to your `build.gradle`:
```
implementation 'io.github.jinputguard:jinputguard:0.0.1'
```

# Introduction

## Definition
A guard is represented by a functional interface, exposing a `process` method that takes an input value and returning a `GuardResult`. This results contains either an output value or a failure (more on that below in the doc).

Processing an input argument consists basically in performing sequential operations on it. Base operations are:
* **sanitization**: transform a value into a value of the _same_ type (e.g. `String` to `String` by applying `String.strip()`)
* **mapping**: transform a value into a value of _another_ type (e.g. `String` to `Integer`, or `String` to `UUID`)
* **validation**: verifies a value matches a given condition

See [usage](#usage) for more details about available operations.

## Why should I use it?
JInputGuard was developed with the vision of facilitating build and usage of Value Objects in your code base. But even if you're not using Value Objects (you should consider it 😉), input sanitization and validation is still a mandatory requirement to mitigate risks exposed by [CWE-1019: Validate Inputs](https://cwe.mitre.org/data/definitions/1019.html): SQL/XML injections, CSRF, etc.

To facilitate adoption, JInputGuards is/has:
* Discoverable API: Thanks to fluent methods, use your IDE auto-completion to easily create and use guards.
* Fully extensible: Write your own sanitization, transformation or validation functions to satisfy your needs.
* Immutability: Guards are immutable and thread-safe POJOs in their default implementation.

# Usage

## Base builder
Base guard builder is obtained by calling the `InputGuard.builder().forClass(Class<T>)` method, which returns a `InputGuardBuilder`. From there you can call generic sanitization, validation and mapping methods to configure guard process steps. These methods usualy take lambdas as arguments.
Finish the creation by calling the `build()` method to get an instance of `InputGuard`.

Below is an example of a very simple guard taking a `String` as input and returning an `Integer` as output. It should be pretty straightforward to understand:
```java
InputGuard<String, Integer> myGuard = InputGuard.builder()
    .forClass(String.class)                                         // For "String" input type
    .sanitize(String::strip)                                        // Remove leading and traling whitespace chars
    .sanitize(value -> value.replace(".0", ""))                     // Remove any ".0" in the String
    .validate(value -> value.length() <= 3, "value is too long")    // Ensure String is 3 chars max
    .map(Integer::parseInt)                                         // Parse String into Integer
    .build();
```


## Specialized builders
For a more practical and usefull approach, use others `InputGuard.builder().forXXX()` methods to get specialized builders, that helps you build guards in a more easy and efficient way: they expose various predefined and discoverable methods, saving you time and effort.

Those predefined builders are directly accessible using appropriate `InputGuard.builder().forXXX()` methods.

Here is the same guard than in the base builder example, but with specialized `String` builder:
```java
var myGuard = InputGuard.builder()
    .forString() // also available: forInteger, forLong, etc. (more to come in future versions)
    .sanitize().strip().replace(".0", "").then() // Specialized String sanitizer
    .validateThat().isMaxLength(3).then()        // Specialized String validator
    .build();
```

## Sanitization
Sanitization is the process of applying a transformation to data while preserving its type.
It is mainly used for cleaning input data and making it safe to use in your code (e.g., removing illegal characters from a string, clamping numbers, etc.). 
It can also be used to format values to adhere to certain standards (e.g., forcing capital letters for family names).
```java
// InputGuardBuilder.sanitize(Function<T, T>)
InputGuard<String, String> myGuard = InputGuard.builder()
  .forClass(String.class)
  .sanitize(String::strip)
  .build();
  
// Input value " abc123 " becomes "abc123"
```

Specialized builders propose various built-in sanitization methods:
```java
InputGuard<String, String> myGuard = InputGuard.builder()
  .forString()
  .sanitize()        // Enter sanitization extend point
    .strip()
    .toUpperCase()
    .prefix("ID-")
    .then()          // Exist sanitization extend point
  .build();

// Input value " abc123 " becomes "ID-ABC123"
```

## Mapping
Mapping is the process of transforming a variable of a given type into another type. 

For example, converting a `String` to a `Integer` can be done this way:
```java
// InputGuardBuilder.map(Function<T, R>)
InputGuard<String, Integer> myGuard = InputGuard.builder()
  .forClass(String.class)
  .map(Integer::parseInt)
  .build();
// Input value "123" becomes 123 (Integer)
```

Specialized builders propose various built-in mapping methods:
```java
InputGuard<String, Integer> myGuard = InputGuard.builder()
  .forString()
  .mapToInteger()
  .build();
// Input value "123" becomes 123 (Integer)
```

## Validation
Validation is the process of checking if a variable or data meets certain criteria or constraints. For example, validation can involve verifying that an email address is in the correct format, ensuring a number falls within a specified range, or confirming that required fields are not empty.

The `validate` method takes a `Function<T, ValidationError>` as argument, returning `null` if the value is valid, or a `ValidationError` otherwise. `ValidationError` is a _sealed interface_, you can create your own errors by extending the `CustomValidationError`.


```java
// InputGuardBuilder.validate(Function<T, ValidationError>)
InputGuard<String, Integer> myGuard = InputGuard.builder()
  .forClass(String.class)
  .validate(value -> value.isEmpty() ? new ValidationError.StringIsEmpty() : null)
  .build();
```

Specialized builders propose various built-in validation methods:
```java
InputGuard<String, Integer> myGuard = InputGuard.builder()
  .forString()
  .validateThat().isNotEmpty().then()
  .build();
```

## Handling null
By default, `null` values are processed by guards, meaning it can lead to NullPointerException.
To tackle that, you can declare a _null strategy_ when building your guard. This strategy will be effective from the point you declare it until the rest of the guard or until you declare another _null strategy_.

Available strategies are:
* Process (default): Continue process of <code>null</code> value. This may raise NullPointerException inside guard.
* Skip: Skip the process of <code>null</code> value, meaning a <code>null</code> input will provide a <code>null</code> output.
* Fail: Fail the process of <code>null</code> value, meaning the result will be a failure.
* UseDefault: Use the provided default value if the input value is <code>null</code>.

```java
InputGuard<String, Integer> myGuard = InputGuard.builder()
  .forClass(String.class)
  .ifNullThen().useDefault("0") // Choose null strategy
  .mapToInteger()
  .build();
```

## Nesting guards
It is possible to reuse guards by nesting one into another.

```java
var innerGuard = InputGuard.builder()
    .forString()
    .sanitize().strip().then()
    .build();
    
var outerGuard = InputGuard.builder()
    .forString()
    .apply(innerGuard) // Use "apply(InputGuard<T, T> guard)"
    .validateThat().isMaxLength(3).then()
    .build();
```

## Testing result
Guard process result is a `GuardResult`, containing either an output value or a failure. Testing the result is done with the `isSuccess()` and `isFailure()` methods.

```java
InputGuard<String, Integer> myGuard = ...;
    
GuardResult<Integer> result = myGuard.process(" 123.0 ");

if (result.isSuccess()) { // or "isFailure()"
    System.out.println("Guard OK :)");
} else {
    System.err.println("Guard KO :(");
}
```

## Getting the value or the failure
In case of success, the output value can be retrieved using the `GuardResult.get()` method.
In case of failure, the failure is retrieved with the `GuardResult.getFailure()` method. This object is of type `GuardFailure` and contains details about the failure. 

Important note: calling one of these methods when the result is in the opposite state (e.g. calling `get()` when `isFailure()` returns `true`) throws an `IllegalStateException`.

```java
GuardResult<Integer> result = ...;
if (result.isSuccess()) {
    System.out.println("Guard OK: " + result.get());
    // Calling result.getFailure() here would throw an IllegalStateException
} else {
    System.err.println("Guard KO: " + result.getFailure());
    // Calling result.get() here would throw an IllegalStateException
}
```

You can also directly get the output value by calling the `getOrThrow()` method, without testing first. In case of failure, an `InputGuardFailureException` is thrown (extends `IllegalArgumentException`), containing the `GuardFailure`.
```java
GuardResult<Integer> result = myGuard.process("notAnInt");
var i = result.getOrThrow(); // throws InputGuardFailureException
```

# Customization

## Custom specialized builders
In case the specialized builder for a specific type does not (yet) exists, or you require custom guard methods, you can create it by extending `AbstractInputGuardBuilder` (see `StringInputGuardBuilder` in code as an example):
```java
// InputGuardBuilder.map(Function<T, R>, Function<InputGuard<T, R>, B>)
InputGuard<String, Integer> myGuard = InputGuard.builder()
  .forString()
  .map(MyCustomType::parse, MyCustomTypeInputGuardBuilder::new) // Map String into MyCustomType using MyCustomType.parse(String), and return a new specialized builder
  .anyCustomMethod(/* ... */) // Use MyCustomTypeInputGuardBuilder specialized methods
  .build();
```


## Custom exception
Input validation should not always throw the same exception everywhere in your code. This is particulary true if you're using value objects: when validating user input in the controller layer you probably want to throw some kind of "http bad request" exception, but fetching the same value from database is not caused by users so "http bad request" would not apply here - it should rather be an internal exception.

You can then use the `GuardResult.getOrThrow(Function<GuardFailure, X>)` method to map the `GuardFailure` to your own exception of type `X` (must extends `RuntimeException`).
```java
GuardResult<Integer> result = myGuard.process("notAnInt");
var i = result.getOrThrow(failure -> new MyCustomException(failure)); // throws MyCustomException
```

# License
This project is licensed under the Apache License 2.0. See the LICENSE file for details.

# Contributing & Contact
For any questions or feedback, please open an issue on this repository.