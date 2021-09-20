package com.jx.arch.util;

import java.util.HashSet;
import java.util.Set;

public class PushTagsUtil
{
    public static Set<String> getAllTags()
    {
        Set<String> tags = new HashSet<>();
        QMLog.i("PushTagsUtil", "set tag: " + GsonHelper.toJson(tags));
        return tags;
    }
}
