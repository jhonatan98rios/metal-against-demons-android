package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private ImageButton pauseButton;
    private Group menuModal;
    private Navigator navigator;
    private EventManager eventManager;

    public UserInterface(Stage stage, GameStatus gameStatus, Navigator navigator) {
        this.stage = stage;
        this.gameStatus = gameStatus;
        this.navigator = navigator;
        this.eventManager = EventManager.getInstance();
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
            ((((float) Constants.SCENARIO_HEIGHT / 2) - menuModal.getHeight()) / 2) + 30
        );

        // Background e Title do modal
        setBackgroundModal(menuModal);
        setTitleModal(menuModal);

        // Botão para continuar a partida
        ImageButton continueButton = drawContinueButton(menuModal);
        menuModal.addActor(continueButton);

        // Botão para voltar para outra página
        ImageButton backButton = drawExitButton();
        menuModal.addActor(backButton);

        stage.addActor(menuModal);
    }

    public void setBackgroundModal(Group menuModal) {
        Texture bgTexture = new Texture("ui/modal_bg.png");
        Drawable bgDrawable = new TextureRegionDrawable(new TextureRegion(bgTexture));
        ImageButton bgImage = new ImageButton(bgDrawable);
        bgImage.setSize(menuModal.getWidth(), menuModal.getHeight());
        menuModal.addActor(bgImage);
    }

    public void setTitleModal(Group menuModal) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font.getData().setScale(3);
        Label titleLabel = new Label("Menu", labelStyle);
        titleLabel.setPosition((menuModal.getWidth() - titleLabel.getWidth()) / 2, menuModal.getHeight() - 50);
        menuModal.addActor(titleLabel);
    }

    public ImageButton drawExitButton() {
        String url = "ui/back.png";
        float posX = (menuModal.getWidth() - 300) / 2;
        float posY = (menuModal.getHeight() / 3);
        float width = 300;
        float height = 36;
        return drawButton(url, posX, posY , width, height, () -> {
            navigator.navigateToMenu();
        });
    }

    public ImageButton drawContinueButton(Group menuModal) {
        String url = "ui/continue.png";
        float posX = (menuModal.getWidth() - 300) / 2;
        float posY = (menuModal.getHeight() / 3) * 1.5f;
        float width = 300;
        float height = 36;

        return drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            EventManager.getInstance().emit("status:play");
        });
    }

    public void drawLevel(String content, float posX, float posY, SpriteBatch batch) {
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2.4f);
        font.draw(batch, content, posX, posY);
    }

    public void dispose() {
        if (pauseButton != null) pauseButton.remove();
        if (menuModal != null) menuModal.remove();
    }

    private void addEventListeners() {
        eventManager.on("status:pause", args -> {
            showMenuModal();
        });
    }
}
