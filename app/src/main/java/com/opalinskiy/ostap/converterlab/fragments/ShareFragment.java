package com.opalinskiy.ostap.converterlab.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.customView.MyWidgetView;
import com.opalinskiy.ostap.converterlab.loaders.AsyncBitmapSaver;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.io.File;
import java.io.Serializable;


public class ShareFragment extends DialogFragment implements LoaderManager.LoaderCallbacks<String> {
    private Organisation organisation;
    private MyWidgetView view;
    private AsyncBitmapSaver bitmapSaver;
    private Bitmap bitmap;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        organisation = (Organisation) getArguments().get(Constants.LIST_KEY);

        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getLoaderManager().initLoader(Constants.BITMAP_SAVER_ID, null, this);

        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = layoutInflater.inflate(R.layout.share_fragment, null);
        Button shareButton = (Button) layout.findViewById(R.id.btn_share_SF);

        // adds view to the layout
        view = new MyWidgetView(getActivity());
        view.passOrganisation(organisation);
        LinearLayout frame = (LinearLayout) layout.findViewById(R.id.ll_frame_SF);
        frame.addView(view);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = getBitmapFromView(view);
                bitmapSaver.forceLoad();
            }
        });

        dialog.setContentView(layout);
        setDialogWindowSize(dialog);
        return dialog;
    }

    private void setDialogWindowSize(Dialog dialog) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int displayHeight = displaymetrics.heightPixels;
        int displayWidth = displaymetrics.widthPixels;

        int desiredHeight = displayHeight / 2;
        int desiredWidth = (int) (displayWidth * 0.9);

        Window window = dialog.getWindow();
        window.setLayout(desiredWidth, desiredHeight);
    }

    public static ShareFragment newInstance(Organisation organisation) {
        ShareFragment dialog = new ShareFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.LIST_KEY, (Serializable) organisation);
        dialog.setArguments(args);
        return dialog;
    }

    public Bitmap getBitmapFromView(View view) {
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        bitmapSaver = new AsyncBitmapSaver(getActivity(), organisation, bitmap);
        return bitmapSaver;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        //share image
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        Log.d("TAG", "path: " + data);
        final File photoFile = new File(data);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
        startActivity(Intent.createChooser(shareIntent, "Share image using:"));
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
