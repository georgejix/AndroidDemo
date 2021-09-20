package com.test.normal;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

public class 同步锁2
{
    Object object = new Object();

    @Test
    public void main()
    {
        同步锁2 testList = new 同步锁2();
        //testList.test1();
        testList.test3();
    }

    private void test1()
    {
        System.out.println("test1");
        synchronized (object)
        {
            System.out.println("test1 start");
            test2();
            System.out.println("test1 end");
        }
    }

    private void test2()
    {
        System.out.println("test2");
        synchronized (object)
        {
            System.out.println("test2 start");

            System.out.println("test2 end");
        }
    }

    ReentrantLock lock = new ReentrantLock();

    private void test3()
    {
        System.out.println("test3");
        lock.lock();
        System.out.println("test3 start");
        test4();
        System.out.println("test3 end");
        lock.unlock();
    }

    private void test4()
    {
        System.out.println("test4");
        lock.lock();
        System.out.println("test4 start");
        System.out.println("test4 end");
        lock.unlock();
    }
}
