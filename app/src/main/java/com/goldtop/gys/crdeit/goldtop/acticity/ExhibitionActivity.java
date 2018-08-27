package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.StatusBarUtil;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.ThisApplication;

/**
 * Created by 郭月森 on 2018/6/25.
 */

public class ExhibitionActivity extends BaseActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarLightMode(this.getWindow());
        BaseActivity.hiedBar(this);

        setContentView(R.layout.activity_exhibition);
        viewPager = findViewById(R.id.exhibition_pager);
        initactivity();

    }

    private void initactivity() {
        viewPager.setAdapter(new PagerAdapter() {
            int [] t = new int[]{R.mipmap.ydy01,
                    R.mipmap.ydy02,
                    R.mipmap.ydy03};
            @Override
            public int getCount() {
                return t.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(ExhibitionActivity.this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(t[position]);
                //MyVolley.getImage(t[position],imageView);
                /*TextView textView = new TextView(ExhibitionActivity.this);
                textView.setGravity(Gravity.CENTER);
                textView.setText(""+t[position]);*/
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    findViewById(R.id.exhibition_break).setVisibility(View.GONE);
                    findViewById(R.id.exhibition_next).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.exhibition_next).setVisibility(View.GONE);
                    findViewById(R.id.exhibition_break).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        findViewById(R.id.exhibition_break).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ThisApplication.getContext(),"跳过",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ExhibitionActivity.this,HomeActivity.class));
                finish();
            }
        });
        findViewById(R.id.exhibition_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ThisApplication.getContext(),"立即进入",Toast.LENGTH_LONG).show();
                //WebUtilActivity.InWeb(ExhibitionActivity.this,"http://www.baidu.com","百度",null);
                startActivity(new Intent(ExhibitionActivity.this,HomeActivity.class));
                finish();
            }
        });
    }


}
