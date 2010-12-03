package spacesim;

public abstract class Rocket {
	public boolean forwardThrust, rotateCWThrust, rotateCCWThrust, alive;
	//TODO: These variable names suck
	public double angle,  	//angle in degrees 
					dx,   	//change in x
					dy, 	//change in y
					x, 		//x coordinate
					y, 		//y coordinate
					ed, 	//enemy ship distance
					ea, 	//enemy ship relative angle
					ema, 	//enemy missile relative angle
					emd, 	//enemy missile distance
					eda, 	//enemy ship relative angle in difference of velocity
					emda,   //enemy missile relative angle in difference of velocity
					accel;  //acceleration per tenth of a second
	
	public static double mass=0;
	
	public Rocket enemyMissile, enemyShip;
	
	public Rocket(double x, double y, double angle){
		forwardThrust=false;
		dx=0;
		dy=0;
		this.x=x;
		this.y=y;
		this.angle=angle;
		this.alive=true;
	}
		
	public double relativeAngle(Rocket a, Rocket b) {
		
		return 0;
	}
	
	public void move() {
		if(!alive) {
			return;
		}
		
		if(forwardThrust){
			dx+=Math.cos(Math.toRadians(angle))*accel;
			dy+=Math.sin(Math.toRadians(angle))*accel;
		}
		x+=dx;
		y+=dy;
		
		if(enemyShip!=null){
			ed=Util.distance(x, y, enemyShip.x, enemyShip.y);
			ea=Util.angle(x, y, enemyShip.x, enemyShip.y);
			eda=Util.angle(dx, dy, enemyShip.dx, enemyShip.dy);
		}
		if(enemyMissile!=null){
			emd=Util.distance(x, y, enemyMissile.x, enemyMissile.y);
			ema=Util.angle(x, y, enemyMissile.x, enemyMissile.y);
			emda=Util.angle(dx, dy, enemyMissile.dx, enemyMissile.dy);
		}
	}
	
	//getters, setters required by drools
	public void setForwardThrust(boolean t) {
		forwardThrust=t;
	}
	
	public boolean getForwardThrust() {
		return forwardThrust;
	}
	
	public void setAngle(double a) {
		angle=a;
	}
	
	public double getAngle() {
		return angle;
	}
	
	//read only
	public boolean getAlive() {
		return alive;
	}
	
	public double getY() {
		return y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getDx() {
		return dx;
	}
	
	public double getDy() {
		return dy;
	}
	
	public double getEd() {
		return ed;
	}
	
	public double getEa() {
		return ea;
	}
	
	public double getEda() {
		return eda;
	}
	
	public double getEmda() {
		return emda;
	}
	
	public Rocket getEnemyShip() {
		return enemyShip;
	}
	
	public Rocket getEnemyMissile() {
		return enemyMissile;
	}
}