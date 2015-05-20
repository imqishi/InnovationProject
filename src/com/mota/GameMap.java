package com.mota;

import javax.game.TiledLayer;

import android.graphics.Bitmap;

public class GameMap
{
	private static final int[][] heroPosition = 
	{
		{104,12,102,106,32,111,109,115,11,39,70,111,119,111,104,3,5, 93, 111,119,39,60},
		{104,12,102,106,32, 108,103,1,51,83,99,119,111,114,5,7,71,111,119,49, 71,60}	
	};
	
	public static final int			SWITCH_OFFSET	= 44;
	public static final int			TILE_WIDTH		= 120;
	public static final int			TILE_HEIGHT		= TILE_WIDTH;
	private static final int			TILE_NUM_COL	= 11;
	private static final int			TILE_NUM_ROW	= TILE_NUM_COL;
	public static final int			MAP_WIDTH		= TILE_WIDTH * TILE_NUM_COL;
	public static final int			MAP_HEIGHT		= MAP_WIDTH;
	public static final int			FLOOR_NUM		= 22;
	public static final int			TILE_NUM		= TILE_NUM_COL * TILE_NUM_ROW;
	//Maze Data
	public static final int			maze_TILE_NUM_COL = 30;
	public static final int 		maze_TILE_NUM_ROW = maze_TILE_NUM_COL;
	public static final int			maze_MAP_WIDTH		= TILE_WIDTH * maze_TILE_NUM_COL;
	public static final int			maze_MAP_HEIGHT		= maze_MAP_WIDTH;
	public static final int			maze_TILE_NUM		= maze_TILE_NUM_COL * maze_TILE_NUM_ROW;
	
	private int						curUpDown		= 0;

	private int[]						curFloorArray	= new int[TILE_NUM];
	private int[]					mazeFloorArray = new int[maze_TILE_NUM];
	public boolean []isFirst = new boolean[20];  //从0到最后场景是否是第一次进入 
	public boolean []isMazeFirst = new boolean[20];
	
	public int							curFloorNum		= 0;
	public int							reachedHighest	= 0;
	protected boolean					mazeFlag = false;
	private TiledLayer					floorMap		= null;
	private TiledLayer					MazefloorMap	= null;
	private HeroSprite					hero			= null;
	
	private int						aheadIndex		= 0;
	private int						aheadCol		= 0;
	private int						aheadRow		= 0;

	public GameMap(HeroSprite hero, Bitmap bmap)
	{
		this.hero = hero;
		reFirst(isFirst);
		reFirst(isMazeFirst);
		floorMap = new TiledLayer(TILE_NUM_COL, TILE_NUM_ROW, bmap, TILE_WIDTH, TILE_HEIGHT);
		MazefloorMap = new TiledLayer(maze_TILE_NUM_COL, maze_TILE_NUM_ROW, bmap, TILE_WIDTH, TILE_HEIGHT);
		setMap(curFloorNum);
	}


	public void setMap(int floorNum)
	{
		int[] colrow;
		colrow = getColRow(heroPosition[curUpDown][floorNum]);
		
		for (int i = 0; i < TILE_NUM; i++)
		{
			curFloorArray[i] = floorArray[floorNum][i];
		}
		for (int i = 0; i < TILE_NUM; i++)
		{
			colrow = getColRow(i);
			floorMap.setCell(colrow[0], colrow[1], floorArray[floorNum][i]);
		}

		if(mazeFlag)
		{
			if(floorNum == 1)
				colrow = getColRow(12);
			else if(floorNum == 4)
				colrow = getColRow(35);
		}
		else
		{
			if(floorNum == 1)
			{
				if(curFloorNum == 0)
					colrow = getColRow(93);
				else if(curFloorNum == 2)
					colrow = getColRow(42);
				else if(curFloorNum == 3)
					colrow = getColRow(37);
			}
			else if(floorNum == 0)
				colrow = getColRow(104);
			else if(floorNum == 2)
				colrow = getColRow(102);
			else if(floorNum == 3)
				colrow = getColRow(106);
		}
		int x = (int) (colrow[0] * TILE_WIDTH + TILE_WIDTH / 2);
		int y = (int) (colrow[1] * TILE_HEIGHT + TILE_HEIGHT / 2);
		hero.setRefPixelPosition(x, y);
		mazeFlag = false;
		curFloorNum = floorNum;
	}
	
	public void setMazeMap(int floorNum)
	{
		int[] colrow;
		
		for (int i = 0; i < maze_TILE_NUM; i++)
		{
			mazeFloorArray[i] = MazefloorArray[floorNum][i];
		}
		for (int i = 0; i < maze_TILE_NUM; i++)
		{
			colrow = getMazeColRow(i);
			MazefloorMap.setCell(colrow[0], colrow[1], MazefloorArray[floorNum][i]);
		}
		
		if(floorNum == 0)
		{
			if(!mazeFlag)
				hero.setRefPixelPosition(TILE_WIDTH * 3 / 2, TILE_HEIGHT * 3 / 2);
			else
				if(curFloorNum == 1)
					hero.setRefPixelPosition((int) (TILE_WIDTH * 13.5), (int) (TILE_HEIGHT * 25.5));
		}
		else if(floorNum == 1)
		{
			if(mazeFlag)
				hero.setRefPixelPosition((int) (TILE_WIDTH * 25.5), (int) (TILE_HEIGHT * 28.5));
			else
				hero.setRefPixelPosition((int) (TILE_WIDTH * 1.5), (int) (TILE_HEIGHT * 19.5));
		}
		mazeFlag = true;
		curFloorNum = floorNum;
	}


