package com.learn.testing;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodOrderedByOrderIndexTest {

    @Test
    @Order(3)
    void testD() {
        System.out.println("Running Test D");
    }

    @Test
    @Order(4)
    void testA() {
        System.out.println("Running Test A");
    }

    @Test
    @Order(1)
    void testC() {
        System.out.println("Running Test C");
    }

    @Test
    @Order(2)
    void testB() {
        System.out.println("Running Test B");
    }
}
