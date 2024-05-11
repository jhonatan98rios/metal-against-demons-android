package com.teixeirarios.mad;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.player.PlayerFactory;
import com.teixeirarios.mad.lib.domain.entities.scenario.Scenario;


public class MAD extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	Scenario scenario;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = PlayerFactory.create(batch);
		scenario = new Scenario(batch);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		scenario.drawBackground();
		player.update();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