	private int[] getColRow(int index)
	{
		int[] result = new int[2];

		result[0] = index % TILE_NUM_COL;
		result[1] = (index - result[0]) / TILE_NUM_ROW;
		return result;
	}

	private int[] getMazeColRow(int index)
	{
		int[] result = new int[2];
		
		result[0] = index % maze_TILE_NUM_COL;
		result[1] = (index - result[0]) / maze_TILE_NUM_ROW;
		return result;
	}
	

	public void animateMap()                                       //动态图效果制作
	{
		/*int switchedCell;
		for (int i = 0; i < TILE_NUM; i++)
		{
			switchedCell = 0;
			int type = floorArray[curFloorNum][i];
			if (type == MAP_DESK4)
			{
				switchedCell = MAP_DESK4;
			}
			else if (type == MAP_SHOP2)
			{
				switchedCell = MAP_SHOP2;
			}
			else if (type == MAP_BED1)
			{
				switchedCell = MAP_BED1;
			}
			else if (type == MAP_BED2)
			{
				switchedCell = MAP_BED2;
			}
			else if (type == MAP_BED3)
			{
				switchedCell = MAP_BED3;
			}
			else if (type == MAP_BED4)
			{
				switchedCell = MAP_BED4;
			}
			else if ((type >= MAP_ANGLE) && (type <= MAP_ORGE31))
			{
				switchedCell = type + SWITCH_OFFSET;
			}
			
			if (switchedCell != 0)
			{
				changeCell(i, switchedCell);
			}
		}*/
	}


	public TiledLayer getFloorMap()
	{
		return floorMap;
	}

	public TiledLayer getmazeFloorMap()
	{
		return MazefloorMap;
	}
	
	public int upstair()
	{
		if ((curFloorNum + 1) < FLOOR_NUM)
		{
			curUpDown = 0;
			setMap(++curFloorNum);
			if (curFloorNum > reachedHighest)
				reachedHighest = curFloorNum;
		}
		return curFloorNum;
	}


	public int downstair()
	{
		if ((curFloorNum - 1) >= 0)
		{
			curUpDown = 1;
			setMap(--curFloorNum);
		}
		return curFloorNum;
	}

	public int canPass(int direction)
	{
		int col = hero.getRefPixelX() / TILE_WIDTH;
		int row = hero.getRefPixelY() / TILE_HEIGHT;
		int result;
		boolean isBound = true;
		switch (direction)
		{
			case GameScreen.UP:
				if (row - 1 >= 0)
				{
					row--;
					isBound = false;
				}
				break;
			case GameScreen.DOWN:
				if (row + 1 < TILE_NUM_ROW)
				{
					row++;
					isBound = false;
				}
				break;
			case GameScreen.LEFT:
				if (col - 1 >= 0)
				{
					col--;
					isBound = false;
				}
				break;
			case GameScreen.RIGHT:
				if (col + 1 < TILE_NUM_COL)
				{
					col++;
					isBound = false;
				}
				break;
		}
		if (isBound == true)
		{
			result = 0;
		}
		else
		{
			aheadCol = col;
			aheadRow = row;
			aheadIndex = TILE_NUM_ROW * row + col;
			result = floorArray[curFloorNum][aheadIndex];
		}
		return result;
	}

	public int canPassMaze(int direction)
	{
		int col = hero.getRefPixelX() / TILE_WIDTH;
		int row = hero.getRefPixelY() / TILE_HEIGHT;
		int result;
		boolean isBound = true;
		switch (direction)
		{
			case GameScreen.UP:
				if (row - 1 >= 0)
				{
					row--;
					isBound = false;
				}
				break;
			case GameScreen.DOWN:
				if (row + 1 < maze_TILE_NUM_ROW)
				{
					row++;
					isBound = false;
				}
				break;
			case GameScreen.LEFT:
				if (col - 1 >= 0)
				{
					col--;
					isBound = false;
				}
				break;
			case GameScreen.RIGHT:
				if (col + 1 < maze_TILE_NUM_COL)
				{
					col++;
					isBound = false;
				}
				break;
		}
		if (isBound == true)
		{
			result = 0;
		}
		else
		{
			aheadCol = col;
			aheadRow = row;
			aheadIndex = maze_TILE_NUM_ROW * row + col;
			result = MazefloorArray[curFloorNum][aheadIndex];
		}
		return result;
	}
	
	public void changeCell(int index, int type)
	{
		int[] colrow = getColRow(index);
		floorArray[curFloorNum][index] = type;
		floorMap.setCell(colrow[0], colrow[1], type);
	}

	public void changeMazeCell(int index, int type)
	{
		int[] colrow = getMazeColRow(index);
		MazefloorArray[0][index] = type;
		MazefloorMap.setCell(colrow[0], colrow[1], type);
	}

	public void remove()
	{
		floorArray[curFloorNum][aheadIndex] = 1;
		floorMap.setCell(aheadCol, aheadRow, 1);
	}


	public void remove(int floor, int index)
	{
		floorArray[floor][index] = 1;
	}


	public void remove(int floor, int index, int type)
	{
		floorArray[floor][index] = type;
	}


	public void jump(int floor)
	{
		if (reachedHighest >= floor)
		{
			if (floor >= curFloorNum)
				curUpDown = 0;
			else
				curUpDown = 1;
			curFloorNum = floor;
			setMap(curFloorNum);
		}
	}


