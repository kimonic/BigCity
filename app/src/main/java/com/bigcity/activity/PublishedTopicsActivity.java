package com.bigcity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.bmobbean.BlogBmobBean;
import com.bigcity.bean.bmobbean.BlogDetailsBmobBean;
import com.bigcity.bean.bmobbean.DateSearchBmobBean;
import com.bigcity.bean.bmobbean.LoginInfoBmobBean;
import com.bigcity.bean.bmobbean.PageInfoBmobBean;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.DialogUtils;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.SharedPreferencesUtils;
import com.bigcity.utils.StringUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * * ===============================================================
 * name:             PublishedTopicsActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/20
 * description：  发布贴子的activity
 * history：
 * *==================================================================
 */

public class PublishedTopicsActivity extends BaseActivity {
    @BindView(R.id.mtb_act_publishedtopics)
    MTopBarView mtb;
    @BindView(R.id.tv_act_publishedtopics_huzhuwenda)
    TextView tvHuZhuWenDa;
    @BindView(R.id.tv_act_publishedtopics_kaixinyike)
    TextView tvKaiXinYiKe;
    @BindView(R.id.tv_act_publishedtopics_jiaojiaopengyou)
    TextView tvJiaoJiaoPengYou;
    @BindView(R.id.tv_act_publishedtopics_jignchenggonglve)
    TextView tvJignChengGongLve;
    @BindView(R.id.et_act_publishedtopics_title)
    EditText etTitle;
    @BindView(R.id.et_act_publishedtopics_content)
    EditText etContent;

    private List<TextView> listTv;

    /**
     * 类型
     */
    private int type = 1;

    private AlertDialog dialog;
    /**
     * 贴子的唯一标识
     */
    private String id;

    /**
     * 分类名称
     */
    private String pageName;

    /**
     * 当前日期发布的排序序号
     */
    private int dateId;

    private SharedPreferencesUtils sp;
    /**
     * 登陆信息bean
     */
    private LoginInfoBmobBean bean;

