package com.example.lab4;

import org.junit.Test;
import static org.junit.Assert.*;

public class test_MainActivity {

    // Testing the Name and Price entry validation functions

    @Test
    public void testValidateName() {;
        assertTrue(MainActivity.validateName("Orange"));
        assertTrue(MainActivity.validateName("Screw Driver"));
        assertFalse(MainActivity.validateName("$crew Dr!ver"));
    }

    @Test
    public void testValidatePrice() {;
        assertTrue(MainActivity.validatePrice("120000"));
        assertTrue(MainActivity.validatePrice("65.99"));
        assertFalse(MainActivity.validatePrice("KR.99"));
        assertFalse(MainActivity.validatePrice("1.999"));
    }

}
