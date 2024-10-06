/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.jarabradix;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class ArabicEncoderTest {

    public ArabicEncoderTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of int2Arabic method, of class ArabicEncoder.
     */
    @Test
    public void testInt2Arabic_int_charArr() {
        System.out.println("int2Arabic");

        char[] cbuf;
        int buflen;
        int result;

        cbuf = new char[12];
        buflen = cbuf.length;

        result = ArabicEncoder.int2Arabic(123, cbuf);
        assertEquals(3, result);
        assertEquals("123", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(0, cbuf);
        assertEquals(1, result);
        assertEquals("0", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(-1, cbuf);
        assertEquals(2, result);
        assertEquals("-1", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(1, cbuf);
        assertEquals(1, result);
        assertEquals("1", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(1234567890, cbuf);
        assertEquals(10, result);
        assertEquals("1234567890", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(-1234567890, cbuf);
        assertEquals(11, result);
        assertEquals("-1234567890", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(65536, cbuf);
        assertEquals(5, result);
        assertEquals("65536", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(65535, cbuf);
        assertEquals(5, result);
        assertEquals("65535", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(Integer.MAX_VALUE, cbuf);
        assertEquals(10, result);
        assertEquals("2147483647", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(Integer.MIN_VALUE, cbuf);
        assertEquals(11, result);
        assertEquals("-2147483648", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.int2Arabic(Integer.MIN_VALUE + 1, cbuf);
        assertEquals(11, result);
        assertEquals("-2147483647", new String(cbuf, buflen - result, result));

        return;
    }

    /**
     * Test of int2Arabic method, of class ArabicEncoder.
     */
    @Test
    public void testInt2Arabic_3args() {
        System.out.println("int2Arabic");

        char[] cbuf;
        int buflen;
        int result;

        cbuf = new char[10];
        buflen = cbuf.length;

        result = ArabicEncoder.int2Arabic(123, cbuf, 5);
        assertEquals(3, result);
        assertEquals("123", new String(cbuf, 5 - result + 1, result));

        return;
    }

    /**
     * Test of long2Arabic method, of class ArabicEncoder.
     */
    @Test
    public void testLong2Arabic_long_charArr() {
        System.out.println("long2Arabic");

        char[] cbuf;
        int buflen;
        int result;

        cbuf = new char[20];
        buflen = cbuf.length;

        result = ArabicEncoder.long2Arabic(123L, cbuf);
        assertEquals(3, result);
        assertEquals("123", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.long2Arabic(0L, cbuf);
        assertEquals(1, result);
        assertEquals("0", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.long2Arabic(-123L, cbuf);
        assertEquals(4, result);
        assertEquals("-123", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.long2Arabic(1234567890L, cbuf);
        assertEquals(10, result);
        assertEquals("1234567890", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.long2Arabic(123456789012345L, cbuf);
        assertEquals(15, result);
        assertEquals("123456789012345", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.long2Arabic(Long.MAX_VALUE, cbuf);
        assertEquals(19, result);
        assertEquals("9223372036854775807", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.long2Arabic(Long.MIN_VALUE, cbuf);
        assertEquals(20, result);
        assertEquals("-9223372036854775808", new String(cbuf, buflen - result, result));

        result = ArabicEncoder.long2Arabic(Long.MIN_VALUE + 1L, cbuf);
        assertEquals(20, result);
        assertEquals("-9223372036854775807", new String(cbuf, buflen - result, result));

        return;
    }

    /**
     * Test of long2Arabic method, of class ArabicEncoder.
     */
    @Test
    public void testLong2Arabic_3args() {
        System.out.println("long2Arabic");

        char[] cbuf;
        int result;

        cbuf = new char[10];

        result = ArabicEncoder.long2Arabic(123L, cbuf, 5);
        assertEquals(3, result);
        assertEquals("123", new String(cbuf, 5 - result + 1, result));

        return;
    }

}
