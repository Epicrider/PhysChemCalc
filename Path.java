public class Path
{
	private String start;
	private String end;
	private String path;

	private final String AMU_TO_GRAM_PER_MOL = "amu>gram>mol>particles>gram/mol";
	private final String GRAM_PER_MOL_TO_AMU = "gram/mol>particles>mol>gram>amu";
	private final String METRIC_TO_GRAM = "metric>imperial>gram";
	private final String GRAM_TO_METRIC = "gram>imperial>metric";

	public Path(String a, String b)
	{
		start = a;
		end = b;
		path = new String("");
	}

	public String getSecondaryPath()
	{
		if(METRIC_TO_GRAM.indexOf(start) < METRIC_TO_GRAM.indexOf("gram"))
		{
			start = "gram";
			return METRIC_TO_GRAM.substring(METRIC_TO_GRAM.indexOf(start));
		}
		else if(METRIC_TO_GRAM.indexOf("gram") < METRIC_TO_GRAM.indexOf(end))
		{
			return METRIC_TO_GRAM.substring(METRIC_TO_GRAM.indexOf("gram"), METRIC_TO_GRAM.indexOf(end))+end;
		}
		return "";
	}

	public String getMainPath()
	{
		if(AMU_TO_GRAM_PER_MOL.indexOf(start) < AMU_TO_GRAM_PER_MOL.indexOf(end))
			return AMU_TO_GRAM_PER_MOL.substring(AMU_TO_GRAM_PER_MOL.indexOf(start), AMU_TO_GRAM_PER_MOL.indexOf(end))+end;
		else
			return GRAM_PER_MOL_TO_AMU.substring(GRAM_PER_MOL_TO_AMU.indexOf(start), GRAM_PER_MOL_TO_AMU.indexOf(end))+end;

	}
}
//