import java.io.File;
import java.util.*;
import java.io.*;

public class decision_tree 
{
	public static void main(String[] args) throws Exception
	{
		GetData();
	}
	public static void GetData() throws Exception
	{
		String GetLine;
		int j = 0;
		int i = 0;
		int width = 5;
		int height = 14;
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
		for(i = 0; i<width;i++)
		{
			System.out.println();
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
					}
				}
				else if(j == 0)
				{
					SaveAttributeTmp.add(save[j][i]);
					System.out.print(save[j][i]+" ");
				}
			}
			
			SaveAttribute.put(i,SaveAttributeTmp);
		}
	}

}
