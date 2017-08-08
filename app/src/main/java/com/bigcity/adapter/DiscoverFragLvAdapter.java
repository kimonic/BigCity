package com.bigcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.bean.CommonBean;
import com.bigcity.utils.ImageGlideUtils;

import java.util.List;

/**
 * * ================================================
 * name:            DiscoverFragLvAdapter
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/8
 * description：  首页--发现--lvadapter
 * history：
 * ===================================================
 */

public class DiscoverFragLvAdapter extends BaseAdapter {
    private Context context;
    private List<CommonBean> list;

    public DiscoverFragLvAdapter(Context context, List<CommonBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(context).inflate(R.layout.lvitem_discoverfragadapter,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView1= (ImageView) view.findViewById(R.id.iv_lvitem_discoveract);
            viewHolder.textView1= (TextView) view.findViewById(R.id.tv_lvitem_discoveract);
            view.setTag(viewHolder);

        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.textView1.setText(list.get(position).getTitle());


        viewHolder.imageView1.setBackgroundResource(list.get(position).getImaId1());




        return view;
    }

    private class ViewHolder{
        private TextView textView1;
        private ImageView imageView1;
    }
}
