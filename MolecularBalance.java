import java.util.Scanner;

public class MolecularBalance
{
	private String equation;
	private Scanner termReader;
	private MoleculeGroup mgroup;

	public MolecularBalance()
	{
		equation = "Some Equation";
		termReader = new Scanner(System.in);
		mgroup = new MoleculeGroup();
	}

	public void initiateSequence()
	{
		this.introduction();

		this.inputEquation();
		mgroup.setMoleculeList(equation);
		System.out.print("\nReactants Side");
		for(Molecule molecule : mgroup.getReactants())
			System.out.print(" : "+molecule.getName()+"\n              ");
		System.out.print("\nProducts Side");
		for(Molecule molecule : mgroup.getProducts())
			System.out.print("  : "+molecule.getName()+"\n             ");
	}

	public void introduction()
	{
		System.out.println("\n\n\n<><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
		System.out.println("                   || MolecularBalance ||");
		System.out.println("|| This program will help you balance chemical equations ||");
	}

	public void inputEquation()
	{
		System.out.println("\n-->Enter the given chemical equation below (EX: C6H12O6 + O2 -> CO2 + H20) : ");
		equation = termReader.nextLine();
	}
}