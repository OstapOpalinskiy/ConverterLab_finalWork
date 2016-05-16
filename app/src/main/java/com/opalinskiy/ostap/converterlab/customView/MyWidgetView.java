package com.opalinskiy.ostap.converterlab.customView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.opalinskiy.ostap.converterlab.DetailActivity;
import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.model.Currency;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.util.List;


public class MyWidgetView extends View {

    private Paint paint;
    private Organisation organisation;
    private List<Currency> currencies;
    private Context context;
    private int subTextColor;
    private int displayWidth;
    private int displayHeight;
    private int titleHeight;
    private int itemHeight;
    private int largeTextSize;
    private int medTextSize;
    private Resources r;

    public MyWidgetView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MyWidgetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
            subTextColor = context.getResources().getColor(R.color.textColorSecondary);
            paint = new Paint();
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((DetailActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            displayHeight = displaymetrics.heightPixels;
            displayWidth = displaymetrics.widthPixels;
            r = context.getResources();
            titleHeight = r.getDimensionPixelSize(R.dimen.titleHeight);
            itemHeight = r.getDimensionPixelSize(R.dimen.itemHeight);
            largeTextSize = r.getDimensionPixelSize(R.dimen.textLarge);
            medTextSize = r.getDimensionPixelSize(R.dimen.textMed);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("TAG", "onMeasure()");
        if(currencies != null){
            int viewWidth = (int) (displayWidth * 0.8);
            int viewHeight = titleHeight + (itemHeight * currencies.size());
            setMeasuredDimension(viewWidth, viewHeight);
        } else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("TAG", "onDraw()");
            int offset = titleHeight;
            drawTitle(canvas, organisation);
            for (int i = 0; i < currencies.size(); i++) {
                String askBid = currencies.get(i).getAsk().substring(0, 5) + "/" +
                        currencies.get(i).getBid().substring(0, 5);
                if (i == 0) {
                    drawCurrency(canvas, titleHeight, currencies.get(i).getIdCurrency(), askBid);
                } else {
                    offset += itemHeight;
                    drawCurrency(canvas, offset, currencies.get(i).getIdCurrency(), askBid);
                }
            }
        }

    private void drawTitle(Canvas canvas, Organisation organisation) {

        int padding = itemHeight / 2;

        paint.setColor(Color.BLACK);
        paint.setTextSize(largeTextSize);

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(organisation.getTitle(), padding, padding, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        paint.setTextSize(medTextSize);
        paint.setColor(subTextColor);
        canvas.drawText(organisation.getRegion(), padding, padding + padding, paint);
        canvas.drawText(organisation.getCity(), padding, padding + 2 * padding, paint);
    }


    private void drawCurrency(Canvas canvas, int offSet, String currency, String rate) {
        int currencyIdMargin = r.getDimensionPixelOffset(R.dimen.currencyIdMargin);
        int askBidMargin = (int) (this.getWidth() * 0.60);

        paint.setTextSize(largeTextSize);
        int currencyColor = context.getResources().getColor(R.color.currencyColor);
        paint.setColor(currencyColor);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(currency, currencyIdMargin, offSet, paint);

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setColor(subTextColor);
        canvas.drawText(rate, askBidMargin, offSet, paint);
    }
    public void passOrganisation(Organisation organisation){
        this.organisation = organisation;
        currencies = organisation.getCurrencies().getCurrencyList();
        requestLayout();
        invalidate();
    }
}
