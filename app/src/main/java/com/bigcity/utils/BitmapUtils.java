package com.bigcity.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * * ===============================================================
 * name:             BitmapUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/14
 * description： 图像压缩工具类
 * history：
 * *==================================================================
 */

public class BitmapUtils {


    private BitmapUtils() {
    }

    /**
     * 压缩图片-->尺寸压缩-->将图片压缩到指定宽高,同时缩小bitmap的存储大小
     *
     * @param inBitmap  要压缩的bitmap
     * @param newWidth  新的宽度
     * @param newHeight 新的高度
     * @return 指定宽高的bitmap, 原bitmap已回收
     */
    public static Bitmap getReduceBitmap(Bitmap inBitmap, int newWidth, int newHeight) {
        Bitmap outBitmap;


        float width = inBitmap.getWidth();
        float height = inBitmap.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        matrix.postScale(scaleWidth, scaleHeight);

        outBitmap = Bitmap.createBitmap(inBitmap, 0, 0, (int) width,
                (int) height, matrix, true);

        inBitmap.recycle();
        return outBitmap;

    }

    /**
     * 压缩图片-->质量压缩-->压缩图片的保存体积,图片本身的宽高不变
     *
     * 质量压缩适合将图片保存到本地,再次读取压缩后的图片时,bitmap的大小没有变化,所占内存与原图片相同
     *
     * @param inBitmap 需要压缩的图片
     * @return 压缩后的图片, 原图片已回收
     */

    public static Bitmap getCompressBitmap(Bitmap inBitmap) {
        Bitmap outBitmap;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        inBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        Log.e("TAG", "getFixation: ----------baos.toByteArray()-11----------" + baos.toByteArray().length);


        int options = 100*100 * 1024 / (baos.toByteArray().length);//将图片压缩到100kb的压缩比
        baos.reset();
        inBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中


        // TODO: 2017/9/14 将图片数据baos保存到本地

        Log.e("TAG", "getFixation: ----------baos.toByteArray()-22----------" + baos.toByteArray().length);

        outBitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);


        inBitmap.recycle();
        return outBitmap;

    }

    /**
     * 从指定的图像路径获取位图
     *
     * @param imgPath 图片路径
     * @return bitmap
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

//
//
//    /**
//     * 按原比例压缩图片到指定尺寸
//     *
//     * @param context
//     * @param inputUri
//     * @param outputUri
//     * @param maxLenth  最长边长
//     */
//    public static void reducePicture(Context context, Uri inputUri,
//                                     Uri outputUri, int maxLenth, int compress) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        InputStream is = null;
//        try {
//            is = context.getContentResolver().openInputStream(inputUri);
//            BitmapFactory.decodeStream(is, null, options);
//            if (is != null) {
//                is.close();
//            }
//            int sampleSize = 1;
//            int longestSide = 0;
//            int longestSideLenth = 0;
//            if (options.outWidth > options.outHeight) {
//                longestSideLenth = options.outWidth;
//                longestSide = 0;
//            } else {
//                longestSideLenth = options.outHeight;
//                longestSide = 1;
//            }
//            if (longestSideLenth > maxLenth) {
//                sampleSize = longestSideLenth / maxLenth;
//            }
//            options.inJustDecodeBounds = false;
//            options.inSampleSize = sampleSize;
//
//            is = context.getContentResolver().openInputStream(inputUri);
//            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
//            if (is != null) {
//                is.close();
//            }
//
//            if (bitmap == null) {
//                Toast.makeText(context, "图片获取失败，请确认您的存储卡是否正常",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            Bitmap srcBitmap = bitmap;
//            float scale = 0;
//            if (longestSide == 0) {
//                scale = (float) maxLenth / (float) (srcBitmap.getWidth());
//            } else {
//                scale = (float) maxLenth / (float) (srcBitmap.getHeight());
//            }
//            Matrix matrix = new Matrix();
//            matrix.postScale(scale, scale);
//            bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
//                    srcBitmap.getHeight(), matrix, true);
//            // 如果尺寸不变会返回本身，所以需要判断是否是统一引用来确定是否需要回收
//            if (srcBitmap != bitmap) {
//                srcBitmap.recycle();
//                srcBitmap = null;
//            }
//
//            saveBitmapToUri(bitmap, outputUri, compress);
//            bitmap.recycle();
//            bitmap = null;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    private static boolean saveBitmapToUri(Bitmap bitmap, Uri uri, int compress)
//            throws IOException {
//        File file = new File(uri.getPath());
//        if (file.exists()) {
//            if (file.delete()) {
//                if (!file.createNewFile()) {
//                    return false;
//                }
//            }
//        }
//
//        BufferedOutputStream outStream = new BufferedOutputStream(
//                new FileOutputStream(file));
//        bitmap.compress(Bitmap.CompressFormat.JPEG, compress, outStream);
//        outStream.flush();
//        outStream.close();
//
//        return true;
//    }
//
    /**
     * 压缩图片（质量压缩）
     *将图片压缩到100kb并保存,返回file
     * @param bitmap 位图
     */
    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
