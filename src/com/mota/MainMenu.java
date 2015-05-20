package com.mota;

import com.mota.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MainMenu extends GameView
{
	private String[]	menu		= { "新的游戏", "继续上次游戏", "帮助", "关于", "退出" };
	private int			itemNum		= menu.length;
	private int			curItem;
	private boolean		isFirstPlay	= false;
	public int			borderX, borderY;
	private float x1,y1,x2,y2;
	private Bitmap		mImgMenuBG	= null,
						m1 = null,
						m2 = null,
						m3 = null,
						m1_ = null,
						m2_ = null,
						m3_ = null;

	private MainGame	mMainGame	= null;

	private int			split		= yarin.TextSize + 5;

	private int			y;
	private Paint		paint		= null;


	public MainMenu(Context context, MainGame mainGame)
	{
		super(context);
		borderX = 0;
		borderY = 0;
		
		paint = new Paint();
		paint.setTextSize(yarin.TextSize);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mMainGame = mainGame;
		
		if(mMainGame.mbMusic == 1)
			mMainGame.mCMIDIPlayer.PlayMusic(1);
		
		mImgMenuBG = BitmapFactory.decodeResource(this.getResources(), R.drawable.menuback);
		m1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.newgame);
		m1_ = BitmapFactory.decodeResource(this.getResources(), R.drawable.newgame1);
		m2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.aboutus);
		m2_ = BitmapFactory.decodeResource(this.getResources(), R.drawable.aboutus1);
		m3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.exit);
		m3_ = BitmapFactory.decodeResource(this.getResources(), R.drawable.exit1);
	}

	protected void onDraw(Canvas canvas)
	{
		paint.setColor(Color.GRAY);
		yarin.fillRect(canvas, 0, 0, yarin.SCREENW, yarin.SCREENH, paint);

		//yarin.drawImage(canvas, mImgMenuBG, (yarin.SCREENW - mImgMenuBG.getWidth()) / 2, 60);
		yarin.drawImage(canvas, mImgMenuBG, 0, 0);
		drawItem(canvas);
		y = (curItem + 1) * split + borderY + yarin.SCREENH * 1/3;
		
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
			default:
				break;
		}
		return true;
	}


	public boolean onKeyDown(int keyCode)
	{
		return true;
	}

	private void drawItem(Canvas canvas)
	{
		yarin.drawImage(canvas, m1_, yarin.SCREENW / 2 - 178, yarin.SCREENH / 3);
		yarin.drawImage(canvas, m2_, yarin.SCREENW / 2 - 178, yarin.SCREENH / 3 + m1_.getHeight() + 20);
		yarin.drawImage(canvas, m3_, yarin.SCREENW / 2 - 178, yarin.SCREENH / 3 + m1_.getHeight() + m2_.getHeight() + 40);	
	}

	public void refurbish()
	{

	}


	public void reCycle()
	{
		mImgMenuBG = null;
		paint = null;
		m1 = null;
		m1_ = null;
		m2 = null;
		m2_ = null;
		m3 = null;
		m3_ = null;
		System.gc();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//继承了Activity的onTouchEvent方法，直接监听点击事件  
        if(event.getAction() == MotionEvent.ACTION_UP) {  
            //当手指离开的时候  
            x2 = event.getX();  
            y2 = event.getY();  
            if(x2 >= (yarin.SCREENW/2-178) && x2 <= (yarin.SCREENW/2+178)) 
            {  
            	if(y2 >= yarin.SCREENH/3 && y2 <= (yarin.SCREENH / 3 + m1_.getHeight()))
            	{
            		mMainGame.controlView(yarin.GAME_HERO);
    				if (mMainGame.mbMusic == 1)
    				{
    					mMainGame.mCMIDIPlayer.PlayMusic(2);
    				}
            	}
            	else if(y2 >= (yarin.SCREENH / 3 + m1_.getHeight() + 20) && y2 <= (yarin.SCREENH / 3 + m1_.getHeight() + m2_.getHeight() + 20))
            	{
            		mMainGame.controlView(yarin.GAME_ABOUT);
            	}
            	else if(y2 >= (yarin.SCREENH / 3 + m1_.getHeight() + m2_.getHeight() + 40) && y2 <= (yarin.SCREENH / 3 + m1_.getHeight() + m2_.getHeight() + m3_.getHeight() + 40))
            	{
            		if (mMainGame.mCMIDIPlayer != null)
    				{
    					mMainGame.mCMIDIPlayer.FreeMusic();
    					mMainGame.mCMIDIPlayer = null;
    				}
    				System.gc();
    				mMainGame.getMagicTower().finish();
            	}
            } 
        }  
 
        return true;  
	}


	@Override
	public boolean onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
}
