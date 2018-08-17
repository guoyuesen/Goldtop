package com.goldtop.gys.crdeit.goldtop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.interfaces.DateButtonListener;


/**
 * Created by 郭月森 on 2018/6/27.
 */

@SuppressLint("AppCompatCustomView")
public class DateButton extends TextView {
    private static int CharactersColor = Color.parseColor("#eeeeee");
    private static String text = "点击";
    private DateButtonListener clickListener = null;
    private static int startnum = 60;
    private static int num = 60;
    private Handler handler = new Handler();
    Runnable runnable =new Runnable() {
        @Override
        public void run() {
            num--;
            setText(""+num+"S");
            if (num==-1){
                num = startnum;
                setText(text);
                invalidate();
                Message message = new Message();
                message.what = 1;
                stopHander.sendMessage(message);
            }
            handler.postDelayed(this,1000);
        }
    };
    private Handler stopHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    handler.removeCallbacks(runnable);
                    if(clickListener!=null){
                        clickListener.onStop();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public DateButton(Context context) {
        super(context);
        initB();
    }

    public DateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initB();
    }

    public DateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initB();
    }
    private void initB(){
        setText(text);
        setGravity(Gravity.CENTER);
    }


    public void setOnClick(@Nullable DateButtonListener l) {
        this.clickListener = l;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num == startnum){
                    //handler.postDelayed(runnable,100);
                    clickListener.onClick(view);
                }
            }
        });

    }
    public boolean Start(){
        if (num == startnum){
            handler.postDelayed(runnable,100);
            if(clickListener!=null){
                clickListener.onStart();
            }
            return true;
        }else {
            return false;
        }
    }
    public static void setCharactersColor(int charactersColor) {
        CharactersColor = charactersColor;
    }

    public void setNum(int num) {
        DateButton.num = num;
        DateButton.startnum = num;
    }

    public void setthisText(String text) {
        DateButton.text = text;
        setText(text);
    }
    public void onStop(){
        handler.removeCallbacks(runnable);
    }

}
