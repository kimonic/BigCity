package com.bigcity.datamanage;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.bmobbean.BlogBmobBean;
import com.bigcity.bean.bmobbean.BlogDetailsBmobBean;
import com.bigcity.bean.bmobbean.DateSearchBmobBean;
import com.bigcity.bean.bmobbean.PageInfoBmobBean;
import com.bigcity.utils.DialogUtils;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.StringUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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


    @BindView(R.id.et_act_addblogbmobbean_author)
    EditText etAuthor;
    @BindView(R.id.et_act_addblogbmobbean_details)
    EditText etDetails;
    @BindView(R.id.et_act_addblogbmobbean_details_image_url)
    EditText etDetailsImageUrl;
    @BindView(R.id.et_act_addblogbmobbean_dateid)
    EditText etDateId;


    @BindView(R.id.tv_act_addblogbmobbean_1)
    TextView tv1;
    @BindView(R.id.tv_act_addblogbmobbean_2)
    TextView tv2;
    @BindView(R.id.tv_act_addblogbmobbean_3)
    TextView tv3;
    @BindView(R.id.tv_act_addblogbmobbean_4)
    TextView tv4;
    @BindView(R.id.tv_act_addblogbmobbean_5)
    TextView tv5;
    @BindView(R.id.tv_act_addblogbmobbean_6)
    TextView tv6;
    @BindView(R.id.tv_act_addblogbmobbean_7)
    TextView tv7;
    @BindView(R.id.tv_act_addblogbmobbean_8)
    TextView tv8;
    @BindView(R.id.tv_act_addblogbmobbean_9)
    TextView tv9;
    @BindView(R.id.tv_act_addblogbmobbean_10)
    TextView tv10;
    @BindView(R.id.tv_act_addblogbmobbean_11)
    TextView tv11;
    @BindView(R.id.tv_act_addblogbmobbean_12)
    TextView tv12;
    @BindView(R.id.tv_act_addblogbmobbean_13)
    TextView tv13;

    private AlertDialog dialog;
    private String id;

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
            case R.id.tv_act_addblogbmobbean_1:
                etIconUrl.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_2:
                etTitle.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_3:
                etContent.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_4:
                etImageUrl1.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_5:
                etImageUrl2.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_6:
                etImageUrl3.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_7:
                etImagePriviewCount.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_8:
                etImageReplyCount.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_9:
                etType.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_10:
                etAuthor.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_11:
                etDetails.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_12:
                etDetailsImageUrl.setText("");
                break;
            case R.id.tv_act_addblogbmobbean_13:
                etDateId.setText("");
                break;
