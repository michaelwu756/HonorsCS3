import java.awt.*;
import java.awt.geom.*;

public class Player extends DrawnObject
{
	private static Color FILL=Color.BLACK;
	private static Color OUTLINE=Color.BLACK;
	private double d;
	private boolean dead;
	private CircularCollisionBox hitBox;
	
	public Player(double x, double y, double diameter)
	{
		super(x,y);
		d=diameter;
		dead = false;
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
	public boolean isDead()
	{
		return dead;
	}
	public void setDead()
	{
		dead=true;
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		if(!dead)
		{
			Ellipse2D shape = new Ellipse2D.Double(x-d/2, y-d/2, d, d);
			g2d.setColor(FILL);
			g2d.fill(shape);
			g2d.setColor(OUTLINE);
			g2d.draw(shape);
		}
	}
}