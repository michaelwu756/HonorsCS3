package ui;

import members.*;
import engine.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.awt.image.*;

public class GamePanel extends JPanel implements KeyListener,MouseListener,MouseMotionListener,FocusListener,Runnable
{
	public static final int VHEIGHT=412;
	public static final int VWIDTH=636;
	private static final int FRAME_DELAY = 16;
	private static final int MIN_CURSOR_HEIGHT = 100;
	private static final double GEN_PROB = 0.03;
	private static final int RESTOCK_FRAMES = 210;
	
	private ConcurrentLinkedQueue<Explosion> explosionQueue;
	private ConcurrentLinkedQueue<Missile> missileQueue;
	
	private CursorTarget cursor;
	private Turret turrets[];
	private City cities[];
	private boolean fired[];
	
	private PriorityBlockingQueue<DrawnObject> objectsToDraw;
	private boolean focus, lost;
	private int interval, score;
	private Thread anim;
	public GamePanel()
	{
		super();
		setPreferredSize(new Dimension(VWIDTH,VHEIGHT));
		objectsToDraw = new PriorityBlockingQueue<DrawnObject>();
		explosionQueue = new ConcurrentLinkedQueue<Explosion>();
		missileQueue = new ConcurrentLinkedQueue<Missile>();
		cursor = new CursorTarget();
		turrets = new Turret[3];
		cities = new City[6];
		fired = new boolean[3];
		focus=false;
		
		resetValues();
		
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addFocusListener(this);
	}
	
	public void checkCollisions()
	{
		HashSet<Missile> mis = new HashSet<Missile>();
		HashSet<Explosion> ex = new HashSet<Explosion>();
		HashSet<Explosion> missedEx = new HashSet<Explosion>();
		for (DrawnObject x:objectsToDraw)
		{
			if (x instanceof Missile)
			{
				Missile m = (Missile)x;
				if (m.hostile())
				{
					mis.add(m);
				}
			}
			else if (x instanceof Explosion)
			{
				Explosion e = (Explosion)x;
				if (e.hostile())
				{
					ex.add(e);
				}
				else missedEx.add(e);
			}
		}
		for (Missile m:mis)
		{
			for (Explosion e:ex)
			{
				if (e.explosionArea().contains(m.getX(),m.getY()))
				{
					m.explode();
					score++;
				}
			}
		}
		for (Explosion e:missedEx)
		{
			for (City c:cities)
			{
				if (e.getX()==c.getX())
					c.destroy();
			}
			for(Turret t:turrets)
			{
				if (e.getX()==t.getX())
					while(t.shoot()){}
			}
		}
	}	
	public void updateProjectiles()
	{
		for (DrawnObject x:objectsToDraw)
		{
				x.animate();
		}
		Iterator<DrawnObject> it = objectsToDraw.iterator();
		while (it.hasNext())
		{
			DrawnObject x = it.next();
			if (x.done())
			{
				if (x instanceof Missile)
				{
					Missile m = (Missile)x;
					if (m.hostile() && !m.exploded())
						explosionQueue.add(new Explosion(m.getX(),m.getY(),false));
					else explosionQueue.add(new Explosion(m.getX(),m.getY()));
				}
				it.remove();
			}
		}
	}
	public void pushActionQueue()
	{
		while (explosionQueue.peek()!= null)
			objectsToDraw.add(explosionQueue.poll());
		while (missileQueue.peek()!=null)
			objectsToDraw.add(missileQueue.poll());
	}
	public void generateMissiles()
	{
		if (Math.random()<GEN_PROB)
		{
			objectsToDraw.add(new Missile(
				new Point2D.Double(Math.random()*VWIDTH,0),
				new Point2D.Double((int)(Math.random()*9)*VWIDTH*3.0f/32.0f+(double)VWIDTH/8,VHEIGHT-MIN_CURSOR_HEIGHT/2),
				true, 1.5));
		}
	}
	public void restock()
	{
		interval++;
		if (interval>RESTOCK_FRAMES)
		{
			if (!lost)
				for (Turret t: turrets)
					t.addAmmo();
			interval=0;
		}
	}
	public void checkLoss()
	{
		lost=true;
		for (City c:cities)
		{
			if (!c.done())
				lost=false;
		}
	}
	
	public void resetValues()
	{
		Arrays.fill(fired, false);
		score= 0;
		interval = 0;
		lost = false;
		turrets[0]=new Turret((double)VWIDTH/8,VHEIGHT-MIN_CURSOR_HEIGHT/2);
		turrets[1]=new Turret((double)VWIDTH/2,VHEIGHT-MIN_CURSOR_HEIGHT/2);
		turrets[2]=new Turret(7*(double)VWIDTH/8,VHEIGHT-MIN_CURSOR_HEIGHT/2);
		cities[0]=new City(1*VWIDTH*3.0f/32.0f+(double)VWIDTH/8, VHEIGHT-MIN_CURSOR_HEIGHT/2);
		cities[1]=new City(2*VWIDTH*3.0f/32.0f+(double)VWIDTH/8, VHEIGHT-MIN_CURSOR_HEIGHT/2);
		cities[2]=new City(3*VWIDTH*3.0f/32.0f+(double)VWIDTH/8, VHEIGHT-MIN_CURSOR_HEIGHT/2);
		cities[3]=new City(5*VWIDTH*3.0f/32.0f+(double)VWIDTH/8, VHEIGHT-MIN_CURSOR_HEIGHT/2);
		cities[4]=new City(6*VWIDTH*3.0f/32.0f+(double)VWIDTH/8, VHEIGHT-MIN_CURSOR_HEIGHT/2);
		cities[5]=new City(7*VWIDTH*3.0f/32.0f+(double)VWIDTH/8, VHEIGHT-MIN_CURSOR_HEIGHT/2);
		objectsToDraw.clear();
		explosionQueue.clear();
		missileQueue.clear();
		objectsToDraw.add(cursor);
		for (Turret x:turrets)
			objectsToDraw.add(x);
		for (City x:cities)
			objectsToDraw.add(x);
	}
	
