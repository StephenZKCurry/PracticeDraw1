package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        // 绘制坐标轴
        Paint paintAxis = new Paint();
        paintAxis.setColor(Color.WHITE);
        paintAxis.setAntiAlias(true);
        // 绘制y轴
        canvas.drawLine(100, 100, 100, 800, paintAxis);
        // 绘制x轴
        canvas.drawLine(100, 800, 1000, 800, paintAxis);

        // 绘制直方图
        Paint paintRect = new Paint();
        paintRect.setColor(Color.parseColor("#72B916"));
        paintRect.setAntiAlias(true);
        RectF rect1 = new RectF(120, 796, 220, 800);
        canvas.drawRect(rect1, paintRect);

        RectF rect2 = new RectF(240, 750, 340, 800);
        canvas.drawRect(rect2, paintRect);

        RectF rect3 = new RectF(360, 750, 460, 800);
        canvas.drawRect(rect3, paintRect);

        RectF rect4 = new RectF(480, 550, 580, 800);
        canvas.drawRect(rect4, paintRect);

        RectF rect5 = new RectF(600, 400, 700, 800);
        canvas.drawRect(rect5, paintRect);

        RectF rect6 = new RectF(720, 300, 820, 800);
        canvas.drawRect(rect6, paintRect);

        RectF rect7 = new RectF(840, 600, 940, 800);
        canvas.drawRect(rect7, paintRect);

        // 绘制横轴坐标
        Paint paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(30);
        paintText.setAntiAlias(true);
        paintText.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Froyo", 170, 830, paintText);
        canvas.drawText("GB", 290, 830, paintText);
        canvas.drawText("ICS", 410, 830, paintText);
        canvas.drawText("JB", 530, 830, paintText);
        canvas.drawText("KitKat", 650, 830, paintText);
        canvas.drawText("L", 770, 830, paintText);
        canvas.drawText("M", 890, 830, paintText);

        // 绘制标题
        paintText.setFakeBoldText(true);
        paintText.setTextSize(50);
        canvas.drawText("直方图", 550, 900, paintText);
    }
}
