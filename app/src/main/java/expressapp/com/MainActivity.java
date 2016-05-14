package expressapp.com;

import queryutil.com.QueryExpressUtil;
import queryutil.com.*;

import com.lidroid.xutils.exception.DbException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import bean.com.DataBaseHistory;
import expressapp.com.R;

@SuppressLint("InflateParams")
public class MainActivity extends Fragment implements OnClickListener {
	// 自定义组件变量
	private Button searchBtn;
	private EditText expressCompany, expressListNumber;
	private ImageView numberScan;//扫描按钮
	private Button btnImportData;// 导入按钮
	
	// 设置属性
	private ProgressDialog progressDialog,progressDialogImport;

	private String code = "";

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.activity_main, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		boolean isNetwork = NetUtil.checkNet(getActivity());// 获取网络信息
		if (!isNetwork) {
			setNetwork();
			Toast.makeText(getActivity(), "网络连接不可用，请检查您的网络！", Toast.LENGTH_LONG).show();
		}

		// 获取资源中的组件
		searchBtn = (Button) getView().findViewById(R.id.searchBtn);
		expressCompany = (EditText) getView().findViewById(R.id.expressCompany);
		expressListNumber = (EditText) getView().findViewById(R.id.expressListNumber);
		numberScan = (ImageView) getView().findViewById(R.id.numberScan);
		btnImportData = (Button)getView().findViewById(R.id.btnImportData);
		
		// 添加控件设置
		expressCompany.setOnClickListener(this);
		numberScan.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		btnImportData.setOnClickListener(this);
	}

	private final static int SCANNIN_GREQUEST_CODE = 0;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.numberScan:// 扫描二维码
			Intent openCameraIntent = new Intent(getActivity(), MipcaActivityCapture.class);
			startActivityForResult(openCameraIntent, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.expressCompany:// 快递公司按钮
			Intent intent = new Intent(getActivity(), CompanyListShow.class);
			startActivityForResult(intent, 1);
			Log.d("tag", "1");
			break;
		case R.id.searchBtn:// 查询按钮
			String expressName = expressCompany.getText().toString();
			if (expressName.equals("")) {// 设置公司为空时的提示消息
				Toast.makeText(getActivity(), "快递公司不能为空，请先选择快递公司", Toast.LENGTH_SHORT).show();
			} else {
				String expressNumber = expressListNumber.getText().toString();// 获取快递公司信息
				if (expressNumber.equals("")) {// 设置快递单号为空时的提示消息
					Toast.makeText(getActivity(), "快递单号不能为空，请先输入快递单号", Toast.LENGTH_SHORT).show();
				} else {
					// searchForJson(number,name);
					progressDialog = new ProgressDialog(getActivity());
					QueryExpressUtil.queryExpressForNumber(expressNumber, expressName, code, getActivity(),
							progressDialog, 0);
					Log.d("tag", "2");
				}
			}
			break;
		case R.id.btnImportData://导入数据
			String name = expressCompany.getText().toString();
			if (name.equals("")) {// 设置公司为空时的提示消息
				Toast.makeText(getActivity(), "快递公司不能为空，请先选择快递公司", Toast.LENGTH_SHORT).show();
			} else {
				String number = expressListNumber.getText().toString();// 获取快递公司信息
				if (number.equals("")) {// 设置快递单号为空时的提示消息
					Toast.makeText(getActivity(), "快递单号不能为空，请先输入快递单号", Toast.LENGTH_SHORT).show();
				} else {
					// searchForJson(number,name);
					progressDialogImport = new ProgressDialog(getActivity());
					QueryExpressUtil.queryExpressForNumber(number, name, code, getActivity(),
							progressDialogImport, 3);
					Log.d("tag", "3");
				}
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (data == null) {
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {// 快递公司控件触发时
			code = data.getStringExtra("code");
			String name = data.getStringExtra("name");
			expressCompany.setText(name);
			expressListNumber.setText("");
		} else if (requestCode == 0) {
			// 扫描按钮触发的
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			expressListNumber.setText(scanResult);
		}
	}

	public void setNetwork() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(R.string.netstate);
		builder.setMessage(R.string.setnetwork);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent("/");
				ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
				mIntent.setComponent(comp);
				mIntent.setAction("android.intent.action.VIEW");
				startActivity(mIntent);
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create();
		builder.show();
	}

}
