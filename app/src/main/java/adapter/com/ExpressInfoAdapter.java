package adapter.com;

import java.util.ArrayList;

import bean.com.ExpressInfo;
import expressapp.com.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExpressInfoAdapter extends BaseAdapter {

	private ArrayList<ExpressInfo> lists;
	private Context mContext;
	private LayoutInflater mInflater;

	public ExpressInfoAdapter(Context mContext, ArrayList<ExpressInfo> lists) {
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
		return position;
	}

	static class ViewHolder {
		TextView time;
		TextView context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.express_info_list, parent, false);
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.tv_info_time);
			holder.context = (TextView) convertView.findViewById(R.id.tv_info_context);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.time.setText(lists.get(position).time);
		holder.context.setText(lists.get(position).context);

		return convertView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
