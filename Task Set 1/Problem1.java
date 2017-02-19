import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.math.*;
import java.util.ArrayList;


public class Problem1
{
	public static void main(String args[])
	{
		File name = new File("problem1.txt");

		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text="";
			ArrayList<Integer> primeList = new ArrayList<Integer>(1000);
			ArrayList<Integer> inputList = new ArrayList<Integer>();
			ArrayList<Integer> outputList = new ArrayList<Integer>();
			String outputString="";
			int readValue = 0;
			int nextNum=3;
			boolean divisible;
			primeList.add(2);
			while (primeList.size()<=1000)
			{
				divisible = false;
				for (int x:primeList)
				{
					if ((nextNum % x)==0)
					{
						divisible = true;
					}
				}
				if (divisible==false)
				{
					primeList.add(nextNum);
				}
				nextNum++;
			}

			while( !(text=input.readLine()).equals("0") && text!=null)
			{
				if (text!= null && !text.equals("0"))
				{
					readValue = Integer.parseInt(text);
					if (readValue<=1000)
					{
						inputList.add(readValue);
						outputList.add(primeList.get(readValue-1));
						System.out.println(primeList.get(readValue-1));
					}
					else System.out.println("Too large");
				}
			}
			outputString=outputString.concat("The ");
			for (int x:inputList)
			{
				if (inputList.indexOf(x)!=(inputList.size()-1))
				{
					if (x%10==1)
						outputString=outputString.concat(String.valueOf(x)+"-st, ");
					else outputString=outputString.concat(String.valueOf(x)+"-th, ");
				}
				else
				{
					outputString=outputString.substring(0,(outputString.length()-2));
					if (x%10==1)
						outputString=outputString.concat(" and "+String.valueOf(x)+"-st ");
					else outputString=outputString.concat(" and "+String.valueOf(x)+"-th ");
				}
			}
			outputString=outputString.concat("prime numbers are ");
			for (int x:outputList)
			{
				if (outputList.indexOf(x)!=(outputList.size()-1))
				{
					outputString=outputString.concat(String.valueOf(x)+", ");
				}
				else
				{
					outputString=outputString.concat("and "+String.valueOf(x));
				}
			}
			outputString=outputString.concat(" respectively.");
			System.out.println();
			System.out.println(outputString);

		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

	}
}
