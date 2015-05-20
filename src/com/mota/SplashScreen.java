package com.mota;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class SplashScreen extends GameView
{
	private int			tick		= 0;
	private Paint		paint		= null;
	private MainGame	mMainGame	= null;
	private float x1,x2,y1,y2;
	

	public SplashScreen(Context context, MainGame mainGame)
	{
		super(context);
		paint = new Paint();
		mMainGame = mainGame;
		paint.setTextSize(yarin.TextSize);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
	}


	protected void onDraw(Canvas canvas)
	{
		tick++;
		paint.setColor(Color.BLACK);
		yarin.fillRect(canvas, 0, 0, yarin.SCREENW, yarin.SCREENH, paint);

		paint.setColor(Color.WHITE);
		String string = "�Ƿ�����Ч��";
		yarin.drawString(canvas, string, (yarin.SCREENW - paint.measureText(string)) / 2, (yarin.SCREENH - paint.getTextSize()) / 2, paint);
		string = "��";
		yarin.drawString(canvas, string, 5, yarin.SCREENH - paint.getTextSize() - 5, paint);
		string = "��";
		yarin.drawString(canvas, string, yarin.SCREENW - paint.measureText(string) - 5, yarin.SCREENH - paint.getTextSize() - 5, paint);
	}


	public boolean onKeyUp(int keyCode)
	{
		if (keyCode == yarin.KEY_DPAD_OK)
		{
			mMainGame.mbMusic = 1;
		}
		mMainGame.controlView(yarin.GAME_MENU);
		if (mMainGame.mbMusic == 1)
		{
			mMainGame.mCMIDIPlayer.PlayMusic(1);
		}
		return true;
	}

	public boolean onKeyDown(int keyCode)
	{
		return true;
	}


	public void refurbish()
	{
		// if (tick > 10)
		// {
		// mMainGame.controlView(yarin.GAME_MENU);
		// }
	}


	public void reCycle()
	{
		paint = null;
		System.gc();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//�̳���Activity��onTouchEvent������ֱ�Ӽ�������¼�  
        /*
		if(event.getAction() == MotionEvent.ACTION_DOWN) {  
            //����ָ���µ�ʱ��  
            x1 = event.getX();
            y1 = event.getY();

        }
        */
        if(event.getAction() == MotionEvent.ACTION_UP) {  
            //����ָ�뿪��ʱ��  
            x2 = event.getX();  
            y2 = event.getY();  
            if(y2 > yarin.SCREENH * 3/4) {  
            	if(x2 < yarin.SCREENW / 3)
            	{
            		mMainGame.mbMusic = 1;
            		mMainGame.controlView(yarin.GAME_MENU);
            	}
            	else if(x2 > yarin.SCREENW *2/3)
            	{
            		mMainGame.mbMusic = 0;
            		mMainGame.controlView(yarin.GAME_MENU);
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
