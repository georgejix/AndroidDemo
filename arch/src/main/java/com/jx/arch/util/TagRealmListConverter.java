package com.jx.arch.util;

import android.nfc.Tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import io.realm.RealmList;

public class TagRealmListConverter implements JsonSerializer<RealmList<Tag>>,
        JsonDeserializer<RealmList<Tag>>
{

    @Override
    public JsonElement serialize(RealmList<Tag> src, Type typeOfSrc,
                                 JsonSerializationContext context)
    {
        JsonArray ja = new JsonArray();
        for (Tag tag : src)
        {
            ja.add(context.serialize(tag));
        }
        return ja;
    }

    @Override
    public RealmList<Tag> deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context)
            throws JsonParseException
    {
        RealmList<Tag> tags = new RealmList<>();
        JsonArray ja = json.getAsJsonArray();
        for (JsonElement je : ja)
        {
            tags.add((Tag) context.deserialize(je, Tag.class));
        }
        return tags;
    }

}
