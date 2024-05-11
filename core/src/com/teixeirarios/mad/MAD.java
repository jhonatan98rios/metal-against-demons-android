package com.teixeirarios.mad;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.player.PlayerFactory;
import com.teixeirarios.mad.lib.domain.entities.scenario.Scenario;
import com.teixeirarios.mad.lib.infra.input.VirtualJoystick;


public class MAD extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	Scenario scenario;
	FitViewport viewport;
	Stage stage;
	VirtualJoystick joystick;
	
	@Override
	public void create () {
		viewport = new FitViewport(
				Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2
		);

		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);
		joystick = new VirtualJoystick(stage);

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);

		player = PlayerFactory.create(batch, joystick);
		scenario = new Scenario(batch);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		scenario.drawBackground();

		batch.begin();
		player.update();

		batch.end();
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
