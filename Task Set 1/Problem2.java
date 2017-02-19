import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.math.*;
import java.text.DecimalFormat;


public class Problem2
{
	public static void main(String args[])
	{
		File name = new File("problem2.txt");

		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text,text2,output="";
			Double square, guess = 0.0;
			DecimalFormat df = new DecimalFormat("#.0000");
			DecimalFormat dfOne= new DecimalFormat("#.0");
			while( (text=input.readLine())!= null && (text2=input.readLine())!=null)
			{
				square = Double.parseDouble(text);
				guess = Double.parseDouble(text2);
				for (int x=0; x<7; x++)
				{
					System.out.println("After iteration "+String.valueOf(x+1)+" : "+String.valueOf(df.format(guess)));
					guess=0.5*((square/guess)+guess);

				}
				System.out.println("Square root of "+String.valueOf(dfOne.format(square))+" is "+String.valueOf(df.format(guess))+" after 7 iterations.");
			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

	}
}
