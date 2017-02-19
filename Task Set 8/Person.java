public class Person implements Comparable<Person>
{
	private String first, last;
	private int month, day, year;
	public Person(String f, String l, int m, int d, int y)
	{
		first=f;
		last=l;
		month=m;
		day=d;
		year=y;
	}
	public String getFirstName()
	{
		return first;
	}
	public String getLastName()
	{
		return last;
	}
	public int getMonth()
	{
		return month;
	}
	public int getDay()
	{
		return day;
	}
	public int getYear()
	{
		return year;
	}
	public int compareTo(Person given)
	{
		if(given.getYear()!=year)
			return year-given.getYear();
		if(given.getMonth()!=month)
			return month-given.getMonth();
		if(given.getDay()!=day)
			return day-given.getDay();
		if(!given.getLastName().equals(last))
			return last.compareTo(given.getLastName());
		if(!given.getFirstName().equals(year))
			return first.compareTo(given.getFirstName());
		return 0;
	}
	public String toString()
	{
		return last+", "+first+"\tBirth Year: "+year+"\tBirth Month: "+month+"\tBirth Day: "+day;
	}
}