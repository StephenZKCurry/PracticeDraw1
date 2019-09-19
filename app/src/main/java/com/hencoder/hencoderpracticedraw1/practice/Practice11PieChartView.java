package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Practice11PieChartView extends View {

    /**
     * View的宽高
     */
    private int mWidth;
    private int mHeight;
    /**
     * 扇形半径
     */
    private int mRadius;

    private List<Model> mData;
    private List<Region> mRegionList;

    private Paint paintPie;
    private TextPaint paintText;

    public Practice11PieChartView(Context context) {
        this(context, null);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintPie = new Paint();
        paintPie.setAntiAlias(true);

        paintText = new TextPaint();
        paintText.setColor(Color.WHITE);
        paintText.setStrokeWidth(3);
        paintText.setTextSize(30);
        paintText.setAntiAlias(true);

        mData = new ArrayList<>();
        mData.add(new Model("Marshmallow", -60, 55, Color.parseColor("#FFC107"), false));
        mData.add(new Model("Gingerbread", -5, 10, Color.parseColor("#9C27B0"), false));
        mData.add(new Model("Ice Cream Sandwich", 5, 10, Color.parseColor("#9E9E9E"), false));
        mData.add(new Model("Jelly Bean", 15, 50, Color.parseColor("#009688"), false));
        mData.add(new Model("KitKat", 65, 100, Color.parseColor("#2196F3"), false));
        mData.add(new Model("Lollipop", 165, 135, Color.parseColor("#F44336"), true));

        mRegionList = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = (int) (Math.min(w, h) * 0.6 / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        mRegionList.clear();

        for (Model model : mData) {
            drawContent(canvas, model.getStartAngle(), model.getSweepAngle(),
                    model.getColor(), model.getName(), model.isSelected());
        }
    }

    /**
     * 绘制每个扇形的内容
     *
     * @param canvas
     * @param startAngle 起始角度
     * @param sweepAngle 扫过角度
     * @param color      扇形背景颜色
     * @param text       文字
     * @param isSelected 是否被选中
     */
    private void drawContent(Canvas canvas, float startAngle, float sweepAngle, int color, String text, boolean isSelected) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        // 计算扇形中心的弧度，这里取负数是相对于数学坐标系来说
        float centerAngle = -(float) ((startAngle + sweepAngle / 2) * Math.PI / 180);
        // 选中扇形的偏移量
        float offsetX = (float) (30 * Math.cos(centerAngle));
        float offsetY = (float) (30 * Math.sin(centerAngle));

        // 绘制扇形
        paintPie.setColor(color);
        RectF circleRect = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        if (!isSelected) {
//            canvas.drawArc(circleRect, startAngle, sweepAngle, true, paintPie);
            Path path = new Path();
            path.addArc(circleRect, startAngle, sweepAngle);
            path.lineTo(0, 0);
            path.close();
            canvas.drawPath(path, paintPie);

            Region region = new Region();
            region.setPath(path, new Region(-mRadius, -mRadius, mRadius, mRadius));
            mRegionList.add(region);
        } else {
            canvas.save();
            canvas.translate(offsetX, -offsetY);
//            canvas.drawArc(circleRect, startAngle, sweepAngle, true, paintPie);
            Path path = new Path();
            path.addArc(circleRect, startAngle, sweepAngle);
            path.lineTo(0, 0);
            path.close();
            canvas.drawPath(path, paintPie);

            Region region = new Region();
            region.setPath(path, new Region(-mRadius, -mRadius, mRadius, mRadius));
            mRegionList.add(region);

            canvas.restore();
        }

        // 绘制折线和文字
        float lineStartX = (float) (mRadius * Math.cos(centerAngle) - (isSelected ? -offsetX : 0));
        float lineStartY = -((float) (mRadius * Math.sin(centerAngle) + (isSelected ? offsetY : 0)));
        float lineEndX = (float) ((mRadius + 30) * Math.cos(centerAngle) - (isSelected ? -offsetX : 0));
        float lineEndY = -((float) ((mRadius + 30) * Math.sin(centerAngle) + (isSelected ? offsetY : 0)));
        // 绘制斜线
        canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, paintText);
        Rect textRect = new Rect();
        paintText.getTextBounds(text, 0, text.length(), textRect);
        if (centerAngle >= -Math.PI / 2 && centerAngle <= Math.PI / 2) {
            // 右半部分
            // 绘制横线
            canvas.drawLine(lineEndX, lineEndY, lineEndX + 50, lineEndY, paintText);
            // 绘制文字
            StaticLayout staticLayout = new StaticLayout(text, paintText,
                    (int) (mWidth / 2 - lineEndX - 60), Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
            canvas.save();
            Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
            canvas.translate(lineEndX + 60, lineEndY + (fontMetrics.top - fontMetrics.bottom) / 2);
            staticLayout.draw(canvas);
            canvas.restore();
//            canvas.drawText(text, lineEndX + 60, lineEndY + textRect.height() / 2, paintText);
        } else {
            // 左半部分
            // 绘制横线
            canvas.drawLine(lineEndX, lineEndY, lineEndX - 50, lineEndY, paintText);
            // 绘制文字
            StaticLayout staticLayout = new StaticLayout(text, paintText,
                    (int) (mWidth / 2 + lineEndX - 60), Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
            canvas.save();
            Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
            canvas.translate(lineEndX - 60 - Math.min(mWidth / 2 + lineEndX - 60, paintText.measureText(text)),
                    lineEndY + (fontMetrics.top - fontMetrics.bottom) / 2);
            staticLayout.draw(canvas);
            canvas.restore();
//            canvas.drawText(text, lineEndX - 60 - paintText.measureText(text), lineEndY + textRect.height() / 2, paintText);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() - mWidth / 2);
            int y = (int) (event.getY() - mHeight / 2);

            for (int i = 0; i < mRegionList.size(); i++) {
                if (mRegionList.get(i).contains(x, y)) {
                    for (Model model : mData) {
                        model.setSelected(false);
                    }
                    mData.get(i).setSelected(true);
                    break;
                }
            }
            invalidate();
        }
        return true;
    }

    class Model {
        String name;
        float startAngle;
        float sweepAngle;
        int color;
        boolean isSelected;

        public Model(String name, float startAngle, float sweepAngle, int color, boolean isSelected) {
            this.name = name;
            this.startAngle = startAngle;
            this.sweepAngle = sweepAngle;
            this.color = color;
            this.isSelected = isSelected;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getStartAngle() {
            return startAngle;
        }

        public void setStartAngle(float startAngle) {
            this.startAngle = startAngle;
        }

        public float getSweepAngle() {
            return sweepAngle;
        }

        public void setSweepAngle(float sweepAngle) {
            this.sweepAngle = sweepAngle;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
