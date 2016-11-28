//CalcRunner.java
//Omar Hossain
//11-27-16
//Version: 1.0
//Start Calc here!
//Run this file

public class CalcRunner
{
	public static void main(String [] args)
	{
		CalcRunner cr = new CalcRunner();
		cr.runner();
	}

	public void runner()
	{
		int choice = 0;
		choice = Prompt.getInt("\n\n\nPick the number for the desired Calculator:\n"+
			" 1. Chem Calc (Semi-Functional) \n"+
			" 2. Physics Calc (Non-Functional)\n");
		if(choice == 1)
		{
			System.out.print("\nLoading Chemistry Calculator...\n");
			MolecularCalcs mc = new MolecularCalcs();
			mc.run();
		}
		else if(choice == 2)
		{
			System.out.print("\nLoading Physics Calculator...\n");
			PhysicsCalculator pc = new PhysicsCalculator();
			pc.run();
		}
	}
}
