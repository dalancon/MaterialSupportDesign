package com.materialdesign.xm.materialdesigntest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.materialdesign.xm.materialdesigntest.R;
import com.materialdesign.xm.materialdesigntest.adapter.RecyclerViewAdapter;
import com.materialdesign.xm.materialdesigntest.adapter.RecyclerViewCardViewItemAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xm on 2015/8/3.
 */
public class RecycleViewCardViewItemFragment extends RecycleViewFragment {

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new RecyclerViewCardViewItemAdapter(getActivity());
    }
}
