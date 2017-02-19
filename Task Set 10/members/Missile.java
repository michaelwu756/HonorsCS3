package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;

public class Missile extends DrawnObject
{
	private static final int PRIORITY=1;
	private static final double SPEED = 6;
	
	private Point2D start, finish;
	private Line2D current;
	private double xScl, yScl, speed;
	private boolean enemy, exploded;
	public Missile(Point2D s, Point2D f)
	{
		this(s,f,false,SPEED);
	}
	public Missile(Point2D s, Point2D f, boolean e)
	{
		this(s,f,e,SPEED);
	}
	public Missile(Point2D s, Point2D f, boolean e, double sp)
	{
		super(s.getX(),s.getY(),PRIORITY);
		start = s;
		finish = f;
		enemy = e;
		speed = sp;
		exploded = false;
		current = new Line2D.Double(start,start);
		xScl = (finish.getX()-start.getX())/start.distance(finish);
		yScl = (finish.getY()-start.getY())/start.distance(finish);
	}
	public void animate()
	{
		moveX(xScl*speed);
		moveY(yScl*speed);
		if (done() && !exploded)
		{
			x=finish.getX();
			y=finish.getY();
		}
		current.setLine(start.getX(),start.getY(),x,y);
	}
	public boolean done()
	{
		if (exploded)
			return true;
		return (start.distance(new Point2D.Double(x,y))>=start.distance(finish));
	}
	public boolean exploded()
	{
		return exploded;
	}	
	public boolean hostile()
	{
		return enemy;
	}
	public void explode()
	{
		exploded=true;
	}
	public void paint(Graphics g)
	{
		Graphics2D tempG = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.XOR);
		tempG.setComposite(ac);
		tempG.setColor(Color.WHITE);
		tempG.draw(current);
		ac = AlphaComposite.getInstance(AlphaComposite.SRC);
		tempG.setComposite(ac);
		tempG.setColor(Color.RED);
		if (!enemy)
			tempG.fillRect((int)finish.getX()-3,(int)finish.getY()-3,6,6);
	}
}