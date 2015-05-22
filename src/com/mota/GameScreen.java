package com.mota;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;

import javax.game.LayerManager;

import com.mota.R;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

public class GameScreen extends GameView
{
	private Paint			paint		= null;
	private TextPaint		tpaint		= null;
	public MainGame			mMainGame	= null;

	public static final int TEXT_COLOR = 0xffc800;
	public static final int BACK_COLOR = 0x000000;
	public static final int SMALL_FONT = 12;
	public static final int NORMAL_FONT = 15;
	public static final int LARGE_FONT = 16;
	public static final int UP = 0,DOWN = 1,LEFT = 2,RIGHT = 3;
	public static final int MILLIS_PER_TICK = 300;

	public boolean mshowMessage = false;
	public boolean mshowDialog = false;
	public boolean mshowFight = false;
	private boolean mshowInfo = false;
	public boolean isBuy = false;
	public boolean done[] = new boolean[10]; //0判木屋大桌选择否1判花瓶样式选择否2判花瓶水满程度3判瀑布选择否
	
	boolean swap = false;
	int walkFlag = 0;  //用于控制人物走动
	
	private String strMessage = "";
	public FightScreen mFightScreen;

	//private HeroSprite hero;
	//private GameMap gameMap;
	private FightCalc fightCalc;
	private Task task;
	private static final int step = GameMap.TILE_WIDTH;
	public Canvas mcanvas;
	public static final int IMAGE_HERO = 0,
	IMAGE_MAP = 1,
	IMAGE_DIALOG_HERO = 2,
	IMAGE_DIALOG_ANGLE = 3,
	IMAGE_DIALOG_THIEF = 4,
	IMAGE_BORDER = 5,
	IMAGE_DIALOGBOX = 6,
	IMAGE_MESSAGEBOX = 7,
	IMAGE_BORDER2 = 8,
	IMAGE_BORDER3 = 9,
	IMAGE_BORDER4 = 10,
	IMAGE_DIALOG_PRINCESS = 11,
	IMAGE_DIALOG_BOSS = 12,
	IMAGE_BLUE_GEEZER = 13,
	IMAGE_RED_GEEZER = 14,
	IMAGE_SPLASH = 15,
	IMAGE_GAMEOVER = 16;

	public int					borderX, borderY;
	private int				winWidth, winHeight;
	private int				scrollX, scrollY;
	private int				curDialogImg;
	private int				imgType = 0;
	private float x1,y1,x2,y2;
	private MagicTower			magicTower;
	Context mcontext = null;

	public TextUtil				tu				= null;
	Timer timer = null;
	TimerWalk timerWalk = null;
	
