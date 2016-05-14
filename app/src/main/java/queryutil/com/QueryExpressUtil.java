package queryutil.com;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import bean.com.ExpressInfo;
import expressapp.com.DataExpressImportActivity;
import expressapp.com.DataExpressInfoActivity;
import expressapp.com.ExpressInfoActivity;
import expressapp.com.MyExpressInfoActivity;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class QueryExpressUtil {
	public static void queryExpressForNumber(final String number, final String name, final String code,
			final Context context, final ProgressDialog progressDialog, final int n) {
		String url;
		url = "http://api.ickd.cn/?id=102616&secret=16135ea51cb60246eff620f130a005bd&com=";
		url += code;
		url += "&nu=";
		url += number;
		url += "&type=json&encode=utf8&ord=asc";
		Log.v("tag", url);
		// HttpUtils使用方法：使用普通get方法
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				Log.d("tag", "查询中" + current);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject all = new JSONObject(responseInfo.result);
					String status = all.getString("status");

					if (status.equals("0")) { // 0代表查找失败
						String message = all.getString("message"); // message中存储着错误消息
						Toast.makeText(context, message + "查询失败", Toast.LENGTH_LONG).show();
						progressDialog.dismiss();
						return;
					}

					JSONArray jsonArray = all.getJSONArray("data");
					int length = jsonArray.length();
					Log.v("tag", "长度是" + length);
					Intent intent = null;
					if (n == 0) {
						intent = new Intent(context, ExpressInfoActivity.class);
					} else if (n == 1) {
						intent = new Intent(context, MyExpressInfoActivity.class);
					} else if (n == 2) {
						intent = new Intent(context, DataExpressInfoActivity.class);
					}else{
						intent = new Intent(context, DataExpressImportActivity.class);
					}
					ArrayList<ExpressInfo> infoList = new ArrayList<ExpressInfo>();
					for (int i = 0; i < length; i++) {
						ExpressInfo expressInfo = new ExpressInfo();
						String time = jsonArray.getJSONObject(i).getString("time");
						String context = jsonArray.getJSONObject(i).getString("context");
						expressInfo.time = time;
						expressInfo.context = context;
						infoList.add(expressInfo);
					}
					Bundle bundle = new Bundle();
					bundle.putParcelableArrayList("infos", infoList);
					intent.putExtras(bundle);
					intent.putExtra("name", name);
					intent.putExtra("number", number);
					intent.putExtra("code", code);
					progressDialog.dismiss();
					context.startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onStart() {
				progressDialog.setMessage("正在查询，请稍候.....");
				progressDialog.setTitle("提示");
				progressDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.v("tag", "卧槽，失败了" + msg);
				progressDialog.dismiss();
				Toast.makeText(context, "网络状况不好，检查个网络先。。。", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
