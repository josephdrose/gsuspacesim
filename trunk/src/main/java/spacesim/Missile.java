package spacesim;

public class Missile extends Rocket {
	public int fuel;
	public boolean boom;
	public double explosionRadius=10;

	public Missile() {
		super(0, 0, 0);
		fuel=1000;
		boom=false;
		forwardThrust=false;
		accel=.003;
		alive=false;
	}
	
	public void fire(double x, double y, double angle, double dx, double dy) {
		this.x=x;
		this.y=y;
		this.angle=angle;
		this.dx=dx;
		this.dy=dy;
		alive=true;
		forwardThrust=true;
	}
	
	public void move() {
		super.move();
		
		if(!alive) {
			return;
		}
		
		fuel--;
		
		if(fuel<=0)
			boom=true;
	}

	//getters, setters required by drools
	public boolean getBoom() {
		return boom;
	}
	
	public void setBoom(boolean b) {
		boom=b;
	}
	
	//read only
	public int getFuel() {
		return fuel;
	}
	
	public double getExplosionRadius() {
		return explosionRadius;
	}
}