    @Override
    public int getLayoutResId() {
        return R.layout.act_publishedtopics;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act_publishedtopics_huzhuwenda://互助问答
                setBtnStytle(0);
                break;
            case R.id.tv_act_publishedtopics_kaixinyike://开心一刻
                setBtnStytle(1);
                break;
            case R.id.tv_act_publishedtopics_jiaojiaopengyou://交交朋友
                setBtnStytle(2);
                break;
            case R.id.tv_act_publishedtopics_jignchenggonglve://京城攻略
                setBtnStytle(3);
                break;
//            case R.id.: break;
//            case R.id.: break;
        }

    }

    private void setBtnStytle(int positon) {
        type = positon + 1;
        for (int i = 0; i < listTv.size(); i++) {
            if (positon == i) {
                listTv.get(i).setTextColor(Color.WHITE);
                listTv.get(i).setBackgroundResource(R.drawable.xshape_rounrect_grayborder2);
            } else {
                listTv.get(i).setTextColor(Color.BLACK);
                listTv.get(i).setBackgroundResource(R.drawable.xshape_rounrect_grayborder1);
            }
        }
    }

    @Override
    public void initDataFromIntent() {
        listTv = new ArrayList<>();
        listTv.add(tvHuZhuWenDa);
        listTv.add(tvKaiXinYiKe);
        listTv.add(tvJiaoJiaoPengYou);
        listTv.add(tvJignChengGongLve);


        sp = new SharedPreferencesUtils(this, "loginInfo");
        checkLogin();
    }

    @Override
    public void initView() {


//        //        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtb.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtb.setLayoutParams(params);

    }

    @Override
    public void initListener() {
        tvHuZhuWenDa.setOnClickListener(this);
        tvKaiXinYiKe.setOnClickListener(this);
        tvJiaoJiaoPengYou.setOnClickListener(this);
        tvJignChengGongLve.setOnClickListener(this);
        mtb.getLeftTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
        mtb.getRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitInfo();
            }
        });

    }

    /**
     * 提交信息
     */
    private void commitInfo() {
        Log.e("TAG", "commitInfo: ---------------------提交信息1");
        if (dialog == null) {
            dialog = DialogUtils.showProgreessDialog(this, getResources().getString(R.string.zaicidianjijtcbgym));
        } else {
            dialog.show();
        }
        id = StringUtils.getSoleId();

        getPageName();
        if (pageName != null) {//分类不为空
            addDateSearch();
        } else {
            ToastUtils.showToast(this, R.string.qingtianjiazhengquedfl);
        }
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi() {

    }

    /**
     * 添加详情内容
     */
    private void addDetails() {

        Log.e("TAG", "commitInfo: ---------------------提交信息5");

        BlogDetailsBmobBean bean1 = new BlogDetailsBmobBean();

        bean1.setId(id);//贴子的唯一标识
        bean1.setContent(etContent.getText().toString().trim());//完整内容
        bean1.setAuthor(bean.getUserName());

//        bean1.setImageUrl(etDetailsImageUrl.getText().toString().trim());//详情图片连接
        bean1.setImageUrl("");//详情图片连接

        DateSearchBmobBean bean2 = new DateSearchBmobBean();
        bean2.setHasBlog("1");
        bean2.setName(pageName);
        bean2.setYear(TimeUtils.getCurrentYearStr());
        bean2.setMonth(TimeUtils.getCurrentMonthStr());
        bean2.setYsdCollection(TimeUtils.getStringDateShortN());
        bean1.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {

                    //更新用户登陆信息
                    updateLoginInfo();
                    //数据保存成功
                    //------------------添加分页内容-----------
//                    ToastUtils.showToast(PublishedTopicsActivity.this, "数据保存成功!");

                } else {
                    dialog.dismiss();
                    //数据保存失败
                    ToastUtils.showToast(PublishedTopicsActivity.this, "数据保存失败!");
                }
            }
        });
    }

    /**
     * 更新登陆信息
     */
    private void updateLoginInfo() {
        Log.e("TAG", "commitInfo: ---------------------提交信息6");
        int topics = StringUtils.string2Integer(bean.getTotalReply()) + 1;
        bean.setTotalTopics("" + topics);
        bean.setIdCollection(bean.getIdCollection() + "," + id);


        bean.update(bean.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dialog.dismiss();
                if (e == null) {
                    ToastUtils.showToast(PublishedTopicsActivity.this, "更新成功:");
                } else {
                    ToastUtils.showToast(PublishedTopicsActivity.this, "更新失败：" + e.getMessage());
                }
            }

        });
    }


    /**
     * 添加列表内容
     */
    private void addListInfo() {
        Log.e("TAG", "commitInfo: ---------------------提交信息4");

        BlogBmobBean bean = new BlogBmobBean();

//        bean.setIconUrl(etIconUrl.getText().toString().trim());
        bean.setIconUrl("");
        Log.e("TAG", "commitInfo: ---------------------提交信息4?1");

        if (etContent.getText().toString().trim().length()>100){
            bean.setContent(etContent.getText().toString().trim().subSequence(0, 100).toString());//显示的简略内容
        }else {
            bean.setContent(etContent.getText().toString().trim());//显示的简略内容
        }

        bean.setTitle(etTitle.getText().toString().trim());//标题
        Log.e("TAG", "commitInfo: ---------------------提交信息4?2");

//        bean.setImageUrl1(etImageUrl1.getText().toString().trim());//图片1
//        bean.setImageUrl2(etImageUrl2.getText().toString().trim());//图片2
//        bean.setImageUrl3(etImageUrl3.getText().toString().trim());//图片3
        bean.setImageUrl1("");//图片1
        bean.setImageUrl2("");//图片2
        bean.setImageUrl3("");//图片3

//        bean.setReplyCount(etImageReplyCount.getText().toString().trim());//回复数
//        bean.setPreviewCount(etImagePriviewCount.getText().toString().trim());//浏览数
        bean.setReplyCount("0");//回复数
        bean.setPreviewCount("0");//浏览数


        bean.setType("" + type);//类型

        bean.setReleaseTimeDate(TimeUtils.getStringDateShortN());
        bean.setReleaseTimeHour(TimeUtils.getCurentTimeN());
        bean.setId(id);

        bean.setDateId(dateId);
        Log.e("TAG", "commitInfo: ---------------------提交信息41");


        bean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //数据保存成功
                    //---------------------保存详情内容-------------------
                    Log.e("TAG", "commitInfo: ---------------------提交信息42");
                    addDetails();
                } else {
                    dialog.dismiss();
                    //数据保存失败
                    Log.e("TAG", "commitInfo: ---------------------提交信息43");
                    ToastUtils.showToast(PublishedTopicsActivity.this, "数据保存失败!");
                }
            }
        });
    }


    /**
     * 添加分页内容
     */
    private void addPageInfo() {
        Log.e("TAG", "commitInfo: ---------------------提交信息3");
        BmobQuery<DateSearchBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("name", pageName);
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
                        bean2.setName(pageName);
                        bean2.setYear(TimeUtils.getCurrentYearStr());
                        bean2.setMonth(TimeUtils.getCurrentMonthStr());
                        bean2.setYsdCollection(TimeUtils.getStringDateShortN());

                        bean2.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    //数据保存成功
//                                    ToastUtils.showToast(PublishedTopicsActivity.this, "保存成功:");

                                    //-----------------------添加日期查找----------------
                                    addListInfo();

                                } else {
                                    dialog.dismiss();
                                    //数据保存失败
                                    ToastUtils.showToast(PublishedTopicsActivity.this, "保存失败");
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
                                        addListInfo();
                                    } else {
                                        dialog.dismiss();
                                        ToastUtils.showToast(PublishedTopicsActivity.this, "更新失败：" + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            addListInfo();
                        }
                    }
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(PublishedTopicsActivity.this, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 添加日期查找
     */
    private void addDateSearch() {
        Log.e("TAG", "commitInfo: ---------------------提交信息2");

        BmobQuery<PageInfoBmobBean> query1 = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query1.addWhereEqualTo("name", pageName);
        query1.addWhereEqualTo("date", TimeUtils.getStringDateShortN());
        query1.addWhereEqualTo("type", "" + type);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query1.setLimit(10);
        //执行查询方法
        query1.findObjects(new FindListener<PageInfoBmobBean>() {
            @Override
            public void done(List<PageInfoBmobBean> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        //无查询数据
                        PageInfoBmobBean bean2 = new PageInfoBmobBean();
                        bean2.setName(pageName);
                        bean2.setDate(TimeUtils.getStringDateShortN());
                        bean2.setTotal(1);
                        bean2.setType("" + type);
                        dateId = 1;

                        bean2.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                dialog.dismiss();
                                if (e == null) {
                                    //数据保存成功
                                    addPageInfo();
//                                    ToastUtils.showToast(PublishedTopicsActivity.this, "保存成功:");
                                } else {
                                    //数据保存失败
                                    dialog.dismiss();
                                    ToastUtils.showToast(PublishedTopicsActivity.this, "保存失败:");
                                }
                            }
                        });

                    } else {
                        //有查询数据
                        PageInfoBmobBean bean2 = object.get(0);
                        bean2.setTotal(1 + bean2.getTotal());
                        dateId = bean2.getTotal();
                        bean2.update(bean2.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    addPageInfo();
//                                    ToastUtils.showToast(PublishedTopicsActivity.this, "更新成功:");
                                } else {
                                    dialog.dismiss();
                                    ToastUtils.showToast(PublishedTopicsActivity.this, "更新失败：" + e.getMessage());
                                }
                            }
                        });
                    }
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(PublishedTopicsActivity.this, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


    /**
     * 获取当前列表的名称
     */
    private void getPageName() {
        switch (type) {
            case 1:
                pageName = "huzhuwenda";
                break;
            case 2:
                pageName = "kaixinyike";
                break;
            case 3:
                pageName = "jiaojiaopengyou";
                break;
            case 4:
                pageName = "jignchenggonglve";
                break;
        }
    }

    /**
     * 检测是否已登陆
     */
    private void checkLogin() {
        LoginInfoBmobBean tempBean;
        String userInfo = sp.getString("loginInfo", "");
        String date = sp.getString("date", "");
        if (!userInfo.equals("")) {
            Gson gson = new Gson();
            tempBean = gson.fromJson(userInfo, LoginInfoBmobBean.class);
//            if (!date.equals(TimeUtils.getStringDateShortN())) {
            BmobQuery<LoginInfoBmobBean> query = new BmobQuery<>();
            //查询playerName叫“比目”的数据
            query.addWhereEqualTo("userName", tempBean.getUserName());
            query.addWhereEqualTo("password", tempBean.getPassword());
            //返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(10);
            //执行查询方法
            query.findObjects(new FindListener<LoginInfoBmobBean>() {
                @Override
                public void done(List<LoginInfoBmobBean> object, BmobException e) {
                    if (e == null) {
                        if (object.size() == 0) {
                            //无查询数据
                            ToastUtils.showToast(PublishedTopicsActivity.this, R.string.qingxiandenglu);
                            Intent intent = new Intent(PublishedTopicsActivity.this, LoginActivityC.class);
                            startActivityForResult(intent, 1);
                        } else {
                            //有查询数据
                            bean = object.get(0);
                        }
                    } else {
                        ToastUtils.showToast(PublishedTopicsActivity.this, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
//            }
        } else {
            //无查询数据
            ToastUtils.showToast(PublishedTopicsActivity.this, R.string.qingxiandenglu);
            Intent intent = new Intent(PublishedTopicsActivity.this, LoginActivityC.class);
            startActivityForResult(intent, 1);
//            openActivity(LoginActivityC.class);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {//继续检查结果,知道含有登陆信息
            checkLogin();
        }
    }
}
