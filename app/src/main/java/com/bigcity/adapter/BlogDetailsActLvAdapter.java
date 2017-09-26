package com.bigcity.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
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
import com.bigcity.utils.StringUtils;
import com.bigcity.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.lvitem_blogdetailsactadapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView1 = (ImageView) view.findViewById(R.id.iv_blogdetailsadapter_icon);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.tv_blogdetailsadapter_name);
//            viewHolder.textView2 = (TextView) view.findViewById(R.id.iv_blogdetailsadapter_replynum);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.iv_blogdetailsadapter_admirenum);
            viewHolder.textView4 = (TextView) view.findViewById(R.id.tv_blogdetailsadapter_content);
            viewHolder.textView5 = (TextView) view.findViewById(R.id.tv_blogdetailsadapter_admire);
            viewHolder.listView = (ListView) view.findViewById(R.id.lv_blogdetailsadapter_huifu);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView1.setText(list.get(position).getName());
//        viewHolder.textView2.setText(list.get(position).getReplyNum());
        viewHolder.textView3.setText(list.get(position).getAdmire());


        if (listImage == null) {
            listImage = initStaticFaces();
        }
        String content = list.get(position).getContent();
        SpannableString spannableString = new SpannableString(content);

        for (int i = 0; i < listImage.size(); i++) {
            if (content.contains(listImage.get(i))) {
                List<Integer> listTemp = getIndex(listImage.get(i), content);
                Bitmap bitmap = getImageFromAssetsFile(content.substring(listTemp.get(0) - 4, listTemp.get(0) + 16));
                for (int j = 0; j < listTemp.size(); j++) {
                    ImageSpan imgSpan = new ImageSpan(context, bitmap);
                    spannableString.setSpan(imgSpan, (listTemp.get(j) - 6),
                            (listTemp.get(j) + 18), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

        }
        viewHolder.textView4.setText(spannableString);
        viewHolder.textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getUserIdCollection()==null||!list.get(position).getUserIdCollection().contains(list.get(position).getName())) {
                    list.get(position).setAdmire("" + (StringUtils.string2Integer(list.get(position).getAdmire()) + 1));
                    list.get(position).setUserIdCollection(list.get(position).getUserIdCollection() + "," +
                            list.get(position).getName());
                    list.get(position).update(list.get(position).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ToastUtils.showToast(context, context.getResources().getString(R.string.dianzanchenggong));
                                BlogDetailsActLvAdapter.this.notifyDataSetChanged();
                            } else {
                                list.get(position).setUserIdCollection(list.get(position).getUserIdCollection().replace(("," +
                                        list.get(position).getName()),""));
                                ToastUtils.showToast(context, "点赞失败：" + e.getMessage());
                            }
                        }
                    });
                }else {
                    ToastUtils.showToast(context, R.string.niyijingdianguozanla);
                }



            }
        });


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
     * 从Assets中读取图片,根据图片的路径,png/f_static_000.png
     */
    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    private class ViewHolder {
        private TextView textView1, textView2, textView3, textView4, textView5;
        private ImageView imageView1;
        private ListView listView;
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

    /**
     * 获取字符串中包含的所有某个字符串的开始索引集合
     */
    private List<Integer> getIndex(String key, String str) {
        List<Integer> listInt = new ArrayList<>();
        int a = str.indexOf(key);
        listInt.add(a);
        while (a != -1) {
            a = str.indexOf(key, a + 10);
            if (a != -1) {
                listInt.add(a);
            }
        }
        return listInt;
    }


}
