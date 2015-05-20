package com.mota;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class HeroInfor extends Activity{
	int information[] = new int[9];
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.heroinfor);
		
		run();
	}
	
	private void run()
	{
		Intent intent = getIntent();
		information = intent.getExtras().getIntArray("heroInfor");
		
		ImageView pic = (ImageView) findViewById(R.id.imageView1);
		if(information[8] == 1)
			pic.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.heroma));
		else if(information[8] == 2)
			pic.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.herofe));
		
		String temp1,temp2;
		if(information[6] == -1)
			temp1 = "无";
		else
		{
			temp1 = "无";
		}
		if(information[5] == -1)
			temp2 = "无";
		else
		{
			temp2 = "无";
		}
		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setTextColor(Color.rgb(0, 0, 0));
		String ss = "等级："+ information[0] + "\n生命值：" + information[1] + "\n金币：" + information[2] + "\n攻击力：" + information[3]
				+ "\n防御力：" + information[4] + "\n衣服：" + temp2 + "\n武器：" + temp1 + "\n经验值：" + information[7];
		textView.setText((CharSequence)(ss));

	}
}
