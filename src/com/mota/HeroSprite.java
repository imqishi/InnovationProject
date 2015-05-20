package com.mota;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.game.Sprite;

import android.graphics.Bitmap;

public class HeroSprite extends Sprite
{
	private int		hp			= 100;
	private int		attack		= 100;
	private int		defend		= 100;
	private int		experience	= 0;
	private int		money		= 50;

	private int clothes = -1;
	private int weapon = -1;
	private int pack[][] = new int [12][12];
	private int		level		= 1;
	private Task	task;
	protected int[] buff = new int[20];
	
	public HeroSprite(Bitmap image, int frameWidth, int frameHeight)
	{
		super(image, frameWidth, frameHeight);
		for(int i=0;i<=11;i++)
			for(int j=0;j<=11;j++)
				if(i==0 && j==0)
					pack[i][j]=0;
				else
					pack[i][j]=-1;
	}


	public void setTask(Task task)
	{
		this.task = task;
	}

	public Task getTask()
	{
		return task;
	}
	

	public void levelUp(int value)
	{
		level += value;
		hp += 1000 * value;
		attack += 7 * value;
		defend += 7 * value;
	}


	public int getAttack()
	{
		return attack;
	}


	public int getHp()
	{
		return hp;
	}


	public int getDefend()
	{
		return defend;
	}


	public int getMoney()
	{
		return money;
	}


	public int getExperience()
	{
		return experience;
	}

	public void cutHp(int value)
	{
		hp -= value;
	}


	public void addHp(int value)
	{
		hp += value;
	}


	public void setHp(int value)
	{
		hp = value;
	}


	public void addMoney(int value)
	{
		money += value;
	}


	public void cutMoney(int value)
	{
		money -= value;
	}


	public void addExperience(int value)
	{
		experience += value;
	}


	public void cutExperience(int value)
	{
		experience -= value;
	}


	public void addAttack(int value)
	{
		attack += value;
	}


	public void addDefend(int value)
	{
		defend += value;
	}

	public int getLevel()
	{
		return level;
	}

	public void setClothes(int type)
	{
		clothes = type;
	}
	
	public int getClothes()
	{
		return clothes;
	}
	
	public void setweapon(int type)
	{
		weapon = type;
	}
	
	public int getweapon()
	{
		return weapon;
	}
	
	public boolean setpack(int type)  //pack[][]第0行0列存总物品种类数，第0行n列存第n件物品的数量，第n行n列存物品属性（名称）
	{
		int index = 0, flag = 0;
		for(int i=1;i<=10;i++)
		{
			if(pack[0][i] == -1)
				index=i;
			if(pack[i][i] == type)
			{
				pack[0][i]++;
				//pack[0][0]++;
				flag++;
				break;
			}
		}
		if(flag == 0)
		{
			if(index == -1)
				return false;
			else
			{
				pack[0][index] = 1;
				pack[index][index]=type;
				pack[0][0]++;
				return true;
			}
		}
		return true;	
	}
	
	public int getpacksum()
	{
		return pack[0][0];
	}
	
	public int[][] getpack()
	{
		return pack;
	}
	
	public boolean usepack(int type)
	{
		if((type >= 4 && type <= 14) || (type == 16) || (type ==17))
			return false;
		else
		{
			int flag = 0;
			for(int i=1;i<=10;i++)
				if(pack[i][i] == type)
				{
					pack[0][i]--;
					if(pack[0][i] == 0)
					{
						pack[i][i] = -1;
						pack[0][i] = -1;
						pack[0][0]--;
					}	
					flag++;
					break;
				}
			if(flag == 0)
				return false;
			else
				return true;
		}		
	}

	public boolean usepackforce(int type)
	{
		int flag = 0;
		for(int i=1;i<=10;i++)
			if(pack[i][i] == type)
			{
				pack[0][i]--;
				if(pack[0][i] == 0)
				{
					pack[i][i] = -1;
					pack[0][i] = -1;
					pack[0][0]--;
				}	
				flag++;
				break;
			}
		if(flag == 0)
			return false;
		else
			return true;
	}
	
	public byte[] encode()
	{
		byte[] result = null;
		try
		{

			ByteArrayOutputStream bstream = new ByteArrayOutputStream();
			DataOutputStream ostream = new DataOutputStream(bstream);
			ostream.writeInt(hp);
			ostream.writeInt(attack);
			ostream.writeInt(defend);
			ostream.writeInt(experience);
			ostream.writeInt(money);
			ostream.writeInt(clothes);
			ostream.writeInt(weapon);
			for(int i = 0;i <= 10;i++)
			{
				ostream.writeInt(pack[0][i]);
			}
			for(int i = 1;i <= 10;i++)
			{
				ostream.writeInt(pack[i][i]);
			}
			for(int i = 0;i <= buff.length;i++)
			{
				ostream.writeInt(buff[i]);
			}
			
			result = bstream.toByteArray();
		}
		catch (Exception e)
		{
			System.out.println("encode error::" + e);
		}
		return result;
	}


	// decode the byte[] from RMS
	public void decode(byte[] data)
	{
		
	}
}
