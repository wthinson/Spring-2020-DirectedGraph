package DiGraph_A5;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class DiGraph implements DiGraphInterface {

	// in here go all your data and methods for the graph
	private long numOfNodes;
	private long numOfEdges;
	private HashMap<String, Vertex> labelHash;
	private HashMap<Long, Vertex> idHash;
	private HashMap<String, Edge> edgeLabelHash;
	private HashMap<Long, Edge> edgeIDHash;
	private List<Vertex> nodes;
	private int index;

	public DiGraph ( ) { // default constructor
		// explicitly include this
		// we need to have the default constructor
		// if you then write others, this one will still be there
		numOfNodes = 0;
		numOfEdges = 0;
		idHash = new HashMap<>();
		labelHash = new HashMap<>();
		edgeLabelHash = new HashMap<>();
		edgeIDHash = new HashMap<>();
		nodes = new LinkedList<>();
	}

	@Override
	public boolean addNode(long idNum, String label) {
		if (idNum < 0) {
			return false;
		}
		
		if (idHash.containsKey(idNum)) {
			return false;	
		}
		if (labelHash.containsKey(label)) {
			return false;	
		}
		
		Vertex n = new Vertex(idNum, label);
		labelHash.put(label, n);
		idHash.put(idNum, n);
		nodes.add(n);
		
		numOfNodes++;
		return true;
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		
		if (idNum < 0) {
			return false;
		}
		if (edgeIDHash.containsKey(idNum)) {
			return false; 
		}
		if (!labelHash.containsKey(sLabel) || !labelHash.containsKey(dLabel)) {
			return false;
		}
		if (labelHash.get(sLabel).outEdges.containsKey(dLabel)) {
			return false; 
		}
		
		Edge edge = new Edge(idNum, eLabel, weight, labelHash.get(sLabel), labelHash.get(dLabel));
		edgeLabelHash.put(eLabel, edge);
		edgeIDHash.put(idNum, edge);
		labelHash.get(sLabel).outEdges.put(dLabel, edge);
		labelHash.get(sLabel).edges.add(edge);
		labelHash.get(dLabel).inEdges.put(sLabel, edge);
		numOfEdges++;
		return true;
	}

	@Override
	public boolean delNode(String label) {
		
		if (!labelHash.containsKey(label)) {
			return false; 
		}
		
		Vertex dNode = labelHash.get(label);
		
		dNode.inEdges.forEach((k, v)->{
			labelHash.get(k).edges.remove(v);
			labelHash.get(k).outEdges.remove(v.dest.label);
			edgeIDHash.remove(v.idNum);
			edgeLabelHash.remove(v.label);
			numOfEdges--;
		});
		
		dNode.outEdges.forEach((k, v)->{
			labelHash.get(k).edges.remove(v);
			labelHash.get(k).inEdges.remove(v.source.label);
			edgeIDHash.remove(v.idNum);
			edgeLabelHash.remove(v.label);
			numOfEdges--;
		});
		//delete Node
		idHash.remove(dNode.idNum);
		labelHash.remove(dNode.label);
		nodes.remove(dNode);
		
		numOfNodes--;
		return true;
	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		if (!labelHash.containsKey(sLabel) || !labelHash.containsKey(dLabel)) {
			return false;
		}
		if (!labelHash.get(sLabel).outEdges.containsKey(dLabel)) {
			return false; //edge does not exist
		}
		Edge edge = labelHash.get(sLabel).outEdges.get(dLabel);
		long id = edge.idNum;
		String eLabel = edge.label;
		labelHash.get(sLabel).edges.remove(edge);
		labelHash.get(sLabel).outEdges.remove(dLabel);
		labelHash.get(dLabel).inEdges.remove(sLabel);
		edgeIDHash.remove(id);
		edgeLabelHash.remove(eLabel);
		
		numOfEdges--;
		return true;
	}

	@Override
	public long numNodes() {
		return numOfNodes;
	}

	@Override
	public long numEdges() {
		return numOfEdges;
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {

		labelHash.forEach((currString, currVertex)->{
			currVertex.distance = Integer.MAX_VALUE; // making the values infinite
			currVertex.isKnown = false;
		});

		ShortestPathInfo[] spi = new ShortestPathInfo[(int)numOfNodes];
		PriorityQueue<Vertex> pQ = new PriorityQueue<>();
		Vertex s = labelHash.get(label);
		index = 0;
		s.distance = 0;
		pQ.add(s);

		while (pQ.size() > 0) {
			Vertex n = pQ.element();
			long d = pQ.element().distance;
			
			if (index < spi.length) {
				spi[index] = new ShortestPathInfo(n.label, n.distance);
				index++;
			}
			
			pQ.remove();
			
			if (n.isKnown) {
				continue;
			} else {
				n.isKnown = true;
				n.outEdges.forEach((currString, currVertex)-> {
					Vertex a = labelHash.get(currString);

					if (!a.isKnown) {
						if (a.distance > (d + currVertex.weight)) {
							a.distance = (d + currVertex.weight);
							if (pQ.contains(a)) {
								pQ.remove(a);
							}
							pQ.add(a);
						}
					}
				});
			}
		}
		labelHash.forEach((currString, currVertex)-> {
			if (!currVertex.isKnown) {
				spi[index] = new ShortestPathInfo(currString, -1);
				index++;
			}
		});
		return spi;
	}

}