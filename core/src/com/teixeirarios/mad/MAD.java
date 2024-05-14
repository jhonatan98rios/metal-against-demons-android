package com.teixeirarios.mad;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManagerFactory;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.player.PlayerFactory;
import com.teixeirarios.mad.lib.domain.entities.scenario.Scenario;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.canvas.RenderStack;
import com.teixeirarios.mad.lib.infra.input.VirtualJoystick;

import java.util.ArrayList;
import java.util.List;


public class MAD extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;

	EnemyManager enemyManager;
	Scenario scenario;
	Stage stage;
	VirtualJoystick joystick;
	Camera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage();
		joystick = new VirtualJoystick(stage);

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);

		player = PlayerFactory.create(batch, joystick);

		scenario = new Scenario(batch);
		camera = new Camera(batch, player);
		enemyManager = EnemyManagerFactory.create(batch, player, camera);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();

		scenario.drawBackground();

		ArrayList<Body2D> body2DList = new ArrayList<>();
		body2DList.add(player);

//		for (Enemy enemy : enemyManager.getEnemies()) {
//			body2DList.add(enemy);
//		}

		RenderStack.update(body2DList);
		//player.update();
		camera.update();

		enemyManager.update();
		batch.end();
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
