package com.foodiematata.foodiematata;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class HelperClass {
    public static void setImage(ImageView imageView, String imagePath)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (bitmap != null)
        {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            // Show a placeholder image to add
        }
    }

    public static File saveImageFile(Context context) {
        final File path = new File(context.getFilesDir(), context.getPackageName());
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

    public static Bitmap decodeUri(Context context, Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o);

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
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o2);
    }
}
