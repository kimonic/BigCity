package com.bigcity.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by 12348 on 2017/4/19 0019.
 */

public class SingleGif {
    private static SingleGif instance=null;
    private Context context;
    /**存储静态png的key值*/
    private Set<String> str;
    /**存储gif图片的分解key值*/
    private Set<String> str1;
    /**静态图片集合*/
    private Map<String,Bitmap> map;
    /**动态图片集合*/
    private Map<String,Bitmap>  gifMap;
    private String TAG="singlegif";
    /**存储gif图包含的bitmap个数*/
    private Map<String,Integer> gifCountMap;

    private SingleGif(Context context) {
        this.context=context;
        str=new HashSet<String>();
        str1=new HashSet<String>();
        map=new HashMap<String, Bitmap>();
        gifMap=new HashMap<String, Bitmap>();
        gifCountMap=new HashMap<String, Integer>();
    }

    public static SingleGif getInstance(Context context){
        if (instance==null){
            instance=new SingleGif(context);
        }
        return instance;
    }

    /**保存静态png图像*/
    public Bitmap getGif(String path){
        Bitmap bitmap=null;
        if (!str.contains(path)){
            str.add(path);
            try {
                bitmap= BitmapFactory.decodeStream(context.getAssets().open(path));
                map.put(path,bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            };
        }else {
            bitmap=map.get(path);
        }
        return bitmap;
    }

    /**存储gif的分解bitmap图*/
    public Bitmap getGifBitmap(String path,Bitmap bitmap){
        Bitmap bitmap1=null;
        if (!str1.contains(path)&&bitmap!=null){
            str1.add(path);
            gifMap.put(path,bitmap);
        }else {
            bitmap1=gifMap.get(path);
        }
        return bitmap1;
    }
    /**释放资源*/
    public void clearAll(){
        str.clear();
        str1.clear();
        for (String key:map.keySet()){
            map.get(key).recycle();
        }
        for (String key:gifMap.keySet()){
            gifMap.get(key).recycle();
        }
        map.clear();
        gifMap.clear();
        gifCountMap.clear();
        System.gc();
    }
    /**判断是否包含动态图片*/
    public boolean gifExist(String path){
        if (str1.contains(path)){
            return true;
        }
        return false;
    }

    /**存储gif的bitmap的个数*/
    public void saveGifCount(String path,int count){
        gifCountMap.put(path,count);

    }
    /**获得gif的bitmap的个数*/
    public int getGifCount(String path){
        return gifCountMap.get(path);
    }

}
