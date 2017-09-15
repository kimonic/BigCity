package com.bigcity.datamanage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.bmobbean.BlogBmobBean;
import com.bigcity.bean.bmobbean.BlogDetailsBmobBean;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.StringUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * * ===============================================================
 * name:             BlogBmobBeanAddActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/15
 * description：   文章内容添加activity
 * history：
 * *==================================================================
 */

public class BlogBmobBeanAddActivity extends BaseActivity {
    @BindView(R.id.et_act_addblogbmobbean_icon_url)
    EditText etIconUrl;
    @BindView(R.id.et_act_addblogbmobbean_title)
    EditText etTitle;
    @BindView(R.id.et_act_addblogbmobbean_content)
    EditText etContent;
    @BindView(R.id.et_act_addblogbmobbean_image_url1)
    EditText etImageUrl1;
    @BindView(R.id.et_act_addblogbmobbean_image_url2)
    EditText etImageUrl2;
    @BindView(R.id.et_act_addblogbmobbean_image_url3)
    EditText etImageUrl3;
    @BindView(R.id.et_act_addblogbmobbean_image_priview_count)
    EditText etImagePriviewCount;
    @BindView(R.id.et_act_addblogbmobbean_image_reply_count)
    EditText etImageReplyCount;
    @BindView(R.id.et_act_addblogbmobbean_type)
    EditText etType;
    @BindView(R.id.tv_act_addblogbmobbean_commit)
    TextView tvCommit;
    @BindView(R.id.ll_act_addblogbmobbean)
    TextView mtop;
    @BindView(R.id.et_act_addblogbmobbean_author)
    EditText etAuthor;
    @BindView(R.id.et_act_addblogbmobbean_details)
    EditText etDetails;
    @BindView(R.id.et_act_addblogbmobbean_details_image_url)
    EditText etDetailsImageUrl;

    @Override
    public int getLayoutResId() {
        return R.layout.act_addblogbmobbean;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act_addblogbmobbean_commit://提交数据
                commitInfo();
                break;
//                 case R.id.:break;
//                 case R.id.:break;
//                 case R.id.:break;
//                 case R.id.:break;
//                 case R.id.:break;
        }

    }

    /**
     * 提交数据
     */
    private void commitInfo() {
        String id=StringUtils.getSoleId();
        BlogBmobBean bean = new BlogBmobBean();
        bean.setIconUrl(etIconUrl.getText().toString().trim());
        bean.setContent(etContent.getText().toString().trim());
        bean.setTitle(etTitle.getText().toString().trim());

        bean.setImageUrl1(etImageUrl1.getText().toString().trim());
        bean.setImageUrl2(etImageUrl2.getText().toString().trim());
        bean.setImageUrl3(etImageUrl3.getText().toString().trim());

        bean.setReplyCount(etImageReplyCount.getText().toString().trim());
        bean.setPreviewCount(etImagePriviewCount.getText().toString().trim());
        bean.setType(etType.getText().toString().trim());

        bean.setReleaseTimeDate(TimeUtils.getStringDateShortN());
        bean.setReleaseTimeHour(TimeUtils.getCurentTimeN());
        bean.setId(id);


        BlogDetailsBmobBean bean1=new BlogDetailsBmobBean();

        bean1.setId(id);
        bean1.setContent(etDetails.getText().toString().trim());
        bean1.setAuthor(etAuthor.getText().toString().trim());
        bean1.setImageUrl(etDetailsImageUrl.getText().toString().trim());



        bean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //数据保存成功
                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "数据保存成功!");

                } else {
                    //数据保存失败
                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "数据保存失败!");
                }
            }
        });


        bean1.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //数据保存成功
                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "数据保存成功!");

                } else {
                    //数据保存失败
                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "数据保存失败!");
                }
            }
        });

    }

    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {

        //        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtop.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtop.setLayoutParams(params);
    }

    @Override
    public void initListener() {
        tvCommit.setOnClickListener(this);
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
