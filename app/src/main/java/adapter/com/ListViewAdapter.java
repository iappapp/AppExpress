package adapter.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import expressapp.com.R;

public class ListViewAdapter extends BaseAdapter {
	private String[] names;
	private Context mContext;  
    private LayoutInflater mInflater;
    private int[] resIds;
	public ListViewAdapter(Context mContext,String[] names,int[] resIds) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		mInflater = LayoutInflater.from(this.mContext);
		this.names = names;
		this.resIds=resIds;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	static class ViewHolder {
		TextView text;
		ImageView image;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 ViewHolder holder;

		 if (convertView == null) {
			 convertView = mInflater.inflate(R.layout.list_view_item,parent, false);
			 holder = new ViewHolder();
			 holder.text = (TextView) convertView.findViewById(R.id.tv_list_item_title);
			 holder.image=(ImageView)convertView.findViewById(R.id.tv_list_item_image);
			 convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(names[position]);
		holder.image.setImageResource(resIds[position]);
		holder.text.setTextSize(40);
		return convertView;
	}
}
