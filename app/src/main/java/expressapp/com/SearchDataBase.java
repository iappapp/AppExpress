package expressapp.com;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import bean.com.DataBaseHistory;
import bean.com.ExpressHistory;
import queryutil.com.QueryExpressUtil;

public class SearchDataBase extends Fragment implements OnClickListener {

	private static final int N = 2;
	private Button queryExpressData;
	private EditText dataInput;
	String number,name,code;
	private DbUtils db;
	private DataBaseHistory isExist,dbHistory; // 是否存在的实体
	private ProgressDialog progressDialog;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.query_express_from_database, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		queryExpressData = (Button) getView().findViewById(R.id.queryExpressData);
		dataInput = (EditText) getView().findViewById(R.id.dataInput);
		queryExpressData.setOnClickListener(this);
		db = DbUtils.create(getActivity());
		isExist = null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.queryExpressData) {
			number= dataInput.getText().toString();
			Log.v("tag", number);
			try {
				isExist = db.findFirst(Selector.from(DataBaseHistory.class).where("expressnumber", "=", number));// 通过entity的属性查找
				Log.v("tag", isExist + "获取数据库");
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isExist!=null){
			code=isExist.getExpressCode();
			name=isExist.getExpressName();
			progressDialog = new ProgressDialog(getActivity());
		    QueryExpressUtil.queryExpressForNumber(number, name, code, getActivity(), progressDialog, N);
		    }else{
		    	Toast.makeText(getActivity(), "没有导入此订单号或订单号有误！\n请重新输入！", Toast.LENGTH_SHORT).show();
		    }
		}

	}

}
