public class Converter
{
	private double metricConvFirst;
	private double metricConvLast;
	private double finalResult;
	private boolean metricAlready;
	private MolecularScreen screen;

	public Converter(int a, int b, double given, MolecularScreen mscr)
	{
		metricConvFirst = 0.0;
		metricConvLast = 0.0;
		if(a != 0)
			metricConvFirst = (double)(Math.pow(10, a));
		if(b != 0)
			metricConvLast = (double)(Math.pow(10, b));
		
		finalResult = given;
		screen = mscr;
		metricAlready = false;
	}

	public void takeCommand(String command)
	{
		if(command.contains("amu") && command.contains("gram"))
			amu_Gram(command);
		else if(command.contains("gram") && command.contains("mol"))
			gram_Mol(command);
		else if(command.contains("mol") && command.contains("particles"))
			mol_Particles(command);
		else if(command.contains("particles") && command.contains("gram/mol"))
			particles_GramMol(command);
		else
			gram_Metrics(command);
	}

	private String gram_Metrics(String command)
	{
		if(command.startsWith("gram"))
		{
			return "";
		}
		else
		{
			return "";
		}

	}

	private String amu_Gram(String command)
	{
		if(command.startsWith("amu"))
		{
			return "";
		}
		else
		{
			return "";
		}
	}

	private String gram_Mol(String command)
	{
		if(command.startsWith("gram"))
		{
			return "";
		}
		else
		{
			return "";
		}
	}

	private String mol_Particles(String command)
	{
		if(command.startsWith("mol"))
		{
			return "";
		}
		else
		{			
			return "";
		}
	}

	private String particles_GramMol(String command)
	{
		if(command.startsWith("particles"))
		{
			return "";
		}
		else
		{
			return "";
		}
	}
}