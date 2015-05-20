package com.mota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Backpack extends Activity{

	public final static String [] goods = new String[]
			{"����","�ǹ�","����","������","����Կ��","��Կ��","��ʯ","��Ʊ","�����γ�",
			"��ʯ","���ÿ�","ɨ��","ˮͰ","����","����","ʳƷ","����","����"};
	
	String [] backpack;
	
    private int[] allOptionsMenuIcons = {
    		R.drawable.yinliao,
    		R.drawable.tangguo,
    		R.drawable.hanbao,
    		R.drawable.sanmingzhi,
    		R.drawable.wannengyaoshi,
    		R.drawable.jinyaoshi,
    		R.drawable.baoshi,
    		R.drawable.chaopiao,
    		android.R.drawable.ic_menu_delete,
    		R.drawable.zuanshi,
    		R.drawable.xinyongka,
    		R.drawable.saoba,
    		R.drawable.shuitong,
    		R.drawable.jiangsheng,
    		R.drawable.jiangsheng,
    		R.drawable.siliao,
    		R.drawable.shipin,
    		R.drawable.fuzi,
    		R.drawable.baoguo
    		};
	
    private int[] thisOptions;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.backpack);
        //setTheme(R.style.dialog); 
        
        
        
      	GridView gridView = (GridView)findViewById(R.id.gridview);
      	
        
        Intent intent = getIntent();
        int data[]=new int[32];
        
        data = intent.getIntArrayExtra("backpack");
		int sum = data[0];
		thisOptions = new int[sum];
		backpack = new String[sum];
		for(int i = 1, j = 0;i <= sum*2; i++)
		{
			if(i % 2 != 0)
			{
				switch(data[i])
				{
					case 0:
						backpack[j] = "����";
						thisOptions[j] = allOptionsMenuIcons[0];
						break;
					case 1:
						backpack[j] = "�ǹ�";
						thisOptions[j] = allOptionsMenuIcons[1];
						break;
					case 2:
						backpack[j] = "����";
						thisOptions[j] = allOptionsMenuIcons[2];
						break;
					case 3:
						backpack[j] = "������";
						thisOptions[j] = allOptionsMenuIcons[3];
						break;
					case 4:
						backpack[j] = "����Կ��";
						thisOptions[j] = allOptionsMenuIcons[4];
						break;
					case 5:
						backpack[j] = "��Կ��";
						thisOptions[j] = allOptionsMenuIcons[5];
						break;
					case 6:
						backpack[j] = "��ʯ";
						thisOptions[j] = allOptionsMenuIcons[6];
						break;
					case 7:
						backpack[j] = "��Ʊ";
						thisOptions[j] = allOptionsMenuIcons[7];
						break;
					case 8:
						backpack[j] = "�����γ�";
						thisOptions[j] = allOptionsMenuIcons[8];
						break;
					case 9:
						backpack[j] = "��ʯ";
						thisOptions[j] = allOptionsMenuIcons[9];
						break;
					case 10:
						backpack[j] = "���ÿ�";
						thisOptions[j] = allOptionsMenuIcons[10];
						break;
					case 11:
						backpack[j] = "ɨ��";
						thisOptions[j] = allOptionsMenuIcons[11];
						break;
					case 12:
						backpack[j] = "ˮͰ";
						thisOptions[j] = allOptionsMenuIcons[12];
						break;
					case 13:
						backpack[j] = "����";
						thisOptions[j] = allOptionsMenuIcons[13];
						break;
					case 14:
						backpack[j] = "����";
						thisOptions[j] = allOptionsMenuIcons[14];
						break;
					case 15:
						backpack[j] = "ʳƷ";
						thisOptions[j] = allOptionsMenuIcons[15];
						break;
					case 16:
						backpack[j] = "����";
						thisOptions[j] = allOptionsMenuIcons[16];
						break;
					case 17:
						backpack[j] = "����";
						thisOptions[j] = allOptionsMenuIcons[17];
						break;
				}
			}
			else
			{
				backpack[j] += "x";
				backpack[j] += data[i];
				j++;
			}
		}
        
        
		SimpleAdapter menuSimpleAdapter;
		menuSimpleAdapter = createSimpleAdapter(backpack,thisOptions);
		gridView.setAdapter(menuSimpleAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				String keyword=backpack[position];
						
				int key=toint(keyword);
				Intent intent=new Intent();
				intent.putExtra("backpack", key);
				Backpack.this.setResult(2,intent);
				Backpack.this.finish();
			}
		});
		//�����Ի�����ʾ
		gridView.setAdapter(menuSimpleAdapter);
    }
    
	public SimpleAdapter createSimpleAdapter(String[] menuNames,int[] menuImages){
		List<Map<String,?>> data = new ArrayList<Map<String,?>>();
		String[] fromsAdapter = {"item_text","item_image"};
		int[] tosAdapter = {R.id.item_text,R.id.item_image};
		for(int i=0;i<menuNames.length;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(fromsAdapter[0], menuNames[i]);
			map.put(fromsAdapter[1], menuImages[i]);
			data.add(map);
		}
		
		SimpleAdapter SimpleAdapter = new SimpleAdapter(this, data, R.layout.items, fromsAdapter, tosAdapter);
		return SimpleAdapter;
	}

	private int toint(String str)
	{
		int choice = -1;
		for(int i=0; i<goods.length; i++)
			if(str.contains(goods[i]))
			{
				choice=i;
				break;
			}

		return choice;
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			Backpack.this.finish();
		}
		return true;
	}
}