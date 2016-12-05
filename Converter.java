public class Converter
{
	private double finalResult;
	private boolean metricAlready;
	private MolecularScreen screen;

	private double METRIC_PER_GRAM;
	private final double AMU_PER_GRAM = Math.pow(1.66,-24);
	private double GRAM_PER_MOL;
	private final double MOL_PER_PARTICLES = Math.pow(6.02,23);

	public Converter(double a, double given, MolecularScreen mscr, double molarMass)
	{
		METRIC_PER_GRAM = Math.pow(10, a);
		finalResult = given;
		screen = mscr;
		GRAM_PER_MOL = molarMass;
		metricAlready = false;
	}
	//method for testing
	public double getFinalResult()
	{
		return finalResult;
	}

	public void takeCommand(String command)
	{
		if(command.contains("amu -> gram") || command.contains("gram -> amu"))
			amu_Gram(command);
		else if(command.contains("particles -> gram/mol") || command.contains("gram/mol -> particles"))
			particles_GramMol(command);
		else if(command.contains("gram -> mol") || command.contains("mol -> gram"))
			gram_Mol(command);
		else if(command.contains("mol -> particles") || command.contains("particles -> mol"))
			mol_Particles(command);
		else
			gram_Metrics(command);
	}

	public void takeCommand(String command, int conversionFactor)
	{//overloaded to handle double metric-metric commands
		metric_Metric_Double(command, conversionFactor);
	}

	private String metric_Metric_Double(String command, int conversionFactor)
	{
		if(command.startsWith("gram"))
		{
			finalResult = finalResult*(1/Math.pow(10,conversionFactor));
				return "";
		}
		finalResult = finalResult*(Math.pow(10,conversionFactor));
		return "";
	}

	private String gram_Metrics(String command)
	{
		if(command.startsWith("gram"))
		{
			finalResult = finalResult*(1/METRIC_PER_GRAM);
			return "";
		}
		finalResult = finalResult*(METRIC_PER_GRAM);
		return "";
	}

	private String amu_Gram(String command)
	{
		if(command.startsWith("amu"))
		{
			finalResult = finalResult*(1/AMU_PER_GRAM);
			return "";
		}
		finalResult = finalResult*(AMU_PER_GRAM);
		return "";
	}

	private String gram_Mol(String command)
	{
		if(command.startsWith("gram"))
		{
			finalResult = finalResult*(1/GRAM_PER_MOL);
			return "";
		}
		finalResult = finalResult*(GRAM_PER_MOL);
		return "";
	}

	private String mol_Particles(String command)
	{
		if(command.startsWith("mol"))
		{
			finalResult = finalResult*(MOL_PER_PARTICLES);
			return "";
		}
		finalResult = finalResult*(1/MOL_PER_PARTICLES);
		return "";
	}

	private String particles_GramMol(String command)
	{
		if(command.startsWith("particles"))
		{
			finalResult = finalResult*(GRAM_PER_MOL);
			return "";
		}
		finalResult = finalResult*(1/GRAM_PER_MOL);
		return "";
	}
}