package com.example.annikadiekmann.bucketlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

public class MainViewModel extends ViewModel {

    private BucketItemRepository mRepository;
    private LiveData<List<BucketItem>> mBucketItems;

    public MainViewModel(Context context) {
        mRepository = new BucketItemRepository(context);
        mBucketItems = mRepository.getAllBucketItems();
    }
    public LiveData<List<BucketItem>> getBucketItems() {
        return mBucketItems;
    }
    public void insert(BucketItem bucketItem) {
        mRepository.insert(bucketItem);
    }
    public void update(BucketItem bucketItem) {
        mRepository.update(bucketItem);
    }
    public void delete(BucketItem bucketItem) {
        mRepository.delete(bucketItem);
    }
}