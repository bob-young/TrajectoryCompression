package douglasPeucke;

import java.text.DecimalFormat;

public class LatLongPoint implements Comparable<LatLongPoint>
{
	public int id;
	public double lat;// γ��
	public double lon;// ����

	public LatLongPoint()
	{
	}

	public String toString()
	{
		return this.id + "," + this.lat + "," + this.lon;
	}

	public String toResultString()
	{
		DecimalFormat df = new DecimalFormat("0.0000");
		return this.id + "," + df.format(this.lat) + "," + df.format(this.lon);
	}

	public int compareTo(LatLongPoint other)
	{
		if (this.id < other.id)
			return -1;
		else if (this.id > other.id)
			return 1;
		else
			return 0;
	}
}
