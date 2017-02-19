import java.util.*;
import java.io.*;

public class BowlerMap
{
	public void readBowlers(TreeMap<Integer,PriorityQueue<Bowler>> map, String fileName)
	{
		File name = new File(fileName);
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text = new String();
			while ((text=input.readLine())!=null)
			{
				String first,last;
				first = text.split(" ")[0];
				last = text.split(" ")[1];
				int score = Integer.valueOf(text.split(" ")[2]);
				if (!map.containsKey(score))
					map.put(score, new PriorityQueue<Bowler>());
				PriorityQueue<Bowler> tempQueue = map.get(score);
				tempQueue.add(new Bowler(first, last, score));
				map.put(score,tempQueue);
			}
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		TreeMap<Integer, PriorityQueue<Bowler>> map = new TreeMap<Integer, PriorityQueue<Bowler>>();
		BowlerMap app = new BowlerMap();
		app.readBowlers(map, "BowlingData.txt");
		Iterator it=map.entrySet().iterator();
		while(it.hasNext())
		{	
			Map.Entry tempMap = (Map.Entry) it.next();
			System.out.print(String.valueOf(tempMap.getKey()+"="));
			PriorityQueue tempQueue = (PriorityQueue)tempMap.getValue();
			String output = new String();
			while (tempQueue.peek()!=null)
			{
				Bowler b = (Bowler)tempQueue.poll();
				output=output.concat(b.toString()+", ");
			}
			output=output.substring(0,output.length()-2);
			System.out.println(output);
		}
	}
}
	