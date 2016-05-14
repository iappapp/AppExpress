package expressapp.com;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import adapter.com.DataExpressAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import bean.com.DataBaseHistory;
import queryutil.com.QueryExpressUtil;

//数据库存储页面设计
public class ExpressDataBase extends Fragment {
	private static final int N = 2;

	private ListView dataExpress;
	private List<DataBaseHistory> infoList;
	private ProgressDialog progressDialog;
	DataExpressAdapter adapter;
	private DataBaseHistory isExist;
	DbUtils db;
	private String number;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.database_activity, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		db = DbUtils.create(getActivity());
		dataExpress = (ListView) getView().findViewById(R.id.data_express);
		// infoList = getIntent().getExtras().getParcelableArrayList("lists");
		//点击列表查询订单的详细信息
		dataExpress.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Log.v("setOnItemClickListener", "setOnItemClickListener");
				progressDialog = new ProgressDialog(getActivity());
				number = infoList.get(position).getExpressNumber();
				String name = infoList.get(position).getExpressName();
				String code = infoList.get(position).getExpressCode();
				QueryExpressUtil.queryExpressForNumber(number, name, code, getActivity(), progressDialog, N);

			}
		});
		//
	dataExpress.setOnItemLongClickListener(new OnItemLongClickListener(){

	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
		// TODO Auto-generated method stub
		Log.v("setOnItemLongClick", "setOnItemLongClickListener");
		new AlertDialog.Builder(getActivity()).setTitle("操作")
				.setItems(R.array.arrcontent, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						final String[] PK = getResources().getStringArray(R.array.arrcontent);
						if (PK[which].equals("修改备注号码")) {
							final EditText editText = new EditText(getActivity());
							new AlertDialog.Builder(getActivity()).setTitle("请输入备注电话号码")
									.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
									.setPositiveButton("确定", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									number = infoList.get(position).getExpressNumber();
									// TODO Auto-generated method stub
									try {
										isExist = db.findFirst(Selector.from(DataBaseHistory.class)
												.where("expressnumber", "=", number));
									} catch (DbException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									isExist.setPhone(editText.getText().toString());
									try {
										db.update(isExist);
									} catch (DbException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									adapter = (DataExpressAdapter) dataExpress.getAdapter();
									adapter.notifyDataSetChanged();// 实现数据的实时刷新
								}
							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							}).create().show();

						}
						if (PK[which].equals("删除")) {
							String number = infoList.get(position).getExpressNumber();
							Log.v("number", number);
							try {
								isExist = db.findFirst(
										Selector.from(DataBaseHistory.class).where("expressnumber", "=", number));
							} catch (DbException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								db.deleteById(DataBaseHistory.class, isExist.getId());
							} catch (DbException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							infoList.remove(position);
							adapter = (DataExpressAdapter) dataExpress.getAdapter();
							if (!adapter.isEmpty()) {
								adapter.notifyDataSetChanged(); // 实现数据的实时刷新
							}
							Toast.makeText(getActivity(), PK[which] + "成功", Toast.LENGTH_LONG).show();
						}
						if (PK[which].equals("通过短信发送")) {
							String number = infoList.get(position).getExpressNumber();
							try {
								isExist = db.findFirst(
										Selector.from(DataBaseHistory.class).where("expressnumber", "=", number));
							} catch (DbException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							String phone=isExist.getPhone();
							Log.v("taglog","数据库中的电话号码是："+"phone");
							
							Uri uri = Uri.parse("smsto:"+phone);

							Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

							intent.putExtra("sms_body",
									"您的"+infoList.get(position).getExpressName()+"快递已到达"+infoList.get(position).getNewInfo()+",快递单号为"
							        +infoList.get(position).getExpressNumber()+"请速来收取快递，来时请报快递单号，谢谢！");
							startActivity(intent);
						}
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).show();
		return true;

	}

	});
}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		DbUtils db = DbUtils.create(getActivity());
		//List<ExpressHistory> infoList = null;
		try {
			infoList = db.findAll(DataBaseHistory.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(infoList == null || infoList.size() == 0){
			Toast.makeText(getActivity(), "当前还没有导入任何快递哦,导入数据后再来这里查看吧！", Toast.LENGTH_SHORT).show();
		}else{
		DataExpressAdapter adapter = new DataExpressAdapter(getActivity(), (ArrayList<DataBaseHistory>) infoList);
		dataExpress.setAdapter(adapter);
		}
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

}
