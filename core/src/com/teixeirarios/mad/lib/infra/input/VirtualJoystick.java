package com.teixeirarios.mad.lib.infra.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teixeirarios.mad.lib.drivers.input.AbstractInputObserver;

import java.util.ArrayList;
import java.util.List;

public class VirtualJoystick {
    private Touchpad touchpad;
    private List<AbstractInputObserver> observers;

    public VirtualJoystick(Stage stage) {

        observers = new ArrayList<>();

        // Texturas para o fundo e botão do touchpad
        Texture backgroundTexture = new Texture("touchpad_background.png");
        Texture knobTexture = new Texture("touchpad_knob.png");

        // Convertendo as texturas em Drawable
        Drawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);
        Drawable knobDrawable = new TextureRegionDrawable(knobTexture);

        // Criando o touchpad manualmente
        Touchpad.TouchpadStyle style = new Touchpad.TouchpadStyle();
        style.background = backgroundDrawable;
        style.knob = knobDrawable;
        style.knob.setMinHeight(200);
        style.knob.setMinWidth(200);

        touchpad = new Touchpad(0, style);
        touchpad.setBounds(
                ((float) Gdx.graphics.getWidth() / 2) - 200,
                100,
                400,
                400
        );

        setEventListeners();
        stage.addActor(touchpad);
    }

    public void addObserver(AbstractInputObserver observer) {
        observers.add(observer);
    }

    public void setEventListeners() {
        touchpad.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setTouchDraggedNotifiers();
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                setTouchDraggedNotifiers();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (AbstractInputObserver observer : observers) {
                    observer.nofityStopMoveTouch();
                }
            }

            public void setTouchDraggedNotifiers() {
                for (AbstractInputObserver observer : observers) {
                    observer.nofityMoveTouch(getKnobX(), getKnobY());
                }
            }
        });
    }

    public float getKnobX() {
        return touchpad.getKnobPercentX();
    }
    public float getKnobY() {
        return touchpad.getKnobPercentY();
    }
}