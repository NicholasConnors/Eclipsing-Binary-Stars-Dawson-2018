package ce.simulation;

import static ce.util.Physics.*;

public class Config {
	public String name;
	public Star starA, starB;
	public double inclination, longitudeOfTheNode;
	public double semiMajorAxis, eccentricity;
	
	private Config(String name, Star starA, Star starB, double i, double omega, double a, double e) {
		this.name = name;
		this.starA = starA;
		this.starB = starB;
		this.inclination = i;
		this.longitudeOfTheNode = omega;
		this.semiMajorAxis = a;
		this.eccentricity = e;
	}
	
	public static final Config[] configurations = {
			new Config(
					"Algol",
					new Star(SOLAR_MASS * 3.17, SOLAR_RADIUS * 2.73, 13000),
					new Star(SOLAR_MASS * 0.7, SOLAR_RADIUS * 3.48, 4500),
					98.7, 43.43, 0.062 * AU, 0
					),
			new Config(
					"DP Leonis", 
					new Star(SOLAR_MASS * 0.6, SOLAR_RADIUS * 0.01, 13500), 
					new Star(SOLAR_MASS * 0.09, SOLAR_RADIUS * 0.12, 3000),
					79.5, 0, 0.0027 * AU, 0
					),
			new Config(
					"MY Camelopardalis",
					new Star(SOLAR_MASS * 37.7, SOLAR_RADIUS * 7.6, 42000),
					new Star(SOLAR_MASS * 31.6, SOLAR_RADIUS * 7.01, 39000),
					62, 0, 19.24 * SOLAR_RADIUS, 0
					),
			new Config(
					"VV Cephei",
					new Star(SOLAR_MASS * 18.2, SOLAR_RADIUS * 1050, 3800),
					new Star(SOLAR_MASS * 18.4, SOLAR_RADIUS * 25, 38000),
					84, 0, 24.8 * AU, 0.346
					),
			new Config(
					"R Canis Majoris",
					new Star(SOLAR_MASS * 1.67, SOLAR_RADIUS * 1.78, 7300),
					new Star(SOLAR_MASS * 0.22, SOLAR_RADIUS * 1.22, 4350),
					90, 0, 0.027 * AU, 0
					),
			new Config(
					"Kepler-34",
					new Star(SOLAR_MASS * 1.04, SOLAR_RADIUS * 1.16, 5900),
					new Star(SOLAR_MASS * 1.02, SOLAR_RADIUS * 1.09, 5800),
					89, 0, 0.22 * AU, 0.52
					),
			new Config(
					"Beta Lyrae",
					new Star(SOLAR_MASS * 13.16, SOLAR_RADIUS * 6, 30000),
					new Star(SOLAR_MASS * 2.97, SOLAR_RADIUS * 15.2, 13300),
					92.25, -254.4, 0.20 * AU, 0
					),
			new Config(
					"Example: Eccentric orbits",
					new Star(SOLAR_MASS * 1.4, SOLAR_RADIUS *  1.4, 7500),
					new Star(SOLAR_MASS * 0.7, SOLAR_RADIUS * 0.9, 3700),
					98, 0, 0.05 * AU, 0.8
					),
			new Config(
					"Example: Equal orbits",
					new Star(SOLAR_MASS * 0.5, SOLAR_RADIUS * 0.6, 2000),
					new Star(SOLAR_MASS * 0.5, SOLAR_RADIUS * 0.65, 4000),
					90, 50, 0.01 * AU, 0
					),
			new Config(
					"Example: Twin stars",
					new Star(SOLAR_MASS * 1, SOLAR_RADIUS * 1.4, 8000),
					new Star(SOLAR_MASS * 1, SOLAR_RADIUS * 1.4, 8000),
					90, 0, 0.03 * AU, 0
					),
			new Config(
				"Example: Contact binary", 
				new Star(SOLAR_MASS * 1.4, SOLAR_RADIUS * 1.4, 10000),
				new Star(SOLAR_MASS * 0.5, SOLAR_RADIUS * 7, 2000),
				90, 20, SOLAR_RADIUS * 10, 0
				)
	};
}
