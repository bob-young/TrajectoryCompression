package douglasPeucke;

public class Pretreatment
{
	public Pretreatment()
	{
	}

	// 角度制弧度制转化
	public static double Rad(double d)
	{
		return d * Math.PI / 180.0;
	}

	// 根据经纬度算两点距离
	public static double geodist(double lat1, double lon1, double lat2, double lon2)
	{
		double radLat1 = Rad(lat1);
		double radLat2 = Rad(lat2);
		double delta_lon = Rad(lon2 - lon1);
		double top_1 = Math.cos(radLat2) * Math.sin(delta_lon);
		double top_2 = Math.cos(radLat1) * Math.sin(radLat2) - Math.sin(radLat1) * Math.cos(radLat2) * Math.cos(delta_lon);
		double top = Math.sqrt(top_1 * top_1 + top_2 * top_2);
		double bottom = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(delta_lon);
		double delta_sigma = Math.atan2(top, bottom);
		double distance = delta_sigma * 6378137.0;

		return distance;
	}

	// 一点到另外两点连线的距离
	public static double distancePointToLine(LatLongPoint pa, LatLongPoint pb, LatLongPoint pc)
	{

		double a = Math.abs(geodist(pa.lat, pa.lon, pb.lat, pb.lon));
		double b = Math.abs(geodist(pa.lat, pa.lon, pc.lat, pc.lon));
		double c = Math.abs(geodist(pb.lat, pb.lon, pc.lat, pc.lon));

		double p = (a + b + c) / 2;
		double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));

		double distance = area * 2 / b;

		return distance;
	}
}
