package com.opalinskiy.ostap.converterlab.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.model.Currency;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.util.List;


public class MyWidgetView extends View {
    final int MIN_WIDTH = 300; //961
    final int MIN_HEIGHT = 150;
    final int DEFAULT_COLOR = Color.RED;
    final int STROKE_WIDTH = 2;
    private int mColor;
    private Paint mPaint;
    private Organisation organisation;
    private List<Currency> currencies;
    private Context context;
    private int subTextColor;

    public MyWidgetView(Context context, Organisation organisation) {
        super(context);
        this.context = context;
        this.organisation = organisation;
        currencies = organisation.getCurrencies().getCurrencyList();
        Log.d("TAG1", "constructor in view");
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
        setMinimumWidth(MIN_WIDTH);
        setMinimumHeight(MIN_HEIGHT);
        mColor = DEFAULT_COLOR;
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //setMeasuredDimension(700, 700);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int offset = 200;
        int padding = 80;
        drawTitle(canvas, organisation);

        for(int i = 0; i < currencies.size(); i++){
            String askBid = currencies.get(i).getAsk().substring(0, 5) + "/" +
                    currencies.get(i).getBid().substring(0, 5);
            if(i == 0){
                drawCurrency(canvas,offset , currencies.get(i).getIdCurrency(), askBid);
            } else{
                offset += padding;
                drawCurrency(canvas, offset, currencies.get(i).getIdCurrency(), askBid);
            }

        }
    }

    private void drawTitle(Canvas canvas, Organisation organisation) {
        int offsetTop = 40;
        int offsetLeft = 40;
        int padding = 40;

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(35);

        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(organisation.getTitle(), offsetLeft, offsetTop, mPaint);
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        mPaint.setTextSize(30);
        mPaint.setColor(subTextColor);
        canvas.drawText(organisation.getRegion(), offsetLeft, offsetTop + padding, mPaint);
        canvas.drawText(organisation.getCity(), offsetLeft, offsetTop + 2 * padding, mPaint);
    }


    private void drawCurrency(Canvas canvas, int offSet, String currency, String rate) {
        mPaint.setTextSize(35);
        int currencyColor = context.getResources().getColor(R.color.currencyColor);
        mPaint.setColor(currencyColor);
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(currency, 80, offSet, mPaint);
        
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        mPaint.setColor(subTextColor);
        canvas.drawText(rate, 450, offSet, mPaint);
    }

    public void setColor(int color) {
        mColor = color;
    }
}
