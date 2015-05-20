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
	 * ��ͣ
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
	 * �ػ�
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
	 * ��������
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
	 * ��������
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
	
	
	//���ڷ��ضԻ�������
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
					case Choice.��:
						cchoice = Choice.��;
						Toast.makeText(this, "��ѡ����ǣ� �� \n�Ա��˲Ż�������̽���", Toast.LENGTH_SHORT).show();
						break;
					case Choice.����ɫ��:
						cchoice = Choice.����ɫ��;
						Toast.makeText(this, "��ѡ����ǣ� ����ɫ��\n�Ա��˲Ż�������̽��� ", Toast.LENGTH_SHORT).show();
						break;
					case Choice.�罴��:
						cchoice = Choice.�罴��;
						Toast.makeText(this, "��ѡ����ǣ�  �罴��\n�Ա��˲Ż�������̽���", Toast.LENGTH_SHORT).show();
						break;
					case Choice.����Ƭ:
						cchoice = Choice.����Ƭ;
						Toast.makeText(this, "��ѡ����ǣ�����Ƭ\n�Ա��˲Ż�������̽���  ", Toast.LENGTH_SHORT).show();
						break;
					case Choice.�׷�:
						cchoice = Choice.�׷�;
						Toast.makeText(this, "��ѡ����ǣ� �׷� \n�Ա��˲Ż�������̽���", Toast.LENGTH_SHORT).show();
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
					case Choice.����:
						if(mmaingame.getMainView().hero.getMoney()-12 >= 0)
						{
							mmaingame.getMainView().hero.cutMoney(12);
							mmaingame.getMainView().hero.setpack(Choice.����);
							Toast.makeText(this, "�������ˣ� ����\n���Ľ�ң�12\nʹ�ú������ɻָ�50%�� ", Toast.LENGTH_SHORT).show();
						}
						else
							j = false;
	
						break;
					case Choice.�ǹ�:
						if(mmaingame.getMainView().hero.getMoney()-12 >= 0)
						{
							mmaingame.getMainView().hero.cutMoney(12);
							mmaingame.getMainView().hero.setpack(Choice.�ǹ�);
							Toast.makeText(this, "�������ˣ� �ǹ�\n���Ľ�ң�12\nʹ�ú������ɻָ�50%�� ", Toast.LENGTH_SHORT).show();
						}
						else
							j = false;
						break;
					case Choice.����:
						if(mmaingame.getMainView().hero.getMoney()-12 >= 0)
						{
							mmaingame.getMainView().hero.cutMoney(20);
							mmaingame.getMainView().hero.setpack(Choice.����);
							Toast.makeText(this, "�������ˣ�����\n���Ľ�ң�20\nʹ�ú������ɻָ�100%�� ", Toast.LENGTH_SHORT).show();
						}
						else
							j = false;
						
						break;
					case Choice.������:
						if(mmaingame.getMainView().hero.getMoney()-20 >= 0)
						{
							mmaingame.getMainView().hero.cutMoney(20);
							mmaingame.getMainView().hero.setpack(Choice.������);
							Toast.makeText(this, "�������ˣ� ������ \n���Ľ�ң�20\nʹ�ú������ɻָ�100%��", Toast.LENGTH_SHORT).show();
						}
						else
							j = false;
						
						break;
					case Choice.����Կ��:
						mmaingame.getMainView().hero.cutMoney(30);
						mmaingame.getMainView().hero.setpack(Choice.����Կ��);
						Toast.makeText(this, "�������ˣ�����Կ�� \n���Ľ�ң�30\nʹ�ú�ɽ������ⳡ����", Toast.LENGTH_SHORT).show();
						break;
					case Choice.ʲô����Ҫ:
						Toast.makeText(this, "��ʲôҲû����", Toast.LENGTH_LONG).show();
						break;
					default:
						break;	
				}
				
				if(!j)
				{
					Toast.makeText(this, "��Ǯ���㣡", Toast.LENGTH_SHORT).show();
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.HOSPITAL)	
			{
				switch(choice)
				{
					case Choice.��:
						mmaingame.getMainView().hero.setHp(100);
						Toast.makeText(this, "���ֵ�һ��...ҩ����ͣ����\nPs������ֵ�ָ�100%��", Toast.LENGTH_SHORT).show();
						break;
					case Choice.��:
						Toast.makeText(this, "�����ƣ� T T...", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.SEND_PACK)
			{
				switch(choice)
				{
					case Choice.��:
						mmaingame.getMainView().hero.getTask().execTask(Task.SEND_PACK);
						break;
					case Choice.��:
						Toast.makeText(this, "�ѵ������ҵİ������Ƕ���������Ҳû�����", Toast.LENGTH_SHORT).show();
						mmaingame.getMainView().hero.getTask().reTaskState(Task.SEND_PACK);
						break;
					default:
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.WATERFALL)
			{
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.WATERFALL);
				Toast.makeText(this, "��ѡ���ˣ�" + choice, Toast.LENGTH_SHORT).show();
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
					case Choice.��:
						mmaingame.getMainView().hero.buff[3] = Choice.��;
						Toast.makeText(this, "��ѡ���ˣ���", Toast.LENGTH_SHORT).show();
						break;
					case Choice.��:
						mmaingame.getMainView().hero.buff[3] = Choice.��;
						Toast.makeText(this, "��ѡ���ˣ���", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(this, "û������Կ�ף��޷�����Сľ�ݣ��뵽�����̵깺���ͨ���������á�", Toast.LENGTH_SHORT).show();
			}
			else if(mmaingame.getMainView().choicetype == Task.DESKTYPE)
			{
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.DESKTYPE);
				switch(choice)
				{
					case newChoice.������:
						mmaingame.getMainView().hero.buff[4] = 1;
						Toast.makeText(this, "��ѡ���ˣ�������", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.������:
						mmaingame.getMainView().hero.buff[4] = 1;
						Toast.makeText(this, "��ѡ���ˣ�������", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.Բ��:
						mmaingame.getMainView().hero.buff[4] = 0;
						Toast.makeText(this, "��ѡ���ˣ�Բ��", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.��Բ��:
						mmaingame.getMainView().hero.buff[4] = 0;
						Toast.makeText(this, "��ѡ���ˣ���Բ��", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.������:
						mmaingame.getMainView().hero.buff[4] = 2;
						Toast.makeText(this, "��ѡ���ˣ�������", Toast.LENGTH_SHORT).show();
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.HUAPINGTYPE)
			{
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.HUAPINGTYPE);
				switch(choice)
				{
					case newChoice.����:
						mmaingame.getMainView().hero.buff[6] = 0;
						Toast.makeText(this, "��ѡ���ˣ�����", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.ճ��:
						mmaingame.getMainView().hero.buff[6] = 0;
						Toast.makeText(this, "��ѡ���ˣ�ճ��", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.����:
						mmaingame.getMainView().hero.buff[6] = 0;
						Toast.makeText(this, "��ѡ���ˣ�����", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.����:
						mmaingame.getMainView().hero.buff[6] = 1;
						Toast.makeText(this, "��ѡ���ˣ�����", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.����:
						mmaingame.getMainView().hero.buff[6] = 1;
						Toast.makeText(this, "��ѡ���ˣ�����", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.ľ:
						mmaingame.getMainView().hero.buff[6] = 1;
						Toast.makeText(this, "��ѡ���ˣ�ľ", Toast.LENGTH_SHORT).show();
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.WATERVECTOR)
			{
				mmaingame.getMainView().hero.getTask().updateTaskState(Task.WATERVECTOR);
				switch(choice)
				{
					case newChoice.װ��:
						mmaingame.getMainView().hero.buff[5] = 2;
						Toast.makeText(this, "��ѡ���ˣ�װ��", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.һ��:
						mmaingame.getMainView().hero.buff[5] = 1;
						Toast.makeText(this, "��ѡ���ˣ�һ��", Toast.LENGTH_SHORT).show();
						break;
					case newChoice.�յ�:
						mmaingame.getMainView().hero.buff[5] = 0;
						Toast.makeText(this, "��ѡ���ˣ��յ�", Toast.LENGTH_SHORT).show();
						break;
				}
			}
			else if(mmaingame.getMainView().choicetype == Task.MURDER)
			{
				
				mmaingame.getMainView().hero.buff[2] = choice;
				Toast.makeText(this, "ԭ������������л���ṩ�ı��������������ư��ˣ�(������ɢȥ)", Toast.LENGTH_SHORT).show();
				//��Ⱥɢȥ
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
				String ss = "ʹ����";
				ss += Backpack.goods[type];
				if(type >= 0 && type <=1)
				{
					ss += "\n�����ָ�50��";
				}	
				else if(type >=2 &&type <= 3)
				{
					ss += "\n�����ָ�100%��";
				}
				Toast.makeText(this, ss, Toast.LENGTH_SHORT).show();
			}
			else
				Toast.makeText(this, "����ʹ�õ���Ʒ��", Toast.LENGTH_SHORT).show();
		}
	}
}





