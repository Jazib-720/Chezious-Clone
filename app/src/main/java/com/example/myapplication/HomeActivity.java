package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ImageView btn_search, btn_contact, btn_profile;
    private Handler handler = new Handler();
    private int scrollDelay = 5000; // 5 seconds for auto-scroll
    private LinearLayout bulletIndicators;
    private LoopingViewPager loopingViewPager;
    private LoopingPagerAdapter loopingPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Your home.xml layout

        btn_search = findViewById(R.id.btn_search);

        btn_profile = findViewById(R.id.btn_profile);
        loopingViewPager = findViewById(R.id.loopViewPager);
        viewPager = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
btn_contact=findViewById(R.id.btn_contact);

        LinearLayout layoutLocation = findViewById(R.id.layout_location);



        bulletIndicators = findViewById(R.id.bulletIndicators);
        //----------------------------------------------
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        Log.d("FirebaseTest", "Firebase initialized successfully");

        btn_search.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Search.class);
            startActivity(intent);
        });

        btn_contact.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
            startActivity(intent);
        });

        btn_profile.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent;
            if (currentUser == null) {
                // If the user is not authenticated, redirect to login screen
                Intent loginIntent = new Intent(HomeActivity.this, PhoneSignInActivity.class);
                startActivity(loginIntent);
                finish();
            }
            else{
                // If the user is not authenticated, redirect to login screen
                Intent Intent = new Intent(HomeActivity.this, ProfileUser.class);
                startActivity(Intent);
                finish();
            }


        });

        // Set up the ViewPager2 with an adapter
        FragmentManager fragmentManager = getSupportFragmentManager();
        Lifecycle lifecycle = getLifecycle();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragmentManager, lifecycle);
        viewPager.setAdapter(viewPagerAdapter);

//location
        layoutLocation.setOnClickListener(v -> {
            // Navigate to another activity
            Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
            startActivity(intent);
        });
















        // Tab titles
        String[] tabTitles = {
                "Bhook Ka End", "Starters", "Somewhat Local", "Somewhat Super",
                "Cheezy Treats", "Pizza Deals", "Sandwich & Platters",
                "Special Pizza", "SomeWhat Amazing", "Pastas", "Burgurz",
                "Side Orders", "Addons"
        };

        // Linking TabLayout with ViewPager2 using TabLayoutMediator
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabTitles[position]);
        }).attach();




        List<Integer> imageList = Arrays.asList(
                R.drawable.image1,
                R.drawable.image2,
                R.drawable.image3,
                R.drawable.image4,
                R.drawable.image5
        );

        loopingPagerAdapter = new LoopingPagerAdapter(this, imageList);
        loopingViewPager.setAdapter(loopingPagerAdapter);

        initBulletIndicators(imageList.size());

        loopingViewPager.addOnPageChangeListener(new LoopingViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updateBulletIndicators(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Not needed
            }
        });

        startAutoScroll();
    }

    private void initBulletIndicators(int size) {
        bulletIndicators.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView bullet = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20, 20); // Bullet size
            params.setMargins(10, 0, 10, 0); // Margin between bullets
            bullet.setLayoutParams(params);
            bullet.setImageResource(R.drawable.inactive_bullet); // Default state for bullets
            bulletIndicators.addView(bullet);
        }
    }

    private void updateBulletIndicators(int position) {
        int childCount = bulletIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView bullet = (ImageView) bulletIndicators.getChildAt(i);
            if (i == position) {
                bullet.setImageResource(R.drawable.active_bullet); // Active state for current bullet
            } else {
                bullet.setImageResource(R.drawable.inactive_bullet); // Inactive state for others
            }
        }
    }

    private void startAutoScroll() {
        Runnable autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = loopingViewPager.getCurrentItem();
                int nextItem = currentItem + 1;
                if (nextItem >= loopingPagerAdapter.getCount()) {
                    nextItem = 0; // Loop back to the first item
                }
                loopingViewPager.setCurrentItem(nextItem, true); // Move to the next item smoothly
                handler.postDelayed(this, scrollDelay); // Repeat after the delay
            }
        };

        handler.postDelayed(autoScrollRunnable, scrollDelay);
    }















    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
