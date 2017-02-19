public class Bowler implements Comparable<Bowler>
{
	private String first, last;
	private int score;
	public Bowler(String f, String l, int s)
	{
		first = f;
		last = l;
		score = s;
	}
	public String getFirstName()
	{
		return first;
	}
	public String getLastName()
	{
		return last;
	}
	public int getScore()
	{
		return score;
	}
	public int compareTo(Bowler b)
	{
		return last.compareTo(b.getLastName());
	}
	public String toString()
	{
		return first+" "+last;
	}
}