package com.hqumath.androidmvvm.data;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.hqumath.androidmvvm.entity.MaterInfoEntity;
import com.hqumath.androidmvvm.entity.RenterInfoEntity;
import com.hqumath.androidmvvm.entity.ShowBatchRenterInfoEntity;
import com.hqumath.androidmvvm.entity.ShowCalculatorBeans;
import com.hqumath.androidmvvm.entity.TotalMaterEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.utils.SPUtil;
import com.hqumath.androidmvvm.utils.CommonUtil;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ****************************************************************
 * 文件名称: MyRepository
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:29
 * 文件描述: MVVM的Model层，统一模块的数据仓库，可配合Room框架使用
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyRepository {
    private static volatile MyRepository INSTANCE = null;//禁止重排序
    private AppDatabase mDatabase;

    private MyRepository() {
        mDatabase = AppDatabase.getInstance(CommonUtil.getContext());
    }

    public static MyRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (MyRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyRepository();
                }
            }
        }
        return INSTANCE;
    }

    public void runInTransaction(Runnable runnable){
        mDatabase.runInTransaction(runnable);
    }

    public void saveUserName(String userName) {
        SPUtil.getInstance().put("UserName", userName);
    }

    public String getUserName() {
        return SPUtil.getInstance().getString("UserName");
    }

    public void savePassword(String password) {
        SPUtil.getInstance().put("password", password);
    }

    public String getPassword() {
        return SPUtil.getInstance().getString("password");
    }

    public void saveToken(String token) {
        SPUtil.getInstance().put("token", token);
    }

    public String getToken() {
        return SPUtil.getInstance().getString("token");
    }

    //用户信息表操作
    public LiveData<List<UserInfoEntity>> loadAllUsers() {
        return mDatabase.userInfoDao().loadAll();
    }

    public List<UserInfoEntity> loadAllUsers1() {
        return mDatabase.userInfoDao().loadAll1();
    }

    public DataSource.Factory<Integer, UserInfoEntity> loadAllUsers2() {
        return mDatabase.userInfoDao().loadAll2();
    }

    public void insertAllUsers(List<UserInfoEntity> list) {
        mDatabase.userInfoDao().insertAll(list);
    }

    public void deleteAllUsers() {
        mDatabase.userInfoDao().deleteAll();
    }

    public int getNextIndexInUsers(){
        return mDatabase.userInfoDao().getNextIndex();
    }

    public void insertRenter(RenterInfoEntity renterInfoEntity){
        mDatabase.renterInfoDao().insertOne(renterInfoEntity);
    }
    public void insertAllRenter(List<RenterInfoEntity> list){
        mDatabase.renterInfoDao().insertAll(list);
    }
    public List<RenterInfoEntity> findRenterByName(String name){
        return mDatabase.renterInfoDao().findByName(name);
    }
    public List<RenterInfoEntity> findRenterById(int id){
        return mDatabase.renterInfoDao().findById(id);
    }
    public void updateRenter(RenterInfoEntity renterInfoEntity){
        mDatabase.renterInfoDao().update(renterInfoEntity);
    }
    public DataSource.Factory<Integer, RenterInfoEntity> loadAllRenters() {
        return mDatabase.renterInfoDao().loadAll1();
    }
    public int getNextIndexInRenters(){
        return mDatabase.renterInfoDao().getNextIndex();
    }

    public void deleteAllRenters(){
        mDatabase.renterInfoDao().deleteAll();
    }

    public LiveData<List<RenterInfoEntity>> loadAllRenters2(){
        return mDatabase.renterInfoDao().loadAll();
    }

    public void deleteRenterById(int id){
        mDatabase.renterInfoDao().deleteById(id);
    }

    public void insertMater(MaterInfoEntity materInfoEntity){
        //查询是否存在，如果存在，更新
        List<MaterInfoEntity> dataList = mDatabase.materInfoDao().loadByDate(materInfoEntity.getDate(),materInfoEntity.getRenter_id());
        if(dataList.size()>0){
            //设置ID,如果ID存在那么更新，否则插入
            materInfoEntity.setMater_id(dataList.get(0).getMater_id());
        }
        mDatabase.materInfoDao().insertOne(materInfoEntity);
    }
    public DataSource.Factory<Integer, MaterInfoEntity> loadMatersByRenterId(int renterId) {
        return mDatabase.materInfoDao().getMaterInfoByRenterId(renterId);
//        return mDatabase.materInfoDao().loadAll();
    }

    public List<MaterInfoEntity> getAllMatersById(int id) {
        return  mDatabase.materInfoDao().loadById(id);
    }
    public void updateMater(MaterInfoEntity materInfoEntity){
        mDatabase.materInfoDao().update(materInfoEntity);
    }

    /**
     * TotalMater
     * */
    public void insertTotalMater(TotalMaterEntity totalMaterEntity){
        mDatabase.totalMaterDao().insertOne(totalMaterEntity);
    }
    public DataSource.Factory<Integer, TotalMaterEntity> loadAllTotalMaters() {
        return mDatabase.totalMaterDao().getAllTotalMaters();
    }
    public List<TotalMaterEntity> getAllTotalMaters(){
        return mDatabase.totalMaterDao().getAll();
    }
    public void updateTotalMater(TotalMaterEntity materInfoEntity){
        mDatabase.totalMaterDao().update(materInfoEntity);
    }
    public List<TotalMaterEntity> findTotalMaterByDate(String date){
        return mDatabase.totalMaterDao().findByDate(date);
    }
    public List<ShowCalculatorBeans> getShowCalculatorBeanListByDate(String date){
        return mDatabase.materInfoDao().getShowCalculatorByDate(date);
    }
    public DataSource.Factory<Integer,ShowCalculatorBeans> getShowAllCalculatorBeanListByDate(String date){
        return mDatabase.materInfoDao().getShowAllCalculatorByDate(date);
    }
    public DataSource.Factory<Integer, ShowBatchRenterInfoEntity> getShowAllBatchRenterInfoEntityList(String date){
        return mDatabase.materInfoDao().getShowBatchRenterInfoEntity(date);
    }
    public void deleteTotalRenterByDate(String date){
        mDatabase.totalMaterDao().deleteByDate(date);
    }
}
