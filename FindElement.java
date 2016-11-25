import java.util.Scanner;
import java.io.*;

public class FindElement
{
	private Scanner fileReader;
	private File info;

	public FindElement()
	{
		info = new File("periodInformation.txt");
		resetScanner();
	}

	public void resetScanner()
	{
		try
		{
			fileReader = new Scanner(info);
		}
		catch(FileNotFoundException a)
		{
			System.out.println("Could not find file : periodInformation.txt\n\n");
			System.exit(1);
		}
	}

	public double findMass(String element, int num)
	{
		resetScanner();
		String line = "";
		while(fileReader.hasNext())
		{
			line = fileReader.nextLine();
			if(line.indexOf(element) != -1)
			{
				String mass = line.substring(line.indexOf("[")+1);
				return num*Double.parseDouble(mass);
			}
		}
		return 0.0;
	}
}