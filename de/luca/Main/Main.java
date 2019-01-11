package de.luca.Main;

import java.io.IOException;

import java.net.URL;
import java.util.List;

import javazoom.jl.player.Player;
import net.labymod.api.LabyModAPI;
import net.labymod.api.LabyModAddon;
import net.labymod.main.LabyMod;
import net.labymod.settings.LabyModSettingsGui;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.NumberElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ServerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;


public class Main extends LabyModAddon {
	Boolean Enabled = true;
	static Boolean RadioEnabled = false;
	static Radio radio = new Radio();
	private Player player;
	public static int Volume = 10;
	static String URL = "http://stream01.iloveradio.de/iloveradio1.mp3";
	public static RadioObject selected = GuiRadio.ilr;

	@Override
	protected void fillSettings(List<SettingsElement> args) {
	

	}

	@Override
	public void loadConfig() {
		getSubSettings().add(
				new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), new Consumer<Boolean>() {
					public void accept(Boolean accepted) {
						Main.this.Enabled = accepted;
						
						if(!accepted) {
							radio.stop();
							return;
						}
					}
				}, true));

			
		StringElement channelStringElement = new StringElement( "Channel URL", new ControlElement.IconData( Material.PAPER ),
				"Click on Done", new Consumer<String>() {
		    @Override
		    public void accept( String accepted ) {
		
		        if(accepted.toString().equalsIgnoreCase("Click On Done"))
		       	Minecraft.getMinecraft().displayGuiScreen(new GuiRadio(new GuiMainMenu()));   
		        	return;
		    }
		});

		getSubSettings().add( channelStringElement );
		



		NumberElement numberElement = new NumberElement( "Volume",
                new ControlElement.IconData( Material.ENCHANTED_BOOK ), 30 );


numberElement.addCallback( new Consumer<Integer>() {
    @Override
    public void accept( Integer accepted ) {
    	Main.this.Volume = accepted;
      
    }
} );


getSubSettings().add( numberElement );

	}

	@Override
	public void onDisable() {

	}

	@Override
	public void onEnable() {


		System.err.println("Starting Radio....");
		
	}
public static void Stop() {
	try {
		radio.stop();
		
	} catch (Throwable e) {
	
	}
	
}
	public static void Start() {

		if (RadioEnabled) {

			try {

				radio.SetStream(new URL(selected.url).openStream());
				radio.Start();
			} catch (Throwable e) {
				radio.stop();
				e.printStackTrace();
			}

		}
	}
}
