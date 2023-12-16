package com.broniec.rest.demo;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses()
public class ArchitecturalTest {

    @ArchTest
    public ArchRule webMustBePackagePrivate = classes().that().resideInAPackage("..web..")
            .should().bePackagePrivate();


}
