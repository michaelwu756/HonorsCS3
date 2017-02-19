import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.math.*;
import java.util.*;


public class Problem1
{
	public static void main(String args[])
	{
		File name = new File("problem1.txt");
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			LinkedList<Word> stringQueue= new LinkedList<Word>();
			PriorityQueue<Word> priorityStringQueue= new PriorityQueue<Word>(10000);
			Word wordText;
			String text;
			while( (text=input.readLine())!= null)
			{
				wordText = new Word(text);
				for(Word word:wordText.split(" "))
				{
					stringQueue.add(word);
					priorityStringQueue.add(word);
				}
			}
			
			while (stringQueue.size()>0)
			{
				System.out.format("%20s%20s\n", stringQueue.poll(), priorityStringQueue.peek());
				priorityStringQueue.remove(priorityStringQueue.peek());
			}

		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

	}
}
