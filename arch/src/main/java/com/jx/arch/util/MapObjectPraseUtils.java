package com.jx.arch.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapObjectPraseUtils
{
    /**
     * 将 Object 转为 Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj)
    {
        if (obj == null)
        {
            return null;
        }

        if (obj instanceof Map)
        {
            return (Map<String, Object>) obj;
        }

        Map<String, Object> map = new HashMap<>();

        try
        {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields)
            {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(obj);
                map.put(key, value);
            }

            Class superClazz = obj.getClass().getSuperclass();
            if (GeneralUtils.isNotNull(superClazz))
            {
                Field[] declaredSuperFields = superClazz.getFields();
                if (declaredSuperFields != null
                        && declaredSuperFields.length > 0)
                {
                    for (Field field : declaredSuperFields)
                    {
                        field.setAccessible(true);
                        String key = field.getName();
                        Object value = field.get(obj);
                        map.put(key, value);
                    }
                }
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return map;
    }
}
