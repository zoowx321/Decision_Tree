import java.io.File;
import java.util.*;
import java.io.*;

public class decision_tree 
{
	
	static int width = 5;
	static int height = 14;
	static double[][] count;
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
		HashMap<Integer,ArrayList<String>> SaveAttribute = new HashMap<Integer,ArrayList<String>>();
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
			NumAtt++;
			//System.out.println(" NumAttVal " + NumAttval);
			MaxNumAttVal = comp_GetMax(NumAttval);
			//System.out.println(" Temp MaxNumAttVal " + MaxNumAttVal);
			SaveAttribute.put(i,SaveAttributeTmp);
			
		}	
		count = new double[NumAtt][MaxNumAttVal];
		//System.out.println("NumAtt "+NumAtt + " MaxNumAttVal " + MaxNumAttVal);
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
	/*static void count_Att(String save, ArrayList<String> SaveAttributeTmp)
	{
		if(save[i][0].equals("Sunny"))
			count[0][0]++;
		else if(save[i][0].equals("Overcast"))
			count[0][1]++;
		else if(save[i][0].equals("Rain"))
			count[0][2]++;
		
		if(save[i][1].equals("Hot"))
			count[1][0]++;
				else if(save[i][1].equals("Mild"))
					count[1][1]++;
						else if(save[i][1].equals("Cool"))
							count[1][2]++;
								
		
		if(save[i][2].equals("High"))
			count[2][0]++;
				else if(save[i][2].equals("Normal"))
					count[2][1]++;
						
		
		if(save[i][3].equals("Weak"))
			count[3][0]++;
				else if(save[i][3].equals("Strong"))
					count[3][1]++;
						
		c++;

	}*/

}
