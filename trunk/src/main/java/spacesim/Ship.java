package spacesim;

public class Ship {
	public boolean forwardThrust, rotateCWThrust, rotateCCWThrust;
	public double angle, dx, dy, x, y;
	
	public static final double accel=.0001;
	
	public Ship(int x, int y, int angle){
		forwardThrust=false;
		dx=0;
		dy=0;
		this.x=x;
		this.y=y;
		this.angle=angle;
	}
	
	public void move() {
		if(forwardThrust){
			dx+=Math.cos(Math.toRadians(angle))*accel;
			dy+=Math.sin(Math.toRadians(angle))*accel;
		}
		x+=dx;
		y+=dy;
	}
	
	//getters, setters required by drools
	public void setForwardThrust(boolean t){forwardThrust=t;}
	public boolean getForwardThrust(){return forwardThrust;}
	public void setAngle(double a){angle=a;}
	public double getAngle(){return angle;}

	//read only
	public double getDx(){return dx;}
	public double getDy(){return dy;}
}
