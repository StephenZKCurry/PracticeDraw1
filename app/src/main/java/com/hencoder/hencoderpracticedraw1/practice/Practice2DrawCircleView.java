package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice2DrawCircleView extends View {

    public Practice2DrawCircleView(Context context) {
        super(context);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawCircle() 方法画圆
//        一共四个圆：1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆
        Paint paintFill = new Paint();
        paintFill.setColor(Color.BLACK);
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setAntiAlias(true);

        Paint paintStroke = new Paint();
        paintStroke.setColor(Color.BLACK);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setAntiAlias(true);

        canvas.drawCircle(200, 200, 160, paintFill);

        canvas.drawCircle(600, 200, 160, paintStroke);

        paintFill.setColor(Color.parseColor("#4A90E2"));
        canvas.drawCircle(200, 600, 160, paintFill);

        paintStroke.setStrokeWidth(20);
        canvas.drawCircle(600, 600, 160, paintStroke);
    }
}
