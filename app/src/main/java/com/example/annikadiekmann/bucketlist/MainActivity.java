package com.example.annikadiekmann.bucketlist;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BucketItemAdapter.BucketItemClickListener {

    //Local variables
    private List<BucketItem> mBucketItems;
    private BucketItemAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MainViewModel mMainViewModel;

    public static final String EXTRA_REMINDER = "BucketItem";
    public static final int REQUESTCODE_ADDING = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize the local variables
        mBucketItems = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerView);
        updateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mMainViewModel = new MainViewModel(getApplicationContext());
        mMainViewModel.getBucketItems().observe(this, new Observer<List<BucketItem>>() {
            @Override
            public void onChanged(@Nullable List<BucketItem> bucketItems) {
                mBucketItems = bucketItems;
                updateUI();
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                        target) {
                    return false;
                }

                //Called when a user swipes left or right on a ViewHolder
                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                    //Get the index corresponding to the selected position
                    int position = (viewHolder.getAdapterPosition());
                    mMainViewModel.delete(mBucketItems.get(position));

                }

            };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void goToAddPage(View view) {
        Intent intent;
        intent = new Intent(MainActivity.this, AddBucketItem.class);
        startActivityForResult(intent, REQUESTCODE_ADDING);
    }

    public void onBucketListCheckBoxChange(int position, boolean mDone) {
        BucketItem bucketItem = mBucketItems.get(position);
        bucketItem.setDone(mDone);
        mBucketItems.set(position, bucketItem);
        mMainViewModel.update(mBucketItems.get(position));
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new BucketItemAdapter(mBucketItems, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mBucketItems);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCODE_ADDING) {
            if (resultCode == RESULT_OK) {
                BucketItem addedBucketItem = data.getParcelableExtra(MainActivity.EXTRA_REMINDER);
                // New timestamp: timestamp of update
                mMainViewModel.insert(addedBucketItem);
                updateUI();
            }
        }
    }

}





