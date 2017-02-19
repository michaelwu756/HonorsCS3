import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.math.*;
import java.util.ArrayList;

public class Problem4
{
	public static String selectionSort(String s)
	{
		String returnString = "";
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < s.length(); i++){
			String c = s.substring(i, i+1);
			list.add(Integer.parseInt(c));
		}
		int largest;
		for (int x=0;x<list.size();x++)
		{
			largest=x;
			for(int y=x;y<list.size();y++)
			{
				if (list.get(y)>list.get(largest))
					largest=y;
			}
			if (x!=largest)
			{
				list.add(x, list.remove(largest));
				list.add(largest, list.remove(x+1));
			}
		}
		for (int i:list)
		{
			returnString = returnString.concat(String.valueOf(i));
		}
		return returnString;
	}

	public static void main(String args[])
	{
		File name = new File("problem4.txt");

		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text,output="";
			int x=0;
			while((text=input.readLine())!=null)
			{
				x++;
				text=Problem4.selectionSort(text);
				output+="Output #"+String.valueOf(x)+": "+text+"\n";
			}

			System.out.println(output);

		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

	}
}
