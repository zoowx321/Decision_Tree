package hw9;
import java.util.ArrayList;


class TreeNode 
{
	public int usedAttribute;
	public int entropy;
	boolean Istravel;
	public ArrayList<ArrayList<String>> data;
	public ArrayList<ArrayList<String>> remainAttribute;
	public ArrayList<ArrayList<String>> remainClass;
	public int Att;
	public ArrayList<String> decompositionAttribute;
	public String decompositionValue;
	
	public TreeNode[] children;
	public TreeNode parent;
	
	public TreeNode() {
		data = new ArrayList<ArrayList<String>>();
		
	}

}
