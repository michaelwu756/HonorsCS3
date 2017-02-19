import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.awt.image.*;
import java.applet.*;
import java.util.*;

public class DrawExclamationPoint extends JPanel implements KeyListener, Runnable
{
	int x;
	int y;
	public static int VHEIGHT=340;
	public static int VWIDTH=600;
	public static int BORDER_WIDTH=10;
	public static int FRAME_DELAY = 2;
	private Color bgColor;
	private PlayerCircle player;
	private TargetSquare square;
	private double dx,dy;
	Thread anim;
	Boolean direction[]= new Boolean[4];
	JFrame frame=new JFrame();

	public DrawExclamationPoint()
	{
		x=100;
		y=100;
		dx=0;
		dy=0;
		bgColor = Color.WHITE;
		Arrays.fill(direction,false);
		
		player=new PlayerCircle(100,100,10);
		square=new TargetSquare(123,132,54);
		
		frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(VWIDTH+50,VHEIGHT+50);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		anim = new Thread(this);
		anim.start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(bgColor);
		g2d.fillRect(0,0,VWIDTH,VHEIGHT);

		g2d.setColor(Color.BLACK);
		player.paint(g2d);
		square.paint(g2d);
	}

	public void keyReleased(KeyEvent ke)
	{
		for (int i=0; i<4; i++)
		{
			if(ke.getKeyCode()==KeyEvent.VK_LEFT+i)
			{
				direction[i]=false;
			}
		}
	}

	public void keyPressed(KeyEvent ke)
	{
		for (int i=0; i<4; i++)
		{
			if(ke.getKeyCode()==KeyEvent.VK_LEFT+i)
			{
				direction[i]=true;
			}
		}
	}

	public void keyTyped(KeyEvent ke)
	{
	}
	
	public void updateLocation()
	{
		double speed = 0.01;
		Color colors[]= {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.PINK};
		if (direction[0])
			dx-=speed;
		if (direction[1])
			dy-=speed;
		if (direction[2])
			dx+=speed;
		if (direction[3])
			dy+=speed;
		if (dx>0)
			dx-=speed/2;
		else if (dx<0)
			dx+=speed/2;
		if (dy>0)
			dy-=speed/2;
		else if (dy<0)
			dy+=speed/2;
		if (dx<speed/2 && dx>-speed/2)
			dx=0;
		if (dy<speed/2 && dy>-speed/2)
			dy=0;
		if (player.xValue()+dx<BORDER_WIDTH || player.xValue()+dx>VWIDTH-BORDER_WIDTH)
			dx=0;
		if (player.yValue()+dy<BORDER_WIDTH || player.yValue()+dy>VHEIGHT-BORDER_WIDTH)
			dy=0;
		player.moveX(dx);
		player.moveY(dy);
		if (player.hitBox().collidesWith(square.hitBox()))
		{
			square.newRandomLocation(VWIDTH, VHEIGHT, BORDER_WIDTH);
			Color newColor;
			do{
				newColor=colors[(int)(Math.random()*colors.length)];
			}while (newColor.equals(bgColor));
			bgColor=newColor;
		}
		repaint();
	}
	
	public void run()
	{
		while(true)
		{
			updateLocation();
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
			{}
		}
	}
	
	public static void main(String args[])
	{
		DrawExclamationPoint app=new DrawExclamationPoint();
	}
}