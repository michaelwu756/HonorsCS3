import java.util.*;

public class ListNodeTest
{
	public ListNodeTest()
	{
		ListNode<Integer> intList = new ListNode<Integer>(null, null, null);
		for (int x=0;x<30;x++)
		{
			intList.append(new Integer((int)(Math.random()*1000)+1));
		}
		System.out.println(intList);
		System.out.println(intList.toStringReverse());
		System.out.println(String.valueOf(intList.size()));
		System.out.println(String.valueOf(sum(intList)));
		System.out.println(String.valueOf(sumEven(intList)));
		System.out.println(String.valueOf(sumOdd(intList)));
		duplicateEven(intList);
		System.out.println(intList);
		removeMultiplesOfThree(intList);
		System.out.println(intList);
		intList.insert(3, 5555);
		System.out.println(intList);
		int value=getMedian(intList);
		System.out.println(String.valueOf(value));
		ListNode<Integer> secondHalf = spliceSecondHalf(intList, value);
		System.out.println(intList);
		System.out.println(secondHalf.getNext());
	}
	
	
	public int sum (ListNode<Integer> a)
	{
		int output=0;
		for (ListNode<Integer> x=a;x!=null;x=x.getNext())
		{
			output+=x.getValue();
		}
		return output;
	}
	public int sumEven(ListNode<Integer> a)
	{
		int output=0;
		boolean even = true;
		for (ListNode<Integer> x=a;x!=null;x=x.getNext())
		{
			if (even)
			{
				even=false;
				output+=x.getValue();
			}
			else
				even=true;
		}
		return output;
	}
	public int sumOdd(ListNode<Integer> a)
	{
		int output=0;
		boolean odd = false;
		for (ListNode<Integer> x=a;x!=null;x=x.getNext())
		{
			if (odd)
			{
				odd=false;
				output+=x.getValue();
			}
			else
				odd=true;
		}
		return output;
	}
	public void duplicateEven(ListNode<Integer> a)
	{
		int origSize=a.size();
		ListNode<Integer> x=a;
		
		for (int i=0;i<origSize;i++)
		{
			if (x.getValue()%2==0)
				a.append(x.getValue());
			x=x.getNext();
		}
	}
	public void removeMultiplesOfThree(ListNode<Integer> a)
	{
		boolean removed= false;
		for (ListNode<Integer> x=a;x!=null;x=x.getNext())
		{
			if (removed && x!=null)
			{
				x=x.getPrevious();
				removed=false;
			}
			if(x.getValue()%3==0)
			{
				x.remove();
				removed=true;
			}
			if (x.getNext()==null && x.getValue()%3==0)
			{
				x.remove();
			}
		}
	}
	public ListNode<Integer> spliceSecondHalf(ListNode<Integer> a, int value)
	{
		for (ListNode<Integer> x=a;x!=null;x=x.getNext())
		{
			if (x.getValue()==value)
			{
				if (x.getPrevious()!=null)
				{
					x.getPrevious().setNext(null);
					x.setPrevious(null);
				}
				else a=null;
				return x;
			}
		}
		return null;
	}
	public int getMedian(ListNode<Integer> a)
	{
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		for(ListNode<Integer> x=a;x!=null;x=x.getNext())
		{
			intArray.add(x.getValue());
		}
		Collections.sort(intArray);
		System.out.println(intArray);
		return intArray.get((intArray.size()-1)/2);
	}

	public static void main(String args[])
	{
		ListNodeTest app=new ListNodeTest();
	}

}