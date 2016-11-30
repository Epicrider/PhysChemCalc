import java.util.Scanner;
import java.io.*;

public class Path
{
	private String start;
	private String end;
	private String path;
	private boolean metricFirst;
	private Scanner siReader;
	private File siPrefixes;

	private final String PATH_DIRECTION_FWD = "amu -> gram -> mol -> particles -> gram/mol -> ";//extra arrow at the end to easily work around IndexOutOfRange exceptions
	private final String PATH_DIRECTION_REV = "gram/mol -> particles -> mol -> gram -> amu -> ";

	public Path(String a, String b)
	{
		start = a;
		end = b;
		path = "";
		metricFirst = false;
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

	public boolean isMetricFirst()
	{
		return metricFirst;
	}

	public boolean onlyOneConversionType(String type)
	{//bool is false corresponds with onlyMetricConversion, bool is true corresponds with onlyNonMetricConversion
		boolean bool = true;
		if(type.equals("onlyMetric"))
			bool = false;

		String line = "";
		setScanner();
		while(siReader.hasNext())
		{
			line = siReader.nextLine();
			line = line.substring(0,line.indexOf("["));
			if(line.contains(start))
			{
				metricFirst = true;
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
				line = line.substring(0,line.indexOf("["));
				if(line.contains(end))
				{
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
		String start = this.start;//"this" keyword indicates to first look for start outside of the method
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