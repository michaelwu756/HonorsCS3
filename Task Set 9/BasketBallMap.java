import java.util.*;
import java.io.*;

public class BasketBallMap
{
	public void readBasketBallPlayers(HashMap<String,ArrayList<BasketBallPlayer>> map, String fileName)
	{
		File name = new File(fileName);
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text = new String();
			while ((text=input.readLine())!=null)
			{
				String first,last,pos,team;
				first = text.split("\t")[0].split(" ")[0];
				last = text.split("\t")[0].split(" ")[1];
				pos = text.split("\t")[1];
				team = text.split("\t")[2];
				if (!map.containsKey(team))
					map.put(team, new ArrayList<BasketBallPlayer>());
				ArrayList<BasketBallPlayer> tempList = map.get(team);
				tempList.add(new BasketBallPlayer(first, last, pos));
				map.put(team,tempList);
			}
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	public void readBasketBallPlayers(TreeMap<String,ArrayList<BasketBallPlayer>> map, String fileName)
	{
		File name = new File(fileName);
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text = new String();
			while ((text=input.readLine())!=null)
			{
				String first,last,pos,team;
				first = text.split("\t")[0].split(" ")[0];
				last = text.split("\t")[0].split(" ")[1];
				pos = text.split("\t")[1];
				team = text.split("\t")[2];
				if (!map.containsKey(team))
					map.put(team, new ArrayList<BasketBallPlayer>());
				ArrayList<BasketBallPlayer> tempList = map.get(team);
				tempList.add(new BasketBallPlayer(first, last, pos));
				map.put(team,tempList);
			}
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		HashMap<String, ArrayList<BasketBallPlayer>> map = new HashMap<String, ArrayList<BasketBallPlayer>>();
		BasketBallMap app = new BasketBallMap();
		app.readBasketBallPlayers(map, "BasketBallPlayerList.txt");
		Iterator it=map.entrySet().iterator();
		while(it.hasNext())
		{	
			System.out.println(it.next());
		}
		
		System.out.println("");
		
		TreeMap<String, ArrayList<BasketBallPlayer>> map2 = new TreeMap<String, ArrayList<BasketBallPlayer>>();
		app.readBasketBallPlayers(map2, "BasketBallPlayerList.txt");
		it=map2.entrySet().iterator();
		while(it.hasNext())
		{	
			System.out.println(it.next());
		}
	}
}