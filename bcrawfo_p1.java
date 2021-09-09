/*	
	Brodie Crawford
	CS 2300
	9/2/21
	Assignment 1
	Creates some matrices and adds them to a file
*/

import java.io.*;
import java.text.*;

public class bcrawfo_p1
{    
	public static void main (String [] args) throws IOException
	{
	    //creates files to hold all of the data inside each matrix
		File file1 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat1.txt");
		File file2 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat2.txt");
		File file3 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat3.txt");
		File file4 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat4.txt");
	    File file5 = new File("C:\\Users\\brodi\\OneDrive\\Desktop\\Brodie\\College\\Fall 2021\\CS 2300\\bcrawfo_mat5.txt");
	    
		//create strings of first name and last name
		String fN = "Brodie";
		String lN = "Crawford";
		
		//create matrices specified in the assignment description
		double[][] mat1 = createMatrix(fN.length(), lN.length(), 1, 1, false);
		double[][] mat2 = createMatrix(lN.length(), fN.length(), 3, 3, true);
		double[][] mat3 = createMatrix(lN.length(), fN.length(), 0.4, 0.3, true);
		double[][] mat4 = createMatrix(6, 13, 2, 2, true);
		double[][] mat5 = createMatrix(6, 13, -7, 1, false);

        ///error checking to create a new file only if it doesn't exist already
        if (!file1.exists())
        {
            file1.createNewFile();
        }
        
        if (!file2.exists())
        {
            file2.createNewFile();
        }
        
        if (!file3.exists())
        {
            file3.createNewFile();
        }
        
        if (!file4.exists())
        {
            file4.createNewFile();
        }
        
        if (!file5.exists())
        {
            file5.createNewFile();
        }
        ///
        
        //sends the matrices to be written to the files
        writeMat(mat1, file1);
        writeMat(mat2, file2);
        writeMat(mat3, file3);
        writeMat(mat4, file4);
        writeMat(mat5, file5);
	}
	
	public static double[][] createMatrix(int rows, int columns, double start, double inc, boolean rowsFirst)
	{
		double[][] mat;
		double val = start;
		int r, c;
		
		mat = new double[rows][columns];
		
		///checks if the rows or the columns are first to put values in
		if (rowsFirst)
		{
			for (int i = 0; i < columns; i++)
			{
				for (int j = 0; j < rows; j++)
				{
					mat[j][i] = val;
					val += inc;
				}
			}
		}
		else
		{
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					mat[i][j] = val;
					val += inc;
				}
			}
		}
		///
		
		return mat;
	}
	
	//writes data from the given matrix to the given file, formatted correctly
	public static void writeMat(double[][] mat, File f) throws IOException
	{		
        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));

        //writes matrix data to the file passed
		for (int i = 0; i < mat.length; i++)
		{
			for (int j = 0; j < mat[i].length; j++)
			{
				bw.write(new DecimalFormat("0.#").format(mat[i][j]) + " ");
			}
			
        	bw.write("\n");
        }
		
        bw.close();
	}
}
