package expressapp.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {

	private static final long SPLASH_DISPLAY_LENGHT = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent mainIntent = new Intent(StartActivity.this, MainTabActivity.class);
				StartActivity.this.startActivity(mainIntent);
				StartActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
	}

}
