import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class MolecularScreen
{
	private JFrame frame;

	public MolecularScreen(Molecule molecule)
	{
		frame = new MolecularFrame();
		((MolecularFrame)(frame)).setPanel();
		((MolecularPanel1)(getMP1())).setArrays(molecule.getElementArray(),molecule.getDigitArray());
	}

	public void setPathDiagram(String s)
	{
		((MolecularPanel1)(getMP1())).setPathDiagram(s);
	}

	public JPanel getMP1()
	{
		return ((MolecularFrame)(frame)).getTopPanel();
	}

	public JPanel getMP2()
	{
		return ((MolecularFrame)(frame)).getBotPanel();
	}
}

class MolecularPanel1 extends JPanel
{
	private String [] atomicSymbols;
	private int [] atomCount;
	private String pathDiagram;
	private final int LEFT_JUSTF = 50;
	private final int TOP_JUSTF = 180;
	private final int TOP_JUSTF2 = 195;
	private final BasicStroke thickStroke = new BasicStroke(9.0f);//changes the thickness of lines (utilized by drawLine(~))
	private int SIZE_FACTOR; //treat as constant even though not declared as final

	public MolecularPanel1()
	{
		atomicSymbols = new String[1000];
		atomCount = new int[1000];
		pathDiagram = "";
		SIZE_FACTOR = 0;
	}

	public void setArrays(String [] a1, int [] a2)
	{//automatically called
		atomicSymbols = a1;
		atomCount = a2;
		if(400/atomicSymbols.length > 80) //makes sure max font size for drawing molecule is 80
			SIZE_FACTOR = 80;
		else
			SIZE_FACTOR = 400/atomicSymbols.length; //reduces font size as molecule size gets larger
		repaint();
	}

	public void setPathDiagram(String s)
	{//will be called (not directly) by MolecularMath when path selection is complete
		pathDiagram = s;
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int shift = 0;
		g.setFont(new Font("Sans Serif",Font.BOLD,40));
		g.drawString("YOUR MOLECULE : ",50,80);

		Graphics2D extraGraphics = (Graphics2D)g;
		extraGraphics.setStroke(thickStroke);
		extraGraphics.drawLine(55,85,400,85);
		extraGraphics.drawLine(475,0,475,900);
		extraGraphics.drawLine(750,85,860,85);

		g.drawString("PATH : ",750,80);
		if(pathDiagram.contains("#"))
		{
			String line = pathDiagram.substring(0,pathDiagram.indexOf("#")+1);
			g.drawString(line,500,200);
			line = pathDiagram.substring(pathDiagram.indexOf("#")+1);
			g.drawString(line,500,350);
		}
		else
			g.drawString(pathDiagram,500,200);
		g.setFont(new Font("Sans Serif",Font.BOLD, SIZE_FACTOR));
		
		for(int i = 0; i<= atomicSymbols.length-1; i++)
		{
			if(atomCount[i] == 0) //reached unpopulated area of array, end printing loop through overincrementation
				i+=atomicSymbols.length+1000;
			else
			{
				g.drawString(atomicSymbols[i],shift+LEFT_JUSTF,TOP_JUSTF);
				if(atomCount[i] != 1)
				{
					g.drawString(""+atomCount[i],shift+LEFT_JUSTF+atomicSymbols[i].length()*SIZE_FACTOR,TOP_JUSTF2);
					shift+= atomicSymbols[i].length()*SIZE_FACTOR+ (""+atomCount[i]).length()*SIZE_FACTOR;
				}
				else
					shift+=atomicSymbols[i].length()*SIZE_FACTOR;
			}
		}
	}
}

class MolecularPanel2 extends JPanel
{
	private final int TOP_JUSTF = 100;
	private final int TOP_JUSTF2 = 200;
	private final BasicStroke thickStroke = new BasicStroke(9.0f);

	public MolecularPanel2()
	{

	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setFont(new Font("Sans Serif", Font.BOLD, 40));
		g.drawString("RESULT :",470,50);

		Graphics2D extraGraphics = (Graphics2D)g;
		extraGraphics.setStroke(thickStroke);
		extraGraphics.drawLine(470,60,630,60);
	}
}

class ContentPanel extends JPanel
{
	public ContentPanel()
	{
		setBackground(Color.BLACK);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}

class MolecularFrame extends JFrame
{
	private JPanel background;
	private JPanel topPanel;
	private JPanel botPanel;

	public MolecularFrame()
	{
		super("MolecularCalcs");
		setVisible(true);
		setResizable(true);
		setSize(1400,900);
		setDefaultCloseOperation(HIDE_ON_CLOSE);	
		background = new ContentPanel();
		setContentPane(background);
		topPanel = new MolecularPanel1();
		botPanel = new MolecularPanel2();
	}

	public void setPanel()
	{
		background.setLayout(new GridLayout(2,1,9,9));
		background.add(topPanel);
		background.add(botPanel);
	}

	public JPanel getTopPanel()
	{
		return topPanel;
	}

	public JPanel getBotPanel()
	{
		return botPanel;
	}
}