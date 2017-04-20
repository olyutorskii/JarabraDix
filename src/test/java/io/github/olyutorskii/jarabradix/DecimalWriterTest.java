/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.jarabradix;

import java.io.CharArrayWriter;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class DecimalWriterTest {

    public DecimalWriterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of print method, of class DecimalWriter.
     * @throws java.io.IOException
     */
    @Test
    public void testPrint_int() throws IOException {
        System.out.println("print");

        DecimalWriter writer;
        CharArrayWriter out;

        out = new CharArrayWriter();
        writer = new DecimalWriter(out);

        out.reset();
        writer.print(1);
        assertEquals("1", out.toString());

        out.reset();
        writer.print(0);
        assertEquals("0", out.toString());

        out.reset();
        writer.print(-1);
        assertEquals("-1", out.toString());

        out.reset();
        writer.print(1_234_567_890);
        assertEquals("1234567890", out.toString());

        out.reset();
        writer.print(Integer.MAX_VALUE);
        assertEquals("2147483647", out.toString());

        out.reset();
        writer.print(Integer.MIN_VALUE);
        assertEquals("-2147483648", out.toString());

        return;
    }

    /**
     * Test of print method, of class DecimalWriter.
     * @throws java.io.IOException
     */
    @Test
    public void testPrint_long() throws IOException {
        System.out.println("print");


        DecimalWriter writer;
        CharArrayWriter out;

        out = new CharArrayWriter();
        writer = new DecimalWriter(out);

        out.reset();
        writer.print(1L);
        assertEquals("1", out.toString());

        out.reset();
        writer.print(0L);
        assertEquals("0", out.toString());

        out.reset();
        writer.print(-1L);
        assertEquals("-1", out.toString());

        out.reset();
        writer.print(1_234_567_890L);
        assertEquals("1234567890", out.toString());

        out.reset();
        writer.print(Long.MAX_VALUE);
        assertEquals("9223372036854775807", out.toString());

        out.reset();
        writer.print(Long.MIN_VALUE);
        assertEquals("-9223372036854775808", out.toString());

        return;
    }

}
