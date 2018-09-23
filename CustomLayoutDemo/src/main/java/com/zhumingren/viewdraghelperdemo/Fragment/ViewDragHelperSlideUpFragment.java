package com.zhumingren.viewdraghelperdemo.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zhumingren.viewdraghelperdemo.R;
import com.zhumingren.viewdraghelperdemo.View.TopDrawerLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZhuMingren on 2017/9/27.
 */

public class ViewDragHelperSlideUpFragment extends Fragment {

    @Bind(R.id.top_drawer)
    TopDrawerLayout topDrawerLayout;
    @Bind(R.id.btn_down)
    Button btnDown;
    @Bind(R.id.btn_slide_to)
    Button btnSlideTo;
    @Bind(R.id.btn_up)
    Button btnUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_drag_slide, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @OnClick(R.id.btn_up)
    void up() {
        topDrawerLayout.closeDrawer();
    }

    @OnClick(R.id.btn_slide_to)
    void openTo() {
        topDrawerLayout.openDrawerTo(0.3f);
    }

    @OnClick(R.id.btn_down)
    void down() {
        topDrawerLayout.openDrawer();
    }

    private void initView(View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                down();
            }
        }, 300);
    }
}
