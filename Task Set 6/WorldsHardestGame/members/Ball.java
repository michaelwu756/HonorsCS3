package members;

import engine.*;
import java.awt.geom.*;
import java.awt.*;

public abstract class Ball extends DrawnObject
{
	private static Color FILL=new Color(0,0,255);
	private static Color OUTLINE=new Color(0,0,0);
	protected static int OUTLINE_BORDER=3;
	protected static double DEFAULT_SPEED=3.45;
	protected static double DEFAULT_DIAMETER=15;
	
	protected double d,speed;
	protected CircularCollisionBox hitBox;
	public Ball(double x, double y, double diameter, double speed)
	{
		super(x,y);
		d=diameter;
		this.speed=speed;
		hitBox=new CircularCollisionBox((int)x,(int)y,(int)d);
	}
	public CircularCollisionBox hitBox()
	{
		return hitBox;
	}
	public abstract void move();
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		Ellipse2D shape = new Ellipse2D.Double(x-d/2, y-d/2, d, d);
		g2d.setColor(OUTLINE);
		g2d.fill(shape);
		shape.setFrame(x-d/2+OUTLINE_BORDER, y-d/2+OUTLINE_BORDER, d-2*OUTLINE_BORDER, d-2*OUTLINE_BORDER);
		g2d.setColor(FILL);
		g2d.fill(shape);
	}
}