	public int[] getOrgeArray()
	{
		int[] result = new int[TILE_NUM];
		int type, num = 0;
		boolean repeated = false;
		for (int i = 0; i < TILE_NUM; i++)
		{
			repeated = false;
			if ((type = floorArray[curFloorNum][i]) >= FightCalc.MIN_ORGE_INDEX)
			{
				if (type > FightCalc.MAX_ORGE_INDEX)
					type -= SWITCH_OFFSET;
				//if (type < MAP_ORGE1)
					//continue;
				for (int j = 0; j < num; j++)
				{
					if (result[j] == type)
					{
						repeated = true;
						break;
					}
				}
				if (repeated == false)
				{
					result[num] = type;
					num++;
				}
			}

		}
		result[TILE_NUM - 1] = num;
		return result;
	}


	public byte[] getFloorArray(int floor)
	{
		byte[] result = new byte[TILE_NUM];
		int type;
		for (int i = 0; i < TILE_NUM; i++)
		{
			type = floorArray[floor][i];
			if (type > FightCalc.MAX_ORGE_INDEX)
				type -= SWITCH_OFFSET;
			result[i] = (byte) type;
		}
		return result;
	}
	
	public byte[] getMazeFloorArray()
	{
		byte[] result = new byte[maze_TILE_NUM];
		int type;
		for (int i = 0; i < maze_TILE_NUM; i++)
		{
			type = mazeFloorArray[i];
	
			result[i] = (byte) type;
		}
		return result;
	}

	public void setMazeFloorArray(int floor, byte[] data)
	{
		for (int i = 0; i < TILE_NUM; i++)
		{
			floorArray[floor][i] = data[i];
		}
	}
	
	public void setFloorArray(int floor, byte[] data)
	{
		for (int i = 0; i < TILE_NUM; i++)
		{
			floorArray[floor][i] = data[i];
		}
	}
	
	private void reFirst(boolean isFirst[])
	{
		for(int i=0; i<=isFirst.length-1; i++)
			isFirst[i] = true;
	}
	
