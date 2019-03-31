package ce.simulation;

import static ce.util.Physics.*;

import java.awt.Color;

import ce.util.Vector;

public class Star {	
	private final double mass; 			//kg
	private final double radius;		//m
	private final double temperature; 	//K
	
	private Vector position; 	//m
	private Vector velocity;	//m s^-1
	
	/**
	 * @param mass in kg
	 * @param radius in m
	 * @param temperature in K
	 * @param luminosity in W
	 */
	public Star(double mass, double radius, double temperature) {
		this.mass = mass;
		this.radius = radius;
		this.temperature = temperature;
		
		this.position = new Vector();
		this.velocity = new Vector();
	}
	
	public double getMass() {
		return this.mass;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public double getTemperature() {
		return this.temperature;
	}
	
	public Vector getPosition() {
		return this.position;
	}
	
	public void setPosition(Vector v) {
		this.position = v;
	}
	
	public Vector getVelocity() {
		return this.velocity;
	}
	
	public void setVelocity(Vector v) {
		this.velocity = v;
	}
	
	public Color getColor() {
		if(this.temperature < 3900) return new Color(255, 0, 0);		//Red
		if(this.temperature < 5200) return new Color(255, 128, 0);		//Orange
		if(this.temperature < 6000) return new Color(255, 255, 32);		//Yellow
		if(this.temperature < 7500) return new Color(255, 255, 128);	//Yellow-white
		if(this.temperature < 10000) return new Color(255, 255, 255);	//White
		if(this.temperature < 30000) return new Color(128, 200, 255);	//Blue-white
		return new Color(64, 255, 255);									//Blue
	}
	
	public double luminosity() {
		return STEFAN_BOLTZMANN_CONSTANT * this.area() * Math.pow(this.temperature, 4);
	}
	
	public double area() {
		return 4 * Math.PI * Math.pow(this.radius, 2);
	}
}
