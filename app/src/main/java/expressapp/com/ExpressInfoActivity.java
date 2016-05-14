package expressapp.com;

import java.util.ArrayList;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

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
import bean.com.*;
import adapter.com.*;

//显示查询的结果
public class ExpressInfoActivity extends Activity implements OnClickListener {

	private ListView lv_express_info;
	private String number;
	private String code;
	private String name;
	private Button btnBack;// 返回按钮
	private Button btnSaveOrBack;// 保存按钮
	private DbUtils db;
	private ArrayList<ExpressInfo> infoList;
	private ExpressHistory isExist; // 是否存在的实体

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.express_info_activity);
		// 获取数据
		infoList = getIntent().getExtras().getParcelableArrayList("infos");
		lv_express_info = (ListView) findViewById(R.id.lv_express_info_list);
		lv_express_info.setAdapter(new ExpressInfoAdapter(this, infoList));
		number = getIntent().getStringExtra("number");
		code = getIntent().getStringExtra("code");
		name = getIntent().getStringExtra("name");
		btnSaveOrBack = (Button) findViewById(R.id.btn_info_save_or_delete);
		btnSaveOrBack.setOnClickListener(this);
		btnBack = (Button) findViewById(R.id.btn_info_back);
		btnBack.setOnClickListener(this);
		db = DbUtils.create(this);
		// 标记是否已经保存过此订单号的信息
		isExist = null;
		try {
			isExist = db.findFirst(Selector.from(ExpressHistory.class).where("expressnumber", "=", number));// 通过entity的属性查找
			Log.v("tag", isExist + "");
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isExist != null) {
			btnSaveOrBack.setText("删除");
			isExist.setNewDate(infoList.get(infoList.size() - 1).time);
			isExist.setNewInfo(infoList.get(infoList.size() - 1).context);
			try {
				db.update(isExist, "newdate", "newinfo");
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
		case R.id.btn_info_back:
			finish();
			break;
		case R.id.btn_info_save_or_delete:
			if (btnSaveOrBack.getText().toString().equals("保存")) {
				final EditText editText = new EditText(this);
				new AlertDialog.Builder(this).setTitle("请输入备注姓名").setIcon(android.R.drawable.ic_dialog_info)
						.setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								ExpressHistory expressHistory = new ExpressHistory();
								expressHistory.setExpressName(name);
								expressHistory.setExpressCode(code);
								expressHistory.setExpressNumber(number);
								expressHistory.setNewDate(infoList.get(infoList.size() - 1).time);
								expressHistory.setNewInfo(infoList.get(infoList.size() - 1).context);
								expressHistory.setName(editText.getText().toString());
								try {
									db.save(expressHistory);
									Toast.makeText(ExpressInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
									btnSaveOrBack.setText("删除");
								} catch (DbException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Toast.makeText(ExpressInfoActivity.this, "啊哦，保存失败了呢", Toast.LENGTH_SHORT).show();
								}
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						}).create().show();
			} else if (btnSaveOrBack.getText().toString().equals("删除")) {
				try {
					db.deleteById(ExpressHistory.class, isExist.getId());
					Toast.makeText(ExpressInfoActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
					finish();
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;

		default:
			break;
		}

	}

}
