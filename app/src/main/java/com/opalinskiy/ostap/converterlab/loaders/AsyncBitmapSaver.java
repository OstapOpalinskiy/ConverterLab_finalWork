package com.opalinskiy.ostap.converterlab.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by Evronot on 16.05.2016.
 */
public class AsyncBitmapSaver extends AsyncTaskLoader<String> {
    private Organisation organisation;
    private Bitmap bitmap;

    public AsyncBitmapSaver(Context context, Organisation organisation, Bitmap bitmap) {
        super(context);
        this.organisation = organisation;
        this.bitmap = bitmap;
    }

    @Override
    public String loadInBackground() {
        String root = Environment.getExternalStorageDirectory().toString();
        String rootPath = root + "/saved_images";
        File myDir = new File(rootPath);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + organisation.getTitle() + n + ".jpg";
        String fullPath = rootPath + fname;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullPath;
    }
}
