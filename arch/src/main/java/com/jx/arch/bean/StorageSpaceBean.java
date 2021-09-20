package com.jx.arch.bean;


public class StorageSpaceBean
{

    /**
     * 总计存储空间
     */
    public long blockCount;

    /**
     * 可用存储空间
     */
    public long availableCount;

    /**
     * 剩余可用（包括保留块：应用无法使用的空间）
     */
    public long freeBlocks;

    /**
     * 总共
     */
    public long totalSpace;
}
