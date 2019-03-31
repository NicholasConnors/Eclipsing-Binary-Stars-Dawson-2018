package ce.simulation;

import static ce.util.Physics.*;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import ce.graphics.Display;
import ce.graphics.GraphicsObject;
import ce.graphics.GraphicsText;
import ce.graphics.LightCurveDisplay;
import ce.util.Vector;

public class Simulation implements Runnable {
	//Display elements and variables
	private JFrame frame;
	private Display display, topDownDisplay;
	private LightCurveDisplay lightCurveDisplay;
	private final int displayWidth = 240, displayHeight = 240;
	
	//Graphics settings and variables
	private static final double TIME_STEP_MODIFIER = 15;	//How many seconds an orbital period will take
	private static final double FRAMES_PER_SECOND = 18;
	private static final double SCALE_MODIFIER = 2.5;		//Scale display by screen width / this * semiMajorAxis
	private final double scale;
	
	//Simulation settings
	private final String starName;
	private final Star starA, starB;
	private final double inclination;
	private final double longitudeOfTheNode;
	private final double semiMajorAxis;
	private final double eccentricity;
	private final double orbitalPeriod;
	
	private boolean running = false;
	private double simulationTime = 0;
	
	public Simulation(Config configuration) {
		//Load configuration
		starName = configuration.name;
		starA = configuration.starA;
		starB = configuration.starB;
		inclination = configuration.inclination;
		longitudeOfTheNode = configuration.longitudeOfTheNode;
		semiMajorAxis = configuration.semiMajorAxis;
		eccentricity = configuration.eccentricity;
		
		//Initialize star positions
		starA.setPosition(new Vector(0, 0, 0));
		starB.setPosition(new Vector(semiMajorAxis, 0, 0));
		
		//Initialize star velocity
		starA.setVelocity(new Vector(0, 0, apoapsisVelocity(starA, starB, eccentricity)));
		starB.setVelocity(new Vector(0, 0, 0));
		
		orbitalPeriod = period(starA, starB);
		
		//Initialize display
		scale = displayWidth / (SCALE_MODIFIER * semiMajorAxis);
		display = new Display("View from Earth", displayWidth, displayHeight);
		display.setGraphicsText(
				new GraphicsText(starName, 4, displayHeight - 14, 14),
				new GraphicsText(String.format("Screen width = %.2f AU", SCALE_MODIFIER * semiMajorAxis / AU), 4, displayHeight, 12)
				);
		topDownDisplay = new Display("Top-down view", displayWidth, displayHeight);
		lightCurveDisplay = new LightCurveDisplay("Light curve display", displayWidth, displayHeight);
		
		//Initialize JFrame + add displays
		frame = new JFrame("Eclipsing Binaries CE");
		frame.setLayout(new BorderLayout());
		frame.add(display, BorderLayout.LINE_START);
		frame.add(lightCurveDisplay, BorderLayout.CENTER);
		frame.add(topDownDisplay, BorderLayout.LINE_END);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		
		start();
	}
	
	private void start() {
		running = true;
		run();
	}

