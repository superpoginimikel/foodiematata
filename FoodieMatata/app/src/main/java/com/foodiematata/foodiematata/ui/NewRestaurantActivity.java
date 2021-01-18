package com.foodiematata.foodiematata.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodiematata.foodiematata.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class NewRestaurantActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;

    public static final String EXTRA_REPLY_NAME = "com.foodiematata.foodiematata.REPLY_NAME";
    public static final String EXTRA_REPLY_LOCATION = "com.foodiematata.foodiematata.REPLY_LOCATION";
    public static final String EXTRA_REPLY_CATEGORY = "com.foodiematata.foodiematata.REPLY_CATEGORY";
    public static final String EXTRA_REPLY_PHONE = "com.foodiematata.foodiematata.REPLY_PHONE";
    public static final String EXTRA_REPLY_PRICE = "com.foodiematata.foodiematata.REPLY_PRICE";
    public static final String EXTRA_REPLY_IMAGE = "com.foodiematata.foodiematata.REPLY_IMAGE";

    private ImageView mImageView;
    private EditText mEditNameView;
    private EditText mEditCategoryView;
    private EditText mEditLocationView;
    private EditText mEditPhoneView;
    private EditText mEditPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restaurant);
        mImageView = findViewById(R.id.restaurantImageView);
        mEditNameView = findViewById(R.id.edit_restaurant_name);
        mEditCategoryView = findViewById(R.id.edit_restaurant_category);
        mEditLocationView = findViewById(R.id.edit_restaurant_location);
        mEditPhoneView = findViewById(R.id.edit_restaurant_phone);
        mEditPriceView = findViewById(R.id.edit_restaurant_price);
    }

    public void saveRestaurant(View view) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditNameView.getText()) || TextUtils.isEmpty(mEditCategoryView.getText())
            || TextUtils.isEmpty(mEditLocationView.getText()) || TextUtils.isEmpty(mEditPhoneView.getText()))
        {
            setResult(RESULT_CANCELED, replyIntent);
        }
        else
        {
            Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            File imageFile = saveImageFile(this);
            try (FileOutputStream out = new FileOutputStream(imageFile)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String name = mEditNameView.getText().toString();
            String category = mEditCategoryView.getText().toString();
            String location = mEditLocationView.getText().toString();
            String phone = mEditPhoneView.getText().toString();
            String price = mEditPriceView.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY_NAME, name);
            replyIntent.putExtra(EXTRA_REPLY_CATEGORY, category);
            replyIntent.putExtra(EXTRA_REPLY_LOCATION, location);
            replyIntent.putExtra(EXTRA_REPLY_PHONE, phone);
            replyIntent.putExtra(EXTRA_REPLY_PRICE, price);
            replyIntent.putExtra(EXTRA_REPLY_IMAGE, imageFile.getAbsolutePath());
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
            try {
                Uri selectedImage = data.getData();
                Bitmap bitmap = decodeUri(selectedImage);
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File saveImageFile(Context context) {
        final File path = new File(getFilesDir(), context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
        }

        String randomId = UUID.randomUUID().toString().substring(0,6);
        File file = new File(path + "/" + randomId);
        if (!file.exists()) {
            try {
                new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 100;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
    }
}
