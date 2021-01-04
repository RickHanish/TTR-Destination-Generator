public class Route{
	private Destination dest1;
	private Destination dest2;

	public Route(Destination d1, Destination d2){
		dest1 = d1;
		dest2 = d2;
	}

	public Destination getDest1(){
		return dest1;
	}

	public Destination getDest2(){
		return dest2;
	}

	public double getDistance(){
		int xdiff = dest2.getMapX() - dest1.getMapX();
		int ydiff = dest2.getMapY() - dest1.getMapY();
		return Math.pow(Math.pow(xdiff, 2) + Math.pow(ydiff, 2), 0.5);
	}

	public String toString(){
		return (dest1.getName() + " : " + dest2.getName());
	}

	public boolean equals(Object o){
		Route other = (Route) o;
		return dest1.equals(other.dest1) && dest2.equals(other.dest2);
	}
}