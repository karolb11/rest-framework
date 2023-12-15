package com.broniec.rest.demo;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitecturalTest {

    @ArchTest
    public static ArchRule domainNotDependsOnWeb = classes().that().resideInAPackage("...domain...")
            .should().onlyDependOnClassesThat().resideOutsideOfPackage("...web...");

}
