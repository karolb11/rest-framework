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
The **Author** entity possesses properties such as **id**, **first name**, **last name**, **date of birth**, and more. <br>
Additionally, it includes a collection of **local descriptors**.
A **Local Descriptor** is an entity with two attributes: **local identifier** and **data source.**
This entity is designed to establish links between the **Author** entity and its various representations in third-party systems.<br>
Each **author** must be unique across the system based on their **firstName** and **lastName** attributes combination.<br>
It is also necessary to implement an endpoint that enables the comprehensive updating of author details,
encompassing both basic attributes and **local descriptors**.
An important consideration in the update procedure is the requirement to preserve the **IDs** of sub-objects.
Therefore, when an API user submits a PUT author request and includes the **ID** of a **descriptor** that already exists within this **author**,
the system must retain the **ID** of this **descriptor** and proceed to update its properties.

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
The **web** package contains subpackages, each first level subpackage representing a specific API version. 
Any class in package dedicated for specific api version **must not** depend on any class from package of different api version
and must not be a dependency of classes from inside domain package.
ArchUnit tests ensure that those core assumptions are met.<br>
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

### Validation Factory refactor
To reduce the size of the **buildAuthorDTOValidator** method,
we can extract the instantiation and configuration of **ValidationRules** into separate methods. 
The final structure of the method is as follows:
```java
public Validator<AuthorDTO> buildAuthorDTOValidator() {
        var config = new ValidationConfig<AuthorDTO>();

        config.addRules(identityConstraint(AuthorDTO::id, AuthorDTO.Fields.id));
        config.addRules(unicityConstraint(this::hasDuplicates));

        config.addRules(firstNameConstrants());
        config.addRules(lastNameConstraints());
        config.addRules(dateOfBirthConstraints());
        config.addRules(dateOfDeathConstraints());
        config.addRules(localDescriptorConstraints());

        return new Validator<>(config);
    }
```

### unicityConstraint

**UnicityConstraint** method creates a constraint if the processed author is not a duplicate of already existing ones. 
The method requires a predicate as its argument. 
The predicate should return true if the author is a duplicate.

```java
private boolean hasDuplicates(AuthorDTO authorDTO, ValidationContext context) {
        var collidingAuthor = authorFacade.findAuthor(authorDTO.firstName(), authorDTO.lastName());
        return switch (context.operationType()) {
            case CREATE -> collidingAuthor.isPresent();
            case UPDATE -> collidingAuthor
                    .map(Author::getId)
                    .filter(id -> !id.equals(context.updatedAggregateResourceId()))
                    .isPresent();
        };
    }
```

## Deeper jump into Validation Framework
Each **ValidationRules** (being added to validation config using **addRules** method) is an interface.
It has multiple implementations allowing for defining a set of validation rules for specific DTO property.
The interface declares an **execute** method returning stream of **ConstraintViolations**

```java
public interface ValidationRules<O> {
    Stream<ConstraintViolation> execute(O obj, ValidationContext context);
}
```

StringFieldConstraints is likely most commonly used implementation of ValidationRules.
It is dedicated for validation of String-typed properties.

```java
public class StringFieldConstraints<O> implements ValidationRules<O> {

    private final Function<O, String> valueGetter;
    private final String fieldLabel;
    private final Collection<Constraint<O, String>> constraints;

    StringFieldConstraints(Function<O, String> valueGetter, String fieldLabel) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.constraints = new ArrayList<>();
    }

    @Override
    public Stream<ConstraintViolation> execute(O obj, ValidationContext context) {
        var value = valueGetter.apply(obj);
        return constraints.stream().flatMap(constraint -> constraint.check(obj, context, value, fieldLabel));
    }

    public StringFieldConstraints<O> mandatory() {
        constraints.add(new MandatoryStringConstraint<>(fieldLabel));
        return this;
    }

    public StringFieldConstraints<O> minLength(int minLength) {
        constraints.add(new MinStringLengthConstraint<>(fieldLabel, minLength));
        return this;
    }

    public StringFieldConstraints<O> maxLength(int maxLength) {
        constraints.add(new MaxStringLengthConstraint<>(fieldLabel, maxLength));
        return this;
    }
}
```

As mentioned earlier, the object constructor requires a function that allows obtaining the value of the validated property 
and the label, which will be further used to address recognized violations.<br>
My implementation allows for defining three validation criteria:
- whether the property is mandatory
- what is minimal length of the string
- what is maximal length of the string

### Extension possibilities

Fluent API of the class provides handy way of validation criteria configuration.<br>
But what exactly the **mandatory**, **minLength**, and **maxLength** methods do?
They add new constraints to the **constraints** collection being an instance variable of the class.

Let's consider what needs to be done when the next validation criterion is required.
For example, when the first character in the string must be a capital letter. 
To meet this requirement, we would have to implement a new **Constraint**, such as **CapitalStartingCharConstraint**, 
and add a new method to the **StringFieldConstraints** class. 
The execution of this new method should add the newly created constraint to the **constraints** collection.

Example implementation of **CapitalStartingCharConstraint**:
```java
class CapitalStartingCharConstraint<O> implements Constraint<O, String> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    CapitalStartingCharConstraint(String fieldLabel) {
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
    }

    @Override
    public Stream<ConstraintViolation> check(O validatedObj, ValidationContext context, String value, String fieldLabel) {
        if (StringUtils.isBlank(value)) {
            return Stream.empty();
        } else if (Character.isUpperCase(value.charAt(0))) {
            return Stream.empty();
        } else {
            return constraintViolationBuilder.mustStartWithCapitalLetter();
        }
    }
}

```

New method of **StringFieldConstraints**:
```java
public StringFieldConstraints<O> startsWithCapital() {
        constraints.add(new CapitalStartingCharConstraint<>(fieldLabel));
        return this;
    }
```

Please note that this extension of StringFieldConstraints required no code modifications. 
All that has been done is extending the Validation Framework with new code, 
which aligns well with the open-closed principle of SOLID.
