package com.jx.normal;

class 测试接口抽象类
{
}

abstract class A{
    int getA1(){
        return 1;
    }
    abstract int getA2();
}

abstract class A2 extends A{

}

class A3 extends A2{

    @Override
    int getA2()
    {
        return 0;
    }
}

interface B{
    int getB();
}

interface B2 extends B{

}

class B3 implements B2{

    @Override
    public int getB()
    {
        return 0;
    }
}

interface C{
    int getB();
}

class A1 implements B,C{

    @Override
    public int getB()
    {
        return 0;
    }
}
