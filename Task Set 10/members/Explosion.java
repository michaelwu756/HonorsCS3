package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;

public class Explosion extends DrawnObject
{
	private static final int LIFETIME=160;
	private static final int MAX_SIZE=80;
	private static final int PRIORITY=0;
	
	private int life;
	private Ellipse2D shape;
	private boolean dangerous;
	public Explosion(double x, double y)
	{
		this(x,y,true);
	}
	public Explosion (double x, double y, boolean d)
	{
		super(x,y,PRIORITY);
		life = 0;
		dangerous = d;
		shape = new Ellipse2D.Double(x,y,0,0);
	}
	public void animate()
	{
		life++;
		int lifeAdj=life;
		if (life>LIFETIME/2)
			lifeAdj=LIFETIME-life;
		double r = ((double)lifeAdj/(double)LIFETIME)*(double)MAX_SIZE;
		shape = new Ellipse2D.Double(x-r,y-r,2*r,2*r);
	}
	public boolean done()
	{
		return (life>=LIFETIME);
	}
	public Ellipse2D explosionArea()
	{
		return shape;
	}
	public boolean hostile()
	{
		return dangerous;
	}
	public void paint (Graphics g)
	{
		Graphics2D tempG = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.XOR);
		tempG.setComposite(ac);
		tempG.setColor(Color.WHITE);
		tempG.fill(shape);
	}
}