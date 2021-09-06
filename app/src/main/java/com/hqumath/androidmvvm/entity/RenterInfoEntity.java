package com.hqumath.androidmvvm.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * FileName: RenterInfo
 * Date: 2021/6/15 14:28
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
@Entity(tableName = "renter_info")
public class RenterInfoEntity{
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String mark;

    private Integer rent_room;

    private Integer rent_water;

    private int indexInResponse = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getRent_room() {
        return rent_room;
    }

    public void setRent_room(Integer rent_room) {
        this.rent_room = rent_room;
    }

    public Integer getRent_water() {
        return rent_water;
    }

    public void setRent_water(Integer rent_water) {
        this.rent_water = rent_water;
    }

    public int getIndexInResponse() {
        return indexInResponse;
    }

    public void setIndexInResponse(int indexInResponse) {
        this.indexInResponse = indexInResponse;
    }

    @Override
    public String toString() {
        return "RenterInfoEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mark='" + mark + '\'' +
                ", rent_room=" + rent_room +
                ", rent_water=" + rent_water +
                ", indexInResponse=" + indexInResponse +
                '}';
    }
}
