package douglasPeucke;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class DouglasPeucke
{
	public static void main(String[] args) throws Exception
	{
		long begin = System.currentTimeMillis(); // 这段代码放在程序执行前

		// 初始数据存储链表
		ArrayList<LatLongPoint> initList = new ArrayList<LatLongPoint>();
		// 用于轨迹压缩计算的数据的存储链表
		ArrayList<LatLongPoint> compressionList = new ArrayList<LatLongPoint>();
		// 结果数据存储链表
		ArrayList<LatLongPoint> resultList = new ArrayList<LatLongPoint>();

		// 定义文件变量和操作
		File inFile = new File("2007-10-14-GPS.log");
		File outFile_1 = new File("DouglasPeuckeResult_1.log");
		File outFile_2 = new File("DouglasPeuckeResult_2.log");
		FileOperations fileOperations = new FileOperations();

		// 读取数据
		initList = fileOperations.getPointFromFile(inFile);
		fileOperations.writePointToFile(outFile_1, initList);

		// 对计算数据进行预处理
		int size = initList.size();
		LatLongPoint[] pointInitArray = new LatLongPoint[size]; // 数据存入数组便于查询
		Iterator<LatLongPoint> temp = initList.iterator();
		int i = 0;
		for (i = 0; i < size; i++)
		{
			pointInitArray[i] = temp.next();
		}

		// 轨迹压缩计算
		int start = 0;
		int end = size - 1;
		int maxDistance = 30;
		compressionList.add(pointInitArray[start]);
		compressionList.add(pointInitArray[end]);
		dfTrajectoryCompression(pointInitArray, compressionList, start, end, maxDistance);

		// 依据序号对结果数据进行排序
		i = 0;
		temp = compressionList.iterator();
		int resultSize = compressionList.size();
		LatLongPoint[] pointResultArray = new LatLongPoint[resultSize];
		while (temp.hasNext())
		{
			pointResultArray[i] = temp.next();
			i++;
		}
		Arrays.sort(pointResultArray);

		// 结果输出
		double compressioinRate = (double) resultSize / size;
		System.out.println(compressioinRate);
		for (i = 0; i < resultSize; i++)
		{
			resultList.add(pointResultArray[i]);
		}

		fileOperations.writePointToFile(outFile_2, resultList);

		long endend = System.currentTimeMillis() - begin; // 这段代码放在程序执行后
		System.out.println("耗时：" + endend + "毫秒");
	}

	public static void dfTrajectoryCompression(LatLongPoint[] pointArray, ArrayList<LatLongPoint> list, int start, int end, double maxDistance)
	{
		if (start < end)
		{
			double currentMaxDistance = 0;
			double currentDistance = 0;
			int currentIndex = 0;
			for (int i = start + 1; i < end; i++)
			{
				currentDistance = Pretreatment.distancePointToLine(pointArray[start], pointArray[i], pointArray[end]);

				if (currentDistance > currentMaxDistance)
				{
					currentMaxDistance = currentDistance;
					currentIndex = i;
				}
			}

			if (currentMaxDistance > maxDistance)
			{
				list.add(pointArray[currentIndex]);
				dfTrajectoryCompression(pointArray, list, start, currentIndex, maxDistance);
				dfTrajectoryCompression(pointArray, list, currentIndex, end, maxDistance);
			}
		}

		return;
	}
}
