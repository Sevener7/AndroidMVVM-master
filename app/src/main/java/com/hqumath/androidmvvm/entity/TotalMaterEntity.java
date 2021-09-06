package com.hqumath.androidmvvm.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * FileName: RenterInfo
 * Date: 2021/6/15 14:28
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
@Entity(tableName = "total_mater")
public class TotalMaterEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Integer mater;
    /**
     * 总共
     * */
    private double value;

    /**
     * 公摊
     * */
    private double share_value;
    /**
     * 格式 20210618
     * */
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getMater() {
        return mater;
    }

    public void setMater(Integer mater) {
        this.mater = mater;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getShare_value() {
        return share_value;
    }

    public void setShare_value(double share_value) {
        this.share_value = share_value;
    }
}

