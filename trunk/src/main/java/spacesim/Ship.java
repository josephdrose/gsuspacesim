package spacesim;

public class Ship {
	public boolean forwardThrust, rotateCWThrust, rotateCCWThrust;
	public int dx, dy, x, y;
	public float angle;
	
	public Ship(){
		forwardThrust=false;
		rotateCWThrust=false;
		rotateCCWThrust=false;
		dx=0;
		dy=0;
		x=0;
		y=0;
		angle=0;
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
