package com.broniec.rest.demo;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses
public class ArchitecturalTest {

    @ArchTest
    public ArchRule domainMustNotDependOnWeb = classes().that().resideInAPackage("com.broniec.rest.demo.domain..")
            .should().onlyDependOnClassesThat().resideOutsideOfPackage("com.broniec.rest.demo.web..");

    @ArchTest
    public ArchRule onlyWebV1DependsOnItself = classes().that().resideOutsideOfPackage("com.broniec.rest.demo.web.v1..")
            .should().onlyDependOnClassesThat().resideOutsideOfPackage("com.broniec.rest.demo.web.v1..");

}
