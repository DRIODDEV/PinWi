package com.hatchtact.pinwi.utility;


import java.io.InputStream;

import com.hatchtact.pinwi.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class GifView extends View {

	private InputStream gifInputStream;
	private Movie gifMovie;
	private int movieWidth, movieHeight;
	private long movieDuration;
	private long mMovieStart;

	public GifView(Context context) {
		super(context);
		init(context);
	}

	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GifView(Context context, AttributeSet attrs, 
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context){
		setFocusable(true);
		gifInputStream = context.getResources().openRawResource(R.drawable.wave);

		gifMovie = Movie.decodeStream(gifInputStream);
		movieWidth = gifMovie.width();
		movieHeight = gifMovie.height();
		movieDuration = gifMovie.duration();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, 
			int heightMeasureSpec) {
		//setMeasuredDimension(movieWidth, movieHeight);
		if (gifMovie != null) {
			int movieWidth = gifMovie.width();
			int movieHeight = gifMovie.height();
			/*
			 * Calculate horizontal scaling
			 */
			int measureModeWidth = MeasureSpec.getMode(widthMeasureSpec);
			float scaleW = 1f, scaleH = 1f;
			if (measureModeWidth != MeasureSpec.UNSPECIFIED) {
				int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
				if (movieWidth > maximumWidth) {
					scaleW = (float) movieWidth / (float) maximumWidth;
				} else {
					scaleW = (float) maximumWidth / (float) movieWidth;
				}
			}

			/*
			 * calculate vertical scaling
			 */
			int measureModeHeight = MeasureSpec.getMode(heightMeasureSpec);

			if (measureModeHeight != MeasureSpec.UNSPECIFIED) {
				int maximumHeight = MeasureSpec.getSize(heightMeasureSpec);
				if (movieHeight > maximumHeight) {
					scaleH = (float) movieHeight / (float) maximumHeight;
				} else {
					scaleH = (float) maximumHeight / (float) movieHeight;
				}
			}

			/*
			 * calculate overall scale
			 */


			mScaleH = .5f;
			mScaleW = .5f;


			mMeasuredMovieWidth = (int) (movieWidth * mScaleW);
			mMeasuredMovieHeight = (int) (movieHeight * mScaleH);

			setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);

		} else {
			setMeasuredDimension(getSuggestedMinimumWidth(),
					getSuggestedMinimumHeight());
		}
	}
	private float mLeft;
	private float mTop;
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mLeft = (getWidth() - mMeasuredMovieWidth) / 2f;
		mTop = (getHeight() - mMeasuredMovieHeight) / 2f;
	}
	private int mMeasuredMovieWidth;
	private int mMeasuredMovieHeight;
	private float mScaleH = 1f, mScaleW = 1f;

	public int getMovieWidth(){
		return movieWidth;
	}

	public int getMovieHeight(){
		return movieHeight;
	}

	public long getMovieDuration(){
		return movieDuration;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		long now = android.os.SystemClock.uptimeMillis();
		if (mMovieStart == 0) {   // first time
			mMovieStart = now;
		}

		if (gifMovie != null) {

			int dur = gifMovie.duration();
			if (dur == 0) {
				dur = 1000;
			}

			int relTime = (int)((now - mMovieStart) % dur);

			gifMovie.setTime(relTime);
			canvas.scale(mScaleW, mScaleH);
			gifMovie.draw(canvas, mLeft / mScaleW, mTop / mScaleH);
			//gifMovie.draw(canvas, 0, 0);
			invalidate();

		}

	}

}
