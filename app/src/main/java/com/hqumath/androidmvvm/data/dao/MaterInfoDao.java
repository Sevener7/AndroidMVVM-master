package com.hqumath.androidmvvm.data.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hqumath.androidmvvm.entity.MaterInfoEntity;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.ShowBatchRenterInfoEntity;
import com.hqumath.androidmvvm.entity.ShowCalculatorBeans;

import java.util.List;

/**
 * FileName: MaterInfoDao
 * Date: 2021/6/18 16:46
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
@Dao
public interface MaterInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(MaterInfoEntity entity);

    @Query("SELECT * from mater_info " +
            "WHERE mater_info.renter_id == :renterId ORDER BY date ASC")
    List<MaterInfoEntity>  loadById(int renterId);
    @Query("SELECT * from mater_info " +
            "WHERE mater_info.date == :date and renter_id == :renterId ORDER BY date ASC")
    List<MaterInfoEntity>  loadByDate(String date,int renterId);
    @Query("SELECT * from mater_info " +
            "WHERE renter_id == :renterId ORDER BY date ASC")
    DataSource.Factory<Integer, MaterInfoEntity> getMaterInfoByRenterId(int renterId);

    @Update
    void update(MaterInfoEntity entity);
    //使用内连接查询
    @Query("SELECT use_mater,mark from mater_info LEFT JOIN renter_info ON mater_info.renter_id=renter_info.id " +
            "WHERE mater_info.date == :date ORDER BY date ASC")
    List<ShowCalculatorBeans> getShowCalculatorByDate(String date);
    //使用内连接查询
    @Query("SELECT use_mater,mark from mater_info LEFT JOIN renter_info ON mater_info.renter_id=renter_info.id " +
            "WHERE mater_info.date == :date ORDER BY date ASC")
    DataSource.Factory<Integer,ShowCalculatorBeans> getShowAllCalculatorByDate(String date);

    //使用内连接查询
    @Query("SELECT renter_id,mater,date,name,rent_room,rent_water from mater_info LEFT JOIN renter_info ON mater_info.renter_id=renter_info.id " +
            "WHERE mater_info.date == :date ORDER BY 2")
    DataSource.Factory<Integer, ShowBatchRenterInfoEntity> getShowBatchRenterInfoEntity(String date);
}
