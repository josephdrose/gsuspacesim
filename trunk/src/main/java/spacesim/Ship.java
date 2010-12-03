package spacesim;

public class Ship extends Rocket {
	public int missiles;
	public boolean fireMissile;
	public Ship(double x, double y, double angle) {
		super(x, y, angle);
		missiles=1;
		fireMissile=false;
		accel=.0001;
	}
	
	//getters, setters required by drools
	public boolean getFireMissile(){return fireMissile;}
	public void setFireMissile(boolean fm){fireMissile=fm;}
}