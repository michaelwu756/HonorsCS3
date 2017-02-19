import java.util.*;
import java.io.*;

public class HashTreeSet
{
	public void readPeople(TreeSet<Person> tset, String fileName)
	{
		File name = new File(fileName);
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text = new String();
			while ((text=input.readLine())!=null)
			{
				String firstName,lastName;
				firstName = text.split(" ")[0];
				lastName = text.split(" ")[1];
				
				int month,day,year;
				month=Integer.valueOf(text.split(" ")[2].split("/")[0]);
				day=Integer.valueOf(text.split(" ")[2].split("/")[1]);
				year=Integer.valueOf(text.split(" ")[2].split("/")[2]);
				
				tset.add(new Person(firstName,lastName,month,day,year));
			}
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	public void readPeople(HashSet<Person> hset, String fileName)
	{
		File name = new File(fileName);
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text = new String();
			while ((text=input.readLine())!=null)
			{
				String firstName,lastName;
				firstName = text.split(" ")[0];
				lastName = text.split(" ")[1];
				
				int month,day,year;
				month=Integer.valueOf(text.split(" ")[2].split("/")[0]);
				day=Integer.valueOf(text.split(" ")[2].split("/")[1]);
				year=Integer.valueOf(text.split(" ")[2].split("/")[2]);
				
				hset.add(new Person(firstName,lastName,month,day,year));
			}
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	public void printSet(HashSet<Person> set)
	{
		Iterator it=set.iterator();
		while(it.hasNext())
		{
			Person temp=(Person)it.next();
			System.out.printf("%-30s %-20s %-20s %-20s %-20s", temp.getLastName()+", "+temp.getFirstName(),
			"Birth Year: "+temp.getYear(), "Birth Month: "+temp.getMonth(), "Birth Day: "+temp.getDay(), "HashValue: "+ temp.hashCode()); 
			System.out.println();
		}
	}
	public void printSet(TreeSet<Person> set)
	{
		Iterator it=set.iterator();
		while(it.hasNext())
		{
			Person temp=(Person)it.next();
			System.out.printf("%-30s %-20s %-20s %-20s %-20s", temp.getLastName()+", "+temp.getFirstName(),
			"Birth Year: "+temp.getYear(), "Birth Month: "+temp.getMonth(), "Birth Day: "+temp.getDay(), "HashValue: "+ temp.hashCode()); 
			System.out.println();
		}
	}
	public static void main(String[] args)
	{
		HashTreeSet app = new HashTreeSet();
		TreeSet<Person> treeSet = new TreeSet<Person>();
		HashSet<Person> hashSet = new HashSet<Person>();
		app.readPeople(hashSet, "PersonList.txt");
		app.readPeople(treeSet, "PersonList.txt");
		app.printSet(hashSet);
		System.out.println("----------");
		app.printSet(treeSet);
	}
}