import java.util.ArrayList;

public class DestinationList {
	private ArrayList<Destination> list;
	private static final int MINIMUM_DISTANCE = 180;

	public DestinationList(ArrayList<Destination> dests){
		list = dests;
	}

	public Route generateRoute(){
		Destination dest1 = list.get((int)(Math.random() * list.size()));
		Destination dest2 = list.get((int)(Math.random() * list.size()));
		if(dest1.compareTo(dest2) > 0){
			Destination temp = dest1;
			dest1 = dest2;
			dest2 = temp;
		}
		Route route = new Route(dest1, dest2);
		if(route.getDistance() <= MINIMUM_DISTANCE)
			return generateRoute();
		else return route;
	}
}
