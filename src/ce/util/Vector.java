package ce.util;

public class Vector {
	private double x, y, z;
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public void setAll(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	public Vector unit() {
		if(this.magnitude() == 0) return null;
		
		return Vector.multiplication(this, 1 / this.magnitude());
	}
	
	public Vector rotationX(double theta) {
		double newX = this.x;
		double newY = this.y * Math.cos(theta) - this.z * Math.sin(theta);
		double newZ = this.y * Math.sin(theta) + this.z * Math.cos(theta);
		
		return new Vector(newX, newY, newZ);
	}
	
	public Vector rotationY(double theta) {
		double newX = this.z * Math.sin(theta) + this.x * Math.cos(theta);
		double newY = this.y;
		double newZ = this.z * Math.cos(theta) - this.x * Math.sin(theta);
		
		return new Vector(newX, newY, newZ);
	}
	
	public Vector rotationZ(double theta) {
		double newX = this.x * Math.cos(theta) - this.y * Math.sin(theta);
		double newY = this.x * Math.sin(theta) + this.y * Math.cos(theta);
		double newZ = this.z;
		
		return new Vector(newX, newY, newZ);
	}

	public static Vector addition(Vector v1, Vector v2) {
		double dx = v1.getX() + v2.getX();
		double dy = v1.getY() + v2.getY();
		double dz = v1.getZ() + v2.getZ();
		
		return new Vector(dx, dy, dz);
	}
	
	public static Vector subtraction(Vector v1, Vector v2) {
		double dx = v1.getX() - v2.getX();
		double dy = v1.getY() - v2.getY();
		double dz = v1.getZ() - v2.getZ();
		
		return new Vector(dx, dy, dz);
	}
	
	public static Vector multiplication(Vector v, double k) {
		double kx = v.getX() * k;
		double ky = v.getY() * k;
		double kz = v.getZ() * k;
		
		return new Vector(kx, ky, kz);
	}
	
	@Override
	public String toString() {
		return String.format("%f\t%f\t%f", x, y, z);
	}
}
