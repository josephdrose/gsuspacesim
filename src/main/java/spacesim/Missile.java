package spacesim;

public class Missile extends Rocket {
	public static double mass=10;
	public int fuel;
	public boolean boom;
	public double explosionRadius=100;
	public Missile(double x, double y, double angle, double dx, double dy) {
		super(x, y, angle);
		fuel=1000;
		boom=false;
		this.dx=dx;
		this.dy=dy;
		forwardThrust=true;
	}
	public void move() {
		super.move();
		fuel--;
		
		if(fuel<=0)
			boom=true;
	}
}