public class Converter
{
	private double metricConvFirst;
	private double metricConvLast;
	private double finalResult;
	private boolean metricAlready;

	public Converter(int a, int b, double given)
	{
		metricConvFirst = 0.0;
		metricConvLast = 0.0;
		if(a != 0)
			metricConvFirst = (double)(Math.pow(10, a));
		if(b != 0)
			metricConvLast = (double)(Math.pow(10, b));
		
		finalResult = given;
		metricAlready = false;
	}

	/*public String betweenMetrics()
	{
		if(metricConvFirst != 0.0)
			finalResult = finalResult*(1/metricConvFirst);
		return "";
	}

	public String amu_Gram()
	{
		return "";
	}

	public String gram_Mol()
	{

	}

	public String mol_Particles()
	{

	}

	public String particles_GramMol()
	{

	}*/

}