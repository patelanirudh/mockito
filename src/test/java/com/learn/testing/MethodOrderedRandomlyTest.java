package com.learn.testing;

import org.junit.jupiter.api.*;

@Order(3)
@TestMethodOrder(MethodOrderer.Random.class)
public class MethodOrderedRandomlyTest {

    @Test
    void testA() {
        System.out.println("Running Test A");
    }

    @Test
    void testB() {
        System.out.println("Running Test B");
    }

    @Test
    void testC() {
        System.out.println("Running Test C");
    }

    @Test
    void testD() {
        System.out.println("Running Test D");
    }
}
