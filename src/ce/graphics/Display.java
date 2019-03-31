package ce.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class Display extends JPanel {
	private static final long serialVersionUID = 1L;
	private List<GraphicsObject> gObjects;
	private List<GraphicsText> gTexts;
	private String name;
	
	public Display(String name, int width, int height) {
		this.name = name;
		
		Dimension dimension = new Dimension(width, height);
		this.setPreferredSize(dimension);
		setBackground(Color.black);
		
		gObjects = new ArrayList<GraphicsObject>();
		gTexts = new ArrayList<GraphicsText>();
	}
	
	public synchronized void setGraphicsObjects(GraphicsObject... graphicsObjects) {
		this.gObjects.clear();
		this.gObjects.addAll(Arrays.asList(graphicsObjects));
	}
	
	public synchronized void setGraphicsText(GraphicsText...graphicsTexts) {
		this.gTexts.clear();
		this.gTexts.addAll(Arrays.asList(graphicsTexts));
	}
	
	public synchronized void render(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		Collections.sort(this.gObjects);
		for(GraphicsObject gObj : gObjects) {
			gObj.paint(g2d, getWidth(), getHeight());
		}
		
		for(GraphicsText gText : gTexts) {
			gText.paint(g2d);
		}
		
		//Center name at top of display
		g2d.setFont(new Font("Font", Font.PLAIN, 20));
		FontMetrics metrics = g2d.getFontMetrics();
		int x = (getWidth() - metrics.stringWidth(name)) / 2;
		g2d.setColor(Color.WHITE);
		g2d.drawString(name, x, 24);
	}
	
	@Override
	public void paint(Graphics g) {
		render(g);
	}
}
