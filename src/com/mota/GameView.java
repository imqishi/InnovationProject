package com.mota;
import javax.game.LayerManager;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
public abstract class GameView extends View
{
	public boolean mmakeChoice = false; //用于Activity之间的转移
	public int choicetype = -1; //用于各种选择界面的识别分类
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
	 * 绘图
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	protected abstract void onDraw(Canvas canvas);
	/**
	 * 按键按下
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onKeyDown(int keyCode);
	/**
	 * 按键弹起
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onKeyUp(int keyCode);
	
	public abstract boolean onTouch(MotionEvent event);
	
	/**
	 * 手势滑动
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onTouchEvent(MotionEvent event);
	/**
	 * 回收资源
	 *
	 */
	protected abstract void reCycle();	
	
	/**
	 * 刷新
	 *
	 */
	protected abstract void refurbish();
	
}

