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
import com.materialdesign.xm.materialdesigntest.adapter.StaggeredRecyclerViewAdapter;
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

    private RecyclerViewAdapter mAdapter = null;

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

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(false);
        }

        swiperefreshlayout.setColorSchemeResources(android.R.color.holo_red_dark, android.R.color.holo_green_dark,
                android.R.color.holo_blue_light, android.R.color.holo_orange_dark);

        toolbar.setTitle("LinearLayoutManager");
        //设置RecyclerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(linearLayoutManager);
        mAdapter = new RecyclerViewAdapter(this);

        mAdapter.setmOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                Toast.makeText(RecyclerViewActivity.this, "Click item Postion :  " + position, Toast.LENGTH_SHORT).show();
            }
        });

        recycleview.setAdapter(mAdapter);

        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefreshlayout.setRefreshing(true);
                swiperefreshlayout.setEnabled(false);
                //模拟发请求 5秒延迟
                mHandler.sendEmptyMessageDelayed(REQUEST_SUCCESS, 5000);
            }
        });

        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mCurrentState = RecyclerView.SCROLL_STATE_IDLE;

            private int lastdy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mCurrentState == RecyclerView.SCROLL_STATE_DRAGGING || mCurrentState == RecyclerView.SCROLL_STATE_SETTLING) {

                    if (dy < 0) {//向下滑动
                        //可以不处理，在SwipeRefreshLayout的onRefreshListener中实现下拉刷新
                    } else {//向上滑动
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            int lastitem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            if (recyclerView.getAdapter().getItemCount() == lastitem + 1) {
                                swiperefreshlayout.setRefreshing(true);
                                swiperefreshlayout.setEnabled(false);
                                //模拟发请求 5秒延迟
                                mHandler.sendEmptyMessageDelayed(REQUEST_SUCCESS, 5000);
                            }
                        }
                    }

                    lastdy = dy;
                }
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mCurrentState = newState;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public static final int MENUITEMID_LISTVIEW_VERTIC = 0;
    public static final int MENUITEMID_LISTVIEW_HORIZON = 1;
    public static final int MENUITEMID_GridView = 2;
    public static final int MENUITEMID_StaggeView = 3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENUITEMID_LISTVIEW_VERTIC, 0, "ListView_Vertic").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER |
                MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(0, MENUITEMID_LISTVIEW_HORIZON, 0, "ListView_Horizonal").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER |
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
            case MENUITEMID_LISTVIEW_VERTIC://ListView
                toolbar.setTitle("LinearLayoutManager_Vertic");
                //设置RecyclerView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recycleview.setLayoutManager(linearLayoutManager);
                return true;

            case MENUITEMID_LISTVIEW_HORIZON://ListView
                toolbar.setTitle("LinearLayoutManager_Horizon");
                //设置RecyclerView
                LinearLayoutManager horizonlinearLayoutManager = new LinearLayoutManager(this);
                horizonlinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recycleview.setLayoutManager(horizonlinearLayoutManager);
                return true;
            case MENUITEMID_GridView://GridView
                toolbar.setTitle("GridLayoutManager");
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                recycleview.setLayoutManager(gridLayoutManager);
                return true;
            case MENUITEMID_StaggeView://瀑布流
                toolbar.setTitle("StaggeredGridLayoutManager");
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                recycleview.setLayoutManager(staggeredGridLayoutManager);
                recycleview.setAdapter(new StaggeredRecyclerViewAdapter(RecyclerViewActivity.this));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
