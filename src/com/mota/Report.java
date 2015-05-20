package com.mota;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import chk.MD5;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Report extends Activity{
	public final String game_11[]=new String[]{"你的掌控力100%，愿意细细品味生活中的种种滋味，是个懂得享受生活的人，同时又有很强的气场，知道如何控制场面，擅长调动气氛，把握事态发展方向，具备领导能力。",
			"掌控力40%，在自己重视的场合、人物面前容易感到紧张，有时无法很好表现自己，但通常具备较强的个人能力，能够在某些特定领域独当一面，发挥非凡的才能。",
			"你的掌控力80%，喜欢享受料理的乐趣，讲究秩序，做事有规划，能够按照规划逐步实现目标，不会操之过急，通常具备较强的统筹能力。",
			"你的掌控力60%，为人直率、坦诚、执着，做事从不浪费时间，说话办事都喜欢直来直去，即便在短期这一目标无法达成，也毫不灰心，不久便会重整旗鼓。",
			"你的掌控力为20%，内向、谨慎、考虑事情细致入微，性格较为沉稳，虽然在行动前有所计划但不会轻举妄动，因为在你的内心深处十分排斥失败，渴望爱情与成功。"};
	public final String game_12[]=new String[]{"你潜意识里最大的弱点是疾病，你最感到战栗的莫过于自己得了不治之症，受尽治疗的折磨，最后狼狈而亡。你害怕身体上的苦痛和死亡的威胁，但这并不是由于你的胆怯和懦弱，而是源于对生活的热爱。",
			"你潜意识里最大的弱点是死亡，但并不是你的死亡，而是你最亲密的人的死亡。因为你的感情依赖度非常高，是个重感情的人，尤其是对于父母、配偶、兄弟姐妹，当不幸发生后，你将无法承受。",
			"你潜意识里最大的恐惧来自于一切自然界无法解释的现象，包括灵魂、报应之类。你内心中躲藏着自己拟造的鬼魅，而在梦境或意识模糊时出现，这是你非常难以克服的弱点，这可能与你幼年时的创伤有关。",
			"你潜意识里最大的弱点是背叛，你无法面对情人变心或亲密的挚友出卖你，在他人恶意背叛你时，你会脆弱得失去所有反击能力，不过这个弱点不易轻易被人察觉，只有面临困境时才会对最亲近的人有所显现。"};
	public final String game_13[]=new String[]{"你是一个愿意分享的人。",
			"你不容易打开心扉。"};
	public final String game_14[]=new String[]{"对于你信任的朋友，你会接受并完全相信他们。",
			"你选择朋友有点挑剔，多数只会同经常来往的朋友交往。",
			"你对朋友很挑剔，一生中的朋友不多，但只要是你认定的朋友就会非常交心。"};
	public final String game_15[]=new String[]{"你时常感到自己的人生不够充实。",
			"在你心中，你觉得自己只得到了你想要的一半。",
			"你觉得自己的生活十分充实。"};
	public final String game_16[]=new String[]{"在生活中，你的内心往往很脆弱，是个性格敏感的人。",
			"在生活中，你的内心十分坚强，是个有原则、有底线的人。"};
	public final String game_17[]=new String[]{"在感情中，你更注重精神的契合和灵魂的陪伴，并不刻意追求感官的满足，对你来说，感官的快乐必须建立在感情基础之上的。",
			"在感情中，你注重精神的契合和灵魂的陪伴，也期待实现感官的满足，对你来说，感官的快乐是基于感情的，是生活中不可或缺的部分。",
			"在感情中，精神的契合和灵魂的陪伴相对于感官的满足，二者对你一样重要，生活中不论少了哪一样东西都会使你感到并不完美。"};
	
	public final String game_21[]=new String[]{"1——在你看来挚友是唯一的",
			"2至5——你有几个好朋友",
			"6至10——你有很多好朋友"};
	public final String game_22[]=new String[]{"旧的——对过去的感情并不满意，与理想相差一定距离。",
			"新的——过去的感情值得怀念，并且对于未来的感情也有所憧憬。"};
	public final String game_23[]=new String[]{"是——伴侣在身边时也时常因为一些外在事物分心，对于自己追求的事物并不明确。",
			"不——忠诚度较高，伴侣在身边时不会因为其他事物过多分心。"};
	public final String game_24[]={"是——当你的伴侣不在你身边，你可能难以忍耐孤独。",
			"没有——甚至当你的伴侣不在你身边，你会时常思念他/她，忠于你的伴侣。"};
	public final String game_25[]={"汽车——是个会考虑下一步怎么走的人，通常会有一定的积蓄。",
			"钻石——理财态度保守，总是想着如何保值。",
			"信用卡—— 有多少用多少的享乐主义者，对未来的理财规划没有太多规划。"};
	public final String game_26[]=new String[]{"传统杯子——入境随俗，你很体贴人，四海之内皆朋友。",
			"水晶杯——跟自己的品味和原则相同的人，才是朋友。",
			"瓷杯——只要那个人很随和，你就可以和他交朋友。但对条件比你好太多的朋友心存芥蒂。"};
	public final String game_27[]=new String[]{"自己把碎片放好——主动道歉，总是觉得自己错，善于自我反省。",
			"待侍者收拾——总希望别人先低头，生活中较为强势。",
			"假装无所谓——很容易因为小小的过错而失去朋友，即使自己有错，也不愿意先低头，自尊心极强。"};
	public final String game_28[]=new String[]{"白——你的爱情观很美好，如果有伴侣的话，在你心中认为伴侣几乎是完美的。",
			"灰——你的爱情观念不偏激，不十分乐观也不消极，如果有伴侣的话，你能辩证的认识伴侣的优点缺点。",
			"黑——你的爱情观念有些消极，这可能与你之前的经历有关，如果有伴侣的话，你更容易发现ta的不足之处，容易忽视其优点。"};
	
	public final String game_31[]=new String[]{"小——低自我","中等——中自我","大——高自我"};
	public final String game_32[]=new String[]{"纸/木 ——谦虚的个性","金属——骄傲并坚持个性"};
	public final String game_33[]=new String[]{"过桥¬——追求平淡安稳的人生， 知足常乐，并不会因为金钱的诱惑而使自己陷入困境，面对困难也不并不心急，有耐性，但是一定程度上缺乏冒险精神。喜欢生活清闲自在的生活，但有时会因为安排不够周密而忘记事情。",
			"划船——一生都会非常的不稳定，高低起伏大。面对金钱容易妥协，赚钱的速度快，但同时花钱的速度也很快。常常可以在非常危险的时候想出一些能够减轻压力的方法，做事灵活不死板。",
			"游泳———个性勇敢坚强，为了达到某种目的，往往会让自己掉入一种很深的困境之中。面对金钱的魅力很容易妥协，赚钱的速度很快，但同时花钱的速度也很快，对待朋友出手大方，但也容易因金钱相关的事情使自己陷入两难境地。"};
	public final String game_34[]=new String[]{"金属桥梁——与朋友的友谊坚固","木制桥梁——与朋友的友谊一般","藤桥梁——与朋友相处得不好"};
	public final String game_35[]=new String[]{"发呆/吃草-你眼中的伴侣是一个非常顾家和谦虚的人。","奔跑-你眼中的伴侣是一个放荡不羁、追求自由的人。"};
	public final String game_36[]=new String[]{"龙卷风——在你的人生中的问题","盒子——你.你把你的问题留给自己.","桥梁——你的朋友.当你遇到问题时，你会去寻求朋友的帮助。",
			"马——你的伴侣.当你遇到问题时，你会寻求伴侣的帮助。"};
	
	
	private static final String APP_ID = "wx808e7f83fc6c1c28";
	private String sendText;
	private IWXAPI api;
	private MD5 test;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_window);
        api = WXAPIFactory.createWXAPI(this, APP_ID,true);
        api.registerApp(APP_ID);
        
        test = new MD5();
        try {
			test.getSign(this);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        getBun();
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 初始化一个WXTextObject对象
				WXTextObject textObj = new WXTextObject();
				textObj.text = sendText;
				
				// 用WXTextObject对象初始化一个WXMediaMessage对象
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObj;
				msg.description = sendText;

				// 构造一个Req
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = String.valueOf(System.currentTimeMillis()); // transaction字段用于唯一标识一个请求
				req.message = msg;
				
				// 调用api接口发送数据到微信
				api.sendReq(req);
			}	
		});
   		
	}
	
	public void getBun(){
		Bundle bundle = this.getIntent().getExtras();  
		int flag[] = bundle.getIntArray("flag");  
		TextView textview1=(TextView)findViewById(R.id.textView1);
		textview1.setMovementMethod(ScrollingMovementMethod.getInstance());
		switch(flag[0]){
		   case 1:{
			   sendText = "\n\n"+game_11[flag[1]]+"\n"+"\n\n"+game_12[flag[2]]+"\n"
			             +"\n"+game_13[flag[3]]+"\n"+"\n"+game_14[flag[4]]+"\n"
			             +"\n"+game_15[flag[5]]+"\n"+"\n"
			             +game_16[flag[6]]+"\n"+"\n"+game_17[flag[7]]+"\n";
			   textview1.setText(sendText);
			   break;
		   }
		   case 2:{
			   if(flag[1]>=1&&flag[2]<=4)
				   flag[1]=1;
			   else if(flag[1]>4&&flag[1]<9)
				   flag[1]=2;
			   textview1.setText("\n一串金钥匙，你认为有多少条钥匙？(用1到10表示)\n"+game_21[flag[1]]+"\n"+"\n这座宫殿是新的还是旧的？\n"+game_22[flag[2]]+"\n"
					   +"\n看见一池黑水上浮着发光的宝石，你会捡起宝石吗？\n"+game_23[flag[3]]+"\n"+"\n水池装着清水，水面上有钱，你会捡起钱吗？\n"+game_24[flag[4]]+"\n"
					   +"\n您的理财态度:\n"+game_25[flag[5]]+"\n"+"\n西域风情的杯子  欧洲水晶高脚杯  中国青花瓷杯子  选择哪一个？\n"+"你的人际EQ有多高:\n"+game_26[flag[6]]
					   +"\n"+"\n和朋友及情人吵架时，你的态度\n"+game_27[flag[7]]+"\n"+"\n发现三匹很喜欢的马(白色/灰色//黑色)，我可以试养其中一匹，我选择？\n"+game_28[flag[8]]
				       +"\n");
			   break;
		   }
		   case 3:{
			   textview1.setText("\n你觉得盒子有多在？ (小/中/大)\n"+game_31[flag[1]]+"\n"+"\n盒子是什么材质的？ (纸/木/金属)\n"+game_32[flag[2]]+"\n"
					   +"\n过河方式\n"+game_33[flag[3]]+"\n"+"\n桥是用什么做的？(金属/木头/藤)\n"+game_34[flag[4]]+"\n"+"\n马在做什么？(发呆/吃草/奔跑)\n"
		               +game_35[flag[5]]+"\n"+"\n龙卷风来了，你离马有一段距离\n"+game_36[flag[6]]+"\n");
			   break;
		   }
		   case 4:{
			   
		   }
		}
	}
	
	
	
}
