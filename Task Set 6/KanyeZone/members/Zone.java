package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;

public class Zone extends DrawnObject
{
	private static Color INNER_FILL=new Color(186, 155, 238);
	private static Color FILL=new Color(196, 163, 251);
	private static Color OUTLINE=new Color(41, 41, 41);
	private static int OUTLINE_WIDTH=10;
	private static double INIT_DIAMETER=75;
	private static double INIT_INNER_DIAMETER=60;
	private static double GROWTH_RATE=15;
	private static double EXPAND_RATE=0.8;
	
	private boolean inTheZone;
	private double d,innerD, targetD;
	private CircularCollisionBox hitBox;
	public Zone(double x, double y)
	{
		super(x,y);
		d=INIT_DIAMETER;
		targetD=INIT_DIAMETER;
		innerD=INIT_INNER_DIAMETER;
		inTheZone=false;
		hitBox=new CircularCollisionBox((int)x,(int)y,(int)d);
	}
	
	public CircularCollisionBox hitBox()
	{
		return hitBox;
	}
	public void addScore()
	{
		innerD+=GROWTH_RATE;
		if (innerD>d)
		{
			innerD=GROWTH_RATE;
			targetD+=GROWTH_RATE;
		}
	}
	public void grow()
	{
		if (targetD>d)
		{
			d+=EXPAND_RATE;
			hitBox.updateDiameter((int)d);
		}
	}
	public void setInTheZone()
	{
		inTheZone=true;
	}
	public boolean inTheZone()
	{
		return inTheZone;
	}
	public double getDiameter()
	{
		return d;
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(OUTLINE);
		g2d.fill(new Ellipse2D.Double(x-(d+OUTLINE_WIDTH)/2,y-(d+OUTLINE_WIDTH)/2,(d+OUTLINE_WIDTH),(d+OUTLINE_WIDTH)));
		g2d.setColor(FILL);
		g2d.fill(new Ellipse2D.Double(x-d/2,y-d/2,d,d));
		g2d.setColor(INNER_FILL);
		g2d.fill(new Ellipse2D.Double(x-innerD/2,y-innerD/2,innerD,innerD));
	
	}
}