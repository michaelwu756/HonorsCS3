package members.ball;

import members.*;

public class CircularBall extends Ball
{
	private double centerX, centerY, r, angle;
	public CircularBall(double x, double y, double radius, double angle, double speed)
	{
		super(x+Math.cos(angle)*radius,y-Math.sin(angle)*radius,Ball.DEFAULT_DIAMETER,speed);
		r=radius;
		centerX=x;
		centerY=y;
		this.angle=angle;
	}
	public void move()
	{
		angle+=speed;
		x=centerX+Math.cos(angle)*r;
		y=centerY-Math.sin(angle)*r;
		hitBox.updateX((int)x);
		hitBox.updateY((int)y);
	}
}