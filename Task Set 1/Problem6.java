import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.math.*;
import java.util.ArrayList;


public class Problem6
{
	public static ArrayList<Integer> mergeSort(ArrayList<Integer> list)
	{
		int low = 0;
		int high = (list.size()-1);
		if (low>=high)
		{
			return list;
		}
		int middle = (low+high)/2;
		Problem6.mergeSort(list, low, middle);
		Problem6.mergeSort(list, middle+1, high);
		int endLow = middle;
		int startHigh = middle+1;
		while ((0<=endLow) &&(startHigh <=high))
		{
			if (list.get(low)< list.get(startHigh))
			{
				low++;
			}
			else
			{
				int temp = list.get(startHigh);
				for (int k=startHigh-1; k>=low; k--)
				{
					list.set(k+1,list.get(k));
				}
				list.set(low, temp);
				low++;
				endLow++;
				startHigh++;
			}
		}
		return list;
	}
	public static ArrayList<Integer> mergeSort(ArrayList<Integer> list, int lo, int n)
	{
		int low = lo;
		int high = n;
		if (low>=high)
		{
			return list;
		}
		int middle = (low+high)/2;
		Problem6.mergeSort(list, low, middle);
		Problem6.mergeSort(list, middle+1, high);
		int endLow = middle;
		int startHigh = middle+1;
		while ((lo<=endLow) &&(startHigh <=high))
		{
			if (list.get(low)< list.get(startHigh))
			{
				low++;
			}
			else
			{
				int temp = list.get(startHigh);
				for (int k=startHigh-1; k>=low; k--)
				{
					list.set(k+1,list.get(k));
				}
				list.set(low, temp);
				low++;
				endLow++;
				startHigh++;
			}
		}
		return list;
	}
	public static ArrayList<Integer> reverse(ArrayList<Integer> list)
	{
		ArrayList<Integer> reversedList = new ArrayList<Integer>();
		while (list.size()>0)
		{
			reversedList.add(list.remove(list.size()-1));
		}
		return reversedList;
	}
	public static ArrayList<Integer> pokerSort(String input)
	{
		ArrayList<Integer> sortString = new ArrayList<Integer>();
		for (int x=0; x<input.length(); x++)
		{
			if (!(input.substring(x,x+1).equals("A")) && !(input.substring(x,x+1).equals("K")) && !(input.substring(x,x+1).equals("Q")) && !(input.substring(x,x+1).equals("J")) && !(input.substring(x,x+1).equals("0")))
				sortString.add(Integer.parseInt(input.substring(x,x+1)));
			else switch (input.substring(x,x+1)){
				case "A":
					sortString.add(14);
				break;
				case "K":
					sortString.add(13);
				break;
				case "Q":
					sortString.add(12);
				break;
				case "J":
					sortString.add(11);
				break;
				case "0":
					sortString.add(10);
				break;
			}
		}
		return Problem6.mergeSort(sortString);
		
	}
	
	public static ArrayList<Integer> pokerValue(ArrayList<Integer> hand)
	{
		ArrayList<Integer> emptyHand = new ArrayList<Integer>();
		emptyHand.add(0);
		if(Problem6.fourOfAKind(hand)!=null)
		{
			return Problem6.fourOfAKind(hand);
		}
		else if(Problem6.fullHouse(hand)!=null)
		{
			return Problem6.fullHouse(hand);
		}
		else if(Problem6.straight(hand)!=null)
		{
			return Problem6.straight(hand);
		}
		else if(Problem6.threeOfAKind(hand)!=null)
		{
			return Problem6.threeOfAKind(hand);
		}
		else if(Problem6.twoPairs(hand)!=null)
		{
			return Problem6.twoPairs(hand);
		}
		else if(Problem6.onePair(hand)!=null)
		{
			return Problem6.onePair(hand);
		}
		return emptyHand;
	}
	
	public static int cardCount(int num, ArrayList<Integer> hand)
	{
		int countNum = 0;
		for (int y:hand)
		{
			if (y==num)
				countNum++;
		}
		return countNum;
	}
	
