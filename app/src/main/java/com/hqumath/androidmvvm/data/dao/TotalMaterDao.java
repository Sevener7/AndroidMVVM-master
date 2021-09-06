package com.hqumath.androidmvvm.data.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hqumath.androidmvvm.entity.MaterInfoEntity;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.TotalMaterEntity;

import java.util.List;

/**
 * FileName: MaterInfoDao
 * Date: 2021/6/18 16:46
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
@Dao
public interface TotalMaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(TotalMaterEntity entity);

    @Query("SELECT * from total_mater " +
            "WHERE total_mater.date == :date ORDER BY date ASC")
    List<TotalMaterEntity> loadById(String date);

    @Query("SELECT * from total_mater " +
            " ORDER BY date ASC")
    DataSource.Factory<Integer, TotalMaterEntity> getAllTotalMaters();

    @Query("SELECT * from total_mater " +
            "ORDER BY date ASC")
    List<TotalMaterEntity> getAll();

    @Query("SELECT * from total_mater " +
            "ORDER BY date ASC")
    DataSource.Factory<Integer, TotalMaterEntity> getMaterInfoByRenterDate();

    @Update
    void update(TotalMaterEntity entity);

    //根据名字查询是否存在
    @Query("select * FROM total_mater where date=:date")
    List<TotalMaterEntity> findByDate(String date);

    //根据日期删除
    @Query("DELETE  FROM total_mater where date=:date")
    void deleteByDate(String date);
}
