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
	private Converter convert;

	public MolecularMath(String a, String b, double given, Molecule m)
	{
		start = a;
		end = b;
		current = "";
		givenValue = given;
		molecule = m;
		path = new Path(start,end);
		convert = null;
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

	public void doMath(MolecularScreen screen)
	{
		convert = new Converter(path.getMetricConversionFirst(), path.getMetricConversionLast(), givenValue, screen);
		System.out.println("\n\nHere are the series of individual conversions : ");
		
		if(!foundExceptions())
		{
			boolean done = false;
			String line = path.getPath();
			String temp = "";
			int index = 0;

			String metricLastTemp = "";
			if(path.isMetricLast())
			{
				metricLastTemp = line.substring(line.indexOf("#")+1);
				line = line.substring(0,line.indexOf("#"));
			}

			if(path.isMetricFirst())
			{
				temp = line.substring(0,line.indexOf("#"));
				line = line.substring(line.indexOf("#")+1);
				System.out.println(" * Command : |"+temp+"|");
			}

			while(!done)
			{
				if(line.indexOf(" -> ") == line.lastIndexOf(" -> "))
				{
					done = true;
					temp = line;
				}
				else
				{
					index = line.indexOf(" -> ")+4;
					temp = line.substring(index);
					index = index + temp.indexOf(" -> ");
					temp = line.substring(0,index);
					System.out.println(" * Command : |"+temp+"|");
					line = line.substring(line.indexOf(" -> ")+4);
					if(path.isMetricLast() && line.indexOf(" -> ") == line.lastIndexOf(" -> "))
						System.out.println(" * Command : |"+line+"|");
				}	
			}

			if(path.isMetricLast())
				System.out.println(" * Command : |"+metricLastTemp+"|");
			else
				System.out.println(" * Command : |"+temp+"|");
		}
	}

	public boolean foundExceptions()
	{
		String line = path.getPath();
		if(line.equals("mol -> gram"))
		{
			System.out.println(" * Command : |mole -> gram|");
			return true;
		}
		else if(line.equals("gram -> mol"))
		{
			System.out.println(" * Command : |gram -> mol|");
			return true;
		}
		else if(line.equals("amu -> gram"))
		{
			System.out.println(" * Command : |amu -> gram|");
			return true;
		}
		else if(line.equals("gram -> amu"))
		{
			System.out.println(" * Command : |gram -> amu|");
			return true;
		}
		else
		{
			if(line.indexOf(" -> ") == line.lastIndexOf(" -> ") && line.indexOf("gram") != line.lastIndexOf("gram"))
			{
				if(line.startsWith("gram ->"))
				{
					System.out.println(" * Command : |gram -> "+line.substring(line.indexOf(" -> ")+4)+"|");
					return true;
				}
				else if(line.endsWith("-> gram"))
				{
					System.out.println(" * Command : |"+line.substring(0,line.indexOf(" -> "))+" -> gram|");
					return true;
				}
			}
			if(line.contains("#"))
			{
				if(line.startsWith("amu") && line.indexOf("amu") < line.indexOf("#"))
				{
					System.out.println(" * Command : |amu -> gram");
					System.out.println(" * Command : |gram -> "+line.substring(line.lastIndexOf(" -> ")+4)+"|");
					return true;
				}
				else if(line.startsWith("mol") && line.indexOf("mol") < line.indexOf("#"))
				{
					System.out.println(" * Command : |mol -> gram");
					System.out.println(" * Command : |gram -> "+line.substring(line.lastIndexOf(" -> ")+4)+"|");
					return true;
				}
			}
		}
		return false;
	}
}