	public static ArrayList<Integer> fourOfAKind(ArrayList<Integer> hand)
	{
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		int cardNum;
		for (int x:hand)
		{
			cardNum = Problem6.cardCount(x, hand);
			if (cardNum==4)
			{
				returnValue.add(6);
				returnValue.add(x);
				for (int y:hand)
				{
					cardNum = Problem6.cardCount(y, hand);
					if (cardNum==1)
						returnValue.add(y);
				}
				return returnValue;
			}
		}
		return null;
	}
	public static ArrayList<Integer> fullHouse(ArrayList<Integer> hand)
	{
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		int cardNum;
		for (int x:hand)
		{
			cardNum = Problem6.cardCount(x, hand);
			if (cardNum==3)
			{
				for (int y:hand)
				{
					cardNum = Problem6.cardCount(y, hand);
					if (cardNum==2)
					{
						returnValue.add(5);
						returnValue.add(x);
						return returnValue;
					}
				}
				
			}
		}
		return null;
	}
	public static ArrayList<Integer> straight(ArrayList<Integer> hand)
	{
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		boolean straight=true;
		int x=hand.get(0);
		for (int y=1; y<hand.size(); y++)
		{
			if (!(x+y==hand.get(y)))
			{
				straight=false;
			}
		}
		if (straight==true)
		{
			returnValue.add(4);
			returnValue.add(hand.get(hand.size()-1));
			return returnValue;
		}	
		return null;
	}
	public static ArrayList<Integer> threeOfAKind(ArrayList<Integer> hand)
	{
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		ArrayList<Integer> kickers = new ArrayList<Integer>();
		int cardNum;
		for (int x:hand)
		{
			cardNum = Problem6.cardCount(x, hand);
			if (cardNum==3)
			{
				returnValue.add(3);
				returnValue.add(x);
				for (int y:hand)
				{
					cardNum = Problem6.cardCount(y, hand);
					if (cardNum==1)
						kickers.add(y);
				}
				kickers = Problem6.mergeSort(kickers);
				kickers = Problem6.reverse(kickers);
				for (int y:kickers)
				{
					returnValue.add(y);
				}
				return returnValue;
			}
		}
		return null;
	}
	public static ArrayList<Integer> twoPairs(ArrayList<Integer> hand)
	{
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		ArrayList<Integer> kickers = new ArrayList<Integer>();
		int cardNum;
		for (int x:hand)
		{
			cardNum = Problem6.cardCount(x, hand);
			if (cardNum==2)
			{
				for (int y:hand)
				{
					cardNum = Problem6.cardCount(y, hand);
					if (cardNum==2 && y!=x)
					{
						returnValue.add(2);
						if (y>x)
						{
							returnValue.add(y);
							returnValue.add(x);
						}
						else
						{
							returnValue.add(x);
							returnValue.add(y);
						}
						for (int z:hand)
						{
							cardNum = Problem6.cardCount(z, hand);
							if (cardNum==1)
								kickers.add(z);
						}
						kickers = Problem6.mergeSort(kickers);
						kickers = Problem6.reverse(kickers);
						for (int z:kickers)
						{
							returnValue.add(z);
						}
						return returnValue;
					}
				}
				
			}
		}
		return null;
	}
	public static ArrayList<Integer> onePair(ArrayList<Integer> hand)
	{
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		ArrayList<Integer> kickers = new ArrayList<Integer>();
		int cardNum;
		for (int x:hand)
		{
			cardNum = Problem6.cardCount(x, hand);
			if (cardNum==2)
			{
				returnValue.add(1);
				returnValue.add(x);
				for (int y:hand)
				{
					cardNum = Problem6.cardCount(y, hand);
					if (cardNum==1)
						kickers.add(y);
				}
				kickers = Problem6.mergeSort(kickers);
				kickers = Problem6.reverse(kickers);
				for (int y:kickers)
				{
					returnValue.add(y);
				}
				return returnValue;
			}
		}
		return null;
	}
	public static int pokerCompare(String hand1, String hand2)
	{
		ArrayList<Integer> hand1List = Problem6.pokerSort(hand1);
		ArrayList<Integer> hand2List = Problem6.pokerSort(hand2);
		ArrayList<Integer> hand1Value = Problem6.pokerValue(hand1List);
		ArrayList<Integer> hand2Value = Problem6.pokerValue(hand2List);
		for (int x=0; x<hand1Value.size(); x++)
		{
			if (hand1Value.get(x)>hand2Value.get(x))
				return 1;
			else if (hand2Value.get(x)>hand1Value.get(x))
				return 2;
		}
		return 0;
	}
	public static void main(String args[])
	{
		File name = new File("problem6.txt");

		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text, hand1, hand2="";
			text=input.readLine();
			int lines = Integer.parseInt(text);
			for (int i=0; i<lines; i++)
			{
				text = input.readLine();
				hand1 = text.substring(0,5);
				hand2 = text.substring(6,11);
				System.out.println(hand1+" "+hand2+" "+Problem6.pokerCompare(hand1, hand2));
			}

		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

	}
}
