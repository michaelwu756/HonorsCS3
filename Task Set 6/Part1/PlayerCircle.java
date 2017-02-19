import java.awt.*;

public class PlayerCircle extends DrawnObject
{
	private double d;
	private CircularCollisionBox hitBox;
	public PlayerCircle(double x, double y, double diameter)
	{
		super(x,y);
		d=diameter;
		hitBox=new CircularCollisionBox((int)x,(int)y,(int)diameter);
	}
	public CircularCollisionBox hitBox()
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
	public void paint(Graphics g)
	{
		g.fillOval((int)(x-d/2),(int)(y-d/2),(int)d,(int)d);
	}
}