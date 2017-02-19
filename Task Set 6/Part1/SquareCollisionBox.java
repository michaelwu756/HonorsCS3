import java.lang.Math;

public class SquareCollisionBox
{
	private double x,y,s;
	public SquareCollisionBox(int x,int y, int sideLength)
	{
		this.x=(double)x;
		this.y=(double)y;
		s=(double)sideLength;
	}
	public boolean collidesWith(SquareCollisionBox box)
	{
		double rightEdge=x+s/2;
		double leftEdge=x-s/2;
		double boxRightEdge=box.getX()+box.getSideLength()/2;
		double boxLeftEdge=box.getX()-box.getSideLength()/2;
		if (boxLeftEdge>rightEdge || boxRightEdge<leftEdge)
			return false;
		double topEdge=y-s/2;
		double bottomEdge=y+s/2;
		double boxTopEdge=box.getY()-box.getSideLength()/2;
		double boxBottomEdge=box.getY()+box.getSideLength()/2;
		if (boxTopEdge>bottomEdge || boxBottomEdge<topEdge)
			return false;
		return true;
	}
	public boolean collidesWith(CircularCollisionBox circ)
	{
		return circ.collidesWith(this);
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public double getSideLength()
	{
		return s;
	}
	public void updateX(int x)
	{
		this.x=(double)x;
	}
	public void updateY(int y)
	{
		this.y=(double)y;
	}
	public void updateSideLength(int sideLength)
	{
		s=(double)sideLength;
	}
	
}