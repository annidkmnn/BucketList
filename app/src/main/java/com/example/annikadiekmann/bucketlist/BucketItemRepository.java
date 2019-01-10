package com.example.annikadiekmann.bucketlist;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BucketItemRepository {
    private AppDatabase mAppDatabase;
    private BucketListDao mBucketListDao;
    private LiveData<List<BucketItem>> mBucketItems;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public BucketItemRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
        mBucketListDao = mAppDatabase.bucketListDao();
        mBucketItems = mBucketListDao.getAllBucketItems();
    }
    public LiveData<List<BucketItem>> getAllBucketItems() {
        return mBucketItems;
    }

    public void insert(final BucketItem bucketItem) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketListDao.insertBucketItems(bucketItem);
            }
        });
    }
    public void update(final BucketItem bucketItem) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketListDao.updateBucketItems(bucketItem);
            }
        });
    }
    public void delete(final BucketItem bucketItem) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketListDao.deleteBucketItems(bucketItem);
            }
        });
    }
}
