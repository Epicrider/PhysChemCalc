public class MolecularMath
{
	public static final String PATH_GRAMMOL = "gram/mol";
	public static final String PATH_MOL = "mol";
	public static final String PATH_GRAM = "gram";
	public static final String PATH_MOLECULE = "molecules/atoms";
	public static final String PATH_AMU = "amu";
	public static final double AMU_TO_GRAM = 1.66*Math.pow(10,-24);
	public static final double AVOGADRO_NUM = 6.022*Math.pow(10,23);

	private double givenValue;
	private Molecule molecule;
	private Path path;
	private String start;
	private String end;

	public MolecularMath(String a, String b, double given, Molecule m)
	{
		start = a;
		end = b;
		path = new Path(start,end);
		givenValue = given;
		molecule = m;
	}

	public void executePath(MolecularScreen screen)
	{
		boolean onlyMetric = path.onlyOneConversionType("onlyMetric");
		boolean onlyNonMetric = path.onlyOneConversionType("onlyNonMetric");
		if(onlyMetric)
		{
			System.out.println("Only interrelated metric units conversions");
		}
		else if(onlyNonMetric)
		{
			System.out.println("Only interrelated non-metric units conversions");
		}
		else if(!onlyNonMetric && !onlyMetric)
			System.out.println("Mix of interrelated metric and interrelated non-metric conversions");
	}
}