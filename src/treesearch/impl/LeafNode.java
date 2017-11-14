package treesearch.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Leaf Node class of the BPlus Tree. This contains data structures to hold values corresponding to keys
 * (entire pairs) as well as links to preceding and subsequent Leaf Nodes
 * @author kesha
 *
 */
public class LeafNode extends Node {
	protected List<List<Object>> objects; // since copies are allowed, have a 2-D array
	protected LeafNode leftLeaf;
	protected LeafNode rightLeaf;
	
	protected LeafNode(List<Double> keys, List<List<Object>> objects) {
		super(keys);
		this.objects = objects;
	}
	
	protected int insert(double key, Object object) {
		int searchRes[] = searchKeys(key);
		if(searchRes[0] == 0) {
			keys.add(searchRes[1], key);
			objects.add(searchRes[1], new ArrayList<Object>());
		} else {
			searchRes[1]--; //this is done because the Leaf Nodes don't have the extra first child node
		}
		List<Object> values = objects.get(searchRes[1]);
		values.add(object);
		return keys.size();
	}
	
	protected Object[] splitNode(int splitIndex) {
		//first, get the key at the index to use later
		double newKey = keys.get(splitIndex);
		
		//then, create the new LeafNode
		List<Double> newKeys = new ArrayList<Double>(keys.subList(splitIndex, keys.size()));
		List<List<Object>> newObjects = new ArrayList<List<Object>>(objects.subList(splitIndex, objects.size()));
		LeafNode newLeaf = new LeafNode(newKeys, newObjects);
		//remove everything from this node
		keys.removeAll(newKeys);
		objects.removeAll(newObjects);
		//and link the new Node to this one
		updateLinks(newLeaf);
		
		Object retObject[] = new Object[]{newKey, newLeaf};
		return retObject;
	}
	
	protected void updateLinks(LeafNode toUpdate) {
		toUpdate.leftLeaf = this;
		toUpdate.rightLeaf = this.rightLeaf;
		if(this.rightLeaf != null) {
			this.rightLeaf.leftLeaf = toUpdate;
		}
		this.rightLeaf = toUpdate;
	}
}
