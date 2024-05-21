package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.infra.events.EventManager;

public class UserInterface {

    public GameStatus gameStatus;
    public Stage stage;
    private ImageButton currentButton;

    public UserInterface(Stage stage, GameStatus gameStatus) {
        this.stage = stage;
        this.gameStatus = gameStatus;
    }

    private ImageButton drawButton(String imageUrl, Stage stage, float posX, float posY, float width, float height, final Runnable action) {
        Texture texture = new Texture(imageUrl);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        ImageButton button = new ImageButton(drawable);
        button.setPosition(posX, posY);
        button.setSize(width, height);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });

        stage.addActor(button);

        return button;
    }

    public ImageButton drawPauseButton(Stage stage) {
        return drawButton("ui/pause.png", stage, 32, Gdx.graphics.getHeight() - 80, 48, 48, new Runnable() {
            @Override
            public void run() {
                EventManager.getInstance().emit("status:pause");
                drawPauseAndPlay();
            }
        });
    }

    public ImageButton drawPlayButton(Stage stage) {
        return drawButton("ui/play.png", stage, 32, Gdx.graphics.getHeight() - 80, 48, 48, new Runnable() {
            @Override
            public void run() {
                EventManager.getInstance().emit("status:play");
                drawPauseAndPlay();
            }
        });
    }

    public void drawPauseAndPlay() {
        if (currentButton != null) {
            currentButton.remove();
        }

        if (gameStatus.isPlaying()) {
            currentButton = drawPauseButton(stage);
        }
        else if (gameStatus.isPaused()) {
            currentButton = drawPlayButton(stage);
        }
    }
}
