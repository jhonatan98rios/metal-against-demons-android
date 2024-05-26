package com.teixeirarios.mad;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.domain.abstracts.Navigator;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManagerFactory;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatusFactory;
import com.teixeirarios.mad.lib.domain.entities.orb.OrbManager;
import com.teixeirarios.mad.lib.domain.entities.orb.OrbManagerFactory;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.player.PlayerFactory;
import com.teixeirarios.mad.lib.domain.entities.scenario.Scenario;
import com.teixeirarios.mad.lib.domain.entities.skills.SkillManager;
import com.teixeirarios.mad.lib.domain.entities.skills.SkillManagerFactory;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.canvas.RenderStack;
import com.teixeirarios.mad.lib.infra.canvas.UserInterface;
import com.teixeirarios.mad.lib.infra.input.ControllerFactory;
import com.teixeirarios.mad.lib.infra.input.VirtualJoystick;
import com.teixeirarios.mad.lib.infra.sound.BackgroundSound;


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

	public MAD (Navigator navigator) {
		this.navigator = navigator;
	}

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage();

		joystick = ControllerFactory.create(stage);
		player = PlayerFactory.create(batch, joystick);

		scenario = new Scenario(batch);
		camera = Camera.getInstance(batch, player);
		enemyManager = EnemyManagerFactory.create(batch, player, camera);
		gameStatus = GameStatusFactory.create();
		skillManager = SkillManagerFactory.create(player, batch);
		orbManager = OrbManagerFactory.create();

		userInterface = new UserInterface(stage, gameStatus, navigator);
		BackgroundSound.init();
		//BackgroundSound.play();

		userInterface.drawPauseButton();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		if (gameStatus.isPlaying()) {
			batch.begin();
			scenario.drawBackground();
			player.update();
			enemyManager.update();
			camera.update();

			Array<Body2D> body2DList = new Array<>();
			body2DList.add(player);

			for (int i = 0; i < enemyManager.getEnemies().size; i++) {
				Enemy enemy = enemyManager.getEnemies().get(i);
				body2DList.add(enemy);
			}

			RenderStack.render(body2DList);
			skillManager.update(enemyManager);

			if (batch.isDrawing()) {
				batch.end();
			}
			RenderStack.renderHealthBar(body2DList, camera);
			orbManager.renderOrbs();
		}

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.clear();
		enemyManager.dispose();
		player.dispose();
		BackgroundSound.dispose();
		joystick.dispose();
		scenario.dispose();
		userInterface.dispose();

		GameStatus.instance = null;
	}
}
