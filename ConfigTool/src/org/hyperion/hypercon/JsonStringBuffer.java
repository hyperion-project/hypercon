package org.hyperion.hypercon;

public class JsonStringBuffer {

	private final StringBuffer mStrBuf = new StringBuffer();
	
	private final int mStartIndentLevel;
	private int mIndentLevel = 0;
	
	/** Flag indicating that the parts written are 'commented-out' */
	private boolean mComment = false;
	
	public JsonStringBuffer() {
		this(0);

		mStrBuf.append("{\n"); 
		++mIndentLevel;
	}
	
	public JsonStringBuffer(int pIndentLevel) {
		mStartIndentLevel = pIndentLevel;
		mIndentLevel      = pIndentLevel;
	}

	public void newLine() {
		mStrBuf.append('\n');
	}
	
	public void finish() {
		
		for (int i=0; i<mIndentLevel; ++i) {
			mStrBuf.append('\t');
		}
		mStrBuf.append("\"end-of-json\" : \"end-of-json\"\n"); 
		
		--mIndentLevel;
		if (mIndentLevel != mStartIndentLevel) {
			System.err.println("Json write closed in incorrect state!"); 
		}
		for (int i=0; i<mIndentLevel; ++i) {
			mStrBuf.append('\t');
		}
		mStrBuf.append("}\n"); 
	}
	
	public void writeComment(String pComment) {
		String[] commentLines = pComment.split("\\r?\\n"); 

		for (String commentLine : commentLines) {
			for (int i=0; i<mIndentLevel; ++i) {
				mStrBuf.append('\t');
			}
			mStrBuf.append("// ").append(commentLine).append('\n'); 
		}
	}
	
	public void toggleComment(boolean b) {
		mComment = b;
	}
	
	private void startLine() {
		if (mComment) mStrBuf.append("// "); 
		for (int i=0; i<mIndentLevel; ++i) {
			mStrBuf.append('\t');
		}
	}
	
	public void startObject(String pKey) {
		if (!pKey.isEmpty()) {
			startLine();
			mStrBuf.append('"').append(pKey).append('"').append(" : \n"); 
		}
		startLine();
		mStrBuf.append("{\n"); 
		
		++mIndentLevel;
	}

	/**
	 * @param endOfSection
	 */
	public void stopObject(boolean endOfSection) {
		--mIndentLevel;

		startLine();
		if (endOfSection) {
			mStrBuf.append("}\n"); 
		} else {
			mStrBuf.append("},\n"); 
		}
		
	}

	/**
	 *
	 */
	public void stopObject() {
		--mIndentLevel;

		startLine();
		mStrBuf.append("},\n"); 
	}

	/**
	 * @param pKey
	 */
	public void startArray(String pKey) {
		startLine();
		mStrBuf.append('"').append(pKey).append('"').append(" : \n"); 
		startLine();
		mStrBuf.append("[\n"); 
		
		++mIndentLevel;
	}

	/**
	 * @param lastValue
	 */
	public void stopArray(boolean lastValue) {
		--mIndentLevel;

		startLine();
		if (lastValue) {
			mStrBuf.append("]\n"); 
		} else {
			mStrBuf.append("],\n"); 
		}
	}


	/**
	 * @param pKey
	 * @param pValue
	 * @param lastValue
	 */
	public void addRawValue(String pKey, String pValue, boolean lastValue) {
		startLine();
		mStrBuf.append('"').append(pKey).append('"').append(" : ").append(pValue); 
		if (lastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 
		}
	}

	public void addPathValue(String pValue, boolean lastValue) {
		startLine();
		mStrBuf.append('"').append(pValue).append('"'); 
		if (lastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 	
		}
	}
	/**
	 *
	 * @param pKey
	 * @param pValue
	 * @param lastValue
	 */
	public void addValue(String pKey, String pValue, boolean lastValue) {
		startLine();
		mStrBuf.append('"').append(pKey).append('"').append(" : ").append('"').append(pValue).append('"'); 
		if (lastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 
		}
	}
	/**
	 *
	 * @param pKey
	 * @param pValue
	 * @param pValue1
	 * @param pValue2
	 * @param lastValue
	 */
	public void addArrayRGB(String pKey, int pValue, int pValue1, int pValue2, boolean lastValue) {
		startLine();
		mStrBuf.append('"').append(pKey).append('"').append(" : ").append('[').append(pValue).append(',').append(pValue1).append(',').append(pValue2).append(']'); 
		if (lastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 
		}
	}
	/**
	 *
	 * @param pKey
	 * @param pValue
	 * @param lastValue
	 */
	public void addArray(String pKey, String pValue, boolean lastValue) {
		startLine();
		mStrBuf.append('"').append(pKey).append('"').append(" : ").append('[').append(pValue).append(']'); 
		if (lastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 
		}
	}
	/**
	 *
	 * @param pKey
	 * @param pValue
	 * @param lastValue
	 */
	public void addValue(String pKey, double pValue, boolean lastValue) {
		startLine();
		mStrBuf.append('"').append(pKey).append('"').append(" : ").append(pValue); 
		if (lastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 
		}
	}

	/**
	 * @param pKey
	 * @param pValue
	 * @param lastValue
	 */
	public void addValue(String pKey, int pValue, boolean lastValue) {
		startLine();
		mStrBuf.append('"').append(pKey).append('"').append(" : ").append(pValue); 
		if (lastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 
		}
	}

	/**
	 * @param pKey
	 * @param pValue
	 * @param lastValue
	 */
	public void addValue(String pKey, boolean pValue, boolean lastValue) {
		startLine();
		mStrBuf.append('"').append(pKey).append('"').append(" : ").append(pValue); 
		if (lastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 
		}
	}
	
	/**
	 * Adds an array element to an opened array. 
	 * 
	 * @param pValue The value of the element
	 * @param pLastValue Indicates that it is the last element in the array
	 */
	public void addArrayElement(String pValue, boolean pLastValue) {
		startLine();
		mStrBuf.append('"').append(pValue).append('"');
		if (pLastValue) {
			mStrBuf.append("\n"); 
		} else {
			mStrBuf.append(",\n"); 
		}
	}
	
	@Override
	public String toString() {
		return mStrBuf.toString();
	}	
}
