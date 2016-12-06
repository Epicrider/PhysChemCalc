//CalcRunner.java
//Omar Hossain- Jeyadave Nuntha Kumar
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
			" :1 Chem Calc (Semi-Functional) \n"+
			" :2 Physics Calc (Non-Functional)\n"+
			" :3 Chemical Equation Balancer (Non-Functional)\n :");
		if(choice == 1)
		{
			System.out.print("\nLoading Chemistry Calculator...\n");
			MolecularCalcs mc = new MolecularCalcs();
			mc.initiateSequence();
		}
		else if(choice == 2)
		{
			System.out.print("\nLoading Physics Calculator...\n");
			PhysicsCalculator pc = new PhysicsCalculator();
			pc.run();
		}
		else if(choice == 3)
		{
			//I'm thinking of adding another calculator that integrates some features of the Chem Calculator
			//Basically what it'll do is help you balance chemical equations
			//It won't completely solve it for you, but it'll keep track of your inventory
			//You can set the coefficients, and the inventory should change consequently
			//There probably won't be a need for a GUI.
			System.out.println("\nLoading Chemical Equation Balancer...\n");
			MolecularBalance mb = new MolecularBalance();
			mb.initiateSequence();
		}
	}
}
