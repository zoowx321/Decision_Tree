package hw9;

import java.io.File;

import java.util.*;

import java.io.*;



public class decision_tree 

{

	static int k_fold = 3;
/////////training set configuration////////////
	static int width = 5;
	static int Height = 12;
	static int Set = Height/k_fold;
	static int height = Height - Set;
	static String[][] save = new String[height][width];
///////////////////////////////////////////////
	
////////test set configuration/////////////////
	static int Theight = Set;
	static int Twidth = 5;
	static String[][] testSet = new String[Theight][Twidth] ;
///////////////////////////////////////////////
	
	static int maxClassIndex;
	
	static int[][][] CountAtt;

	static int[] CountClass;

	static double[][] H;

	static double[] WeightedAverageH;
	static double averagePrecision = 0;
	static int Old;

	static int Id = 0;

	static double old;

	static TreeNode root = new TreeNode();

	static DataInfo info;
	public static void main(String[] args) throws Exception

	{
		int first = 0;
		int last = Set;//boundary of test set
		
		//Composition(8, 12);

		for(int i = 0; i<k_fold;i++)
		{
			Composition(first, last);
			first += Set;
			last += Set; 
			//System.out.println(first + " " + last);
			save = new String[height][width];
			count = 0;
		}
		System.out.println();
		System.out.println("Average Precesion : " + averagePrecision/k_fold+"%");
	}
	public static void Composition(int first,int last) throws Exception
	{
		GetData(first,last);
		//System.out.println(calculation(info.NumAtt, info.MaxNumAttVal, info.save, info.SaveAttribute, info.SaveClass));
		root.decompositionAttribute = calculation(info.NumAtt, info.MaxNumAttVal, info.save, info.SaveAttribute, info.SaveClass);
		root.Istravel = true;
		//System.out.println(root.decompositionAttribute.size());
		
		Decompose(root);
		//String[][] testSet = {{"overcast", "" , "" ,""}};
		//System.out.println(root.children[1].children[0].data);
		int countCorrect = 0;
		double precision = 0;
		for(int i = 0; i<testSet.length;i++)
		{
			System.out.println();
			System.out.println("for testSet"+i);
			System.out.println("Matching List:");
			String answer = CheckTest(testSet[i],root);
			//System.out.println("answer : "+answer + " " + testSet[i][testSet[i].length-1]);
			if(answer.equals(testSet[i][testSet[i].length-1]))
			{
				System.out.println("correct");
				countCorrect++;
			}
			else
				System.out.println("wrong");
		}
		//System.out.println("countcorrect"+ countCorrect);
		precision = (double)countCorrect/Set*100;
		System.out.println("precision(%) : "+precision+"%");
		averagePrecision += precision;
	}
	static int savetmp = 0;
	public static String CheckTest(String[] TestSet,TreeNode node)
	{
		
		for(int x = 0; x<node.decompositionAttribute.size();x++)
		{
			
				for(int j = 0; j<TestSet.length;j++)
				{
					
					if(node.children[x].decompositionValue.equals(TestSet[j]))
					{
						//System.out.println("check" + node.children[x].Isendnode);
						savetmp = x;
						//System.out.println("print savetmp" + x);
						System.out.println(TestSet[j] + " " + node.children[x].decompositionValue);
						if(node.children[x].Isendnode)
						{
							//System.out.println("answer of Testset is : " + node.children[x].answer);
							return node.children[x].answer;
						}
						return CheckTest(TestSet, node.children[x]);
					}
					
					
				}
			
		}
		System.out.print("maybe predict ");
		return root.children[savetmp].answer;
	}
	static int count = 0;
	public static void Decompose(TreeNode node) throws Exception
	{
		node.Istravel = false;
		//System.out.println("fadsf"+root.remainAttribute);
		System.out.println("count : "+ count);
		if(count>0)
		{
			GetDataChild(node);
			node.decompositionAttribute = calculation(info.NumAtt, info.MaxNumAttVal, info.save, info.SaveAttribute, info.SaveClass);
			//System.out.println("test0158 node.decompostionAtt : " + node.decompositionAttribute);
			System.out.println("info entropy : " + info.entropy);
			
			if(info.entropy>0.7)
			{
				System.out.println("return by entropy > 0.7");
				return;
			}
			
			
			
			//System.out.println("test");
		}
		node.children = new TreeNode[node.decompositionAttribute.size()];
		
		ArrayList<ArrayList<String>> tmp = new ArrayList<ArrayList<String>>();
		
		
		tmp.addAll(node.remainAttribute);
		if(ContainAlpha(node.decompositionAttribute.get(0)))
		{
			
			ArrayList<String> tmpIn = new ArrayList<String>();
			tmpIn.add("");
			tmp.set(Id,tmpIn);
		}
		else
		{
			//System.out.println("test remain Attribute of root: " +root.remainAttribute);
			
			int delete_Id = 0;
			for(int i = 0; i<tmp.get(Id).size();i++)
			{
				//System.out.println("equal? " + tmp.get(Id).get(i) + " " + node.decompositionAttribute.get(0));
				if(tmp.get(Id).get(i).equals(node.decompositionAttribute.get(0)))
				{
					
					delete_Id = i;
				}
			}
			
			String tmpIn = "";
			tmp.get(Id).set(delete_Id, tmpIn);
			//System.out.println("test remain Attribute of tmp: " +tmp);
			//System.out.println("test remain Attribute of root: " +root.remainAttribute);
		}
		
		System.out.println("children data:");
		if(node.decompositionAttribute.get(0).equals("same value"))
		{
			node.children[0] = new TreeNode();
			node.children[0].parent = node;
			node.children[0].data.addAll(node.data);
			node.Isendnode = true;
			//System.out.println("children data size : "+ node.children[0].data);
			node.answer = node.children[0].data.get(0).get(node.children[0].data.get(0).size()-1);
			System.out.println("answer : "+node.children[0].answer);
			System.out.println(node.children[0].data + " ID : " + Id);
			System.out.println("return by same value");
			return;
		}
		//System.out.println("root.remainClass.get0 : "+root.remainClass.get(0));
		for(int i = 0; i<node.decompositionAttribute.size();i++)
		{
			node.children[i] = new TreeNode();
			node.children[i].parent = node;
			node.children[i].Isendnode = false;
			node.answer = root.remainClass.get(0).get(maxClassIndex);
			//System.out.println("answer of "+i+" : "+ node.children[i].answer);
			if(ContainAlpha(node.decompositionAttribute.get(i)))//case1: categorical attribute
			{
				node.children[i].data.addAll(MatchString(save, node.decompositionAttribute.get(i),Id));
				node.children[i].decompositionValue = node.decompositionAttribute.get(i);
			}
			else//case2: numerical attribute
			{
				//System.out.println("test 0151: "+node.decompositionAttribute.get(i));
				node.children[i].data.addAll(UpDown(save, node.decompositionAttribute.get(0),Id,i));//children0 = < , children1 = >=
				node.children[i].decompositionValue = node.decompositionAttribute.get(0);
				
				
			}
			
			node.children[i].remainAttribute = tmp;
			node.children[i].remainClass = node.remainClass;
			System.out.println("children["+i+"] "+node.children[i].data);
		}
		//System.out.println("test0201 : "+node.children[0].data);
		System.out.println("remain Attribute of root: " +root.remainAttribute);
		System.out.println("remain Attribute of children: " +node.children[0].remainAttribute);
		System.out.println();
		if(info.entropy == 0)
		{
			for(int i = 0; i<node.children.length;i++)
			{
				//System.out.println(node.children[i].data.isEmpty());
				if(node.children[i].data.isEmpty())
					continue;
				//System.out.println("i" + i);
				node.children[i].Isendnode = true;
				//System.out.println("EndNode");
				node.children[i].answer = node.children[i].data.get(0).get(node.children[i].data.get(0).size()-1);
				//System.out.println("answer : "+node.children[i].answer);
			}
			
			
			System.out.println("return by entropy == 0");
			return;
			
		}
		if (node.Istravel)
		{
			System.out.println("return by node is traveled");
			return;
		}
		node.Istravel = true;
		for(int i = 0; i<node.decompositionAttribute.size();i++)
		{
			count++;
			Decompose(node.children[i]);
			
		}
		System.out.println("Decompose End");
		/*if(count < 2)
		{
			count++;
			Decompose(node.children[0]);
			
			//System.out.println("test" + count);
		}*/
		
	}
	public static String Preict(TreeNode input, String testset)
	{
		return "";
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
	
	public static ArrayList<ArrayList<String>> UpDown(String[][] input, String matchingTarget,int Att,int num)
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp;
		
		for(int i = 0; i<input.length;i++)
		{
			tmp = new ArrayList<String>();
			if(num == 0)
			{
				if(Integer.parseInt(input[i][Att])<(Integer.parseInt(matchingTarget)))
				{
					for(int j = 0; j<input[i].length;j++)
					{
						tmp.add(input[i][j]); 
					}
	
					//System.out.println("tmp : "+tmp);
					output.add(tmp);
				}
			}
			else if(num == 1)
			{
				if(Integer.parseInt(input[i][Att])>=(Integer.parseInt(matchingTarget)))
				{
					for(int j = 0; j<input[i].length;j++)
					{
						tmp.add(input[i][j]); 
					}
	
					//System.out.println("tmp : "+tmp);
					output.add(tmp);
				}
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
	public static void GetData(int first, int last) throws Exception

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

		Scanner sc = new Scanner(new File("play-tennis_train.txt"));
		//System.out.println("size of save "+save.length);
		int count = 0;
		while(sc.hasNext())

		{

			

			GetLine = sc.nextLine();

			/*if (GetLine.startsWith("@"))

				continue;

			else if (GetLine.startsWith(""))

				continue;*/
			if(count>=first && count<last)//for k-fold validation. Make test set
			{
				//System.out.println("j" + j);
				testSet[j] = GetLine.split(",");
				j++;
			}
			else//for k-fold validation. Make training set
			{
				//System.out.println("i" + i);
				save[i] = GetLine.split(",");
				i++;
			}
			
			count++;
			//System.out.print("");

		}

		sc.close();

		for(i = 0; i<save.length;i++)

		{

			for(j = 0;j<save[i].length;j++)

			{

				System.out.print(save[i][j]);

			}

			System.out.println();

		}

		for(i = 0; i<Theight;i++)

		{

			for(j = 0;j<Twidth;j++)

			{

				System.out.print(testSet[i][j]);

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

				/*for(j = 0;j<NumAttval;j++)

				{

					System.out.println(" test:" + SaveAttributeTmp.get(j));

				}*/

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
		ArrayList<Integer> numericAttribute = new ArrayList<Integer>();
		HashMap<Integer,Integer> NumericAttribute = new HashMap<>();

		for(int i = 0; i<width-1;i++)

		{

			for(int j = 0; j<save.length;j++)

			{

				

					for(int k = 0; k<SaveAttribute.get(i).size();k++)

					{

						

						if(save[j][i].equals(SaveAttribute.get(i).get(k)))

						{
							
							//System.out.println(save[j][i].equals(SaveAttribute.get(i).get(k)));

							//System.out.println("save: "+save[j][i] + " saveAttribute.get("+i+").get("+k+ ") : "+SaveAttribute.get(i).get(k));

							for(int ClassIndex = 0;ClassIndex<SaveClass.get(0).size();ClassIndex++)

							{

								//System.out.println("save: "+save[j][i] + " saveclass : "+SaveClass.get(0).get(ClassIndex));

								if(save[j][width-1].equals(SaveClass.get(0).get(ClassIndex)))

								{

									CountAtt[i][k][ClassIndex]++;

									//if(i == 0)
									CountClass[ClassIndex]++;

									//System.out.println("CountAtt["+i+"]["+k+"]["+ClassIndex+"]"+"++" + " " + save[j][i]);

								}

								

							}

						}

						

						

					}

				

			}

		}
		
		int num = 0;
		double ClassEntropy = 0;
		maxClassIndex = 0;
		for(int ClassIndex = 0;ClassIndex<SaveClass.get(0).size();ClassIndex++)
		{
			num += CountClass[ClassIndex];
			if(ClassIndex >0)
			{
				if(CountClass[ClassIndex] > CountClass[maxClassIndex])
				{
					maxClassIndex = ClassIndex;
				}
			}
			
			//comp_GetMax(CountClass[ClassIndex]);
			//System.out.println("<Calculation> CountClass "+ CountClass[ClassIndex]);
		}
		for(int ClassIndex = 0;ClassIndex<SaveClass.get(0).size();ClassIndex++)
		{
			ClassEntropy += (double)CountClass[ClassIndex]/num*CalLog2((double)CountClass[ClassIndex]/num);
		}
		//System.out.println("<Calculation> ClassEntropy "+ ClassEntropy);
		if(Double.isNaN(ClassEntropy))
		{
			//System.out.println("<Calculation> same value ");
			ArrayList<String> endtmp = new ArrayList<String>();
			endtmp.add("same value");
			return endtmp;
		}
		num = 0;
		int num4weight = 0;

		H = new double[NumAtt][MaxNumAttVal];

		WeightedAverageH = new double[NumAtt];

		for(int i = 0; i<width-1;i++)

		{
			num4weight = 0;
			//System.out.println("print saveAttribute.get"+i+".get0"+SaveAttribute.get(i).get(0));
			if(ContainAlpha(SaveAttribute.get(i).get(0)))//categorical attribute

			{

				for(int j = 0; j<SaveAttribute.get(i).size();j++)

				{

					num = 0;

					for(int n = 0; n<SaveClass.get(0).size();n++)

					{

						//System.out.println("saveAttribute" + SaveAttribute.get(i).get(j)+" "+SaveClass.get(0).get(n) +" CountAtt["+i+"]["+j+"]["+n+"] "+CountAtt[i][j][n]);

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
							//System.out.println("Hij : " + H[i][j] + " "+"CountAtt"+i+j+n+" " +CountAtt[i][j][n] + " num "+num);
						}

						//System.out.println("H "+H[i][j]);
						if(Double.isNaN((double)H[i][j]*num))
						{
							WeightedAverageH[i] += 0;
						}
						else
							WeightedAverageH[i] += (double)H[i][j]*num;
						//System.out.println("num : " + num + " H : "+H[i][j] + " TempWeightedAverageH"+i+" : "+WeightedAverageH[i]);
						num4weight += num;
						//System.out.println("num4weight "+num4weight);
					

					

				}

				WeightedAverageH[i] = (double)WeightedAverageH[i]/num4weight;
				if(Double.isNaN(WeightedAverageH[i]))
				{
					WeightedAverageH[i] = 0;
				}
				//System.out.println("num4weight "+num4weight);
				//System.out.println("WeightedAverageH"+i+" " + WeightedAverageH[i]);

			}

			else if(ContainNumber(SaveAttribute.get(i).get(0)))//numerical attribute
			{

				old = 1;//cause it's min..... not 0

				double Min = 0;
				Integer AValue = null;
				
				for(int x = 0; x<SaveAttribute.get(i).size();x++)
				{

					for(int n = 0; n<SaveClass.get(0).size();n++)

					{

						//System.out.println("saveAttribute" + SaveAttribute.get(i).get(x)+" "+SaveClass.get(0).get(n) +" CountAtt["+i+"]["+x+"]["+n+"] "+CountAtt[i][x][n]);

						

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

						//System.out.println("asdf : " + SaveAttribute.get(i) + "Id : " + Id);
						AValue = Id;
						
				}
				if(SaveAttribute.get(i) != null)
				{
					numericAttribute.add(AValue);
					NumericAttribute.put(i, AValue);
				}
				System.out.println("numericAttribute " + numericAttribute);
				WeightedAverageH[i] = Min;

				System.out.println("H " + WeightedAverageH[i] + " MIn " + Min);

			}
			else
			{
				WeightedAverageH[i] = 10;
			}
			

		}
		System.out.println(numericAttribute);
		double MinWeightedAverageH = 0;

		for(int i = 0; i<width-1;i++)

		{

			if(i == 0)
			{
				old = WeightedAverageH[i];
				Id = 0;
			}

			MinWeightedAverageH = comp(WeightedAverageH[i],i);

		}

		//System.out.println(CountClass[0] + " " + CountClass[1]);
		//System.out.println("SaveAttribute.get(0) : " +SaveAttribute.get(0) + " " + Id);
		//System.out.println("MinWeightedAverageH : "+SaveAttribute.get(Id) + " " + MinWeightedAverageH);
		DataInfo.entropy = MinWeightedAverageH;
		//if(ContainNumber(SaveAttribute.get(Id).get(NumericAttribute.get(Id))))
		if(ContainNumber(SaveAttribute.get(Id).get(0)))
		{
			ArrayList<String> tmp = new ArrayList<String>();
			for(int i = 0; i<2; i++)
				tmp.add(SaveAttribute.get(Id).get(NumericAttribute.get(Id)));
			//Id = NumericAttribute.get(Id);
			System.out.println("reference value : " + tmp);
			return tmp;
		}
		
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
	
	static boolean ContainNumber(String OneOfAtt)

	{

		boolean atleastOneNumber = OneOfAtt.matches(".*[1-9]+.*");

		return atleastOneNumber;

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

		//System.out.println("Old New " + old + " " + New);

		if (old>=New)

		{

			old = New;

			Id = id;
			//System.out.println("Id : "+Id);
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
