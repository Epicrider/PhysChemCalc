import java.util.Scanner;
import java.io.*;

public class Path
{
	private String start;
	private String end;
	private String path;
	private Scanner siReader;
	private File siPrefixes;

	private final String AMU_TO_GRAM_PER_MOL = "amu>gram>mol>particles>gram/mol";
	private final String GRAM_PER_MOL_TO_AMU = "gram/mol>particles>mol>gram>amu";
	private final String METRIC_TO_GRAM = "metric>imperial>gram";
	private final String GRAM_TO_METRIC = "gram>imperial>metric";

	public Path(String a, String b)
	{
		start = a;
		end = b;
		path = "";
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
			System.out.println(line);
			line = line.substring(0,line.indexOf("["));
			if(start.contains(line))
			{
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
				System.out.println(line);
				line = line.substring(0,line.indexOf("["));
				if(end.contains(line))
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
}