package ce.util;

import ce.simulation.Star;

public class Physics {
	public static final double SOLAR_MASS = 1.989 * Math.pow(10, 30); 		//kg
	public static final double SOLAR_RADIUS = 6.957 * Math.pow(10, 8);		//m
	public static final double SOLAR_TEMPERATURE = 5778;					//K
	public static final double SOLAR_LUMINOSITY = 3.828 * Math.pow(10, 26);	//W
	
	public static final double AU = 1.496 * Math.pow(10, 11);				//m
	public static final double LIGHTYEAR = 9.461 * Math.pow(10, 15);		//m
	public static final double PARSEC = 3.086 * Math.pow(10, 16);			//m
	
	public static final double G = 6.674 * Math.pow(10, -11); 				//m^3 kg^-1 s^-2
	public static final double WEINS_CONSTANT = 2.898 * Math.pow(10, -3);	//m K
	public static final double STEFAN_BOLTZMANN_CONSTANT = 5.670 * Math.pow(10, -8); //W m^-2 k^4
	
	/** Calculate the Vector distance (m) between two stars */
	public static Vector distance(Star starA, Star starB) {
		return Vector.subtraction(starA.getPosition(), starB.getPosition());
	}
	
	/** Calculate gravitational force (N) between two stars */
	public static double gravitationalForce(Star starA, Star starB) {
		double r = distance(starA, starB).magnitude();
		return (G * starA.getMass() * starB.getMass()) / (r * r);
	}
	
	/** Calculates an acceleration due to gravity vector */
	public static Vector gravitationalAcceleration(Star starA, Star starB) {
		double acceleration = -gravitationalForce(starA, starB) / starA.getMass();
		Vector distance = Vector.subtraction(starA.getPosition(), starB.getPosition());
		return Vector.multiplication(distance.unit(), acceleration);
	}
	
	/** Calculate the position of the center of mass (m) between two stars */
	public static Vector barycenter(Star starA, Star starB) {
		double totalMass = starA.getMass() + starB.getMass();
		
		Vector weightedPositionA = Vector.multiplication(starA.getPosition(), starA.getMass());
		Vector weightedPositionB = Vector.multiplication(starB.getPosition(), starB.getMass());
		Vector weightedSum = Vector.addition(weightedPositionA, weightedPositionB);
		
		return Vector.multiplication(weightedSum, 1 / totalMass);
	}
	
	/** Calculate the velocity required at apoapsis for the first star to orbit the second */
	public static double apoapsisVelocity(Star starA, Star starB) {
		double M = starA.getMass() + starB.getMass();
		double r = Vector.subtraction(starA.getPosition(), starB.getPosition()).magnitude();
		
		return Math.sqrt(G * M / r);
	}
	
	/** Calculate the velocity required at apoapsis for the first star to orbit the second */
	public static double apoapsisVelocity(Star starA, Star starB, double e) {
		double M = starA.getMass() + starB.getMass();
		double r = Vector.subtraction(starA.getPosition(), starB.getPosition()).magnitude();
		
		double a = r;
		double b = a * Math.sqrt(1 - e * e);
		
		return Math.sqrt((G * M / a) * (b / r) * (b / r));
	}
	
	public static double period(Star starA, Star starB) {
		double r = Vector.subtraction(starA.getPosition(), starB.getPosition()).magnitude();
		double denominator = G * (starA.getMass() + starB.getMass());
		return 2 * Math.PI * Math.sqrt(r * r * r / denominator);
	}
	
	public static double smallAngleApprox(double angle, double distance) {
		return angle * distance / 206265;
	}
	
	public static double smallAngleApproxParsec(double angle, double parsec) {
		return angle * parsec;
	}
	
	public static double areaOfIntersectingCircles(double x0, double y0, double r0, double x1, double y1, double r1) {
		double dx = x0 - x1;
		double dy = y0 - y1;
		double dist = Math.sqrt(dx * dx + dy * dy);
		
		//Circles don't intersect
		if(dist >= r0 + r1) return 0;
		
		//Circles fully intersect
		if(dist < Math.abs(r1 - r0)) return Math.PI * Math.min(r0 * r0, r1 * r1);
		
		if(r1 < r0) {
			//swap
			double temp = r1;
			r1 = r0;
			r0 = temp;
		}
		
		double a = r0 * r0 * Math.acos((dist * dist + r0 * r0 - r1 * r1) / (2 * dist * r0));
		double b = r1 * r1 * Math.acos((dist * dist + r1 * r1 - r0 * r0) / (2 * dist * r1));
		double c = 0.5 * Math.sqrt((-dist + r0 + r1) * (dist + r0 - r1) * (dist - r0 + r1) * (dist + r0 + r1));
		
		return a + b - c;
	}
	
	public static String timeString(double time) {
		int years = (int) (time / 365 / 24 / 60 / 60);
		int days = (int) ((time / 24 / 60 / 60) % 365);
		int hours = (int) ((time / 60 / 60) % 24);
		int minutes = (int) ((time / 60) % 60);
		
		return String.format("%4dy %4dd %4dh %4dm", years, days, hours, minutes);
	}
}
