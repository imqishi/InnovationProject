package com.mota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
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
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class newChoice extends Activity{

	private int type = -1;
	String [] supper=new String[]
			{"汤","生菜色拉","腌酱菜","烤肉片","白饭"};
	String [] shop=new String[]
			{"饮料","糖果","汉堡","三明治","万能钥匙","什么都不需要"};
	String [] general =new String[]
			{"是","否"};
	String [] numbers=new String[]
			{"0","1","2","3","4","5","6","7","8","9"};
	String [] graphs = new String[]
			{"正方形","长方形","圆形","椭圆形","三角形"};
	String [] huaping = new String[]
			{"玻璃","粘土","瓷器","金属","塑料","木"};
	String [] huapingvector = new String[]
			{"装满","一半","空的"};
	String [] murderChoice = new String[]
			{"强盗抢劫劫财劫色","男友报复她移情别恋","暗恋她的变态狂行为","情敌下的毒手"};
	
	private int[] supperIcons = {
			R.drawable.tang,
			R.drawable.shengcaisela,
			R.drawable.yanjiangcai,
			R.drawable.kaoroufan,
			R.drawable.baifan
			};
	private int[] shopIcons = {
			R.drawable.yinliao,
			R.drawable.tangguo,
			R.drawable.hanbao,
			R.drawable.sanmingzhi,
			R.drawable.wannengyaoshi,
			R.drawable.no
			};
	private int[] generalIcons = {
			R.drawable.yes,
			R.drawable.no
			};
	private int[] numbersIcons = {
			R.drawable.n_0,
			R.drawable.n_1,
			R.drawable.n_2,
			R.drawable.n_3,
			R.drawable.n_4,
			R.drawable.n_5,
			R.drawable.n_6,
			R.drawable.n_7,
			R.drawable.n_8,
			R.drawable.n_9
			};
	private int[] graphsIcons = {
			R.drawable.n_0,
			R.drawable.n_0,
			R.drawable.n_0,
			R.drawable.n_0,
			R.drawable.n_0
			};
	private int[] huapingIcons = {
			R.drawable.n_0,
			R.drawable.n_0,
			R.drawable.n_0,
			R.drawable.n_0,
			R.drawable.n_0,
			R.drawable.n_0
	};
	private int[] huapingvectorIcons = {
			R.drawable.n_0,
			R.drawable.n_0,
			R.drawable.n_0,
	};
	private int[] murderIcons = {
			R.drawable.n_0,
			R.drawable.n_1,
			R.drawable.n_2,
			R.drawable.n_3
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentFortype = getIntent();
		type = intentFortype.getExtras().getInt("choicetype");
        
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(type == Task.BUY_IN_SHOP)
        	setContentView(R.layout.inshop);
        else 
        	setContentView(R.layout.night_item);
        setTheme(R.style.dialog); 
        
        TextView textview1=(TextView)findViewById(R.id.textView1);
        textview1.setTextColor(Color.BLACK);
        
      	GridView gridView = (GridView)findViewById(R.id.gridview);
      	
        
		
		SimpleAdapter menuSimpleAdapter;
		switch(type)
		{
			case Task.SUPPER:
				textview1.setText("离家前最后的晚餐，肚子好饿，用烤肉套餐犒劳自己。先从哪个吃起呢？");
				menuSimpleAdapter = createSimpleAdapter(supper,supperIcons);
				
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=supper[position];
					
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.BUY_IN_SHOP:
				//textview1.setText("您需要买些什么？");
				menuSimpleAdapter = createSimpleAdapter(shop,shopIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=shop[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.HOSPITAL:
				textview1.setText("是否要接受治疗？");
				menuSimpleAdapter = createSimpleAdapter(general,generalIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=general[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.SEND_PACK:
				textview1.setText("是否要交付包裹？");
				menuSimpleAdapter = createSimpleAdapter(general,generalIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=general[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.WATERFALL:
				textview1.setText("前面有一个瀑布，过去看看！嗯...水正在向下倾泄，水的速度是多少呢？");
				menuSimpleAdapter = createSimpleAdapter(numbers,numbersIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=numbers[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.WOODENHOUSE:
				textview1.setText("你觉得门是开启还是关闭的？");
				menuSimpleAdapter = createSimpleAdapter(general,generalIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=general[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.DESKTYPE:
				textview1.setText("这么多桌子真的有必要吗？留一个就好了吧……可是留哪一张呢？");
				menuSimpleAdapter = createSimpleAdapter(graphs,graphsIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=graphs[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.HUAPINGTYPE:
				textview1.setText("只有花没有瓶多可惜，应该是什么材质的花瓶呢？");
				menuSimpleAdapter = createSimpleAdapter(huaping,huapingIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=huaping[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.WATERVECTOR:
				textview1.setText("不知道花瓶里有多少水噢……");
				menuSimpleAdapter = createSimpleAdapter(huapingvector,huapingvectorIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=huapingvector[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.MURDER:
				textview1.setText("前面好像发生了什么……Aaaaa!(人群中间躺着一个人，满地是血)原来是一位年轻女子被谋害了，遇害时正好手中抓着一条断裂的口红，请用直觉推断她遇害的原因？");
				menuSimpleAdapter = createSimpleAdapter(murderChoice,murderIcons);
				gridView.setAdapter(menuSimpleAdapter);
				gridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String keyword=murderChoice[position];
						
						int key=toint(keyword);
						Intent intent=new Intent();
						intent.putExtra("choice", key);
						newChoice.this.setResult(1,intent);
						newChoice.this.finish();
					}
				});
				//创建对话框并显示
				gridView.setAdapter(menuSimpleAdapter);
				break;
			default:
				break;	
		}

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
		
		switch(type)
		{
			case Task.SUPPER:
				for(int i=0; i<supper.length; i++)
					if(str.equals(supper[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.BUY_IN_SHOP:
				for(int i=0; i<shop.length; i++)
					if(str.equals(shop[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.HOSPITAL:
				for(int i=0; i<general.length; i++)
					if(str.equals(general[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.SEND_PACK:
				for(int i=0; i<general.length; i++)
					if(str.equals(general[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.WATERFALL:
				for(int i=0; i<numbers.length; i++)
					if(str.equals(numbers[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.WOODENHOUSE:
				for(int i=0; i<general.length; i++)
					if(str.equals(general[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.DESKTYPE:
				for(int i=0; i<graphs.length; i++)
					if(str.equals(graphs[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.HUAPINGTYPE:
				for(int i=0; i<huaping.length; i++)
					if(str.equals(huaping[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.WATERVECTOR:
				for(int i=0; i<huapingvector.length; i++)
					if(str.equals(huapingvector[i]))
					{
						choice=i;
						break;
					}
				break;
			case Task.MURDER:
				for(int i=0; i<murderChoice.length; i++)
					if(str.equals(murderChoice[i]))
					{
						choice=i;
						break;
					}
				break;
			default:
				break;
		}
		
		return choice;
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			newChoice.this.finish();
		}
		return true;
	}
	
	public static final int 什么都不要 =4,
							汤 = 0,
							生菜色拉 = 1,
							腌酱菜 = 2,
							烤肉片 = 3,
							白饭 = 4,
							饮料 = 0,
							糖果 = 1,
							汉堡 = 2,
							三明治 = 3,
							是 = 0,
							否 = 1,
							正方形 = 0,
							长方形 = 1,
							圆形 = 2,
							椭圆形 = 3,
							三角形 = 4,
							玻璃 = 0,
							粘土 = 1,
							瓷器 = 2,
							金属 = 3,
							塑料 = 4,
							木 = 5,
							装满 = 0,
							一半 = 1,
							空的 = 2;
}
