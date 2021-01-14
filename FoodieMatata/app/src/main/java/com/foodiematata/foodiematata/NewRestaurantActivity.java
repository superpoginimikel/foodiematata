package com.foodiematata.foodiematata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NewRestaurantActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static final int CROP_IMAGE_RESULT = 2;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    public static final String EXTRA_REPLY_NAME = "com.foodiematata.foodiematata.REPLY_NAME";
    public static final String EXTRA_REPLY_DESCRIPTION = "com.foodiematata.foodiematata.REPLY_DESCRIPTION";
    public static final String EXTRA_REPLY_LOCATION = "com.foodiematata.foodiematata.REPLY_LOCATION";
    public static final String EXTRA_REPLY_PHONE = "com.foodiematata.foodiematata.REPLY_PHONE";
    public static final String EXTRA_REPLY_PRICE = "com.foodiematata.foodiematata.REPLY_PRICE";
    public static final String EXTRA_REPLY_IMAGE = "com.foodiematata.foodiematata.REPLY_IMAGE";

    private ImageView mImageView;
    private EditText mEditNameView;
    private EditText mEditDescriptionView;
    private EditText mEditLocationView;
    private EditText mEditPhoneView;
    private EditText mEditPriceView;
//    private Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restaurant);
        mImageView = findViewById(R.id.restaurantImageView);
        mEditNameView = findViewById(R.id.edit_restaurant_name);
        mEditDescriptionView = findViewById(R.id.edit_restaurant_description);
        mEditLocationView = findViewById(R.id.edit_restaurant_location);
        mEditPhoneView = findViewById(R.id.edit_restaurant_phone);
        mEditPriceView = findViewById(R.id.edit_restaurant_price);
//        button_save = findViewById(R.id.button_save);
    }

    public void saveRestaurant(View view) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditNameView.getText()) || TextUtils.isEmpty(mEditDescriptionView.getText())
            || TextUtils.isEmpty(mEditLocationView.getText()) || TextUtils.isEmpty(mEditPhoneView.getText()))
        {
            setResult(RESULT_CANCELED, replyIntent);
        }
        else
        {
            Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageInByte = baos.toByteArray();

            String name = mEditNameView.getText().toString();
            String description = mEditDescriptionView.getText().toString();
            String location = mEditLocationView.getText().toString();
            String phone = mEditPhoneView.getText().toString();
            String price = mEditPriceView.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY_NAME, name);
            replyIntent.putExtra(EXTRA_REPLY_DESCRIPTION, description);
            replyIntent.putExtra(EXTRA_REPLY_LOCATION, location);
            replyIntent.putExtra(EXTRA_REPLY_PHONE, phone);
            replyIntent.putExtra(EXTRA_REPLY_PRICE, price);
            replyIntent.putExtra(EXTRA_REPLY_IMAGE, imageInByte);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

    public void AddRestaurantImage(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { pickIntent });

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

        } else if (requestCode == CROP_IMAGE_RESULT && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
}