//                 case R.id.tv_act_addblogbmobbean_14:break;
        }

    }

    /**
     * 提交数据
     */
    private void commitInfo() {
        if (dialog == null) {
            dialog = DialogUtils.showProgreessDialog(this, getResources().getString(R.string.zaicidianjijtcbgym));
        } else {
            dialog.show();
        }
        id = StringUtils.getSoleId();
        //---------列表内容添加-------------------
        addListInfo();
        //------------------------------分页用表数据创建--------------------------------------------
        //------------------------------分页用表数据创建--------------------------------------------
    }
    /**添加列表内容*/
    private void addListInfo() {
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

        bean.setDateId(StringUtils.string2Integer(etDateId.getText().toString().trim()));


        bean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //数据保存成功
                    //---------------------保存详情内容-------------------
                    addDetails();
                } else {
                    dialog.dismiss();
                    //数据保存失败
                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "数据保存失败!");
                }
            }
        });
    }
    /**添加详情内容*/
    private void addDetails() {

        BlogDetailsBmobBean bean1 = new BlogDetailsBmobBean();

        bean1.setId(id);
        bean1.setContent(etDetails.getText().toString().trim());
        bean1.setAuthor(etAuthor.getText().toString().trim());
        bean1.setImageUrl(etDetailsImageUrl.getText().toString().trim());

        DateSearchBmobBean bean2 = new DateSearchBmobBean();
        bean2.setHasBlog("1");
        bean2.setName("home");
        bean2.setYear(TimeUtils.getCurrentYearStr());
        bean2.setMonth(TimeUtils.getCurrentMonthStr());
        bean2.setYsdCollection(TimeUtils.getStringDateShortN());
        bean1.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //数据保存成功
                    //------------------添加分页内容-----------
                    addPageInfo();

                } else {
                    dialog.dismiss();
                    //数据保存失败
                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "数据保存失败!");
                }
            }
        });
    }
    /**添加分页内容*/
    private void addPageInfo() {
        BmobQuery<DateSearchBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("name", "home");
        query.addWhereEqualTo("year", TimeUtils.getCurrentYearStr());
        query.addWhereEqualTo("month", TimeUtils.getCurrentMonthStr());
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<DateSearchBmobBean>() {
            @Override
            public void done(List<DateSearchBmobBean> object, BmobException e) {
                if (e == null) {
                    //                    ToastUtils.showToast(RegisterActivity.this, "查询成功：共" + object.size() + "条数据。");
                    if (object.size() == 0) {
                        //无查询数据--新建
                        DateSearchBmobBean bean2 = new DateSearchBmobBean();
                        bean2.setHasBlog("1");
                        bean2.setName("home");
                        bean2.setYear(TimeUtils.getCurrentYearStr());
                        bean2.setMonth(TimeUtils.getCurrentMonthStr());
                        bean2.setYsdCollection(TimeUtils.getStringDateShortN());

                        bean2.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    //数据保存成功
//                                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "保存成功:");

                                    //-----------------------添加日期查找----------------
                                    addDateSearch();
                                } else {
                                    dialog.dismiss();
                                    //数据保存失败
                                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "保存失败");
                                }
                            }
                        });
                    } else {
                        //有查询数据--更新
                        DateSearchBmobBean bean2 = object.get(0);
                        bean2.setHasBlog("1");
                        if (!object.get(0).getYsdCollection().contains(TimeUtils.getStringDateShortN())) {
                            bean2.setYsdCollection(bean2.getYsdCollection() + "," + TimeUtils.getStringDateShortN());
                            bean2.update(bean2.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        //-----------------------添加日期查找----------------
                                        addDateSearch();
                                    } else {
                                        dialog.dismiss();
                                        ToastUtils.showToast(BlogBmobBeanAddActivity.this, "更新失败：" + e.getMessage());
                                    }
                                }
                            });
                        }else {
                            addDateSearch();
                        }
                    }
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
    /**添加日期查找*/
    private void addDateSearch() {
        BmobQuery<PageInfoBmobBean> query1 = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query1.addWhereEqualTo("name", "home");
        query1.addWhereEqualTo("date", TimeUtils.getStringDateShortN());
        query1.addWhereEqualTo("type", etType.getText().toString().trim());
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query1.setLimit(10);
        //执行查询方法
        query1.findObjects(new FindListener<PageInfoBmobBean>() {
            @Override
            public void done(List<PageInfoBmobBean> object, BmobException e) {
                dialog.dismiss();
                if (e == null) {
                    if (object.size() == 0) {
                        //无查询数据
                        PageInfoBmobBean bean2 = new PageInfoBmobBean();
                        bean2.setName("home");
                        bean2.setDate(TimeUtils.getStringDateShortN());
                        bean2.setTotal(1);
                        bean2.setType(etType.getText().toString().trim());

                        bean2.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                dialog.dismiss();
                                if (e == null) {
                                    //数据保存成功
                                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "保存成功:");
                                } else {
                                    //数据保存失败
                                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "保存成功:");
                                }
                            }
                        });

                    } else {
                        //有查询数据
                        PageInfoBmobBean bean2 = object.get(0);
                        bean2.setTotal(1 + bean2.getTotal());
                        bean2.update(bean2.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                dialog.dismiss();
                                if (e == null) {
                                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "更新成功:");
                                } else {
                                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "更新失败：" + e.getMessage());
                                }
                            }
                        });
                    }
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(BlogBmobBeanAddActivity.this, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv1.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        tv1.setLayoutParams(params);

    }

    @Override
    public void initListener() {
        tvCommit.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv10.setOnClickListener(this);
        tv11.setOnClickListener(this);
        tv12.setOnClickListener(this);
//        tv14.setOnClickListener(this);
//        tv13.setOnClickListener(this);
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }


}
