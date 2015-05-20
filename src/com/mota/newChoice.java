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
			{"��","����ɫ��","�罴��","����Ƭ","�׷�"};
	String [] shop=new String[]
			{"����","�ǹ�","����","������","����Կ��","ʲô������Ҫ"};
	String [] general =new String[]
			{"��","��"};
	String [] numbers=new String[]
			{"0","1","2","3","4","5","6","7","8","9"};
	String [] graphs = new String[]
			{"������","������","Բ��","��Բ��","������"};
	String [] huaping = new String[]
			{"����","ճ��","����","����","����","ľ"};
	String [] huapingvector = new String[]
			{"װ��","һ��","�յ�"};
	String [] murderChoice = new String[]
			{"ǿ�����ٽٲƽ�ɫ","���ѱ������������","�������ı�̬����Ϊ","����µĶ���"};
	
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
				textview1.setText("���ǰ������ͣ����Ӻö����ÿ����ײ������Լ����ȴ��ĸ������أ�");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.BUY_IN_SHOP:
				//textview1.setText("����Ҫ��Щʲô��");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.HOSPITAL:
				textview1.setText("�Ƿ�Ҫ�������ƣ�");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.SEND_PACK:
				textview1.setText("�Ƿ�Ҫ����������");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.WATERFALL:
				textview1.setText("ǰ����һ���ٲ�����ȥ��������...ˮ����������й��ˮ���ٶ��Ƕ����أ�");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.WOODENHOUSE:
				textview1.setText("��������ǿ������ǹرյģ�");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.DESKTYPE:
				textview1.setText("��ô����������б�Ҫ����һ���ͺ��˰ɡ�����������һ���أ�");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.HUAPINGTYPE:
				textview1.setText("ֻ�л�û��ƿ���ϧ��Ӧ����ʲô���ʵĻ�ƿ�أ�");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.WATERVECTOR:
				textview1.setText("��֪����ƿ���ж���ˮ�ޡ���");
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
				//�����Ի�����ʾ
				gridView.setAdapter(menuSimpleAdapter);
				break;
			case Task.MURDER:
				textview1.setText("ǰ���������ʲô����Aaaaa!(��Ⱥ�м�����һ���ˣ�������Ѫ)ԭ����һλ����Ů�ӱ�ı���ˣ�����ʱ��������ץ��һ�����ѵĿں죬����ֱ���ƶ���������ԭ��");
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
				//�����Ի�����ʾ
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
	
	public static final int ʲô����Ҫ =4,
							�� = 0,
							����ɫ�� = 1,
							�罴�� = 2,
							����Ƭ = 3,
							�׷� = 4,
							���� = 0,
							�ǹ� = 1,
							���� = 2,
							������ = 3,
							�� = 0,
							�� = 1,
							������ = 0,
							������ = 1,
							Բ�� = 2,
							��Բ�� = 3,
							������ = 4,
							���� = 0,
							ճ�� = 1,
							���� = 2,
							���� = 3,
							���� = 4,
							ľ = 5,
							װ�� = 0,
							һ�� = 1,
							�յ� = 2;
}
