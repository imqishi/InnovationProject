package com.mota;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Choice extends ListActivity{

	private int type = -1;
	String [] supper=new String[]
			{"汤","生菜色拉","腌酱菜","烤肉片","白饭"};
	String [] shop=new String[]
			{"饮料","糖果","汉堡","三明治","万能钥匙","什么都不需要"};
	String [] general =new String[]
			{"是","否"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice);
		
		TextView textview1=(TextView)findViewById(R.id.textView1);
		Intent intentFortype = getIntent();
		type = intentFortype.getExtras().getInt("choicetype");
		switch(type)
		{
			case Task.SUPPER:
				textview1.setText("离家前最后的晚餐，肚子好饿，用烤肉套餐犒劳自己。先从哪个吃起呢？");
				this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,supper));
				break;
			case Task.BUY_IN_SHOP:
				textview1.setText("您需要买些什么？");
				this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,shop));
				break;
			case Task.HOSPITAL:
				textview1.setText("是否要接受治疗？");
				this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,general));
				break;
			default:
				break;	
		}
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
			default:
				break;
		}
		
		return choice;
	}
    @Override
	protected void onListItemClick(ListView l,View v,int position,long id){
		super.onListItemClick(l, v, position, id);
		Object o=this.getListAdapter().getItem(position);
		String keyword=o.toString();
		//Toast.makeText(this, "您选择的是：  "+keyword, Toast.LENGTH_LONG).show();
		
		int key=toint(keyword);
		Intent intent=new Intent();
		intent.putExtra("choice", key);
		Choice.this.setResult(RESULT_OK,intent);
		Choice.this.finish();
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
    	if(keyCode == KeyEvent.KEYCODE_BACK)   //屏蔽返回键
    	{
    		return true;
    	}
    	
    	return super.onKeyDown(keyCode, event);
    }
    
    public static final int 什么都不要 =5,
    						万能钥匙 = 4,
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
    		                否 = 1;
    	
}
