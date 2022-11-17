package com.learn.testing;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Order(2)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MethodOrderedByNameTest {

    @Test
    void testD() {
        System.out.println("Running Test D");
    }

    @Test
    void testA() {
        System.out.println("Running Test A");
    }

    @Test
    void testC() {
        System.out.println("Running Test C");
    }

    @Test
    void testB() {
        System.out.println("Running Test B");
    }
}
