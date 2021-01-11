package com.foodiematata.foodiematata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewRestaurantActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_NAME = "com.foodiematata.foodiematata.REPLY_NAME";
    public static final String EXTRA_REPLY_DESCRIPTION = "com.foodiematata.foodiematata.REPLY_DESCRIPTION";
    public static final String EXTRA_REPLY_LOCATION = "com.foodiematata.foodiematata.REPLY_LOCATION";
    public static final String EXTRA_REPLY_PHONE = "com.foodiematata.foodiematata.REPLY_PHONE";
    private EditText mEditNameView;
    private EditText mEditDescriptionView;
    private EditText mEditLocationView;
    private EditText mEditPhoneView;
    private Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restaurant);
        mEditNameView = findViewById(R.id.edit_restaurant_name);
        mEditDescriptionView = findViewById(R.id.edit_restaurant_description);
        mEditLocationView = findViewById(R.id.edit_restaurant_location);
        mEditPhoneView = findViewById(R.id.edit_restaurant_phone);
        button_save = findViewById(R.id.button_save);
    }

    public void saveWord(View view) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditNameView.getText()) || TextUtils.isEmpty(mEditDescriptionView.getText())
            || TextUtils.isEmpty(mEditLocationView.getText()) || TextUtils.isEmpty(mEditPhoneView.getText()))
        {
            setResult(RESULT_CANCELED, replyIntent);
        }
        else
        {
            String name = mEditNameView.getText().toString();
            String description = mEditDescriptionView.getText().toString();
            String location = mEditLocationView.getText().toString();
            String phone = mEditPhoneView.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY_NAME, name);
            replyIntent.putExtra(EXTRA_REPLY_DESCRIPTION, description);
            replyIntent.putExtra(EXTRA_REPLY_LOCATION, location);
            replyIntent.putExtra(EXTRA_REPLY_PHONE, phone);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
