package com.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam; // Kullanıcı Gözünden Kamera
    protected Vector3 mouse;
    protected GameStateManager gsm; //oyunun durumlarını kontrol etmek için manager

    protected State(GameStateManager gsm){ // constructor
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }


    // Kullancağımız metodarın ön tanımlaması
    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
