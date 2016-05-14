package adapter.com;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import bean.com.DataBaseHistory;
import expressapp.com.*;

public class DataExpressAdapter extends BaseAdapter {
	
	private ArrayList<DataBaseHistory> lists;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public DataExpressAdapter(Context mContext, ArrayList<DataBaseHistory> lists) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		mInflater = LayoutInflater.from(this.mContext);
		this.lists = lists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return  position;
	}
	
	static class ViewHolder {
		TextView remarkPhone;
		TextView expressNameAndNumber;
		TextView newInfo;
		TextView newTime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.data_express_item, parent, false);
			holder = new ViewHolder();
			holder.remarkPhone = (TextView) convertView.findViewById(R.id.db_remark_phone);
			holder.expressNameAndNumber = (TextView) convertView.findViewById(R.id.db_express_name_number);
			holder.newInfo = (TextView) convertView.findViewById(R.id.db_new_context);
			holder.newTime = (TextView) convertView.findViewById(R.id.db_new_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.remarkPhone.setText(lists.get(position).getPhone());
		holder.expressNameAndNumber
				.setText(lists.get(position).getExpressName() + "  " + lists.get(position).getExpressNumber());
		holder.newInfo.setText(lists.get(position).getNewInfo());
		holder.newTime.setText(lists.get(position).getNewDate());
		return convertView;
	}

}