	@Override
	public void run() {
		double graphicsTimer = 0; //Timer to keep track of when to render the graphics displays
		
		double then = System.nanoTime();
		while(running) {
			double now = System.nanoTime();
			double dt = (now - then) * Math.pow(10, -9);
			then = now;
			
			graphicsTimer += dt;
			
			//For the purposes of the simulation, multiply the time step by the modifier
			dt *= (orbitalPeriod / TIME_STEP_MODIFIER);
			simulationTime += dt;
			
			//If the simulationTime is too big, reset it
			if(simulationTime > 1000 * 365 * 24 * 60 * 60) {
				simulationTime = 0;
			}
			
			//EXIT BUTTON
			//Clicking the exit button disposes the JFrame, check if JFrame has been disposed and exit running loop
			if(!frame.isDisplayable()) {
				running = false;
			}
			
			//PHYSICS CALCULATIONS
			////////////////////////////////////////////////////////////////////////////////////
			Vector barycenter = barycenter(starA, starB);
			
			Vector acc, deltaVel, deltaPos;
			
			//StarA
			acc = gravitationalAcceleration(starA, starB);
			deltaVel = Vector.multiplication(acc, dt);
			starA.setVelocity(Vector.addition(starA.getVelocity(), deltaVel));
			deltaPos = Vector.multiplication(starA.getVelocity(), dt);
			starA.setPosition(Vector.addition(starA.getPosition(), deltaPos));
			
			//StarB
			acc = gravitationalAcceleration(starB, starA);
			deltaVel = Vector.multiplication(acc, dt);
			starB.setVelocity(Vector.addition(starB.getVelocity(), deltaVel));
			deltaPos = Vector.multiplication(starB.getVelocity(), dt);
			starB.setPosition(Vector.addition(starB.getPosition(), deltaPos));
			
			//Luminosity
			double x, y, z;
			double xTheta, yTheta, zTheta;
			
			xTheta = inclination - 90;
			yTheta = 0;
			zTheta = longitudeOfTheNode;
			
			//Position of starA with regards to the Earth
			x = scale * (starA.getPosition().getX() - barycenter.getX());
			y = scale * (starA.getPosition().getY() - barycenter.getY());
			z = scale * (starA.getPosition().getZ() - barycenter.getZ());
			Vector scaledStarA = new Vector(x, y, z);
			Vector rotatedStarA = scaledStarA.rotationX(Math.toRadians(xTheta)).rotationY(Math.toRadians(yTheta)).rotationZ(Math.toRadians(zTheta));
			double scaledRadiusStarA = scale * (starA.getRadius());
			
			//Position of starB with regards to the Earth
			x = scale * (starB.getPosition().getX() - barycenter.getX());
			y = scale * (starB.getPosition().getY() - barycenter.getY());
			z = scale * (starB.getPosition().getZ() - barycenter.getZ());
			Vector scaledStarB = new Vector(x, y, z);
			Vector rotatedStarB = scaledStarB.rotationX(Math.toRadians(xTheta)).rotationY(Math.toRadians(yTheta)).rotationZ(Math.toRadians(zTheta));
			double scaledRadiusStarB = scale * (starB.getRadius());
			
			//Calculate percentage of eclipsed area to find luminosity
			double area = areaOfIntersectingCircles(
					rotatedStarA.getX(), rotatedStarA.getY(), scaledRadiusStarA,
					rotatedStarB.getX(), rotatedStarB.getY(), scaledRadiusStarB
					);
			double percentAreaStarA, percentAreaStarB, totalLuminosity;
			if(rotatedStarA.getZ() < rotatedStarB.getZ()) {
				double areaStarA = scaledRadiusStarA * scaledRadiusStarA * Math.PI;
				percentAreaStarA = (areaStarA - area) / areaStarA;
				percentAreaStarB = 1.0;
			} else {
				double areaStarB = scaledRadiusStarB * scaledRadiusStarB * Math.PI;
				percentAreaStarB = (areaStarB - area) / areaStarB;
				percentAreaStarA = 1.0;
			}
			totalLuminosity = percentAreaStarA * starA.luminosity() + percentAreaStarB * starB.luminosity();
			
			//GRAPHICS
			/////////////////////////////////////////////////////////////////////////////////
			if(graphicsTimer > 1 / FRAMES_PER_SECOND) {
				graphicsTimer -= 1 / FRAMES_PER_SECOND;
				
				//VIEW FROM EARTH
				display.setGraphicsObjects(
						new GraphicsObject(starA.getColor(), scaledRadiusStarA, rotatedStarA),
						new GraphicsObject(starB.getColor(), scaledRadiusStarB, rotatedStarB)
						);
				display.repaint();
				
				//TOP-DOWN VIEW
				topDownDisplay.setGraphicsObjects(
						new GraphicsObject(starA.getColor(), scaledRadiusStarA, scaledStarA.rotationX(90)),
						new GraphicsObject(starB.getColor(), scaledRadiusStarB, scaledStarB.rotationX(90))
						);
				topDownDisplay.repaint();
				
				//LIGHT CURVE DISPLAY
				double periodPercentage = (simulationTime / orbitalPeriod) % 2.0;
				double luminosityPercentage = totalLuminosity / (starA.luminosity() + starB.luminosity());
				
				lightCurveDisplay.addPoint(new Vector(periodPercentage, 1.0 - luminosityPercentage, 0)); 
				lightCurveDisplay.setGraphicsText(
						new GraphicsText(String.format("Time: %s", timeString(simulationTime)), 4, displayHeight - 14, 12),
						new GraphicsText(String.format("Luminosity: %.2e W", totalLuminosity), 4, displayHeight, 12)
						);
				lightCurveDisplay.repaint();	
				
			}
		}
	}
}
