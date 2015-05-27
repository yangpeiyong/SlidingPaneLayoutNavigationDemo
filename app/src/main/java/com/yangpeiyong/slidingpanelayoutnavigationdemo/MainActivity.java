package com.yangpeiyong.slidingpanelayoutnavigationdemo;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;


import com.yangpeiyong.widget.SlidingPaneLayout;


public class MainActivity extends ActionBarActivity
        implements SlidingPaneLayout.PanelSlideListener{

    FragmentSideMenu mFragmentSideMenu;

    private static final int FRAGMENT_TYPE_1 = 0;
    private static final int FRAGMENT_TYPE_2 = 1;
    private static final int FRAGMENT_TYPE_3 = 2;

    SlidingPaneLayout slidingPaneLayout;
    SparseArray<Fragment> mRightFragments;
    Fragment mCurrentFragment;

    private ScaleAnimation myAnimation_Scale;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_menu_selector);

        mRightFragments = new SparseArray<>();
        slidingPaneLayout = (SlidingPaneLayout)findViewById(R.id.sliding_pane_layout);
        slidingPaneLayout.setPanelSlideListener(this);
        slidingPaneLayout.setCoveredFadeColor(0xffff0000);

        myAnimation_Scale = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        myAnimation_Scale.setDuration(2000);

        mFragmentSideMenu = new FragmentSideMenu();
        Fragment fragment1 = new Fragment1();


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLeft, mFragmentSideMenu);
        ft.replace(R.id.frameRight, fragment1);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置动画效果
        ft.commit();

        mRightFragments.put(FRAGMENT_TYPE_1, fragment1);


    }



    @Override
    public void onPanelSlide(View view, float v) {
        mFragmentSideMenu.scale(v);
    }

    @Override
    public void onPanelOpened(View view) {
    }

    @Override
    public void onPanelClosed(View view) {
    }

    public void openPane(View view) {
        slidingPaneLayout.openPane();
    }


    private void showFragment(int fragmentsId) {

        Fragment fragment = mRightFragments.get(fragmentsId);
        if (fragment == null) {
            fragment = createFragment(fragmentsId);
            mRightFragments.put(fragmentsId, fragment);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameRight, fragment);
        ft.commit();
        mCurrentFragment = fragment;
    }

    private Fragment createFragment(int fragmentsId) {
        Fragment fragment = null;
        switch (fragmentsId) {

            case FRAGMENT_TYPE_1:
                fragment = new Fragment1();
                break;
            case FRAGMENT_TYPE_2:
                fragment = new Fragment2();
                break;
            case FRAGMENT_TYPE_3:
                fragment = new Fragment3();
                break;
        }
        return fragment;
    }

    public void showFragment1(View view) {

        showFragment(FRAGMENT_TYPE_1);
        slidingPaneLayout.closePane();
    }
    public void showFragment2(View view) {

        showFragment(FRAGMENT_TYPE_2);
        slidingPaneLayout.closePane();
    }
    public void showFragment3(View view) {

        showFragment(FRAGMENT_TYPE_3);
        slidingPaneLayout.closePane();
    }





    private long key_back_time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!slidingPaneLayout.isOpen()) {
                key_back_time = 0;
                slidingPaneLayout.openPane();
            } else {
                long current = System.currentTimeMillis();
                if (current - key_back_time < 2000)
                    return super.onKeyDown(keyCode, event);
                else {
                    key_back_time = current;
                    Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                openPane(null);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

