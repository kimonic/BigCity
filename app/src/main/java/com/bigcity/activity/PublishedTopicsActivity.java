package com.bigcity.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.bmobbean.BlogBmobBean;
import com.bigcity.bean.bmobbean.BlogDetailsBmobBean;
import com.bigcity.bean.bmobbean.DateSearchBmobBean;
import com.bigcity.bean.bmobbean.LoginInfoBmobBean;
import com.bigcity.bean.bmobbean.PageInfoBmobBean;
import com.bigcity.bean.bmobbean.TotalItemNumBmobBean;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.BitmapHandleThread;
import com.bigcity.utils.BitmapUtils;
import com.bigcity.utils.DialogUtils;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.SharedPreferencesUtils;
import com.bigcity.utils.StringUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteBatchListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

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
    @BindView(R.id.tv_act_publishedtopics_image1)
    TextView tvImage1;
    @BindView(R.id.iv_act_publishedtopics_image_url1)
    ImageView ivImageUrl1;
    @BindView(R.id.tv_act_publishedtopics_image2)
    TextView tvImage2;
    @BindView(R.id.iv_act_publishedtopics_image_url2)
    ImageView ivImageUrl2;
    @BindView(R.id.tv_act_publishedtopics_image3)
    TextView tvImage3;
    @BindView(R.id.iv_act_publishedtopics_image_url3)
    ImageView ivImageUrl3;

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

    private String imageUrl1, imageUrl2, imageUrl3;
    private String imageBmobUrl1 = "", imageBmobUrl2 = "", imageBmobUrl3 = "";
    private File file1, file2, file3;
    private boolean success1 = false, success2 = false, success3 = false;

    /**
     * 压缩后的图片文件集合
     */
    private List<File> listFile;

    /**
     * 已上传图片的集合
     */
    private List<String> listBmobUrl;
    /**
     * 当前类型下item的总数
     */
    private int itemTotal = 0;

    /**
     * 确认图片压缩已完成
     */
    private boolean compressOver1 = true, compressOver2 = true, compressOver3 = true;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    commitInfo();
                    break;
            }
        }
    };

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
            case R.id.tv_act_publishedtopics_image1://选择图片1
                selectImage(7);
                break;
            case R.id.tv_act_publishedtopics_image2://选择图片2
                selectImage(8);
                break;
            case R.id.tv_act_publishedtopics_image3://选择图片3
                selectImage(9);
                break;
            case R.id.iv_act_publishedtopics_image_url1:
                Intent intent = new Intent(this, ShowImageActivity.class);
                intent.putExtra("url", imageUrl1);
                startActivity(intent);
                break;
            case R.id.iv_act_publishedtopics_image_url2:
                Intent intent2 = new Intent(this, ShowImageActivity.class);
                intent2.putExtra("url", imageUrl2);
                startActivity(intent2);
                break;
            case R.id.iv_act_publishedtopics_image_url3:
                Intent intent3 = new Intent(this, ShowImageActivity.class);
                intent3.putExtra("url", imageUrl3);
                startActivity(intent3);
                break;
        }

    }

    /**
     * 选择图片
     */
    private void selectImage(int requestCode) {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {//继续检查结果,知道含有登陆信息
            checkLogin();
        } else if (data != null) {

            //遗留问题:小米4手机中,一旦点击图片则返回本activity,无法点击相册中的确定
            String temp = data.getData().toString();

            String   hhh=temp;//--------------------------------------------

            int density = ScreenSizeUtils.getDensity(this);
            Bitmap bitmap = BitmapUtils.getBitmap(this, data.getData(), 100*density, 100*density);
            Log.e("TAG", "onActivityResult: -----------位图大小----------"+bitmap.getByteCount()/1024f/1024);

            hhh=hhh+"-------"+bitmap.toString();
            tvImage1.setText(hhh);
            switch (requestCode) {
                case 7:
                    handleBitmapFromThread(bitmap, 1);//将位图压缩到100左右
//                        File file = BitmapUtils.compressImage(bitmap);//生成文件
                    ivImageUrl1.setImageBitmap(bitmap);
//                    ivImageUrl1.setImageBitmap(BitmapUtils.getReduceBitmap(bitmap, 100 * density, 100 * density));
                    imageUrl1 = temp;
                    break;
                case 8:
                    ivImageUrl2.setImageBitmap(bitmap);

                    handleBitmapFromThread(bitmap, 2);
//                    ivImageUrl2.setImageBitmap(BitmapUtils.getReduceBitmap(bitmap, 100 * density, 100 * density));
                    imageUrl2 = temp;
//                        file2 = file;
                    break;
                case 9:
//                    ivImageUrl3.setImageBitmap(BitmapUtils.getReduceBitmap(bitmap, 100 * density, 100 * density));
                    ivImageUrl3.setImageBitmap(bitmap);

                    handleBitmapFromThread(bitmap, 3);
                    imageUrl3 = temp;
//                        file3 = file;
                    break;
            }


        }


    }

    /**
     * 开启线程处理图片压缩
     */
    private void handleBitmapFromThread(Bitmap bitmap, int position) {
        switch (position) {
            case 1:
                compressOver1 = false;
                break;
            case 2:
                compressOver2 = false;
                break;
            case 3:
                compressOver3 = false;
                break;
        }
        BitmapHandleThread thread3 = new BitmapHandleThread(bitmap, position);
        thread3.setListener(new BitmapHandleThread.CompressListener() {
            @Override
            public void result(File file, int position) {
                listFile.add(file);
                switch (position) {
                    case 1:
                        file1 = file;
                        compressOver1 = true;
                        break;
                    case 2:
                        file2 = file;
                        compressOver2 = true;
                        break;
                    case 3:
                        file3 = file;
                        compressOver3 = true;
                        break;
                }
            }
        });
        new Thread(thread3).start();
    }


    /**
     * 上传图片
     *
     * @param file     要上传的文件
     * @param position 上传文件的位置
     */
    private void uploadingImage(File file, final int position) {
        if (file != null) {
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址,上传文件成功
                        listBmobUrl.add(bmobFile.getFileUrl());//保存已上传图片的地址,二次提交时删除已上传图片
                        switch (position) {
                            case 1:
                                success1 = true;
                                imageBmobUrl1 = bmobFile.getFileUrl();
                                break;
                            case 2:
                                success2 = true;
                                imageBmobUrl2 = bmobFile.getFileUrl();
                                break;
                            case 3:
                                success3 = true;
                                imageBmobUrl3 = bmobFile.getFileUrl();
                                break;
                        }
                        uploadingProgress();
                    } else {
                        ToastUtils.showToast(PublishedTopicsActivity.this, "上传文件失败：" + e.getMessage());
                        Log.e("TAG", "done: ---------------------" + e.getMessage());
                    }
                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        } else {
            uploadingProgress();
        }

    }

    /**
     * 一张接一张上传图片
     */
    private void uploadingProgress() {
        if (success1) {
            success1 = false;
            if (file2 != null) {
                uploadingImage(file2, 2);
            } else {
                success2 = true;
                uploadingImage(file3, 3);
            }
        } else if (success2) {
            success2 = false;
            if (file3 != null) {
                uploadingImage(file3, 3);
            } else {
                addDateSearch();
            }
        } else if (success3) {
            success3 = false;
            addDateSearch();
        }

    }

    /**
     * 批量删除文件
     */
    private void deleImage() {
        //此url必须是上传文件成功之后通过bmobFile.getUrl()方法获取的。
        String[] urls = new String[listBmobUrl.size()];
        if (listBmobUrl.size() > 0) {
            for (int i = 0; i < listBmobUrl.size(); i++) {
                urls[i] = listBmobUrl.get(i);
            }
            BmobFile.deleteBatch(urls, new DeleteBatchListener() {
                @Override
                public void done(String[] failUrls, BmobException e) {
                    if (e == null) {
                        ToastUtils.showToast(PublishedTopicsActivity.this, "全部删除成功");
                    } else {
                        if (failUrls != null) {
                            ToastUtils.showToast(PublishedTopicsActivity.this, "删除失败个数：" + failUrls.length + "," + e.toString());
                        } else {
                            ToastUtils.showToast(PublishedTopicsActivity.this, "全部文件删除失败：" + e.getErrorCode() + "," + e.toString());
                        }
                    }
                }
            });
        }
    }


    @Override
    public void initDataFromIntent() {
        listTv = new ArrayList<>();
        listTv.add(tvHuZhuWenDa);
        listTv.add(tvKaiXinYiKe);
        listTv.add(tvJiaoJiaoPengYou);
        listTv.add(tvJignChengGongLve);

        listFile = new ArrayList<>();
        listBmobUrl = new ArrayList<>();

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

        tvImage1.setOnClickListener(this);
        tvImage2.setOnClickListener(this);
        tvImage3.setOnClickListener(this);

        ivImageUrl1.setOnClickListener(this);
        ivImageUrl2.setOnClickListener(this);
        ivImageUrl3.setOnClickListener(this);

        mtb.getLeftTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
        mtb.getRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkContent()) {
                    if (dialog == null) {
                        dialog = DialogUtils.showProgreessDialog(PublishedTopicsActivity.this, getResources().getString(R.string.zaicidianjijtcbgym));
                    } else {
                        dialog.show();
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            while (!(compressOver1 && compressOver2 && compressOver3)) {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            Message msg = Message.obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }.start();
                }
            }
        });
    }

    /**
     * 检测发布的内容
     */
    private boolean checkContent() {
        if (etTitle.getText().toString().trim().equals("")) {
            ToastUtils.showToast(PublishedTopicsActivity.this, R.string.biaotibunengweikong);
            return false;
        }
        if (etContent.getText().toString().trim().equals("")) {
            ToastUtils.showToast(PublishedTopicsActivity.this, R.string.wenzhangneirongbunengweikong);
            return false;
        }
        return true;
    }

    /**
     * 提交信息
     */
    private void commitInfo() {
        Log.e("TAG", "commitInfo: ---------------------提交信息1");
//        if (dialog == null) {
//            dialog = DialogUtils.showProgreessDialog(this, getResources().getString(R.string.zaicidianjijtcbgym));
//        } else {
//            dialog.show();
//        }
        id = StringUtils.getSoleId();

        getPageName();
        if (pageName != null) {//分类不为空
            queryTotal();
        } else {
            ToastUtils.showToast(this, R.string.qingtianjiazhengquedfl);
        }
    }


    /**
     * 查询当前类下的总item数
     */

    private void queryTotal() {
        Log.e("TAG", "commitInfo: ---------------------提交信息2");
        BmobQuery<TotalItemNumBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("type", "" + type);
        query.addWhereEqualTo("name", pageName);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<TotalItemNumBmobBean>() {
            @Override
            public void done(List<TotalItemNumBmobBean> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        //无查询数据
                        TotalItemNumBmobBean totalItemNumBmobBean = new TotalItemNumBmobBean();
                        totalItemNumBmobBean.setName(pageName);
                        totalItemNumBmobBean.setType("" + type);
                        totalItemNumBmobBean.setTotal(0);
                        totalItemNumBmobBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    //数据保存成功
                                    itemTotal = 0;


                                    deleImage();//删除之前上传的图片
                                    if (file1 == null) {
                                        success1 = true;
                                    }
                                    if (file2 == null) {
                                        success2 = true;
                                    }
                                    if (file3 == null) {
                                        success3 = true;
                                    }


                                    uploadingImage(file1, 1);
                                } else {
                                    //数据保存失败
                                    dialog.dismiss();
                                    ToastUtils.showToast(PublishedTopicsActivity.this, R.string.shujubaocunshibai);
                                }
                            }
                        });
                    } else {
                        //有查询数据
                        TotalItemNumBmobBean totalItemNumBmobBean = object.get(0);
                        itemTotal = totalItemNumBmobBean.getTotal();
                        totalItemNumBmobBean.setTotal(itemTotal + 1);
                        totalItemNumBmobBean.update(totalItemNumBmobBean.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    deleImage();//删除之前上传的图片
                                    if (file1 == null) {
                                        success1 = true;
                                    }
                                    if (file2 == null) {
                                        success2 = true;
                                    }
                                    if (file3 == null) {
                                        success3 = true;
                                    }
                                    uploadingImage(file1, 1);
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
        BlogDetailsBmobBean bean1 = new BlogDetailsBmobBean();
        bean1.setId(id);//贴子的唯一标识
        bean1.setContent(etContent.getText().toString().trim());//完整内容
        bean1.setAuthor(bean.getUserName());
        bean1.setPublishTime(TimeUtils.getNowDateAll());
//        bean1.setImageUrl(etDetailsImageUrl.getText().toString().trim());//详情图片连接
        bean1.setImageUrl("");//详情图片连接
//        DateSearchBmobBean bean2 = new DateSearchBmobBean();
//        bean2.setHasBlog("1");
//        bean2.setName(pageName);
//        bean2.setYear(TimeUtils.getCurrentYearStr());
//        bean2.setMonth(TimeUtils.getCurrentMonthStr());
//        bean2.setYsdCollection(TimeUtils.getStringDateShortN());

        bean1.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {

                    //更新用户登陆信息
                    //------------------添加分页内容-----------
                    updateLoginInfo();
                    //数据保存成功
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
        int topics = StringUtils.string2Integer(bean.getTotalTopics()) + 1;
        bean.setTotalTopics("" + topics);
        bean.setIdCollection(bean.getIdCollection() + "," + id);


        bean.update(bean.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dialog.dismiss();
                if (e == null) {
                    ToastUtils.showToast(PublishedTopicsActivity.this, "发表成功:");
                    dellAllFile();
                    closeActivity();
                } else {
                    ToastUtils.showToast(PublishedTopicsActivity.this, "发表失败：" + e.getMessage());
                }
            }

        });
    }


    /**
     * 添加列表内容
     */
    private void addListInfo() {
        BlogBmobBean bean3 = new BlogBmobBean();
        bean3.setIconUrl(bean.getIconUrl());
        if (etContent.getText().toString().trim().length() > 100) {
            bean3.setContent(etContent.getText().toString().trim().subSequence(0, 100).toString());//显示的简略内容
        } else {
            bean3.setContent(etContent.getText().toString().trim());//显示的简略内容
        }
        bean3.setTitle(etTitle.getText().toString().trim());//标题
        bean3.setImageUrl1(imageBmobUrl1);//图片1
        bean3.setImageUrl2(imageBmobUrl2);//图片2
        bean3.setImageUrl3(imageBmobUrl3);//图片3
        bean3.setReplyCount("0");//回复数
        bean3.setPreviewCount("0");//浏览数
        bean3.setType("" + type);//类型
        bean3.setReleaseTimeDate(TimeUtils.getStringDateShortN());
        bean3.setReleaseTimeHour(TimeUtils.getCurentTime());
        bean3.setId(id);
        bean3.setDateId(dateId);
        bean3.setNumId(itemTotal + 1);
        bean3.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //数据保存成功
                    //---------------------保存详情内容-------------------
                    addDetails();
                } else {
                    dialog.dismiss();
                    //数据保存失败
                    ToastUtils.showToast(PublishedTopicsActivity.this, "数据保存失败!");
                }
            }
        });
    }


    /**
     * 添加分页内容
     */
    private void addPageInfo() {
        Log.e("TAG", "commitInfo: ---------------------提交信息4");
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
        Log.e("TAG", "commitInfo: ---------addDateSearch------------提交信息3");

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
                        bean2.setTotalNum(itemTotal + 1);//--------------------------------------------------------------------查询前一天的总数进行操作------------------------------------------------------------
                        bean2.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
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
                        bean2.setTotalNum(bean2.getTotalNum() + 1);//--------------总数加1-------------------------
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
                pageName = "jingchenggonglve";
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

    /**
     * 删除所有保存的图片
     */
    private boolean dellAllFile() {
        boolean success = false;
        if (listFile.size() > 0) {
            for (int i = 0; i < listFile.size(); i++) {
                success = listFile.get(i).delete();
            }
        }
        return success;
    }
}
