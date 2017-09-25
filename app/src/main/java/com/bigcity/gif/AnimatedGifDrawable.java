package com.bigcity.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AnimatedGifDrawable extends AnimationDrawable {
	private final String TAG="AnimatedGifDrawable";
	private int mCurrentIndex = 0;
	private UpdateListener mListener;
	private List<BitmapDrawable>  list;//-----------------------------------自己添加-----------------------------------



	public AnimatedGifDrawable(InputStream source, UpdateListener listener,String gif,Context context) {
		mListener = listener;
		GifDecoder decoder=null;
		int gifCount=0;
		SingleGif sinGif=SingleGif.getInstance(context);

		decoder = new GifDecoder();
//		decoder.read(source);
//		gifCount=decoder.getFrameCount();
//		sinGif.saveGifCount(gif,gifCount);


		if (SingleGif.getInstance(context).gifExist(gif+"0")){
			gifCount=sinGif.getGifCount(gif);
			try {
				if (source!=null){
					source.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			decoder = new GifDecoder();
			decoder.read(source);
			gifCount=decoder.getFrameCount();
			sinGif.saveGifCount(gif,gifCount);
		}

		list=new ArrayList<BitmapDrawable>();//------------------------------自己添加----------------------------------------
		// Iterate through the gif frames, add each as animation frame

//		Log.e(TAG, "AnimatedGifDrawable: -------------"+gif+"-----------"+decoder.getFrameCount() );
		for (int i = 0; i < gifCount; i++) {
			Bitmap bitmap;
			/**------------------------------------------------------------------------*/
			if (SingleGif.getInstance(context).getGifBitmap(gif+i,null)!=null){
				bitmap=SingleGif.getInstance(context).getGifBitmap(gif+i,null);
//				Log.e(TAG, "AnimatedGifDrawable: 取出已存在的bitmap" );
			}else {
//				Log.e(TAG, "AnimatedGifDrawable: 创建新的bitmap" +gif+"--------------"+i);
				bitmap = decoder.getFrame(i);
//				Log.e(TAG, bitmap+"-----AnimatedGifDrawable: ------------"+(gif+i) );

				SingleGif.getInstance(context).getGifBitmap(gif+i,bitmap);
			}



			/**------------------------------------------------------------------------*/
//			Bitmap bitmap = decoder.getFrame(i);
			BitmapDrawable drawable = new BitmapDrawable(bitmap);

			list.add(drawable);//---------------------------------------------自己添加-------------------------
			// Explicitly set the bounds in order for the frames to display

			drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//			addFrame(drawable, decoder.getDelay(i));//---------------------------------------------------
			addFrame(drawable, 300);
//			Log.e(TAG, "AnimatedGifDrawable: decoder.getDelay(i)--------------"+ decoder.getDelay(i));
			if (i == 0) {
				// Also set the bounds for this container drawable
				setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
			}
		}
//		mListener = listener;
//		GifDecoder decoder = new GifDecoder();
//		decoder.read(source);
//		list=new ArrayList<BitmapDrawable>();//------------------------------自己添加----------------------------------------
//		// Iterate through the gif frames, add each as animation frame
//		for (int i = 0; i < decoder.getFrameCount(); i++) {
//			Bitmap bitmap = decoder.getFrame(i);
//			BitmapDrawable drawable = new BitmapDrawable(bitmap);
//			list.add(drawable);//---------------------------------------------自己添加-------------------------
//			// Explicitly set the bounds in order for the frames to display
//			drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//			addFrame(drawable, decoder.getDelay(i));
//			if (i == 0) {
//				// Also set the bounds for this container drawable
//				setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//			}
//		}
	}

	/**
	 * Naive method to proceed to next frame. Also notifies listener.
	 */
	public void nextFrame() {
		mCurrentIndex = (mCurrentIndex + 1) % getNumberOfFrames();
		if (mListener != null&&mListener.stopUpdate()){//mListener不为空并且,停止位FALSE时继续更新
			mListener.update();
		}else {//---------------------------------------------自己添加-------------------------
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i)!=null){
					list.get(i).setCallback(null);
				}
			}
			System.gc();
		}
	}

	/**
	 * Return display duration for current frame
	 */
	public int getFrameDuration() {
		return getDuration(mCurrentIndex);
	}

	/**
	 * Return drawable for current frame
	 */
	public Drawable getDrawable() {
		return getFrame(mCurrentIndex);
	}

	/**
	 * Interface to notify listener to update/redraw Can't figure out how to
	 * invalidate the drawable (or span in which it sits) itself to force redraw
	 */
	public interface UpdateListener {
		void update();
		boolean stopUpdate();
	}

}