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
	//method for testing
	public double getFinalResult()
	{
		return convert.getFinalResult();
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

	public void packageAndSendCommand(String command)
	{
		System.out.println(command);
		command = command.substring(command.indexOf("|")+1,command.lastIndexOf("|"));
		convert.takeCommand(command);
	}

	public void doMath(MolecularScreen screen)
	{
		convert = new Converter(path.getMetricConversionFirst(), path.getMetricConversionLast(), givenValue, screen, molecule.getTotalMass());
		System.out.println("\n\n-------------------------------------------------------------------------------------------------------------");
		System.out.println(" <The following information presented to you is helpful for your testing<\n");
		System.out.println(" * Here are the series of individual conversions :");
		
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
				packageAndSendCommand(" * Command : |"+temp+"|");
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
					packageAndSendCommand(" * Command : |"+temp+"|");
					line = line.substring(line.indexOf(" -> ")+4);
					if(path.isMetricLast() && line.indexOf(" -> ") == line.lastIndexOf(" -> "))
						packageAndSendCommand(" * Command : |"+line+"|");
				}	
			}

			if(path.isMetricLast())
				packageAndSendCommand(" * Command : |"+metricLastTemp+"|");
			else
				packageAndSendCommand(" * Command : |"+temp+"|");
		}
	}

	public boolean foundExceptions()
	{
		String line = path.getPath();
		if(line.equals("mol -> gram"))
		{
			packageAndSendCommand(" * Command : |mole -> gram|");
			return true;
		}
		else if(line.equals("gram -> mol"))
		{
			packageAndSendCommand(" * Command : |gram -> mol|");
			return true;
		}
		else if(line.equals("amu -> gram"))
		{
			packageAndSendCommand(" * Command : |amu -> gram|");
			return true;
		}
		else if(line.equals("gram -> amu"))
		{
			packageAndSendCommand(" * Command : |gram -> amu|");
			return true;
		}
		else
		{
			if(line.indexOf(" -> ") == line.lastIndexOf(" -> ") && line.indexOf("gram") != line.lastIndexOf("gram"))
			{
				if(line.startsWith("gram ->"))
				{
					packageAndSendCommand(" * Command : |gram -> "+line.substring(line.indexOf(" -> ")+4)+"|");
					return true;
				}
				else if(line.endsWith("-> gram"))
				{
					packageAndSendCommand(" * Command : |"+line.substring(0,line.indexOf(" -> "))+" -> gram|");
					return true;
				}
			}
			if(line.contains("#"))
			{
				if(line.startsWith("amu") && line.indexOf("amu") < line.indexOf("#"))
				{
					packageAndSendCommand(" * Command : |amu -> gram");
					packageAndSendCommand(" * Command : |gram -> "+line.substring(line.lastIndexOf(" -> ")+4)+"|");
					return true;
				}
				else if(line.startsWith("mol") && line.indexOf("mol") < line.indexOf("#"))
				{
					packageAndSendCommand(" * Command : |mol -> gram");
					packageAndSendCommand(" * Command : |gram -> "+line.substring(line.lastIndexOf(" -> ")+4)+"|");
					return true;
				}
			}
		}
		return false;
	}
}