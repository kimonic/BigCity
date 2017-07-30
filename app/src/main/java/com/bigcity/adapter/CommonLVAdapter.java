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
 * name:            CommonLVAdapter
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/30
 * description：好物推荐,首页通用listview  adapter
 * history：
 * ===================================================
 */
public class CommonLVAdapter extends BaseAdapter {

    private Context context;
    private List<CommonBean>  list;

    public CommonLVAdapter(Context context, List<CommonBean> list) {
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
            view= LayoutInflater.from(context).inflate(R.layout.lvitem_commonadapter,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView1= (ImageView) view.findViewById(R.id.iv_lvitem_commonadapter1);
            viewHolder.imageView2= (ImageView) view.findViewById(R.id.iv_lvitem_commonadapter2);
            viewHolder.imageView3= (ImageView) view.findViewById(R.id.iv_lvitem_commonadapter3);
            viewHolder.imageView4= (ImageView) view.findViewById(R.id.iv_lvitem_commonadapter4);
            viewHolder.textView1= (TextView) view.findViewById(R.id.tv_lvitem_commonadapter1);
            viewHolder.textView2= (TextView) view.findViewById(R.id.tv_lvitem_commonadapter2);
            viewHolder.textView3= (TextView) view.findViewById(R.id.tv_lvitem_commonadapter3);
            viewHolder.textView4= (TextView) view.findViewById(R.id.tv_lvitem_commonadapter4);
            view.setTag(viewHolder);

        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.textView1.setText(list.get(position).getTitle());
        viewHolder.textView2.setText(list.get(position).getContent());
        viewHolder.textView3.setText(list.get(position).getHuifushu());
        viewHolder.textView4.setText(list.get(position).getLiulanshu());


        ImageGlideUtils.loadCircularImage(viewHolder.imageView1,list.get(position).getImaId());

        viewHolder.imageView2.setBackgroundResource(list.get(position).getImaId1());
        viewHolder.imageView3.setBackgroundResource(list.get(position).getImaId2());
        viewHolder.imageView4.setBackgroundResource(list.get(position).getImaId3());




        return view;
    }

    private class ViewHolder{
        private TextView textView1,textView2,textView3,textView4;
        private ImageView imageView1,imageView2,imageView3,imageView4;
    }

}