	private int[][] floorArray =
	{
	//HOME0
	{
		24,25,24,24,24,24,24,24,24,24,24,
		24,24,24,26,27,30,31,34,35,24,24,
		23,23,23,28,29,32,33,23,23,23,23,
		1,1,1,1,1,1,1,1,1,6,7,
		1,1,1,1,1,1,1,1,1,8,9,
		1,1,1,14,15,1,1,1,1,19,20,
		1,1,1,16,17,1,1,1,1,21,22,
		1,1,1,1,1,1,1,1,1,1,1,
		1,1,1,1,1,1,1,1,1,1,1,
		36,36,36,36,36,1,36,36,36,36,36,
		24,24,24,24,24,13,24,24,24,24,24
	},
	//HOME OUT 1
	{
		2, 10, 4, 78, 79, 80, 4, 4, 78, 79, 80,
		18, 3, 4, 89, 75, 91, 2, 2, 89, 76, 91,
		2, 3, 4, 100, 81, 102, 18, 18, 100, 82, 102,
		18, 177, 178, 175, 176, 3, 3, 3, 3, 3, 3,
		2, 188, 189, 186, 187, 83, 83, 2, 3, 2, 4,
		18, 164, 3, 83, 78, 79, 80, 18, 3, 18, 4,
		2, 37, 38, 2, 89, 90, 91, 2, 3, 166, 4,
		18, 39, 40, 18, 100, 101, 102, 18, 3, 167, 168,
		3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
		2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2,
		18, 4, 18, 4, 18, 4, 18, 4, 18, 4, 18
	},
	//SHOP2
	{
		36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36,
		24, 49, 24, 24, 24, 51, 59, 60, 24, 24, 24,
		23, 50, 23, 23, 23, 23, 70, 71, 23, 23, 23,
		1, 1, 1, 1, 55, 190, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 55, 1, 1, 1, 1, 1, 1,
		1, 1, 44, 1, 1, 1, 1, 1, 1, 1, 1,
		1, 56, 57, 58, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		36, 36, 36, 1, 36, 36, 36, 36, 36, 36, 36,
		24, 24, 24, 1, 24, 24, 24, 24, 24, 24, 24,
		23, 23, 23, 13, 23, 23, 23, 23, 23, 23, 23
	},
	//HOSPITAL3
	{
		67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67,
		26, 27, 68, 68, 68, 68, 59, 60, 26, 27, 68,
		28, 29, 69, 69, 69, 69, 70, 71, 28, 29, 69,
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
		5, 5, 5, 65, 5, 5, 5, 5, 5, 5, 5,
		5, 5, 5, 64, 5, 5, 5, 5, 5, 5, 5,
		5, 5, 61, 62, 63, 5, 5, 5, 5, 5, 5,
		5, 5, 72, 73, 74, 5, 5, 5, 5, 5, 5,
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
		67, 67, 67, 67, 67, 67, 67, 5, 67, 67, 67,
		69, 69, 69, 69, 69, 69, 69, 13, 69, 69, 69
	},
	//木屋地下一层4
	{
		105, 105, 104, 155, 156, 105, 105, 60, 59, 105, 105,
		106, 106, 104, 162, 163, 106, 106, 70, 70, 106, 106,
		99, 161, 104, 99, 99, 158, 99, 71, 71, 99, 99,
		99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
		99, 99, 99, 99, 99, 99, 99, 99, 99, 157, 99,
		99, 99, 99, 99, 99, 99, 99, 99, 99, 161, 99,
		99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
		99, 109, 99, 99, 99, 99, 99, 99, 99, 99, 99,
		99, 107, 108, 99, 99, 99, 99, 99, 99, 99, 99,
		99, 107, 108, 99, 99, 99, 99, 99, 159, 160, 99,
		99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99
	},
	//floor5
	{
		22,2,23,2,24,56,1,1,56,19,20,
		1,2,25,2,56,1,1,1,1,56,19,
		57,2,1,2,55,1,2,2,13,2,2,
		1,13,1,2,32,55,2,1,58,55,49,
		57,2,1,2,2,2,2,1,1,1,55,
		25,2,1,1,1,52,53,1,1,1,1,
		26,2,2,54,2,2,2,2,1,1,1,
		1,48,2,54,2,1,1,1,58,62,1,
		2,2,2,52,2,13,2,14,2,13,2,
		1,1,2,1,2,52,2,26,13,1,2,
		12,1,52,1,1,1,2,19,2,11,2
	},
	//floor6
	{
		38,59,2,26,2,19,65,40,2,24,24,
		59,19,2,25,2,1,19,65,2,60,24,
		19,63,14,1,14,63,1,19,2,1,60,
		1,1,2,62,2,1,1,1,2,69,1,
		2,2,2,15,2,2,2,2,2,13,2,
		1,1,64,1,19,19,19,1,64,1,1,
		1,2,2,2,2,2,2,2,2,2,2,
		1,2,57,13,57,1,1,1,1,1,1,
		1,2,13,2,13,2,2,2,2,2,14,
		1,2,57,2,1,1,2,2,1,1,1,
		1,1,1,2,11,1,13,13,1,1,12
	},
	//floor7
	{
		11,1,1,1,1,1,1,1,2,2,2,
		2,2,1,63,2,14,2,59,1,2,2,
		2,1,63,26,2,66,2,25,59,1,2,
		1,1,2,2,2,10,2,2,2,1,1,
		1,1,14,66,16,42,10,66,14,1,1,
		1,2,2,2,2,10,2,2,2,2,1,
		1,2,23,25,2,66,2,26,23,2,1,
		1,2,19,23,2,14,2,23,19,2,1,
		1,2,2,20,20,24,20,20,2,2,1,
		1,1,2,2,2,15,2,2,2,1,1,
		2,1,1,13,12,1,1,13,1,1,2
	},
	//floor8
	{
		12,2,1,1,1,1,2,1,19,59,1,
		1,2,1,2,2,13,2,13,2,2,1,
		1,2,1,2,1,1,14,1,1,2,25,
		1,2,1,2,61,2,2,2,57,2,54,
		57,2,1,2,23,2,11,1,1,2,54,
		63,2,26,2,23,2,2,2,2,2,1,
		57,2,54,2,1,1,1,2,1,63,1,
		1,2,54,2,2,2,62,2,13,2,2,
		1,2,1,59,1,2,59,2,1,1,1,
		1,2,2,2,13,2,1,2,2,2,1,
		1,1,61,1,1,2,1,65,66,65,1
	},
	//floor9
	{
		37,19,1,2,2,2,1,1,1,2,1,
		19,1,69,13,1,1,1,2,1,13,59,
		2,13,2,2,1,2,2,2,1,2,19,
		1,1,1,2,1,2,1,1,1,2,19,
		1,1,1,2,1,2,12,2,1,2,23,
		2,14,2,2,1,2,2,2,1,2,2,
		26,68,25,2,61,2,11,2,1,2,23,
		2,13,2,2,1,1,1,13,1,2,19,
		59,23,59,2,2,14,2,2,1,2,19,
		20,59,23,2,60,61,60,2,1,13,59,
		28,20,59,13,24,60,24,2,1,2,1
	},
	//floor10
	{
		1,2,2,26,69,2,69,25,2,2,1,
		1,1,2,2,13,2,13,2,2,1,60,
		1,1,1,1,1,2,1,1,1,60,24,
		1,2,1,2,2,2,2,2,1,2,2,
		57,2,1,1,19,19,19,1,1,2,19,
		63,2,1,2,2,2,2,13,2,2,19,
		57,2,1,16,1,12,2,1,13,63,1,
		1,2,2,2,2,2,2,13,2,2,1,
		1,2,23,26,25,2,1,63,1,2,19,
		1,2,23,26,25,15,61,2,61,2,19,
		11,2,23,26,25,2,20,2,20,2,23
	},
	//floor11
	{
		23,2,19,2,20,2,21,2,24,33,24,
		23,2,19,2,20,2,21,2,62,62,62,
		23,2,19,2,20,2,21,2,1,62,1,
		13,2,13,2,13,2,13,2,2,14,2,
		1,1,1,1,1,2,1,1,1,1,1,
		13,2,2,14,2,2,2,14,2,2,13,
		26,2,1,70,24,72,24,70,1,2,25,
		26,2,69,2,2,2,2,2,69,2,25,
		26,2,69,2,3,17,4,2,69,2,25,
		2,2,15,2,23,1,23,2,15,2,2,
		12,1,1,1,1,1,1,1,1,1,11
	},
	//floor12
	{
		49,26,2,1,67,74,67,1,2,24,43,
		25,1,2,1,2,13,2,1,2,1,24,
		1,1,2,1,2,74,2,1,2,1,1,
		1,72,2,1,2,19,2,1,2,77,1,
		72,73,2,1,2,19,2,1,2,75,77,
		2,14,2,1,2,23,2,1,2,14,2,
		1,1,1,1,2,23,2,1,1,1,1,
		2,2,2,1,2,2,2,1,2,2,2,
		26,72,13,70,70,71,70,70,13,72,25,
		2,2,2,2,2,14,2,2,2,2,2,
		11,1,1,1,1,1,1,1,1,1,12
	},
	//floor13
	{
		1,72,1,1,1,1,1,2,1,73,1,
		1,2,2,2,2,2,13,2,1,2,1,
		1,2,1,1,70,1,1,2,1,2,1,
		24,2,15,2,2,2,1,2,1,2,1,
		67,2,1,1,73,2,70,2,25,2,1,
		74,2,1,76,10,2,71,2,25,2,1,
		67,2,73,16,48,2,70,2,25,2,26,
		1,2,2,2,2,2,1,2,1,2,26,
		1,67,1,2,1,1,1,73,1,2,26,
		2,2,1,2,24,2,2,2,2,2,1,
		12,1,1,14,1,11,2,39,76,13,1
	},
	//floor14
	{
		2,1,77,22,11,1,1,1,1,1,2,
		2,1,24,2,2,2,2,2,24,1,2,
		2,1,2,2,2,2,2,2,2,1,2,
		2,1,2,2,2,41,2,2,2,1,2,
		2,1,2,2,2,16,2,2,2,1,2,
		2,1,23,2,2,73,2,2,23,1,2,
		2,1,6,6,2,76,2,6,6,1,2,
		2,1,6,6,2,73,2,6,6,1,2,
		2,1,6,6,2,14,2,6,6,1,2,
		2,70,71,70,14,1,14,70,71,70,2,
		2,2,2,2,2,12,2,2,2,2,2
	},
	//floor15
	{
		1,1,1,1,12,6,11,1,1,1,1,
		1,6,6,6,6,6,6,6,6,6,1,
		1,6,6,2,2,2,2,2,6,6,1,
		1,6,2,2,48,2,49,2,2,6,1,
		1,6,2,2,26,2,26,2,2,6,1,
		1,6,2,2,25,2,25,2,2,6,1,
		1,6,6,2,1,2,1,2,6,6,1,
		1,6,6,2,13,2,13,2,6,6,1,
		1,6,6,6,1,1,1,6,6,6,1,
		1,6,6,6,6,15,6,6,6,6,1,
		1,1,1,1,1,1,1,1,1,1,1
	},
	//floor16
	{
		6,6,6,6,6,1,12,6,6,6,6,
		6,6,6,6,6,1,6,6,6,6,6,
		6,6,6,6,6,1,6,6,6,6,6,
		6,6,6,6,2,15,2,6,6,6,6,
		6,6,6,2,2,1,2,2,6,6,6,
		6,6,6,2,2,79,2,2,6,6,6,
		6,6,6,2,2,1,2,2,6,6,6,
		6,6,6,2,2,11,2,2,6,6,6,
		6,6,6,6,2,2,2,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6
	},
	//floor17
	{
		6,77,76,1,1,1,1,1,1,1,77,
		6,76,6,6,6,6,6,6,6,6,1,
		6,1,6,77,1,1,1,1,1,1,77,
		6,1,6,1,6,6,6,6,6,6,6,
		6,1,6,1,6,77,1,1,1,77,6,
		6,1,6,77,1,1,6,6,6,1,6,
		6,1,6,6,6,6,6,77,1,77,6,
		6,76,6,6,6,12,6,1,6,6,6,
		6,77,76,1,78,1,6,77,1,1,77,
		6,6,6,6,6,6,6,6,6,6,1,
		11,1,78,1,1,1,1,1,1,1,77
	},
	//floor18
	{
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,2,2,2,6,6,6,6,
		6,6,6,2,2,2,2,2,6,6,6,
		6,6,6,2,2,2,2,2,6,6,6,
		6,6,6,2,2,2,2,2,6,6,6,
		6,6,6,6,2,2,2,6,6,6,6,
		6,6,6,6,6,1,6,6,6,6,6,
		6,6,6,6,6,1,6,6,6,6,6,
		12,1,1,1,1,1,1,1,1,1,1
	},
	//floor19
	{
		1,1,1,1,1,1,1,1,1,1,1,
		1,6,1,6,6,6,6,6,1,6,1,
		1,6,1,6,6,6,6,6,1,6,1,
		1,6,1,6,6,11,6,6,1,6,1,
		1,6,1,6,6,1,6,6,1,6,1,
		1,6,79,6,6,1,6,6,79,6,1,
		1,6,16,6,6,80,6,6,16,6,1,
		1,6,31,6,6,1,6,6,35,6,1,
		1,6,6,6,6,1,6,6,6,6,1,
		1,6,6,6,6,1,6,6,6,6,1,
		1,1,1,1,1,1,1,1,1,1,12
	},
	//floor20
	{
		78,25,66,23,76,21,76,23,66,25,78,
		24,6,19,6,20,6,20,6,19,6,24,
		6,26,66,1,77,1,77,1,66,26,6,
		23,6,19,6,1,12,1,6,19,6,23,
		76,20,77,1,1,1,1,1,77,20,76,
		21,6,6,6,1,6,1,6,6,6,21,
		76,20,77,1,1,1,1,1,77,20,76,
		23,6,19,6,1,11,1,6,19,6,23,
		6,26,66,1,77,1,77,1,66,26,6,
		24,6,19,6,20,6,20,6,19,6,24,
		78,25,66,23,76,21,76,23,66,25,78
	},
	//floor21
	{
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,1,1,6,80,6,1,1,6,6,
		6,1,1,6,6,75,6,6,1,1,6,
		6,1,1,1,6,75,6,1,1,1,6,
		6,6,1,1,1,1,1,1,1,6,6,
		6,6,1,1,1,1,1,1,1,6,6,
		6,6,6,1,1,6,1,1,6,6,6,
		6,6,6,6,10,12,10,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6
	}
	};

	
	private int[][] MazefloorArray =
	{
		{
			84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 
			84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84,
			98, 83, 83, 83, 83, 83, 83, 83, 83, 2, 84, 83, 84, 87, 83, 83, 
			83, 83, 83, 83, 84, 83, 83, 83, 83, 83, 83, 84, 95, 84,
			84, 83, 84, 84, 84, 84, 84, 84, 83, 18, 84, 83, 84, 84, 84, 84, 
			84, 84, 84, 83, 84, 83, 84, 84, 84, 84, 83, 84, 83, 84,
			84, 83, 83, 83, 84, 84, 83, 83, 83, 84, 84, 83, 83, 83, 83, 84, 
			83, 83, 83, 83, 84, 83, 84, 94, 84, 83, 83, 2, 83, 84,
			84, 84, 84, 83, 84, 84, 83, 169, 83, 83, 83, 83, 83, 83, 83, 84, 
			83, 83, 83, 83, 84, 83, 84, 83, 84, 84, 84, 18, 83, 84,
			84, 84, 84, 83, 84, 84, 83, 84, 84, 84, 84, 84, 84, 84, 83, 84, 
			83, 83, 83, 83, 84, 83, 84, 83, 83, 84, 83, 83, 83, 84,
			84, 83, 84, 83, 84, 84, 83, 84, 83, 88, 83, 84, 83, 83, 83, 84, 
			83, 83, 83, 83, 84, 83, 84, 83, 83, 84, 83, 84, 84, 84,
			84, 83, 84, 83, 84, 84, 83, 84, 83, 83, 83, 84, 83, 83, 83, 83, 
			83, 83, 83, 83, 84, 83, 84, 83, 83, 84, 83, 83, 83, 84,
			84, 83, 84, 83, 84, 84, 83, 84, 83, 83, 83, 84, 83, 84, 84, 84, 
			84, 84, 84, 84, 84, 83, 84, 83, 83, 84, 83, 83, 83, 84,
			84, 83, 84, 83, 92, 84, 83, 84, 83, 83, 83, 84, 83, 83, 83, 83, 
			83, 83, 83, 83, 83, 83, 84, 83, 83, 84, 83, 83, 83, 84,
			84, 83, 84, 83, 84, 84, 83, 84, 84, 84, 83, 84, 83, 83, 83, 83, 
			83, 83, 83, 83, 83, 2, 84, 83, 83, 84, 83, 84, 83, 84,
			84, 83, 84, 83, 84, 84, 83, 84, 83, 83, 83, 84, 83, 84, 84, 84, 
			84, 83, 83, 84, 84, 18, 83, 83, 84, 84, 84, 84, 83, 84,
			84, 83, 84, 83, 84, 84, 83, 84, 83, 83, 83, 84, 83, 83, 83, 83, 
			84, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 84, 83, 84,
			84, 83, 84, 83, 84, 84, 87, 84, 83, 83, 83, 84, 83, 83, 83, 83, 
			84, 83, 83, 84, 83, 83, 83, 83, 83, 83, 83, 84, 83, 84,
			84, 83, 84, 83, 84, 84, 84, 84, 83, 84, 84, 84, 84, 84, 84, 84, 
			84, 84, 84, 84, 84, 84, 84, 83, 84, 84, 84, 84, 83, 84,
			84, 83, 83, 83, 84, 84, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 
			83, 83, 83, 83, 83, 83, 84, 83, 84, 83, 83, 83, 83, 84,
			84, 83, 83, 83, 84, 84, 83, 83, 83, 83, 83, 83, 83, 84, 84, 84, 
			84, 84, 84, 84, 83, 84, 84, 83, 83, 83, 84, 84, 83, 84,
			84, 83, 83, 83, 84, 84, 83, 83, 84, 84, 84, 84, 83, 84, 83, 83, 
			83, 83, 83, 83, 83, 83, 84, 84, 84, 83, 84, 84, 83, 84,
			84, 83, 83, 83, 84, 84, 83, 83, 84, 83, 83, 84, 83, 84, 83, 84, 
			83, 83, 83, 84, 83, 84, 87, 88, 84, 83, 84, 84, 83, 84,
			84, 83, 83, 84, 84, 84, 83, 83, 84, 83, 83, 84, 83, 84, 83, 84, 
			84, 84, 83, 84, 84, 83, 83, 83, 84, 83, 84, 84, 87, 84,
			84, 83, 83, 84, 84, 84, 83, 83, 84, 83, 83, 83, 83, 84, 83, 84, 
			83, 84, 83, 84, 83, 83, 84, 83, 84, 83, 84, 84, 84, 84,
			84, 83, 83, 83, 83, 83, 83, 83, 2, 83, 84, 83, 83, 83, 83, 84, 
			83, 84, 83, 83, 83, 83, 84, 84, 84, 87, 84, 84, 83, 84,
			84, 83, 83, 83, 83, 83, 83, 83, 18, 83, 84, 84, 84, 84, 84, 84, 
			83, 84, 84, 84, 84, 83, 84, 83, 84, 84, 84, 84, 83, 84,
			84, 84, 84, 84, 84, 84, 84, 84, 84, 83, 84, 41, 42, 43, 84, 84, 
			83, 83, 83, 83, 84, 84, 84, 83, 83, 83, 83, 83, 83, 84,
			84, 83, 83, 83, 83, 83, 83, 83, 83, 83, 84, 52, 53, 54, 84, 84, 
			83, 83, 84, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 84,
			84, 83, 83, 84, 84, 84, 84, 84, 84, 84, 84, 83, 83, 83, 84, 84, 
			83, 83, 84, 84, 84, 83, 83, 83, 84, 83, 84, 84, 84, 84,
			84, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 
			83, 83, 84, 86, 84, 83, 83, 83, 84, 83, 84, 83, 83, 83,
			84, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 
			84, 84, 84, 86, 84, 83, 84, 84, 2, 83, 119, 83, 84, 84,
			84, 83, 83, 83, 93, 83, 83, 83, 83, 83, 83, 83, 83, 84, 83, 83, 
			83, 83, 83, 83, 83, 83, 83, 83, 18, 83, 83, 83, 83, 85,
			84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 
			84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84
		},
		{//木屋一层
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 105, 105, 125, 125, 105, 105, 132, 105,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 106, 106, 106, 106, 106, 106, 106, 106,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 154, 154, 154, 128, 126, 126, 130, 154,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 154, 154, 154, 129, 127, 127, 131, 154,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 154, 154, 154, 154, 154, 154, 154, 154,
			105, 105, 105, 105, 105, 105, 105, 110, 114, 105, 105, 105, 105, 105, 105, 105, 
			105, 105, 105, 105, 148, 149, 140, 154, 154, 154, 154, 154, 154, 154,
			106, 106, 106, 115, 116, 106, 106, 111, 112, 113, 106, 106, 106, 150, 152, 106, 
			106, 121, 122, 106, 106, 106, 154, 154, 154, 154, 154, 154, 154, 154,
			154, 154, 154, 117, 118, 154, 154, 154, 154, 154, 120, 154, 154, 151, 153, 154, 
			154, 123, 124, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154,
			154, 154, 154, 154, 154, 154, 154, 141, 154, 154, 154, 154, 154, 154, 154, 154, 
			154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154,
			154, 103, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 
			154, 154, 154, 154, 154, 154, 154, 154, 133, 133, 133, 133, 133, 133,
			154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 141, 
			142, 143, 141, 154, 154, 154, 154, 154, 137, 55, 154, 154, 138, 138,
			154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 141, 
			144, 145, 141, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 154, 154, 154, 154, 154, 154, 154, 
			154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 154, 154, 154, 154, 154, 154, 154, 
			154, 154, 154, 154, 154, 154, 154, 154, 133, 133, 133, 133, 134, 134,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 154, 154, 154, 154, 154, 154, 154, 
			146, 147, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 154, 154, 154, 154, 154, 154, 154, 
			154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 136,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 154, 154, 154, 154, 154, 154, 154, 
			154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 139,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 135, 154, 135, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0
		}
	};
	
