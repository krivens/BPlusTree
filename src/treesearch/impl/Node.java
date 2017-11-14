package treesearch.impl;

import java.util.List;
import java.util.ArrayList;

/**
 * Parent node class of the BPlus Tree. For convenience, this is integrated with the branch node,
 * so it contains the ability to have pointers to child nodes as well.
 * @author kesha
 *
 */
public class Node {
	protected List<Double> keys; //the keys stored at this node
	protected List<Node> nodes; //the nodes stored between keys at this node
	
	public Node(List<Double> keys) {
		this.keys = keys;// new ArrayList<Double>();
		//nodes = new ArrayList<Node>(); //unnecessary - I've combined the Branch node into this node, so if it's a LeafNode there might
		//not be children
	}
	
	protected Node(List<Double> keys, List<Node> nodes) {
		this.keys = keys;
		this.nodes = nodes;
	}
	
	protected Node search(double key) {
		//convenience method to get a node that should be at a key
		return nodes.get(searchKeys(key)[1]);
	}
	
	protected int[] searchKeys(double key) { //return the index at which the key should be, plus a boolean found if it actually is there
		//at this point, any time this is called, this node should always have keys
		//can never have a null key-set. Just returning the index is enough, don't need to find the actual key as
		//this is an internal node
		int [] returnArr = new int[]{0,0};
		for(double i:keys) {
			if(i == key) {
				returnArr[0] = 1;
				returnArr[1]++;
				break;
			}
			if(i>key){
				break;
			}
			returnArr[1]++;
		}
		return returnArr;
	}
	
	protected int insert(double key, Node node) { //can only insert one node at a time, with one key. This will never be found in a branch node
		int insertIndex = searchKeys(key)[1];
		keys.add(insertIndex, key);
		nodes.add(insertIndex+1, node); //this should never throw an IndexOutOfBoundsException, since there's always something on the LHS
		return keys.size(); //returns one less than the degree of this Node, for the implementation to decide what to do
	}
	
	protected Object[] splitNode(int splitIndex) {
		//first get values from the current node
		double parentKey = keys.get(splitIndex);
		List<Double> newKeys = new ArrayList<Double>(keys.subList(splitIndex+1, keys.size()));
		List<Node> newNodes = new ArrayList<Node>(nodes.subList(splitIndex+1, nodes.size())); // this is the same index as the keys
		//because it will be the leftmost child of the child of the new node being created/returned. Along with the fact that the children
		//are 1 more in degree than the number of keys at a node, this is correct.
		
		//create new split node
		Node newSplitNode = new Node(newKeys, newNodes);
		
		//finally, remove all the elements from the current Node
		keys.removeAll(newKeys);
		nodes.removeAll(newNodes);
		keys.remove(splitIndex);
		
		Object retObject[] = new Object[]{parentKey, newSplitNode};
		//newly returned node has child nodes at the "wrong" index, but it should be fixed as it is inserted higher up
		return retObject;
	}
}
