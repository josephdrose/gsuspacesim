package spacesim;

public class Util {
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
	
	public static double angle(double x1, double y1, double x2, double y2) {
		return Math.toDegrees(Math.atan2((y2-y1), (x2-x1)));
	}
}