import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Problem3 extends JFrame
{
	public static int MINIMUM_BOX_HEIGHT=20;
	public static int ASSIGNMENT_BOX_WIDTH=100;
	int studentNum=0, assignmentNum=0, boxWidth, extraW;
	ArrayList<ArrayList<JTextField>> scoreArray=new ArrayList<ArrayList<JTextField>>();
	ArrayList<JTextField> assignmentArray=new ArrayList<JTextField>();
	ArrayList<JTextField> studentArray=new ArrayList<JTextField>();
	JPanel numberPanel, studentPanel, assignmentPanel, topPanel, buttonPanel; 
	JButton addGradeButton, addStudentButton, removeStudentButton, removeGradeButton, totalAverage, gradeAverage, studentAverage;
	String fileName;
	GridLayout layout;
	
	public Problem3(String name)
	{
		super();
		init(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	public class TextUpdatedListener implements FocusListener
	{
		public void focusLost(FocusEvent e)
		{
			JTextField source = (JTextField)(e.getSource());
			try
			{
				source.setText(String.valueOf(Integer.valueOf(source.getText())));
			}
			catch (NumberFormatException ex)
			{
				try
				{
					source.setText(String.valueOf(Double.valueOf(source.getText())));
				}
				catch (NumberFormatException ex2)
				{
					ex2.printStackTrace();
					source.setText("0");
				}
			}
			save();
		}
		public void focusGained(FocusEvent e)
		{
		}
	}
	public void init(String name) 
	{
		boxWidth="Student 1".length()*6;
		Container container=getContentPane();
		fileName=name;
		
		try
		{
			String[] studentReadArray=readStudents(fileName);
			studentNum=studentReadArray.length;
			boxWidth=longestStringLength(studentReadArray)*6;
		
			String[][] gradeReadArray=readGrades(fileName);
			assignmentNum=gradeReadArray.length;
			
			if (assignmentNum<=0)
				assignmentNum=1;
			if (studentNum<=0)
				studentNum=1;
	
			for(int x=0; x<studentNum;x++)
			{
				studentArray.add(new JTextField(studentReadArray[x]));
			}
			for(int y=0;y<assignmentNum;y++)
			{
				scoreArray.add(new ArrayList<JTextField>());
				assignmentArray.add(new JTextField("Assignment "+String.valueOf(y+1)));
				for(int x=0;x<studentNum;x++) 
				{
					try
					{
						Double.valueOf(gradeReadArray[y][x]);
						scoreArray.get(y).add(new JTextField(gradeReadArray[y][x]));
					}
					catch(NumberFormatException e)
					{
						e.printStackTrace();
						scoreArray.get(y).add(new JTextField("0"));
					}
				}
			}
		
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			init();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			init();
		}
		
		addGradeButton=new JButton("Add Assignment");
		removeGradeButton=new JButton("Remove Assignment");
		addStudentButton=new JButton("Add Student");
		removeStudentButton=new JButton("Remove Student");
		totalAverage=new JButton("Class Average");
		gradeAverage=new JButton("Assignment Average");
		studentAverage=new JButton("Student Average");
		
		addGradeButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					addGrade();
				}
			}
		);
		removeGradeButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					removeGrade();
				}
			}
		);
		addStudentButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					addStudent();
				}
			}
		);
		removeStudentButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					removeStudent();
				}
			}
		);	
		totalAverage.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					getTotalAverage();
				}
			}
		);
		gradeAverage.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					getGradeAverage();
				}
			}
		);
		studentAverage.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					getStudentAverage();
				}
			}
		);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(addGradeButton);
		buttonPanel.add(removeGradeButton);
		buttonPanel.add(addStudentButton);
		buttonPanel.add(removeStudentButton);
		buttonPanel.add(totalAverage);
		buttonPanel.add(gradeAverage);
		buttonPanel.add(studentAverage);
		buttonPanel.add(Box.createHorizontalGlue());
		
		reDrawPanels();
		save();
		setVisible(true);
		setTitle("Grading Program");
		setSize(new Dimension(0,0));
		extraW = (int)getSize().getWidth()-(int)getContentPane().getSize().getWidth();
		setMinimumSize(new Dimension(
			(int)container.getPreferredSize().getWidth() +extraW,
			(int)container.getPreferredSize().getHeight() + (int)buttonPanel.getPreferredSize().getHeight() + (int)topPanel.getPreferredSize().getHeight()));
	}
	public void init()
	{
		if (assignmentNum<=0)
			assignmentNum=1;
		if (studentNum<=0)
			studentNum=1;
		for(int x=studentArray.size(); x<studentNum;x++)
		{
			studentArray.add(new JTextField("Student "+String.valueOf(x+1)));
		}
		int lastFullRow=0;
		while(scoreArray.size()!=0 && scoreArray.get(lastFullRow).size()==studentNum)
		{
			lastFullRow++;
		}
		for(int y=lastFullRow+1;y<assignmentNum;y++)
		{
			scoreArray.add(new ArrayList<JTextField>());
			assignmentArray.add(new JTextField("Assignment "+String.valueOf(y+1)));
			for(int x=scoreArray.get(y).size();x<studentNum;x++) 
			{
				scoreArray.get(y).add(new JTextField("0"));
			}
		}
		if(scoreArray.size()!=0)
		{
			for(int x=scoreArray.get(lastFullRow).size();x<studentNum;x++) 
			{
				scoreArray.get(lastFullRow).add(new JTextField("0"));
			}
		}
		else
		{
			assignmentArray.add(new JTextField("Assignment "+String.valueOf(1)));
			scoreArray.add(new ArrayList<JTextField>());
			for(int x=scoreArray.get(lastFullRow).size();x<studentNum;x++) 
			{
				scoreArray.get(lastFullRow).add(new JTextField("0"));
			}
		}
	}
	
	public String[] readStudents(String nameParam)
	{
		File name = new File(nameParam);
		
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text="";
			text=input.readLine();
			String[] textArray=text.split(" ");
			for (int x=0; x<textArray.length; x++)
			{
				textArray[x]=textArray[x].replace('_',' ');
			}
			return textArray;
		}
		catch (IOException io)
		{
			io.printStackTrace();
			BufferedWriter writer=null;
			try
			{
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
				writer.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
			readStudents(nameParam);
		}
		catch (NullPointerException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	public String[][] readGrades(String nameParam)
	{
		File name = new File(nameParam);
		
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text="";
			int y=0,x=0,count=0;
			input.readLine();
			while (!((text=input.readLine())==null))
			{
				y++;
			}
			x=readStudents(nameParam).length;
			String[][] gradeArray=new String[y][x];
			
			input = new BufferedReader(new FileReader(name));
			input.readLine();
			while (!((text=input.readLine())==null))
			{
				gradeArray[count]=text.split(" ");
				count++;
			}
			return gradeArray;
		}
		catch (IOException io)
		{
			io.printStackTrace();
			BufferedWriter writer=null;
			try
			{
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
				writer.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
			readGrades(nameParam);
		}
		return null;
	}
	public int longestStringLength(String[] array)
	{
		int y=0;
		for (String x:array)
		{
			if (y<x.length())
				y=x.length();
		}
		return y;
	}
	
	public void addGrade()
	{
		assignmentNum++;
		scoreArray.add(new ArrayList<JTextField>());
		assignmentArray.add(new JTextField("Assignment "+String.valueOf(assignmentArray.size()+1)));
		for(int x=0;x<studentNum;x++) 
		{
			scoreArray.get(scoreArray.size()-1).add(new JTextField("0"));
		}
		reDrawPanels();
		save();
	}
	public void removeGrade()
	{
		if (assignmentNum>1)
		{
			assignmentNum--;
			scoreArray.remove(scoreArray.size()-1);
			assignmentArray.remove(assignmentArray.size()-1);
		}
		reDrawPanels();
		save();
	}
	public void addStudent()
	{
		String inputName = JOptionPane.showInputDialog("Please input a name");
		if (inputName!=null)
		{
			studentNum++;
			for(int x=0;x<assignmentNum;x++) 
			{
				scoreArray.get(x).add(new JTextField("0"));
			}
			studentArray.add(new JTextField(inputName));
			if(inputName.length()*6>boxWidth)
				boxWidth=inputName.length()*6;
		}
		reDrawPanels();
		save();
	}
	public void removeStudent()
	{
		String inputName = JOptionPane.showInputDialog("Please input a name");
		if (inputName!=null)
		{
			for(int x=0;x<studentNum;x++)
			{
				if (studentArray.get(x).getText().equals(inputName)&& studentNum>1)
				{
					studentNum--;
					studentArray.remove(x);
					for(int y=0;y<assignmentNum;y++) 
					{
						scoreArray.get(y).remove(x);
					}
					break;
				}
			}
		}
		reDrawPanels();
		save();
	}
	
	public void getTotalAverage()
	{
		int counter=0;
		double total=0;
		for(int y=0;y<assignmentNum;y++)
		{
			for(int x=0;x<studentNum;x++) 
			{
				counter++;
				JTextField field=(JTextField)(scoreArray.get(y).get(x));
				total+=Double.valueOf(field.getText());
			}
		}
		JOptionPane.showMessageDialog(null, "Class Average: "+String.valueOf((double)total/(double)counter));
	}
	public void getGradeAverage()
	{
		String inputNum = JOptionPane.showInputDialog("Please input an assignment number");
		try{
			if (inputNum!=null && Integer.valueOf(inputNum)<=assignmentNum && Integer.valueOf(inputNum)>0)
			{
				int counter=0;
				double total=0;
				for(int x=0;x<studentNum;x++) 
				{
					counter++;
					JTextField field=(JTextField)(scoreArray.get(Integer.valueOf(inputNum)-1).get(x));
					total+=Double.valueOf(field.getText());
				}	
				JOptionPane.showMessageDialog(null, "Assignment "+inputNum+" Average: "+String.valueOf((double)total/(double)counter));
			}
		}catch(NumberFormatException e)
		{
			e.printStackTrace();
		};
	}
	public void getStudentAverage()
	{
		String inputName = JOptionPane.showInputDialog("Please input a name");
		if (inputName!=null)
		{
			int counter=0;
			double total=0;
			for (int x=0;x<studentArray.size();x++)
			{
				if (studentArray.get(x).getText().equals(inputName))
				{
					for(int y=0;y<assignmentNum;y++) 
					{
						counter++;
						JTextField field=(JTextField)(scoreArray.get(y).get(x));
						total+=Double.valueOf(field.getText());
					}
					JOptionPane.showMessageDialog(null, inputName+" Average: "+String.valueOf((double)total/(double)counter));
				}
			}
		}
	}
	
	public void reDrawPanels()
	{
		Container container=getContentPane();
		
		container.removeAll();
		layout=new GridLayout(assignmentNum,studentNum);
		numberPanel=new JPanel(layout);
		studentPanel=new JPanel(new GridLayout(1,studentNum));
		assignmentPanel=new JPanel(new GridLayout(assignmentNum,1));
		for(int x=0; x<studentNum;x++)
		{
			JTextField student=(JTextField)(studentArray.get(x));
			student.setPreferredSize(new Dimension(boxWidth,Problem3.MINIMUM_BOX_HEIGHT));
			student.setEditable(false);
			studentPanel.add(student);
		}
 		for(int y=0;y<assignmentNum;y++)
		{
			JTextField assignment=(JTextField)(assignmentArray.get(y));
			assignment.setPreferredSize(new Dimension(Problem3.ASSIGNMENT_BOX_WIDTH,Problem3.MINIMUM_BOX_HEIGHT));
			assignment.setEditable(false);
			assignmentPanel.add(assignment);
			for(int x=0;x<studentNum;x++)
			{
				JTextField field=(JTextField)(scoreArray.get(y).get(x));
				field.addFocusListener(new TextUpdatedListener());
				field.setPreferredSize(new Dimension(boxWidth,Problem3.MINIMUM_BOX_HEIGHT));
				numberPanel.add(field);
			}
		}
		
		topPanel=new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		JTextField assignmentBox=(JTextField)(assignmentArray.get(0));
		JTextField studentBox=(JTextField)(studentArray.get(0));
		topPanel.add(Box.createHorizontalStrut((int)(assignmentBox.getPreferredSize().getWidth())));
		topPanel.add(studentPanel);
		
		container.setLayout(new BorderLayout());
		container.add(numberPanel, BorderLayout.CENTER);
		container.add(topPanel, BorderLayout.NORTH);
		container.add(assignmentPanel, BorderLayout.WEST);
		container.add(buttonPanel, BorderLayout.SOUTH);
		SwingUtilities.updateComponentTreeUI(this);
		
		setVisible(true);
		setMinimumSize(new Dimension(
			(int)container.getPreferredSize().getWidth() + extraW,
			(int)container.getPreferredSize().getHeight() + (int)buttonPanel.getPreferredSize().getHeight() + (int)topPanel.getPreferredSize().getHeight()));
	}
	public void save()
	{
		BufferedWriter writer=null;
		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
			for (JTextField x:studentArray)
			{
				writer.write(x.getText().replace(" ","_")+" ");
			}
			writer.newLine();
			for(ArrayList<JTextField> y:scoreArray)
			{
				for (JTextField x:y)
				{
					writer.write(x.getText()+" ");
				}
				writer.newLine();
			}
		}
		catch (IOException ex)
		{
		  ex.printStackTrace();
		}
		finally
		{
			try
			{
				writer.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	public static void main(String[] args)
	{
		Problem3 app=new Problem3("problem3.txt");
	}
}