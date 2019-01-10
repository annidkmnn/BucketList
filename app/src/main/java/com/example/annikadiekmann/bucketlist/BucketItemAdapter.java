package com.example.annikadiekmann.bucketlist;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class BucketItemAdapter extends RecyclerView.Adapter<BucketItemAdapter.ViewHolder> {

    private List<BucketItem> mBucketItems;
    final private BucketItemClickListener mBucketItemClickListener;

    public interface BucketItemClickListener{
        void onBucketListCheckBoxChange(int position, boolean mDone);
    }

    public BucketItemAdapter(List<BucketItem> mBucketItems, BucketItemClickListener mBucketItemClickListener) {
        this.mBucketItems = mBucketItems;
        this.mBucketItemClickListener = mBucketItemClickListener;
    }

    @NonNull
    @Override
    public BucketItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bucket_item_layout, parent, false);
        // Return a new holder instance
        BucketItemAdapter.ViewHolder viewHolder = new BucketItemAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BucketItemAdapter.ViewHolder holder, int position) {
        BucketItem bucketItem =  mBucketItems.get(position);
        holder.title.setText(bucketItem.getTitle());
        holder.description.setText(bucketItem.getDescription());
        holder.done.setChecked(bucketItem.isChecked());
        crossOutTextIfDone(holder.title, holder.description, holder.done);
    }

    @Override
    public int getItemCount() {
        return mBucketItems.size();
    }


    public void crossOutTextIfDone(TextView title, TextView description, CheckBox done) {

        if (done.isChecked()) {
            title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            description.setPaintFlags(description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            title.setPaintFlags(title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            description.setPaintFlags(description.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title;
        public TextView description;
        public CheckBox done;

        public ViewHolder(View itemView) {

            super(itemView);
            title=  itemView.findViewById(R.id.text_title);
            description=  itemView.findViewById(R.id.text_description);
            done=  itemView.findViewById(R.id.checkbox_done);
            done.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();

            mBucketItemClickListener.onBucketListCheckBoxChange(
                    clickedPosition,
                    done.isChecked()
            );
            crossOutTextIfDone(title, description, done);
        }

    }

    public void swapList (List<BucketItem> newList) {

        mBucketItems = newList;

        if (newList != null) {

            // Force the RecyclerView to refresh

            this.notifyDataSetChanged();

        }

    }

}

