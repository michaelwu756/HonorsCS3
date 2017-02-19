package ui;

import engine.*;
import members.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements KeyListener,MouseListener,FocusListener,Runnable
{
	public static int VHEIGHT=500;
	public static int VWIDTH=700;
	public static int BORDER_WIDTH=10;
	public static int LEFT_PANEL_WIDTH=175;
	public static int MONEY_BASE_LIFE = 5;
	private static int FRAME_DELAY = 16;
	private static double ACCEL = 0.024/(Math.PI);
	private static double MAX_SPEED = Math.PI/50;
	private static int MONEY_NUM = 20;
	private Color innerColor, outerColor;
	private Indicator indicator;
	private Kanyes kanye;
	private Player player;
	private Zone theZone;
	private RestartButton restartButton;
	private double dTheta;
	private int score;
	private LinkedList<DrawnObject> objectsToDraw;
	private boolean spacePressed, spaceHeld, focus;
	private Boolean direction[];
	private Thread anim;
	
	public GamePanel()
	{
		super();
		setPreferredSize(new Dimension(VWIDTH,VHEIGHT));
		restartButton = new RestartButton(LEFT_PANEL_WIDTH/2,120);
		direction= new Boolean[4];
		Arrays.fill(direction,false);
		spaceHeld=false;
		spacePressed=false;
		focus=false;
		resetValues();
		
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addFocusListener(this);
	}
	
	public void animate()
	{
		theZone.grow();
		player.wobble();
		for (DrawnObject o:objectsToDraw)
		{
			if (o instanceof Money)
			{
				Money money=(Money)o;
				money.move();
				if (money.life()==1)
				{
					for (int j=0;j<MONEY_BASE_LIFE;j++)
						money.move();
				}
			}
		}
		for (int i=0; i<objectsToDraw.size(); i++)
			{
			if (objectsToDraw.get(i) instanceof Money)
			{
				Money money = (Money)(objectsToDraw.get(i));
				if (!money.active())
				{
					objectsToDraw.remove(i);
					i--;
				}
			}
		}
	}	
	public void updateProjectiles()
	{
		kanye.move();
		if (kanye.hitBox().collidesWith(player.hitBox()))
		{
			score+=5;
			double x=kanye.xValue(),y=kanye.yValue();
			theZone.addScore();
			player.hit(kanye);
			objectsToDraw.remove(kanye);
			objectsToDraw.remove(indicator);
			kanye=new Kanyes(indicator);
			indicator.newRandomLocation();
			objectsToDraw.add(indicator);
			objectsToDraw.add(kanye);
			for (int i=0;i<MONEY_NUM;i++)
			{
				objectsToDraw.add(new Money(x,y));
			}
		}
		if (new CircularCollisionBox((int)kanye.xValue(),(int)kanye.yValue(),1).collidesWith(theZone.hitBox()))
		{
			theZone.setInTheZone();
		}
	}
	public void updatePlayerLocation()
	{		
		double reverse=0;
		if (spacePressed&&!spaceHeld)
		{
			reverse=Math.PI;
			spaceHeld=true;
		}
		if (direction[0])
			dTheta+=ACCEL;
		if (direction[2])
			dTheta-=ACCEL;
		if (dTheta>0)
			dTheta-=ACCEL/2;
		else if (dTheta<0)
			dTheta+=ACCEL/2;
		if (dTheta<ACCEL/2 && dTheta>-ACCEL/2)
			dTheta=0;
		if (dTheta>MAX_SPEED)
			dTheta=MAX_SPEED;
		if (dTheta<-MAX_SPEED)
			dTheta=-MAX_SPEED;
		player.moveTheta(dTheta+reverse);
	}
	public void resetValues()
	{  
		dTheta=0;
		score=0;
		innerColor = Color.DARK_GRAY;
		outerColor = Color.GRAY;
		
		theZone=new Zone((LEFT_PANEL_WIDTH+VWIDTH)/2,VHEIGHT/2);
		player=new Player(theZone,40);
		
		indicator = new Indicator();
		kanye=new Kanyes(indicator);
		indicator.newRandomLocation();
		
		objectsToDraw = new LinkedList<DrawnObject>();
		objectsToDraw.add(theZone);
		objectsToDraw.add(player);
		objectsToDraw.add(restartButton);
		objectsToDraw.add(indicator);
		objectsToDraw.add(kanye);
	}
	
	public void start()
	{
		anim = new Thread(this);
		anim.start();
	}
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		resetValues();
		while(anim==thisThread)
		{
			if (!theZone.inTheZone()&&focus)
			{
				updatePlayerLocation();
				updateProjectiles();
			}
			animate();
			repaint();
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
			{}
		}
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
		if (ke.getKeyCode()==KeyEvent.VK_SPACE)
		{
			spacePressed=false;
			spaceHeld=false;
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
		if (ke.getKeyCode()==KeyEvent.VK_SPACE)
		{
			spacePressed=true;
		}
		if (ke.getKeyCode()==KeyEvent.VK_ENTER)
		{
			start();
		}
	}
	public void keyTyped(KeyEvent ke){}
	
	public void mousePressed(MouseEvent e) 
	{
		if (new CircularCollisionBox(e.getX(),e.getY(),0).collidesWith(restartButton.hitBox()))
			restartButton.setPressed(true);
	}
	public void mouseReleased(MouseEvent e)
	{
		if (restartButton.pressed())
		{
			restartButton.setPressed(false);
			start();
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e)
	{
		requestFocusInWindow();
	}
	
	public void focusGained(FocusEvent e)
	{
		focus=true;
	}
	public void focusLost(FocusEvent e)
	{
		focus=false;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.setClip(0, 0, VWIDTH, VHEIGHT);
		g.setColor(outerColor);
		g.fillRect(0,0,VWIDTH,VHEIGHT);
		g.setColor(innerColor);
		g.fillRect(LEFT_PANEL_WIDTH,0,VWIDTH,VHEIGHT);
		
		for (DrawnObject x:objectsToDraw)
		{
			x.paint(g);
		}
		
		g.setColor(Color.BLACK);
		if (theZone.inTheZone())
			g.drawString("Game Over", VWIDTH/2,VHEIGHT/2);
		g.setColor(Color.WHITE);
		g.drawString("KanyeZone",30,50);
		g.drawString("Score:",40,220);
		g.drawString(String.valueOf(score),45,250);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("by cheesyfluff", 20, 320);
		if (!focus && !theZone.inTheZone())
		{
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0,0,VWIDTH,VHEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("Paused", -30+VWIDTH/2,VHEIGHT/2);
		}
	}
}