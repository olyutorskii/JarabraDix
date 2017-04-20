/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.jarabradix;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.BufferOverflowException;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class DecimalSequenceTest {

    public DecimalSequenceTest() {
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
     * Test of update method, of class DecimalSequence.
     */
    @Test
    public void testUpdate_int() {
        System.out.println("putInteger");

        DecimalSequence ds;

        ds = new DecimalSequence();
        assertEquals("0", ds.toString());

        ds.update(1);
        assertEquals("1", ds.toString());

        ds.update(-1);
        assertEquals("-1", ds.toString());

        ds.update(1234567890);
        assertEquals("1234567890", ds.toString());

        ds.update(Integer.MAX_VALUE);
        assertEquals("2147483647", ds.toString());

        ds.update(Integer.MIN_VALUE);
        assertEquals("-2147483648", ds.toString());

        return;
    }

    /**
     * Test of update method, of class DecimalSequence.
     */
    @Test
    public void testUpdate_long() {
        System.out.println("putInteger");

        DecimalSequence ds;

        ds = new DecimalSequence();
        assertEquals("0", ds.toString());

        ds.update(1L);
        assertEquals("1", ds.toString());

        ds.update(-1L);
        assertEquals("-1", ds.toString());

        ds.update(1234567890L);
        assertEquals("1234567890", ds.toString());

        ds.update(Long.MAX_VALUE);
        assertEquals("9223372036854775807", ds.toString());

        ds.update(Long.MIN_VALUE);
        assertEquals("-9223372036854775808", ds.toString());

        return;
    }

    /**
     * Test of flushDigitTo method, of class DecimalSequence.
     */
    @Test
    public void testFlushDigitTo_CharBuffer() {
        System.out.println("flushDigitTo");

        DecimalSequence ds;
        CharBuffer nioCbuf;

        ds = new DecimalSequence();
        nioCbuf = CharBuffer.allocate(10);

        assertEquals(0, nioCbuf.position());
        ds.flushDigitTo(nioCbuf);
        assertEquals(1, nioCbuf.position());
        assertEquals('0', nioCbuf.get(0));

        ds.update(123);
        ds.flushDigitTo(nioCbuf);
        assertEquals(4, nioCbuf.position());
        assertEquals('0', nioCbuf.get(0));
        assertEquals('1', nioCbuf.get(1));
        assertEquals('2', nioCbuf.get(2));
        assertEquals('3', nioCbuf.get(3));

        ds.update(1234567);
        try{
            ds.flushDigitTo(nioCbuf);
            fail();
        }catch(BufferOverflowException e){
        }

        nioCbuf.clear();
        nioCbuf = nioCbuf.asReadOnlyBuffer();
        try{
            ds.flushDigitTo(nioCbuf);
            fail();
        }catch(ReadOnlyBufferException e){
        }

        return;
    }

    /**
     * Test of flushDigitTo method, of class DecimalSequence.
     */
    @Test
    public void testFlushDigitTo_StringBuilder() {
        System.out.println("flushDigitTo");

        DecimalSequence ds;
        StringBuilder buf;

        ds = new DecimalSequence();
        buf = new StringBuilder();

        ds.flushDigitTo(buf);
        assertEquals("0", buf.toString());

        ds.update(123);
        ds.flushDigitTo(buf);
        assertEquals("0123", buf.toString());
        ds.flushDigitTo(buf);
        assertEquals("0123123", buf.toString());

        ds.update(4567);
        ds.flushDigitTo(buf);
        assertEquals("01231234567", buf.toString());

        return;
    }

    /**
     * Test of flushDigitTo method, of class DecimalSequence.
     */
    @Test
    public void testFlushDigitTo_StringBuffer() {
        System.out.println("flushDigitTo");

        DecimalSequence ds;
        StringBuffer buf;

        ds = new DecimalSequence();
        buf = new StringBuffer();

        ds.flushDigitTo(buf);
        assertEquals("0", buf.toString());

        ds.update(123);
        ds.flushDigitTo(buf);
        assertEquals("0123", buf.toString());
        ds.flushDigitTo(buf);
        assertEquals("0123123", buf.toString());

        ds.update(4567);
        ds.flushDigitTo(buf);
        assertEquals("01231234567", buf.toString());

        return;
    }

    /**
     * Test of flushDigitTo method, of class DecimalSequence.
     * @throws java.io.IOException
     */
    @Test
    public void testFlushDigitTo_Writer() throws IOException {
        System.out.println("flushDigitTo");

        DecimalSequence ds;
        StringWriter swriter;
        Writer writer;

        ds = new DecimalSequence();
        swriter = new StringWriter();
        writer = swriter;

        ds.flushDigitTo(writer);
        writer.flush();
        assertEquals("0", swriter.toString());

        ds.update(123);
        ds.flushDigitTo(writer);
        writer.flush();
        assertEquals("0123", swriter.toString());

        return;
    }

    /**
     * Test of flushDigitTo method, of class DecimalSequence.
     * @throws java.io.IOException
     */
    @Test
    public void testFlushDigitTo_Appendable() throws IOException {
        System.out.println("flushDigitTo");

        DecimalSequence ds;
        StringWriter swriter;
        Appendable app;

        ds = new DecimalSequence();
        swriter = new StringWriter();
        app = swriter;

        ds.flushDigitTo(app);
        swriter.flush();
        assertEquals("0", swriter.toString());

        ds.update(123);
        ds.flushDigitTo(app);
        swriter.flush();
        assertEquals("0123", swriter.toString());

        return;
    }

    /**
     * Test of subSequence method, of class DecimalSequence.
     */
    @Test
    public void testSubSequence() {
        System.out.println("subSequence");

        DecimalSequence ds;
        CharSequence sub;

        ds = new DecimalSequence();
        ds.update(12345);

        sub = ds.subSequence(0, 5);
        assertEquals("12345", sub.toString());

        sub = ds.subSequence(2, 4);
        assertEquals("34", sub.toString());

        sub = ds.subSequence(2, 2);
        assertEquals("", sub.toString());

        try{
            ds.subSequence(-1, 4);
            fail();
        }catch(IndexOutOfBoundsException e){
        }

        try{
            ds.subSequence(2, 6);
            fail();
        }catch(IndexOutOfBoundsException e){
        }

        try{
            ds.subSequence(4, 2);
            fail();
        }catch(IndexOutOfBoundsException e){
        }

        return;
    }

    /**
     * Test of charAt method, of class DecimalSequence.
     */
    @Test
    public void testCharAt() {
        System.out.println("charAt");

        DecimalSequence ds;

        ds = new DecimalSequence();
        assertEquals('0', ds.charAt(0));

        ds.update(123);
        assertEquals('1', ds.charAt(0));
        assertEquals('2', ds.charAt(1));
        assertEquals('3', ds.charAt(2));

        try{
            ds.charAt(-1);
            fail();
        }catch(IndexOutOfBoundsException e){
        }

        try{
            ds.charAt(3);
            fail();
        }catch(IndexOutOfBoundsException e){
        }

        return;
    }

    /**
     * Test of length method, of class DecimalSequence.
     */
    @Test
    public void testLength() {
        System.out.println("length");

        DecimalSequence ds;

        ds = new DecimalSequence();
        assertEquals(1, ds.length());

        ds.update(9);
        assertEquals(1, ds.length());

        ds.update(-9);
        assertEquals(2, ds.length());

        ds.update(10);
        assertEquals(2, ds.length());

        ds.update(99);
        assertEquals(2, ds.length());

        ds.update(100);
        assertEquals(3, ds.length());

        ds.update(Integer.MIN_VALUE);
        assertEquals(11, ds.length());

        ds.update(Long.MIN_VALUE);
        assertEquals(20, ds.length());

        return;
    }

    /**
     * Test of toString method, of class DecimalSequence.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        DecimalSequence ds;

        ds = new DecimalSequence();
        assertEquals("0", ds.toString());

        ds.update(7);
        assertEquals("7", ds.toString());

        ds.update(8L);
        assertEquals("8", ds.toString());

        ds.update(Integer.MIN_VALUE);
        assertEquals("-2147483648", ds.toString());

        ds.update(Long.MIN_VALUE);
        assertEquals("-9223372036854775808", ds.toString());

        return;
    }

}
