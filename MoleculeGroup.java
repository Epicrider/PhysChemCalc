import java.util.ArrayList;

public class MoleculeGroup
{
	private ArrayList<Molecule> reactants;
	private ArrayList<Molecule> products;

	public MoleculeGroup()
	{
		reactants = new ArrayList<Molecule>();
		products = new ArrayList<Molecule>();
	}

	public ArrayList<Molecule> getReactants()
	{
		return reactants;
	}

	public ArrayList<Molecule> getProducts()
	{
		return products;
	}

	public void setMoleculeList(String equation)
	{
		String reactantsSide = equation.substring(0,equation.indexOf("->")).trim();
		String productsSide = equation.substring(equation.indexOf("->")+2).trim();

		if(!reactantsSide.contains("+") && !productsSide.contains("+"))
		{
			System.out.println("1-1 ratio, already balanced!");
			System.exit(1);
		}

		String moleculeName = "";
		int indexCounter = 0;
		if(!reactantsSide.contains("+"))
		{
			moleculeName = reactantsSide;
			reactants.add(new Molecule(moleculeName.trim()));
		}
		else
		{
			while(reactantsSide.contains("+"))
			{
				moleculeName = reactantsSide.substring(0,reactantsSide.indexOf("+"));
				reactants.add(new Molecule(moleculeName.trim()));
				reactantsSide = reactantsSide.substring(reactantsSide.indexOf("+")+1);
			}
			reactants.add(new Molecule(reactantsSide.trim()));
		}

		if(!productsSide.contains("+"))
		{
			moleculeName = productsSide;
			products.add(new Molecule(moleculeName.trim()));
		}
		else
		{
			while(productsSide.contains("+"))
			{
				moleculeName = productsSide.substring(0,productsSide.indexOf("+"));
				products.add(new Molecule(moleculeName.trim()));
				productsSide = productsSide.substring(productsSide.indexOf("+")+1);
			}
			products.add(new Molecule(productsSide.trim()));
		}
	}
}