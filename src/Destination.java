public class Destination {
	private String name;
	private int mapX;
	private int mapY;
	private int tickX;
	private int tickY;

	public Destination(String str, int mapX, int mapY, int tickX, int tickY){
		name = str;
		this.mapX = mapX;
		this.mapY = mapY;
		this.tickX = tickX;
		this.tickY = tickY;
	}

	public int getMapX(){
		return mapX;
	}

	public int getMapY(){
		return mapY;
	}

	public int getTickX(){
		return tickX;
	}

	public int getTickY(){
		return tickY;
	}

	public String getName(){
		return name;
	}

	public int compareTo(Object o){
		Destination other = (Destination) o;
		return mapX + (int)(mapY * 1.5) - other.getMapX() - (int)(1.5 * other.getMapY());
	}

	public boolean equals(Object o){
		Destination other = (Destination) o;
		return name.equals(other.name);
	}
}
