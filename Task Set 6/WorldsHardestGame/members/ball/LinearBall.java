package members.ball;

import members.*;
import engine.*;

public class LinearBall extends Ball
{
	public static int X_AXIS = 1;
	public static int Y_AXIS = 2;
	
	private int axis;
	private Grid grid;
	public LinearBall(double x, double y, Grid grid, int axis)
	{
		super (x,y,Ball.DEFAULT_DIAMETER,Ball.DEFAULT_SPEED);
		this.grid = grid;
		this.axis = axis;
		if (axis<0)
		{
			this.axis*=-1;
			speed*=-1;
		}
	}
	public LinearBall(double x, double y, Grid grid, int axis, double speed)
	{
		super (x,y,Ball.DEFAULT_DIAMETER,speed);
		this.grid = grid;
		this.axis = axis;
		if (axis<0)
		{
			this.axis*=-1;
			speed*=-1;
		}
	}
	
	public void move()
	{
		if (axis == X_AXIS)
		{
			if (grid.collidesWithWall(new RectangularCollisionBox((int)(x+speed),(int)y,(int)d,(int)d)))
				speed=-speed;
			super.moveX(speed);
			hitBox.updateX((int)x);
		}
		else
		{
			if (grid.collidesWithWall(new RectangularCollisionBox((int)x,(int)(y+speed),(int)d,(int)d)))
				speed=-speed;
			super.moveY(speed);
			hitBox.updateY((int)y);
		}
	}
}