/*	
	Brodie Crawford
	CS 2300
	12/1/21
	Assignment 4, Part B
	Uses linear classification to train a computer to
	tell if feature sets are in or out of a certain group.
*/

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class bcrawf_partB
{
	public static void main(String[] args) throws IOException
	{
		//create paths and files to work with
		String input1Path = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 5\\training_input_B.txt";
		String input2Path = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 5\\test_input_B.txt";
		String outputPath = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 5\\bcrawford_output_B.txt";
		File input1 = new File(input1Path);
		File input2 = new File(input2Path);
		File output = new File(outputPath);
		
		if (!output.exists())
        {
            output.createNewFile();
        }
		
		Scanner scan1 = new Scanner(input1);
		Scanner scan2 = new Scanner(input2);
		BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsoluteFile()));
		DecimalFormat dF = new DecimalFormat();
		dF.setMaximumFractionDigits(0);
		
		//holds data from file as well as calculated data
		double[] labels = new double[5];
		double[] weights = new double[6];
		double[][] featureSets = new double[][] {
			{ 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0 }
		};
		double[][] featureSetsTest = new double[][] {
			{ 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0 }
		};
		double[] labelsTest = new double[5];
		
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				if (j == 0)
				{
					labels[i] = scan1.nextDouble();
				}
				else
					featureSets[i][j] = scan1.nextDouble();
			}
		}

		weights = perceptronAlgorithm(labels, featureSets, output);
		
		//don't do the testing if the training algorithm fails, but do test if it passed
		if (weights != null)
		{
			//scan the feature sets to classify
			for (int i = 0; i < 5; i++)
			{
				for (int j = 1; j < 6; j++)
				{
					featureSetsTest[i][j] = scan2.nextDouble();
					
					System.out.print(featureSetsTest[i][j] + " ");
				}
				
				System.out.println();
			}
			
			//classify the feature sets with the calculated weights
			labelsTest = classify(weights, featureSetsTest);
			
			//and then print the labels
			for (int i = 0; i < labelsTest.length; i++)
			{
				bw.write(dF.format(labelsTest[i]) + " ");
			}
			
			bw.write("\n");
			
			//write the weights below the labels
			for (int i = 0; i < weights.length; i++)
			{
				bw.write(weights[i] + " ");
			}
		}
		
		scan1.close();
		scan2.close();
		bw.close();
	}
	
	//check if the matrix is stochastic and all the values are non-negative
	public static double[] perceptronAlgorithm (double[] labels, double[][] featureSets, File f) throws IOException
	{
		double[] weights = new double[] { 2, 2, 2, 2, 2, 2 };
		double fOfWDotX = 0;
		int maxIterations = 10;
		int k = 0;
		double error = 1;
		double tolerance = 0.01;
		
		do
		{			
			for (int i = 0; i < featureSets.length; i++)
			{				
				fOfWDotX = dotProduct(weights, featureSets[i]);
				
				//set correct label based on dot product
				if (fOfWDotX >= 0)
				{
					fOfWDotX = 1;
				}
				else
					fOfWDotX = 0;
				
				error = labels[i] - fOfWDotX;
				
				//debugging
				System.out.println(error);
				
				//update the weights array
				for (int j = 0; j < weights.length; j++)
				{
					weights[j] =  weights[j] + (featureSets[i][j] * error);
					
					//debugging
					System.out.print(weights[j] + " ");
				}
				
				//debugging
				System.out.println();
			}
			
			//increments the iterator to check for convergence
			k++;
			
			System.out.println();
				
		} while (k != maxIterations && error > tolerance);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
		
		if (k == maxIterations)
		{
			bw.write("Error: Data is not linearly separable.");
		}
		
		bw.close();
		
		//algorithm completed
		return weights;
	}
	
	//classifies the test features based on what the algorithm learned using the weights
	public static double[] classify (double[] weights, double[][] features)
	{
		double[] labels = new double[features.length];
		double d;
		
		for (int i = 0; i < features.length; i++)
		{
			d = dotProduct(weights, features[i]);
			
			if (d >= 0)
			{
				labels[i] = 1;
			}
			else
				labels[i] = 0;
		}
		
		return labels;
	}
	
	//calculates the dot product of two vectors.
	public static double dotProduct (double[] v1, double[] v2)
	{
		double sum = 0;
		
		for (int i = 0; i < v1.length; i++)
		{
			sum += (v1[i] * v2[i]);
		}
		return sum;
	}
}