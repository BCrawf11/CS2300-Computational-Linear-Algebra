/*	
	Brodie Crawford
	CS 2300
	11/30/21
	Assignment 4, Part A
	Uses the power method to find the dominant
	eigenvector of a stochastic, non-negative
	matrix. Then ranks the indeces of the
	components from greatest to least.
*/

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;

public class bcrawf_partA
{
	public static void main(String[] args) throws IOException
	{
		//create paths and files to work with
		String inputPath = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 5\\input_A.txt";
		String outputPath = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 5\\bcrawford_output_A.txt";
		File input = new File(inputPath);
		File output = new File(outputPath);
		
		if (!output.exists())
        {
            output.createNewFile();
        }
		
		Scanner scan = new Scanner(input);
		BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsoluteFile()));
		
		//holds data from file as well as calculated data

	    double[][] matrix = new double[10][10];
		
		while(scan.hasNext())
		{
			for (int i = 0; i < matrix.length; i++)
			{
				for (int j = 0; j < matrix[i].length; j++)
				{
					matrix[i][j] = scan.nextDouble();
				}
			}
		}
		
		if (isStochasticAndNonNegative(matrix))
		{
			//do power algorithm and write error here if there is one
			bw.write(powerAlgorithm(matrix, output));
		}
		else
			bw.write("Input not valid. (Matrix is not stochastic or value in matrix is negative.)");
		
		scan.close();
		bw.close();
	}
	
	//check if the matrix is stochastic and all the values are non-negative
	public static boolean isStochasticAndNonNegative (double[][] mat)
	{
		double sum;
		
		for (int i = 0; i < mat.length; i++)
		{
			sum = 0;
			
			for (int j = 0; j < mat.length; j++)
			{
				//if a value is negative
				if (mat[j][i] < 0)
				{
					return false;
				}
				
				sum += (float)mat[j][i];
			}
			
			//if one of the columns doesn't add up to 1
			if (Math.round(sum) != 1)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static String powerAlgorithm (double[][] mat, File f) throws IOException
	{
		double[] eigenvector = new double[mat.length];
		double scaleValue = 0;
		double curEigenValue = 0;
		double prevEigenValue = 0;
		double tolerance = 0.000001;
		int maxIterations = 20;
		int k = 2;
		
		//set all components in the dominant eigenvector to 1
		for (int i = 0; i < eigenvector.length; i++)
		{
			eigenvector[i] = 1;
		}
		
		do
		{
			//error checking for maximum iterations
			if (k == maxIterations)
			{
				return "Maximum number of iterations exceeded.";
			}
			
			//update the eigenvector
			//implements y = Ar(k-1)
			eigenvector = multiplyMatrixByVector(mat, eigenvector);
			
			//get previous eigenvalue if count is greater than 2 to check with tolerance
			if (k > 3)
			{
				prevEigenValue = curEigenValue;
			}
			else if (k == 3)
			{
				prevEigenValue = 0.5;
			}
			
			//update the current eigenvalue
			//holds the largest value in the current eigenvector
			curEigenValue = largestValueInVector(eigenvector);
			
			//update the scale value
			scaleValue = largestValueInVector(eigenvector);
			
			if (scaleValue == 0)
			{
				return "Eigenvalue zero, select new r(1) and restart.";
			}
			
			//update the eigenvector
			//scale the eigenvector appropriately with r^(k) = y/yj using the scale value;
			for (int i = 0; i < eigenvector.length; i++)
			{
				eigenvector[i] = eigenvector[i]/scaleValue;
			}
			
			//increments the iteration counter
			k++;
		}
		//check if the eigenvalue is close enough to the previous eigenvalue (convergence)
		while (Math.abs(curEigenValue - prevEigenValue) > tolerance);
		
		/*
		 * Add writers and formatters, and then use them
		 * to write the required data to the file
		 */
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
		
		//creates decimal formatters
		DecimalFormat dF1 = new DecimalFormat();
		dF1.setMinimumFractionDigits(3);
		
		DecimalFormat dF2 = new DecimalFormat();
		dF2.setMinimumFractionDigits(0);
		
		//write eigenvector to file
		for (int i = 0; i < eigenvector.length; i++)
		{
			bw.write(dF1.format(eigenvector[i]) + " ");
		}
		
		bw.write("\n");
		
		double[] ranks = findPageRanks(eigenvector);
		
		//write indeces of eigenvector components from greatest to least to the file
		for (int i = 0; i < ranks.length; i++)
		{
			if (i == ranks.length - 1)
			{
				bw.write(dF2.format(ranks[i]) + "");
			}
			else
				bw.write(dF2.format(ranks[i]) + ", ");
		}
			
		bw.close();
		
		//algorithm completed successfully
		return "";
	}
	
	//multiplies a matrix and a vector together and returns a vector
	public static double[] multiplyMatrixByVector (double[][] mat, double[] vector)
	{
		double[] v = new double[vector.length];
		double sum = 0;
		
		for (int i = 0; i < mat.length; i++)
		{
			sum = 0;
			
			for (int j = 0; j < mat[i].length; j++)
			{
				sum += mat[i][j] * vector[j];
			}
			
			v[i] = sum;
		}
		
		return v;
	}
	
	//returns the largest value in the given vector
	public static double largestValueInVector (double[] v)
	{
		double maxValue = -1;
		
		for (int i = 0; i < v.length; i++)
		{
			if (v[i] > maxValue)
			{
				maxValue = v[i];
			}
		}
		
		return maxValue;
	}
	
	//return the indeces of the components of the eigenvector from greatest to least
	public static double[] findPageRanks (double[] eigenvector)
	{
		double[] ranks = new double[eigenvector.length];
		HashMap<Double, Integer> components = new HashMap<Double, Integer>();
		
		//add components to the hashmap
		for (int i = 0; i < eigenvector.length; i++)
		{
			components.put(eigenvector[i], i + 1);
		}
		
		//sorts the eigenvector components from least to greatest
		Arrays.sort(eigenvector);
		
		//puts the indeces in the ranks array based on the elements that they correspond
		//to, but backwards
		for (int i = 0; i < eigenvector.length; i++)
		{
			ranks[eigenvector.length - 1 - i] = components.get(eigenvector[i]);
		}
		
		return ranks;
	}
}