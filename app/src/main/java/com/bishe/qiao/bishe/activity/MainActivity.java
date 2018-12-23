package com.bishe.qiao.bishe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.fragment.HomeFragment;
import com.bishe.qiao.bishe.util.MyApplication;
import com.jjhcps.jysb.pagerslidingtabstriplib.PagerSlidingTabStrip;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    public ProgressDialog progressDialog;
    private JSONObject jobj;
    private String[] mTitles = new String[]{"热点", "说说", "关注", "竞猜", "第五"};
    private String[] allTitles = new String[]{"第一allTitlesallTitles", "第二allTitlesallTitles", "第三allTitlesallTitles", "第四allTitlesallTitles", "第五allTitlesallTitles", "第六allTitlesallTitles", "第七", "第八", "第九", "第十"};
    private List<Fragment> fragments;
    private List<Fragment> allFragments;
    private DrawerLayout mainDrawLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showProgressDialog();
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

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }

        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(MainActivity.this, MyCommentActivity.class);
                startActivity(intent);
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
        getMenuInflater().inflate(R.menu.toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MyApplication.getContext(), "搜索", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

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
    protected void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载···");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    protected void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }


}
