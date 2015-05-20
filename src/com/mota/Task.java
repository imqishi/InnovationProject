package com.mota;

public class Task
{
	//hold the task state
	private static final int UNRECIEVE = 0,  //don't recieve task
							 RECIEVED = 1,   //have recieved task,can't finish
							 CANFINISH = 2,  //can finish task,but no finish
							 FINISHED = 3;   //finished
	//hold the task type
	public static final int  BUY_IN_SHOP = 0,
			                 SUPPER = 1,
			                 HOSPITAL = 2,
			                 FIND_SISTER = 3,
			                 SEND_PACK = 4,
			                 WATERFALL = 5,
			                 WOODENHOUSE = 6,
			                 DESKTYPE = 7,
			                 HUAPINGTYPE = 8,
			                 WATERVECTOR = 9,
			                 MURDER = 10;
							 
							
	private byte[] taskState = 
		{
			1, //0:购买物品方可打开北面关卡 
			3,
			3,
			0,
			0,
			2,
			2,
			2,
			2,
			2
		};
	private GameScreen gameScreen;
	private HeroSprite hero;
	private GameMap gameMap;
	public int curTask;
	public int curTask2;
	public boolean mbtask = false;
	
	public Task(GameScreen gameScreen,HeroSprite hero,GameMap gameMap)
	{
		this.gameScreen = gameScreen;
		this.hero = hero;
		this.gameMap = gameMap;
	}
	
	public void execTask(int curTask)
	{
		this.curTask = curTask;
		this.curTask2 = 0;
		switch (taskState[curTask])
		{
			case UNRECIEVE:
				mbtask = false;
				recieveTask();
				break;
			case RECIEVED:
				switch(curTask)
				{
					case BUY_IN_SHOP:
						if (hero.getpacksum() > 0)
						{
							mbtask = true;	
							finishTask();
							hero.addExperience(10);
						}
						else
						{
							mbtask = false;	
							gameScreen.mshowMessage = true;
							gameScreen.tu.InitText("外面的世界太危险，现在还不是时候，再转转吧……", 0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
						}
						break;
					case FIND_SISTER:
						gameScreen.mshowMessage=true;
						gameScreen.tu.InitText("好心人，帮我找到妹妹了吗？怎么还没见她回来呢？" ,  0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
						break;
					case SEND_PACK:
						gameScreen.mshowMessage = true;
						gameScreen.tu.InitText("快去找出口吧～", 0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
						break;
				}
				
				break;
			case CANFINISH:
				mbtask = true;	
				switch(curTask)
				{
					case FIND_SISTER:
						gameScreen.mshowMessage=true;
						gameScreen.tu.InitText("谢谢你的帮助！送你一枚万能钥匙作为回报好啦～" ,  0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
						break;
					case SEND_PACK:
						gameScreen.mshowMessage = true;
						gameScreen.tu.InitText("十分感谢！给你50金币作为酬谢！", 0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
						finishTask();
						break;
				}
				finishTask();
				break;
			case FINISHED:
				break;
		}
	}
	
	private void recieveTask()
	{
		taskState[curTask]++;
		switch (curTask)
		{
			case BUY_IN_SHOP:
				gameMap.remove();
				break;
			case FIND_SISTER:
				gameScreen.mshowMessage=true;
				gameScreen.tu.InitText("拜托帮我找找我那迷失在森林里孤苦无依、不知所措的孩子吧……" ,  0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
				break;
			case SEND_PACK:
				if(hero.setpack(17))
				{
					gameScreen.mshowMessage=true;
					gameScreen.tu.InitText("什么？你在找出口？那麻烦你帮我带个包裹给出口附近的朋友吧！ " ,  0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
				}
				else
				{
					gameScreen.mshowMessage=true;
					gameScreen.tu.InitText("背包空间不足！请清理后再完成任务！" ,  0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
					taskState[curTask]--;
				}
				break;

		}
	}
	private void finishTask()
	{
		taskState[curTask]++;
		switch (curTask)
		{
			case BUY_IN_SHOP:
				gameMap.changeCell(1, GameMap.MAP_MAZE_IN);
				taskState[curTask] = FINISHED;
				break;
			case FIND_SISTER:
				if(hero.setpack(4))
				{
					hero.addExperience(15);
					taskState[curTask] = FINISHED;
				}
				else
				{
					gameScreen.mshowMessage=true;
					gameScreen.tu.InitText("背包空间不足！请清理后再完成任务！" ,  0, (yarin.SCREENH-yarin.MessageBoxH)/2, yarin.SCREENW, yarin.MessageBoxH, 0x0, 0xffff00, 255, yarin.TextSize);
					taskState[curTask]--;
				}
				break;
			case SEND_PACK:
				hero.addExperience(35);
				hero.addMoney(50);
				hero.usepackforce(17);
				taskState[curTask] = FINISHED;
				break;
		}
	}
	
	public void updateTaskState(int type)
	{
		taskState[type] ++;
	}
	
	public void reTaskState(int type)
	{
		taskState[type] --;
	}
	
	public byte[] getTask()
	{
		return taskState;
	}
	
	public void setTask(byte[] data)
	{
		taskState = data;
	}
}

