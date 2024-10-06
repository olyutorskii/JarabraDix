/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.jarabradix;

/**
 * Convert integer value to Arabic-decimal character sequence.
 *
 * <p>It's similar to {@link java.lang.Integer#toString(int)}
 * but, GC-friendly.
 *
 * <p>char buffer grows towards a younger index.
 *
 * @see <a target="_blank"
 * href="https://en.wikipedia.org/wiki/Division_algorithm#Division_by_a_constant">
 * Division by a constant (Wikipedia)
 * </a>
 */
public final class ArabicEncoder {

    /** Required char-array length to convert any int value. */
    public static final int MINLEN_INT  = 11;

    /** Required char-array length to convert any long value. */
    public static final int MINLEN_LONG = 20;

    private static final long INT_MAX_ASLONG = Integer.MAX_VALUE;

    private static final String MINTXT_INT  =          "-2147483648";
    private static final String MINTXT_LONG = "-9223372036854775808";

    private static final int DECA = 10;
    private static final long UINT_MAX = (1L << 32) - 1L;  //  4294967295L

    private static final int CHUNKUNIT = DECA * DECA;
    private static final int CHUNKLIMIT = 65536;

    private static final int DIV10SFT = 19;        // 2^19 = 524288
    private static final int DIV10NUME = 52429;    // near equal 524288 / 10

    private static final int ARABICUC_MASK = 0b0011_0000;

    private static final char[] HECT1;
    private static final char[] HECT0;


    static {
        assert MINTXT_INT .equals(Integer.toString(Integer.MIN_VALUE));
        assert MINTXT_LONG.equals(Long   .toString(Long   .MIN_VALUE));
        assert MINLEN_INT  == MINTXT_INT .length();
        assert MINLEN_LONG == MINTXT_LONG.length();

        HECT1 = new char[CHUNKUNIT];
        HECT0 = new char[CHUNKUNIT];
        for (int idx = 0; idx < CHUNKUNIT; idx++) {
            final int h1 = idx / DECA;
            final int h0 = idx % DECA;
            HECT1[idx] = Character.forDigit(h1, DECA);
            HECT0[idx] = Character.forDigit(h0, DECA);
        }
    }

    static {
        assert CHUNKLIMIT >= CHUNKUNIT / DECA;
        assert (long) (CHUNKLIMIT - 1) * (long) DIV10NUME <= UINT_MAX;

        assert DIV10NUME - ((1 << DIV10SFT) / DECA) <= 1;

        assert ( 1         * DIV10NUME) >>> DIV10SFT == 0;
        assert ((DECA - 1) * DIV10NUME) >>> DIV10SFT == 0;
        assert ( DECA      * DIV10NUME) >>> DIV10SFT == 1;
        assert ((CHUNKLIMIT - 1) * DIV10NUME) >>> DIV10SFT
            ==  (CHUNKLIMIT - 1) / DECA;

        assert Character.forDigit(9, DECA) == (ARABICUC_MASK | 9);
    }


    /**
     * Hidden constructor.
     */
    private ArabicEncoder() {
        assert false;
    }


    /**
     * Convert int value to Arabic-decimal sequence.
     *
     * <p>Result will be aligned to the last array position.
     *
     * @param iVal int value
     * @param cbuf char buffer array output
     * @return stored digits sequence (and sign) length
     * @throws IndexOutOfBoundsException too small array
     * @see java.lang.Integer#toString(int)
     */
    public static int int2Arabic(final int iVal, final char[] cbuf)
            throws IndexOutOfBoundsException {
        int lastPos = cbuf.length - 1;
        return int2Arabic(iVal, cbuf, lastPos);
    }

    /**
     * Convert int value to Arabic-decimal sequence.
     *
     * <p>Result will be aligned to the specified tail position.
     *
     * @param iVal int value
     * @param cbuf char buffer array output
     * @param lastPos index of tail position in array
     * @return stored digits sequence (and sign) length
     * @throws IndexOutOfBoundsException invalid position
     * @see java.lang.Integer#toString(int)
     */
    public static int int2Arabic(
            final int iVal,
            final char[] cbuf,
            final int lastPos
    )
            throws IndexOutOfBoundsException {
        if (iVal == Integer.MIN_VALUE) {
            return cramIntMin(cbuf, lastPos);
        }

        final boolean negative = iVal < 0;
        int cidx = lastPos;
        int absVal32;
        int div32;

        if (negative) {
            absVal32 = -iVal;
        } else {
            absVal32 =  iVal;
        }

        while (absVal32 >= CHUNKLIMIT) {
            div32         = absVal32 / CHUNKUNIT;
            final int mod = absVal32 % CHUNKUNIT;
            absVal32 = div32;

            cbuf[cidx--] = HECT0[mod];
            cbuf[cidx--] = HECT1[mod];
        }

        do {
            div32 = (absVal32 * DIV10NUME) >>> DIV10SFT;
            //  div32 = abs32 * 52429 / 524288
            //        = floor(abs32 * 0.1000003814697265625);
            //        = abs32 / 10;

            final int mod = absVal32 % DECA;
            absVal32 = div32;

            cbuf[cidx--] = (char) (mod | ARABICUC_MASK);
        } while (div32 > 0);

        if (negative) cbuf[cidx--] = '-';

        final int result = lastPos - cidx;

        return result;
    }

