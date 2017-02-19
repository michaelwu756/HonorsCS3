package engine;

import java.awt.*;

public class DrawnObject implements Comparable<DrawnObject>
{
	protected double x,y;
	protected int drawPriority;
	
	public DrawnObject(double x,double y,int priority)
	{
		this.x=x;
		this.y=y;
		drawPriority=priority;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
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
	public void animate()
	{
	}
	public boolean done()
	{
		return false;
	}
	public int compareTo(DrawnObject o)
	{
		return this.drawPriority-o.drawPriority;
	}
	public void paint(Graphics g)
	{
		g.fillRect((int)(x),(int)(y),20,20);
	}
}