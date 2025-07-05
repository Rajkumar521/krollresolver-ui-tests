package com.krollresolver.automation.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.krollresolver.automation.utils.StepLogger;

public class MyTransformer implements IAnnotationTransformer {

    @SuppressWarnings("rawtypes")
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
        StepLogger.info("Applied RetryAnalyzer to test method: " +
                (testMethod != null ? testMethod.getName() : "Unknown"));
    }
}