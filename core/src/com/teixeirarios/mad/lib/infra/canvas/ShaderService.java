package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.teixeirarios.mad.lib.domain.entities.player.Player;

public class ShaderService {

    ShaderProgram shader;
    SpriteBatch batch;
    Player player;

    public ShaderService(SpriteBatch batch) {
        this.batch = batch;
        this.player = Player.getInstance();

        String vertexShader = Gdx.files.internal("shaders/vertex.glsl").readString();
        String fragmentShader = Gdx.files.internal("shaders/fragment.glsl").readString();
        shader = new ShaderProgram(vertexShader, fragmentShader);
        if (!shader.isCompiled()) {
            Gdx.app.error("Shader", "Error compiling shader: " + shader.getLog());
        }
    }

    public void update() {
        if (player.playerStatus.isDamaged) {
            batch.setShader(shader);
        }
        shader.bind();
    }

    public void dispose() {
        batch.setShader(null);
        shader.dispose();
    }
}
