public class ListNode<E>
{
	private E value;
	private ListNode<E> previous;
	private ListNode<E> next;

	public ListNode(E initValue, ListNode<E> initPrevious, ListNode<E> initNext)
	{
		value=initValue;
		previous=initPrevious;
		next=initNext;
	}
	public ListNode(ListNode<E> initNode)
	{
		value=initNode.getValue();
		previous=null;
		next=null;
	}
	
	public E getValue()
	{
		return value;
	}
	public ListNode<E> getPrevious()
	{
		return previous;
	}	
	public ListNode<E> getNext()
	{
		return next;
	}
	public void setValue(E theNewValue)
	{
		value=theNewValue;
	}	
	public void setPrevious(ListNode<E> theNewPrevious)
	{
		previous=theNewPrevious;
	}
	public void setNext(ListNode<E> theNewNext)
	{
		next=theNewNext;
	}
	private ListNode<E> getNodeAtIndex(int index)
	{
		ListNode<E> nodeAtIndex = this;
		for (int x=0; x<index; x++)
		{
			nodeAtIndex=nodeAtIndex.getNext();
		}
		return nodeAtIndex;
	}
	
	public void setValue(int index, E theNewValue)
	{
		getNodeAtIndex(index).setValue(theNewValue);
	}	
	public void append(E givenValue)
	{
		ListNode<E> nodeAtIndex = getNodeAtIndex(size()-1);
		if (nodeAtIndex.getValue()!=null)
			nodeAtIndex.setNext(new ListNode<E>(givenValue, nodeAtIndex, null));
		else
			nodeAtIndex.setValue(givenValue);
	}
	public void insert(int index, E givenValue)
	{
		ListNode<E> nodeAtIndex = getNodeAtIndex(index);
		if (nodeAtIndex==null)
		{
			append(givenValue);
		}
		else
		{
			ListNode<E> newNode = new ListNode<E>(givenValue, nodeAtIndex.getPrevious(), nodeAtIndex);
			nodeAtIndex.getPrevious().setNext(newNode);
			nodeAtIndex.setPrevious(newNode);
		}
	}
	public int size()
	{
		ListNode<E> nodeAtIndex = this;
		int index = 0;
		while(nodeAtIndex!=null)
		{
			index++;
			nodeAtIndex=nodeAtIndex.getNext();
		}
		if (index==1 && value==null)
			return 0;
		return index;
	}
	public void remove(int index)
	{
		ListNode<E> nodeAtIndex = getNodeAtIndex(index);
		if (nodeAtIndex.getPrevious()!=null)
		{
			nodeAtIndex.getPrevious().setNext(nodeAtIndex.getNext());
			if (nodeAtIndex.getNext()!=null)	
				nodeAtIndex.getNext().setPrevious(nodeAtIndex.getPrevious());
			return;	
		}
		if (nodeAtIndex==this)
		{
			if (getNext()!= null)
			{
				value=getNext().getValue();
				setNext(getNext().getNext());
				if (getNext()!=null)
					getNext().setPrevious(this);
			}
		}
	}
	public void remove()
	{
		remove(0);
	}
	
	public String toString()
	{
		String output="{";
		for (int x=0; x<this.size(); x++)
		{
			ListNode<E> nodeAtIndex=getNodeAtIndex(x);
			output=output.concat(String.valueOf(nodeAtIndex.getValue()));
			
			if (x!=this.size()-1)
				output=output.concat(", ");
		}
		output=output.concat("}");
		return output;
	}
	
	public String toStringReverse()
	{
		String output="{";
		for (int x=this.size()-1; x>=0; x--)
		{
			ListNode<E> nodeAtIndex=getNodeAtIndex(x);
			output=output.concat(String.valueOf(nodeAtIndex.getValue()));
			
			if (x!=0)
				output=output.concat(", ");
		}
		output=output.concat("}");
		return output;
	}
	
	public void outputLinks()
	{
		
		String output = "";
		if (getPrevious()!=null)
			output+=(getPrevious().getNext()==this)+" ";
		else
			output+="null ";
		output+=getValue()+" ";
		if (getNext()!=null)
			output+=(getNext().getPrevious()==this)+" ";
		else
			output+="null";
		System.out.println(output);
		if (getNext()!=null)
			getNext().outputLinks();
	}
	
}
