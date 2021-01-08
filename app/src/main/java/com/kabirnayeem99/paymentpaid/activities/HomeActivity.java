package com.kabirnayeem99.paymentpaid.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.HomePagerAdapter;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabItemWorks;
    TabItem tabItemPayments;
    TabItem tabItemExports;
    ViewPager homeViewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

        tabLayout.setupWithViewPager(homeViewPager);

        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        homeViewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homeViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                
            }
        });
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tab_layout);
        tabItemWorks = findViewById(R.id.tab_item_works);
        tabItemPayments = findViewById(R.id.tab_item_payments);
        tabItemExports = findViewById(R.id.tab_item_exports);
        homeViewPager = findViewById(R.id.home_view_pager);
    }
}