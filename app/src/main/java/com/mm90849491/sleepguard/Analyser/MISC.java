package com.mm90849491.sleepguard.Analyser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * A set of static functions for getting integer from binary file or array.
 * @author Meng Meng
 *
 * 2015/02/26
 */
final public class MISC {

    /**
     * Fill the input byte array by reading a BufferedInputStream as binary file.
     * @param that byte[]: byte array to be filled.
     * @param in BufferedInputStream: binary file to be read.
     * @param BigEndian boolean: true if the last element in the array is the first read from file.
     * @return boolean: TRUE if the file is shorter than the array or corrupted.
     */
    public static boolean read(byte[] that, BufferedInputStream in, boolean BigEndian) {
        int i = 0;
        int c = 0;
        int len = that.length;
        try {
            for(i = 0; (i < len)
                    && ( (c = in.read()) != -1 ) ; i++) {
                if(BigEndian) that[i] = (byte)c;
                    //Little Endian to Big Endian
                else that[len - 1 - i] = (byte)c;
            }
        } catch(IOException e) {
        }
        return (i != len);
    }

    /**
     * Read a four bytes long signed integer from a BufferedInputStream as binary file.
     * BigEndian: 0x0F00 -> 0d240
     * @param in BufferedInputStream: binary file to be read.
     * @return integer.
     */
    public static int readDWord(BufferedInputStream in) {
        int c = 0;
        int sum;
        try {
            c = in.read();
            sum = (byte)c & 0xFF;
            c = in.read();
            sum ^= ((byte)c & 0xFF) << 8;
            c = in.read();
            sum ^= ((byte)c & 0xFF) << 16;
            c = in.read();
            sum ^= (byte)c << 24;
        } catch(IOException e) {
            sum = -1;
        }
        return sum;
    }

    /**
     * Read a two bytes long signed integer from a BufferedInputStream as binary file.
     * BigEndian: 0x0F -> 0d240
     * @param in BufferedInputStream: binary file to be read.
     * @return integer.
     */
    public static int readWord(BufferedInputStream in) {
        int c = 0;
        int sum;
        try {
            c = in.read();
            sum = (byte)c & 0xFF;
            c = in.read();
            sum ^= ((byte)c) << 8;
        } catch(IOException e) {
            sum = -1;
        }
        return sum;
    }

    /**
     * Convert a four bytes array to a signed integer.
     * BigEndian: {0,F,0,0} -> 240
     * @param that byte[].
     * @return integer.
     */
    public static int bbbb2int(byte[] that) {
        int sum
                = that[0] << 24;
        sum ^= (that[1] & 0xFF) << 16;
        sum ^= (that[2] & 0xFF) << 8;
        sum ^= that[3] & 0xFF;
        return sum;
    }

    /**
     * Convert a four bytes array to an unsigned integer.
     * BigEndian: {0,F,0,0} -> 240
     * @param that byte[].
     * @return integer.
     */
    public static int bbbb2uint(byte[] that) {
        int sum
                = (that[0] & 0xFF) << 24;
        sum ^= (that[1] & 0xFF) << 16;
        sum ^= (that[2] & 0xFF) << 8;
        sum ^= that[3] & 0xFF;
        return sum;
    }

    /**
     * Convert a three bytes array to an unsigned integer.
     * BigEndian: {0,F,0} -> 240
     * @param that byte[].
     * @return integer.
     */
    public static int bbb2int(byte[] that) {
        int sum
                = that[0] << 16;
        sum ^= (that[1] & 0xFF) << 8;
        sum ^= (that[2] & 0xFF);
        return sum;
    }

    /**
     * Convert a two bytes array to an unsigned integer.
     * BigEndian: {0,F} -> 240
     * @param that byte[].
     * @return integer.
     */
    public static int bb2uint(byte[] that) {
        int sum
                = (that[0] & 0xFF) << 8;
        sum ^= (that[1] & 0xFF);
        return sum;
    }

    /**
     * Convert a four bytes array to an signed integer.
     * LittleEndian: {0,0,F,0} -> 240
     * @param that byte[].
     * @return integer.
     */
    public static int llll2int(byte[] that) {
        int sum
                = that[0] & 0xFF;
        sum ^= (that[1] & 0xFF) << 8;
        sum ^= (that[2] & 0xFF) << 16;
        sum ^= that[3] << 24;
        return sum;
    }

    /**
     * Convert a four bytes array to an unsigned integer.
     * LittleEndian: {0,0,F,0} -> 240
     * @param that byte[].
     * @return integer.
     */
    public static int llll2uint(byte[] that) {
        int sum = 0;
        sum = that[0] & 0xFF;
        sum ^= (that[1] & 0xFF) << 8;
        sum ^= (that[2] & 0xFF) << 16;
        sum ^= (that[3] & 0xFF) << 24;
        return sum;
    }

    /**
     * Convert a three bytes array to an signed integer.
     * LittleEndian: {0,F,0} -> 240
     * @param that byte[].
     * @return integer.
     */
    public static int lll2int(byte[] that) {
        int sum
                = that[0] & 0xFF;
        sum ^= (that[1] & 0xFF) << 8;
        sum ^= (that[2]) << 16;
        return sum;
    }

    /**
     * Convert a two bytes array to an unsigned integer.
     * LittleEndian: {F,0} -> 240
     * @param that byte[].
     * @return integer.
     */
    public static int ll2uint(byte[] that) {
        int sum
                = that[0] & 0xFF;
        sum ^= (that[1] & 0xFF) << 8;
        return sum;
    }

    /**
     * Convert a byte to an unsigned integer.
     * LittleEndian: 0xF -> 0d15
     * @param that byte.
     * @return integer.
     */
    public static int byte2uint(byte that) {
        return that & 0xFF;
    }

    /**
     * MISC test driver.
     * @param args no use.
     */
    public static void main(String[] args) {
        byte[] mm = { (byte) 0xF1, (byte) 0x00,(byte) 0x10, (byte) 0x01};
        byte[] mmm = { (byte) 0x01, (byte) 0x10, (byte) 0x00,(byte) 0xF1};
        BigInteger m2 = new BigInteger(mmm);
        System.out.println(MISC.bbbb2int(mmm));
        System.out.println(m2.intValue());
        System.out.println();
        System.out.println(MISC.llll2int(mm));
        System.out.println(m2.intValue());
        System.out.println();
        System.out.println(MISC.byte2uint((byte) 0xF1));
    }
}
