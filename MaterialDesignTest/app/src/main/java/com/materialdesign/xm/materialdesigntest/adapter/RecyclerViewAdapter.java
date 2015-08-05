package com.materialdesign.xm.materialdesigntest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.materialdesign.xm.materialdesigntest.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xm on 2015/8/3.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;

    private LayoutInflater mInflater = null;

    public RecyclerViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //inflate方法的第二个参数一定要写viewGroup，不然item的宽度是固定的。并不等于屏幕宽度
        View view = mInflater.inflate(R.layout.recycleritem_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.tv.setText("   RecyclerView   " + i);
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv)
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
