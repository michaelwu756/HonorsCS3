import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ButtonAndLayout extends JFrame implements ActionListener
{
	private String[] labels;
	private JButton[] buttonArray;
	private JPanel buttonPanel;
	private JTextField textField;
	private int axis;
	private String layoutMode,initTextField;
	private Color textColor, bgColor;
	private Font font;
	
	private static String FONT_NAME = "Comic Sans MS";
	private static int FONT_SIZE = 20;
	
	public ButtonAndLayout()
	{
		super();
		buttonArray=new JButton[11];
		labels = new String[11];
		labels[0]="Font 1";
		labels[1]="Font 2";
		labels[2]="Bottom";
		labels[3]="Left";
		labels[4]="Top";
		labels[5]="Right";
		labels[6]="Color 1";
		labels[7]="Color 2";
		labels[8]="BgColor 1";
		labels[9]="BgColor 2";
		labels[10]="Reset";
		layoutMode=BorderLayout.SOUTH;
		axis=BoxLayout.LINE_AXIS;
		initTextField="";
		font= new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
		bgColor=Color.WHITE;
		textColor=Color.BLACK;
		reDrawPanels();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void reDrawPanels()
	{
		Container container=getContentPane();
		container.removeAll();
		
		buttonPanel=new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, axis));
		
		for(int x=0;x<buttonArray.length;x++)
		{
			buttonArray[x]=new JButton(labels[x]);
			buttonPanel.add(buttonArray[x]);
			buttonArray[x].addActionListener(this);
			buttonArray[x].setFont(font);
		}
		if (layoutMode.equals(BorderLayout.EAST) || layoutMode.equals(BorderLayout.WEST))
		{
			for (JButton button:buttonArray)
			{
				button.setAlignmentX(Component.CENTER_ALIGNMENT);
			}
		}
		buttonArray[0].setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
		buttonArray[1].setFont(new Font(FONT_NAME, Font.ITALIC, FONT_SIZE));
		buttonArray[6].setForeground(Color.GREEN);
		buttonArray[7].setForeground(Color.RED);
		buttonArray[8].setBackground(Color.PINK);
		buttonArray[9].setBackground(Color.CYAN);
		
		textField=new JTextField(initTextField);
		textField.setPreferredSize(new Dimension((int)textField.getPreferredSize().getWidth(),60));
		textField.setFont(font);
		textField.setForeground(textColor);
		textField.setBackground(bgColor);
		
		container.setLayout(new BorderLayout());
		container.add(textField, BorderLayout.CENTER);
		container.add(buttonPanel, layoutMode);
		SwingUtilities.updateComponentTreeUI(this);
		
		setVisible(true);
		if (layoutMode.equals(BorderLayout.SOUTH) || layoutMode.equals(BorderLayout.NORTH))
			setMinimumSize(new Dimension(
				(int)container.getPreferredSize().getWidth() + ((int)getSize().getWidth()-(int)getContentPane().getSize().getWidth()),
				(int)container.getPreferredSize().getHeight() + (int)buttonPanel.getPreferredSize().getHeight()));
		else
			setMinimumSize(new Dimension(
				(int)container.getPreferredSize().getWidth() + (int)buttonPanel.getPreferredSize().getHeight(),
				(int)container.getPreferredSize().getHeight() + ((int)getSize().getHeight()-(int)getContentPane().getSize().getHeight())));
		setSize(getMinimumSize());
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton buttonPressed = (JButton)(e.getSource());
		for (int x=0;x<buttonArray.length;x++)
		{
			if (buttonPressed.getText().equals(labels[x]))
			{
				switch(x)
				{
					case 0:
						fontOne();
					break;
					case 1:
						fontTwo();
					break;
					case 2:
						bottom();
					break;
					case 3:
						left();
					break;
					case 4:
						top();
					break;
					case 5:
						right();
					break;
					case 6:
						colorOne();
					break;
					case 7:
						colorTwo();
					break;
					case 8:
						bgColorOne();
					break;
					case 9:
						bgColorTwo();
					break;
					case 10:
						reset();
					break;
				}
				break;
			}
		}
		initTextField=textField.getText();
		reDrawPanels();
	}
	
	public void fontOne()
	{
		font= new Font("Comic Sans MS", Font.BOLD, FONT_SIZE);
	}
	public void fontTwo()
	{
		font= new Font(FONT_NAME, Font.ITALIC, FONT_SIZE);
	}
	public void bottom()
	{
		layoutMode=BorderLayout.SOUTH;
		axis=BoxLayout.LINE_AXIS;
	}
	public void left()
	{
		layoutMode=BorderLayout.WEST;
		axis=BoxLayout.PAGE_AXIS;
	}
	public void top()
	{
		layoutMode=BorderLayout.NORTH;
		axis=BoxLayout.LINE_AXIS;
	}
	public void right()
	{
		layoutMode=BorderLayout.EAST;
		axis=BoxLayout.PAGE_AXIS;
	}
	public void colorOne()
	{
		textColor=Color.GREEN;
	}
	public void colorTwo()
	{
		textColor=Color.RED;
	}
	public void bgColorOne()
	{
		bgColor=Color.PINK;
	}
	public void bgColorTwo()
	{
		bgColor=Color.CYAN;
	}
	public void reset()
	{
		axis=BoxLayout.LINE_AXIS;
		layoutMode=BorderLayout.SOUTH;
		bgColor=Color.WHITE;
		textColor=Color.BLACK;
		font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
	}
	public static void main (String[] args)
	{
		ButtonAndLayout app = new ButtonAndLayout();
	}
}