package ce.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import ce.util.Vector;

public class LightCurveDisplay extends Display {
	private static final long serialVersionUID = 1L;
	//x = 0 - 1 of orbital period; y = 0 - 1 of maximum luminosity
	private ArrayList<Vector> lightCurvePoints;

	public LightCurveDisplay(String name, int width, int height) {
		super(name, width, height);
		lightCurvePoints = new ArrayList<Vector>();
	}
	
	public synchronized void addPoint(Vector p) {
		if(lightCurvePoints.size() > 0 && p.getX() < lightCurvePoints.get(lightCurvePoints.size() - 1).getX()) {
			lightCurvePoints.clear();
		}
		lightCurvePoints.add(p);
	}
	
	@Override
	public synchronized void render(Graphics g) {
		super.render(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for(int i = 1; i < lightCurvePoints.size(); i++) {
			int x1 = (int) (lightCurvePoints.get(i - 1).getX() / 2 * getWidth() / 2 + getWidth() / 4);
			int y1 = (int) (lightCurvePoints.get(i - 1).getY() * getHeight() / 2 + getHeight() / 4);
			int x2 = (int) (lightCurvePoints.get(i).getX() / 2 * getWidth() / 2 + getWidth() / 4);
			int y2 = (int) (lightCurvePoints.get(i).getY() * getHeight() / 2 + getHeight() / 4);
			
			g2d.setColor(Color.GRAY);			
			g2d.drawLine(x1, y1, x2, y2);
		}
		//Draw axis
		g2d.setColor(Color.WHITE);	
		g2d.drawLine(getWidth() / 4, getHeight() / 4, getWidth() / 4, 3 * getHeight() / 4);
		g2d.drawLine(getWidth() / 4, 3 * getHeight() / 4, 3 * getWidth() / 4, 3 * getHeight() / 4);
		//Label axis
		g2d.setFont(new Font("Font", Font.PLAIN, 14));
		FontMetrics metrics = g2d.getFontMetrics();
		//X
		g2d.drawString("Time", (getWidth() - metrics.stringWidth("Time")) / 2, 3 * getHeight() / 4 + 16);
		//Y
		g2d.rotate(Math.toRadians(-90));
		g2d.drawString("Luminosity", -(getWidth() + metrics.stringWidth("Luminosity")) / 2, getHeight() / 4 - 4);
		g2d.rotate(Math.toRadians(90));
	}

}