	public void start()
	{
		anim = new Thread(this);
		resetValues();
		anim.start();
	}
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		while(anim==thisThread)
		{
			if (focus)
			{
				restock();
				updateProjectiles();
				checkCollisions();
				pushActionQueue();
				generateMissiles();
				checkLoss();
			}
			repaint();
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
			{}
		}
	}

	public boolean fireTurret(Turret t)
	{
		Point2D pCursor = new Point2D.Double(cursor.getX(),cursor.getY());
		if (t.shoot())
		{
			missileQueue.add(new Missile(new Point2D.Double(t.getX(),t.getY()),pCursor));
			return true;
		}
		return false;
	}
	public void keyReleased(KeyEvent ke)
	{
		if (ke.getKeyCode()==KeyEvent.VK_LEFT && fired[0])
		{
			fired[0]=false;
		}
		else if (ke.getKeyCode()==KeyEvent.VK_UP && fired[1])
		{
			fired[1]=false;
		}
		else if (ke.getKeyCode()==KeyEvent.VK_RIGHT && fired[2])
		{
			fired[2]=false;
		}
	}
	public void keyPressed(KeyEvent ke)
	{
		if (ke.getKeyCode()==KeyEvent.VK_LEFT && !fired[0])
		{
			fireTurret(turrets[0]);
			fired[0]=true;
		}
		else if (ke.getKeyCode()==KeyEvent.VK_UP && !fired[1])
		{
			fireTurret(turrets[1]);
			fired[1]=true;
		}
		else if (ke.getKeyCode()==KeyEvent.VK_RIGHT && !fired[2])
		{
			fireTurret(turrets[2]);
			fired[2]=true;
		}
		else if (ke.getKeyCode()== KeyEvent.VK_ENTER)
		{
			start();
		}
	}
	public void keyTyped(KeyEvent ke){}
	
	public void mousePressed(MouseEvent e)
	{
		Point2D closest,pCursor;
		pCursor=new Point2D.Double(cursor.getX(),cursor.getY());
		PriorityQueue<Turret> priorityTurrets=new PriorityQueue<Turret>(3,new Comparator<Turret>()
		{
			public int compare(Turret o1, Turret o2)
			{
				Point2D p1,p2;
				p1=new Point2D.Double(o1.getX(),o1.getY());
				p2=new Point2D.Double(o2.getX(),o2.getY());
				if (pCursor.distanceSq(p1)-pCursor.distanceSq(p2)<0)
					return -1;
				else if (pCursor.distanceSq(p1)-pCursor.distanceSq(p2)>0)
					return 1;
				return 0;
			}
			public boolean equals(Object obj)
			{
				return false;
			}
		});
		for (Turret t:turrets)
		{
			priorityTurrets.add(t);
		}
		while (priorityTurrets.peek()!=null && !lost)
		{
			if (fireTurret(priorityTurrets.poll()))
			{
				break;
			}
		}
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e)
	{
		requestFocusInWindow();
	}

	public void mouseDragged(MouseEvent e)
	{
		mouseMoved(e);
	}
	public void mouseMoved(MouseEvent e)
	{
		if (e.getY()<VHEIGHT-MIN_CURSOR_HEIGHT)
			cursor.setPos(e.getX(),e.getY());
		else
			cursor.setPos(e.getX(),VHEIGHT-MIN_CURSOR_HEIGHT);
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
		BufferedImage bi = new BufferedImage(VWIDTH, VHEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D tempG = (Graphics2D)bi.getGraphics();
		tempG.setClip(0, 0, VWIDTH, VHEIGHT);
		tempG.setColor(Color.WHITE);
		DrawnObject[] a = objectsToDraw.toArray(new DrawnObject[0]);
		Arrays.sort(a);
		for (DrawnObject x:a)
		{
				x.paint(tempG);
		}
		g.setClip(0, 0, VWIDTH, VHEIGHT);
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, VWIDTH, VHEIGHT);
		g.drawImage(bi,0,0,null);
		FontMetrics met = g.getFontMetrics();
		int lineHeight = met.getAscent()+met.getDescent();
		if (!lost)
		{
			g.setColor(Color.WHITE);
			drawCenteredString("Score: "+score,VWIDTH/2, lineHeight/2,g);
		}
		if (lost)
		{
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0,0,VWIDTH,VHEIGHT);
			g.setColor(Color.WHITE);
			drawCenteredString("You Lose",VWIDTH/2,VHEIGHT/2-lineHeight,g);
			drawCenteredString("Press Enter to Play Again",VWIDTH/2,VHEIGHT/2,g);
			drawCenteredString("Score: "+score,VWIDTH/2, VHEIGHT/2+lineHeight,g);
		}
		else if (!focus)
		{
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0,0,VWIDTH,VHEIGHT);
			g.setColor(Color.WHITE);		
			drawCenteredString("Paused",VWIDTH/2,VHEIGHT/2,g);
		}
	}
	public void drawCenteredString(String s, int xi, int yi, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int xf = xi-(fm.stringWidth(s))/ 2;
		int yf = yi+(fm.getAscent()- fm.getDescent())/2;
		g.drawString(s, xf, yf);
	}
}