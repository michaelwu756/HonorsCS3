
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.math.*;


public class Problem3
{
	public static String decToBin(String dec)
	{
		int a = Integer.parseInt(dec);
		String b = new String();
		do{
			b=Integer.toString(a%2).concat(b);
			a/=2;
		}while(a>0);
		return b;
	}
	
	public static void main(String args[])
	{
		File name = new File("problem3.txt");

		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text,output="";
			while( !(text=input.readLine()).equals("0") && text!=null)
			{
				text=Problem3.decToBin(text);
				output+=text+"\n";
			}

			System.out.println(output);

		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

	}
}
