package com.materialdesign.xm.materialdesigntest.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.materialdesign.xm.materialdesigntest.R;
import com.materialdesign.xm.materialdesigntest.adapter.RecyclerViewAdapter;
import com.materialdesign.xm.materialdesigntest.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xm on 2015/8/5.
 */
public class RecyclerViewActivity extends BaseActivity {

    public static final String TAG = RecyclerView.class.getSimpleName();

    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 请求成功
     */
    public static final int REQUEST_SUCCESS = 100;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RecyclerViewActivity.REQUEST_SUCCESS:
                    Toast.makeText(RecyclerViewActivity.this, "请求数据成功！！", Toast.LENGTH_SHORT).show();
                    swiperefreshlayout.setRefreshing(false);
                    swiperefreshlayout.setEnabled(true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_ui_layout);

        ButterKnife.bind(this);

        if (toolbar != null) {
            toolbar.setTitle(TAG);
            setSupportActionBar(toolbar);
        }

        swiperefreshlayout.setColorSchemeResources(android.R.color.holo_red_dark, android.R.color.holo_green_dark,
                android.R.color.holo_blue_light, android.R.color.holo_orange_dark);

        //设置RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview.setLayoutManager(linearLayoutManager);
        recycleview.setAdapter(new RecyclerViewAdapter(this));

        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefreshlayout.setRefreshing(true);
                swiperefreshlayout.setEnabled(false);
                //模拟发请求 5秒延迟
                mHandler.sendEmptyMessageDelayed(REQUEST_SUCCESS, 5000);
            }
        });
    }

    public static final int MENUITEMID_LISTVIEW = 0;
    public static final int MENUITEMID_GridView = 1;
    public static final int MENUITEMID_StaggeView = 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENUITEMID_LISTVIEW, 0, "ListView").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER |
                MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(0, MENUITEMID_GridView, 0, "GridView").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER |
                MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(0, MENUITEMID_StaggeView, 0, "StaggeView").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER |
                MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case MENUITEMID_LISTVIEW://ListView
                //设置RecyclerView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recycleview.setLayoutManager(linearLayoutManager);
                break;
            case MENUITEMID_GridView://GridView
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                recycleview.setLayoutManager(gridLayoutManager);
                break;
            case MENUITEMID_StaggeView://瀑布流
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recycleview.setLayoutManager(staggeredGridLayoutManager);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
