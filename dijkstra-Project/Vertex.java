import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Vertex {

	String name;
	Vertex prev;
	double distance;
	String status;
	int visited;
	List<Edge> adjecentList;
	
	public Vertex(String name) {
		super();
		this.name = name;
		prev=null;
		distance=Integer.MAX_VALUE;
		status="up";
		adjecentList = new LinkedList<Edge>();
	}
	
	public void reset(){
		this.prev=null;
		this.distance=Integer.MAX_VALUE;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vertex getPrev() {
		return prev;
	}

	public void setPrev(Vertex prev) {
		this.prev = prev;
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

	public List<Edge> getAdjecentList() {
		return adjecentList;
	}

	public void setAdjecentList(List<Edge> adjecentList) {
		this.adjecentList = adjecentList;
	}

	public int getVisited() {
		return visited;
	}

	public void setVisited(int visited) {
		this.visited = visited;
	}

	
	
	
}
