package com.example.annikadiekmann.bucketlist;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddBucketItem extends AppCompatActivity {

    EditText mTitle;
    EditText mDescription;

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bucket_item);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitle = findViewById(R.id.add_title);
        mDescription = findViewById(R.id.add_description);

        //Initialize the local variables
        db = AppDatabase.getInstance(this);

        FloatingActionButton fab = findViewById(R.id.add_save_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int errorStep = 0;
                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();

                BucketItem bucketItem = new BucketItem(title, description, false);

                /* Checking */
                if (title.trim().length() < 1) {
                    errorStep++;
                    mTitle.setError("Provide a task name.");
                }

                if (description.trim().length() < 1) {
                    errorStep++;
                    mDescription.setError("Provide a time");
                }


                if (errorStep == 0) {
                    Intent resultIntent = new Intent(AddBucketItem.this, MainActivity.class);
                    resultIntent.putExtra(MainActivity.EXTRA_REMINDER, bucketItem);
                    setResult(Activity.RESULT_OK, resultIntent);
                    Toast.makeText(getApplicationContext(), "Task Added.", Toast.LENGTH_SHORT).show();

                    finish();
                }

            }
        });

    }
}
