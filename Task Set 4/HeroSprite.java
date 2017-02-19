import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class HeroSprite extends ImageSprite
{
	int dx = 0, dy = 0, shiftx=0;
	public int dir = 0;
	public int jmp = 0;
	int state;
	Grid g;
	static final int STAND = 0;
	static final int JUMP = 1;
	public int gravity = 2;
	static final int ACCELERATION=2;
	BBeanie app;
	static Image im_stand;
	static Image im_up;
	static Image im_down;
	boolean reverse;

	public HeroSprite(BBeanie a, Grid grid, int x, int y)
	{
		super(im_stand, x, y);
		g = grid;
		app = a;
		state = STAND;
		reverse = false;
	}

	static void setStaticImages(Image hs, Image hu, Image hd)
	{
		im_stand = hs;  // default image
		im_up = hu;     // when jumping upward
		im_down = hd;   // when jumping (falling?) downward
	}

	public int getX()
	{
		return (int)x;
	}

	public int width()
	{
		return width;
	}
	
	public void drawSprite(Graphics gr)
	{
		if (reverse)
			gr.drawImage(image,
				(int)x-app.vleft+image.getWidth(app),(int)y,
				(int)x-app.vleft,(int)y+image.getHeight(app),
				0,0,
				image.getWidth(app),image.getHeight(app),
				app);
		else
			gr.drawImage(image,(int)x-app.vleft, (int)y, app);
	}

	public void updateSprite(MovingBlock[] movingBlocks)
	{
		// Handle movement inputs
		if (dir == 0)
		{
			if (dx>0)
			{
				dx-=(ACCELERATION/2);
				if (dx<0)
					dx=0;
			}
			else if (dx<0)
			{
				dx+=(ACCELERATION/2);
				if (dx>0)
					dx=0;
			}
			else
				dx = 0;
		}		
		else
		{
			if (dir==1)
			{
				dx-=ACCELERATION;
				reverse=true;
			}
			else if (dir==2)
			{
				dx+=ACCELERATION;
				reverse=false;
			}
			if (dx>19)
				dx=19;
			if (dx<-19)
				dx=-19;
		}
		for (MovingBlock block:movingBlocks)
		{
			shiftx=0;
			if (onMovingBlock(block))
			{
				shiftx=block.getVelocity();
				dy = block.collisionBox().y-collisionBox().y-collisionBox().height;
				state = STAND;
				setImage(im_stand);
			}
		}
		if ((state == STAND) && (jmp == 1))
		{
			dy = -14;
			state = JUMP;
			setImage(im_up);
			jmp = 0;
		}
		// Then do the moving
		updatePosition();
	}

	public Rectangle collisionBox()
	{
		// We get better gameplay with a 90% collison box
		return new Rectangle((int)(x+0.05*width), (int)(y+0.05*height),
					(int)(0.9*width), (int)(0.9*height));
	}

	public boolean onMovingBlock(MovingBlock block)
	{
		Rectangle r=collisionBox();
		Rectangle blockR=block.collisionBox();
		int rbottom = r.y+r.height+dy;
		int topEdge = blockR.y;
		int bottomEdge = blockR.y+blockR.height;
		if ((rbottom) < topEdge  || rbottom-dy >=topEdge || dy<0)
			return false;
		int rRight=r.x+r.width+dx;
		int rLeft=r.x+dx;
		int blockRight=blockR.x+blockR.width;
		int blockLeft=blockR.x;
		if ((rRight<=blockRight && rRight>=blockLeft)||(rLeft<=blockRight && rLeft>=blockLeft))
			return true;
		return false;
	}
	
	public void powerUp()
	{
		gravity=1;
	}
	
	public void updatePosition()
	{
		dx+=shiftx;
		// First handle sideways movement
		if (dx > 0)
		{
			dx = g.moveRight(collisionBox(), dx);
		}
		else if (dx < 0)
		{
			dx = -g.moveLeft(collisionBox(), -dx);
		}
		if (dx != 0)
			x += dx;
		// Then look at vertical movement (if any)
		if (state == JUMP)
		{
			if (dy > 0)
			{
				dy = g.moveDown(collisionBox(), dy);
				setImage(im_down);
			}
			else if (dy < 0)
			{
				dy = -g.moveUp(collisionBox(), -dy);
			}
			if (dy != 0)
				y += dy;
			// Let gravity act (as acceleration)
			dy += gravity;
			//
			// Fix that problem of the game crashing
			// after long falls - just impose a terminal
			// velocity (less than block size in the grid)
			if (dy > 19)
				dy = 19;
			// If we've landed on something, change state
			if (g.onGround(collisionBox()))
			{
				dy = 0;
				state = STAND;
				setImage(im_stand);
			}
			else if (g.atTop(collisionBox()))
			{
				dy = 1;
			}
		}
		else
			if (!g.onGround(collisionBox()))
				state = JUMP;
		dx-=shiftx;
	}
}
