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
	private String mainPath;
	private String secondaryPath;

	public MolecularMath(String a, String b, double given, Molecule m)
	{
		path = new Path(a,b);
		givenValue = given;
		molecule = m;
	}

	public void executePath(MolecularScreen screen)
	{
		secondaryPath = path.getSecondaryPath();
		mainPath = path.getMainPath();
	}
}