	public GameScreen(Context context, MagicTower magicTower, MainGame mainGame, boolean isNewGame)
	{
		super(context);
		mcontext = context;
		mMainGame = mainGame;
		paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		this.magicTower = magicTower;
		winWidth = yarin.BORDERW;
		winHeight = yarin.BORDERH;
		borderX = yarin.BORDERX;
		borderY = (int) (yarin.SCREENH * 0.18);
		layerManager = new LayerManager();
		tu = new TextUtil();

		Bitmap mbitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.hero_m);
		hero = new HeroSprite(mbitmap, GameMap.TILE_WIDTH, GameMap.TILE_HEIGHT);
		if(mainGame.gender == 2)
		{
			hero = null;
			Bitmap fbitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.hero_f);
			hero = new HeroSprite(fbitmap, GameMap.TILE_WIDTH, GameMap.TILE_HEIGHT);
		}
		hero.defineReferencePixel(GameMap.TILE_WIDTH / 2, GameMap.TILE_HEIGHT / 2);
		mbitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.map);
		gameMap = new GameMap(hero, mbitmap);
		gameMap.isFirst[gameMap.curFloorNum] = false;
		
		if(mMainGame.mbMusic == 1)
			mMainGame.mCMIDIPlayer.PlayMusic(3);

		fightCalc = new FightCalc(hero);
		task = new Task(this, hero, gameMap);
		hero.setTask(task);

		if (isNewGame == false)
		{
			load();
		}
		layerManager.append(hero);
		layerManager.append(gameMap.getFloorMap());
		
		timer = new Timer();  //used to NPC walking.
		
		
		mshowMessage = true;
		tu.InitText( "        欢迎来到我的冒险世界！在屏幕最上方是“任务栏”，这里会提示你需要完成的任务，只有完成任务才能顺利过关，查看个人分析噢！点击屏幕左下角我的头像，可以随时查看“我的信息”.在它旁边的“背包”存放了购买的物品，饿了的时候打开它就可以吃东西了！对了，捡到的东西也会放在这里~准备好去探索外面的世界了吗？Go！" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffffff, 255, yarin.TextSize);
	}


	@SuppressLint("WrongCall")
	protected void onDraw(Canvas canvas)
	{
		mcanvas = canvas;
		paint.setColor(Color.BLACK);
		yarin.fillRect(canvas, 0, 0, yarin.SCREENW, yarin.SCREENH, paint);

		gameMap.animateMap();
		if(mazeFlag)
			scrollMazeWin();
		else
			scrollWin();
		layerManager.setViewWindow(scrollX, scrollY, winWidth, winHeight);
		layerManager.paint(canvas, 0, yarin.SCREENH * 17/120);
		drawAttr(canvas);

		if (mshowMessage)
		{
			showMessage(strMessage);
		}
		if (mshowDialog)
		{
			dialog();
		}
		if(mshowInfo)
		{
			inforHero();
		}
		if (mFightScreen != null && mshowFight)
		{
			mFightScreen.onDraw(canvas);
		}
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
		paint = null;
		System.gc();
	}

	@SuppressLint("WrongCall")
	private void dealType(int type)
	{
		if ((type == GameMap.MAP_UPSTAIR) || (type == GameMap.MAP_YELLOW_DOOR))
		{
			if(mazeFlag)
			{
				if(gameMap.curFloorNum == 0)  //进入社区
				{
					gameMap.setMap(1);
					if(gameMap.isFirst[1])
						gameMap.isFirst[1] = false;
					layerManager.remove(gameMap.getmazeFloorMap());
					layerManager.append(gameMap.getFloorMap());
					mazeFlag = false;
					hero.setFrame(0);
					timerWalk.cancel();
					if(mMainGame.mbMusic == 1)
						mMainGame.mCMIDIPlayer.PlayMusic(4);
				}
				else if(gameMap.curFloorNum == 1)  //进入迷宫
				{
					gameMap.setMazeMap(0);
					if(gameMap.isMazeFirst[0])
						gameMap.isMazeFirst[0] = false;
					hero.setFrame(0);
					timerWalk = new TimerWalk(this);
					timer.schedule(timerWalk,0,500);
					if(mMainGame.mbMusic == 1)
						mMainGame.mCMIDIPlayer.PlayMusic(7);
				}
			}
			else if(gameMap.curFloorNum == 0)
			{
				if(isDoorOpen)  //进入社区
				{
					gameMap.setMap(1);
					if(gameMap.isFirst[1])
					{
						gameMap.isFirst[1] = false;
						mshowDialog = true;
						tu.InitText( "不告诉母上大人一声就跑出来会不会不太好？没关系反正我已经长大了，最重要的是妈妈刚好不在……" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
					}
					hero.setFrame(0);
					if(mMainGame.mbMusic == 1)
						mMainGame.mCMIDIPlayer.PlayMusic(4);
				}
				else
				{
					mshowDialog = true;
					tu.InitText("好饿啊，还是先吃点东西再出门吧～" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
				}
			}
			else if(gameMap.curFloorNum == 2 || gameMap.curFloorNum == 3)
			{
				if(gameMap.curFloorNum == 2)
					timerWalk.cancel();
				gameMap.setMap(1);
				if(gameMap.isFirst[1])
					gameMap.isFirst[1] = false;
				hero.setFrame(0);
				if(mMainGame.mbMusic == 1)
					mMainGame.mCMIDIPlayer.PlayMusic(4);
			}
			else
				gameMap.upstair();//nonsense

		}
		else if (type == GameMap.MAP_DOWNSTAIR) //nonsense
		{
			gameMap.downstair();
			hero.setFrame(0);
		}
		else if((type >= GameMap.MAP_BED1 && type <= GameMap.MAP_BED4)||(type >= GameMap.MAP_BED5 && type <= GameMap.MAP_BED6))
		{
			if(gameMap.curFloorNum == 0)
			{
				mshowDialog=true;
				tu.InitText("如果能睡到太阳晒屁股真是惬意啊！" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			}
		}
		else if ((type >= GameMap.MAP_BOOK1) && (type <= GameMap.MAP_BOOK4))
		{
			if(gameMap.curFloorNum == 0)
			{
				mshowMessage=true;
				String ss="弗洛伊德性心理学……少年维特的烦恼……周公解梦……自杀论……这些都是我的书么！";
				tu.InitText(ss ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			};
		}
		else if ((type >= GameMap.MAP_DESK1) && (type<=GameMap.MAP_DESK4))
		{
			mmakeChoice = true;
			choicetype = Task.SUPPER;
		}
		else if ((type >= GameMap.MAP_TV1) && (type<=GameMap.MAP_TV4))
		{
			if(gameMap.curFloorNum == 0)
			{
				mshowMessage=true;
				tu.InitText("丁丁……迪西……拉拉……波！……再一次再一次……丁丁……迪西……拉拉……波！……噢天啊！我还是不看了吧……" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			}
		}
		else if(type == GameMap.MAP_HOME_DOOR)
		{
			gameMap.setMap(0);  //回家
			if(gameMap.isFirst[0])
				gameMap.isFirst[0] = false;
			hero.setFrame(0);
			if(mMainGame.mbMusic == 1)
				mMainGame.mCMIDIPlayer.PlayMusic(3);
		}
		else if(type == GameMap.MAP_LOCKED_BARRIER)
		{
			dealTask(Task.BUY_IN_SHOP);
		}
		else if(type == GameMap.MAP_ZA_DOOR)
		{
			gameMap.setMap(2);  //进入杂货店
			if(gameMap.isFirst[2])
				gameMap.isFirst[2] = false;
			hero.setFrame(0);
			timerWalk = new TimerWalk(this);
			timer.schedule(timerWalk,0,500);
			if(mMainGame.mbMusic == 1)
				mMainGame.mCMIDIPlayer.PlayMusic(5);
		}
		else if(type == GameMap.MAP_HOS_DOOR)
		{
			gameMap.setMap(3);//进入医院
			if(gameMap.isFirst[3])
				gameMap.isFirst[3] = false;
			hero.setFrame(0);
			if(mMainGame.mbMusic == 1)
				mMainGame.mCMIDIPlayer.PlayMusic(6);
		}
		else if(type == GameMap.MAP_MURDER)  //凶杀案问卷
		{
			mmakeChoice = true;
			choicetype = Task.MURDER;
		}
		else if(type == GameMap.MAP_OUTDOOR_ROLE1 || type == GameMap.MAP_OUTDOOR_ROLE2)
		{
			mshowMessage=true;
			tu.InitText("年轻人就要到外面闯一闯啊……" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
		}
		else if(type == GameMap.MAP_OUTDOOR_ROLE3)
		{
			mshowMessage=true;
			tu.InitText("爱我的人已经飞走鸟，我爱的人她还没有来到……" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
		}
		else if(type == GameMap.MAP_BUSSTOP1 || type == GameMap.MAP_BUSSTOP2)
		{
			mshowMessage=true;
			tu.InitText("375路，开往西直门南……\n84路，开往北京南站……" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
		}
		else if(type == GameMap.MAP_SHOUSE11 || type == GameMap.MAP_SHOUSE12)
		{
			mshowMessage=true;
			tu.InitText("私人空间，请勿打扰……" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
		}
		else if((type >=GameMap.MAP_HOSDESK1 && type<=GameMap.MAP_DOCTOR) || (type >=GameMap.MAP_HOSDESK4 && type <=GameMap.MAP_HOSDESK6))
		{
			mmakeChoice = true;
			choicetype = Task.HOSPITAL;
		}
		else if((type == GameMap.MAP_ZAHUO_SHOPER) || (type >= GameMap.MAP_SHOPDESK1 && type <= GameMap.MAP_SHOPDESK3))
		{
			mmakeChoice = true;
			choicetype = Task.BUY_IN_SHOP;
		}
		else if(type >= GameMap.MAP_SHOPPER1 && type <= GameMap.MAP_SHOPPER7)
		{
			mshowDialog = true;
			imgType = 3; //3 for boss
			tu.InitText("嗯...你来了？唔，执意要出去闯闯么？那么到老板那里买点必需品吧！出去探险可不是闹着玩的！" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			
		}
		else if(type == GameMap.MAP_MAZE_IN)
		{
			gameMap.setMazeMap(0);  //进入迷宫
			if(gameMap.isMazeFirst[0])
				gameMap.isMazeFirst[0] = false;
			layerManager.remove(gameMap.getFloorMap());
			layerManager.append(gameMap.getmazeFloorMap());
			mazeFlag = true;
			hero.setFrame(0);
			timerWalk = new TimerWalk(this);
			timer.schedule(timerWalk,0,500);
			if(mMainGame.mbMusic == 1)
				mMainGame.mCMIDIPlayer.PlayMusic(7);
		}
		else if(type == GameMap.MAP_MAZE_BACK)
		{
			mazeFlag = false;  //回到社区
			gameMap.setMap(1);
			if(gameMap.isFirst[1])
				gameMap.isFirst[1] = false;
			layerManager.remove(gameMap.getmazeFloorMap());
			layerManager.append(gameMap.getFloorMap());
			hero.setFrame(0);
			timerWalk.cancel();
			if(mMainGame.mbMusic == 1)
				mMainGame.mCMIDIPlayer.PlayMusic(4);
		}
		else if(type == GameMap.MAP_MAZE_GOLDBOX)
		{
			mshowMessage=true;
			tu.InitText("你以为这是哪里，还想着有宝藏？你在逗我呢？！" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
		}
		else if(type == GameMap.MAP_MAZE_SILVERBOX)
		{
			mshowMessage=true;
			tu.InitText("喂喂喂？！连金箱子都没东西，你指望我给你啥？= =#" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
		}
		else if(type >= GameMap.MAP_MAZE_HELPER1 && type <= GameMap.MAP_MAZE_HELPER4)
		{
			mshowMessage=true;
			tu.InitText("我说的话有真有假，君自行判断之~1.这迷宫的宝箱都是空的，别浪费时间打开啦。2.路在下面。" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
		}
		else if(type == GameMap.MAP_MAZE_ROLEA)
		{
			dealTask(Task.FIND_SISTER);
		}
		else if(type == GameMap.MAP_MAZE_ROLED)
		{
			if(task.getTask()[Task.FIND_SISTER] == 0)
			{
				mshowMessage=true;
				tu.InitText("......" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			}
			else
			{
				hero.getTask().updateTaskState(Task.FIND_SISTER);
				dealTask(Task.FIND_SISTER);
			}
		}
		else if(type == GameMap.MAP_MAZE_ROLEB)
		{
			dealTask(Task.SEND_PACK);
			//包裹+1
		}
		else if(type == GameMap.MAP_MAZE_ROLEC)
		{
			if(task.getTask()[Task.SEND_PACK] == 0)
			{
				mshowMessage=true;
				tu.InitText("......" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			}
			else
			{
				if(task.getTask()[Task.SEND_PACK] != 3)
				{
					mmakeChoice = true;
					choicetype = Task.SEND_PACK;
					hero.getTask().updateTaskState(Task.SEND_PACK);
				}
			}
		}
		else if(type == GameMap.MAP_MAZE_WATERFALL)
		{
			if(!done[3])
			{
				done[3] = true;
				mmakeChoice = true;
				choicetype = Task.WATERFALL;
			}
		}
		else if(type == GameMap.MAP_MAZE_FOOD1)
		{
			mshowMessage=true;
			tu.InitText("获得了食物！" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			//beibaocaozuo
			gameMap.changeMazeCell(113, GameMap.MAP_MAZE_ROAD);
		}
		else if(type == GameMap.MAP_MAZE_FOOD2)
		{
			mshowMessage=true;
			tu.InitText("获得了食物！ " ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			//beibaocaozuo
			gameMap.changeMazeCell(58, GameMap.MAP_MAZE_ROAD);
		}
		else if(type == GameMap.MAP_SHOUSE6)  //进入小木屋
		{

			if(gameMap.isMazeFirst[1])
			{
				mmakeChoice = true;
				choicetype = Task.WOODENHOUSE;
			}
			else
			{
				if(hero.usepackforce(4))
				{
					gameMap.isMazeFirst[1] = false;
					gameMap.setMazeMap(1);
					layerManager.remove(gameMap.getmazeFloorMap());
					layerManager.append(gameMap.getmazeFloorMap());
					hero.setFrame(0);
					timerWalk.cancel();
					if(mMainGame.mbMusic == 1)
						mMainGame.mCMIDIPlayer.PlayMusic(8);
				}
				else
				{
					mshowMessage=true;
					tu.InitText("没有万能钥匙，无法进入小木屋！请到社区商店购买或通过做任务获得～ " ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
				}
			}
		}
		else if(type == GameMap.MAP_WOODEN_DOWN)
		{
			mazeFlag = false;
			layerManager.remove(gameMap.getmazeFloorMap());
			layerManager.append(gameMap.getFloorMap());
			gameMap.setMap(4);
			if(gameMap.isFirst[4])
				gameMap.isFirst[4] = false;
			hero.setFrame(0);
			if(mMainGame.mbMusic == 1)
				mMainGame.mCMIDIPlayer.PlayMusic(12);  
		}
		else if(type >= GameMap.MAP_WOODEN_LDESK1 && type <= GameMap.MAP_WOODEN_LDESK4)
		{
			if(!done[0])
			{
				done[0] = true;
				mmakeChoice = true;
				choicetype = Task.DESKTYPE;
			}
		}
		else if(type == GameMap.MAP_WOODEN_DESK6)
		{
			if(!done[1])
			{
				done[1] = true;
				mmakeChoice = true;
				choicetype = Task.HUAPINGTYPE;
			}
			else
				if(!done[2])
				{
					done[2] = true;
					mmakeChoice = true;
					choicetype = Task.WATERVECTOR;
				}
		}
		else if(type == GameMap.MAP_WOODEN_UP)
		{
			mazeFlag = true;
			layerManager.remove(gameMap.getFloorMap());
			layerManager.append(gameMap.getmazeFloorMap());
			gameMap.setMazeMap(1);
			hero.setFrame(0);
			if(mMainGame.mbMusic == 1)
				mMainGame.mCMIDIPlayer.PlayMusic(8);
		}
		else if(type == GameMap.MAP_MAZE_OUTDOOR)  //判定是否已完成所有任务，再出去
		{
			if(task.getTask()[Task.FIND_SISTER] >= 3 && task.getTask()[Task.SEND_PACK] >= 3 && task.getTask()[Task.WATERFALL] >= 3
			  && task.getTask()[Task.WOODENHOUSE] >= 3 && task.getTask()[Task.DESKTYPE] >= 3 && task.getTask()[Task.WATERVECTOR] >= 3
			  && task.getTask()[Task.HUAPINGTYPE] >= 3)
			{
				//进入下一个场景
				hero.buff[0] = 1;
				GateFinish = true;
			}
			else
			{
				mshowMessage = true;
				tu.InitText("还没有完成迷宫的所有任务哦～完成所有任务方可进入下一场景！" ,  -20, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
			}
		}
		
		//emotion random
		if(imgType <= 1)  //0 or 1 is player's role
		{
			Random rand = new Random();
			imgType = rand.nextInt(2); 
		}
	}

	public void showMessage(String msg)
	{
		int w = yarin.SCREENW + 48;
		int h = yarin.MessageBoxH;
		int x = -24;
		int y = (yarin.SCREENH - yarin.MessageBoxH) / 2;
		Paint ptmPaint = new Paint();
		ptmPaint.setARGB(255, Color.red(BACK_COLOR), Color.green(BACK_COLOR), Color.blue(BACK_COLOR));

		Bitmap img = BitmapFactory.decodeResource(this.getResources(), R.drawable.dialog);
		yarin.drawImage(mcanvas, img, -100, -580);
		//yarin.fillRect(mcanvas, x, y, w, h * 2, ptmPaint);

		tu.DrawText(mcanvas);
		ptmPaint = null;
	}

	private void inforHero() 
	{
		Paint ptmPaint = new Paint();
		tpaint = new TextPaint();
		ptmPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		ptmPaint.setTextSize(30);
		ptmPaint.setARGB(255, Color.red(0xffff00), Color.green(0xffff00), Color.blue(0xffff00));
		
		if(mMainGame.gender == 1)
			yarin.drawImage(mcanvas, BitmapFactory.decodeResource(getResources(), R.drawable.info_m), 80, yarin.SCREENH * 1/3);
		else
			yarin.drawImage(mcanvas, BitmapFactory.decodeResource(getResources(), R.drawable.info_f), 80, yarin.SCREENH * 1/3);
		
		yarin.drawString(mcanvas, String.valueOf(hero.getAttack()), 170 + yarin.SCREENW / 2, 215 + yarin.SCREENH / 3, ptmPaint);
		yarin.drawString(mcanvas, String.valueOf(hero.getHp()), 170 + yarin.SCREENW / 2, 275 + yarin.SCREENH / 3, ptmPaint);
		yarin.drawString(mcanvas, String.valueOf(hero.getMoney()), 170 + yarin.SCREENW / 2, 330 + yarin.SCREENH / 3, ptmPaint);
		yarin.drawString(mcanvas, String.valueOf(hero.getDefend()), 170 + yarin.SCREENW / 2, 385 + yarin.SCREENH / 3, ptmPaint);
	}

	private void drawAttr(Canvas canvas)
	{
		final int h2= (int) (yarin.SCREENH * 17/120);
		Paint ptmPaint = new Paint();
		tpaint = new TextPaint();
		ptmPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		ptmPaint.setTextSize(GameScreen.SMALL_FONT);
		ptmPaint.setARGB(255, Color.red(0xffff00), Color.green(0xffff00), Color.blue(0xffff00));

		yarin.drawRect(canvas, 0, 0, yarin.SCREENW, h2, ptmPaint);
		
		yarin.drawImage(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.up), yarin.SCREENW - 240, yarin.SCREENH - 360);
		yarin.drawImage(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.down), yarin.SCREENW - 240, yarin.SCREENH - 120);
		yarin.drawImage(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.left), yarin.SCREENW - 360, yarin.SCREENH - 240);
		yarin.drawImage(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.right), yarin.SCREENW - 120, yarin.SCREENH - 240);
		yarin.drawImage(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.button), yarin.SCREENW / 3, yarin.SCREENH - 200);
		
		if(mMainGame.gender == 2)
			yarin.drawImage(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.herofe), 10, yarin.SCREENH - 130);
		else if(mMainGame.gender == 1)
			yarin.drawImage(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.heroma), 10, yarin.SCREENH - 130);

		ptmPaint.setTextSize(GameScreen.SMALL_FONT);
		ptmPaint.setARGB(255, Color.red(0xffffff), Color.green(0xffffff), Color.blue(0xffffff));
		yarin.drawString(canvas, "人物属性", 22, yarin.SCREENH - 17, ptmPaint);
		tpaint.setARGB(255, Color.red(0xffff00), Color.green(0xffff00), Color.blue(0xffff00));
		tpaint.setTextSize(35);

		String string;
		StaticLayout layout = null;
		if(!mazeFlag)
		{
			string = GuideHelp.advice[gameMap.curFloorNum];
			layout = new StaticLayout(string,tpaint,1150,Alignment.ALIGN_NORMAL,1.0F,0.0F,true);
		}
		else
		{
			string = GuideHelp.Mazeadvice[gameMap.curFloorNum];
			layout = new StaticLayout(string,tpaint,1150,Alignment.ALIGN_NORMAL,1.0F,0.0F,true);
		}
		canvas.translate(20, 20);
		layout.draw(canvas);
		ptmPaint = null;
	}


	private void scrollWin()
	{
		scrollX = hero.getRefPixelX() - winWidth / 2;
		scrollY = hero.getRefPixelY() - winHeight / 2;
		if (scrollX < 0)
		{
			scrollX = 0;
		}
		else if ((scrollX + winWidth) > GameMap.MAP_WIDTH)
		{
			scrollX = GameMap.MAP_WIDTH - winWidth;
		}
		if (scrollY < 0)
		{
			scrollY = 0;
		}
		else if ((scrollY + winHeight) > GameMap.MAP_HEIGHT)
		{
			scrollY = GameMap.MAP_HEIGHT - winHeight;
		}
	}

	private void scrollMazeWin()
	{
		scrollX = hero.getRefPixelX() - winWidth / 2;
		scrollY = hero.getRefPixelY() - winHeight / 2;
		if (scrollX < 0)
		{
			scrollX = 0;
		}
		else if ((scrollX + winWidth) > GameMap.maze_MAP_WIDTH)
		{
			scrollX = GameMap.maze_MAP_WIDTH - winWidth;
		}
		if (scrollY < 0)
		{
			scrollY = 0;
		}
		else if ((scrollY + winHeight) > GameMap.maze_MAP_HEIGHT)
		{
			scrollY = GameMap.maze_MAP_HEIGHT - winHeight;
		}
	}

	public boolean fight(int type)
	{
		if (fightCalc.canAttack(type) == false)
			return false;
		mFightScreen = new FightScreen(this, fightCalc, hero, type);
		mshowFight = true;
		gameMap.remove();
		return true;
	}

	private void dealTask(int type)
	{
		int curTask = -1;
		switch (type)
		{
			case Task.BUY_IN_SHOP:
				curTask = Task.BUY_IN_SHOP;
				break;
			case Task.FIND_SISTER:
				curTask = Task.FIND_SISTER;
				break;
			case Task.SEND_PACK:
				curTask = Task.SEND_PACK;
				break;
		}
		if (curTask == -1)
			return;
		task.execTask(curTask);
	}


	public void dialog()
	{
		int x, y, w, h;
		w = yarin.SCREENW;
		h = yarin.MessageBoxH;
		x = -100;
		y = -580;

		drawDialogBox(mMainGame.gender ,imgType , x, y, w, h);
		
		tu.DrawText(mcanvas);
	}


	private void drawDialogBox(int gender, int imgType, int x, int y, int w, int h)
	{
		Paint ptmPaint = new Paint();
		ptmPaint.setARGB(255, Color.red(BACK_COLOR), Color.green(BACK_COLOR), Color.blue(BACK_COLOR));

		Bitmap img = BitmapFactory.decodeResource(this.getResources(), R.drawable.man_hap);
		if(gender == 1)
        {
            img = null;
			if(imgType == 0)
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.man_hap);
            else
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.man_sad);
        }
		else if(gender == 2)
        {
            if(imgType == 0)
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.girl_hap);
            else
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.girl_sad);
        }
		
		if(imgType == 3)
		{
			img = BitmapFactory.decodeResource(this.getResources(), R.drawable.boss);
		}
		
		yarin.drawImage(mcanvas, img, x, y);

		ptmPaint = null;
	}


	protected void keyPressed(int keyCode)
	{

		// switch(keyCode){
		// case GameCanvas.KEY_NUM1: jump();break;
		// case GameCanvas.KEY_NUM3: lookup();break;
		// }
	}


	public void end()
	{
		// stop();
		// EndScreen end = new EndScreen(display,menu);
		// display.setCurrent(end);
		// end.start();
	}


	public Bitmap getImage(int type)
	{
		Bitmap result = null;
		switch (type)
		{

			case IMAGE_MAP:
				result = BitmapFactory.decodeResource(this.getResources(), R.drawable.map);
				break;
			
		}
		return result;
	}


	boolean save()
	{
		
		int col = hero.getRefPixelX() / GameMap.TILE_WIDTH;
		int row = hero.getRefPixelY() / GameMap.TILE_HEIGHT;
		byte[] r1 = hero.encode();
		byte[] r2 = { (byte) gameMap.curFloorNum, (byte) row, (byte) col, (byte) hero.getFrame() };
		byte[] r3 = task.getTask();

		Properties properties = new Properties();

		properties.put("music", String.valueOf(mMainGame.mbMusic));

		properties.put("r1l", String.valueOf(r1.length));
		properties.put("r2l", String.valueOf(r2.length));
		properties.put("r3l", String.valueOf(r3.length));
		for (int i = 0; i < r1.length; i++)
		{
			properties.put("r1_" + i, String.valueOf(r1[i]));
		}
		for (int i = 0; i < r2.length; i++)
		{
			properties.put("r2_" + i, String.valueOf(r2[i]));
		}
		for (int i = 0; i < r3.length; i++)
		{
			properties.put("r3_" + i, String.valueOf(r3[i]));
		}

		for (int i = 0; i < GameMap.FLOOR_NUM; i++)
		{
			byte map[] = gameMap.getFloorArray(i);
			for (int j = 0; j < map.length; j++)
			{
				properties.put("map_" + i + "_" + j, String.valueOf(map[j]));
			}
		}

		try
		{
			FileOutputStream stream = magicTower.openFileOutput("save", Context.MODE_WORLD_WRITEABLE);
			properties.store(stream, "");
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}

		return true;
	}


	boolean load()
	{
		return false;

	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int type = 0;

		//继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();

            if(x2>=yarin.SCREENW / 3 && x2<=120 + yarin.SCREENW / 3 && y2 >=yarin.SCREENH - 200 && y2<=yarin.SCREENH - 80)
            {
            	if(! (mshowMessage || mshowDialog))
            	{
            		if(hero.getpacksum() > 0)
                	{
                		isBackpack = true;
                		return true;
                	}
                	else
                	{
                		Toast.makeText(mcontext, "背包里什么都没有哦～", Toast.LENGTH_LONG).show();
                	}
            	}
            }
            else if(x2 >= 10 && x2 <= 85 && y2 >= yarin.SCREENH-130  && y2 <= yarin.SCREENH-17)
            {
            	if(! (mshowMessage || mshowDialog))
            		mshowInfo = true;
            	/*
            	if(mMainGame.gender == 1)
            		heroInfor = 1;
            	else if(mMainGame.gender == 2)
            		heroInfor = 2;
            	*/
            }
            else 
            {
				mshowInfo = false;
			}
            if(x2>=yarin.SCREENW-240 && x2<=yarin.SCREENW-120 && y2>=yarin.SCREENH-360 && y2<=yarin.SCREENH-240)
            {
            	if(! (mshowMessage || mshowDialog))
            	{
            		if(!mazeFlag)
                	{
                		if(swap)
                		{
                			hero.setFrame(10);
                			swap = !swap;
                		}
                		else
                		{
                			hero.setFrame(11);
                			swap = !swap;
                		}
                		if ((type = gameMap.canPass(UP)) == 1 || (type = gameMap.canPass(UP)) == 3 ||
                			(type = gameMap.canPass(UP)) == 5 ||
                			(type = gameMap.canPass(UP)) == 99 || (type = gameMap.canPass(UP)) == 154) //MAP_ROAD=1 and 3 and 5
                		{
                			hero.move(0, -step);
                		}
                	}
                	else
                	{
                		if(swap)
                		{
                			hero.setFrame(10);
                			swap = !swap;
                		}
                		else
                		{
                			hero.setFrame(11);
                			swap = !swap;
                		}
                		if ((type = gameMap.canPassMaze(UP)) == 1 || (type = gameMap.canPassMaze(UP)) == 3 ||
                			(type = gameMap.canPassMaze(UP)) == 5  ||(type = gameMap.canPassMaze(UP)) == 83 ||
                			(type = gameMap.canPassMaze(UP)) == 99 || (type = gameMap.canPassMaze(UP)) == 154)

                			 //MAP_ROAD=1 and 3 and 5
                		{
                			hero.move(0, -step);
                		}
                	}
            	}
            }
            else if(x2>=yarin.SCREENW - 240 && x2<=yarin.SCREENW - 120 && y2>=yarin.SCREENH - 120 && y2<=yarin.SCREENH)
            {
            	if(! (mshowMessage || mshowDialog))
            	{
            		if(!mazeFlag)
                	{
                		if(swap)
                		{
                			hero.setFrame(1);
                			swap = !swap;
                		}
                		else
                		{
                			hero.setFrame(2);
                			swap = !swap;
                		}
                		if ((type = gameMap.canPass(DOWN)) == 1 || (type = gameMap.canPass(DOWN)) == 3 ||
                			(type = gameMap.canPass(DOWN)) == 5 ||
                			(type = gameMap.canPass(DOWN)) == 99 || (type = gameMap.canPass(DOWN)) == 154)
                		{
                			hero.move(0, step);
                		}
                	}
                	else
                	{
                		if(swap)
                		{
                			hero.setFrame(1);
                			swap = !swap;
                		}
                		else
                		{
                			hero.setFrame(2);
                			swap = !swap;
                		}
                		if ((type = gameMap.canPassMaze(DOWN)) == 1 || (type = gameMap.canPassMaze(DOWN)) == 3 ||
                			(type = gameMap.canPassMaze(DOWN)) == 5 || (type = gameMap.canPassMaze(DOWN)) == 83 ||
                			(type = gameMap.canPassMaze(DOWN)) == 99 || (type = gameMap.canPassMaze(DOWN)) == 154)
                		{
                			hero.move(0, step);
                		}
                	}
            	}
            }
            else if(x2>=yarin.SCREENW-360 && x2<=yarin.SCREENW-240 && y2>=yarin.SCREENH-240 && y2<=yarin.SCREENH-120)
            {
            	if(! (mshowMessage || mshowDialog))
            	{
            		if(!mazeFlag)
                	{
                		if(swap)
                		{
                			hero.setFrame(4);
                			swap = !swap;
                		}
                		else
                		{
                			hero.setFrame(5);
                			swap = !swap;
                		}
                		if ((type = gameMap.canPass(LEFT)) == 1 || (type = gameMap.canPass(LEFT)) == 3 ||
                			(type = gameMap.canPass(LEFT)) == 5 ||
                			(type = gameMap.canPass(LEFT)) == 99 || (type = gameMap.canPass(LEFT)) == 154)
                		{
                			hero.move(-step, 0);
                		}
                	}
                	else
                	{
                		if(swap)
                		{
                			hero.setFrame(4);
                			swap = !swap;
                		}
                		else
                		{
                			hero.setFrame(5);
                			swap = !swap;
                		}
                		if ((type = gameMap.canPassMaze(LEFT)) == 1 || (type = gameMap.canPassMaze(LEFT)) == 3 ||
                			(type = gameMap.canPassMaze(LEFT)) == 5 || (type = gameMap.canPassMaze(LEFT)) == 83 ||
                			(type = gameMap.canPassMaze(LEFT)) == 99 || (type = gameMap.canPassMaze(LEFT)) == 154)
                		{
                			hero.move(-step, 0);
                		}
                	}
            	}
            }
            else if(x2>=yarin.SCREENW-120 && x2<=yarin.SCREENW && y2>=yarin.SCREENH-240 && y2<=yarin.SCREENH-120)
            {
            	if(! (mshowMessage || mshowDialog))
            	{
            		if(!mazeFlag)
                	{
                		if(swap)
                		{
                			hero.setFrame(7);
                			swap = !swap;
                		}
                		else
                		{
                			hero.setFrame(8);
                			swap = !swap;
                		}
                		if ((type = gameMap.canPass(RIGHT)) == 1 || (type = gameMap.canPass(RIGHT)) == 3 ||
                			(type = gameMap.canPass(RIGHT)) == 5 ||
                			(type = gameMap.canPass(RIGHT)) == 99 || (type = gameMap.canPass(RIGHT)) == 154)
                		{
                			hero.move(step, 0);
                		}
                	}
                	else
                	{
                		if(swap)
                		{
                			hero.setFrame(7);
                			swap = !swap;
                		}
                		else
                		{
                			hero.setFrame(8);
                			swap = !swap;
                		}
                		if ((type = gameMap.canPassMaze(RIGHT)) == 1 || (type = gameMap.canPassMaze(RIGHT)) == 3 ||
                			(type = gameMap.canPassMaze(RIGHT)) == 5 || (type = gameMap.canPassMaze(RIGHT)) == 83 ||
                			(type = gameMap.canPassMaze(RIGHT)) == 99 || (type = gameMap.canPassMaze(RIGHT)) == 154)
                		{
                			hero.move(step, 0);
                		}
                	}
            	}
            	
            }
            else
            {
            	if(mshowMessage)
            		mshowMessage = false;
            	if(mshowDialog)
            		mshowDialog = false;
            }
        }
        
        
        if (type >= GameMap.MAP_BED1)
			dealType(type);
        return false;
	}

	public boolean onTouch(MotionEvent event)
	{
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode) {
		// TODO Auto-generated method stub
		int type = 0;
		if (keyCode == KeyEvent.KEYCODE_1)
		{
			mMainGame.mCMIDIPlayer.StopMusic();
			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_2)
		{
			mMainGame.mCMIDIPlayer.PlayMusic(2);
		}
		if (mFightScreen != null && mshowFight)
		{
			mFightScreen.onKeyUp(keyCode);
			return false;
		}
		if ((mshowMessage && keyCode != yarin.KEY_DPAD_OK) || (mshowDialog && keyCode != yarin.KEY_DPAD_OK))
		{
			return false;
		}
		switch (keyCode)
		{
			case yarin.KEY_SOFT_RIGHT:
				//save();
				mMainGame.controlView(yarin.GAME_MENU);
				if (mMainGame.mbMusic == 1)
				{
					mMainGame.mCMIDIPlayer.PlayMusic(1);
				}
				break;
			default:
				break;
		}
		if (type >= GameMap.MAP_LOCKED_BARRIER)
			dealType(type);
		return false;
	}
}

