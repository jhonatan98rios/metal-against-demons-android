package com.teixeirarios.mad;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.teixeirarios.mad.lib.domain.abstracts.Navigator;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.infra.events.EventManager;

public class AndroidLauncher extends AndroidApplication implements Navigator {

	MAD game;
	private EventManager eventManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		game = new MAD(this);
		eventManager = EventManager.getInstance();
		initialize(game, config);
	}

	@Override
	public void navigateToMenu() {
		if (game != null) {
			game.dispose();
		}

		Intent intent = new Intent(this, MenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

	@Override
	public void restartApp() {
		Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
		int pendingIntentId = 123456;
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), pendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pendingIntent);

		System.exit(0);
	}

	@Override
	public void onBackPressed() {
		if (GameStatus.getInstance().isPlaying()) {
			this.eventManager.emit("status:pause");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (game != null) {
			game.dispose();
			game = null;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
		onDestroy();
	}
}