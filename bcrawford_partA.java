/*	
	Brodie Crawford
	CS 2300
	11/10/21
	Assignment 4, Part A
	Takes in data to perform culling for triangles in
	3D space, as well as calculations for the light
	intensity of each of the triangles.
*/

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class bcrawford_partA
{
	public static void main(String[] args) throws IOException
	{
		//create paths and files to work with
		String inputPath = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 4\\input.txt";
		String outputPath = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 4\\bcrawford_output_1_A.txt";
		File input = new File(inputPath);
		File output = new File(outputPath);
		
		if (!output.exists())
        {
            output.createNewFile();
        }
		
		Scanner scan = new Scanner(input);
		BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsoluteFile()));
		
		//holds data from file as well as calculated data
		double[] eyeLocationPoint = { 0, 0, 0 };
		double[] lightDirection = { 0, 0, 0 };
		double[] pointP = { 0, 0, 0 };
		double[] pointQ = { 0, 0, 0 };
		double[] pointR = { 0, 0, 0 };
		double[] viewVector = { 0, 0, 0 };
		double[] normalVector = { 0, 0, 0 };
		
		//holds output
		ArrayList<Integer> cullOrNot = new ArrayList<Integer>();
		ArrayList<Double> intensityValues = new ArrayList<Double>();
		
		//creates a decimal formatter
		DecimalFormat dF = new DecimalFormat();
		dF.setMinimumFractionDigits(3);
		
		//gets the eye location point
		for (int i = 0; i < eyeLocationPoint.length; i++)
		{
			eyeLocationPoint[i] = scan.nextDouble();
		}
		
		//gets the light direction
		for (int i = 0; i < lightDirection.length; i++)
		{
			lightDirection[i] = scan.nextDouble();
		}
		
		//ignores the last three numbers
		scan.nextDouble();
		scan.nextDouble();
		scan.nextDouble();
		
		//each loop is one line worth of data
		while(scan.hasNext())
		{
			//get the 1st triangle point
			for (int i = 0; i < pointP.length; i++)
			{
				pointP[i] = scan.nextDouble();
			}
			
			//get the 2nd triangle point
			for (int i = 0; i < pointQ.length; i++)
			{
				pointQ[i] = scan.nextDouble();
			}
			
			//get the 3rd triangle point
			for (int i = 0; i < pointR.length; i++)
			{
				pointR[i] = scan.nextDouble();
			}
			
			viewVector = viewVector(findCentroid(pointP, pointQ, pointR), eyeLocationPoint);
			normalVector = normalVector(pointP, pointQ, pointR);
			
			//decides whether or not to cull the triangle
			if (dotProduct(normalVector, viewVector) < 0)
				cullOrNot.add(0);
			else
				cullOrNot.add(1);
			
			if (calculateIntensity(lightDirection, normalVector) < 0)
				intensityValues.add(0.0);
			else
				intensityValues.add(calculateIntensity(lightDirection, normalVector));
		}
		
		//part 1: write culling boolean values to file
		for (int i = 0; i < cullOrNot.size(); i++)
		{
			bw.write(cullOrNot.get(i) + "\t");
		}
		
		//move to next line
		bw.write("\n");
		
		//part 2: write intensity values to file
		for (int i = 0; i < intensityValues.size(); i++)
		{
			bw.write(dF.format(intensityValues.get(i)) + "\t");
		}
		
		//move to next line
		bw.write("\n");
		
		//part 3: write intensity values with culling
		for (int i = 0; i < cullOrNot.size(); i++)
		{
			//print intensity if not culling
			if (cullOrNot.get(i) == 1)
				bw.write(dF.format(intensityValues.get(i)) + "\t");
			else
				bw.write("--\t");
		}
		
		//close scanner and writer
		scan.close();
		bw.close();
	}
	
	//finds the centroid of a given triangle.
	public static double[] findCentroid(double[] p, double[] q, double[] r)
	{
		return new double[] { 
				(p[0] + q[0] + r[0])/3, 
				(p[1] + q[1] + r[1])/3, 
				(p[2] + q[2] + r[2])/3 };
	}
	
	//calculates the view vector with the equation v = (e-c)/||(e-c)||.
	public static double[] viewVector(double[] c, double[] e)
	{
		double[] vv = { 0, 0, 0 };
		double[] eMinusC = subtractVectors(e, c);
		double vL = vectorLength(eMinusC);
		
		vv[0] = eMinusC[0]/vL;
		vv[1] = eMinusC[1]/vL;
		vv[2] = eMinusC[2]/vL;
		
		return vv;
	}
	
	//calculates the normal vector with the equation n = (u ^ w)/||u ^ w||.
	public static double[] normalVector(double[] p, double[] q, double[] r)
	{
		double[] u = { q[0] - p[0], q[1] - p[1], q[2] - p[2] };
		double[] w = { r[0] - p[0], r[1] - p[1], r[2] - p[2] };
		double[] crossUW = crossProduct(u, w);
		double vL = vectorLength(crossUW);
		double[] n = {
				crossUW[0]/vL,
				crossUW[1]/vL,
				crossUW[2]/vL };
		
		return n;
	}
	
	//calculates the light intensity with the equation i = (d · n)/(||n|| ||d||).
	public static double calculateIntensity(double[] d, double[] n)
	{
		double dDotN = dotProduct(d, n);
		double dLTimesNL = vectorLength(n) * vectorLength(d);
		
		return dDotN / dLTimesNL;
	}
	
	//calculates the cross product of two vectors.
	public static double[] crossProduct(double[] v1, double[] v2)
	{
		return new double[] {
				(v1[1] * v2[2] - v1[2] * v2[1]),
				(v1[2] * v2[0] - v1[0] * v2[2]),
				(v1[0] * v2[1] - v1[1] * v2[0]) };
	}
	
	//calculates the dot product of two vectors.
	public static double dotProduct(double[] v1, double[] v2)
	{
		return (v1[0] * v2[0]) + (v1[1] * v2[1]) + (v1[2] * v2[2]);
	}
	
	//subtracts the second vector from the first vector.
	public static double[] subtractVectors(double[] v1, double[] v2)
	{
		return new double[] { (v1[0] - v2[0]), (v1[1] - v2[1]), (v1[2] - v2[2]) };
	}
	
	//returns the length of the given vector.
	public static double vectorLength(double[] v)
	{
		return Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2) + Math.pow(v[2], 2));
	}
}
