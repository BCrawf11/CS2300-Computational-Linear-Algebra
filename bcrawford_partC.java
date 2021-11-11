/*	
	Brodie Crawford
	CS 2300
	11/10/21
	Assignment 4, Part C
	Works with points, lines, and planes. Part 1
	computes the distance from points to planes,
	and part 2 finds out whether or not a line
	intersects with the given triangles made from
	inputted points.
*/

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class bcrawford_partC
{
	public static void main(String[] args) throws IOException
	{
		//create paths and files to work with
		String inputPath = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 4\\input.txt";
		String outputPath1 = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 4\\bcrawford_output_1_C1.txt";
		String outputPath2 = "C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\Assignment 4\\bcrawford_output_1_C2.txt";
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
		
		//holds part 1 data from file
		double[] pointQ = { 0, 0, 0 };
		double[] vectorN = { 0, 0, 0 };
		double[] pointX = { 0, 0, 0 };
		
		//holds part 2 data from file
		double[] pointX1 = { 0, 0, 0 };
		double[] pointY1 = { 0, 0, 0 };
		double[] pointP1 = { 0, 0, 0 };
		double[] pointP2 = { 0, 0, 0 };
		double[] pointP3 = { 0, 0, 0 };
		
		//holds output data for 1 line in each of 2 files at a time
		
		//creates a decimal formatter
		DecimalFormat dF = new DecimalFormat();
		dF.setMinimumFractionDigits(0);
		
		double j;
		
		//gets point q on the plane from the file (part 1)
		//gets point x on the line from the file (part 2)
		//loop only for the first line of file
		for (int i = 0; i < pointQ.length; i++)
		{
			j = scan.nextDouble();
			
			//part 1
			pointQ[i] = j;
			
			//part 2
			pointX1[i] = j;
		}
		
		//gets the vector n normal to the plane from the file
		//gets point y on the line from the file (part 2)
		//loop only for the first line of file
		for (int i = 0; i < vectorN.length; i++)
		{
			j = scan.nextDouble();
			
			//part 1
			vectorN[i] = j;
			
			//part 2
			pointY1[i] = j;
		}
		
		//gets point x from the file
		//loop only for the first line of file
		for (int i = 0; i < pointX.length; i++)
		{
			pointX[i] = scan.nextDouble();
		}
		
		//goes through the other lines in the file
		while(scan.hasNext())
		{			
			//gets point q on the plane from the file (part 1)
			for (int i = 0; i < pointQ.length; i++)
			{
				j = scan.nextDouble();
				
				//part 1
				pointQ[i] = j;
				
				//part 2
				pointP1[i] = j;
			}
			
			//gets the vector n normal to the plane from the file (part 1)
			for (int i = 0; i < vectorN.length; i++)
			{
				j = scan.nextDouble();
				
				//part 1
				vectorN[i] = j;
				
				//part 2
				pointP2[i] = j;
			}
			
			//gets point x from the file (part 1)
			for (int i = 0; i < pointX.length; i++)
			{
				j = scan.nextDouble();
				
				//part 1
				pointX[i] = j;
				
				//part 2
				pointP3[i] = j;
			}
			
			bw1.write(dF.format(distPointToPlane(pointQ, vectorN, pointX)) + "\n");
			
			//function that writes the input to a file by itself
			bw2.write(lineAndTriangleIntersection(pointX1, pointY1, pointP1, pointP2, pointP3) + "\n");
		}
		
		scan.close();
		bw1.close();
		bw2.close();
	}
	
	//computes the distance of point x to the plane with the equation d = (c + n·x)/n·n
	//where c = -n·q.
	public static double distPointToPlane(double[] q, double[] n, double[] x) 
	{
		//n·x; n·n
		double nDotX = dotProduct(n, x);
		double nDotN = dotProduct(n, n);
		
		//c = -n·q
		double[] negativeN = { -(n[0]), -(n[1]), -(n[2]) };
		double c = dotProduct(negativeN, q);
		
		//(c + n·x)/n·n
		return (c + nDotX)/nDotN;
	}
	
	//determines if the line and triangle intersect with the equation x + tv = p1 + u1(p2 - p1) + u2(p3 - p1)
	public static String lineAndTriangleIntersection(double[] x, double[] y, 
			double[] p1, double[] p2, double[] p3)
	{
		//x + tv = p1 + (u1)(w) + (u2)(z)
		double[] v = subtractVectors(y, x);
		double[] w = subtractVectors(p2, p1);
		double[] z = subtractVectors(p3, p1);
		
		//A in linear system:
		//{ wz-v 3x3mat } * { u1, u2, t } = { a, b, c }
		double[][] matA = {
				{ w[0], z[0], -v[0] },
				{ w[1], z[1], -v[1] },
				{ w[2], z[2], -v[2] }};
		
		//inverse of A in linear system
		double[][] invA = inverseMatrix3x3(matA);
				
		//b in linear system
		double[] abc = subtractVectors(x, p1);
		
		//solution to linear system Ax = b, where A is a 3x3 matrix
		double[] matX = { 0, 0, 0 };
		
		for (int i = 0; i < invA.length; i++)
		{
			for (int j = 0; j < invA[i].length; j++)
			{
				matX[i] = invA[i][j] * abc[j];
			}
		}
		
		/*
		  Tests if the line intersects with the triangle based on the linear
		  system that was solved for matrix X.
		  
		  Not sure how to find the intersection point of the line, so instead,
		  information on the line as either intersecting with the triangle or
		  not intersecting with the triangle is printed.
		*/
		
		if ((0 < matX[0] && matX[0] < 1) &&
				(0 < matX[1] && matX[1] < 1) &&
				((matX[0] + matX[1]) < 1))
		{
			return "Intersects with triangle";
		}
		else
			return "Does not intersect";
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
	
	//finds the inverse of a 3x3 matrix.
	public static double[][] inverseMatrix3x3(double[][] mat)
	{
		double det = 0.0;
		double[][] invMat = {
				{ 0, 0, 0 },
				{ 0, 0, 0 },
				{ 0, 0, 0 }};
		
		//finds the determinant of the matrix.
		for(int i = 0; i < 3; i++)
		{
	        det = det + (mat[0][i] * 
	        		(mat[1][(i + 1) % 3] * mat[2][(i + 2) % 3]
	        		- mat[1][(i + 2) % 3] * mat[2][(i + 1) % 3]));
		}
		
		//calculates the inverse of each of the matrix components based on the determinant
		//and the original matrix.
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; ++j)
			{
				invMat[i][j] = ((
						(mat[(j + 1) % 3][(i + 1) % 3] * mat[(j + 2) % 3][(i + 2) % 3])
						- (mat[(j + 1) % 3][(i + 2) % 3] * mat[(j + 2) % 3][(i + 1) % 3]))
						/ det);
			}
		}
		
		return invMat;
	}
}