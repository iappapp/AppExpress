package queryutil.com;

//判断手机连接网络的渠道
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	/** 检查当前手机是否连接网络 */
	public static boolean checkNet(Context context) {
		// 判断连接方式
		// 判断手机WIFI连接
		boolean wifiConnected = isWIFIConnected(context);
		// 判断手机mobile连接
		boolean mobileConnected = isMOBILEConnected(context);
		if (wifiConnected == false && mobileConnected == false) {
			// 如果都没有连接返回false，提示用户当前没有网络
			return false;
		}
		return true;
	}

	// 判断手机使用是wifi还是mobile连接网络
	public static boolean isWIFIConnected(Context context) {
		// Context.CONNECTIVITY_SERVICE).

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		// 手机连接Wifi
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/** 判断mobile连接 */
	public static boolean isMOBILEConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		// 手机连接Mobile
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

}
