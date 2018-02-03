package douglasPeucke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

public class FileOperations
{

	public FileOperations()
	{
	}

	public double dataTrans(String str)
	{
		int i = str.indexOf(".");
		String str_1 = str.substring(0, i - 2);
		String str_2 = str.substring(i - 2);

		double data = Double.parseDouble(str_1) + Double.parseDouble(str_2) / 60;

		return data;
	}

	public ArrayList<LatLongPoint> getPointFromFile(File file) throws Exception
	{
		ArrayList<LatLongPoint> list = new ArrayList<LatLongPoint>();
		if (file.exists() && file.isFile())
		{
			InputStreamReader read = new InputStreamReader(new FileInputStream(file));
			BufferedReader bReader = new BufferedReader(read);

			String str;
			String[] strArr;
			int i = 0;

			while ((str = bReader.readLine()) != null)
			{
				strArr = str.split(" ");
				LatLongPoint p = new LatLongPoint();
				p.id = i++;
				p.lat = dataTrans(strArr[5]);
				p.lon = dataTrans(strArr[3]);
				list.add(p);
			}
			bReader.close();
		}

		return list;
	}

	public void writePointToFile(File file, ArrayList<LatLongPoint> list) throws Exception
	{
		Iterator<LatLongPoint> tmpList = list.iterator();
		RandomAccessFile rFilter = new RandomAccessFile(file, "rw");
		LatLongPoint p = new LatLongPoint();
		while (tmpList.hasNext())
		{
			p = (LatLongPoint) tmpList.next();
			String str = p.toResultString() + "\r\n";
			rFilter.writeBytes(str);
		}
		rFilter.close();

		return;
	}
}
