package expressapp.com;

import java.util.ArrayList;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import adapter.com.ExpressInfoAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import bean.com.DataBaseHistory;
import bean.com.ExpressInfo;

//数据库保存的订单号的详细信息
public class DataExpressInfoActivity extends Activity implements OnClickListener {

	private ListView express_data_list;
	private Button btnBack;// 返回按钮
	private Button btnSave;// 保存按钮
	private ArrayList<ExpressInfo> infoList;
	private DataBaseHistory isExist;
	private DbUtils db;
	private String number;
	private String isStore;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_activity);
		// 获取数据
		infoList = getIntent().getExtras().getParcelableArrayList("infos");
		express_data_list = (ListView) findViewById(R.id.express_data_list);
		express_data_list.setAdapter(new ExpressInfoAdapter(this, infoList));
		number=getIntent().getStringExtra("number");
		Log.v("tag",number);
		btnSave = (Button) findViewById(R.id.express_save);
		btnSave.setOnClickListener(this);
		btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(this);
		db = DbUtils.create(this);
		isExist = null;
		try {
			isExist = db.findFirst(Selector.from(DataBaseHistory.class).where("expressnumber", "=", number));// 通过entity的属性查找
			Log.v("tag", isExist + "");
		} catch (DbException e) {
			e.printStackTrace();
		}
		if (isExist != null){
			isStore=isExist.getIsStore();
			Log.v("tag",isStore+"AAAAAA");
			if(isStore == null){
				isStore = "未领取";
			}
			if(isStore.endsWith("已领取")){
				btnSave.setText("已领取");
				Log.v("tag", btnSave.getText().toString());
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.express_save:
			if (btnSave.getText().toString().equals("未领取")) {
				isExist.setIsStore("已领取");
				try {
					db.update(isExist);
					Toast.makeText(DataExpressInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
					btnSave.setText(isExist.getIsStore());
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(DataExpressInfoActivity.this, "啊哦，保存失败了呢", Toast.LENGTH_SHORT).show();
				}
			} else if (btnSave.getText().toString().equals("已领取")) {
				Toast.makeText(DataExpressInfoActivity.this, "快递已经领取，请仔细查对单号", Toast.LENGTH_SHORT).show();
			}
		}

	}

}
