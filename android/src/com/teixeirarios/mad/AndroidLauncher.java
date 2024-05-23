package com.teixeirarios.mad;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.teixeirarios.mad.lib.domain.abstracts.Navigator;
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
	public void onBackPressed() {
		this.eventManager.emit("status:pause");
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