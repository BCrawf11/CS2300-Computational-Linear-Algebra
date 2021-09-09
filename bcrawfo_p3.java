/*	
	Brodie Crawford
	CS 2300
	9/2/21
	Assignment 1
	Puts different combinations of multiplied matrices
	in different output files
*/

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.*;

public class bcrawfo_p3
{
	public static void main (String [] args) throws IOException
	{
		File file1 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat1.txt");
		File file2 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat2.txt");
		File file3 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat3.txt");
		File file4 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat4.txt");
	    	File file5 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat5.txt");
	    
	    //Does 25 combinations of matrix additions. Hard coded due to changing file name each time.
	    writeMultiplicationOfMatrices(file1, file1, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out11.txt"));
	    writeMultiplicationOfMatrices(file1, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out12.txt"));
	    writeMultiplicationOfMatrices(file1, file3, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out13.txt"));
	    writeMultiplicationOfMatrices(file1, file4, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out14.txt"));
	    writeMultiplicationOfMatrices(file1, file5, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out15.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out21.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out22.txt"));
	    writeMultiplicationOfMatrices(file2, file3, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out23.txt"));
	    writeMultiplicationOfMatrices(file2, file4, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out24.txt"));
	    writeMultiplicationOfMatrices(file2, file5, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out25.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out31.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out32.txt"));
	    writeMultiplicationOfMatrices(file3, file3, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out33.txt"));
	    writeMultiplicationOfMatrices(file3, file4, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out34.txt"));
	    writeMultiplicationOfMatrices(file3, file5, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out35.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out41.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out42.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out43.txt"));
	    writeMultiplicationOfMatrices(file4, file4, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out44.txt"));
	    writeMultiplicationOfMatrices(file4, file5, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out45.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out51.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out52.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out53.txt"));
	    writeMultiplicationOfMatrices(file2, file2, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out54.txt"));
	    writeMultiplicationOfMatrices(file5, file5, new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_p3_out55.txt"));    
	}
	
	public static void writeMultiplicationOfMatrices(File file1, File file2, File output) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsoluteFile()));
		Scanner scan1 = new Scanner(file1);
		Scanner scan2 = new Scanner(file2);
		
		//creates the matrices and gets their row dimensions
		double[][] mat1;
		double[][] mat2;
		long h1 = Files.lines(Paths.get(file1.getPath())).count();
		long h2 = Files.lines(Paths.get(file2.getPath())).count();
		int height1 = (int)h1;
		int height2 = (int)h2;
		int count1 = 1;
		int count2 = 1;
		
		//gets the first line and uses it to define the size of the matrix
		String[] s1 = scan1.nextLine().split(" ");
		String[] s2 = scan2.nextLine().split(" ");
		mat1 = new double[height1][s1.length];
		mat2 = new double[height2][s2.length];
		
		//puts the first line into the first matrix
		for (int i = 0; i < mat1[0].length; i++)
		{
			mat1[0][i] = Double.parseDouble(s1[i]);
		}
		
		//puts the first line into the second matrix
		for (int i = 0; i < mat2[0].length; i++)
		{
			mat2[0][i] = Double.parseDouble(s2[i]);
		}
		
		//puts other lines in the first matrix
		while(scan1.hasNext())
		{
			String[] s = scan1.nextLine().split(" ");
			double[] m = new double[s.length];
			
			for (int i = 0; i < m.length; i++)
			{
				m[i] = Double.parseDouble(s[i]);
			}

			mat1[count1] = m;
			count1++;
		}
		
		//puts other lines in the second matrix
		while(scan2.hasNext())
		{
			String[] s = scan2.nextLine().split(" ");
			double[] m = new double[s.length];
			
			for (int i = 0; i < m.length; i++)
			{
				m[i] = Double.parseDouble(s[i]);
			}

			mat2[count2] = m;
			count2++;
		}
		
		//checks if the dimensions are compatible for multiplication
		if (mat1[0].length == mat2.length)
		{
			//Does the addition and prints it to the respective output file
			for (int i = 0; i < mat1.length; i++)
			{
				for (int j = 0; j < mat2[0].length; j++)
				{
					double sum = 0;
					
					//gets the dot product of matrix 1's first row and matrix 2's first column,
					//and then continues from there
					for (int k = 0; k < mat1[0].length; k++)
					{
						sum += mat1[i][k] * mat2[k][j];
					}
					
					//prints the sum calculated in the previous for loop
					bw.write(new DecimalFormat("0.#").format(sum) + " ");
				}
				
				bw.write("\n");
	        	}
		}
		else
			//displays the error message
			bw.write("Error: Unable to multiply these matrices.");
		
		bw.close();
	}
}
