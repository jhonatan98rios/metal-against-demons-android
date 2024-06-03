package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teixeirarios.mad.lib.domain.abstracts.Navigator;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.utils.Constants;

public class UserInterface {

    public GameStatus gameStatus;
    public Stage stage;
    public ImageButton pauseButton;
    private Group menuModal;
    private Navigator navigator;
    SpriteBatch batch;
    private EventManager eventManager;
    BitmapFont font;



    public UserInterface(Stage stage, GameStatus gameStatus, Navigator navigator, SpriteBatch batch) {
        this.stage = stage;
        this.batch = batch;
        this.gameStatus = gameStatus;
        this.navigator = navigator;
        this.eventManager = EventManager.getInstance();
        this.font = new BitmapFont();
        font.getData().setScale(2.4f);
        addEventListeners();
    }

    private ImageButton drawButton(String imageUrl, float posX, float posY, float width, float height, final Runnable action) {
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

    public void drawPauseButton() {
        if (pauseButton != null) pauseButton.remove();
        if (!gameStatus.isPlaying()) return;

        String url = "ui/pause.png";
        float posX = Gdx.graphics.getWidth() - 80;
        float posY = Gdx.graphics.getHeight() - 80;
        float width = 48;
        float height = 48;

        pauseButton = drawButton(url, posX, posY, width, height, () -> eventManager.emit("status:pause"));
    }

    public void showMenuModal() {
        menuModal = new Group();
        menuModal.setSize(500, 300);
        menuModal.setPosition(
            (((float) Constants.SCENARIO_WIDTH / 2) - menuModal.getWidth()) / 2,
            ((((float) Constants.SCENARIO_HEIGHT / 2) - menuModal.getHeight()) / 2) + 50
        );

        drawContinueButton();
        drawExitButton();

        stage.addActor(menuModal);
    }

    public void drawExitButton() {
        String url = "ui/back.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 72;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3);
        ImageButton btn = drawButton(url, posX, posY , width, height, () -> {
            //navigator.navigateToMenu();
        });

        menuModal.addActor(btn);
    }

    public void drawContinueButton() {
        String url = "ui/continue.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 72;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * 1.7f;

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("status:play");
        });

        menuModal.addActor(btn);
    }

    public void showLevelUpModal() {
        menuModal = new Group();
        menuModal.setSize(Gdx.graphics.getWidth() * 0.9f, 400);
        menuModal.setPosition(
            (((float) Constants.SCENARIO_WIDTH / 2) - menuModal.getWidth()) / 2,
            ((((float) Constants.SCENARIO_HEIGHT / 2) - menuModal.getHeight()) / 2) + 50
        );

        drawSoundLevelUpButton();
        drawForcefieldLevelUpButton();
        drawVampireLevelUpButton();

        stage.addActor(menuModal);
    }

    public void drawSoundLevelUpButton() {
        String url = "ui/sound-levelup.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 112;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * 2.2f;

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("player:levelup:sound");
            eventManager.emit("status:play");
        });

        menuModal.addActor(btn);
    }

    public void drawForcefieldLevelUpButton() {
        String url = "ui/forcefield-levelup.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 112;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * 1.2f;

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("player:levelup:forcefield");
            eventManager.emit("status:play");
        });

        menuModal.addActor(btn);
    }

    public void drawVampireLevelUpButton() {
        String url = "ui/vampire-levelup.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 112;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * 0.2f;

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("player:levelup:vampires");
            eventManager.emit("status:play");
        });

        menuModal.addActor(btn);
    }

    public void drawLevel(String content, float posX, float posY) {
        font.draw(batch, content, posX, posY);
    }

    public void dispose() {
        if (pauseButton != null) pauseButton.remove();
        if (menuModal != null) menuModal.remove();
    }

    private void addEventListeners() {
        eventManager.on("status:pause", args -> {
            showMenuModal();
            pauseButton.remove();
        });

        eventManager.on("status:play", args -> {
            menuModal.remove();
            drawPauseButton();
        });

        eventManager.on("player:levelup", args -> {
            showLevelUpModal();
        });
    }
}
