package de.luca.Main;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

public class GuiRadio extends GuiScreen {

	public static final ArrayList<RadioObject> radio = new ArrayList<>();

	public static final RadioObject ilr = new RadioObject("ILoveRadio",
			"http://stream01.iloveradio.de/iloveradio1.mp3");
	public static final RadioObject ilm = new RadioObject("ILoveMashUp",
			"http://stream01.iloveradio.de/iloveradio5.mp3");
	public static final RadioObject ild = new RadioObject("ILove2Dance",
			"http://stream01.iloveradio.de/iloveradio2.mp3");
	public static final RadioObject ilt = new RadioObject("ILoveTop100Charts",
			"http://stream01.iloveradio.de/iloveradio9.mp3");
	public static final RadioObject ilth = new RadioObject("ILoveTheBattle",
			"http://stream01.iloveradio.de/iloveradio3.mp3");
	public static final RadioObject ilh = new RadioObject("ILoveHipHopTurnUp",
			"http://stream01.iloveradio.de/iloveradio13.mp3");
	public static final RadioObject ilrc = new RadioObject("ILoveRadioAndChill",
			"http://stream01.iloveradio.de/iloveradio10.mp3");
	public static final RadioObject bigfm = new RadioObject("BigFM", "http://srv04.bigstreams.de/bigfm-mp3-96");
	public static final RadioObject dasding = new RadioObject("DasDing",
			"https://swr-dasding-live.sslcast.addradio.de/swr/dasding/live/mp3/128/stream.mp3");
	public static final RadioObject bigfm = new RadioObject("Alles Radio", "http://stream.laut.fm/alles-radio");
	public static final RadioObject bigfm = new RadioObject("Alles Chill", "http://stream.laut.fm/alles-chill");
	public static final RadioObject bigfm = new RadioObject("Alles Dance", "http://stream.laut.fm/alles-dance");
	public static final RadioObject bigfm = new RadioObject("Alles Techno", "http://stream.laut.fm/alles-techno");
	public static final RadioObject bigfm = new RadioObject("Alles Hardstyle", "http://stream.laut.fm/alles-hardstyle");
	public static final RadioObject bigfm = new RadioObject("Alles Klub", "http://stream.laut.fm/alles-klub");


	static {
		radio.add(ilr);
		radio.add(ilm);
		radio.add(ild);
		radio.add(ilt);
		radio.add(ilth);
		radio.add(ilh);
		radio.add(ilrc);
		radio.add(bigfm);
		radio.add(dasding);
	}

	private GuiScreen p;
	private GuiButton select;
	private RadioList list;

	public GuiRadio(GuiScreen prev) {
		p = prev;
	}

	@Override
	public void initGui() {
		list = new RadioList(this);
		list.registerScrollButtons(7, 8);
		list.elementClicked(-1, false, 0, 0);

		buttonList.add(new GuiButton(0, width / 2 + 2, height - 30, 100, 20, "Back"));
		buttonList.add(select = new GuiButton(1, width / 2 - 102, height - 30, 100, 20, "Select"));

		super.initGui();
	}
	// ILoveRadio : http://stream01.iloveradio.de/iloveradio1.mp3

	@Override
	public void updateScreen() {
		select.enabled = list.selectedSlot != -1;
		super.updateScreen();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 0) {
				mc.displayGuiScreen(p);
			}
			if (button.id == 1) {
				if(Main.RadioEnabled){
					Main.Stop();
					Main.selected = radio.get(list.selectedSlot);
					Main.Start();
				}else {
					Main.RadioEnabled = true;
					Main.selected = radio.get(list.selectedSlot);
					Main.Start();
				}
			}
		}
		super.actionPerformed(button);
	}

	@Override
	public void handleMouseInput() throws IOException {
		list.handleMouseInput();
		super.handleMouseInput();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		list.drawScreen(mouseX, mouseY, partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private static class RadioList extends GuiSlot {

		private static int selectedSlot = -1;

		public RadioList(GuiScreen screen) {
			super(Minecraft.getMinecraft(), screen.width, screen.height - 56, 36, screen.height - 56, 30);
		}

		@Override
		protected int getSize() {
			return radio.size();
		}

		@Override
		protected void elementClicked(int id, boolean isDoubleClick, int mouseX, int mouseY) {
			selectedSlot = id;
			if(isDoubleClick) {
				if(Main.RadioEnabled){
					Main.Stop();
					Main.selected = radio.get(id);
					Main.Start();
				}else {
					Main.RadioEnabled = true;
					Main.selected = radio.get(id);
					Main.Start();
				}
			}
		}

		@Override
		protected boolean isSelected(int slotIndex) {
			return selectedSlot == slotIndex;
		}

		@Override
		protected void drawBackground() {
		}

		@Override
		protected void drawSlot(int id, int x, int y, int p_180791_4_, int mouseX, int mouseY) {
			RadioObject r = radio.get(id);
			if (Main.selected == r) {
//				glEnable(GL_BLEND);
//				
//				glBegin(GL_QUADS);
//				glColor4d(1, 0, 0, 0.68F);
//				
//				glVertex2d(x - 2, y - 2);
//				glVertex2d(x + 200, y - 2);
//				glVertex2d(x + 200, y + 30);
//				glVertex2d(x - 2, y + 30);
//				
//				glEnd();
//				
//				glDisable(GL_BLEND);
				Gui.drawRect(x - 2, y - 2, x + 220, y + 30, new Color(0, 0, 1, 0.68F).getRGB());
			}
			mc.fontRendererObj.drawStringWithShadow(r.name, x + 5, y + 8, -1);

//			glEnable(GL_BLEND);
//			
//			glLineWidth(1);
//			glBegin(GL_LINES);
//			glColor4d(1, 1, 14, 0.68F);
//			 
//			glVertex2d(x - 2, y + 30);
//			glVertex2d(x + 200, y + 30);
//			
//			glDisable(GL_BLEND);
			Gui.drawRect(x - 2, y + 29, x + 220, y + 30, Color.WHITE.getRGB());
		}
	}
}
