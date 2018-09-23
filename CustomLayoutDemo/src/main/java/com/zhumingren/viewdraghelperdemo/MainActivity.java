package com.zhumingren.viewdraghelperdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.zhumingren.viewdraghelperdemo.Fragment.LeftMenuFragment;
import com.zhumingren.viewdraghelperdemo.Fragment.ViewDragHelpNormalFragment;
import com.zhumingren.viewdraghelperdemo.Fragment.ViewDragHelperRetrunFragment;
import com.zhumingren.viewdraghelperdemo.Fragment.ViewDragHelperSlideUpFragment;
import com.zhumingren.viewdraghelperdemo.Fragment.ViewSlideLeftFragment;
import com.zhumingren.viewdraghelperdemo.View.LeftDrawerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(com.zhumingren.viewdraghelperdemo.R.id.drawerlayout)
    LeftDrawerLayout drawerlayout;
    @Bind(com.zhumingren.viewdraghelperdemo.R.id.content_container)
    FrameLayout contentContainer;

    private LeftMenuFragment mMenuFragment;

    private List<Fragment> fragments = new ArrayList<>();

    private ViewDragHelpNormalFragment normalFragment;
    private ViewDragHelperRetrunFragment retrunFragment;
    private ViewDragHelperSlideUpFragment slideFragment;
    private ViewSlideLeftFragment slideLeftFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.zhumingren.viewdraghelperdemo.R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(com.zhumingren.viewdraghelperdemo.R.string.app_name);

        initMenu();
        initFragment();
    }

    private void initFragment() {
        normalFragment = new ViewDragHelpNormalFragment();
        retrunFragment = new ViewDragHelperRetrunFragment();
        slideFragment = new ViewDragHelperSlideUpFragment();
        slideLeftFragment = new ViewSlideLeftFragment();
/*        fragments.add(normalFragment);
        fragments.add(retrunFragment);*/
        fragments.add(slideFragment);
        fragments.add(slideLeftFragment);

        switchFragment(1);
    }

    private void initMenu() {
        FragmentManager fm = getSupportFragmentManager();
        mMenuFragment = (LeftMenuFragment) fm.findFragmentById(com.zhumingren.viewdraghelperdemo.R.id.menu_container);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(com.zhumingren.viewdraghelperdemo.R.id.menu_container, mMenuFragment = new LeftMenuFragment()).commitAllowingStateLoss();
        }

        mMenuFragment.setOnMenuItemSelectedListener(new LeftMenuFragment.OnMenuItemSelectedListener() {
            @Override
            public void menuItemSelected(int position, String title) {
                drawerlayout.closeDrawer();
                switchFragment(position);
            }
        });
    }

    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = fragments.get(position);
        if (fragment == null) {
            return;
        }
        for (Fragment fragmentItem : fragments) {
            if (fragmentItem == fragment) {
                if (!fragment.isAdded()) {
                    transaction.add(com.zhumingren.viewdraghelperdemo.R.id.content_container, fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (fragmentItem.isAdded()) {
                    transaction.hide(fragmentItem);
                }
            }
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.zhumingren.viewdraghelperdemo.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.zhumingren.viewdraghelperdemo.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
