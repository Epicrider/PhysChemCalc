import java.util.Scanner;
import java.text.DecimalFormat;

public class MolecularCalcs
{
	private String inputMolecule, unitsInit, unitsFin, givenValueSTR;
	private double givenValue;
	private Scanner termReader;
	private Molecule molecule;
	private MolecularScreen screen;
	private MolecularMath math;

	public MolecularCalcs()
	{
		inputMolecule = "";
		unitsInit = "";
		unitsFin = "";
		givenValueSTR = "";
		givenValue = 0.0;
		termReader = new Scanner(System.in);
	}

	public void initiateSequence()
	{
		introduction();
		
		boolean doubleMetric = getMoleculeInfo();
		molecule = new Molecule(inputMolecule);
		molecule.calculateAttr(); //calculate attribute

		screen = new MolecularScreen(molecule); //requires several of molecule's attributes

		math = new MolecularMath(unitsInit,unitsFin,givenValue,molecule, doubleMetric);
		math.setPath(screen); //sets path and prints it to interface
		math.doMath(screen);

		System.out.println("\n Given Value contains : "+getSigFigs(givenValueSTR)+" significant digits.");
		//DecimalFormat dformat = new DecimalFormat("########.###");
		//System.out.println(" Final Result : "+dformat.format(math.getFinalResult())+" "+unitsFin);//used for testing, will later be shown on the display panel
		System.out.println(" Final Result : "+math.getFinalResult()+" "+unitsFin);//used for testing, will later be shown on the display panel

		end();
	}

	public int getSigFigs(String value)
	{
		if(value.contains("*"))
			value = value.substring(0,value.indexOf("*"));
		else if(value.contains("^"))
			value = value.substring(0,value.indexOf("^"));

		if(!value.contains("."))//this probably will never evaluate to true because doubles have decimal places no matter what
		//but just in case, will handle exception if there is no decimal dot
		{
			if(value.contains("0"))
				return value.indexOf("0")-1;
			return value.length();
		}
		if(value.contains("0"))
		{
			if(!value.startsWith("0"))
				return value.length()-1;
			
			for(int i = 2; i<value.length(); i++)
			{
				if(value.charAt(i) != 48)
					return value.length()-i;
			}
		}
		return value.length()-1;
	}

	public void introduction()
	{
		System.out.println("\n\n\n<><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
		System.out.println("                   || MolecularCalcs ||");
		System.out.println("|| This program will help you with dimensional analysis ||");
		System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
		System.out.print("\nHi, please take some time to read this testing guide (P = play, ENTER = skip) : ");
		if(termReader.nextLine().equalsIgnoreCase("p"))
		{
			System.out.println("\nFor the prompts that ask you to enter initial OR final units, here's what you can enter : ");
			System.out.println("\n Any units found in siPrefixes.txt (ex: picogram, exagram, gram)");
			System.out.println(" Any of the following : amu, mol, gram, particles, gram/mol");
			System.out.print("\n\nNow expand your terminal window and hit ENTER : ");
			termReader.nextLine();
			System.out.println(" \n\nHere's the full conversion diagram {curly bracket represent conversion factors}: ");
			System.out.println("\n       metric weight units(found in siPrefixes.txt)");
			System.out.println("                |");
			System.out.println("                | {siPrefixes.txt conversion factors}");
			System.out.println("                |");
			System.out.println("amu ---------- gram ----------- mol ------------------ particles -------------------------------- gram/mol");
			System.out.println("    {gram/amu}       {gram/mol}      {Avogadro's Num}            {calculated w/ periodic table}");
			System.out.print("\nHit ENTER when done : ");
			termReader.nextLine();
			System.out.println("\n\n When the GUI panel pops up, it will show you the path of calculations that will be taken");
			System.out.println(" Anywhere that says '#' represents a split in calculations that you see in RESULTS (Currently, however,\n the you will only see conversions in RESULTS until program is finished");
			System.out.print("\nHit ENTER when done : ");
			termReader.nextLine();
			System.out.println("\n HI again");
			System.out.print("\nHit ENTER when done : ");
			termReader.nextLine();
		}
		System.out.println();
	}

	public void end()
	{
		System.out.println("\n<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
		System.out.println(" * Used atomic mass constants of the periodic table found at : <http://www.science.co.il/PTelements.asp>");
		System.out.println("\n\n");
	}

	public boolean getMoleculeInfo()
	{//gets all the user input, but also determines whether there is a special metric-metric case
		System.out.print("--> Enter your molecule/atom (ex: C6H12O6/ex: Na) : ");
		inputMolecule = termReader.nextLine().trim();
		System.out.print("\n--> Enter Initial Units (metric units in siPrefixes.txt, gram, gram/mol, particles, mol, amu) : ");
		unitsInit = termReader.nextLine().trim();
		
		System.out.print("\n--> Enter acquired value/measurement : ");
		String s = termReader.nextLine().trim();
		givenValueSTR = s;
		if(s.contains("^") && !s.contains("*")) //ex: 15^16
			givenValue = Math.pow(Double.parseDouble(s.substring(0,s.indexOf("^"))),Double.parseDouble(s.substring(s.indexOf("^")+1)));
		else if(s.contains("^") && s.contains("*")) //ex: 2.3*10^17 (scientific notation)
			givenValue = Double.parseDouble(s.substring(0,s.indexOf("*")))*Math.pow(Double.parseDouble(s.substring(s.indexOf("*")+1,s.indexOf("^"))),Double.parseDouble(s.substring(s.indexOf("^")+1)));
		else //ex: 15.16
			givenValue = Double.parseDouble(s);

		System.out.print("\n--> Enter Final Units (metric units in siPrefixes.txt, gram, gram/mol, particles, mol, amu) : ");
		unitsFin = termReader.nextLine();
		if(unitsInit.endsWith("gram") && unitsInit.length() > 4 && unitsFin.endsWith("gram") && unitsFin.length() > 4)
		{
			return true;//there is a metric-metric case
		}
		return false;//there is no metric-metric case
	}
}