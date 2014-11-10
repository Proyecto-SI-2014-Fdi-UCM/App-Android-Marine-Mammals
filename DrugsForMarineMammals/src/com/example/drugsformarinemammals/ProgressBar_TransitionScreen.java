package com.example.drugsformarinemammals;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class ProgressBar_TransitionScreen extends Activity {
	private AccesoBBDD task;
	private ProgressBar statusbar;
	public static final String PREFS_NAME = "MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.progressbar_transitionscreen);
		statusbar = (ProgressBar) findViewById(R.id.progressBar1);
		task = new AccesoBBDD();
		task.execute();
	}

	private void LongTask() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
	}

	public class AccesoBBDD extends AsyncTask<Void, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			for (int i = 1; i <= 10; i++) {
				LongTask();
				publishProgress(i * 10);
			}
			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int progreso = values[0].intValue();
			statusbar.setProgress(progreso);
		}

		protected void onPreExecute() {
			statusbar.setMax(100);
			statusbar.setProgress(0);

		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				Intent i = new Intent(ProgressBar_TransitionScreen.this,
						ViewPager_MainMenu.class);
				startActivity(i);
				finish();
			}
		}
	}

}
