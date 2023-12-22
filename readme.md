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

## Business requirements

### Author registration and update
Provide an endpoint that facilitates author registration.
The Author entity possesses properties such as id, first name, last name, date of birth, and more. <br>
Additionally, it includes a collection of local descriptors.
A Local Descriptor is an entity with two attributes: **local identifier** and **data source.**
This entity is designed to establish links between the Author entity and its various representations in third-party systems.<br>
Each author must be unique across the system based on their **firstName** and **lastName** attributes combination.<br>
It is also necessary to implement an endpoint that enables the comprehensive updating of author details,
encompassing both basic attributes and local descriptors.
An important consideration in the update procedure is the requirement to preserve the IDs of sub-objects.
Therefore, when an API user submits a PUT author request and includes the ID of a descriptor that already exists within this author,
the system must retain the ID of this descriptor and proceed to update its properties.

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
It also exposes a Facade to be used by **Web** components.

## Web
The **web** package contains subpackages, each representing a specific API version. 
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

# Validation framework

Let's examine the class responsible for handling author registration requests. 
The validation process is encapsulated within the private method **validateAuthor**.

```java
class AuthorRegistrationCommandHandler {

  private final AuthorMapper authorMapper;
  private final AuthorFacade authorFacade;
  private final AuthorValidatorFactory authorValidatorFactory;

  public AuthorDTO handle(AuthorDTO authorDTO) throws ValidationException {
    var constraintViolations = validateAuthor(authorDTO);
    if (constraintViolations.isEmpty()) {
      var author = saveAuthor(authorDTO);
      return authorMapper.toAuthorDTO(author);
    } else {
      throw new ValidationException(constraintViolations);
    }
  }

  private Collection<ConstraintViolation> validateAuthor(AuthorDTO authorDTO) {
    var validator = authorValidatorFactory.buildAuthorDTOValidator();
    var validationContext = ValidationContext.builder()
            .operationType(OperationType.CREATE)
            .build();
    return validator.validate(authorDTO, validationContext);
  }
}
```
The **AuthorDTO** validator is created by the **AuthorValidatorFactory** class. 
Subsequently, we need to instantiate a ValidationContext object that contains metadata about the circumstances of the validation process. 
Finally, the **validate** method declared by the **Validator** interface is invoked. 
This method returns a collection of all violations encountered during validation. 
If the resulting collection is empty, the object can be deemed as valid.
```java
class AuthorValidatorFactory {

    private final AuthorFacade authorFacade;
    private final TimeService timeService;

    public Validator<AuthorDTO> buildAuthorDTOValidator() {
        var descriptorValidator = buildLocalDescriptorDTOValidationConfig();
        var config = new ValidationConfig<AuthorDTO>();
        config.addRules(identityConstraint(AuthorDTO::id, AuthorDTO.Fields.id));
        config.addRules(unicityConstraint(this::hasDuplicates));
        config.addRules(stringValidation(AuthorDTO::firstName, AuthorDTO.Fields.firstName)
                .mandatory()
                .minLength(3)
                .maxLength(100)
        );
        config.addRules(stringValidation(AuthorDTO::lastName, AuthorDTO.Fields.lastName)
                .mandatory()
                .minLength(3)
                .maxLength(100)
        );
        config.addRules(dateValidation(AuthorDTO::dateOfBirth, AuthorDTO.Fields.dateOfBirth)
                .mandatory()
                .mustBePast(timeService::currentDate)
                .before(AuthorDTO::dateOfDeath)
        );
        config.addRules(dateValidation(AuthorDTO::dateOfDeath, AuthorDTO.Fields.dateOfDeath)
                .mustBePast(timeService::currentDate)
        );

        config.addRules(composedListValidator(AuthorDTO::localDescriptor, AuthorDTO.Fields.localDescriptor, descriptorValidator));
        return new Validator<>(config);
    }
}
```
The **Validator** is constructed based on a validation configuration. 
This configuration comprises a set of validation rules added using the **addRules** method. 
The ValidationRules interface provides methods that build specific validation rules, such as stringValidation and dateValidation, among others.<br>

Let's take a closer look at the **stringValidation** method. It takes two parameters:
- The **Function<T, String> getter**, which fetches the value of the validated property.
- The **String fieldLabel**, used to customize a label for the validated field (I employ the Lombok annotation @FieldNameConstants to generate labels).

Further constraints can be configured by invoking public methods on the returned object, such as **mandatory,** **minLength,** and **maxLength**.

### unicityConstraint 