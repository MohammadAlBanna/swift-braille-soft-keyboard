package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Locale;

//Paint the Braille Dot
public class BrailleDot extends View {
    Context context;
    Paint paint = null;
    Paint textPaint = null;
    int dotTextNumber = 0;
    Canvas canvas;

    //Dot settings
    int radius = 0;
    int fillColor = 0;
    int strokeColor = 0;
    boolean viewBrailleDotNumber = true;
    boolean fillDot = false;
    boolean fillDotOnTouchSettings = false;
    boolean startKeyboardFromRight;
    RelativeLayout.LayoutParams layoutParams;

    //-------------------------------------------------------------------------//
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public BrailleDot(Context context) {
        super(context);
        try {
            this.context = context;
            this.paint = new Paint();
            this.textPaint = new Paint();
            startKeyboardFromRight = Common.startKeyboardContainerFromRight;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    public int getFullRadius() {
        return (this.radius * 2) + (Common.brailleDotStrokeSize * 2);
    }

    //-------------------------------------------------------------------------//
    public void setRadius(int radius) {
        this.radius = radius;
    }

    //-------------------------------------------------------------------------//
    public void setLayoutParams(RelativeLayout.LayoutParams params) {
        this.layoutParams = params;
    }

    //-------------------------------------------------------------------------//
    public RelativeLayout.LayoutParams getLayoutParams() {
        return this.layoutParams;
    }

    //-------------------------------------------------------------------------//
    public int getRadius() {
        return this.radius;
    }

    //-------------------------------------------------------------------------//
    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    //-------------------------------------------------------------------------//
    public void setViewBrailleDotNumber(boolean viewBrailleDotNumber) {
        this.viewBrailleDotNumber = viewBrailleDotNumber;
    }

    //-------------------------------------------------------------------------//
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    //-------------------------------------------------------------------------//
    public void setFillDotOnTouchSettings(boolean fillDotOnTouchSettings) {
        this.fillDotOnTouchSettings = fillDotOnTouchSettings;
    }

    //-------------------------------------------------------------------------//
    public void setDotTextNumber(int dotTextNumber) {
        this.dotTextNumber = dotTextNumber;
    }

    //-------------------------------------------------------------------------//
    //Fill the dot when I hover or tap on it
    public void fillTheDot() {
        if (fillDotOnTouchSettings) {
            fillDot = true;
            invalidate();
        }
    }

    //-------------------------------------------------------------------------//
    //Clear the fill color dot when I get hover up or un-tap on it
    public void clearFillTheDot() {
        if (fillDotOnTouchSettings) {
            fillDot = false;
            invalidate();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
            this.canvas = canvas;
            setMinimumWidth(radius * 2);
            int theCircleWidth = getWidth();
            int theCircleHeight = getHeight();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.TRANSPARENT);
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            canvas.drawPaint(paint);

            // stroke
            paint.setColor(strokeColor);
            canvas.drawCircle(theCircleWidth / 2, theCircleHeight / 2, radius + Common.brailleDotStrokeSize, paint);

            //Radius
            if ((fillDot && fillDotOnTouchSettings) || !fillDotOnTouchSettings) {
                paint.setColor(fillColor);
                canvas.drawCircle(theCircleWidth / 2, theCircleHeight / 2, radius, paint);
            } else {
                paint.setColor(Common.getMyColor(R.color.swiftBrailleBG));
                canvas.drawCircle(theCircleWidth / 2, theCircleHeight / 2, radius, paint);
            }

            if (viewBrailleDotNumber) {
                textPaint.setColor(strokeColor);
                textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
                textPaint.setTextSize(radius);
                textPaint.setTextAlign(Paint.Align.CENTER);
                int yPos = (int) ((getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(String.format(Locale.getDefault(), "%d", dotTextNumber), theCircleWidth / 2, yPos, textPaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public float getX() {
        return startKeyboardFromRight ? Common.getRelativeLeft(this) : super.getX();
    }
}
