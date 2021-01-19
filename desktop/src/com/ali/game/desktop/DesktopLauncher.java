package com.ali.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ali.game.FlappyBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyBird.WIDTH; // bu kısımlar ile ekranımızın yükseklik
		config.height = FlappyBird.HEIGHT; // en ve başlık konfigrasyonunu
		config.title = FlappyBird.TITLE; // yapmış oluyoruz
		new LwjglApplication(new FlappyBird(), config);
	}
}
