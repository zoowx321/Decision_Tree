package hw9;

import java.io.File;

import java.util.*;

import java.io.*;



public class decision_tree 

{

	

	static int width = 5;

	static int height = 14;
	
	static String[][] save = new String[height][width];

	static int[][][] CountAtt;

	static int[] CountClass;

	static double[][] H;

	static double[] WeightedAverageH;

	static int Old;

	static int Id = 0;

	static double old;

	static TreeNode root = new TreeNode();

	static DataInfo info;
	public static void main(String[] args) throws Exception

	{

		GetData();
		//System.out.println(calculation(info.NumAtt, info.MaxNumAttVal, info.save, info.SaveAttribute, info.SaveClass));
		root.decompositionAttribute = calculation(info.NumAtt, info.MaxNumAttVal, info.save, info.SaveAttribute, info.SaveClass);
		root.Istravel = true;
		//System.out.println(root.decompositionAttribute.size());
		Decompose(root);
	}
	static int count = 0;
	public static void Decompose(TreeNode node) throws Exception
	{
		//System.out.println("fadsf"+root.remainAttribute);
		System.out.println("count : "+ count);
		if(count>0)
		{
			GetDataChild(node);
			node.decompositionAttribute = calculation(info.NumAtt, info.MaxNumAttVal, info.save, info.SaveAttribute, info.SaveClass);
			if(info.entropy>0.7)
				return;
			node.Istravel = true;
			System.out.println("test");
		}
		node.children = new TreeNode[node.decompositionAttribute.size()];
		
		ArrayList<ArrayList<String>> tmp = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmpIn = new ArrayList<String>();
		tmpIn.add("");
		tmp = node.remainAttribute;
		tmp.set(Id,tmpIn);
		
		System.out.println("children data:");
		for(int i = 0; i<node.decompositionAttribute.size();i++)
		{
			node.children[i] = new TreeNode();
			node.children[i].parent = node;
			node.children[i].data.addAll(MatchString(save, node.decompositionAttribute.get(i),Id));
			node.children[i].decompositionValue = node.decompositionAttribute.get(i);
			
			node.children[i].remainAttribute = tmp;
			node.children[i].remainClass = node.remainClass;
			System.out.println(node.children[i].data);
		}
		System.out.println(node.remainAttribute);
		System.out.println(node.children[0].remainAttribute);
		System.out.println();
		/*for(int i = 0; i<node.decompositionAttribute.size();i++)
		{
			Decompose(node.children[i]);
			count++;
		}*/
		if(count < 2)
		{
			count++;
			Decompose(node.children[0]);
			
			//System.out.println("test" + count);
		}
		
	}
	public static ArrayList<ArrayList<String>> MatchString(String[][] input, String matchingTarget,int Att)
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp;

