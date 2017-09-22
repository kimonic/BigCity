package com.bigcity.utils;

import android.graphics.Bitmap;
import java.io.File;

/**
 * * ===============================================================
 * name:             BitmapHandleThread
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/22
 * description：
 * history：
 * *==================================================================
 */

public class BitmapHandleThread implements Runnable {

    private Bitmap bitmap;
    private CompressListener listener;
    private int position;

    public BitmapHandleThread(Bitmap bitmap,int position) {
        this.bitmap=bitmap;
        this.position=position;
    }

    public CompressListener getListener() {
        return listener;
    }

    public void setListener(CompressListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        File file=BitmapUtils.compressImage(bitmap);
        if (listener!=null){
            listener.result(file,position);
        }
    }

    public interface  CompressListener {
        void result(File file,int  position);
    }

}
