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
			temp1 = "��";
		else
		{
			temp1 = "��";
		}
		if(information[5] == -1)
			temp2 = "��";
		else
		{
			temp2 = "��";
		}
		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setTextColor(Color.rgb(0, 0, 0));
		String ss = "�ȼ���"+ information[0] + "\n����ֵ��" + information[1] + "\n��ң�" + information[2] + "\n��������" + information[3]
				+ "\n��������" + information[4] + "\n�·���" + temp2 + "\n������" + temp1 + "\n����ֵ��" + information[7];
		textView.setText((CharSequence)(ss));

	}
}
