package com.bigcity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * * ================================================
 * name:            SharedPreferencesUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/12
 * description：SharedPreferences本地存储工具类,单例
 * history：
 * ===================================================
 */
public class SharedPreferencesUtils {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static SharedPreferencesUtils spUtils;

    /**
     *
     * SharedPreferencesUtils  构造函数
     * @param context   上下文
     * @param spName   存储的文件名
     */
    private SharedPreferencesUtils(Context context,String spName) {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
    }


    public static synchronized SharedPreferencesUtils getInstance(Context context,String spName){
        if (spUtils==null){
            spUtils=new SharedPreferencesUtils(context,spName);
        }
        return spUtils;
    }

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, String value) {
        editor.putString(key, value).apply();
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, int value) {
        editor.putInt(key, value).apply();
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, long value) {
        editor.putLong(key, value).apply();
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(String key) {
        return getLong(key, -1L);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public void remove(String key) {
        editor.remove(key).apply();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * SP中清除所有数据
     */
    public void clear() {
        editor.clear().apply();
    }


//    public void seveSpToBean( String key, Object obj) {
//        //   Editor editor = getSharedPreferences(context).edit();
//        Gson gson = Convert.buildGson();
//        String objString = gson.toJson(obj);
//        editor.putString(key, objString).apply();
//    }

//    /**
//     * @param key
//     * @param clazz   这里传入一个类就是我们所需要的实体类(obj)
//     * @return 返回我们封装好的该实体类(obj)
//     */
//    public <T> T getSpToBean( String key, Class<T> clazz) {
//        String objString = sp.getString(key, "");
//        Gson gson = Convert.buildGson();
//        T data = gson.fromJson(objString, clazz);
//        return data;
//    }

    /**
     * 另外一种方法
     * 存放实体类以及任意类型
     *
     * @param key    键
     * @param obj  对象
     */
    public void putBean( String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                //SharedPreferences.Editor editor = getSharedPreferences(context).edit();
                editor.putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public Object getBean( String key) {
        Object obj = null;
        try {
            String base64 = sp.getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;

    }
}