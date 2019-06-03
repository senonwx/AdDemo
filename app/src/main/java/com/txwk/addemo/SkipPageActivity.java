package com.txwk.addemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SkipPageActivity extends AppCompatActivity {

    private MZBannerView banner;
    private List<String> tipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip_page);

        banner = findViewById(R.id.banner);
        tipList = new ArrayList<>();

        tipList.addAll(Arrays.asList(new String[]{"广告1", "广告2", "广告3"}));
        // 设置数据
        banner.setPages(tipList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.setDuration(3);
    }

    public void back(View view) {
        //这个标志位代表此页面 用户是否完成某个动作 以此跳转不同页面
//        boolean sign = true;
//        if(sign){
//            startActivity(new Intent(this,SkipActivity.class));
//        }else{
//            startActivity(new Intent(this,SkipActivity.class));
//        }

        finish();
    }

    public class BannerViewHolder implements MZViewHolder<String> {
        private TextView banner_tv;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            banner_tv = new TextView(context);
            return banner_tv;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            banner_tv.setText(data);
            banner_tv.setGravity(Gravity.CENTER);
            banner_tv.setTextSize(30);
            banner_tv.setBackgroundColor(R.color.colorPrimaryDark);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();//开始轮播
    }

}
