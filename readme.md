# Introduction
This repository presents a summary of the collaborative efforts between myself and my team in developing CRUD APIs.

## Goals
- Develop an API capable of facilitating the creation and updating of data, with a focus on handling aggregate resource.
- Ensure the API is easily versioned, adhering to the following requirements:
  - Implement a straightforward process for creating new API versions.
  - Establish a mechanism for the systematic removal of deprecated API versions.

## Demo Application
It's evident that I cannot share the actual source code of the enterprise application. Therefore, I've crafted a minimal viable application that illustrates the approach.<br>
The Demo Application serves as a CRUD API for overseeing a catalog of authors and their works (books and articles).<br>
The API facilitates the registration and updating of author details and information regarding the works they have created.<br>
Resource validation includes checks for mandatory properties, certain constraints (such as character count, date format), and ensures uniqueness.

# Solution description

## Used technologies

### Spring
We decided to use Spring framework as a well known technology, 
along with Spring Data Jpa and Spring Web modules as a core tools used in the system.

### Postgres
Business required relational database usage.
The choice of Postgres was made due to its performance aligning accurately with our needs and its license-free usage.

### Java 21
Newest Java version provides some very handy features, which found its usage in the project

# Application architecture

## Framework
The framework offers utility classes that are not directly tied to the domain. 
It serves as a high-level tool, furnishing essential technical features required by the application. 
As a standalone Maven library, it is designed for reusability across all services within the entire system.

## Domain
Domain package is a heart of application. It contains entities, repositories and services handling business logic.
It also exposes a Facade to be used by "Web" components.

## Web
The "web" package contains subpackages, each representing a specific API version. 
All classes within this package tree must be package-private. 
This ensures there are no dependencies between API versions and effectively separates them from the domain. <br>
This separation simplifies the management of API versions:
- To decommission a deprecated version, just delete the corresponding package.
- To stage a new version, create a copy of the latest one and increment its version number.

### DTO
By DTO, I mostly mean class being consumed or served by web controllers.<br>
Utilizing records as Data Transfer Objects is an excellent choice, thanks to their immutability and built-in features.<br>
Immutability of DTOs is crucial to keep the validation and entire DTO processing transparent, easy to monitor and debug.

### Validation
Validation poses the most significant challenge in the entire project. 
Our goal was to develop our own POJO validation framework supporting straightforward validation rules such as mandatory field checks, field size and format validation, and date validation. 
Additionally, the framework is designed to handle more complex domain-related validations, 
including uniqueness checks and validation of relationships between specific properties of POJOs(e.g., ensuring one date is before another).<br>
It is equally crucial to offer a convenient method for declaring a validation configuration for a specific POJO. The configuration must be easy to create, read, and maintain. 
Experience has shown that validators can become intricate when designed and maintained without proper attention.<br>
Validation config is composed of set of validation rules. 
A validation rule is a set of constraints, usually specific to one, particular property of POJO.
Validation framework is intended to be easy expandable, by adding new validation constraints or even entire rules.


