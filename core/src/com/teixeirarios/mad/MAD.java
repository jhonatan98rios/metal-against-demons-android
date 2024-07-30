package com.teixeirarios.mad;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.domain.abstracts.Navigator;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManagerFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.AzazelFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CrawlerFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CyclopeFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.DragonFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.GargoyleFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.NightmareFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.SpiritFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.TormentorFactory;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.orb.OrbManager;
import com.teixeirarios.mad.lib.domain.entities.orb.OrbManagerFactory;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.player.PlayerFactory;
import com.teixeirarios.mad.lib.domain.entities.scenario.Scenario;
import com.teixeirarios.mad.lib.domain.entities.skills.SkillManager;
import com.teixeirarios.mad.lib.domain.entities.skills.SkillManagerFactory;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.drivers.analytics.AbstractAnalyticsService;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.canvas.RenderStack;
import com.teixeirarios.mad.lib.infra.canvas.ShapeCanvas;
import com.teixeirarios.mad.lib.infra.canvas.UserInterface;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.infra.input.ControllerFactory;
import com.teixeirarios.mad.lib.infra.input.VirtualJoystick;
import com.teixeirarios.mad.lib.infra.sound.BackgroundSound;

import java.util.ArrayList;


public class MAD extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	EnemyManager enemyManager;
	Scenario scenario;
	Stage stage;
	VirtualJoystick joystick;
	Camera camera;
	GameStatus gameStatus;
	UserInterface userInterface;
	SkillManager skillManager;
	OrbManager orbManager;
	Navigator navigator;
	AbstractAnalyticsService analyticsService;
	//Viewport viewport;

	public MAD (Navigator navigator, AbstractAnalyticsService analyticsService) {
		// Remover o navigator como "this"
		this.navigator = navigator;
		this.analyticsService = analyticsService;
	}

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage();

		gameStatus = GameStatus.getInstance();
		joystick = ControllerFactory.create(stage);
		player = PlayerFactory.create(batch, joystick);

		scenario = new Scenario(batch);
		camera = new Camera(player);
		//viewport = new FitViewport((float) Constants.SCENARIO_WIDTH /4, (float) Constants.SCENARIO_HEIGHT /4, camera.camera);
		enemyManager = EnemyManagerFactory.create(batch, player, camera);
		skillManager = SkillManagerFactory.create(player, batch, enemyManager);
		orbManager = OrbManagerFactory.create(camera);

		userInterface = new UserInterface(stage, gameStatus, navigator, batch, analyticsService);
		userInterface.drawPauseButton();

		BackgroundSound.init();
		BackgroundSound.play();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		if (gameStatus.isPlaying()) {
			batch.begin();
			scenario.drawBackground();

			player.update();
			enemyManager.update();
			camera.update(batch);

			ArrayList<Body2D> body2DList = new ArrayList<>();
			body2DList.add(player);
			body2DList.addAll(enemyManager.getEnemies());

			RenderStack.render(body2DList, camera);
			skillManager.update();
			userInterface.drawLevel(
				"Level: " + player.playerStatus.level,
				24 + camera.getPosX(),
				Gdx.graphics.getHeight() - 24 + camera.getPosY()
			);

			if (batch.isDrawing()) {
				batch.end();
			}
			RenderStack.renderHealthBar(body2DList, camera);

			orbManager.checkOrbsCollection(player);
			orbManager.renderOrbs(Gdx.graphics.getDeltaTime());
		}

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

//	@Override
//	public void resize(int width, int height) {
//		viewport.update(width, height);
//	}
	
	@Override
	public void dispose () {
		stage.clear();
		enemyManager.dispose();
		player.dispose();
		BackgroundSound.dispose();
		joystick.dispose();
		scenario.dispose();
		userInterface.dispose();
		ShapeCanvas.dispose();

		GameStatus.instance = null;
		StageManager.instance = null;
		Player.instance = null;
		EventManager.instance = null;
		SpiritFactory.enemyCanvas = null;
		CrawlerFactory.enemyCanvas = null;
		CyclopeFactory.enemyCanvas = null;
		DragonFactory.enemyCanvas = null;
		AzazelFactory.enemyCanvas = null;
		GargoyleFactory.enemyCanvas = null;
		NightmareFactory.enemyCanvas = null;
		TormentorFactory.enemyCanvas = null;
	}
}
