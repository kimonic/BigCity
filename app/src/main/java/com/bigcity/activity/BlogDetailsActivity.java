package com.bigcity.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.adapter.BlogDetailsActLvAdapter;
import com.bigcity.adapter.FaceGVAdapter;
import com.bigcity.adapter.FaceVPAdapter;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.bmobbean.BlogDetailsBmobBean;
import com.bigcity.bean.bmobbean.CommentBmobBean;
import com.bigcity.bean.bmobbean.LoginInfoBmobBean;
import com.bigcity.gif.SingleGif;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.DialogUtils;
import com.bigcity.utils.ImageGlideUtils;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.SharedPreferencesUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * * ===============================================================
 * name:             BlogDetailsActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/22
 * description： 帖子详情activity
 * history：
 * *==================================================================
 */

public class BlogDetailsActivity extends BaseActivity {
    @BindView(R.id.mtb_act_blogdetails)
    MTopBarView mtb;
    @BindView(R.id.tv_act_blogdetails_title)
    TextView tvTitle;
    @BindView(R.id.tv_act_blogdetails_time)
    TextView tvTime;
    @BindView(R.id.tv_act_blogdetails_author)
    TextView tvAuthor;
    @BindView(R.id.tv_act_blogdetails_content)
    TextView tvContent;
    @BindView(R.id.iv_act_blogdetails_image1)
    ImageView ivImage1;
    @BindView(R.id.iv_act_blogdetails_image2)
    ImageView ivImage2;
    @BindView(R.id.iv_act_blogdetails_image3)
    ImageView ivImage3;
    @BindView(R.id.lv_act_blogdetails)
    ListView lv;
    @BindView(R.id.tv_act_blogdetails_hint)
    TextView tvHint;


    @BindView(R.id.et_act_blogdetails_reply)
    EditText etReply1;
    @BindView(R.id.tv_act_blogdetails_face)
    TextView tvFace1;
    @BindView(R.id.tv_act_blogdetails_send)
    TextView tvSend1;

    private EditText etReply;
    private TextView tvFace;
    private TextView tvSend;

    private ViewPager faceViewpager;
    private LinearLayout faceBottom;


    /**
     * 唯一标识id
     */
    private String id;
    private String title;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private BlogDetailsBmobBean bean;

    private BlogDetailsActLvAdapter adapter;

    private List<CommentBmobBean> list;

    private AlertDialog dialog;
    private int faceCount = 0;

    private SharedPreferencesUtils sp;
    private LoginInfoBmobBean loginInfoBmobBean;
    //-------------------表情相关---------------------------------------------------------------------------
    private List<View> views = new ArrayList<>();
    private List<String> staticFacesList;
    // 7列3行
    private int columns = 6;
    private int rows = 4;
    private AlertDialog dialogFace;
    //-------------------表情相关---------------------------------------------------------------------------

