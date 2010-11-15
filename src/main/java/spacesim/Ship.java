package spacesim;

public class Ship {
	public boolean forwardThrust, rotateCWThrust, rotateCCWThrust;
	public int dx, dy, x, y;
	public double angle;
	
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
			dx+=Math.cos(Math.toRadians(angle));
			dy+=Math.sin(Math.toRadians(angle));
		}
		x+=dx;
		y+=dy;
	}
	
	public double scaleX() {
		return ((double)x/100000.0);
	}
	public int scaleX(int width){
		return (int)(scaleX()*(double)width-25);
	}
	public double scaleY() {
		return ((double)(100000.0-y)/100000.0);
	}
	public int scaleY(int height){
		return (int)(scaleY()*(double)height-25);
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
