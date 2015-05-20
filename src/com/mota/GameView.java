package com.mota;
import javax.game.LayerManager;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
public abstract class GameView extends View
{
	public boolean mmakeChoice = false; //����Activity֮���ת��
	public int choicetype = -1; //���ڸ���ѡ������ʶ�����
	protected HeroSprite hero = null;
	protected GameMap gameMap = null;
	protected LayerManager layerManager;
	public boolean isDoorOpen = false;
	public boolean mazeFlag = false;
	public boolean isBackpack = false;
	public int	heroInfor = 0;
	public boolean GateFinish = false;
	
	public GameView(Context context)
	{
		super(context);
	}
	/**
	 * ��ͼ
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	protected abstract void onDraw(Canvas canvas);
	/**
	 * ��������
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onKeyDown(int keyCode);
	/**
	 * ��������
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onKeyUp(int keyCode);
	
	public abstract boolean onTouch(MotionEvent event);
	
	/**
	 * ���ƻ���
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onTouchEvent(MotionEvent event);
	/**
	 * ������Դ
	 *
	 */
	protected abstract void reCycle();	
	
	/**
	 * ˢ��
	 *
	 */
	protected abstract void refurbish();
	
}

