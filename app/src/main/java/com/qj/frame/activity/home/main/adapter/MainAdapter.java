package com.qj.frame.activity.home.main.adapter;

import android.content.Context;

import com.qj.common.adapter.CommonAdapter;
import com.qj.common.adapter.ViewHolder;
import com.qj.frame.R;
import com.qj.frame.activity.home.main.bean.MainMenuInfo;

import java.util.List;


public class MainAdapter extends CommonAdapter<MainMenuInfo> {


    public MainAdapter(Context context, List<MainMenuInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }


    @Override
    public void convert(ViewHolder helper, MainMenuInfo item, int position) {

        helper.setText(R.id.txt_title, item.getTitle(), -1);

        helper.setText(R.id.txt_content, item.getContent(), -1);

        helper.setText(R.id.txt_address, item.getAddress(), -1);

        helper.setImageResource(R.id.img_icon, item.getIcon());
    }

    public void updateData(List<MainMenuInfo> infos) {

        mDatas.clear();

        if (infos == null)
            return;

        mDatas.addAll(infos);
    }
}
