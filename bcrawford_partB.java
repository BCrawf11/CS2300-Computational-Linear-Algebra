/*	
	Brodie Crawford
	CS 2300
	11/10/21
	Assignment 4, Part B
	Takes in data to perform parallel and perspective
	projections on a series of points.
*/

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class bcrawford_partB
{
	public static void main(String[] args) throws IOException
	{
		//create paths and files to work with
		String inputPath = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 4\\input.txt";
		String outputPath1 = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 4\\bcrawford_output_1_B1.txt";
		String outputPath2 = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 4\\bcrawford_output_1_B2.txt";
		File input = new File(inputPath);
		File output1 = new File(outputPath1);
		File output2 = new File(outputPath2);
		
		if (!output1.exists())
        {
            output1.createNewFile();
        }
		
		if (!output2.exists())
        {
            output2.createNewFile();
        }
		
		Scanner scan = new Scanner(input);
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(output1.getAbsoluteFile()));
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(output2.getAbsoluteFile()));
		
		//holds data from file as well as calculated data
		double[] pointQ = { 0, 0, 0 };
		double[] vectorN = { 0, 0, 0 };
		double[] vectorV = { 0, 0, 0 };
		double[] pointX1 = { 0, 0, 0 };
		double[] pointX2 = { 0, 0, 0 };
		double[] pointX3 = { 0, 0, 0 };
		
		//holds output data for 1 line in each of 2 files at a time
		double[] parallelProjectedPoint1 = { 0, 0, 0 };
		double[] parallelProjectedPoint2 = { 0, 0, 0 };
		double[] parallelProjectedPoint3 = { 0, 0, 0 };
		double[] perspectiveProjectedPoint1 = { 0, 0, 0 };
		double[] perspectiveProjectedPoint2 = { 0, 0, 0 };
		double[] perspectiveProjectedPoint3 = { 0, 0, 0 };
		
		//creates a decimal formatter
		DecimalFormat dF = new DecimalFormat();
		dF.setMinimumFractionDigits(3);
		
		//gets point q on the plane from the file
		for (int i = 0; i < pointQ.length; i++)
		{
			pointQ[i] = scan.nextDouble();
		}
		
		//gets the vector n normal to the plane from the file
		for (int i = 0; i < vectorN.length; i++)
		{
			vectorN[i] = scan.nextDouble();
		}
		
		//gets the vector v - the projection direction - from the file
		for (int i = 0; i < vectorV.length; i++)
		{
			vectorV[i] = scan.nextDouble();
		}
		
		//each loop is one line worth of data
		while(scan.hasNext())
		{
			//get the 1st triangle point
			for (int i = 0; i < pointX1.length; i++)
			{
				pointX1[i] = scan.nextDouble();
			}
			
			//get the 2nd triangle point
			for (int i = 0; i < pointX2.length; i++)
			{
				pointX2[i] = scan.nextDouble();
			}
			
			//get the 3rd triangle point
			for (int i = 0; i < pointX3.length; i++)
			{
				pointX3[i] = scan.nextDouble();
			}
			
			//compute parallel projection and assign it a variable
			parallelProjectedPoint1 = parallelProjection(pointQ, vectorN, vectorV, pointX1);
			parallelProjectedPoint2 = parallelProjection(pointQ, vectorN, vectorV, pointX2);
			parallelProjectedPoint3 = parallelProjection(pointQ, vectorN, vectorV, pointX3);
			
			////write parallel projection points to output 1
			for (int j = 0; j < parallelProjectedPoint1.length; j++)
			{
				bw1.write(dF.format(parallelProjectedPoint1[j]) + " ");
			}
			
			for (int j = 0; j < parallelProjectedPoint2.length; j++)
			{
				bw1.write(dF.format(parallelProjectedPoint2[j]) + " ");
			}
			
			for (int j = 0; j < parallelProjectedPoint3.length; j++)
			{
				bw1.write(dF.format(parallelProjectedPoint3[j]) + " ");
			}
			////
			
			//compute perspective projection and assign it a variable
			perspectiveProjectedPoint1 = perspectiveProjection(pointQ, vectorN, pointX1);
			perspectiveProjectedPoint2 = perspectiveProjection(pointQ, vectorN, pointX2);
			perspectiveProjectedPoint3 = perspectiveProjection(pointQ, vectorN, pointX3);
			
			////write perspective projection points to output 1
			for (int j = 0; j < perspectiveProjectedPoint1.length; j++)
			{
				bw2.write(dF.format(perspectiveProjectedPoint1[j]) + " ");
			}
			
			for (int j = 0; j < perspectiveProjectedPoint2.length; j++)
			{
				bw2.write(dF.format(perspectiveProjectedPoint2[j]) + " ");
			}
			
			for (int j = 0; j < perspectiveProjectedPoint3.length; j++)
			{
				bw2.write(dF.format(perspectiveProjectedPoint3[j]) + " ");
			}
			////
			
			//move to next line
			bw1.write("\n");
			bw2.write("\n");
		}
		
		//close scanner and writers
		scan.close();
		bw1.close();
		bw2.close();
	}
	
	//computes parallel projection of a point with the equation x' = x + [([q - x]·n) / v·n][v].
	public static double[] parallelProjection(double[] q, double[] n, double[] v, double[] x)
	{
		//[q - x]·n
		double[] qMinusX = { (q[0] - x[0]), (q[1] - x[1]), (q[2] - x[2]) };
		
		//([q - x]·n) / v·n
		double numerator = dotProduct(qMinusX, n);
		double denominator = dotProduct(v, n);
		double numDenom = numerator/denominator;
		
		//x + [([q - x]·n) / v·n][v]
		double[] addToX = { numDenom * v[0], numDenom * v[1], numDenom * v[2] };
		double[] xPrime = { x[0] + addToX[0], x[1] + addToX[1], x[2] + addToX[2] };
				
		return xPrime;
	}
	
	//computes perspective projection of a point with the equation x' = [q·n / x·n][x].
	public static double[] perspectiveProjection(double[] q, double[] n, double[] x)
	{
		//[q·n / x·n]
		double multiplyWithX = dotProduct(q, n) / dotProduct(x, n);
		
		//[q·n / x·n][x]
		double[] xPrime = { multiplyWithX * x[0], multiplyWithX * x[1], multiplyWithX * x[2] };
		
		return xPrime;
	}
	
	//calculates the dot product of two vectors.
	public static double dotProduct(double[] v1, double[] v2)
	{
		return (v1[0] * v2[0]) + (v1[1] * v2[1]) + (v1[2] * v2[2]);
	}
}