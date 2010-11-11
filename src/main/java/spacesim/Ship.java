package spacesim;

public class Ship {
	public boolean forwardThrust, rotateCWThrust, rotateCCWThrust;
	public int dx, dy, x, y;
	public float angle;
	
	public Ship(int x, int y, int angle){
		forwardThrust=false;
		rotateCWThrust=false;
		rotateCCWThrust=false;
		dx=0;
		dy=0;
		this.x=x;
		this.y=y;
		this.angle=angle;
	}
	
	public void move() {
		if(forwardThrust){
			//TODO: base upon angle
			dx+=1;
		}
		if(rotateCWThrust){
			angle-=1.0;
		}
		if(rotateCCWThrust){
			angle+=1.0;
		}
	
		x+=dx;
		y+=dy;
	}
	
	public double scaleX() {
		return ((double)x/100000.0);
	}
	public int scaleX(int width){
		return (int)(scaleX()*(double)width);
	}
	public double scaleY() {
		return ((double)y/100000.0);
	}
	public int scaleY(int width){
		return (int)(scaleY()*(double)width);
	}
	
	//getters, setters required by drools
	public void setdx(int s){dx=s;}
	public int getdx(){return dx;}
	public void setForwardThrust(boolean t){forwardThrust=t;}
	public boolean getForwardThrust(){return forwardThrust;}
	public void setRotateCWThrust(boolean t){rotateCWThrust=t;}
	public boolean getRotateCWThrust(){return rotateCWThrust;}
	public void setRotateCCWThrust(boolean t){rotateCCWThrust=t;}
	public boolean getRotateCCWThrust(){return rotateCCWThrust;}
}
