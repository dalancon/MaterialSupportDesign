package com.materialdesign.xm.materialdesigntest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.materialdesign.xm.materialdesigntest.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xm on 2015/8/10.
 */
public class TextInputLayoutFragment extends Fragment {

    @Bind(R.id.til_id)
    TextInputLayout tilId;
    @Bind(R.id.til_pwd)
    TextInputLayout tilPwd;
    @Bind(R.id.submit)
    Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.textinput_layout, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @OnClick(R.id.submit)
    public void submit(View view) {
        tilId.setError("用户名错误");
        tilPwd.setError("密码错误");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
