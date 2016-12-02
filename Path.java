import java.util.Scanner;
import java.io.*;

public class Path
{
	private String start;
	private String end;
	private String path;
	private int metricConv1;
	private int metricConv2;
	private boolean metricFirst;
	private boolean metricLast;
	private Scanner siReader;
	private File siPrefixes;

	private final String PATH_DIRECTION_FWD = "amu -> gram -> mol -> particles -> gram/mol -> ";//extra arrow at the end to easily work around IndexOutOfRange exceptions
	private final String PATH_DIRECTION_REV = "gram/mol -> particles -> mol -> gram -> amu -> ";

	public Path(String a, String b)
	{
		start = a;
		end = b;
		path = "";
		metricConv1 = 0;
		metricConv2 = 0;
		metricFirst = false;
		metricLast = false;
		siPrefixes = new File("siPrefixes.txt");
		setScanner();
	}

	public void setScanner()
	{
		try
		{
			siReader = new Scanner(siPrefixes);
		}
		catch(FileNotFoundException a)
		{
			System.out.println("Could not find file : 'siPrefixes.txt'");
			System.exit(1);
		}
	}

	public String getPath()
	{
		return path;
	}

	public int getMetricConversionFirst()
	{
		return metricConv1;
	}

	public int getMetricConversionLast()
	{
		return metricConv2;
	}

	public boolean isMetricFirst()
	{
		return metricFirst;
	}

	public boolean isMetricLast()
	{
		return metricLast;
	}

	public boolean onlyOneConversionType(String type)
	{//bool is false corresponds with onlyMetricConversion, bool is true corresponds with onlyNonMetricConversion
		boolean bool = true;
		if(type.equals("onlyMetric"))
			bool = false;

		String line = "";
		String line2 = "";
		setScanner();
		while(siReader.hasNext())
		{
			line = siReader.nextLine();
			line2 = line.substring(0,line.indexOf("["));
			if(line2.contains(start))
			{
				metricFirst = true;
				metricConv1 = Integer.parseInt(line.substring(line.indexOf("^")+1));
				if(bool)
					return !bool;
				bool = !bool;
			}
		}
		if(bool)
		{
			setScanner();
			while(siReader.hasNext())
			{
				line = siReader.nextLine();
				line2 = line.substring(0,line.indexOf("["));
				if(line2.contains(end))
				{
					metricLast = true;
					metricConv2 = Integer.parseInt(line.substring(line.indexOf("^")+1));
					if(type.equals("onlyMetric"))
						return bool;
					return !bool;
				}
			}
		}
		if(type.equals("onlyMetric"))
			return false;
		return true;
	}

	public boolean gramException()
	{
		boolean isMetricLast = false;
		if(start.equals("mol") && end.equals("gram"))
		{
			path = "mol -> gram";
			return true;
		}
		if(start.equals("gram"))
		{
			setScanner();
			while(siReader.hasNext())
			{
				String line = siReader.nextLine();
				if(line.contains(end) && line.indexOf(end) == 0)//good example of short-circuit evaluation
				{
					path = "gram -> "+end;
					return true;
				}
			}
		}
		else if(end.equals("gram"))
		{
			setScanner();
			while(siReader.hasNext())
			{
				String line = siReader.nextLine();
				if(line.contains(start) && line.indexOf(start) == 0)//good example of short-circuit evaluation
				{
					path = start+" -> gram";
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean molException()
	{
		if(start.equals("mol") && metricLast)
		{
			path = "mol -> gram#gram -> "+end;
			return true;
		}
		return false;
	}

	public boolean amuException()
	{
		if(start.equals("amu") && end.equals("gram"))
		{
			path = "amu -> gram";
			return true;
		}
		else if(start.equals("gram") && end.equals("amu"))
		{
			path = "gram -> amu";
			return true;
		}
		return false;
	}

	public void setAMUIrregularPath()//Covers exception that other path finder methods will not find 
	{
		path = start+" -> gram#gram -> "+end;
	}

	public void setMOLIrregularPath(String type)//Covers exception that other path finder methods will not find
	{
		if(type.equals("type1"))
		{
			if(end.equals("gram"))
				path = "mol -> gram";
			else if(end.equals("amu"))
				path = "mol -> gram -> amu";
		}
		else if(type.equals("type2"))
		{
			if(start.equals("gram/mol"))
				path = "gram/mol -> particles -> mol";
			else if(start.equals("particles"))
				path = "particles -> mol";
		}
	}

	public void setMetricPath(String starting, boolean bePrereq)//bePrereq is true = First Path division, bePrereq  is false = Second Path division
	{
		if(starting.equals("gram"))
		{
			if(bePrereq)
				path += start+" -> gram#";
			else
				path += "gram -> "+end;
		}
		else
			path = start+" -> gram -> "+end;
	}

	public void setNonMetricPath(String starting, boolean bePrereq)//bePrereq is true = First Path division, bePrereq  is false = Second Path division
	{
		String start = this.start;//"this" keyword indicates to first look for variableoutside of the method
		String end = this.end;

		if(starting.equals("gram"))
		{
			if(bePrereq)
				end = "gram";
			else
				start = "gram";
		}

		int first = PATH_DIRECTION_FWD.indexOf(start);
		int last = PATH_DIRECTION_FWD.indexOf(end); 
		String lineFirst = "";
		String lineLast = "";
		if(first < last)
		{
			lineFirst = PATH_DIRECTION_FWD.substring(first,last);
			lineLast = PATH_DIRECTION_FWD.substring(last);
			lineLast = lineLast.substring(0,lineLast.indexOf(" ->"));
		}
		else
		{
			first = PATH_DIRECTION_REV.indexOf(start);
			last = PATH_DIRECTION_REV.indexOf(end);
			if(end.equals("gram"))//removes confusion between gram/mol index and gram index
				last = PATH_DIRECTION_REV.lastIndexOf(end);
			lineFirst = PATH_DIRECTION_REV.substring(first,last);
			lineLast = PATH_DIRECTION_REV.substring(last);
			lineLast = lineLast.substring(0,lineLast.indexOf(" ->"));
		}
		if(starting.equals("gram"))
		{
			if(bePrereq)
				path += lineFirst+lineLast+"#";
			else
				path+= lineFirst+lineLast;
		}
		else
			path = lineFirst+lineLast;
	}
}