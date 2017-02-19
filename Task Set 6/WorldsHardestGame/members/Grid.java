package members;

import engine.*;
import ui.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Grid extends DrawnObject
{
	private static int WALL_BORDER=3;
	
	private int sideLength;
	private int gridPos[][];
	private ArrayList<RectangularCollisionBox> walls, finish;
	private ArrayList<Rectangle2D> drawnWalls, drawnGreenArea, drawnCheckerboard;
	public Grid(int data[][])
	{
		super(0,0);
		gridPos=data;
		sideLength=28;
		walls = new ArrayList<RectangularCollisionBox>();
		finish = new ArrayList<RectangularCollisionBox>();
		drawnWalls = new ArrayList<Rectangle2D>();
		drawnGreenArea = new ArrayList<Rectangle2D>();
		drawnCheckerboard = new ArrayList<Rectangle2D>();
		drawnWalls.add(new Rectangle2D.Double(0,0,GamePanel.VWIDTH,GamePanel.BORDER_WIDTH));
		drawnWalls.add(new Rectangle2D.Double(0,0,GamePanel.BORDER_WIDTH,GamePanel.VHEIGHT));
		drawnWalls.add(new Rectangle2D.Double(0,GamePanel.VHEIGHT-GamePanel.BORDER_WIDTH,GamePanel.VWIDTH,GamePanel.BORDER_WIDTH));
		drawnWalls.add(new Rectangle2D.Double(GamePanel.VWIDTH-GamePanel.BORDER_WIDTH,0,GamePanel.BORDER_WIDTH,GamePanel.VHEIGHT));
		
		for (int x=0; x<gridPos[0].length;x++)
		{
			for (int y=0; y<gridPos.length;y++)
			{
				if (gridPos[y][x]==1)
				{
					walls.add(new RectangularCollisionBox(
						sideLength/2+x*sideLength+GamePanel.BORDER_WIDTH,
						sideLength/2+y*sideLength+GamePanel.BORDER_WIDTH,
						sideLength,
						sideLength));
					drawnWalls.add(new Rectangle2D.Double(
						x*sideLength+GamePanel.BORDER_WIDTH,
						y*sideLength+GamePanel.BORDER_WIDTH,
						sideLength,
						sideLength));
				}
				if (gridPos[y][x]==2)
				{
					finish.add(new RectangularCollisionBox(
						sideLength/2+x*sideLength+GamePanel.BORDER_WIDTH,
						sideLength/2+y*sideLength+GamePanel.BORDER_WIDTH,
						sideLength,
						sideLength));
					drawnGreenArea.add(new Rectangle2D.Double(
						x*sideLength+GamePanel.BORDER_WIDTH,
						y*sideLength+GamePanel.BORDER_WIDTH,
						sideLength,
						sideLength));
				}
				if(gridPos[y][x]==3)
				{
					drawnGreenArea.add(new Rectangle2D.Double(
						x*sideLength+GamePanel.BORDER_WIDTH,
						y*sideLength+GamePanel.BORDER_WIDTH,
						sideLength,
						sideLength));
				}
				if (Math.pow(-1,x+y)==-1)
				{
					drawnCheckerboard.add(new Rectangle2D.Double(
						x*sideLength+GamePanel.BORDER_WIDTH,
						y*sideLength+GamePanel.BORDER_WIDTH,
						sideLength,
						sideLength));
				}
			}
		}
	}
	public boolean collidesWithWall(RectangularCollisionBox cb)
	{
		for (RectangularCollisionBox x:walls)
		{
			if (x.collidesWith(cb))
				return true;
		}
		return false;
	}
	public boolean collidesWithFinish(RectangularCollisionBox cb)
	{
		for (RectangularCollisionBox x:finish)
		{
			if (x.collidesWith(cb))
				return true;
		}
		return false;
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(230,230,255));
		for (Rectangle2D x:drawnCheckerboard)
		{
			g2d.fill(x);
		}
		g2d.setColor(new Color(181,254,180));
		for (Rectangle2D x:drawnGreenArea)
		{
			g2d.fill(x);
		}
		g2d.setColor(Color.BLACK);
		for (Rectangle2D x:drawnWalls)
		{
			g2d.fill(new Rectangle2D.Double(x.getX()-WALL_BORDER,
				x.getY()-WALL_BORDER,
				x.getWidth()+2*WALL_BORDER,
				x.getHeight()+2*WALL_BORDER));
		}
		g2d.setColor(new Color(180,181,254));
		for (Rectangle2D x:drawnWalls)
		{
			g2d.fill(x);
		}
	}
}
