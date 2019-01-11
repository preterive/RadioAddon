package de.luca.Main;

import java.io.InputStream;

import com.google.common.base.Objects;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import net.minecraft.client.Minecraft;

public class Radio {
	private Player player;
	private Thread thread;

	public boolean isrunning() {
		return thread != null;

	}

	public void stop() {
		if (isrunning()) {
			thread.interrupt();
			thread = null;
			if (player != null) {
				player.close();
			}

		}
	}

	public void Start() {
		java.util.Objects.requireNonNull(player);
	thread = new Thread() {
			public void run() {
				
				try {
					player.play();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
				
			}
			
		

		};
		thread.start();

	}
	public void SetStream(InputStream inputStream) {
		try {
			player = new Player(inputStream);
			player.setVolume(Main.Volume);
		} catch (JavaLayerException e) {
			e.printStackTrace();
			
		}
		
		
	}
}
