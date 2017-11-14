package treesearch.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import treesearch.io.OpObject.Operation;

/**
 * Convenience class for reading the input to the B+Tree implementation
 * @author Keshava
 *
 */
public class InputParser {
	protected Scanner scanner;
	protected boolean mRead = false;
	protected int mValue;
	
	public InputParser(String inputFile) {
		try {
			File ipFile = new File(inputFile);
			scanner = new Scanner(new BufferedInputStream(new FileInputStream(ipFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getM() {
		if(!mRead) {
			if(scanner.hasNextInt()) {
				mValue = scanner.nextInt();
			} else {
				//TODO add a throw here
			}
		}
		return mValue;
	}
	
	public OpObject getNextOpObject() {
		OpObject returnVal = null;
		if(scanner.hasNext()) {
			String nextString = scanner.next();
			String[] splitString = nextString.split("[(),]");
			double opValue = Double.parseDouble(splitString[1].trim());
			if("insert".equals(splitString[0].trim().toLowerCase())) {
				returnVal = new OpObject(Operation.INSERT, opValue, splitString[2]);
			} else if("search".equals(splitString[0].trim().toLowerCase())) {
				if(splitString.length == 2) {
					returnVal = new OpObject(Operation.SEARCH, opValue, null);
				} else {
					double key2 = Double.parseDouble(splitString[2]);
					returnVal = new OpObject(Operation.SEARCHRANGE, opValue, key2);
				}
			}
		}
		return returnVal;
	}

}
