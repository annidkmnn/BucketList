package com.example.annikadiekmann.bucketlist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BucketListDao {

    @Query("SELECT * FROM bucketList")

    public LiveData<List<BucketItem>> getAllBucketItems();

    @Insert
    public void insertBucketItems(BucketItem bucketItem);

    @Delete
    public void deleteBucketItems(BucketItem bucketItem);

    @Update
    public void updateBucketItems(BucketItem bucketItem);


}
