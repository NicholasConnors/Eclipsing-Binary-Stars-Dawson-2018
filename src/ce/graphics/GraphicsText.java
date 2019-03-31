package ce.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GraphicsText {
	private String text;
	private int x, y;
	private Font font;
	public GraphicsText(String text, int x, int y, int size) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = new Font("Font", Font.PLAIN, size);
	}
	
	public void paint(Graphics2D g2d) {
		g2d.setFont(this.font);
		g2d.setColor(Color.WHITE);
		g2d.drawString(this.text, this.x, this.y);
	}
}
