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
 * Created by Administrator on 2017/8/8.
 */

public class RelatedMeActLvAdapter extends BaseAdapter {
    private Context context;
    private List<CommonBean> list;

    public RelatedMeActLvAdapter(Context context, List<CommonBean> list) {
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
            view= LayoutInflater.from(context).inflate(R.layout.lvitem_relatedmeactadapter,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView1= (ImageView) view.findViewById(R.id.iv_lvitem_relatedmeact1);
            viewHolder.imageView2= (ImageView) view.findViewById(R.id.iv_lvitem_relatedmeact2);
            viewHolder.textView1= (TextView) view.findViewById(R.id.tv_lvitem_relatedmeact1);
            viewHolder.textView2= (TextView) view.findViewById(R.id.tv_lvitem_relatedmeact2);
            view.setTag(viewHolder);

        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.textView1.setText(list.get(position).getTitle());
        viewHolder.textView2.setText(list.get(position).getContent());


        ImageGlideUtils.loadCircularImage(viewHolder.imageView1,list.get(position).getImaId());
        viewHolder.imageView2.setBackgroundResource(list.get(position).getImaId1());




        return view;
    }

    private class ViewHolder{
        private TextView textView1,textView2;
        private ImageView imageView1,imageView2;
    }
}
