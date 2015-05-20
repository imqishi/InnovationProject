package com.mota;

import android.R.integer;

public class TimerWalk extends java.util.TimerTask{
	GameScreen mgameScreen = null;
	int type[] = new int[6];
	
	public TimerWalk(GameScreen gameScreen) {
		mgameScreen = gameScreen;
	}
	
	@Override
	public void run() {
		if(mgameScreen.mazeFlag && mgameScreen.gameMap.curFloorNum == 0)
		{
			type[0] = mgameScreen.gameMap.canPassMaze(mgameScreen.UP);
			type[1] = mgameScreen.gameMap.canPassMaze(mgameScreen.DOWN);
			type[2] = mgameScreen.gameMap.canPassMaze(mgameScreen.LEFT);
			type[3] = mgameScreen.gameMap.canPassMaze(mgameScreen.RIGHT);
			if(((type[1] < 179) || (type[1] > 182)) &&
					((type[2] < 179) || (type[2] > 182)) &&
					((type[3] < 179) || (type[3] > 182)) &&
					((type[0] < 179) || (type[0] > 182)))
			{
				switch (mgameScreen.walkFlag) {
				case 0:
					mgameScreen.gameMap.changeMazeCell(312, 83);
					mgameScreen.gameMap.changeMazeCell(313, 180);
					break;
				case 1:
					mgameScreen.gameMap.changeMazeCell(313, 83);
					mgameScreen.gameMap.changeMazeCell(314, 179);
					break;
				case 2:
					mgameScreen.gameMap.changeMazeCell(314, 181);
					break;
				case 3:
					mgameScreen.gameMap.changeMazeCell(314, 83);
					mgameScreen.gameMap.changeMazeCell(313, 182);
					break;
				case 4:
					mgameScreen.gameMap.changeMazeCell(313, 83);
					mgameScreen.gameMap.changeMazeCell(312, 181);
					break;
				case 5:
					mgameScreen.gameMap.changeMazeCell(312, 179);
					break;
				default:
					break;
				}
				mgameScreen.walkFlag = (mgameScreen.walkFlag + 1) % 6;
			}
		}
		
		else if(mgameScreen.mazeFlag == false && mgameScreen.gameMap.curFloorNum == 2)
		{
			type[0] = mgameScreen.gameMap.canPass(mgameScreen.UP);
			type[1] = mgameScreen.gameMap.canPass(mgameScreen.DOWN);
			type[2] = mgameScreen.gameMap.canPass(mgameScreen.LEFT);
			type[3] = mgameScreen.gameMap.canPass(mgameScreen.RIGHT);
			if(((type[1] < 190) || (type[1] > 197)) &&
					((type[2] < 190) || (type[2] > 197)) &&
					((type[3] < 190) || (type[3] > 197)) &&
					((type[0] < 190) || (type[0] > 197)))
			{
				switch (mgameScreen.walkFlag) {
				case 0:
					mgameScreen.gameMap.changeCell(38, 1);
					mgameScreen.gameMap.changeCell(49, 191);
					break;
				case 1:
					mgameScreen.gameMap.changeCell(49, 194);
					break;
				case 2:
					mgameScreen.gameMap.changeCell(49, 1);
					mgameScreen.gameMap.changeCell(50, 195);
					break;
				case 3:
					mgameScreen.gameMap.changeCell(50, 1);
					mgameScreen.gameMap.changeCell(51, 194);
					break;
				case 4:
					mgameScreen.gameMap.changeCell(51, 1);
					mgameScreen.gameMap.changeCell(52, 195);
					break;
				case 5:
					mgameScreen.gameMap.changeCell(52, 1);
					mgameScreen.gameMap.changeCell(53, 194);
					break;
				case 6:
					mgameScreen.gameMap.changeCell(53, 1);
					mgameScreen.gameMap.changeCell(54, 195);
					break;
				case 7:
					mgameScreen.gameMap.changeCell(54, 196);
					break;
				case 8:
					mgameScreen.gameMap.changeCell(54, 1);
					mgameScreen.gameMap.changeCell(43, 197);
					break;
				case 9:
					mgameScreen.gameMap.changeCell(43, 193);
					break;
				case 10:
					mgameScreen.gameMap.changeCell(43, 1);
					mgameScreen.gameMap.changeCell(42, 192);
					break;
				case 11:
					mgameScreen.gameMap.changeCell(42, 1);
					mgameScreen.gameMap.changeCell(41, 193);
					break;
				case 12:
					mgameScreen.gameMap.changeCell(41, 1);
					mgameScreen.gameMap.changeCell(40, 192);
					break;
				case 13:
					mgameScreen.gameMap.changeCell(40, 1);
					mgameScreen.gameMap.changeCell(39, 193);
					break;
				case 14:
					mgameScreen.gameMap.changeCell(39, 1);
					mgameScreen.gameMap.changeCell(38, 192);
					break;
				case 15:
					mgameScreen.gameMap.changeCell(38, 190);
					break;
				default:
					break;
				}
				mgameScreen.walkFlag = (mgameScreen.walkFlag + 1) % 16;
			}
		}
	}

}
