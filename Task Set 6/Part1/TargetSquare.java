import java.awt.*;

public class TargetSquare extends DrawnObject
{
	private double s;
	private SquareCollisionBox hitBox;
	public TargetSquare(double x, double y, double sideLength)
	{
		super(x,y);
		s=sideLength;
		hitBox=new SquareCollisionBox((int)x,(int)y,(int)sideLength);
	}
	
	public SquareCollisionBox hitBox()
	{
		return hitBox;
	}
	public void moveX(double dx)
	{
		super.moveX(dx);
		hitBox.updateX((int)x);
	}
	public void moveY(double dy)
	{
		super.moveY(dy);
		hitBox.updateY((int)y);
	}
	
	public void newRandomLocation(int width, int height, int border)
	{
		int edge=border+(int)(s/2);
		x= Math.random()*(width-2*edge)+edge; 
		y= Math.random()*(height-2*edge)+edge;
		hitBox.updateX((int)x);
		hitBox.updateY((int)y);
	}
	public void paint(Graphics g)
	{
		g.fillRect((int)(x-s/2),(int)(y-s/2),(int)s,(int)s);
	}
}