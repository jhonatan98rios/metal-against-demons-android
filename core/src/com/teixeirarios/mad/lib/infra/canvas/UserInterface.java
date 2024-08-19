package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teixeirarios.mad.lib.domain.abstracts.Navigator;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.skills.SkillCatalog;
import com.teixeirarios.mad.lib.drivers.analytics.AbstractAnalyticsService;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserInterface {

    private final GameStatus gameStatus;
    private final Stage stage;
    private ImageButton pauseButton;
    private Group menuModal;
    private final Navigator navigator;
    private final SpriteBatch batch;
    private final EventManager eventManager;
    private final BitmapFont font;
    private AbstractAnalyticsService analyticsService;


    public UserInterface(
            Stage stage,
            GameStatus gameStatus,
            Navigator navigator,
            SpriteBatch batch,
            AbstractAnalyticsService analyticsService
    ) {
        this.stage = stage;
        this.batch = batch;
        this.gameStatus = gameStatus;
        this.navigator = navigator;
        this.analyticsService = analyticsService;
        this.eventManager = EventManager.getInstance();
        this.font = new BitmapFont();
        font.getData().setScale(2.4f);
        addEventListeners();
        SkillCatalog.initSkills();
    }

    private Image drawImage(String imageUrl, float posX, float posY, float width, float height) {
        Texture texture = new Texture(imageUrl);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        Image image = new Image(drawable);
        image.setPosition(posX, posY);
        image.setSize(width, height);
        stage.addActor(image);
        return image;
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
            (((float) Constants.SCENARIO_WIDTH / 5) - menuModal.getWidth()) / 2,
            ((((float) Constants.SCENARIO_HEIGHT / 5) - menuModal.getHeight()) / 2) + 50
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
        ImageButton btn = drawButton(url, posX, posY , width, height, navigator::navigateToMenu);

        menuModal.addActor(btn);
    }

    public void drawVictoryTitle() {
        String url = "ui/victory.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 72;
        float posX = (menuModal.getWidth() - width) / 2f;
        float posY = (menuModal.getHeight() / 3) + 150;

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {});
        menuModal.addActor(btn);

        Map<String, String> params = new HashMap<>();
        params.put("category", "Game Over Menu");
        params.put("action", "Victory");
        analyticsService.logCustomEvent("event", params);
    }

    public void drawGameOverTitle() {
        String url = "ui/gameover.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 72;
        float posX = (menuModal.getWidth() - width) / 2f;
        float posY = (menuModal.getHeight() / 3) + 150;

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {});
        menuModal.addActor(btn);

        Map<String, String> params = new HashMap<>();
        params.put("category", "Game Over Menu");
        params.put("action", "Lose");
        analyticsService.logCustomEvent("event", params);

    }

    public void showGameOverModal(boolean isVictory) {
        menuModal = new Group();
        menuModal.setSize(500, 300);
        menuModal.setPosition(
                (((float) Constants.SCENARIO_WIDTH / 5) - menuModal.getWidth()) / 2,
                ((((float) Constants.SCENARIO_HEIGHT / 5) - menuModal.getHeight()) / 2) + 50
        );

        if (isVictory) {
            drawVictoryTitle();
        } else {
            drawGameOverTitle();
        }


        drawExitButton();
        stage.addActor(menuModal);
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
            (((float) Constants.SCENARIO_WIDTH / 5) - menuModal.getWidth()) / 2,
            ((((float) Constants.SCENARIO_HEIGHT / 5) - menuModal.getHeight()) / 2) + 50
        );

        ArrayList<String> randomSkills = SkillCatalog.getRandomSkills();
        for (int i = 0; i < randomSkills.size(); i++) {
            String skill = randomSkills.get(i);
            switch (skill) {
                case "SoundAttack":
                    drawSoundLevelUpButton(i);
                    break;
                case "ForceField":
                    drawForcefieldLevelUpButton(i);
                    break;
                case "Vampires":
                    drawVampireLevelUpButton(i);
                    break;
                case "FireWalk":
                    drawFireWalkLevelUpButton(i);
                    break;
                case "Lightning":
                    drawLightningLevelUpButton(i);
                    break;

                default:
                    break;
            }
        }


        stage.addActor(menuModal);
    }

    public void drawSoundLevelUpButton(int index) {
        String url = "ui/sound-levelup.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 112;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * (2.2f - index);

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("player:levelup:sound");
            eventManager.emit("status:play");

            SkillCatalog.addActiveSkill("SoundAttack");

            Map<String, String> params = new HashMap<>();
            params.put("category", "Level Up Menu");
            params.put("action", "Sound Attack");
            analyticsService.logCustomEvent("event", params);
        });

        menuModal.addActor(btn);
    }

    public void drawForcefieldLevelUpButton(int index) {
        String url = "ui/forcefield-levelup.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 112;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * (2.2f - index);

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("player:levelup:forcefield");
            eventManager.emit("status:play");

            SkillCatalog.addActiveSkill("ForceField");

            Map<String, String> params = new HashMap<>();
            params.put("category", "Level Up Menu");
            params.put("action", "Force Field");
            analyticsService.logCustomEvent("event", params);
        });

        menuModal.addActor(btn);
    }

    public void drawVampireLevelUpButton(int index) {
        String url = "ui/vampire-levelup.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 112;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * (2.2f - index);

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("player:levelup:vampires");
            eventManager.emit("status:play");

            SkillCatalog.addActiveSkill("Vampires");

            Map<String, String> params = new HashMap<>();
            params.put("category", "Level Up Menu");
            params.put("action", "Vampire Horde");
            analyticsService.logCustomEvent("event", params);
        });

        menuModal.addActor(btn);
    }

    public void drawFireWalkLevelUpButton(int index) {
        String url = "ui/firewalk-levelup.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 112;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * (2.2f - index);

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("player:levelup:firewalk");
            eventManager.emit("status:play");

            SkillCatalog.addActiveSkill("FireWalk");

            Map<String, String> params = new HashMap<>();
            params.put("category", "Level Up Menu");
            params.put("action", "Fire Walk");
            analyticsService.logCustomEvent("event", params);
        });

        menuModal.addActor(btn);
    }

    public void drawLightningLevelUpButton(int index) {
        String url = "ui/lightning-levelup.png";
        float width = Gdx.graphics.getWidth() * 0.9f;
        float height = 112;
        float posX = (menuModal.getWidth() - width) / 2;
        float posY = (menuModal.getHeight() / 3) * (2.2f - index);

        ImageButton btn = drawButton(url, posX, posY, width, height, () -> {
            menuModal.remove();
            eventManager.emit("player:levelup:lightning");
            eventManager.emit("status:play");

            SkillCatalog.addActiveSkill("Lightning");

            Map<String, String> params = new HashMap<>();
            params.put("category", "Level Up Menu");
            params.put("action", "Lightning");
            analyticsService.logCustomEvent("event", params);
        });

        menuModal.addActor(btn);
    }

    public void drawLevel(String content, float posX, float posY) {
        font.draw(batch, content, posX, posY);
    }

    public void dispose() {
        SkillCatalog.clearSkills();
        if (pauseButton != null) pauseButton.remove();
        if (menuModal != null) menuModal.remove();
    }

    private void addEventListeners() {
        eventManager.on("status:pause", args -> {
            showMenuModal();
            pauseButton.remove();
        });

        eventManager.on("status:play", args -> {
            drawPauseButton();
            menuModal.remove();
        });

        eventManager.on("player:levelup", args -> {
            showLevelUpModal();
            pauseButton.remove();
        });

        eventManager.on("status:gameover", args -> {
            Boolean isVictory = (Boolean) args[0];
            showGameOverModal(isVictory);
            pauseButton.remove();
        });
    }
}
