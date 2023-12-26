// F40Manage.aidl
package com.jx.androiddemo.aidl2;

// Declare any non-default types here with import statements

interface F40Manage {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void sendMsg(String msg);
}