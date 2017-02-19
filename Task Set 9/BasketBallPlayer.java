public class BasketBallPlayer
{
	String first, last, pos;
	public BasketBallPlayer(String f, String l, String p)
	{
		first=f;
		last=l;
		pos=p;
	}
	public String firstName()
	{
		return first;
	}
	public String lastName()
	{
		return last;
	}
	public String position()
	{
		return pos;
	}
	public String toString()
	{
		return first+" "+last+" "+pos;
	}
}