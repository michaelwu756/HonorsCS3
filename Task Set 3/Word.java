public class Word implements Comparable
{
	String word;
	public Word(String str)
	{
		word = new String(str);
	}
	public Word[] split(String regex)
	{
		String[] stringArray = word.split(regex);
		Word[] returnArray = new Word[stringArray.length];
		int x=0;
		for (String str:stringArray)
		{
			returnArray[x]=new Word(str);
			x++;
		}
		return returnArray;
	}
	public int compareTo(Object e)
	{
		if (e instanceof Word)
		{
			Word input = (Word)e;
			String inputString = input.toString();
			if (word.toLowerCase().compareTo(inputString.toLowerCase()) == 0)
			{
				return word.compareTo(inputString);
			}
			return -1*word.toLowerCase().compareTo(inputString.toLowerCase());
		}
		return 0;
	}
	public String toString()
	{
		return word;
	}
}