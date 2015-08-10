package com.materialdesign.xm.materialdesigntest;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.materialdesign.xm.materialdesigntest.EventBean.AppBarLayoutOffsetChangeEvent;
import com.materialdesign.xm.materialdesigntest.base.BaseActivity;
import com.materialdesign.xm.materialdesigntest.fragment.RecycleViewCardViewItemFragment;
import com.materialdesign.xm.materialdesigntest.fragment.RecycleViewFragment;
import com.materialdesign.xm.materialdesigntest.fragment.TextInputLayoutFragment;
import com.materialdesign.xm.materialdesigntest.ui.RecyclerViewActivity;
import com.materialdesign.xm.materialdesigntest.util.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private List<TabBean> tabs = new ArrayList<TabBean>();

    private MyTabLayoutAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (toolbar != null)
            setSupportActionBar(toolbar);

        collapsingToolbarLayout.setTitle("Material Design");

        tabs.add(new TabBean("TAB1", RecycleViewFragment.class));
        tabs.add(new TabBean("TAB2", RecycleViewCardViewItemFragment.class));
        tabs.add(new TabBean("TAB3", TextInputLayoutFragment.class));

        //移动toolbar到屏幕的最顶部 并移出toolbar.getHeight()的距离
        //toolbar.animate().translationY(-1 * toolbar.getHeight());

        //toolbar.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(500).setStartDelay(1500).setUpdateListener().start();

        adapter = new MyTabLayoutAdapter(getSupportFragmentManager(), tabs);

        viewpager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewpager);

        tablayout.setTabsFromPagerAdapter(adapter);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                Log.d(TAG, "offset : " + i);
                EventBus.getDefault().post(new AppBarLayoutOffsetChangeEvent(i));
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Palette palette = Palette.generate(BitmapFactory.decodeResource(getResources(), R.mipmap.bg));

                List<Palette.Swatch> swatches = palette.getSwatches();
                switch (position) {
                    case 0:
                        if (swatches != null && swatches.size() > 0) {
                            toolbar.setBackgroundColor(swatches.get(0).getRgb());
//                            tablayout.setBackgroundColor(swatches.get(0).getRgb());
                        }
                        break;
                    case 1:
                        if (swatches != null && swatches.size() > 1) {
                            toolbar.setBackgroundColor(swatches.get(1).getRgb());
//                            tablayout.setBackgroundColor(swatches.get(1).getRgb());
                        }
                        break;
                    case 2:
                        if (swatches != null && swatches.size() > 2) {
                            toolbar.setBackgroundColor(swatches.get(2).getRgb());
//                            tablayout.setBackgroundColor(swatches.get(2).getRgb());
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private class MyTabLayoutAdapter extends FragmentPagerAdapter {

        List<TabBean> mTabs = null;

        public MyTabLayoutAdapter(FragmentManager fm, List<TabBean> list) {
            super(fm);
            this.mTabs = list;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment result = null;
            try {
                result = (Fragment) mTabs.get(position).clsFragment.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).title;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_recyclerview:
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                MainActivity.this.startActivity(intent);
                return true;

            case R.id.action_addtab:
                tabs.add(new TabBean("addTAB", RecycleViewFragment.class));
                adapter.notifyDataSetChanged();//必须先调用这个方法，不然会报错adapter content changed ,but not call notifyDataSetChanged()
                tablayout.setTabsFromPagerAdapter(adapter);//刷新tab
                break;
            case R.id.action_deletetab:
                tabs.remove(tabs.size() - 1);
                adapter.notifyDataSetChanged();
                tablayout.setTabsFromPagerAdapter(adapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * TabLayout的数据实体
     */
    private class TabBean {
        public String title;

        public Class clsFragment;

        public TabBean(String title, Class clsFragment) {
            this.title = title;
            this.clsFragment = clsFragment;
        }
    }
}
