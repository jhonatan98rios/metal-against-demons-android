package com.teixeirarios.mad;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.teixeirarios.mad.lib.analytics.AnalyticsServiceJava;
import com.teixeirarios.mad.lib.domain.abstracts.Navigator;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.domain.entities.stage.StageModel;
import com.teixeirarios.mad.lib.infra.database.repository.AppContext;
import com.teixeirarios.mad.lib.infra.database.repository.UserRepository;
import com.teixeirarios.mad.lib.infra.events.EventManager;

public class AndroidLauncher extends AndroidApplication implements Navigator, AppContext {

	MAD game;
	private EventManager eventManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Recuperar o ID passado pela Activity Kotlin
		Intent intent = getIntent();
		int stageId = intent.getIntExtra("stageId", 0);

		StageManager stageManager = StageManager.getInstance();
		StageModel stageData = stageManager.getStageData(stageId);

		if (stageData == null) {
			throw new RuntimeException("Stage not found!");
		}

		UserRepository.getInstance(this);

		stageManager.setCurrentStage(stageData);

		AnalyticsServiceJava analyticsService = new AnalyticsServiceJava(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// Remover o navigator como "this"
		game = new MAD(this, analyticsService);
		eventManager = EventManager.getInstance();
		initialize(game, config);
	}

	@Override
	public void navigateToMenu() {
		if (game != null) {
			game.dispose();
		}
		Intent intent = new Intent(this, AppActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);
		finish();
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
}