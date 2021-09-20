package com.jx.arch;

import com.jx.arch.util.QMLog;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

public class MyMigration implements RealmMigration
{
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion)
    {
        QMLog.i("MyMigration", "oldVersion: " + oldVersion + " newVersion: " + newVersion);
    }

    @Override
    public int hashCode()
    {
        return 37;
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof MyMigration);
    }
}