    @Override
    public int getLayoutResId() {
        return R.layout.act_blogdetails;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act_blogdetails_face://点击选择表情
                etReply.setText(changeStr(etReply1.getText().toString()));
                etReply.setSelection(etReply.getText().length());
                dialogFace.show();
                break;
            case R.id.tv_facesel_face://点击选择表情
                if (faceCount % 2 == 0) {
                    dialogFace.show();
                } else {
                    etReply1.setText(changeStr(etReply.getText().toString()));
                    etReply1.setSelection(etReply1.getText().length());
                    dialogFace.dismiss();


                }
                faceCount++;
                break;
            case R.id.tv_facesel_send://点击发送评论
                if (!etReply.getText().toString().equals("")) {
                    etReply1.setText(changeStr(etReply.getText().toString()));
                    etReply1.setSelection(etReply1.getText().length());
                    checkLogin();
                }
                break;
            case R.id.tv_act_blogdetails_send://点击发送评论
                if (!etReply1.getText().toString().equals("")) {
                    checkLogin();
                }


                break;
//                 case R.id.:break;
//                 case R.id.:break;
//                 case R.id.:break;
//                 case R.id.:break;
        }

    }

    private void saveReply() {
        CommentBmobBean bean1 = new CommentBmobBean();
        bean1.setName(loginInfoBmobBean.getUserName());
        bean1.setReplyNum("0");
        bean1.setTime(TimeUtils.getNowDateAll());
        bean1.setId(id);
        bean1.setIconUrl(loginInfoBmobBean.getIconUrl());
        bean1.setAdmire("0");
        bean1.setAddComment("");
        bean1.setContent(etReply.getText().toString().trim());
        bean1.setOrderTime(TimeUtils.getOrderTime());

        bean1.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //数据保存成功
                    etReply.setText("");
                    etReply1.setText("");
                    dialogFace.dismiss();
                    initDataFromInternet();
                } else {
                    //数据保存失败
                    ToastUtils.showToast(BlogDetailsActivity.this, R.string.shujubaocunshibai);

                }
            }
        });
    }

    private void checkLogin() {
        sp = new SharedPreferencesUtils(this, "loginInfo");
        String userInfo = sp.getString("loginInfo", "");
        String date = sp.getString("date", "");

        if (!userInfo.equals("")) {
            final Gson gson = new Gson();
            loginInfoBmobBean = gson.fromJson(userInfo, LoginInfoBmobBean.class);

            if (!date.equals(TimeUtils.getStringDateShortN())) {

                BmobQuery<LoginInfoBmobBean> query = new BmobQuery<>();
                //查询playerName叫“比目”的数据
                query.addWhereEqualTo("userName", loginInfoBmobBean.getUserName());
                query.addWhereEqualTo("password", loginInfoBmobBean.getPassword());
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(10);
                //执行查询方法
                query.findObjects(new FindListener<LoginInfoBmobBean>() {
                    @Override
                    public void done(List<LoginInfoBmobBean> object, BmobException e) {
                        if (e == null) {
                            if (object.size() == 0) {
                                //无查询数据
                                ToastUtils.showToast(BlogDetailsActivity.this, R.string.qingxiandenglu);
                                openActivity(LoginActivity.class);
                            } else {
                                //有查询数据
                                loginInfoBmobBean = object.get(0);
                                sp.put("date", TimeUtils.getStringDateShortN());
                                sp.put("loginInfo", gson.toJson(loginInfoBmobBean, LoginInfoBmobBean.class));

                                saveReply();
                            }
                        } else {
                            ToastUtils.showToast(BlogDetailsActivity.this, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            } else {
                saveReply();
            }

        } else {
            //无查询数据
            ToastUtils.showToast(BlogDetailsActivity.this, R.string.qingxiandenglu);
            Intent intent = new Intent(BlogDetailsActivity.this, LoginActivityC.class);
            startActivityForResult(intent, 1);
//            openActivity(LoginActivityC.class);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            checkLogin();
        }
    }


    @Override
    public void initDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            title = intent.getStringExtra("title");
            imageUrl1 = intent.getStringExtra("imageurl1");
            imageUrl2 = intent.getStringExtra("imageurl2");
            imageUrl3 = intent.getStringExtra("imageurl3");
        } else {
            id = "";
            title = "";
        }


    }

    @Override
    public void initView() {
        //        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtb.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtb.setLayoutParams(params);


        //--------------------------------------表情相关------------------------------------------------

        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.dialog_facesel, null);

        faceViewpager = (ViewPager) view.findViewById(R.id.viewpager_chatact1);
        faceBottom = (LinearLayout) view.findViewById(R.id.linearlayout_chatact1);
        etReply = (EditText) view.findViewById(R.id.et_facesel_reply);
        tvFace = (TextView) view.findViewById(R.id.tv_facesel_face);
        tvSend = (TextView) view.findViewById(R.id.tv_facesel_send);

        dialogFace = DialogUtils.showTimeSel(this, view);
        dialogFace.dismiss();


        initStaticFaces();//初始化表情数据源
        initViewPager();//初始化表情展示
        list = new ArrayList<>();


        //--------------------------------------表情相关------------------------------------------------


    }

    @Override
    public void initListener() {

        mtb.getLeftTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
        tvFace.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        tvFace1.setOnClickListener(this);
        tvSend1.setOnClickListener(this);
        faceViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < faceBottom.getChildCount(); i++) {
                    faceBottom.getChildAt(i).setSelected(false);
                }
                faceBottom.getChildAt(position).setSelected(true);
            }
        });


    }

    @Override
    public void initDataFromInternet() {
        showDialog();
        BmobQuery<BlogDetailsBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("id", id);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<BlogDetailsBmobBean>() {
            @Override
            public void done(List<BlogDetailsBmobBean> object, BmobException e) {

                if (e == null) {
                    if (object.size() == 0) {
                        //无查询数据
                        dialog.dismiss();
                        ToastUtils.showToast(BlogDetailsActivity.this, R.string.meiyouxiangxineirong);
                    } else {
                        //有查询数据
                        bean = object.get(0);
                        loadReplyData();
                    }
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(BlogDetailsActivity.this, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    /**
     * 查询评论数据
     */
    private void loadReplyData() {
        BmobQuery<CommentBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("id", id);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(100);
        //执行查询方法
        query.findObjects(new FindListener<CommentBmobBean>() {
            @Override
            public void done(List<CommentBmobBean> object, BmobException e) {
                dialog.dismiss();
                if (e == null) {
                    if (object.size() != 0) {
                        //有查询数据
                        list.clear();
                        list.addAll(object);
                    }
                    loadInternetDataToUi();
                } else {
                    ToastUtils.showToast(BlogDetailsActivity.this, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void loadInternetDataToUi() {
        if (bean != null) {
            tvTitle.setText(title);
            tvAuthor.setText(bean.getAuthor());
            tvContent.setText(("        " + bean.getContent()));
            tvTime.setText(bean.getPublishTime());
            loadImage(imageUrl1, 1);
            loadImage(imageUrl2, 2);
            loadImage(imageUrl3, 3);
        }

        if (list.size() > 0) {//有评论数据
            orderList();
            if (adapter == null) {
                adapter = new BlogDetailsActLvAdapter(this, list);
                lv.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            lv.setVisibility(View.VISIBLE);
            tvHint.setVisibility(View.GONE);
        } else {
            tvHint.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
        }

    }

    private void orderList() {

        Collections.sort(list, new Comparator<CommentBmobBean>() {
            @Override
            public int compare(CommentBmobBean o1, CommentBmobBean o2) {
                if (o2.getOrderTime() - o1.getOrderTime() > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }

    private void loadImage(String url, int position) {

        if (url == null || url.equals("")) {
            switch (position) {
                case 1:
                    ivImage1.setVisibility(View.GONE);
                    break;
                case 2:
                    ivImage2.setVisibility(View.GONE);
                    break;
                case 3:
                    ivImage3.setVisibility(View.GONE);
                    break;
            }
        } else {
            switch (position) {
                case 1:
                    ImageGlideUtils.loadImage(ivImage1, url);
                    break;
                case 2:
                    ImageGlideUtils.loadImage(ivImage2, url);
                    break;
                case 3:
                    ImageGlideUtils.loadImage(ivImage3, url);
                    break;
            }
        }
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = DialogUtils.showProgreessDialog(this, getResources().getString(R.string.zaicidianjijtcbgym));
        } else {
            dialog.show();
        }

    }

    /**
     * 带表情的字符串转化
     */

    private SpannableString changeStr(String string) {
        SpannableString spannableString = new SpannableString(string);

        for (int i = 0; i < staticFacesList.size(); i++) {
            if (string.contains(staticFacesList.get(i))) {
                List<Integer> listTemp = getIndex(staticFacesList.get(i), string);
                Bitmap bitmap = getImageFromAssetsFile(string.substring(listTemp.get(0) - 4, listTemp.get(0) + 16));
                for (int j = 0; j < listTemp.size(); j++) {
                    ImageSpan imgSpan = new ImageSpan(this, bitmap);
                    spannableString.setSpan(imgSpan, (listTemp.get(j) - 6),
                            (listTemp.get(j) + 18), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

        }
        return spannableString;
    }


    /**
     * 从Assets中读取图片,根据图片的路径,png/f_static_000.png
     */
    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
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
    //--------------------------------------表情相关------------------------------------------------

    /**
     * 初始化viewpager---------------------------------------------------------------------------------------------
     */
    private void initViewPager() {
        // 获取页数
        int count = getPagerCount();
        for (int i = 0; i < count; i++) {
            views.add(viewPagerItem(i));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            faceBottom.addView(dotsItem(i), params);
        }
        FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
        faceViewpager.setAdapter(mVpAdapter);

        if (faceBottom.getChildAt(0) != null) {
            faceBottom.getChildAt(0).setSelected(true);

        }
    }


    /**
     * 根据表情数量以及GridView设置的行数和列数计算Pager数量
     *
     * @return int
     */
    private int getPagerCount() {
        int count = staticFacesList.size();
        return count % (columns * rows - 1) == 0 ? count / (columns * rows - 1)
                : count / (columns * rows - 1) + 1;
    }


    private View viewPagerItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.face_gridview, null);//表情布局
        GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
//        /**
//         * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
//         * */
        List<String> subList = new ArrayList<>();
        subList.addAll(staticFacesList
                .subList(position * (columns * rows - 1),
                        (columns * rows - 1) * (position + 1) > staticFacesList
                                .size() ? staticFacesList.size() : (columns
                                * rows - 1)
                                * (position + 1)));
//         * 末尾添加删除图标
        subList.add("emotion_del_normal.png");
        FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, this);
        gridview.setAdapter(mGvAdapter);
        gridview.setNumColumns(columns);
        // 单击表情执行的操作
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String png = ((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();

                    if (!png.contains("emotion_del_normal")) {// 如果不是删除图标
                        insert(getFace(png));
                    } else {
                        delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return gridview;
    }


    /**
     * 删除图标执行事件
     * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
     */
    private void delete() {
        if (etReply.getText().length() != 0) {
            int iCursorEnd = Selection.getSelectionEnd(etReply.getText());
            int iCursorStart = Selection.getSelectionStart(etReply.getText());
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(iCursorEnd)) {
                        String st = "#[png/f_static_000.png]#";
                        ( etReply.getText()).delete(
                                iCursorEnd - st.length(), iCursorEnd);
                    } else {
                        ( etReply.getText()).delete(iCursorEnd - 1,
                                iCursorEnd);
                    }
                } else {
                    ( etReply.getText()).delete(iCursorStart,
                            iCursorEnd);
                }
            }
        }
    }

    /**
     * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则将删除整个tempText
     **/
    private boolean isDeletePng(int cursor) {
        String st = "#[png/f_static_000.png]#";
        String content = etReply.getText().toString().substring(0, cursor);
        if (content.length() >= st.length()) {
            String checkStr = content.substring(content.length() - st.length(),
                    content.length());
            String regex = "(\\#\\[png/f_static_)\\d{3}(.png\\]\\#)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(checkStr);
            return m.matches();
        }
        return false;
    }

    private SpannableStringBuilder getFace(String png) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
//            /**
//             * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
//             *😂😂😂😂😃😃😃😃😄😄😄😄🚄🚅🚄🚇🚉🚌🚑🚒⛽🚏🚲🚚🚙🚗🚕🚓🚧🚥🚀 */
            String tempText = "#[" + png + "]#";
            sb.append(tempText);

            sb.setSpan(
                    new ImageSpan(BlogDetailsActivity.this, SingleGif.getInstance(this).getGif(png)), sb.length()
                            - tempText.length(), sb.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb;
    }

    /**
     * 向输入框里添加表情
     */
    private void insert(CharSequence text) {
        int iCursorStart = Selection.getSelectionStart((etReply.getText()));
        int iCursorEnd = Selection.getSelectionEnd((etReply.getText()));
        if (iCursorStart != iCursorEnd) {
            etReply.getText().replace(iCursorStart, iCursorEnd, "");
//            ((Editable) edit.getText()).replace(iCursorStart, iCursorEnd, "");
        }
        int iCursor = Selection.getSelectionEnd((etReply.getText()));
        etReply.getText().insert(iCursor, text);
//        ((Editable) edit.getText()).insert(iCursor, text);
    }

    private ImageView dotsItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dot_image, null);
        ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
        iv.setId(position);
        return iv;
    }

    /**
     * 初始化表情列表staticFacesList
     */
    private void initStaticFaces() {
        try {
            staticFacesList = new ArrayList<>();
            String[] faces = getAssets().list("png");
            //将Assets中的表情名称转为字符串一一添加进staticFacesList
            Collections.addAll(staticFacesList, faces);
            //去掉删除图片
            staticFacesList.remove("emotion_del_normal.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //--------------------------------------表情相关------------------------------------------------
}
