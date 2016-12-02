public class MolecularMath
{
	public static final String PATH_GRAM_MOLS = "gram/mol";
	public static final String PATH_MOLS = "mol";
	public static final String PATH_GRAMS = "gram";
	public static final String PATH_PARTICLES = "particles";
	public static final String PATH_AMUS = "amu";
	public static final double AMUS_TO_GRAMS = 1.66*Math.pow(10,-24);
	public static final double AVOGADRO_NUM = 6.022*Math.pow(10,23);

	private String start;
	private String end;
	private String current;
	private double givenValue;
	private Molecule molecule;
	private Path path;

	public MolecularMath(String a, String b, double given, Molecule m)
	{
		start = a;
		end = b;
		current = "";
		path = new Path(start,end);
		givenValue = given;
		molecule = m;
	}

	public void setPath(MolecularScreen screen)
	{
		boolean onlyMetric = path.onlyOneConversionType("onlyMetric");
		boolean onlyNonMetric = path.onlyOneConversionType("onlyNonMetric");
		if(!path.gramException())
		{
			if(!path.molException())
			{
				if(!path.amuException())
				{
					if((!onlyNonMetric && !onlyMetric) && end.equals("amu"))
					{
						path.setAMUIrregularPath();
					}
					else if(start.equals("mol") && (end.equals("gram") || end.equals("amu")))
					{
						path.setMOLIrregularPath("type1");
					}
					else if((start.equals("gram/mol") || start.equals("particles")) && end.equals("mol"))
					{
						path.setMOLIrregularPath("type2");
					}
					else
					{
						if(onlyMetric)
						{
							path.setMetricPath("N/A",false);
						}
						else if(onlyNonMetric || (!onlyNonMetric && !onlyMetric && (start.equals("gram") || end.equals("gram"))))//after ||: verifies that is not
						//mix solely due to the appearance of gram since gram is found in both metric & non-metric
						{
							path.setNonMetricPath("N/A",false);
						}
						else if(!onlyNonMetric && !onlyMetric)
						{
							if(path.isMetricFirst())
							{
								path.setMetricPath("gram",true);
								path.setNonMetricPath("gram",false);
							}
							else
							{
								path.setNonMetricPath("gram", true);
								path.setMetricPath("gram",false);
							}
						}
					}
				}
			}
		}
		screen.setPathDiagram(path.getPath());
	}
}