package com.hqumath.androidmvvm.data.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: UserInfoDao
 * 作    者: Created by gyd
 * 创建时间: 2019/7/4 11:35
 * 文件描述: 用户信息表操作
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
@Dao
public interface RenterInfoDao {
    @Query("select * from renter_info")
    LiveData<List<RenterInfoEntity>> loadAll();

    @Query("select * from renter_info")
    DataSource.Factory<Integer, RenterInfoEntity> loadAll1();

//    @Query("SELECT * FROM renter_info ORDER BY indexInResponse ASC")
//    DataSource.Factory<Integer, UserInfoEntity> loadAll2();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RenterInfoEntity> entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(RenterInfoEntity entity);



//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateAll(List<UserInfoEntity> entity);
//
//    @Delete
//    void deleteAll(List<UserInfoEntity> entity);

    @Query("delete from renter_info")
    void deleteAll();


    @Query("SELECT MAX(indexInResponse) + 1 FROM renter_info")
    int getNextIndex();

    //根据id删除
    @Query("DELETE  FROM renter_info where id=:id")
    void deleteById(int id);

    //根据名字查询是否存在
    @Query("select * FROM renter_info where name=:name")
    List<RenterInfoEntity> findByName(String name);
    //update
    @Query("select * FROM renter_info where id=:id")
    List<RenterInfoEntity> findById(int id);
    @Update
    void update(RenterInfoEntity renterInfoEntity);
}
