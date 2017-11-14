package treesearch.impl;

import java.util.List;
import java.util.ArrayList;

/**
 * The class containing the BPlus Tree implementation.
 * @author kesha
 *
 */
public class BPlusTree {
	//private int order;
	private int maxKeys;
	private int splitIndex;
	private Node root = null;
	
	
	public BPlusTree(int order) { //throws Exception {
		/*if(order == 1 || order == 2) {
			throw new Exception("A B+ tree cannot have order 1 or 2");
		}*/
		//this.order = order;
		this.maxKeys = order - 1;
		splitIndex = (int)Math.ceil((order-1)/2); //conversion loss is technically possible, but it's not expected to have such a huge m value!
	}
	
	public boolean insert(TreePair pair) {
		double key = pair.getKey();
		Object value = pair.getValue();
		boolean rootChanged = false;
		if(root == null) {
			root = new LeafNode(new ArrayList<Double>(), new ArrayList<List<Object>>());
			rootChanged = true;
		}
		//stack to track traversal down and up the tree
		List<Node> nodeStack = new ArrayList<Node>();
		Node currentNode = root;
		//nodeStack.add(currentNode);
		//first, fall off at the Leaf node that should contain the key
		while(!(currentNode instanceof LeafNode)) {
			nodeStack.add(currentNode);
			currentNode = currentNode.search(key);
		}
		//insert the item at the LeafNode, then keep popping upwards to add newly generated Nodes if needed
		int size = ((LeafNode)currentNode).insert(key, value);
		
		while(nodeStack.size()>0 && size>maxKeys) {
			int currentTopIndex = nodeStack.size() - 1;
			Node topOfStack = nodeStack.get(currentTopIndex);
			Object splitReturn[] = currentNode.splitNode(splitIndex);
			size = topOfStack.insert((double)splitReturn[0], (Node)splitReturn[1]);
			currentNode = topOfStack;
			nodeStack.remove(currentTopIndex);
		}
		
		//base case, we've reached the top of the tree and the last insert made the root too big. Make a new root
		if(size>maxKeys) {
			Object splitReturn[] = currentNode.splitNode(splitIndex);
			List<Double> newKeys = new ArrayList<Double>();
			newKeys.add((double)splitReturn[0]);
			List<Node> newNodes = new ArrayList<Node>();
			newNodes.add(root); //the current root should be the left child of the new root
			newNodes.add((Node)splitReturn[1]);
			Node newRoot = new Node(newKeys, newNodes);
			root = newRoot;
			rootChanged = true;
		}
		return rootChanged;
	}
	
	public List<Object> search(double key) {
		Node currentNode = root;
		if(currentNode == null) {
			return null;
		}
		//First find the leaf node at which the key should be stored
		while(!(currentNode instanceof LeafNode)) {
			currentNode = currentNode.search(key);
		}
		//once the leaf node is found, search it for the key
		int searchRes[] = currentNode.searchKeys(key);
		if(searchRes[0] == 0) { //the key wasn't found
			return null;
		} else { //the key was found
			return ((LeafNode)currentNode).objects.get(searchRes[1]-1);
		}
	}
	
	public List<TreePair> search(double index1, double index2) {
		Node currentNode = root;
		if(currentNode == null) return null;
		if(index1 > index2) { //swap if necessary
			double temp = index1;
			index1 = index2;
			index2 = index1;
		}
		boolean found = false;
		List<TreePair> retList = null;
		//first find the leaf node at which the key should be stored
		while(!(currentNode instanceof LeafNode)) {
			currentNode = currentNode.search(index1);
		}
		int searchRes[] = currentNode.searchKeys(index1);
		if(searchRes[0] == 1) {
			found = true;
			retList = new ArrayList<TreePair>();
			double key = currentNode.keys.get(searchRes[1]-1);
			for(Object o: ((LeafNode)currentNode).objects.get(searchRes[1]-1)) {
				retList.add(new TreePair(key, o));
			}
		}
		Object next[] = getNextKey((LeafNode)currentNode, searchRes[1]-1);
		while(next[0] != null && index2>=(double)next[2]) {
			if(retList == null) retList = new ArrayList<TreePair>();
			for(Object o: ((LeafNode)next[0]).objects.get((int)next[1])) {
				retList.add(new TreePair((double)next[2], o));
			}
			next = getNextKey((LeafNode)next[0], (int)next[1]);
		}
		return retList;
	}
	
	private Object[] getNextKey(LeafNode node, int index) {
		double key = 0;
		index++;
		if(index >= node.keys.size()) {
			node = node.rightLeaf;
			index = 0;
			if(node != null) {
				key = node.keys.get(index);
			}
		} else {
			key = node.keys.get(index);
		}
		return new Object[]{node, index, key};
	}
	
	public void helperVisualize() {
		//ArrayList<Node> stack = new ArrayList<Node>();
		if(root != null)
		doPrint(root, 0, "");
	}
	
	public void doPrint(Node node, int level, String dummy) {
		for(double i: node.keys) {
			System.out.print((int)i + ":" + level + "\t");
		}
		if(node instanceof LeafNode) {
			System.out.print("LeafNode, object size was " + ((LeafNode)node).objects.size());
		}
		System.out.println("");
		if(node.nodes != null)
		for(Node child:node.nodes) {
			doPrint(child, level+1, dummy+level);
		}
	}
}
