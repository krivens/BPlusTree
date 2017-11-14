package treesearch.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import treesearch.impl.TreePair;

public class OutputWriter {
	File outputFile = new File("output_file.txt");
	FileWriter fileWriter = null;
	public OutputWriter() {
		if(outputFile.exists()) {
			outputFile.delete();
		}
		try {
			outputFile.createNewFile();
			fileWriter = new FileWriter(outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeSearch(List<Object> searchRes) {
		try {
			if(searchRes == null) {
				fileWriter.write("Null\n");
			} else {
				fileWriter.write(searchRes.get(0).toString());
				int max = searchRes.size();
				for(int counter = 1;counter<max;counter++) {
				fileWriter.write("," + searchRes.get(counter));
				}
				fileWriter.write("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeRange(List<TreePair> results) {
		try {
			if(results == null) {
				System.out.println("Null");
			} else {
				TreePair t = results.get(0);
				fileWriter.write("("+ t.getKey() + ", " + t.getValue() + ")");
				int max = results.size();
				for(int counter = 1;counter < max; counter++) {
					t = results.get(counter);
					fileWriter.write(",("+ t.getKey() + ", " + t.getValue() + ")");
				}
				fileWriter.write("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
