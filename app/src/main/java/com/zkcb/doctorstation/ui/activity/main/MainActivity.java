package com.zkcb.doctorstation.ui.activity.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.zkcb.doctorstation.R;
import com.zkcb.doctorstation.base.BaseActivity;
import com.zkcb.doctorstation.base.BasePresenter;
import com.zkcb.doctorstation.http.Api;
import com.zkcb.doctorstation.ui.fragment.doctorCenter.DoctorCenterFragment;
import com.zkcb.doctorstation.ui.fragment.hospitalization.HospitalizationFragment;
import com.zkcb.doctorstation.ui.fragment.operation.OperationFragment;
import com.zkcb.doctorstation.models.Tab;
import com.zkcb.doctorstation.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /*    @BindView(R.id.tab_layout)
        TabLayout tabLayout;
        @BindView(R.id.view_pager)
        ViewPager viewPager;*/
    @BindView(R.id.design_navigation_view)
    NavigationView designNavigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fragment_tab_host)
    FragmentTabHost fragmentTabHost;


    private TextView tvName;
    private TextView tvJob;

    private static final String EXIT = "exit";
    private LayoutInflater mLayoutInflater;
    private List<Tab> mList = new ArrayList<>();
    private long exitTimes;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initContent() {
        ButterKnife.bind(this);
        //初始化toolbar
        initToolbar();
        //初始化nav
        initNavContent();
        //初始化tabLayout
        //initTabLayout();
        initTab();
    }

    //数据的准备阶段
    @Override
    public void initData() {
       /* CenterFragment centerFragment = new CenterFragment();
        ClinicFragment clinicFragment = new ClinicFragment();
        RecordsFragment recordsFragment = new RecordsFragment();
        fragments.add(centerFragment);
        fragments.add(clinicFragment);
        fragments.add(recordsFragment);*/

    }


    @Override
    public void bindView() {
        // navigation中item的点击事件 调用onNavigationItemClick()
        navClickEvent();
    }

    public void initTab() {
        mLayoutInflater = LayoutInflater.from(this);
        fragmentTabHost = findViewById(R.id.fragment_tab_host);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.real_content);
        Tab hospitalization = new Tab(R.string.hospitalization, R.drawable.selector_hospitlization, HospitalizationFragment.class);
        Tab outPatient = new Tab(R.string.operation, R.drawable.selector_operation, OperationFragment.class);
        Tab doctor_center = new Tab(R.string.doctor_center, R.drawable.selector_doctor_center, DoctorCenterFragment.class);
        mList.add(hospitalization);
        mList.add(doctor_center);
        mList.add(outPatient);
        for (Tab tab : mList) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildView(tab));
            fragmentTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        fragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        fragmentTabHost.setCurrentTab(0);
    }

    private View buildView(Tab tab) {
        View view = mLayoutInflater.inflate(R.layout.view_tab, null);
        TextView textView = view.findViewById(R.id.tab_title);
        ImageView imageView = view.findViewById(R.id.tab_icon);
        imageView.setImageResource(tab.getIcon());
        textView.setText(getString(tab.getTitle()));
        return view;
    }

   /* private void initTabLayout() {
        //指示条高度
        tabLayout.setSelectedTabIndicatorHeight(2);
        //tabLayout模式为固定
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //添加tab
        addTabItem();
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabPagerAdapter);
//        tabLayout.set
        tabLayout.setupWithViewPager(viewPager);
    }*/

    /* private void addTabItem() {
         TabLayout.Tab tab = tabLayout.newTab();
         tab.setText(getString(R.string.medical_records))
                 .setIcon(R.mipmap.ic_launcher);
         tabLayout.addTab(tab);

         tab = tabLayout.newTab()
                 .setText(getString(R.string.outpatient_department))
                 .setIcon(R.mipmap.ic_launcher);
         tabLayout.addTab(tab);

         tab = tabLayout.newTab()
                 .setText(getString(R.string.center))
                 .setIcon(R.mipmap.ic_launcher);
         tabLayout.addTab(tab);
     }
 */
    // navigation item click event
    public void onNavigationItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_message:
                //我的信息
                showToast("my message");
                break;
            case R.id.item_schedule:
                //我的排班
                showToast("my schedule");
                break;
            case R.id.item_exit:
                exitApp();
                break;
        }
    }

    private void initNavContent() {
        View headerView = designNavigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tv_name);
        tvJob = headerView.findViewById(R.id.tv_job);
        tvName.setText("名字");
        tvJob.setText("其它");
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.nurse);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void navClickEvent() {
        designNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onNavigationItemClick(item);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(EXIT, false);
            if (isExit) {
                this.finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if (System.currentTimeMillis() - exitTimes > 2000) {
                    ToastUtils.showShortToast(this, getString(R.string.double_click_exit));
                    exitTimes = System.currentTimeMillis();
                    return false;
                } else {
                    exitApp();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出程序
    public void exitApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXIT, true);
        startActivity(intent);
    }

    public void login() {

    }
}
