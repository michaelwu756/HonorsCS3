package members.ball;

import members.*;
import java.awt.geom.*;
import java.awt.*;

public class TokenBall extends Ball
{
	boolean alive;
	int deadTime;
	public TokenBall(double x, double y)
	{
		super (x,y,Ball.DEFAULT_DIAMETER,0);
		alive=true;
		deadTime=-1;
	}
	public boolean alive()
	{
		return alive;
	}
	public void setDead()
	{
		alive=false;
	}
	public void setAlive()
	{
		if (!alive)
			deadTime=0;
	}
	public void move(){}
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		if (alive)
		{
			Ellipse2D shape = new Ellipse2D.Double(x-d/2, y-d/2, d, d);
			g2d.setColor(new Color(0,0,0));
			g2d.fill(shape);
			shape.setFrame(x-d/2+Ball.OUTLINE_BORDER, y-d/2+Ball.OUTLINE_BORDER, d-2*Ball.OUTLINE_BORDER, d-2*Ball.OUTLINE_BORDER);
			g2d.setColor(new Color(255,255,0));
			g2d.fill(shape);
		}
		else if (deadTime!=-1)
		{
			deadTime++;
		}
		if (deadTime>PlayerBox.FADE_LIFE)
		{
			alive=true;
			deadTime=-1;
		}
	}
}