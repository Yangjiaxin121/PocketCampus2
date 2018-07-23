package com.example.yangjiaxin.pocketcampus;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.yangjiaxin.pocketcampus.widget.GridItemClickListener;
import com.example.yangjiaxin.pocketcampus.widget.GridItemLongClickListener;
import com.example.yangjiaxin.pocketcampus.widget.GridViewPager;
import com.example.yangjiaxin.pocketcampus.widget.Model;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IT-CTY on 2018/4/25.
 */

public class Fragment1 extends Fragment {

    Banner banner;//banner组件
    List mlist;//图片资源
    List<String> mlist1;//轮播标题
    private Context mContext;
    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment1,container,false);
        //初始化两个集合，图片资源可以是本地，也可以是网络的url
        mlist = new ArrayList<>();
        mlist.add(R.drawable.bingxue);
        mlist.add(R.drawable.fpcard);
        mlist.add(R.drawable.gggg);
        mlist.add(R.drawable.peng);
        mlist1 = new ArrayList<>();
        mlist1.add("123231");
        mlist1.add("123231");
        mlist1.add("123231");
        mlist1.add("123231");

        banner = view.findViewById(R.id.main_banner);

        banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
        banner.setImages(mlist);//设置图片源
        banner.setBannerTitles(mlist1);//设置标题源
        banner.setDelayTime(1500);//设置轮播事件，单位毫秒
        banner.setBannerAnimation(Transformer.Stack);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认

/**
 *  轮播图的点击事件
 */
        mContext = this.getActivity();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(mContext, "xiaoguo" + position, Toast.LENGTH_SHORT).show();
            }
        });
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置

        banner.start();//开始轮播，一定要调用此方法。

        GridViewPager mGridViewPager = (GridViewPager) view.findViewById(R.id.mGridViewPager);
        //初始化数据源
        mGridViewPager
                //设置每一页的容量
                .setPageSize(10)
                .setGridItemClickListener(new GridItemClickListener() {
                    @Override
                    public void click(int pos, int position, String str) {
                        Log.d("123", pos + "/" + str);
                    }
                })
                .setGridItemLongClickListener(new GridItemLongClickListener() {
                    @Override
                    public void click(int pos, int position, String str) {
                        Log.d("456", pos + "/" + str);
                    }
                }).init(initData());

        return view;
    }
    private List<Model> initData() {
        List<Model> mData = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", mContext.getPackageName());
            mData.add(new Model(titles[i], imageId));
        }
        return mData;
    }

}