    /**
     * Convert long value to Arabic-decimal sequence.
     *
     * <p>Result will be aligned to the last array position.
     *
     * @param lVal long value
     * @param cbuf char buffer array output
     * @return stored digits sequence (and sign) length
     * @throws IndexOutOfBoundsException too small array
     * @see java.lang.Long#toString(long)
     */
    public static int long2Arabic(final long lVal, final char[] cbuf)
            throws IndexOutOfBoundsException {
        int lastPos = cbuf.length - 1;
        return long2Arabic(lVal, cbuf, lastPos);
    }

    /**
     * Convert long value to Arabic-decimal sequence.
     *
     * <p>Result will be aligned to the specified tail position.
     *
     * @param lVal long value
     * @param cbuf char buffer array output
     * @param lastPos index of tail position in array
     * @return stored digits sequence (and sign) length
     * @throws IndexOutOfBoundsException invalid position
     * @see java.lang.Long#toString(long)
     */
    public static int long2Arabic(
            final long lVal,
            final char[] cbuf,
            final int lastPos
    )
            throws IndexOutOfBoundsException {
        if (lVal == Long.MIN_VALUE) {
            return cramLongMin(cbuf, lastPos);
        }

        final boolean negative = lVal < 0L;
        int cidx = lastPos;
        long absVal64;
        long div64;

        if (negative) {
            absVal64 = -lVal;
        } else {
            absVal64 =  lVal;
        }

        while (absVal64 > INT_MAX_ASLONG) {
            div64         =        absVal64 / CHUNKUNIT;
            final int mod = (int) (absVal64 % CHUNKUNIT);
            absVal64 = div64;

            cbuf[cidx--] = HECT0[mod];
            cbuf[cidx--] = HECT1[mod];
        }

        int absVal32 = (int) absVal64;
        int div32;

        while (absVal32 >= CHUNKLIMIT) {
            div32         = absVal32 / CHUNKUNIT;
            final int mod = absVal32 % CHUNKUNIT;
            absVal32 = div32;

            cbuf[cidx--] = HECT0[mod];
            cbuf[cidx--] = HECT1[mod];
        }

        do {
            div32 = (absVal32 * DIV10NUME) >>> DIV10SFT;
            //  div32 = abs32 * 52429 / 524288
            //        = floor(abs32 * 0.1000003814697265625);
            //        = abs32 / 10;

            final int mod = absVal32 % DECA;
            absVal32 = div32;

            cbuf[cidx--] = (char) (mod | ARABICUC_MASK);
        } while (div32 > 0);

        if (negative) cbuf[cidx--] = '-';

        final int result = lastPos - cidx;

        return result;
    }

    /**
     * Store int minimum digits to char array.
     *
     * @param cbuf char buffer
     * @param lastPos index of tail position
     * @return digits (and sign) length
     * @throws IndexOutOfBoundsException invalid position
     * @see java.lang.Integer#MIN_VALUE
     */
    private static int cramIntMin(final char[] cbuf, final int lastPos)
            throws IndexOutOfBoundsException {
        final int startPos = lastPos - (MINLEN_INT - 1);
        MINTXT_INT.getChars(0, MINLEN_INT, cbuf, startPos);
        return MINLEN_INT;
    }

    /**
     * Store long minimum digits to char array.
     *
     * @param cbuf char buffer
     * @param lastPos index of tail position
     * @return digits (and sign) length
     * @throws IndexOutOfBoundsException invalid position
     * @see java.lang.Long#MIN_VALUE
     */
    private static int cramLongMin(final char[] cbuf, final int lastPos)
            throws IndexOutOfBoundsException {
        final int startPos = lastPos - (MINLEN_LONG - 1);
        MINTXT_LONG.getChars(0, MINLEN_LONG, cbuf, startPos);
        return MINLEN_LONG;
    }

}
