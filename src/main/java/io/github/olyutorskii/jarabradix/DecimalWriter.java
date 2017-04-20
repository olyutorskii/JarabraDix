/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.jarabradix;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Writer for integer(int and long) value printing.
 *
 * <p>It's similar to {@link java.io.PrintWriter}
 * but, GC-friendly.
 *
 * <p>There is thread-safe methods only.
 *
 * @see java.io.PrintWriter
 */
public class DecimalWriter extends FilterWriter{

    private static final int CBUFLEN = 20;
    private static final int LASTPOS = CBUFLEN - 1;

    static{
        assert CBUFLEN == ArabicEncoder.MINLEN_LONG;
    }


    private final char[] cbuf;
    private final Object bufLock;


    /**
     * Constructor.
     * @param out char output stream
     */
    public DecimalWriter(Writer out){
        super(out);
        this.cbuf = new char[CBUFLEN];
        this.bufLock = new Object();
        return;
    }


    /**
     * Print int value.
     *
     * <p>It's similar to {@link java.io.PrintWriter#print(int)}.
     *
     * @param iVal int value
     * @throws IOException output error
     * @see java.io.PrintWriter#print(int)
     */
    public void print(int iVal) throws IOException{
        synchronized(this.bufLock){
            int span = ArabicEncoder.int2Arabic(iVal, this.cbuf, LASTPOS);
            int headPos = CBUFLEN - span;
            write(this.cbuf, headPos, span);
        }
        return;
    }

    /**
     * Print long value.
     *
     * <p>It's similar to {@link java.io.PrintWriter#print(long)}.
     *
     * @param lVal long value
     * @throws IOException output error
     * @see java.io.PrintWriter#print(long)
     */
    public void print(long lVal) throws IOException{
        synchronized(this.bufLock){
            int span = ArabicEncoder.long2Arabic(lVal, this.cbuf, LASTPOS);
            int headPos = CBUFLEN - span;
            write(this.cbuf, headPos, span);
        }
        return;
    }

}
