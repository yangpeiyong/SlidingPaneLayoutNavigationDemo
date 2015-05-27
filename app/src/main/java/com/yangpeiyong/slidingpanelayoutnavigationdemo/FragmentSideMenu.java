package com.yangpeiyong.slidingpanelayoutnavigationdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




/**
 * @author yangpeiyong
 * 侧边目录
 */
public class FragmentSideMenu extends Fragment {

    private Context mContext;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = this.getActivity();
        View view = inflater.inflate(R.layout.fragment_side_navigation, container, false);
        linearLayout = (LinearLayout)view.findViewById(R.id.box_main);
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this.getActivity();


    }




    LinearLayout linearLayout;
    public void scale(float v) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            linearLayout.setScaleX(v);
            linearLayout.setScaleY(v);
            linearLayout.setPivotX(0);
            linearLayout.setPivotY(dip2px(getActivity(), 240));
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
