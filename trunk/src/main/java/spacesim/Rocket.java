package spacesim;

public abstract class Rocket {
	public boolean forwardThrust, rotateCWThrust, rotateCCWThrust;
	public double angle, dx, dy, x, y, ed, ea, ema, emd;
	
	public static final double accel=.0001;
	
	public static double mass=0;
	
	public Rocket enemyMissile, enemyShip;
	
	public Rocket(double x, double y, double angle){
		forwardThrust=false;
		dx=0;
		dy=0;
		this.x=x;
		this.y=y;
		this.angle=angle;
	}
		
	public double relativeAngle(Rocket a, Rocket b) {
		
		return 0;
	}
	
	public void move() {
		if(forwardThrust){
			dx+=Math.cos(Math.toRadians(angle))*accel;
			dy+=Math.sin(Math.toRadians(angle))*accel;
		}
		x+=dx;
		y+=dy;
		
		if(enemyShip!=null){
			ed=Util.distance(x, y, enemyShip.x, enemyShip.y);
			ea=relativeAngle(this, enemyShip);
		}
		if(enemyMissile!=null){
			emd=Util.distance(x, y, enemyMissile.x, enemyMissile.y);
			ema=relativeAngle(this, enemyMissile);
		}
	}
	
	//getters, setters required by drools
	public void setForwardThrust(boolean t){forwardThrust=t;}
	public boolean getForwardThrust(){return forwardThrust;}
	public void setAngle(double a){angle=a;}
	public double getAngle(){return angle;}
	
	//read only
	public double getDx(){return dx;}
	public double getDy(){return dy;}
	public double getEd(){return ed;}
}