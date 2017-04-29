/*
 */

package pkg.benchmark;

import io.github.olyutorskii.jarabradix.DecimalWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Random;

/**
 *
 */
public class BenchMark {

    static final int ITER = 50;
    static final int NUMS = 1_000_000;

    static int[] randArray(int nums){
        Random rand = new Random();
        int[] result = new int[nums];

        for(int idx = 0; idx < nums; idx++){
            result[idx] = rand.nextInt();
        }

        return result;
    }

    static void benchDecimalWriter(int iter, int[] array, DecimalWriter writer)
            throws IOException{
        for(int ct = 0; ct < iter; ct++){
            for(int iVal : array){
                writer.print(iVal);
            }
        }
        return;
    }

    static void benchPrintWriter(int iter, int[] array, PrintWriter writer)
            throws IOException{
        for(int ct = 0; ct < iter; ct++){
            for(int iVal : array){
                writer.print(iVal);
            }
        }
        return;
    }

    public static void main(String[] args) throws IOException{
        int[] array = randArray(NUMS);
        Writer writer = new NothingWriter();

        boolean testJarabraDix;
        testJarabraDix = true;
//        testJarabraDix = false;

        long startNano;
        long stopNano;

        if(testJarabraDix){
            DecimalWriter decimalWriter = new DecimalWriter(writer);
            benchDecimalWriter(1, array, decimalWriter);
            startNano = System.nanoTime();
            benchDecimalWriter(ITER, array, decimalWriter);
            stopNano = System.nanoTime();
        }else{
            PrintWriter printWriter = new PrintWriter(writer);
            benchPrintWriter(1, array, printWriter);
            startNano = System.nanoTime();
            benchPrintWriter(ITER, array, printWriter);
            stopNano = System.nanoTime();
        }

        long duration = stopNano - startNano;
        long ms = duration / 1000L / 1000L;

        System.out.print(ms);
        System.out.println(" ms");

        return;
    }

    static class NothingWriter extends Writer{

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException{
            return;
        }

        @Override
        public void flush() throws IOException{
            return;
        }

        @Override
        public void close() throws IOException{
            return;
        }

    }

}
