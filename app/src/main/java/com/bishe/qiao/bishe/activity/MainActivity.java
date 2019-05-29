package com.bishe.qiao.bishe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.fragment.HomeFragment;
import com.bishe.qiao.bishe.util.Status;
import com.bishe.qiao.bishe.util.Util;
import com.bumptech.glide.Glide;
import com.jjhcps.jysb.pagerslidingtabstriplib.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bishe.qiao.bishe.util.MyApplication.getContext;

public class MainActivity extends BaseActivity {
    public ProgressDialog progressDialog;
    public static JSONObject jobj;
    private String[] allTitles;
    private List<Fragment> allFragments;
    private DrawerLayout mainDrawLayout;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            closeProgressDialog();
            Bundle data = msg.getData();
            String res = data.getString("res", null);
            switch (msg.what) {
                case 100:
                    Toast.makeText(getContext(), "网络错误！请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
                case 5://请求成功
                    Intent intent = new Intent(MainActivity.this, MyCommentActivity.class);
                    intent.putExtra("res", res);
                    startActivity(intent);
                    break;
                case 9://请求成功
                    Intent intent3 = new Intent(MainActivity.this, MyBrowseActivity.class);
                    intent3.putExtra("res", res);
                    startActivity(intent3);
                    break;
                case 11://请求成功
                    Intent intent4 = new Intent(MainActivity.this, MyFavoriteActivity.class);
                    intent4.putExtra("res", res);
                    startActivity(intent4);
                    break;
                case 19://请求成功
                    Intent intent5 = new Intent(MainActivity.this, SearchResultActivity.class);
                    intent5.putExtra("res", res);
                    startActivity(intent5);
                    break;                case 77://请求成功
                    Intent intent2 = new Intent(MainActivity.this, RecommentAcitvity.class);
                    intent2.putExtra("res", res);
                    startActivity(intent2);
                    break;
                default:
                    Toast.makeText(getContext(), "出现未知错误！请检查后重试", Toast.LENGTH_SHORT).show();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String res = intent.getStringExtra("res");
        jobj = JSON.parseObject(res);

//        showProgressDialog();
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mainDrawLayout = findViewById(R.id.main_draw_layout);
        NavigationView navView = findViewById(R.id.activity_main_nav_view);
        View headerView = navView.getHeaderView(0);
        //用户头像
        ImageView headImg = headerView.findViewById(R.id.nav_header_icon_image);
        Glide.with(this).load(jobj.getJSONObject("userInfo").getString("headUrl")).into(headImg);
        TextView nickName = headerView.findViewById(R.id.nav_header_user_name);
        nickName.setText(jobj.getJSONObject("userInfo").getString("nickName"));
        ActionBar actionBar = getSupportActionBar();

        Adapter adapter = new Adapter(getSupportFragmentManager(),allTitles);
        PagerSlidingTabStrip tabs = findViewById(R.id.tabs);
        ViewPager viewpager = findViewById(R.id.viewpager);
        allFragments = new ArrayList<>();


        JSONObject rankList = JSONObject.parseObject(jobj.getString("rankList"));
        int numbers = Integer.parseInt(rankList.getString("total"));
        allTitles = new String[numbers];
        for (int i = 1; i <= numbers; i++){
            allFragments.add(HomeFragment.newInstance(rankList.getString(String.valueOf(i))));
            allTitles[i-1] = rankList.getString("tag_" + i);
        }

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

        navView.setCheckedItem(R.id.nav_my_favorite);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_my_browse:
//                        TODO 我的收藏
                        showProgressDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                        .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                        .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("token", Status.token)
                                        .build();
                                Request request = new Request.Builder()
                                        .url(Util.baseUrl + "myBrowse")
                                        .post(requestBody)
                                        .build();
                                Response response;
                                String responseDate;
                                Message msg = new Message();
                                try {
                                    response = client.newCall(request).execute();
                                    responseDate = response.body().string();
                                    msg.what = 9;
                                    Bundle data = new Bundle();
                                    data.putString("res",responseDate);
                                    msg.setData(data);
                                    handler.sendMessage(msg);
                                }catch (Exception e){
                                    msg.what = 100;
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();
                        break;
                    case R.id.nav_my_comments:
                        showProgressDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                        .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                        .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("token", Status.token)
                                        .build();
                                Request request = new Request.Builder()
                                        .url(Util.baseUrl + "myComment")
                                        .post(requestBody)
                                        .build();
                                Response response;
                                String responseDate;
                                Message msg = new Message();
                                try {
                                    response = client.newCall(request).execute();
                                    responseDate = response.body().string();
                                    msg.what = 5;
                                    Bundle data = new Bundle();
                                    data.putString("res",responseDate);
                                    msg.setData(data);
                                    handler.sendMessage(msg);
                                }catch (Exception e){
                                    msg.what = 100;
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();
                        break;
                    case R.id.nav_my_recomment:
                        showProgressDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                        .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                        .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("token", Status.token)
                                        .build();
                                Request request = new Request.Builder()
                                        .url(Util.baseUrl + "recomment")
                                        .post(requestBody)
                                        .build();
                                Response response;
                                String responseDate;
                                Message msg = new Message();
                                try {
                                    response = client.newCall(request).execute();
                                    responseDate = response.body().string();
                                    msg.what = 77;
                                    Bundle data = new Bundle();
                                    data.putString("res",responseDate);
                                    msg.setData(data);
                                    handler.sendMessage(msg);
                                }catch (Exception e){
                                    msg.what = 100;
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();
                        break;
                    case R.id.nav_my_favorite:
                        showProgressDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                        .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                        .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("token", Status.token)
                                        .build();
                                Request request = new Request.Builder()
                                        .url(Util.baseUrl + "myFavorite")
                                        .post(requestBody)
                                        .build();
                                Response response;
                                String responseDate;
                                Message msg = new Message();
                                try {
                                    response = client.newCall(request).execute();
                                    responseDate = response.body().string();
                                    msg.what = 11;
                                    Bundle data = new Bundle();
                                    data.putString("res",responseDate);
                                    msg.setData(data);
                                    handler.sendMessage(msg);
                                }catch (Exception e){
                                    msg.what = 100;
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();
                        break;

                }
                mainDrawLayout.closeDrawers();
                return true;
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
            public boolean onQueryTextSubmit(final String s) {
                showProgressDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient.Builder()
                                .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("token", Status.token)
                                .add("searchStr", s)
                                .build();
                        Request request = new Request.Builder()
                                .url(Util.baseUrl + "search")
                                .post(requestBody)
                                .build();
                        Response response;
                        String responseDate;
                        Message msg = new Message();
                        try {
                            response = client.newCall(request).execute();
                            responseDate = response.body().string();
                            msg.what = 19;
                            Bundle data = new Bundle();
                            data.putString("res",responseDate);
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }catch (Exception e){
                            msg.what = 100;
                            handler.sendMessage(msg);
                        }
                    }
                }).start();
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
