package com.bigcity.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.utils.BitmapUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;

/**
 * * ===============================================================
 * name:             ShowImageActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/21
 * description：全屏展示图片activity
 * history：
 * *==================================================================
 */

public class ShowImageActivity extends BaseActivity {
    @BindView(R.id.iv_act_showimage)
    ImageView ivActShowimage;

    private String url;

    @Override
    public int getLayoutResId() {
        return R.layout.act_showimage;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_act_showimage:
                closeActivity();
                break;
//                 case R.id.:break;
//                 case R.id.:break;
//                 case R.id.:break;
//                 case R.id.:break;
//                 case R.id.:break;
        }

    }

    @Override
    public void initDataFromIntent() {

        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
        } else {
            url = "";
        }

    }

    @Override
    public void initView() {

        Bitmap bitmap= BitmapUtils.getBitmap(this,Uri.parse(url),0,0);
        if (bitmap!=null){
            ivActShowimage.setImageBitmap(bitmap);
            Log.e("TAG", "initView: ---------------------"+bitmap.getByteCount()/1024f/1024);
        }else {
            ivActShowimage.setImageResource(R.mipmap.ic_launcher);
        }
//        ContentResolver resolver = getContentResolver();
//        try {
//            InputStream inputStream = resolver.openInputStream(Uri.parse(url));
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            ivActShowimage.setImageBitmap(bitmap);
//        }catch (NullPointerException e){
//            ivActShowimage.setImageResource(R.mipmap.ic_launcher);
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            ivActShowimage.setImageResource(R.mipmap.ic_launcher);
//            e.printStackTrace();
//        }
    }

    @Override
    public void initListener() {
        ivActShowimage.setOnClickListener(this);
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi() {

    }


}
