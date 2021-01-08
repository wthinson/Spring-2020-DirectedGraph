package DiGraph_A5;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Vertex implements Comparable<Vertex>{
	public long idNum;
	public String label;
	public List<Edge> edges;
	public HashMap<String, Edge> inEdges; //key= source node
	public HashMap<String, Edge> outEdges; //key= destination node
	//for dijkstra's
	public long distance;
	public boolean isKnown;

	public Vertex (long idNum, String label) {
		this.idNum = idNum;
		this.label = label;
		inEdges = new HashMap<>();
		outEdges = new HashMap<>();
		edges = new LinkedList<>();
	}

	@Override
	public int compareTo(Vertex o) {
		if (this.distance == o.distance) {
			return 0;
		} else if (this.distance < o.distance) {
			return -1;
		} else if (this.distance > o.distance) {
			return 1;
		}
		return 0;
	}
}
