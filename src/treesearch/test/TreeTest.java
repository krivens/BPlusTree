package treesearch.test;

import java.util.List;
import java.util.Scanner;

import treesearch.impl.BPlusTree;
import treesearch.impl.TreePair;

public class TreeTest {
	
	public static void main(String args[]) {
		BPlusTree bTree = new BPlusTree(3);
		double dummyArray[] = new double[]{1,4,5,6,6,4};
		for(int i=0; i<25; i++) {
			//double key = dummyArray[i];
			double key = (int)(Math.random()*100) % 15;
			//double key = i%5;
			System.out.println("Trying to insert key " + (int)key);
			bTree.insert(new TreePair(key, key));
			//bTree.helperVisualize();
		}
		bTree.helperVisualize();
		
		double key = (int)(Math.random()*100) % 15;
		System.out.println("\n Searching for key " + key);
		List<Object> searchRes = bTree.search(key);
		if(searchRes == null) {
			System.out.println("Null");
		} else {
			System.out.println("Results were:");
			for(Object o : searchRes) {
				System.out.println(o);
			}
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter low index:");
		double lowIndex = scanner.nextDouble();
		System.out.println("Enter high index:");
		double highIndex = scanner.nextDouble();
		scanner.close();
		List<TreePair> results = bTree.search(lowIndex, highIndex);
		if(results == null) {
			System.out.println("Null");
		} else {
			for(TreePair t : results) {
				System.out.print("("+ t.getKey() + ", " + t.getValue() + "),");
			}
		}
	}
	
}