	public static final int MAP_ROAD = 1,
							MAP_TREE1 = 2,
							MAP_ROAD1 = 3,
							MAP_LAWN = 4,
							MAP_ROAD2 = 5,
							MAP_BED1 = 6,
							MAP_BED2 = 7,
							MAP_BED3 = 8,
							MAP_BED4 = 9,
							MAP_LOCKED_BARRIER = 10,
							MAP_UPSTAIR = 11,
							MAP_DOWNSTAIR = 12,
							MAP_YELLOW_DOOR = 13,
							MAP_DESK1 = 14,
							MAP_DESK2 = 15,
							MAP_DESK3 = 16,
							MAP_DESK4 = 17,
							MAP_TREE2 = 18,
							//
							MAP_BED5 = 19,
							MAP_BED6 = 20,
							MAP_BED7 = 21,
							MAP_BED8 = 22,
							MAP_WALL2 = 23,
							MAP_WALL1 = 24,
							MAP_BOTTOM = 25,
							MAP_BOOK1 = 26,
							MAP_BOOK2 = 27,
							MAP_BOOK3 = 28,
							MAP_BOOK4 = 29,
							MAP_TV1 = 30,
							MAP_TV2 = 31,
							MAP_TV3 = 32,
							MAP_TV4 = 33,
							MAP_CAL1 = 34,//3
							MAP_CAL2 = 35,//4
							MAP_WALLF = 36,
							MAP_SHOUSE9 = 37,
							MAP_SHOUSE10 = 38,
							MAP_SHOUSE11 = 39,
							MAP_SHOUSE12 = 40,
							MAP_SHOUSE1 = 41,
							MAP_SHOUSE2 = 42,
							MAP_SHOUSE3 = 43,
							MAP_ZAHUO_SHOPER = 44,//5
							//
							MAP_DESK_N1 = 45,
							MAP_DESK_N2 = 46,
							MAP_DESK_N3 = 47,
							MAP_DESK_N4 = 48,
							MAP_ZA_INDOOR1 = 49,
							//
							MAP_ZA_INDOOR2 = 50,
							MAP_ZHUANGSHIPIN = 51,
							MAP_SHOUSE4 = 52,
							MAP_SHOUSE5 = 53,
							MAP_SHOUSE6 = 54,
							MAP_SHOP_BLOCK = 55,
							MAP_SHOPDESK1 = 56,
							MAP_SHOPDESK2 = 57,
							MAP_SHOPDESK3 = 58,
							MAP_SHOP_GUI1 = 59,
							MAP_SHOP_GUI2 = 60,
							MAP_HOSDESK1 = 61,
							MAP_HOSDESK2 = 62,
							MAP_HOWSDESK3 = 63,
							MAP_DOCTOR = 64,
							MAP_HOS_CHAIR1 = 65,
							MAP_HOS_CHAIR2 = 66,
							MAP_WALL3 = 67,
							MAP_WALL4 = 68,
							MAP_WALL5 = 69,
							MAP_SHOP_GUI3 = 70,
							MAP_SHOP_GUI4 = 71,
							MAP_HOSDESK4 = 72,
							MAP_HOSDESK5 = 73,
							MAP_HOSDESK6 = 74,
							MAP_HOS_BOARD = 75,
							MAP_ZA_BOARD = 76,
							MAP_DOOR_ZUOFEI = 77,
							MAP_BIGHOUSE1 = 78,
							MAP_BIGHOUSE2 = 79,
							MAP_BIGHOUSE3 = 80,
							MAP_HOS_DOOR  = 81,
							MAP_ZA_DOOR  = 82,
							MAP_MAZE_ROAD = 83,
							MAP_MAZE_TREE = 84,
							MAP_MAZE_OUTDOOR = 85,
							MAP_MAZE_WATERFALL = 86,
							MAP_MAZE_GOLDBOX = 87,
							MAP_MAZE_SILVERBOX = 88,
							MAP_BIGHOUSE4 = 89,
							MAP_BIGHOUSE5 = 90,
							MAP_BIGHOUSE6 = 91,
							MAP_MAZE_ROLEA = 92,
							MAP_MAZE_ROLED = 93,
							MAP_MAZE_FOOD1 = 94,
							MAP_MAZE_FOOD2 = 95,
							MAP_MAZE_WATERFALLN = 96,
							MAP_MAZE_IN = 97,
							MAP_MAZE_BACK = 98,
							MAP_WOODEN_DOWNFLOOR = 99,
							MAP_MAZE_ROLEB = 169,
							MAP_MAZE_ROLEC = 119,
							MAP_BIGHOUSE7 = 100,
							MAP_BIGHOUSE8 = 102,
							MAP_HOME_DOOR = 101,
							MAP_WOODEN_DOWN = 103,
							MAP_WOODEN_UP = 104,
							MAP_WOODEN_WALL = 105,
							MAP_WOODEN_WALL1 = 106,
							MAP_WOODEN_ZA1 = 107,
							MAP_WOODEN_ZA2 = 108,
							MAP_WOODEN_VEGETABLE1 = 109,
							MAP_WOODEN_TOOLS = 110,
							MAP_WOODEN_DESK1 = 111,
							MAP_WOODEN_DESK2 = 112,
							MAP_WOODEN_DESK3 = 113,
							MAP_WOODEN_WALL2 = 114,
							MAP_WOODEN_CUPBOARD1 = 115,
							MAP_WOODEN_CUPBOARD2 = 116,
							MAP_WOODEN_CUPBOARD3 = 117,
							MAP_WOODEN_CUPBOARD4 = 118,
							MAP_WOODEN_VEGETABLE2 = 120,
							MAP_WOODEN_PIANO1 = 121,
							MAP_WOODEN_PIANO2 = 122,
							MAP_WOODEN_PIANO3 = 123,
							MAP_WOODEN_PIANO4 = 124,
							MAP_WOODEN_WINDOW = 125,
							MAP_WOODEN_BED1 = 126,
							MAP_WOODEN_BED2 = 127,
							MAP_WOODEN_MAT1 = 128,
							MAP_WOODEN_MAT2 = 129,
							MAP_WOODEN_MAT3 = 130,
							MAP_WOODEN_MAT4 = 131,
							MAP_WOODEN_CLOCK = 132,
							MAP_WOODEN_STONEDESK1 = 133,
							MAP_WOODEN_STONEDESK2 = 134,
							MAP_WOODEN_PLANTS = 135,
							MAP_WOODEN_WINEDESK = 136,
							MAP_WOODEN_WINE = 137,
							MAP_WOODEN_WOODS = 138,
							MAP_WOODEN_DESK4 = 139,
							MAP_WOODEN_CHAIR1 = 140,
							MAP_WOODEN_CHAIR2 = 141,
							MAP_WOODEN_LDESK1 = 142,
							MAP_WOODEN_LDESK2 = 143,
							MAP_WOODEN_LDESK3 = 144,
							MAP_WOODEN_LDESK4 = 145,
							MAP_WOODEN_DESK5 = 146,
							MAP_WOODEN_DESK6 = 147,
							MAP_WOODEN_PIC1 = 148,
							MAP_WOODEN_PIC2 = 149,
							MAP_WOODEN_CLOTH1 = 150,
							MAP_WOODEN_CLOTH2 = 151,
							MAP_WOODEN_CLOTH3 = 152,
							MAP_WOODEN_CLOTH4 = 153,
							MAP_WOODEN_FLOOR = 154,
							MAP_WOODEN_FRAME1 = 155,
							MAP_WOODEN_FRAME2 = 156,
							MAP_WOODEN_VEGETABLE3 = 157,
							MAP_WOODEN_SDESK = 158,
							MAP_WOODEN_SHIELD = 159,
							MAP_WOODEN_STATUE = 160,
							MAP_WOODEN_CAN = 161,
							MAP_WOODEN_A1 = 162,
							MAP_WOODEN_A2 = 163,
							MAP_OUTDOOR_ROLE1 = 164,
							MAP_OUTDOOR_ROLE2 = 165,
							MAP_OUTDOOR_ROLE3 = 166,
							MAP_BUSSTOP1 = 167,
							MAP_BUSSTOP2 = 168,
							MAP_MURDER = 176,
							MAP_MAZE_HELPER1 = 179,
							MAP_MAZE_HELPER2 = 180,
							MAP_MAZE_HELPER3 = 181,
							MAP_MAZE_HELPER4 = 182,
							MAP_SHOPPER1 = 190,
							MAP_SHOPPER7 = 197
							;
}

