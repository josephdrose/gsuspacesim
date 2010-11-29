package spacesim;

public class Missile extends Rocket {
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

	//getters, setters required by drools
	public boolean getBoom(){return boom;}
	public void setBoom(boolean b){boom=b;}
	
	//read only
	public int getFuel(){return fuel;}
}