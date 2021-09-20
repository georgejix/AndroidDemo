package com.jx.arch;

import com.jx.arch.util.DeviceUtils;
import com.jx.arch.util.GeneralUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DbConfiguration
{
    /**
     * 切换数据库
     *
     * @param adminId admin为""时，切换默认数据库
     */
    public static void setDbConfiguration(String adminId)
    {
        String defaultName = GeneralUtils.isNotNullOrZeroLength(adminId) ? adminId : "qm";
        String fileName = DeviceUtils.getDeviceSN() + "-" + defaultName + ".realm";
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(fileName) //文件名
                .schemaVersion(1) //版本号
//                .migration(new MyMigration())
                .deleteRealmIfMigrationNeeded()
                .build();
//        Realm.getInstance(realmConfig);
        Realm.setDefaultConfiguration(realmConfig);
    }
}
