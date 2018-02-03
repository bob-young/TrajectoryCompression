package douglasPeucke;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class SlidingWindow
{
	public static void main(String[] args) throws Exception
	{
		long begin = System.currentTimeMillis(); // ��δ�����ڳ���ִ��ǰ

		// ��ʼ���ݴ洢����
		ArrayList<LatLongPoint> initList = new ArrayList<LatLongPoint>();
		// ���ڹ켣ѹ����������ݵĴ洢����
		ArrayList<LatLongPoint> compressionList = new ArrayList<LatLongPoint>();
		// ������ݴ洢����
		ArrayList<LatLongPoint> resultList = new ArrayList<LatLongPoint>();

		// �����ļ������Ͳ���
		File inFile = new File("2007-10-14-GPS.log");
		File outFile_1 = new File("SlidingWindowResult_1.log");
		File outFile_2 = new File("SlidingWindowResult_2.log");

		FileOperations fileOperations = new FileOperations();

		// ��ȡ����
		initList = fileOperations.getPointFromFile(inFile);
		fileOperations.writePointToFile(outFile_1, initList);

		// �Լ������ݽ���Ԥ����
		int size = initList.size();
		LatLongPoint[] pointInitArray = new LatLongPoint[size]; // ���ݴ���������ڲ�ѯ
		Iterator<LatLongPoint> temp = initList.iterator();
		int i = 0;
		for (i = 0; i < size; i++)
		{
			pointInitArray[i] = temp.next();
		}

		// �켣ѹ������
		int maxDistance = 30;
		swTrajectoryCompression(pointInitArray, compressionList, maxDistance);

		// ������ŶԽ�����ݽ�������
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

		// ������
		double compressioinRate = (double) resultSize / size;
		System.out.println(compressioinRate);
		for (i = 0; i < resultSize; i++)
		{
			resultList.add(pointResultArray[i]);
		}

		fileOperations.writePointToFile(outFile_2, resultList);

		long endend = System.currentTimeMillis() - begin; // ��δ�����ڳ���ִ�к�
		System.out.println("��ʱ��" + endend + "����");
	}

	// �жϴ����ڸ��㵽�߾����Ƿ񳬹���ֵ
	public static boolean isUnderMaxDistance(ArrayList<LatLongPoint> list, LatLongPoint[] array, int start, int end, double maxDistance)
	{
		int size = list.size();
		int i = 0;
		double distance = 0;

		for (i = 1; i < size - 2; i++)
		{
			distance = Pretreatment.distancePointToLine(array[start], array[start + i], array[end]);
			if (distance > maxDistance)
				return false;
		}

		return true;
	}

	public static void swTrajectoryCompression(LatLongPoint[] array, ArrayList<LatLongPoint> list, double maxDistance)
	{
		int i = 0;
		int size = array.length;
		int start = 0;
		int end = 0;
		ArrayList<LatLongPoint> tmpList = new ArrayList<LatLongPoint>();

		list.add(array[0]);
		while (i < size - 1)
		{
			start = i;
			tmpList.add(array[i]);
			tmpList.add(array[++i]);
			end = i;
			while ((i + 1) < size && (end - start == 1 || isUnderMaxDistance(tmpList, array, start, end, maxDistance)))
			{
				tmpList.add(array[++i]);
				end++;
			}

			if (i == size - 1 && isUnderMaxDistance(tmpList, array, start, end, maxDistance))
				list.add(array[i]);
			else
				list.add(array[--i]);

			tmpList.clear();
		}
	}
}
