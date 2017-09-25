package com.bigcity.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.activity.BlogDetailsActivity;
import com.bigcity.bean.bmobbean.BlogBmobBean;
import com.bigcity.bean.bmobbean.CommentBmobBean;
import com.bigcity.gif.SingleGif;
import com.bigcity.utils.ImageGlideUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * * ================================================
 * name:            BlogDetailsActLvAdapter
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/9/15
 * description：BlogDetailsActivity中listview  adapter
 * history：
 * ===================================================
 */
public class BlogDetailsActLvAdapter extends BaseAdapter {

    private Context context;
    private List<CommentBmobBean> list;
    private List<String> listImage;

    public BlogDetailsActLvAdapter(Context context, List<CommentBmobBean> list) {
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
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.lvitem_blogdetailsactadapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView1 = (ImageView) view.findViewById(R.id.iv_blogdetailsadapter_icon);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.tv_blogdetailsadapter_name);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.iv_blogdetailsadapter_replynum);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.iv_blogdetailsadapter_admirenum);
            viewHolder.textView4 = (TextView) view.findViewById(R.id.tv_blogdetailsadapter_content);
            viewHolder.listView = (ListView) view.findViewById(R.id.lv_blogdetailsadapter_huifu);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView1.setText(list.get(position).getName());
        viewHolder.textView2.setText(list.get(position).getAdmire());
        viewHolder.textView3.setText(list.get(position).getReplyNum());


        if (listImage == null) {
            listImage = initStaticFaces();
        }
        String content = list.get(position).getContent();
        SpannableString spannableString = new SpannableString(content);
//        for (int i = 0; i < listImage.size(); i++) {
////            if (content.contains(listImage.get(i))) {
//                Log.e("TAG", "getView: ----------截取的字符串-----------"+listImage.get(i));
//                ImageSpan imgSpan = new ImageSpan(context, SingleGif.getInstance(context).getGif(listImage.get(i)));
//                spannableString.setSpan(imgSpan, 0,
//                        2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
////            }
//
//        }
//
        ImageSpan imgSpan = null;
        imgSpan = new ImageSpan(context, R.drawable.icon_ai);
        spannableString.setSpan(imgSpan, 0,
                2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.textView4.setText(spannableString);

        /**
         * // 第三种方式
         mTextView04 = (TextView) findViewById(R.id.textview_04);
         ImageSpan imgSpan = new ImageSpan(this, R.drawable.apple);
         SpannableString spannableString = new SpannableString("012345");
         spannableString.setSpan(imgSpan, 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
         mTextView04.setText(spannableString);
         */
//        viewHolder.textView4.setText(list.get(position).getContent());

        if (list.get(position).getIconUrl() != null) {
            ImageGlideUtils.loadCircularImage(viewHolder.imageView1, list.get(position).getIconUrl().trim());
        } else {
            ImageGlideUtils.loadCircularImage(viewHolder.imageView1, "");
        }

        //-----------------------评论回复待添加--------------------------------------------------

        //-----------------------评论回复待添加--------------------------------------------------

        return view;
    }

    /**
     * 初始化表情列表staticFacesList
     */
    private List<String> initStaticFaces() {
        List<String> staticFacesList = new ArrayList<>();
        try {
            String[] faces = context.getAssets().list("png");
            //将Assets中的表情名称转为字符串一一添加进staticFacesList
            for (int i = 0; i < faces.length; i++) {
                staticFacesList.add(faces[i]);
            }
            //去掉删除图片
            staticFacesList.remove("emotion_del_normal.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staticFacesList;
    }

    private SpannableStringBuilder getFace(String png) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            /**
             * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
             *😂😂😂😂😃😃😃😃😄😄😄😄🚄🚅🚄🚇🚉🚌🚑🚒⛽🚏🚲🚚🚙🚗🚕🚓🚧🚥🚀 */
            String tempText = "#[" + png + "]#";
            sb.append(tempText);
            sb.setSpan(new ImageSpan(context, SingleGif.getInstance(context).getGif(png)), sb.length()
                    - tempText.length(), sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb;
    }

    private class ViewHolder {
        private TextView textView1, textView2, textView3, textView4;
        private ImageView imageView1;
        private ListView listView;
    }

}
