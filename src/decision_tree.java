import java.io.File;
import java.util.*;
import java.io.*;

public class decision_tree 
{
	
	static int width = 5;
	static int height = 14;
	static int[][][] CountAtt;
	static int[] CountClass;
	static double[][] H;
	static double[] WeightedAverageH;
	static int Old;
	static int Id = 0;
	static double old;
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
			
			if(ContainAlpha(SaveAttributeTmp.get(NumAttval-1)))//show Attribute is categorical(true) or numerical(false)
			{}
			else
			{
				Collections.sort(SaveAttributeTmp);
				for(j = 0;j<NumAttval;j++)
				{
					System.out.println(" test:" + SaveAttributeTmp.get(j));
				}
			}
			NumAtt++;
			//System.out.println(" NumAttVal " + NumAttval);
			MaxNumAttVal = comp_GetMax(NumAttval);
			//System.out.println(" Temp MaxNumAttVal " + MaxNumAttVal);
			
			if(i<width-1)
				SaveAttribute.add(SaveAttributeTmp);
			else
			{
				SaveClass.add(SaveAttributeTmp);
				
				//System.out.println(" test:" + SaveClass.get(0));
			}
			
			
			
			//SaveAttributeTmp.clear();
		}
		CountAtt = new int[NumAtt][MaxNumAttVal][SaveClass.get(0).size()];
		CountClass = new int[SaveClass.get(0).size()];
		
		count_Att(NumAtt, MaxNumAttVal, save, SaveAttribute, SaveClass);
		
		//CalculateEntropy();
		//System.out.println(" 0:" + SaveAttribute.get(0));
		//System.out.println(" have sunny?:" + SaveAttribute.get(0).contains("sunny"));
		////////count = new double[NumAtt][MaxNumAttVal];
		System.out.println();
		//System.out.println(" test:" + SaveAttribute.get(3));
		//System.out.println("NumAtt "+NumAtt + " MaxNumAttVal " + MaxNumAttVal);
	}
	static double CalculateEntropy()
	{
		
		
		return 0.0;
	}
	static void count_Att(int NumAtt, int MaxNumAttVal, String[][] save, ArrayList<ArrayList<String>> SaveAttribute, ArrayList<ArrayList<String>> SaveClass)
	{
		for(int i = 0; i<width-1;i++)
		{
			for(int j = 0; j<height;j++)
			{
				
					for(int k = 0; k<SaveAttribute.get(i).size();k++)
					{
						
						if(save[j][i].equals(SaveAttribute.get(i).get(k)))
						{
							//System.out.println(save[j][i].equals(SaveAttribute.get(i).get(k)));
							System.out.println("save: "+save[j][i] + " saveAttribute.get("+i+").get("+k+ ") : "+SaveAttribute.get(i).get(k));
							for(int ClassIndex = 0;ClassIndex<SaveClass.get(0).size();ClassIndex++)
							{
								//System.out.println("save: "+save[j][i] + " saveclass : "+SaveClass.get(0).get(ClassIndex));
								if(save[j][width-1].equals(SaveClass.get(0).get(ClassIndex)))
								{
									CountAtt[i][k][ClassIndex]++;
									if(i == 0)
										CountClass[ClassIndex]++;
									System.out.println("CountAtt["+i+"]["+k+"]["+ClassIndex+"]"+"++");
								}
								
							}
						}
						
						
					}
				
			}
		}
		int num = 0;
		int num4weight = 0;
		H = new double[NumAtt][MaxNumAttVal];
		WeightedAverageH = new double[NumAtt];
		for(int i = 0; i<width-1;i++)
		{
			if(ContainAlpha(SaveAttribute.get(i).get(0)))
			{
				for(int j = 0; j<SaveAttribute.get(i).size();j++)
				{
					num = 0;
					for(int n = 0; n<SaveClass.get(0).size();n++)
					{
						System.out.println("saveAttribute" + SaveAttribute.get(i).get(j)+" "+SaveClass.get(0).get(n) +" CountAtt["+i+"]["+j+"]["+n+"] "+CountAtt[i][j][n]);
						num += CountAtt[i][j][n];
						
					}
					
					
						for(int n = 0; n<SaveClass.get(0).size();n++)
						{
							if((double)CountAtt[i][j][n]/num == 0)
							{
								H[i][j] = 0;
								break;
							}
							H[i][j] += -(double)CountAtt[i][j][n]/num*CalLog2((double)CountAtt[i][j][n]/num);
						}
						//System.out.println("H "+H[i][j]);
						WeightedAverageH[i] += (double)H[i][j]*num;
						num4weight += num;
					
					
				}
				WeightedAverageH[i] = (double)WeightedAverageH[i]/num4weight;
				System.out.println("H " + WeightedAverageH[i]);
			}
			else
			{
				old = 1;//cause it's min..... not 0
				double Min = 0;
				for(int x = 0; x<SaveAttribute.get(i).size();x++)
				{
					for(int n = 0; n<SaveClass.get(0).size();n++)
					{
						System.out.println("saveAttribute" + SaveAttribute.get(i).get(x)+" "+SaveClass.get(0).get(n) +" CountAtt["+i+"]["+x+"]["+n+"] "+CountAtt[i][x][n]);
						
					}
					int[] CountAttBelow = new int [SaveClass.get(0).size()];
					int[] CountAttAbove = new int [SaveClass.get(0).size()];
					double H_T1 = 0;
					double H_T2 = 0;
					double H_T = 0;
						int num1 = 0;
						int num2 = 0;
						
						for(int y1 = 0; y1<=x;y1++)
						{
							for(int n = 0; n<SaveClass.get(0).size();n++)
							{
								CountAttBelow[n] += CountAtt[i][y1][n];
								num1 += CountAttBelow[n];
							}
						}
						
						
						for(int y2 = x; y2<SaveAttribute.get(i).size();y2++)
						{
							for(int n = 0; n<SaveClass.get(0).size();n++)
							{
								CountAttAbove[n] += CountAtt[i][y2][n];
								num2 += CountAttAbove[n];
							}
						}
						
						for(int n = 0; n<SaveClass.get(0).size();n++)
						{
							System.out.println("below "+CountAttBelow[n] + " " + num1);
							System.out.println("above "+CountAttAbove[n] + " " + num2);
							
							H_T1 += -(double)CountAttBelow[n]/num1*CalLog2((double)CountAttBelow[n]/num1);
							H_T2 += -(double)CountAttAbove[n]/num2*CalLog2((double)CountAttAbove[n]/num2);
							if(CountAttBelow[n] == 0)
							{
								H_T1 = 0;
							}
							if(CountAttAbove[n] == 0)
							{
								H_T2 = 0;
							}
						}
						//System.out.println("H_temp1 2 :" + H_T1 + " "+H_T2);
						H_T = (double)num1/(num1+num2)*H_T1 + (double)num2/(num1+num2)*H_T2; 
						//System.out.println("H_temp" + H_T);
						Min = comp(H_T,x);
						
				}
				WeightedAverageH[i] = Min;
				System.out.println("H " + WeightedAverageH[i] + " MIn " + Min);
			}
			
		}
		double MinWeightedAverageH = 0;
		for(int i = 0; i<width-1;i++)
		{
			if(i == 0)
				old = WeightedAverageH[i];
			MinWeightedAverageH = comp(WeightedAverageH[i],i);
		}
		//System.out.println(CountClass[0] + " " + CountClass[1]);
		System.out.println("MinWeightedAverageH : "+SaveAttribute.get(Id) + " " + MinWeightedAverageH);
	}
	static double CalLog2(double a)
	{
		return Math.log(a)/Math.log(2);
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
	
	static double comp(double New, int id)
	{
		//System.out.println("Old New" + Old + " " + New);
		if (old>=New)
		{
			old = New;
			Id = id;
			return New;
			
			//System.out.println(winner);
		}
		else if (old<New)
		{
			return old;
		}
		return 0;
		
	}
	//SaveAttribute.
	

}
