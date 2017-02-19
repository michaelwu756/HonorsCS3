public class Book implements Comparable<Book>
{
	private String firstName,lastName,title;
	private double rating;
	private int numRatings,score,numVoters;
	public Book(String firstName, String lastName, String title, double rating, int numRatings, int score, int numVoters)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.rating = rating;
		this.numRatings = numRatings;
		this.score = score;
		this.numVoters = numVoters;
	}
	private String getLastName()
	{
		return lastName;
	}
	public int compareTo(Book o)
	{
		return lastName.compareToIgnoreCase(o.getLastName());
	}
	public String toString()
	{
		return lastName+", "+firstName+" - "+title+" "+rating+" "+numRatings+" "+score+" "+numVoters;
	}
}