		for(int i = 0; i<input.length;i++)
		{
			tmp = new ArrayList<String>();
			if(input[i][Att].equals(matchingTarget))
			{
				for(int j = 0; j<input[i].length;j++)
				{
					tmp.add(input[i][j]); 
				}

				//System.out.println("tmp : "+tmp);
				output.add(tmp);
			}
			
			
		}
		//System.out.println("output: "+ output);
		return output;
	}
	public static void GetDataChild(TreeNode node) throws Exception
	{
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		input = node.data;
		save = new String[input.size()][width];
		int j = 0;

		int i = 0;

		ArrayList<ArrayList<String>> SaveAttribute = new ArrayList<ArrayList<String>>();
		SaveAttribute = node.remainAttribute;
		ArrayList<ArrayList<String>> SaveClass = new ArrayList<ArrayList<String>>();
		SaveClass = node.remainClass;

		
		for(i = 0; i<input.size();i++)

		{

			for(j = 0;j<width;j++)

			{

				save[i][j] = input.get(i).get(j);

			}

			//System.out.println();

		}



		Old = 0;

		int NumAtt = SaveAttribute.size();

		int MaxNumAttVal = 0;
		for(i = 0; i<SaveAttribute.size();i++)
		{
			MaxNumAttVal = comp_GetMax(SaveAttribute.get(i).size());
		}
		

		CountAtt = new int[NumAtt][MaxNumAttVal][SaveClass.get(0).size()];

		CountClass = new int[SaveClass.get(0).size()];

		info = new DataInfo();
		info.NumAtt = NumAtt;
		info.MaxNumAttVal = MaxNumAttVal;
		info.save = save;
		info.SaveAttribute = SaveAttribute;
		info.SaveClass = SaveClass;
		//delete    System.out.println(calculation(info.NumAtt, info.MaxNumAttVal, info.save, info.SaveAttribute, info.SaveClass));

		

		//CalculateEntropy();

		//System.out.println(" 0:" + SaveAttribute.get(0));

		//System.out.println(" have sunny?:" + SaveAttribute.get(0).contains("sunny"));

		////////count = new double[NumAtt][MaxNumAttVal];

		System.out.println();

		//System.out.println(" test:" + SaveAttribute.get(3));

		//System.out.println("NumAtt "+NumAtt + " MaxNumAttVal " + MaxNumAttVal);
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

		//String[][] save = new String[height][width];

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
		root.remainAttribute = new ArrayList<ArrayList<String>>();
		root.remainAttribute.addAll(SaveAttribute);
		root.remainClass = new ArrayList<ArrayList<String>>();
		root.remainClass.addAll(SaveClass);
		CountAtt = new int[NumAtt][MaxNumAttVal][SaveClass.get(0).size()];

		CountClass = new int[SaveClass.get(0).size()];

		info = new DataInfo();
		info.NumAtt = NumAtt;
		info.MaxNumAttVal = MaxNumAttVal;
		info.save = save;
		info.SaveAttribute = SaveAttribute;
		info.SaveClass = SaveClass;
		//delete    System.out.println(calculation(info.NumAtt, info.MaxNumAttVal, info.save, info.SaveAttribute, info.SaveClass));

		

		//CalculateEntropy();

		//System.out.println(" 0:" + SaveAttribute.get(0));

		//System.out.println(" have sunny?:" + SaveAttribute.get(0).contains("sunny"));

		////////count = new double[NumAtt][MaxNumAttVal];

		System.out.println();

		//System.out.println(" test:" + SaveAttribute.get(3));

		//System.out.println("NumAtt "+NumAtt + " MaxNumAttVal " + MaxNumAttVal);

	}


	static ArrayList<String> calculation(int NumAtt, int MaxNumAttVal, String[][] save, ArrayList<ArrayList<String>> SaveAttribute, ArrayList<ArrayList<String>> SaveClass)

	{

		for(int i = 0; i<width-1;i++)

		{

			for(int j = 0; j<save.length;j++)

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
			num4weight = 0;
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
							//System.out.println("Hij : " + H[i][j]);
						}

						//System.out.println("H "+H[i][j]);

						WeightedAverageH[i] += (double)H[i][j]*num;
						//System.out.println("num : " + num + " H : "+H[i][j]);
						num4weight += num;
						//System.out.println("num4weight "+num4weight);
					

					

				}

				WeightedAverageH[i] = (double)WeightedAverageH[i]/num4weight;
				//System.out.println("num4weight "+num4weight);
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

								
							}
							
						}

						//System.out.println("num1 "+num1);

						

						for(int y2 = x+1; y2<SaveAttribute.get(i).size();y2++)

						{

							for(int n = 0; n<SaveClass.get(0).size();n++)

							{

								CountAttAbove[n] += CountAtt[i][y2][n];
							
								
							}
							
						}

						for(int n = 0; n<SaveClass.get(0).size();n++)

						{
							num1 += CountAttBelow[n];							
							num2 += CountAttAbove[n];

						}
						
						//System.out.println("num1 "+num1);
						//System.out.println("num2 "+num2);
						for(int n = 0; n<SaveClass.get(0).size();n++)

						{
							
							
							//System.out.println("below "+CountAttBelow[n] + " " + num1);

							//System.out.println("above "+CountAttAbove[n] + " " + num2);

							

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
		DataInfo.entropy = MinWeightedAverageH;
		return SaveAttribute.get(Id);
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
