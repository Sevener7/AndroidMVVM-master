package com.hqumath.androidmvvm.data.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.hqumath.androidmvvm.entity.CommitEntity;

/**
 * ****************************************************************
 * 文件名称: UserInfoFactory
 * 作    者: Created by gyd
 * 创建时间: 2019/7/31 15:32
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class CommitFactory extends DataSource.Factory<Long, CommitEntity> {

    private MutableLiveData<CommitSource> sourceLiveData = new MutableLiveData<>();
    private int pageSize;//分页大小
    private String userName, reposName;

    public CommitFactory(int pageSize, String userName, String reposName) {
        this.pageSize = pageSize;
        this.userName = userName;
        this.reposName = reposName;
    }

    @NonNull
    @Override
    public DataSource<Long, CommitEntity> create() {
        CommitSource source = new CommitSource(pageSize, userName, reposName);
        sourceLiveData.postValue(source);
        return source;
    }

    public MutableLiveData<CommitSource> getSourceLiveData() {
        return sourceLiveData;
    }
}