//                long length = baos.toByteArray().length;
        }
        String filename = StringUtils.getRandomCode()+System.currentTimeMillis();
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        return file;
    }
//
//    /**
//     * 回收位图
//     */
//    public static void recycleBitmap(Bitmap... bitmaps) {
//        if (bitmaps == null) {
//            return;
//        }
//        for (Bitmap bm : bitmaps) {
//            if (null != bm && !bm.isRecycled()) {
//                bm.recycle();
//            }
//        }
//    }
//
//    /**
//     * 将位图存储到指定的图像路径中
//     *
//     * @param bitmap  位图
//     * @param outPath 存储路径
//     * @throws FileNotFoundException 文件io异常
//     */
//    public void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
//        FileOutputStream os = new FileOutputStream(outPath);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
//    }
//
//    /**
//     * 通过像素压缩图像，这将改变图像的宽度/高度。用于获取缩略图
//     *
//     * @param imgPath image path
//     * @param pixelW  目标宽度像素
//     * @param pixelH  高度目标像素
//     * @return bitmap
//     */
//    public Bitmap ratio(String imgPath, float pixelW, float pixelH) {
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
//        newOpts.inJustDecodeBounds = true;
//        newOpts.inPreferredConfig = Config.RGB_565;
//        // Get bitmap info, but notice that bitmap is null now
//        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
//
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        // 想要缩放的目标尺寸
//        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
//        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
//        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;// be=1表示不缩放
//        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
//        newOpts.inSampleSize = be;// 设置缩放比例
//        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
//        // 压缩好比例大小后再进行质量压缩
//        // return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
//        return bitmap;
//    }
//
//    /**
//     * 压缩图像的大小，这将修改图像宽度/高度。用于获取缩略图
//     *
//     * @param image  bitmap
//     * @param pixelW target pixel of width
//     * @param pixelH target pixel of height
//     * @return bitmap
//     */
//    public Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
//        if (os.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
//            os.reset();// 重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, 50, os);// 这里压缩50%，把压缩后的数据存放到baos中
//        }
//        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        newOpts.inJustDecodeBounds = true;
//        newOpts.inPreferredConfig = Config.RGB_565;
//        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
//        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
//        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;// be=1表示不缩放
//        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
//        newOpts.inSampleSize = be;// 设置缩放比例
//        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        is = new ByteArrayInputStream(os.toByteArray());
//        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
//        // 压缩好比例大小后再进行质量压缩
//        // return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
//        return bitmap;
//    }
//
//    /**
//     * 按质量压缩，并将图像生成指定的路径
//     *
//     * @param image   Bitmap
//     * @param outPath 输出路径
//     * @param maxSize 目标将被压缩到小于这个大小（KB）。
//     * @throws IOException io异常
//     */
//    public void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        // scale
//        int options = 100;
//        // Store the bitmap into output stream(no compress)
//        image.compress(Bitmap.CompressFormat.JPEG, options, os);
//        // Compress by loop
//        while (os.toByteArray().length / 1024 > maxSize) {
//            // Clean up os
//            os.reset();
//            // interval 10
//            options -= 10;
//            image.compress(Bitmap.CompressFormat.JPEG, options, os);
//        }
//
//        // Generate compressed image file
//        FileOutputStream fos = new FileOutputStream(outPath);
//        fos.write(os.toByteArray());
//        fos.flush();
//        fos.close();
//    }
//
//    /**
//     * 按质量压缩，并将图像生成指定的路径
//     *
//     * @param imgPath     位图路径
//     * @param outPath     输出路径
//     * @param maxSize     目标将被压缩到小于这个大小（KB）。
//     * @param needsDelete 是否压缩后删除原始文件
//     * @throws IOException io异常
//     */
//    public void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete)
//            throws IOException {
//        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
//
//        // Delete original file
//        if (needsDelete) {
//            File file = new File(imgPath);
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//    }
//
//    /**
//     * 比例和生成拇指的路径指定
//     *
//     * @param image   Bitmap
//     * @param outPath 输出路径
//     * @param pixelW  目标宽度像素
//     * @param pixelH  高度目标像素
//     * @throws FileNotFoundException 文件异常
//     */
//    public void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH)
//            throws FileNotFoundException {
//        Bitmap bitmap = ratio(image, pixelW, pixelH);
//        storeImage(bitmap, outPath);
//    }
//
//    /**
//     * 比例和生成拇指的路径指定
//     *
//     * @param outPath     输出路径
//     * @param pixelW      目标宽度像素
//     * @param pixelH      高度目标像素
//     * @param needsDelete 是否压缩后删除原始文件
//     * @throws FileNotFoundException 文件异常
//     */
//    public void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete)
//            throws FileNotFoundException {
//        Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
//        storeImage(bitmap, outPath);
//
//        // Delete original file
//        if (needsDelete) {
//            File file = new File(imgPath);
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//    }

}

