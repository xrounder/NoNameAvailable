
public class FastVector {

	long maxLength;
	int blockSize;

	private int myArray[][];
	/**
	 * Constructor: 
	 * @param maxLength maximal VectorLength
	 * @param blockSize reserves memory in blocks of blockSize
	 */
	FastVector(long maxLength, int blockSize){
		this.maxLength = maxLength;
		this.blockSize = blockSize;
		myArray = new int[(int)(maxLength/(long)(blockSize))][];	
//		for (int t=0;t<(int)(maxLength/(long)(blockSize));t++){
//			myArray[t]=null;
//		}
	}
	/**
	 * setValue is the function to store the value in the array
	 * similar to vector[index] = value
	 * @param index
	 * @param value
	 */
	void setValue(int index, int value)
	{
		int x = index%blockSize;
		int y = index/blockSize;

		if (myArray[y] == null){
			myArray[y] = new int[blockSize];
		}
		myArray[y][x] = value;

	}
	/**
	 * getValue reads out the value from the array
	 * @param index : index of the array
	 * @return      : returns the value 
	 */
	int getValue(int index)
	{
		int x = index%blockSize;
		int y = index/blockSize;
		if (myArray[y] == null){
			return 0;
		}
		else{
			return myArray[y][x];
		}
	}
}

