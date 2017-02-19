import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.awt.image.*;
import java.applet.*;
import java.util.*;

public class Eskiv extends Applet implements KeyListener,MouseListener, Runnable
{
	public static int VHEIGHT=340;
	public static int VWIDTH=600;
	public static int BORDER_WIDTH=10;
	public static int LEFT_PANEL_WIDTH=175;
	public static int FRAME_DELAY = 2;
	private Color innerColor, outerColor;
	private Player player;
	private Target square;
	private RestartButton restartButton;
	private double dx,dy;
	private int score;
	private LinkedList<DrawnObject> objectsToDraw;
	private LinkedList<Projectile> projectiles;
	private Thread anim;
	private Boolean direction[];
	private BufferedImage backbuffer;
	private Graphics backg;

	public Eskiv()
	{
		init();
		start();
	}

	public void placeRandomProjectile()
	{
		double x,y;
		int edge = BORDER_WIDTH+(int)(Projectile.DIAMETER/2);
		do
		{
			x= Math.random()*(VWIDTH-2*edge-LEFT_PANEL_WIDTH)+edge+LEFT_PANEL_WIDTH; 
			y= Math.random()*(VHEIGHT-2*edge)+edge;
		}while (player.hitBox().collidesWith(new CircularCollisionBox((int)x,(int)y,(int)(player.hitBox().getDiameter())*6)));
		projectiles.add(new Projectile(x,y));
		objectsToDraw.add(projectiles.getLast());
	}
	public void updateProjectiles()
	{
		for (Projectile x:projectiles)
		{
			x.move();
			if (x.hitBox().collidesWith(player.hitBox()))
				player.setDead();
		}
	}
	public void updatePlayerLocation()
	{
		double speed = 0.01;
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
		double r = player.hitBox().getDiameter()/2;
		if (player.xValue()+dx<BORDER_WIDTH+LEFT_PANEL_WIDTH+r || player.xValue()+dx>VWIDTH-BORDER_WIDTH-r)
			dx=0;
		if (player.yValue()+dy<BORDER_WIDTH+r || player.yValue()+dy>VHEIGHT-BORDER_WIDTH-r)
			dy=0;
		player.moveX(dx);
		player.moveY(dy);
		if (player.hitBox().collidesWith(square.hitBox()))
		{
			score+=5;
			square.newRandomLocation();
			placeRandomProjectile();
		}
	}
	public void resetValues()
	{
		dx=0;
		dy=0;
		score=0;
		innerColor = Color.LIGHT_GRAY;
		outerColor = Color.GRAY;
		
		player=new Player(VWIDTH/2,VHEIGHT/2,30);
		square=new Target(0,0,30);
		square.newRandomLocation();
		
		projectiles = new LinkedList<Projectile>();
		
		objectsToDraw = new LinkedList<DrawnObject>();
		objectsToDraw.add(player);
		objectsToDraw.add(square);
		objectsToDraw.add(restartButton);
		placeRandomProjectile();
	}
	
	
	public void init()
	{
		addKeyListener(this);
		addMouseListener(this);
		setVisible(true);
		backbuffer = new BufferedImage(VWIDTH,VHEIGHT, BufferedImage.TYPE_INT_BGR);
		backg = backbuffer.getGraphics();
		setSize(new Dimension(VWIDTH+15,VHEIGHT+40));
		
		restartButton = new RestartButton(LEFT_PANEL_WIDTH/2,120);
		direction= new Boolean[4];
		Arrays.fill(direction,false);
		resetValues();
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
		while(!player.isDead() && anim==thisThread)
		{
			updatePlayerLocation();
			updateProjectiles();
			repaint();
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
			{}
		}
	}
	public void update( Graphics g )
	{
		paint(backg);
		g.drawImage( backbuffer, 0, 0, this );
	}
	public void paint(Graphics g)
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
		if (player.isDead())
			g.drawString("Game Over", VWIDTH/2,VHEIGHT/2);
		g.setColor(Color.WHITE);
		g.drawString("Eskiv",30,50);
		g.drawString("Score:",40,220);
		g.drawString(String.valueOf(score),45,250);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("by cheesyfluff", 20, 320);
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
		if (ke.getKeyCode()==KeyEvent.VK_SPACE)// && player.isDead())
		{
			start(); 
		}
	}
	public void keyTyped(KeyEvent ke){}
	
	
	public void mousePressed(MouseEvent e) 
	{
		if (new CircularCollisionBox(e.getX(),e.getY(),0).collidesWith(restartButton.hitBox()))
			restartButton.setPressed(true);
		repaint();
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
	public void mouseClicked(MouseEvent e){}
	
	
	public static void main(String args[])
	{
		JFrame frame=new JFrame();
		Eskiv app=new Eskiv();
		frame.add(app);
		frame.addKeyListener(app);
		frame.addMouseListener(app);
		frame.setSize(VWIDTH+15,VHEIGHT+40);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}