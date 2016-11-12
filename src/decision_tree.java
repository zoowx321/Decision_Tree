import java.io.File;
import java.util.*;
import java.io.*;

public class decision_tree 
{
	
	static int width = 5;
	static int height = 14;
	static int[][][] CountAtt;
	static int[] CountClass;
	static int Old;
	public static void main(String[] args) throws Exception
	{
		GetData();
	}
	public static void GetData() throws Exception
	{
		String GetLine;
		int j = 0;
		int i = 0;
		
		//ArrayList<String> Attribute = new ArrayList<String>();
		//int NumOfAttribute = 5;
		ArrayList<ArrayList<String>> SaveAttribute = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> SaveClass = new ArrayList<ArrayList<String>>();
		ArrayList<String> SaveAttributeTmp = new ArrayList<String>();
		String[][] save = new String[height][width];
		//String[][] SaveAttribute = new String[NumOfAttribute][];
		Scanner sc = new Scanner(new File("weather_numeric.txt"));
		while(sc.hasNext())
		{
			
			GetLine = sc.nextLine();
			/*if (GetLine.startsWith("@"))
				continue;
			else if (GetLine.startsWith(""))
				continue;*/
			save[i] = GetLine.split(",");
			i++;
			//System.out.print("");
		}
		sc.close();
		for(i = 0; i<height;i++)
		{
			for(j = 0;j<width;j++)
			{
				System.out.print(save[i][j]);
			}
			System.out.println();
		}
		/////////save attribute///////////////
		/*
		 key1	ArrayList(Attribute 11, Attribute 12, Attribute 13...)
		 key2	ArrayList(Attribute 21, Attribute 22, ........)
		 key3
		 ...
		 */
		Old = 0;
		int NumAtt = 0;
		int MaxNumAttVal = 0;
		for(i = 0; i<width;i++)
		{
			System.out.println();
			int NumAttval = 0;
			SaveAttributeTmp = new ArrayList<String>();
			for(j = 0;j<height;j++)
			{
				if(j>0)
				{
					if(SaveAttributeTmp.contains(save[j][i]))
					{}
					else
					{
						SaveAttributeTmp.add(save[j][i]);
						System.out.print(save[j][i]+" ");
						NumAttval++;
					}
				}
				else if(j == 0)
				{
					SaveAttributeTmp.add(save[j][i]);
					System.out.print(save[j][i]+" ");
					NumAttval++;
				}
				//count_Att(save[j][i],SaveAttributeTmp);
				
			}
			
			ContainAlpha(SaveAttributeTmp.get(NumAttval-1));//show Attribute is categorical(true) or numerical(false)
			
			NumAtt++;
			//System.out.println(" NumAttVal " + NumAttval);
			MaxNumAttVal = comp_GetMax(NumAttval);
			//System.out.println(" Temp MaxNumAttVal " + MaxNumAttVal);
			/*for(j = 0;j<NumAttval;j++)
			{
				System.out.println(" test:" + SaveAttributeTmp.get(j));
			}*/
			if(i<width-1)
				SaveAttribute.add(SaveAttributeTmp);
			else
			{
				SaveClass.add(SaveAttributeTmp);
				
				//System.out.println(" test:" + SaveClass.get(0));
			}
			
			
			
			//SaveAttributeTmp.clear();
		}
		CountAtt = new int[NumAtt][MaxNumAttVal][SaveClass.size()];
		CountClass = new int[SaveClass.size()];
		count_Att(NumAtt, MaxNumAttVal, save, SaveAttribute, SaveClass);
		//System.out.println(" 0:" + SaveAttribute.get(0));
		//System.out.println(" have sunny?:" + SaveAttribute.get(0).contains("sunny"));
		////////count = new double[NumAtt][MaxNumAttVal];
		System.out.println();
		//System.out.println(" test:" + SaveAttribute.get(3));
		//System.out.println("NumAtt "+NumAtt + " MaxNumAttVal " + MaxNumAttVal);
	}
	static double CalculateEntropy()
	{
		count_Att();
		return 0.0;
	}
	static void count_Att(int NumAtt, int MaxNumAttVal, String[][] save, ArrayList<ArrayList<String>> SaveAttribute, ArrayList<ArrayList<String>> SaveClass)
	{
		for(int i = 0; i<width-1;i++)
		{
			for(int j = 0; j<height;j++)
			{
				for(int n = 0; n<SaveAttribute.size();n++)
				{
					for(int k = 0; k<SaveAttribute.get(n).size();k++)
					{
						if(save[j][i].equals(SaveAttribute.get(n).get(k)));
						{
							for(int ClassIndex = 0;ClassIndex<SaveClass.size();ClassIndex++)
								if(save[j][width-1].equals(SaveClass.get(ClassIndex)))
								{
									CountAtt[n][k][ClassIndex]++;
									System.out.println("test");
								}
						}
					}
				}
			}
		}
		
		for(int i = 0; i<NumAtt;i++)
		{
			for(int j = 0; j<MaxNumAttVal;j++)
			{
				for(int n = 0; n<SaveClass.size();n++)
					System.out.println(CountAtt[i][j][n]);
			}
		}

	}
	static boolean ContainAlpha(String OneOfAtt)
	{
		boolean atleastOneAlpha = OneOfAtt.matches(".*[a-zA-Z]+.*");
		return atleastOneAlpha;
	}
	static int comp_GetMax(int New)
	{
		//System.out.println("Old New" + Old + " " + New);
		if (Old<=New)
		{
			Old = New;
			return New;
			
			//System.out.println(winner);
		}
		else if (Old>New)
		{
			return Old;
		}
		return 0;
		
	}
	//SaveAttribute.
	

}
