package DiGraph_A5;

public class Edge {
	
	public long idNum;
	public String label;
	public long weight;
	public Vertex source;
	public Vertex dest;
	
	public Edge (long idNum, String label, long weight, Vertex source, Vertex dest) {
		
		this.idNum = idNum;
		this.label = label;
		this.weight = weight;
		this.source = source;
		this.dest = dest;
		
	}
}
