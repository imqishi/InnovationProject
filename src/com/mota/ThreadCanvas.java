package com.mota;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThreadCanvas extends View implements Runnable
{
	private String	m_Tag	= "ThreadCanvas_Tag";


	public ThreadCanvas(Context context)
	{
		super(context);
	}


	/**
	 * ��ͼ
	 * 
	 * @param N
	 *            /A
	 * 
	 * @return null
	 */
	@SuppressLint("WrongCall")
	protected void onDraw(Canvas canvas)
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().onDraw(canvas);
		}
		else
		{
			Log.i(m_Tag, "null");
		}
	}


	/**
	 * ��ͼ��ʾ
	 * 
	 */
	public void start()
	{
		Thread t = new Thread(this);
		t.start();
	}


	// ˢ�½���
	public void refurbish()
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().refurbish();
		}
	}


	/**
	 * ��Ϸѭ��
	 * 
	 * @param N
	 *            /A
	 * 
	 * @return null
	 */
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(yarin.GAME_LOOP);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			refurbish(); // ������ʾ
			postInvalidate(); // ˢ����Ļ
		}
	}


	// ��������(��������)
	boolean onKeyDown(int keyCode)
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().onKeyDown(keyCode);
		}
		else
		{
			Log.i(m_Tag, "null");
		}
		return true;
	}


	// ��������
	boolean onKeyUp(int keyCode)
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().onKeyUp(keyCode);
		}
		else
		{
			Log.i(m_Tag, "null");
		}
		return true;
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().onTouchEvent(event);
		}
		else
		{
			Log.i(m_Tag, "null");
		}
		return false;
    }  
	
	public boolean onTouch(MotionEvent event)
	{
		return true;
	}
}
