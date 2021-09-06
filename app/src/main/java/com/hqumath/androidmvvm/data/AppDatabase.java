package com.hqumath.androidmvvm.data;

import android.content.Context;
import android.hardware.camera2.TotalCaptureResult;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hqumath.androidmvvm.data.dao.MaterInfoDao;
import com.hqumath.androidmvvm.data.dao.RenterInfoDao;
import com.hqumath.androidmvvm.data.dao.TotalMaterDao;
import com.hqumath.androidmvvm.data.dao.UserInfoDao;
import com.hqumath.androidmvvm.entity.MaterInfoEntity;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.TotalMaterEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;

/**
 * ****************************************************************
 * 文件名称: AppDatabase
 * 作    者: Created by gyd
 * 创建时间: 2019/7/4 11:35
 * 文件描述: 数据库
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
@Database(entities = {UserInfoEntity.class,RenterInfoEntity.class, MaterInfoEntity.class, TotalMaterEntity.class},version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    public abstract UserInfoDao userInfoDao();
    public abstract RenterInfoDao renterInfoDao();
    public abstract MaterInfoDao materInfoDao();
    public abstract TotalMaterDao totalMaterDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            //对该对象加锁，线程安全
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, "basic.db")
                            .fallbackToDestructiveMigration()//升级时丢弃原来表
                            .allowMainThreadQueries() //在主线程中允许对数据库的操作
                            .build();
                }
            }
        }
        return sInstance;
    }
}
