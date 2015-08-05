package com.materialdesign.xm.materialdesigntest.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.materialdesign.xm.materialdesigntest.EventBean.AppBarLayoutOffsetChangeEvent;
import com.materialdesign.xm.materialdesigntest.R;
import com.materialdesign.xm.materialdesigntest.adapter.RecyclerViewAdapter;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by xm on 2015/8/3.
 */
public class RecycleViewFragment extends Fragment {

    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    MyHandler myHandler = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        //设置SwipeRefreshLayout
        swiperefreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //下拉刷新
            }
        });

        if (myHandler == null)
            myHandler = new MyHandler(getActivity());


        //设置RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview.setLayoutManager(linearLayoutManager);
        recycleview.setAdapter(getAdapter());

        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int currentStatus = RecyclerView.SCROLL_STATE_IDLE;

            private int lastdy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager linearLayoutManager1 = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstvisibleItemPosition = linearLayoutManager1.findFirstCompletelyVisibleItemPosition();
                if (firstvisibleItemPosition == 0 && (dy > lastdy)) {//第一个可见,并且向下滑动列表
                    lastdy = dy;
                } else {

                }
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                currentStatus = newState;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }

    public RecyclerView.Adapter getAdapter() {
        return new RecyclerViewAdapter(getActivity());
    }


    /**
     * 自定义Handler 防止内存泄漏
     */
    class MyHandler extends Handler {

        WeakReference<Activity> mActivityReference;

        MyHandler(Activity activity) {
            mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    //AppBarLayoutOffsetChange 监听
    public void onEventMainThread(AppBarLayoutOffsetChangeEvent event) {
        if (event.offset == 0)
            swiperefreshlayout.setEnabled(true);
        else
            swiperefreshlayout.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
