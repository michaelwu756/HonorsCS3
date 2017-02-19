package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Player extends DrawnObject
{
	private static Color FILL=new Color(75, 120, 255);
	private static Color OUTLINE=new Color(67, 161, 255);
	private static double MARGIN=10;
	private static double TRAIL_DIAMETER=5;
	private static double TRAIL_MARGIN=5;
	private static int TRAIL_LENGTH=15;
	private static int TRAIL_RED=255;
	private static int TRAIL_GREEN=255;
	private static int TRAIL_BLUE=255;
	private static int TRAIL_ALPHA=140;
	private static double WOBBLE_VELOCITY = 7;
	private static double DAMPENING_FORCE=0.3;
	private static double RESTORING_FORCE_RATIO=0.5;
	private double d,theta,dtheta,fx,fy,dx,dy;
	private Zone attachedZone;
	private CircularCollisionBox hitBox;
	private ArrayList<Ellipse2D> trailShapes;
	
	public Player(Zone theZone, double diameter)
	{
		super(theZone.xValue()+(theZone.getDiameter()+MARGIN)*Math.cos(Math.PI/2),theZone.yValue()-(theZone.getDiameter()+MARGIN)*Math.sin(Math.PI/2));
		d=diameter;
		theta=Math.PI/2;
		dtheta=0;
		dx=0;
		dy=0;
		fx=0;
		fy=0;
		attachedZone=theZone;
		trailShapes=new ArrayList<Ellipse2D>();
		hitBox=new CircularCollisionBox((int)x,(int)y,(int)diameter);
	}
	public CircularCollisionBox hitBox()
	{
		return hitBox;
	}
	public void moveTheta(double dTheta)
	{
		this.dtheta=dtheta;
		theta=(theta+dTheta)%(2*Math.PI);
		x=attachedZone.xValue()+(attachedZone.getDiameter()/2+d/2+MARGIN)*Math.cos(theta);
		y=attachedZone.yValue()-(attachedZone.getDiameter()/2+d/2+MARGIN)*Math.sin(theta);
	}
	public double getTheta()
	{
		return theta;
	}
	public void wobble()
	{
		dx+=fx;
		dy+=fy;
		fx-=dx*RESTORING_FORCE_RATIO;
		fy-=dy*RESTORING_FORCE_RATIO;
		if (fx>0)
		{
			fx-=DAMPENING_FORCE;
			if (fx<0)
				fx=0;
		}
		else
		{
			fx+=DAMPENING_FORCE;
			if (fx>0)
				fx=0;
		}
		if (fy>0)
		{
			fy-=DAMPENING_FORCE;
			if (fy<0)
				fy=0;
		}
		else
		{
			fy+=DAMPENING_FORCE;
			if (fy>0)
				fy=0;
		}
		hitBox.updateX((int)(x+dx));
		hitBox.updateY((int)(y+dy));
	}
	public void hit(Kanyes kanye)
	{
		fx=x-kanye.xValue();
		fy=y-kanye.yValue();
		fx/=Math.sqrt(fx*fx+fy*fy);
		fy/=Math.sqrt(fx*fx+fy*fy);
		fx*=WOBBLE_VELOCITY;
		fy*=WOBBLE_VELOCITY;
	}
	public void paint(Graphics gr)
	{
		Graphics2D g2d=(Graphics2D)gr;
		Ellipse2D shape = new Ellipse2D.Double(x-d/2+dx, y-d/2+dy, d, d);
		trailShapes.add(0,shape);
		for (int i=TRAIL_LENGTH;i<trailShapes.size();i++)
		{
			trailShapes.remove(i);
		}
		double r,g,b;
		r=FILL.getRed();
		g=FILL.getGreen();
		b=FILL.getBlue();
		for (int i=trailShapes.size()-1;i>=0;i--)
		{
			Ellipse2D currentShape=trailShapes.get(i);
			r+=(TRAIL_RED-FILL.getRed())*(1.0/TRAIL_LENGTH);
			g+=(TRAIL_GREEN-FILL.getGreen())*(1.0/TRAIL_LENGTH);
			b+=(TRAIL_BLUE-FILL.getBlue())*(1.0/TRAIL_LENGTH);
			g2d.setColor(new Color((int)r,(int)g,(int)b,(int)(TRAIL_ALPHA-(255-TRAIL_ALPHA)*(double)i/TRAIL_LENGTH)));
			currentShape.setFrame(
				currentShape.getX()+(d-TRAIL_DIAMETER)*(0.5/TRAIL_LENGTH),
				currentShape.getY()+(d-TRAIL_DIAMETER)*(0.5/TRAIL_LENGTH),
				currentShape.getWidth()-(d-TRAIL_DIAMETER)*(1.0/TRAIL_LENGTH),
				currentShape.getHeight()-(d-TRAIL_DIAMETER)*(1.0/TRAIL_LENGTH));
			if (i==1)
			{
				currentShape.setFrame(
					currentShape.getX()-TRAIL_MARGIN/2+(d-TRAIL_DIAMETER)*(0.5/TRAIL_LENGTH),
					currentShape.getY()-TRAIL_MARGIN/2+(d-TRAIL_DIAMETER)*(0.5/TRAIL_LENGTH),
					currentShape.getWidth()+TRAIL_MARGIN-(d-TRAIL_DIAMETER)*(1.0/TRAIL_LENGTH),
					currentShape.getHeight()+TRAIL_MARGIN-(d-TRAIL_DIAMETER)*(1.0/TRAIL_LENGTH));
			}
			trailShapes.set(i,currentShape);
			g2d.fill(trailShapes.get(i));
		}
		g2d.setColor(FILL);
		g2d.fill(shape);
		g2d.setColor(OUTLINE);
		g2d.draw(shape);
	}
}