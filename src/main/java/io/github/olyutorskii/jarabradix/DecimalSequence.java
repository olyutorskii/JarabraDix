/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.jarabradix;

import java.io.IOException;
import java.io.Writer;
import java.nio.BufferOverflowException;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

/**
 * Decimal character sequence with {@link java.lang.CharSequence} API.
 *
 * <p>Arabic digit characters holder. (with leading minus-sign if negative)
 *
 * <p>int and long value input supported.
 *
 * @see java.lang.Integer#toString(int)
 * @see java.lang.Long#toString(long)
 */
public class DecimalSequence implements CharSequence{

    private static final int CBUFLEN = 20;
    private static final int LASTPOS = CBUFLEN - 1;


    static{
        assert CBUFLEN == ArabicEncoder.MINLEN_LONG;
    }


    // tail aligned buffer
    private final char[] cbuf;

    private int span;
    private int headPos;


    /**
     * Constructor.
     *
     * <p>Initial digit sequence is &quot;0&quot; (Zero)
     */
    public DecimalSequence(){
        super();
        this.cbuf = new char[CBUFLEN];
        initZero();
        return;
    }


    /**
     * init to zero.
     */
    private void initZero(){
        this.cbuf[LASTPOS] = '0';
        this.headPos = LASTPOS;
        this.span = 1;
        return;
    }

    /**
     * Update decimal sequence by int value.
     *
     * @param iVal int value
     * @return decimal sequence length
     * @see java.lang.Integer#toString(int)
     */
    public int update(int iVal){
        int len = ArabicEncoder.int2Arabic(iVal, this.cbuf, LASTPOS);
        this.span = len;
        this.headPos = CBUFLEN - len;
        return len;
    }

    /**
     * Update decimal sequence by long value.
     *
     * @param lVal long value
     * @return decimal sequence length
     * @see java.lang.Long#toString(long)
     */
    public int update(long lVal){
        int len = ArabicEncoder.long2Arabic(lVal, this.cbuf, LASTPOS);
        this.span = len;
        this.headPos = CBUFLEN - len;
        return len;
    }

    /**
     * Write digits to CharBuffer.
     *
     * @param nioCbuf output
     * @return digits length
     * @throws BufferOverflowException
     * If buffer's current position is not smaller than its limit
     * @throws ReadOnlyBufferException
     * If buffer is read-only
     */
    public int flushDigitTo(CharBuffer nioCbuf)
            throws BufferOverflowException,
                   ReadOnlyBufferException {
        nioCbuf.put(this.cbuf, this.headPos, this.span);
        return this.span;
    }

    /**
     * Write digits to StringBuilder.
     *
     * @param buf output
     * @return digits length
     */
    public int flushDigitTo(StringBuilder buf){
        buf.append(this.cbuf, this.headPos, this.span);
        return this.span;
    }

    /**
     * Write digits to StringBuffer.
     *
     * @param buf output
     * @return digits length
     */
    public int flushDigitTo(StringBuffer buf){
        buf.append(this.cbuf, this.headPos, this.span);
        return this.span;
    }

    /**
     * Write digits to Writer.
     *
     * @param writer output
     * @return digits length
     * @throws IOException If an I/O error occurs
     */
    public int flushDigitTo(Writer writer)
            throws IOException{
        writer.write(this.cbuf, this.headPos, this.span);
        return this.span;
    }

    /**
     * Write digits to Appendable.
     *
     * @param app output
     * @return digits length
     * @throws IOException If an I/O error occurs
     */
    public int flushDigitTo(Appendable app)
            throws IOException{
        for(int idx = this.headPos; idx <= LASTPOS; idx++){
            char decimalCh = this.cbuf[idx];
            app.append(decimalCh);
        }

        return this.span;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int length(){
        return this.span;
    }

    /**
     * {@inheritDoc}
     * @param index {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public char charAt(int index)
            throws IndexOutOfBoundsException{
        if(index < 0){
            throw new IndexOutOfBoundsException();
        }

        char result = this.cbuf[this.headPos + index];
        return result;
    }

    /**
     * {@inheritDoc}
     * @param start {@inheritDoc}
     * @param end {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public CharSequence subSequence(int start, int end)
            throws IndexOutOfBoundsException{
        if(start < 0){
            throw new IndexOutOfBoundsException();
        }

        int offset = this.headPos + start;
        int len = end - start;

        String result = new String(this.cbuf, offset, len);

        return result;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String toString(){
        String result = new String(this.cbuf, this.headPos, this.span);
        return result;
    }

}
