package DiGraph_A5;

import java.util.ArrayList;
import java.util.Random;

public class DiGraphPlayground {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// thorough testing is your responsibility
		//
		// you may wish to create methods like 
		//    -- print
		//    -- sort
		//    -- random fill
		//    -- etc.
		// in order to convince yourself your code is producing
		// the correct behavior
		DiGraph d = new DiGraph();
		ArrayList<String> strings = new ArrayList<>();

		long start = System.currentTimeMillis();
		String name = randomString();
		Long ID = randomLong();
		d.addNode(ID, name);
		strings.add(name);

		for(int i = 0; i<2000000; i++) {
			String name2 = randomString();
			Long ID2 = randomLong();
			int weight = randomInt();
			d.addNode(ID2, name2);
			strings.add(name2);
			//String s1 = randomStringArray(strings);
			//String s2 = randomStringArray(strings);
			d.addEdge(ID, strings.get(i), strings.get(i+1), weight, null);

		}d.shortestPath(name);

		long end = System.currentTimeMillis(); 
		System.out.println("Time " + (end - start) + "ms"); 
		System.out.println("End");
		test1();
	}

	public static void exTest(){
		DiGraph d = new DiGraph();
		d.addNode(1, "f");
		d.addNode(3, "s");
		d.addNode(7, "t");
		d.addNode(0, "fo");
		d.addNode(4, "fi");
		d.addNode(6, "si");
		d.addEdge(0, "f", "s", 0, null);
		d.addEdge(1, "f", "si", 0, null);
		d.addEdge(2, "s", "t", 0, null);
		d.addEdge(3, "fo", "fi", 0, null);
		d.addEdge(4, "fi", "si", 0, null);
		System.out.println("numEdges: "+d.numEdges());
		System.out.println("numNodes: "+d.numNodes());
	}


	public static void test1(){
		DiGraph d = new DiGraph();
		d.addNode(1, "A");
		d.addNode(2, "B");
		d.addNode(3, "C");
		d.addEdge(0, "A", "B", 2, null);
		d.addEdge(1, "A", "C", 1, null);
		d.addEdge(2, "B", "C", 5, null);
		d.addEdge(3, "C", "B", 1, null);
		printGraph(d.shortestPath("A"));

	}

	public static void printGraph(ShortestPathInfo[] spi) {

		for (int i = 0; i < spi.length; i++) {
			System.out.println("Destination: " + spi[i].getDest());
			System.out.println("Weight: " + spi[i].getTotalWeight());
		}


	}


	public static String randomString() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		//strings.add(generatedString);
		return generatedString;
	}

	public static long randomLong() {
		long min = 0;
		long max = 10000000;
		long random_long = (long) (Math.random() * (max - min + 1) + min);
		return random_long;
	}
	public static int randomInt() {
		int min = 0;
		int max = 10000000;
		int random_int =  (int) (Math.random() * (max - min + 1) + min);
		return random_int;
	}
	public static String randomStringArray(ArrayList<String> a) {
		Random random = new Random();
		return a.get( random.nextInt(a.size()) );
	}

}




