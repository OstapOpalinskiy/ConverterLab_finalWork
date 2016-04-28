package com.opalinskiy.ostap.converterlab;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.opalinskiy.ostap.converterlab.constants.Constants;

import java.io.File;


public class ShareFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("TAG", "on create dialog" );
        Dialog dialog = new Dialog(getActivity());
        Bitmap bitmap = (Bitmap) getArguments().get(Constants.BITMAP_KEY);
        final String filePath = getArguments().getString(Constants.FILE_PATH_KEY);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = layoutInflater.inflate(R.layout.share_fragment, null);
        ImageView imageView = (ImageView) layout.findViewById(R.id.iv_bitmap_SF);
        Button shareButton = (Button) layout.findViewById(R.id.btn_share_SF);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpg");
                final File photoFile = new File(filePath);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                startActivity(Intent.createChooser(shareIntent, "Share image using:"));
            }
        });

        imageView.setImageBitmap(bitmap);
        dialog.setContentView(layout);
        return dialog;
    }

    //
    public static ShareFragment newInstance(Bitmap bitmapArg, String filePathArg) {
        ShareFragment dialog = new ShareFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.BITMAP_KEY, (Parcelable) bitmapArg);
        args.putString(Constants.FILE_PATH_KEY, filePathArg);
        dialog.setArguments(args);
        return dialog;
    }
}
