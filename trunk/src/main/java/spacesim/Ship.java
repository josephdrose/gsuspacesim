package spacesim;

public class Ship extends Rocket {
	public static double mass=1000;
	public int missiles;
	public boolean fireMissile;
	public Ship(double x, double y, double angle) {
		super(x, y, angle);
		missiles=1;
		fireMissile=false;
	}
	public boolean getFireMissile(){return fireMissile;}
	public void setFireMissile(boolean fm){fireMissile=fm;}
}