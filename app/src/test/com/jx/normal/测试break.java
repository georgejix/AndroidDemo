package com.test.normal;

import org.junit.Test;

public class 测试break
{
    @Test
    public void main()
    {
        测试break test = new 测试break();
        test.testBreak1();
    }

    private void testBreak1()
    {
        ok:
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                System.out.println("i=" + i + ",j=" + j);
                if (j == 5)
                {
                    break ok;
                }

            }
        }
    }

}
