package engine;

import java.awt.*;

public class DrawnObject
{
	protected double x,y;
	
	public DrawnObject(double x,double y)
	{
		this.x=x;
		this.y=y;
	}
	public double xValue()
	{
		return x;
	}
	public double yValue()
	{
		return y;
	}
	public void moveX(double dx)
	{
		x+=dx;
	}
	public void moveY(double dy)
	{
		y+=dy;
	}
	public void paint(Graphics g)
	{
		g.fillRect((int)(x),(int)(y),20,20);
	}
}