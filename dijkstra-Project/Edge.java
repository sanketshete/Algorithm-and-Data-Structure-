import java.util.Comparator;

public class Edge implements Comparator<Edge>{
	
	Vertex destination;
	double distance;
	String status;
	
	
	public Edge(Vertex destination, double distance, String status) {
		super();
		this.destination = destination;
		this.distance = distance;
		this.status = status;
	}


	public Vertex getDestination() {
		return destination;
	}


	public void setDestination(Vertex destination) {
		this.destination = destination;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public int compare(Edge o1, Edge o2) {
		// TODO Auto-generated method stub
		return o1.getDestination().getName().compareTo(o2.getDestination().getName());
	}
	
	
	
	
}
