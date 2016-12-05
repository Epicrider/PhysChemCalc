public class Molecule
{
	private String name;
	private int elementCount;//element = number of different elements that make up molecule
	private double totalMass;//total mass of molecule in gram/mol units
	private int [] entityCount;//holds # of atoms of each specified element (in correspondence with elements[])
	private String [] elements;//holds the different individual elements that make up molecule
	private FindElement find;

	public Molecule(String n)
	{
		name = n;
		elementCount = 0;
		totalMass = 0.0;
		entityCount = new int[n.length()];
		elements = new String[n.length()];
		find = new FindElement();
	}

	public void calculateAttr()
	{
		setEntityCountNElementArrays();//arrays need to be populated before setting total mass
		setTotalMass(); 
	}

	public void setTotalMass()
	{
		for(int i = 0; i<= elementCount-1; i++)
			totalMass+= find.findMass(elements[i],entityCount[i]); // passes element to find, and the # of that element to calculate
	}

	public String getName()
	{
		return name;
	}

	public double getTotalMass()
	{
		return totalMass;
	}

	public String[] getElementArray()
	{
		return elements;
	}

	public int[] getEntityCountArray()
	{
		return entityCount;
	}

	public void setEntityCountNElementArrays()
	{
		int counter = 0;
		int counter2 = 0;
		String dig = "";

		for(int i = 0; i<= name.length()-1; i++)
		{
			if(name.charAt(i)>= 65 && name.charAt(i)<=90)
			{
				if(i>0 && dig.length() != 0)//
				{
					entityCount[counter] = Integer.parseInt(dig);
					counter++;
				}
				else if(i>0 && dig.length() == 0)
				{
					entityCount[counter] = 1;
					counter++;
				}
				dig = "";

				if(i!= name.length()-1 && name.charAt(i+1) >= 97 && name.charAt(i+1) <= 122) //good example of short-circuit eval
				{
					elements[counter2] = ""+name.charAt(i)+name.charAt(i+1);
					i++;
				}
				else
					elements[counter2] = ""+name.charAt(i);
				
				elementCount++;
				counter2++;
			}
			else
			{
				dig+=name.charAt(i);
			}
		}

		if(name.endsWith(elements[counter2-1]))
			entityCount[counter] = 1;
		else
		{
			String temp = name.substring(name.lastIndexOf(elements[counter2-1]));
			while(temp.charAt(0) >= 97 && temp.charAt(0) <= 122 || temp.charAt(0)>= 65 && temp.charAt(0)<=90)
				temp = temp.substring(1);
			entityCount[counter] = Integer.parseInt(temp);
		}
	}
}