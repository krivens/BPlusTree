package treesearch.test;

import treesearch.impl.BPlusTree;
import treesearch.impl.TreePair;
import treesearch.io.InputParser;
import treesearch.io.OpObject;
import treesearch.io.OutputWriter;

public class TreeTestFile {

	public static void main(String[] args) {
		InputParser ip = new InputParser(args[0]);
		int m = ip.getM();
		BPlusTree bpTree = new BPlusTree(m);
		OpObject opObj;
		OutputWriter outputWriter = new OutputWriter();
		while((opObj = ip.getNextOpObject()) != null) {
			switch(opObj.getOperation()) {
			case INSERT:
				bpTree.insert(new TreePair(opObj.getKey(), opObj.getValue()));
				break;
			case SEARCH:
				outputWriter.writeSearch(bpTree.search(opObj.getKey()));
				break;
			case SEARCHRANGE:
				outputWriter.writeRange(bpTree.search(opObj.getKey(), opObj.getKey2()));
				break;
			}
		}
		outputWriter.close();
	}

}
