package com.bishe.qiao.bishe.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.fragment.HomeFragment;
import com.jjhcps.jysb.pagerslidingtabstriplib.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    private String[] mTitles = new String[]{"热点", "说说", "关注", "竞猜", "第五"};
    private String[] allTitles = new String[]{"第一allTitlesallTitles", "第二allTitlesallTitles", "第三allTitlesallTitles", "第四allTitlesallTitles", "第五allTitlesallTitles", "第六allTitlesallTitles", "第七", "第八", "第九", "第十"};
    private List<Fragment> fragments;
    private List<Fragment> allFragments;
    private DrawerLayout mainDrawLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mainDrawLayout = findViewById(R.id.main_draw_layout);
        NavigationView navView = findViewById(R.id.activity_main_nav_view);

        ActionBar actionBar = getSupportActionBar();



        Adapter adapter = new Adapter(getSupportFragmentManager(),mTitles);
        PagerSlidingTabStrip tabs = findViewById(R.id.tabs);
        ViewPager viewpager = findViewById(R.id.viewpager);
        allFragments = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            allFragments.add(HomeFragment.newInstance(String.valueOf(i)));
        }
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("1"));
        fragments.add(HomeFragment.newInstance("2"));
        fragments.add(HomeFragment.newInstance("3"));
        fragments.add(HomeFragment.newInstance("4"));
        fragments.add(HomeFragment.newInstance("5"));

        adapter.addFragments(allFragments);
        viewpager.setOffscreenPageLimit(allTitles.length);
        viewpager.setAdapter(adapter);
        tabs.setViewPager(viewpager);
        viewpager.setCurrentItem(0, false);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                fragments.remove(4);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });













/*        LayoutInflater inflater=getLayoutInflater();
        View view1 = inflater.inflate(R.layout.fragment_main_home, null);
        TextView textView = view1.findViewById(R.id.fragment_layout_home_text_view);
        textView.setText("zhangshan");
        View view2 = inflater.inflate(R.layout.fragment_main_home,null);
        View view3 = inflater.inflate(R.layout.fragment_main_home, null);

        viewList = new ArrayList<>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);


        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));


                return viewList.get(position);
            }
        };


        viewPager.setAdapter(pagerAdapter);*/
        Drawable drawable = null;
        CircleImageView ddd = null;
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);

        }

        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mainDrawLayout.closeDrawers();
                return true;
            }
        });
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, LoginRegisterActivity.class);
                intent.putExtra("","");
                Log.d("aaaaa", "aaaaaaaaa");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mainDrawLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

/*
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = { "Categories", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid",
                "Top New Free", "Trending" };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return SuperAwesomeCardFragment.newInstance(position);
        }

    }*/

    class Adapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();
        String[] mTitles;

        public Adapter(FragmentManager fm, String[] mTitles) {
            super(fm);
            this.mTitles = mTitles;
        }

        public void addFragments(List<Fragment> fragment) {
            this.mFragments = fragment;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (allTitles != null && allTitles.length > 0 && position < allTitles.length) {
                return allTitles[position];
            }
            return "";
        }
    }
}
