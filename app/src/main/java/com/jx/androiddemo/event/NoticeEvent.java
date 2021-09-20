package com.jx.androiddemo.event;

public class NoticeEvent<T> extends BaseNoticeEvent
{
    public String[] args;

    public Integer[] index;

    public T[] events;


    public NoticeEvent(String tag)
    {
        this.tag = tag;
    }


    public NoticeEvent(String tag, String... args)
    {
        this.tag = tag;
        this.args = args;
    }

    public NoticeEvent(String tag, Integer... index)
    {
        this.tag = tag;
        this.index = index;
    }

    @SafeVarargs
    public NoticeEvent(String tag, T... events)
    {
        this.tag = tag;
        this.events = events;
    }
}
