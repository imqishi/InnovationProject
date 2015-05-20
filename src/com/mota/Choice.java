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
			{"��","����ɫ��","�罴��","����Ƭ","�׷�"};
	String [] shop=new String[]
			{"����","�ǹ�","����","������","����Կ��","ʲô������Ҫ"};
	String [] general =new String[]
			{"��","��"};
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
				textview1.setText("���ǰ������ͣ����Ӻö����ÿ����ײ������Լ����ȴ��ĸ������أ�");
				this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,supper));
				break;
			case Task.BUY_IN_SHOP:
				textview1.setText("����Ҫ��Щʲô��");
				this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,shop));
				break;
			case Task.HOSPITAL:
				textview1.setText("�Ƿ�Ҫ�������ƣ�");
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
		//Toast.makeText(this, "��ѡ����ǣ�  "+keyword, Toast.LENGTH_LONG).show();
		
		int key=toint(keyword);
		Intent intent=new Intent();
		intent.putExtra("choice", key);
		Choice.this.setResult(RESULT_OK,intent);
		Choice.this.finish();
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
    	if(keyCode == KeyEvent.KEYCODE_BACK)   //���η��ؼ�
    	{
    		return true;
    	}
    	
    	return super.onKeyDown(keyCode, event);
    }
    
    public static final int ʲô����Ҫ =5,
    						����Կ�� = 4,
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
    		                �� = 1;
    	
}
