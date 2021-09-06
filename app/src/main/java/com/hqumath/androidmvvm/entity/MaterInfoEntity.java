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
@Entity(tableName = "mater_info", foreignKeys = {
        @ForeignKey(
                entity = RenterInfoEntity.class,
                parentColumns = "id",
                childColumns = "renter_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )/*,
        @ForeignKey(
                entity = ComponentCat.class,
                parentColumns = "component_cat_id",
                childColumns = "component_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )*/
})
public class MaterInfoEntity {

    @PrimaryKey(autoGenerate = true)
    private int mater_id;

    private Integer mater;
    /**
     * 格式 20210618
     * */
    private String date;
    @ColumnInfo(name = "renter_id")
    private int renter_id;

    private double total_rent;

    private double use_mater;

    public double getTotal_spend() {
        return total_spend;
    }

    public void setTotal_spend(double total_spend) {
        this.total_spend = total_spend;
    }

    private double total_spend;


    public int getMater_id() {
        return mater_id;
    }

    public void setMater_id(int mater_id) {
        this.mater_id = mater_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMater() {
        return mater;
    }

    public void setMater(Integer mater) {
        this.mater = mater;
    }

    public int getRenter_id() {
        return renter_id;
    }

    public void setRenter_id(int renter_id) {
        this.renter_id = renter_id;
    }

    public double getTotal_rent() {
        return total_rent;
    }

    public void setTotal_rent(double total_rent) {
        this.total_rent = total_rent;
    }

    public double getUse_mater() {
        return use_mater;
    }

    public void setUse_mater(double use_mater) {
        this.use_mater = use_mater;
    }

    @Override
    public String toString() {
        return "MaterInfoEntity{" +
                "mater_id=" + mater_id +
                ", mater=" + mater +
                ", use_mater=" + use_mater +
                ", date='" + date + '\'' +
                ", renter_id=" + renter_id +
                ", total_rent=" + total_rent +
                ", total_spend=" + total_spend +
                '}';
    }
}

