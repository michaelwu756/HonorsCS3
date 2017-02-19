import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.math.*;
import java.util.ArrayList;

public class Problem5
{
	public static ArrayList<Integer> determineTwins(ArrayList<Integer> list)
	{
		boolean noTwins;
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		for (int x=0; x<list.size(); x++)
		{
			noTwins=true;
			int listNum = list.get(x);
			for(int y=0; y<list.size();y++)
			{
				if (Problem5.isTwins(listNum,list.get(y)) && list.get(y)!=listNum)
					noTwins=false;
			}
			if (noTwins==true)
			{
				returnList.add(listNum);
			}
		}
		return returnList;
	}
	public static boolean isTwins(int x, int y)
	{
		if (Problem5.selectionSort(String.valueOf(x)).equals(Problem5.selectionSort(String.valueOf(y))))
			return true;
		return false;
	}
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
		File name = new File("problem5.txt");

		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text="";
			ArrayList<Integer> inputList = new ArrayList<Integer>();
			input.readLine();
			while((text=input.readLine())!=null)
			{
				inputList.add(Integer.parseInt(text));
			}
			ArrayList<Integer> outputList = Problem5.determineTwins(inputList);
			for (int x:outputList)
			{
				System.out.println(String.valueOf(x));
			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

	}
}
