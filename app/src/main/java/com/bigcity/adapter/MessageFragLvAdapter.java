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
 * name:            MessageFragLvAdapter
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/10
 * description：  消息--MessageFragment中的listview的adapter
 * history：
 * ===================================================
 */

public class MessageFragLvAdapter extends BaseAdapter {

    private Context context;
    private List<CommonBean> list;

    public MessageFragLvAdapter(Context context, List<CommonBean> list) {
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
            view= LayoutInflater.from(context).inflate(R.layout.lvitem_messagefraglvadapter,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView1= (ImageView) view.findViewById(R.id.iv_lvitem_messagefragadapter);
            viewHolder.textView1= (TextView) view.findViewById(R.id.tv_lvitem_messagefragadapter1);
            viewHolder.textView2= (TextView) view.findViewById(R.id.tv_lvitem_messagefragadapter2);
            viewHolder.textView3= (TextView) view.findViewById(R.id.tv_lvitem_messagefragadapter3);
            view.setTag(viewHolder);

        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.textView1.setText(list.get(position).getTitle());
        viewHolder.textView3.setText(list.get(position).getContent());
        viewHolder.textView2.setText(list.get(position).getHuifushu());


        ImageGlideUtils.loadCircularImage(viewHolder.imageView1,list.get(position).getImaId());





        return view;
    }

    private class ViewHolder{
        private TextView textView1,textView2,textView3;
        private ImageView imageView1;
    }

}
