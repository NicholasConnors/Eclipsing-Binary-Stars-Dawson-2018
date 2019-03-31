package ce.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;

import ce.util.Vector;

public class GraphicsObject implements Comparable<GraphicsObject> {
	private final Color color;
	private final float radius;
	private final Vector position;
	
	public GraphicsObject(Color color, double radius, Vector position) {
		this.color = color;
		this.radius = (float) radius;		
		this.position = position;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public Vector getPosition() {
		return this.position;
	}

	@Override
	public int compareTo(GraphicsObject gObj) {
		return this.getPosition().getZ() > gObj.getPosition().getZ() ? 1 : -1;
	}
	
	public void paint(Graphics2D g2d, int displayWidth, int displayHeight) {
		float x = (float) (this.getPosition().getX() + displayWidth / 2);
		float y = (float) (this.getPosition().getY() + displayHeight / 2);
		float r = (float) (this.getRadius() * 1.5f);
		
		if(r < 1) r = 1;
		
		Color starColor = this.getColor();
		Color starColorLight = lighterColor(starColor);
		Color starColorFading = new Color(starColor.getRed(), starColor.getGreen(), starColor.getBlue(), 60);
		Color transparent = new Color(0, 0, 0, 0);
		
		float[] fractions = {0.0f, 0.3f, 0.62f, 0.66f, 1.0f};
		Color[] colors = {starColorLight, starColorLight, starColor, starColorFading, transparent};
		
		Paint paint = new RadialGradientPaint(x, y, r, fractions, colors, CycleMethod.NO_CYCLE);
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, displayWidth, displayHeight);
	}
	
	/** Returns a color with lower saturation */
	private static Color lighterColor(Color c) {
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		hsb[1] /= 2f; //Saturation
		int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
		return new Color(rgb);
	}
}
