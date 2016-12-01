import java.util.Scanner;

public class MolecularCalcs
{
	private String inputMolecule, unitsInit, unitsFin;
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
		givenValue = 0.0;
		termReader = new Scanner(System.in);
	}

	public void initiateSequence()
	{
		introduction();
		
		getMoleculeInfo();
		molecule = new Molecule(inputMolecule);
		molecule.calculateAttr(); //calculate attribute

		screen = new MolecularScreen(molecule); //requires several of molecule's attributes

		math = new MolecularMath(unitsInit,unitsFin,givenValue,molecule);
		math.executePath(screen); //handles math and prints to interface
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
			System.out.println(" Anywhere that says '#' represents a split in calculations that you will later see in RESULTS when program is completed");
			System.out.print("\nHit ENTER when done : ");
			termReader.nextLine();
			System.out.println("\n BTW, the code to determine the path was not that easy to write :/");
			System.out.print("\nHit ENTER when done : ");
			termReader.nextLine();
		}
		System.out.println();
	}

	public void getMoleculeInfo()
	{
		System.out.print("--> Enter your molecule/atom (ex: C6H12O6/ex: Na) : ");
		inputMolecule = termReader.nextLine();
		System.out.print("\n--> Enter Initial Units (units described in guide, gram, gram/mol, particles, mol, amu) : ");
		unitsInit = termReader.nextLine();
		
		System.out.print("\n--> Enter acquired value/measurement : ");
		String s = termReader.nextLine();
		if(s.contains("^") && !s.contains("*")) //ex: 15^16
			givenValue = Math.pow(Double.parseDouble(s.substring(0,s.indexOf("^"))),Double.parseDouble(s.substring(s.indexOf("^")+1)));
		else if(s.contains("^") && s.contains("*")) //ex: 2.3*10^17 (scientific notation)
			givenValue = Double.parseDouble(s.substring(0,s.indexOf("*")))*Math.pow(Double.parseDouble(s.substring(s.indexOf("*")+1,s.indexOf("^"))),Double.parseDouble(s.substring(s.indexOf("^")+1)));
		else //ex: 15.16
			givenValue = Double.parseDouble(s);

		System.out.print("\n--> Enter Final Units (units described in guide, gram, gram/mol, particles, mol, amu) : ");
		unitsFin = termReader.nextLine();
	}
}