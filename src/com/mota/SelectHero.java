package com.mota;

import com.mota.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class SelectHero extends GameView
{
	private String[]	menu		= { "人物 1", "人物 2"};
	private int			itemNum		= menu.length;
	private int			curItem;
	//private boolean		isFirstPlay	= false;
	public int			borderX, borderY;
	private float x1,y1,x2,y2;
	//private Bitmap		mImgMenuBG	= null;
	private Bitmap		male		= null;
	private Bitmap		female		= null;

	private MainGame	mMainGame	= null;
	private int			y;
	private Paint		paint		= null;


	public SelectHero(Context context, MainGame mainGame)
	{
		super(context);
		borderX = 0;
		borderY = 0;

		paint = new Paint();
		paint.setTextSize(yarin.TextSize);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mMainGame = mainGame;
		
		male = BitmapFactory.decodeResource(this.getResources(), R.drawable.male);
		female = BitmapFactory.decodeResource(this.getResources(), R.drawable.female);
	}

	protected void onDraw(Canvas canvas)
	{
		paint.setColor(Color.GRAY);
		yarin.fillRect(canvas, 0, 0, yarin.SCREENW, yarin.SCREENH, paint);
		
		paint.setColor(Color.BLUE);
		
		yarin.drawString(canvas, "选择人物", yarin.SCREENW/2-80, 40, paint);
		yarin.drawImage(canvas, male, (yarin.SCREENW - male.getWidth()) / 2, (yarin.SCREENH/2 - male.getHeight())/2);
		yarin.drawImage(canvas, female, (yarin.SCREENW - female.getWidth()) / 2, (yarin.SCREENH/2 - female.getHeight())/2 + (yarin.SCREENH/2));
	}

	public boolean onKeyUp(int keyCode)
	{
		switch (keyCode)
		{
			case yarin.KEY_SOFT_RIGHT:
				if(mMainGame.mbMusic == 1)
					mMainGame.mCMIDIPlayer.StopMusic();
				mMainGame.getMagicTower().finish();
				break;
		}
		return false;
	}

	public boolean onKeyDown(int keyCode)
	{
		return true;
	}

	public void refurbish()
	{

	}

	public void reCycle()
	{
		male = null;
		female = null;
		paint = null;
		System.gc();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//继承了Activity的onTouchEvent方法，直接监听点击事件  
        if(event.getAction() == MotionEvent.ACTION_UP) {  
            //当手指离开的时候  
            x2 = event.getX();  
            y2 = event.getY();  
            if(x2 >= ((yarin.SCREENW - male.getWidth()) / 2) && x2 <= ((yarin.SCREENW + male.getWidth()) / 2)) {  
            	if(y2 >= ((yarin.SCREENH/2 - male.getHeight())/2) && y2 <=((yarin.SCREENH/2 + male.getHeight())/2))
            	{
            		mMainGame.gender = 1;
    				mMainGame.controlView(yarin.GAME_RUN);
            	}
            	else if(y2 >= ((yarin.SCREENH/2 - female.getHeight())/2 + (yarin.SCREENH/2)) && y2 <= ((yarin.SCREENH/2 + female.getHeight())/2 + (yarin.SCREENH/2)))
            	{
            		mMainGame.gender = 2;
    				mMainGame.controlView(yarin.GAME_RUN);
            	}
            }
        }  
        // return super.onTouchEvent(event);  
        return true;  
	}

	@Override
	public boolean onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
}
