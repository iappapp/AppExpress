package expressapp.com;

import java.util.ArrayList;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import adapter.com.ExpressInfoAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import bean.com.DataBaseHistory;
import bean.com.ExpressInfo;

public class DataExpressImportActivity extends Activity implements OnClickListener {
	private ListView lv_express_info_import_list;
	private String number;
	private String code;
	private String name;
	private Button btnBack;// 返回按钮
	private Button btn_import;// 导入按钮
	private DbUtils importDB;
	private ArrayList<ExpressInfo> infoList;
	private DataBaseHistory isImport;// 是否导入
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dataimportweb);
		// 获取数据
		infoList = getIntent().getExtras().getParcelableArrayList("infos");
		lv_express_info_import_list = (ListView) findViewById(R.id.lv_express_info_import_list);
		lv_express_info_import_list.setAdapter(new ExpressInfoAdapter(this, infoList));
		number = getIntent().getStringExtra("number");
		code = getIntent().getStringExtra("code");
		name = getIntent().getStringExtra("name");
		btnBack = (Button) findViewById(R.id.btn_import_back);
		btnBack.setOnClickListener(this);
		btn_import = (Button) findViewById(R.id.btn_import);
		btn_import.setOnClickListener(this);
		importDB = DbUtils.create(this);
		
		// 标记是否已经将此订单号的信息存入数据库
				isImport = null;
				try {
					isImport = importDB.findFirst(Selector.from(DataBaseHistory.class).where("expressnumber", "=", number));// 通过isImport的属性查找
					Log.v("tag", isImport + "");
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isImport != null) {
					btn_import.setText("已导入");
					isImport.setNewDate(infoList.get(infoList.size() - 1).time);
					isImport.setNewInfo(infoList.get(infoList.size() - 1).context);
					try {
						importDB.update(isImport, "newdate", "newinfo");
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}

		}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_import_back:
			finish();
			break;
		case R.id.btn_import:
			if (btn_import.getText().toString().equals("导入")) {
				final EditText editText = new EditText(this);
				new AlertDialog.Builder(this).setTitle("请输入收件人电话").setIcon(android.R.drawable.ic_dialog_info)
						.setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								DataBaseHistory dataBaseHistory = new DataBaseHistory();
								dataBaseHistory.setExpressName(name);
								dataBaseHistory.setExpressCode(code);
								dataBaseHistory.setExpressNumber(number);
								dataBaseHistory.setNewDate(infoList.get(infoList.size() - 1).time);
								dataBaseHistory.setNewInfo(infoList.get(infoList.size() - 1).context);
								dataBaseHistory.setPhone(editText.getText().toString());
								try {
									importDB.save(dataBaseHistory);
									Toast.makeText(DataExpressImportActivity.this, "导入数据库成功", Toast.LENGTH_SHORT).show();
									btn_import.setText("已导入");
								} catch (DbException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Toast.makeText(DataExpressImportActivity.this, "啊哦，导入数据库失败", Toast.LENGTH_SHORT).show();
								}
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						}).create().show();
			} else if (btn_import.getText().toString().equals("已导入")) {
				Toast.makeText(DataExpressImportActivity.this, "已导入该快递单号,无需重新导入", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
		
	}
}
