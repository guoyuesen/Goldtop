package com.goldtop.gys.crdeit.goldtop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;

/**
 * Created by 郭月森 on 2018/8/1.
 */

@SuppressLint("AppCompatCustomView")
public class RImageView extends ImageView {
    private int StrokeWidth = 0;
    private int StrokeColor = Color.parseColor("#ffffff");
    public RImageView(Context context) {
        super(context);
    }

    public RImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }
    public RImageView setStroke(int color,int size){
        StrokeWidth = size;
        StrokeColor = color;
        return this;
    }
    @Override
    public void setImageBitmap(Bitmap bm) {
        RoundedBitmapDrawable b = createRoundImageWithBorder(bm);
        super.setImageBitmap(b.getBitmap());
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        Bitmap image = drawableToBitmap(drawable);
        RoundedBitmapDrawable b = createRoundImageWithBorder(image);
        super.setImageDrawable(b);
    }
    public static Bitmap drawableToBitmap(Drawable drawable) {

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
    private RoundedBitmapDrawable createRoundImageWithBorder(Bitmap bitmap){
        //原图宽度
        int bitmapWidth = bitmap.getWidth();
        //原图高度
        int bitmapHeight = bitmap.getHeight();
        //边框宽度 pixel

        //转换为正方形后的宽高
        int bitmapSquareWidth = Math.min(bitmapWidth,bitmapHeight);

        //最终图像的宽高
        int newBitmapSquareWidth = bitmapSquareWidth+StrokeWidth;

        Bitmap roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth,newBitmapSquareWidth,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        int x = StrokeWidth + bitmapSquareWidth - bitmapWidth;
        int y = StrokeWidth + bitmapSquareWidth - bitmapHeight;

        //裁剪后图像,注意X,Y要除以2 来进行一个中心裁剪
        canvas.drawBitmap(bitmap, x/2, y/2, null);
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(StrokeWidth);
        borderPaint.setColor(StrokeColor);

        //添加边框
        canvas.drawCircle(canvas.getWidth()/2, canvas.getWidth()/2, newBitmapSquareWidth/2, borderPaint);

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),roundedBitmap);
        roundedBitmapDrawable.setGravity(Gravity.CENTER);
        roundedBitmapDrawable.setCircular(true);
        roundedBitmapDrawable.setAntiAlias(true);
        return roundedBitmapDrawable;
    }


}
