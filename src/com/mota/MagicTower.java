package com.mota;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MagicTower extends Activity
{
	private ThreadCanvas	mThreadCanvas	= null;
	private MainGame mmaingame = null;
	private int cchoice;
	public int screenWidth,screenHeight;
	public int[][] baotemp = new int [12][12];
	public int[] temp = new int[32];
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Display mDisplay = getWindowManager().getDefaultDisplay();
		int W = mDisplay.getWidth();
		int H = mDisplay.getHeight();
		yarin.SCREENH = H;
		yarin.SCREENW = W;
		yarin.BORDERW = W;
		yarin.BORDERH = yarin.SCREENW * 11 / 10;
		
		setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mmaingame = new MainGame(this);
		mThreadCanvas = new ThreadCanvas(this);

		setContentView(mThreadCanvas);	
	}
	
	
	/**
	 * 暂停
	 * 
	 * @param N
	 *            /A
	 * 
	 * @return null
	 */
	protected void onPause()
	{
		super.onPause();
		
	}

	protected void onStop() {
		super.onStop();
		
	}

	/**
	 * 重绘
	 * 
	 * @param N
	 *            /A
	 * 
	 * @return null
	 */
	protected void onResume()
	{
		super.onResume();
		mThreadCanvas.requestFocus();
		mThreadCanvas.start();
	}

	/**
	 * 按键按下
	 * 
	 * @param N
	 *            /A
	 * 
	 * @return null
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		mThreadCanvas.onKeyDown(keyCode);
		return false;
	}


	/**
	 * 按键弹起
	 * 
	 * @param N
	 *            /A
	 * 
	 * @return null
	 */
	@SuppressWarnings("static-access")
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		mThreadCanvas.onKeyUp(keyCode);
		
		if(mmaingame.getMainView().mmakeChoice)
		{
			Intent intent=new Intent(MagicTower.this, newChoice.class);
			int type = mmaingame.getMainView().choicetype;
			intent.putExtra("choicetype", type);
			if(type != -1)
			{
				startActivityForResult(intent,1);
				mmaingame.getMainView().mmakeChoice = false;
			}
		}
		return false;
	}
	
	public boolean onTouch(MotionEvent event)
	{
		return true;
	}
	

	public boolean onTouchEvent(MotionEvent event)
	{
		mThreadCanvas.onTouchEvent(event);
		
		if(mmaingame.getMainView().isBackpack)
		{
			Bundle backbundle = new Bundle();
			baotemp = mmaingame.getMainView().hero.getpack();
			int j=1;
			for(int i = 1; i <= baotemp.length-1; i++)
			{
				if(baotemp[i][i] >= 0)
				{
					temp[j] = baotemp[i][i];
					j++;
					temp[j] = baotemp[0][i];
					j++;
				}
			}
			temp[0] = j/2;
			backbundle.putIntArray("backpack", temp);
			Intent intent = new Intent(MagicTower.this, Backpack.class);
			intent.putExtras(backbundle);
			startActivityForResult(intent,2);
			mmaingame.getMainView().isBackpack = false;
		}
		
		if(mmaingame.getMainView().mmakeChoice)
		{
			Intent intent=new Intent(MagicTower.this, newChoice.class);
			int type = mmaingame.getMainView().choicetype;
			intent.putExtra("choicetype", type);
			if(type != -1)
			{
				startActivityForResult(intent,1);
				mmaingame.getMainView().mmakeChoice = false;
			}
		}
		/*
		if(mmaingame.getMainView().heroInfor != 0)
		{
			Intent intent = new Intent(MagicTower.this, HeroInfor.class);
			int infor[] = new int[9];
			infor[0] = mmaingame.getMainView().hero.getLevel();
			infor[1] = mmaingame.getMainView().hero.getHp();
			infor[2] = mmaingame.getMainView().hero.getMoney();
			infor[3] = mmaingame.getMainView().hero.getAttack();
			infor[4] = mmaingame.getMainView().hero.getDefend();
			infor[5] = mmaingame.getMainView().hero.getClothes();
			infor[6] = mmaingame.getMainView().hero.getweapon();
			infor[7] = mmaingame.getMainView().hero.getExperience();
			infor[8] = mmaingame.gender;
			intent.putExtra("heroInfor", infor);
			startActivityForResult(intent,3);
			mmaingame.getMainView().heroInfor = 0;
		}
		*/
		if(mmaingame.getMainView().GateFinish)
		{
			Intent intent = new Intent();  
	        intent.setClass(MagicTower.this, Report.class);  
	  
	        Bundle bundle = new Bundle();
            bundle.putIntArray("flag", mmaingame.getMainView().hero.buff);  
            intent.putExtras(bundle);  
	        startActivity(intent); 
			
			mmaingame.getMainView().GateFinish = false;
		}
		return true;
    }
	
	
	//用于返回对话框数据
	@SuppressWarnings("static-access")
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == 1)
		{
			int choice=data.getExtras().getInt("choice");
			
			if(mmaingame.getMainView().choicetype == Task.SUPPER)
			{
				switch(choice)
				{
					case Choice.汤:
						cchoice = Choice.汤;
						Toast.makeText(this, "您选择的是： 汤 \n吃饱了才会有力气探险嘛！", Toast.LENGTH_SHORT).show();
						break;
					case Choice.生菜色拉:
						cchoice = Choice.生菜色拉;
						Toast.makeText(this, "您选择的是： 生菜色拉\n吃饱了才会有力气探险嘛！ ", Toast.LENGTH_SHORT).show();
						break;
					case Choice.腌酱菜:
						cchoice = Choice.腌酱菜;
						Toast.makeText(this, "您选择的是：  腌酱菜\n吃饱了才会有力气探险嘛！", Toast.LENGTH_SHORT).show();
						break;
					case Choice.烤肉片:
						cchoice = Choice.烤肉片;
						Toast.makeText(this, "您选择的是：烤肉片\n吃饱了才会有力气探险嘛！  ", Toast.LENGTH_SHORT).show();
						break;
					case Choice.白饭:
						cchoice = Choice.白饭;
						Toast.makeText(this, "您选择的是： 白饭 \n吃饱了才会有力气探险嘛！", Toast.LENGTH_SHORT).show();
					default:
						break;	
				}
				mmaingame.getMainView().hero.buff[1] = choice;
				mmaingame.getMainView().gameMap.changeCell(58, 45);
				mmaingame.getMainView().gameMap.changeCell(59, 46);
				mmaingame.getMainView().gameMap.changeCell(69, 47);
				mmaingame.getMainView().gameMap.changeCell(70, 48);
				mmaingame.getMainView().isDoorOpen = true;
			}
			else if(mmaingame.getMainView().choicetype == Task.BUY_IN_SHOP)
			{
				boolean j = true;
				
				switch(choice)
				{
					case Choice.饮料:
						if(mmaingame.getMainView().hero.getMoney()-12 >= 0)
						{
							mmaingame.getMainView().hero.cutMoney(12);
							mmaingame.getMainView().hero.setpack(Choice.饮料);
							Toast.makeText(this, "您购买了： 饮料\n消耗金币：12\n使用后体力可恢复50%！ ", Toast.LENGTH_SHORT).show();
						}
						else
							j = false;
	
						break;
					case Choice.糖果:
						if(mmaingame.getMainView().hero.getMoney()-12 >= 0)
						{
							mmaingame.getMainView().hero.cutMoney(12);
							mmaingame.getMainView().hero.setpack(Choice.糖果);
							Toast.makeText(this, "您购买了： 糖果\n消耗金币：12\n使用后体力可恢复50%！ ", Toast.LENGTH_SHORT).show();
						}
						else
							j = false;
						break;
					case Choice.汉堡:
						if(mmaingame.getMainView().hero.getMoney()-12 >= 0)
						{
							mmaingame.getMainView().hero.cutMoney(20);
							mmaingame.getMainView().hero.setpack(Choice.汉堡);
							Toast.makeText(this, "您购买了：汉堡\n消耗金币：20\n使用后体力可恢复100%！ ", Toast.LENGTH_SHORT).show();
						}
						else
							j = false;
						
						break;
					case Choice.三明治:
						if(mmaingame.getMainView().hero.getMoney()-20 >= 0)
						{
							mmaingame.getMainView().hero.cutMoney(20);
							mmaingame.getMainView().hero.setpack(Choice.三明治);
							Toast.makeText(this, "您购买了： 三明治 \n消耗金币：20\n使用后体力可恢复100%！", Toast.LENGTH_SHORT).show();
						}
						else
							j = false;
						
						break;
					case Choice.万能钥匙:
						mmaingame.getMainView().hero.cutMoney(30);
						mmaingame.getMainView().hero.setpack(Choice.万能钥匙);
						Toast.makeText(this, "您购买了：万能钥匙 \n消耗金币：30\n使用后可进入特殊场所！", Toast.LENGTH_SHORT).show();
						break;
					case Choice.什么都不要:
						Toast.makeText(this, "您什么也没有买", Toast.LENGTH_LONG).show();
						break;
					default:
						break;	
				}
				
				if(!j)
				{
					Toast.makeText(this, "金钱不足！", Toast.LENGTH_SHORT).show();
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.HOSPITAL)	
			{
				switch(choice)
				{
					case Choice.是:
						mmaingame.getMainView().hero.setHp(100);
						Toast.makeText(this, "听兄弟一句...药不能停啊！\nPs：生命值恢复100%！", Toast.LENGTH_SHORT).show();
						break;
					case Choice.否:
						Toast.makeText(this, "何弃疗！ T T...", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.SEND_PACK)
			{
				switch(choice)
				{
					case Choice.是:
						mmaingame.getMainView().hero.getTask().execTask(Task.SEND_PACK);
						break;
					case Choice.否:
						Toast.makeText(this, "难道不是我的包裹吗？那东西你留着也没用嘛……", Toast.LENGTH_SHORT).show();
						mmaingame.getMainView().hero.getTask().reTaskState(Task.SEND_PACK);
						break;
					default:
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.WATERFALL)
			{
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.WATERFALL);
				Toast.makeText(this, "您选择了：" + choice, Toast.LENGTH_SHORT).show();
				if(choice >= 0 && choice <= 4)
					mmaingame.getMainView().hero.buff[7] = 0;
				else if(choice == 5)
					mmaingame.getMainView().hero.buff[7] = 1;
				else
					mmaingame.getMainView().hero.buff[7] = 2;
			}
			else if(mmaingame.getMainView().choicetype == Task.WOODENHOUSE)
			{
				mmaingame.getMainView().gameMap.isMazeFirst[1] = false;
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.WOODENHOUSE);
				switch(choice)
				{
					case Choice.是:
						mmaingame.getMainView().hero.buff[3] = Choice.是;
						Toast.makeText(this, "您选择了：是", Toast.LENGTH_SHORT).show();
						break;
					case Choice.否:
						mmaingame.getMainView().hero.buff[3] = Choice.否;
						Toast.makeText(this, "您选择了：否", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
				}
				if(mmaingame.getMainView().hero.usepackforce(4))
				{
					mmaingame.getMainView().gameMap.setMazeMap(1);
					mmaingame.getMainView().layerManager.remove(mmaingame.getMainView().gameMap.getmazeFloorMap());
					mmaingame.getMainView().layerManager.append(mmaingame.getMainView().gameMap.getmazeFloorMap());
					mmaingame.getMainView().hero.setFrame(0);
				}
				else
					Toast.makeText(this, "没有万能钥匙，无法进入小木屋！请到社区商店购买或通过做任务获得～", Toast.LENGTH_SHORT).show();
			}
			else if(mmaingame.getMainView().choicetype == Task.DESKTYPE)
			{
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.DESKTYPE);
				switch(choice)
				{
					case newChoice.正方形:
						mmaingame.getMainView().hero.buff[4] = 1;
						Toast.makeText(this, "您选择了：正方形", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.长方形:
						mmaingame.getMainView().hero.buff[4] = 1;
						Toast.makeText(this, "您选择了：长方形", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.圆形:
						mmaingame.getMainView().hero.buff[4] = 0;
						Toast.makeText(this, "您选择了：圆形", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.椭圆形:
						mmaingame.getMainView().hero.buff[4] = 0;
						Toast.makeText(this, "您选择了：椭圆形", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.三角形:
						mmaingame.getMainView().hero.buff[4] = 2;
						Toast.makeText(this, "您选择了：三角形", Toast.LENGTH_SHORT).show();
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.HUAPINGTYPE)
			{
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.HUAPINGTYPE);
				switch(choice)
				{
					case newChoice.玻璃:
						mmaingame.getMainView().hero.buff[6] = 0;
						Toast.makeText(this, "您选择了：玻璃", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.粘土:
						mmaingame.getMainView().hero.buff[6] = 0;
						Toast.makeText(this, "您选择了：粘土", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.瓷器:
						mmaingame.getMainView().hero.buff[6] = 0;
						Toast.makeText(this, "您选择了：瓷器", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.金属:
						mmaingame.getMainView().hero.buff[6] = 1;
						Toast.makeText(this, "您选择了：金属", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.塑料:
						mmaingame.getMainView().hero.buff[6] = 1;
						Toast.makeText(this, "您选择了：塑料", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.木:
						mmaingame.getMainView().hero.buff[6] = 1;
						Toast.makeText(this, "您选择了：木", Toast.LENGTH_SHORT).show();
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.WATERVECTOR)
			{
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.WATERVECTOR);
				switch(choice)
				{
					case newChoice.装满:
						mmaingame.getMainView().hero.buff[5] = 2;
						Toast.makeText(this, "您选择了：装满", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.一半:
						mmaingame.getMainView().hero.buff[5] = 1;
						Toast.makeText(this, "您选择了：一半", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.空的:
						mmaingame.getMainView().hero.buff[5] = 0;
						Toast.makeText(this, "您选择了：空的", Toast.LENGTH_SHORT).show();
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.MURDER)
			{
				
				mmaingame.getMainView().hero.buff[2] = choice;
				Toast.makeText(this, "原来是这样！感谢您提供的宝贵线索让我们破案了！(众人已散去)", Toast.LENGTH_SHORT).show();
				//人群散去
				mmaingame.getMainView().gameMap.changeCell(34, 3);
				mmaingame.getMainView().gameMap.changeCell(35, 3);
				mmaingame.getMainView().gameMap.changeCell(36, 3);
				mmaingame.getMainView().gameMap.changeCell(37, 3);
				mmaingame.getMainView().gameMap.changeCell(45, 3);
				mmaingame.getMainView().gameMap.changeCell(46, 3);
				mmaingame.getMainView().gameMap.changeCell(47, 3);
				mmaingame.getMainView().gameMap.changeCell(48, 3);
				
			}
		}	
		mmaingame.getMainView().choicetype = -1;
		if(resultCode == 2)
		{
			int type=data.getExtras().getInt("backpack");
			
			if(mmaingame.getMainView().hero.usepack(type))
			{
				String ss = "使用了";
				ss += Backpack.goods[type];
				if(type >= 0 && type <=1)
				{
					ss += "\n体力恢复50！";
				}	
				else if(type >=2 &&type <= 3)
				{
					ss += "\n体力恢复100%！";
				}
				Toast.makeText(this, ss, Toast.LENGTH_SHORT).show();
			}
			else
				Toast.makeText(this, "不可使用的物品！", Toast.LENGTH_SHORT).show();
		}
	}
}





