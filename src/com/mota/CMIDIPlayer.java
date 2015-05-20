package com.mota;

import java.io.IOException;

import com.mota.R;

import android.media.MediaPlayer;

public class CMIDIPlayer
{
	public MediaPlayer	playerMusic;

	public MagicTower	magicTower	= null;


	public CMIDIPlayer(MagicTower magicTower)
	{
		this.magicTower = magicTower;

	}


	// 播放音乐
	public void PlayMusic(int ID)
	{
		FreeMusic();
		switch (ID)
		{
			case 1:
				//装载音乐
				playerMusic = MediaPlayer.create(magicTower, R.raw.menu);
				//设置循环
				playerMusic.setLooping(true);
				try
				{
					//准备
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				//开始
				playerMusic.start();
				break;
			case 2:
				playerMusic = MediaPlayer.create(magicTower, R.raw.sel_hero);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 3:
				playerMusic = MediaPlayer.create(magicTower, R.raw.home);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 4:
				playerMusic = MediaPlayer.create(magicTower, R.raw.block);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 5:
				playerMusic = MediaPlayer.create(magicTower, R.raw.store);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 6:
				playerMusic = MediaPlayer.create(magicTower, R.raw.hospital);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 7:
				playerMusic = MediaPlayer.create(magicTower, R.raw.maze);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 8:
				playerMusic = MediaPlayer.create(magicTower, R.raw.maze_house);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 9:
				playerMusic = MediaPlayer.create(magicTower, R.raw.hp_full);
				playerMusic.setLooping(false);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 10:
				playerMusic = MediaPlayer.create(magicTower, R.raw.in_out);
				playerMusic.setLooping(false);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 11:
				playerMusic = MediaPlayer.create(magicTower, R.raw.waterfall);
				playerMusic.setLooping(false);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			case 12:
				playerMusic = MediaPlayer.create(magicTower, R.raw.maze_base);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
			default:
				break;
		}
	}


	// 退出释放资源
	public void FreeMusic()
	{
		if (playerMusic != null)
		{
			playerMusic.stop();
			playerMusic.release();
		}
	}


	// 停止播放
	public void StopMusic()
	{
		if (playerMusic != null)
		{
			playerMusic.stop();
		}
	}
}
