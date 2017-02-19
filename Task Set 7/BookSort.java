import java.util.*;
import java.io.*;

public class BookSort
{
	private Stack<Book> bookList = new Stack<Book>();
	private PriorityQueue<Book> bookQueue = new PriorityQueue<Book>();
	public void readBooks(String fileName)
	{
		File name = new File(fileName);
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text = new String();
			while ((text=input.readLine())!=null)
			{
				text = text.split(" ",2)[1];
				String title,firstName,lastName;
				title = text.split(" by ")[0];
				firstName="";
				for (int i=0; i<text.split("by")[1].split(" ").length-1; i++)
					firstName = firstName.concat(text.split("by")[1].split(" ")[i]+" ");
				firstName = firstName.substring(1, firstName.length()-1);
				lastName = text.split("by")[1].split(" ")[text.split("by")[1].split(" ").length-1];
				
				text = input.readLine();
				double rating = 0;
				int numRatings = 0;
				rating = Double.valueOf(text.substring(0,4));
				numRatings = Integer.valueOf(text.split(" ")[4].replace(",",""));
				
				text = input.readLine();
				int score = 0, numVoters = 0;
				score = Integer.valueOf(text.split(" ")[1].replace(",",""));
				numVoters = Integer.valueOf(text.split(" ")[3].replace(",",""));
				bookList.push(new Book(firstName,lastName,title,rating,numRatings,score,numVoters));
			}
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	public void dumpBookList()
	{
		do{
			System.out.println(bookList.pop());
		}while (!bookList.empty());
	}
	public void bookStackToPriorityQueue()
	{
		do{
			bookQueue.add(bookList.pop());
		}while (!bookList.empty());
	}
	public void dumpBookQueue()
	{
		do{
			System.out.println(bookQueue.poll());
		}while (bookQueue.size()!=0);
	}
	public static void main(String args[])
	{
		BookSort app = new BookSort();
		app.readBooks("Top100SciFiGoodReads.txt");
		app.dumpBookList();
		System.out.println();
		app.readBooks("Top100SciFiGoodReads.txt");
		app.bookStackToPriorityQueue();
		app.dumpBookQueue();
	}
}