package com.hqumath.androidmvvm.entity;

/**
 * FileName: ShowCalculatorBeans
 * Date: 2021/6/24 15:54
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
public class ShowCalculatorBeans {
    private String mark;
    private double use_mater;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public double getUse_mater() {
        return use_mater;
    }

    public void setUse_mater(double use_mater) {
        this.use_mater = use_mater;
    }

    @Override
    public String toString() {
        return "ShowCalculatorBeans{" +
                "mark='" + mark + '\'' +
                ", use_mater=" + use_mater +
                '}';
    }
}
