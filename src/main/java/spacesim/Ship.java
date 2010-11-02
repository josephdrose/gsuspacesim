package spacesim;

public class Ship {
	public boolean thrust;
	public int speed;
	
	public Ship(){
		thrust=false;
		speed=0;
	}
	
	public void move() {
		if(thrust){
			speed+=1;
		}
	}
	
	//getters, setters required by drools
	public void setSpeed(int s){speed=s;}
	public int getSpeed(){return speed;}
	public void setThrust(boolean t){thrust=t;}
	public boolean getThrust(){return thrust;}
}
