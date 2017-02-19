import java.lang.Math;

public class RectangularCollisionBox
{
	private double x,y,w,h;
	public RectangularCollisionBox(int x,int y, int width, int height)
	{
		this.x=(double)x;
		this.y=(double)y;
		w=(double)width;
		h=(double)height;
	}
	public boolean collidesWith(RectangularCollisionBox box)
	{
		double rightEdge=x+w/2;
		double leftEdge=x-w/2;
		double boxRightEdge=box.getX()+box.getWidth()/2;
		double boxLeftEdge=box.getX()-box.getWidth()/2;
		if (boxLeftEdge>rightEdge || boxRightEdge<leftEdge)
			return false;
		double topEdge=y-h/2;
		double bottomEdge=y+h/2;
		double boxTopEdge=box.getY()-box.getHeight()/2;
		double boxBottomEdge=box.getY()+box.getHeight()/2;
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
	public double getWidth()
	{
		return w;
	}
	public double getHeight()
	{
		return h;
	}
	public void updateX(int x)
	{
		this.x=(double)x;
	}
	public void updateY(int y)
	{
		this.y=(double)y;
	}
	public void updateW(int width)
	{
		w=(double)width;
	}
	public void updateH(int height)
	{
		h=(double)height;
	}
}