package com.learn.testing;

import org.junit.jupiter.api.*;

// This is the default, which causes class instance creation for every test method and state is not shared
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
// This causes only one class instance creation for every test method and state is shared between test methods
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestInstanceLifecycleTest {

    StringBuilder completed = new StringBuilder("");

    // Invoked after each test method completes
    @AfterEach
    void afterEach() {
        System.out.println("The state of instance object is: " + completed);
    }

    @Test
    @Order(3)
    void testD() {
        completed.append("3-D");
        System.out.println("Running Test D");
    }

    @Test
    @Order(4)
    void testA() {
        completed.append("4-A");
        System.out.println("Running Test A");
    }

    @Test
    @Order(1)
    void testC() {
        completed.append("1-C");
        System.out.println("Running Test C");
    }

    @Test
    @Order(2)
    void testB() {
        completed.append("2-B");
        System.out.println("Running Test B");
    }
}
