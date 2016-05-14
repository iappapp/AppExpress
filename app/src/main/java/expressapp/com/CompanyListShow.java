package expressapp.com;

import expressapp.com.R;
import adapter.com.ListViewAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class CompanyListShow extends Activity {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.queryresult_layout);
		final int[] resIds = new int[] { R.drawable.shentong, R.drawable.youzheng, R.drawable.shunfeng,
				R.drawable.yuantong, R.drawable.zhongtong, R.drawable.yunda, R.drawable.tiantian, R.drawable.huitong,
				R.drawable.quanfeng, R.drawable.bangde, R.drawable.zaijisong, R.drawable.baishihuitong };

		final String[] all_name = getResources().getStringArray(R.array.common);
		final String[] all_code = getResources().getStringArray(R.array.common_code);
		listView = (ListView) findViewById(R.id.companyList);
		listView.setAdapter(new ListViewAdapter(this,all_name,resIds));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				data.putExtra("code", all_code[position]);
				data.putExtra("name", all_name[position]);
				setResult(0, data);
				finish();
			}

		});
	}
}
