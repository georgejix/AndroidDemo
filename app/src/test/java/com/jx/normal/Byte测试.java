package com.jx.normal; /**
 *
 */

import org.junit.Test;

/**
 * @brief
 * @details
 * @author Administrator
 *
 */
public class Byte测试 {

    @Test
    public void Byte测试() {
        int i1 = 103;
        System.out.println(Integer.SIZE);
        System.out.println(i1 & 0x0000000F);


        byte a1 = -30;
        System.out.println(Integer.toHexString(a1));
        byte a2 = -116;
        System.out.println(Integer.toHexString(a2));
        byte a3 = -88;
        System.out.println(Integer.toHexString(a3));

        byte a4 = -17;
        System.out.println(Integer.toHexString(a4));
        byte a5 = -72;
        System.out.println(Integer.toHexString(a5));
        byte a6 = -113;
        System.out.println(Integer.toHexString(a6));

    }

}
