package com.hqumath.androidmvvm.entity;

/**
 * FileName: ShowBatchRenterInfoEntity
 * Date: 2022/4/6 15:22
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/

public class ShowBatchRenterInfoEntity {
    private Integer renter_id;
    private int mater;
    private String name;
    private Integer rent_room;
    private Integer rent_water;


    public Integer getRenter_id() {
        return renter_id;
    }

    public void setRenter_id(Integer renter_id) {
        this.renter_id = renter_id;
    }

    public int getMater() {
        return mater;
    }

    public void setMater(int mater) {
        this.mater = mater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "ShowBatchRenterInfoEntity{" +
                "renter_id=" + renter_id +
                ", mater=" + mater +
                ", name='" + name + '\'' +
                ", rent_room=" + rent_room +
                ", rent_water=" + rent_water +
                '}';
    }

}
