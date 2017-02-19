import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.math.*;
import java.util.*;


public class Problem2
{
	public static void main(String args[])
	{
		File name = new File("problem2.txt");
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			LinkedList<Passenger> passengerQueue=new LinkedList<Passenger>();
			PriorityQueue<Passenger> passengerPriorityQueue=new PriorityQueue<Passenger>();
			String passengerName, city, time;
			String currentTime="9:03 AM";
			while((passengerName=input.readLine())!= null && !passengerName.equals(""))
			{
				city=input.readLine();
				time=input.readLine();
				passengerQueue.add(new Passenger(passengerName,city,time,currentTime));
			}
			while(passengerQueue.size()>0)
			{
				passengerPriorityQueue.add(passengerQueue.peek());
				System.out.println(passengerQueue.poll());
			}
			System.out.println();
			System.out.println("Flight departures by time");
			while(passengerPriorityQueue.size()>0)
			{
				System.out.println(passengerPriorityQueue.poll());
			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

	}
}