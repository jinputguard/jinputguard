
# JInputGuard

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jinputguard_jinputguard&metric=alert_status&token=42442b67d269c6a17b4578ba2d87731c92b8922a)](https://sonarcloud.io/summary/new_code?id=jinputguard_jinputguard)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=jinputguard_jinputguard&metric=security_rating&token=42442b67d269c6a17b4578ba2d87731c92b8922a)](https://sonarcloud.io/summary/new_code?id=jinputguard_jinputguard)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=jinputguard_jinputguard&metric=vulnerabilities&token=42442b67d269c6a17b4578ba2d87731c92b8922a)](https://sonarcloud.io/summary/new_code?id=jinputguard_jinputguard)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jinputguard_jinputguard&metric=coverage&token=42442b67d269c6a17b4578ba2d87731c92b8922a)](https://sonarcloud.io/summary/new_code?id=jinputguard_jinputguard)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.jinputguard/jinputguard.svg?color=blue)](https://maven-badges.herokuapp.com/maven-central/io.github.jinputguard/jinputguard/)
[![Javadoc](https://javadoc.io/badge2/io.github.jinputguard/jinputguard/javadoc.svg?color=blue)](https://javadoc.io/doc/io.github.jinputguard/jinputguard)
[![Apache License 2.0](https://img.shields.io/:license-Apache%20License%202.0-blue.svg)](https://github.com/jinputguard/jinputguard/blob/main/LICENSE)

**A Java library to sanitize, transform and validate inputs.**

* [Usage](#usage)
* [Why should I use it?](#why-should-i-use-it)
* [Installation](#installation)
* [Features](#features)
  * [Sanitization](#sanitization)
  * [Mapping](#mapping)
  * [Validation](#validation)
  * [Handling null](#handling-null)
  * [Pre-defined builders](#pre-defined-builders)
  * [Nesting guards](#nesting-guards)
  * [Test result](#test-result)
  * [Custom exception](#custom-exception)
* [License](#license)
* [Contact](#contact)

# Usage
Create input guards and use them at the beginning of any method to sanitize and validate input arguments, particularily when they come from user (e.g. in controller layer).

A guard is a sequential list of operations to apply to any value, and returning either a value or an error. Base operations are:
* **sanitization**: transform a value into a value of the _same_ type (e.g. `String` to `String` by applying `String.strip()`)
* **mapping**: transform a value into a value of _another_ type (e.g. `String` to `Integer`, or `String` to `UUID`)
* **validation**: verifies a value matches a given condition

See [features](#Features) for more details about all available operations.

Below is an example of a very simple guard taking a `String` as input and returning an `Integer` as output. It should be pretty straightforward to understand:
```java
InputGuard<String, Integer> myGuard = InputGuard.builder()
    .forClass(String.class)                                         // For "String" input type
	.sanitize(String::strip)                                        // Remove leading and traling whitespace chars
	.sanitize(value -> value.replace(".0", ""))                     // Remove any ".0" in the String
	.validate(value -> value.length() <= 3, "value is too long")    // Ensure String is 3 chars max
	.map(Integer::parseInt)                                         // Parse String into Integer
	.build();
	
Integer valueOK = myGuard.process(" 123.0 ").getOrThrow(); // 123
Integer valueKO = myGuard.process(" abccd ").getOrThrow(); // throw InputGuardFailureException (extends IllegalArgumentException)
```

Note that the same guard can easily be created using the [pre-defined builder for String](#pre-defined-builders):
```java
InputGuard<String, Integer> myGuard = InputGuard.builder()
    .forString()                                    // Use "forString()" instead of "forClass(String.class)"
	.sanitize().strip().replace(".0", "").then()    // Fluent and discoverable API! \o/
	.validateThat().isMaxLength(3).then()
	.mapToInteger()
	.build();
```

# Why should I use it?
JInputGuard was developed with the vision of facilitating build and usage of Value Objects in your code base. But even if you're not using Value Objects (you should envisage it 😉), input sanitization and validation is still a mandatory requirement to mitigate risks exposed by [CWE-1019: Validate Inputs](https://cwe.mitre.org/data/definitions/1019.html): SQL/XML injections, CSRF, etc.

To facilitate adoption, JInputGuards is/has:
* Discoverable API: Thanks to fluent methods, use your IDE auto-completion to easily create and use guards.
* Fully extensible: Write your own sanitization, transdormation or validation functions to satisfy your needs.
* Immutability: Guards are immutable and thread-safe POJOs in their default implementation: reuse them safely all over your codebase.

# Installation
To include JInputGuard in your project, add the following dependency to your `pom.xml` if you are using Maven (be sure to take the latest version):
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

# Features
#### Sanitization
TODO
```java

```

#### Mapping
TODO
```java
// TODO
```

#### Validation
TODO
```java
// TODO
```

#### Handling null
TODO
```java
// TODO
```

#### Pre-defined builders
TODO
```java
// TODO
```

#### Nesting guards
It is possible to reuse guards by nesting one into another.
```java
// TODO
```

#### Test result
TODO
```java
// TODO
```

#### Custom exception
Input validation should not always throw the same exception everywhere in your code. This is particulary true if you're using value objects: when validating user input in the controller layer you probably want to throw some kind of "http bad request" exception, but fetching the same value from database is not caused by users so "http bad request" would not apply here - it should rather be an internal exception.
```java
// TODO
```

# License
This project is licensed under the Apache License 2.0. See the LICENSE file for details.

# Contact
For any questions or feedback, please open an issue on this repository.