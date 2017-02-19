import java.lang.Math;

public class CircularCollisionBox
{
	private double x,y,d;
	public CircularCollisionBox(int x,int y, int diameter)
	{
		this.x=(double)x;
		this.y=(double)y;
		d=(double)diameter;
	}
	public boolean collidesWith(CircularCollisionBox circ)
	{
		if ((x-circ.getX())*(x-circ.getX())+(y-circ.getY())*(y-circ.getY()) <= (d/2+circ.getDiameter()/2)*(d/2+circ.getDiameter()/2))
			return true;
		return false;
	}
	public boolean collidesWith(RectangularCollisionBox box)
	{
		double distanceX = Math.abs(x - box.getX());
		double distanceY = Math.abs(y - box.getY());
		double r=d/2;
		
		if (distanceX > (box.getWidth()/2 + r)) { return false; }
		if (distanceY > (box.getHeight()/2 + r)) { return false; }

		if (distanceX <= (box.getWidth()/2)) { return true; } 
		if (distanceY <= (box.getHeight()/2)) { return true; }

		double cornerDistanceSq = (distanceX - box.getWidth()/2)*(distanceX - box.getWidth()/2) +
							 (distanceY - box.getHeight()/2)*(distanceY - box.getHeight()/2);

		return (cornerDistanceSq <= (r*r));
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public double getDiameter()
	{
		return d;
	}
	public void updateX(int x)
	{
		this.x=(double)x;
	}
	public void updateY(int y)
	{
		this.y=(double)y;
	}
	public void updateDiameter(int diameter)
	{
		d=(double)diameter;
	}
	
}