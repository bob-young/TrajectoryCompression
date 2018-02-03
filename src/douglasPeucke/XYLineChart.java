package douglasPeucke;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class XYLineChart extends ApplicationFrame 
{
   public XYLineChart( String applicationTitle, String chartTitle )
   {
      super(applicationTitle);

      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "x" ,
         "116" ,
         createDataset() ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesPaint( 2 , Color.YELLOW );
      renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 0.5f ) );
      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
   }
   
   private XYDataset createDataset( )
   {
	   
	  String file=System.getProperty("user.dir");
	  List<Float> data1 = new ArrayList<Float>();
	  data1=readFileByLines(file+"//DouglasPeuckeResult_2.log");
	  List<Float> data2 = new ArrayList<Float>();
	  data2=readFileByLines(file+"//DouglasPeuckeResult_1.log");
      final XYSeries firefox = new XYSeries( "after compress" );          
      for(int i=0;i<data1.size();i=i+2){
    	  firefox.add(data1.get(i), data1.get(i+1));
      }
      final XYSeries chrome = new XYSeries( "before compress" );          
      for(int i=0;i<data2.size();i=i+2){
    	  chrome.add(data2.get(i), data2.get(i+1));
      }
      final XYSeries iexplorer = new XYSeries( "InternetExplorer" );          
//      iexplorer.add( 3.0 , 4.0 );          
//      iexplorer.add( 4.0 , 5.0 );          
//      iexplorer.add( 5.0 , 4.0 );          
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( firefox );          
      dataset.addSeries( chrome );
      //dataset.addSeries( iexplorer );
      return dataset;
   }

   public static void main( String[ ] args ) 
   {
	   XYLineChart chart = new XYLineChart("trajectory", "compare");
      chart.pack( );          
      RefineryUtilities.centerFrameOnScreen( chart );          
      chart.setVisible( true ); 
   }


   public List<Float> readFileByLines(String fileName){
	   File file=new File(fileName);
	   System.out.println(fileName);
	   List<Float> fl = new ArrayList<Float>();
	   BufferedReader reader=null;
	   try{
		   reader = new BufferedReader(new FileReader(file));
		   String tempString = null;
		   		int line = 1;
		   		while ((tempString = reader.readLine())!=null){
		   			//System.out.println("line" + line + ": ");
		   			String[] tmp=tempString.split(",");
		   			//System.out.println(Float.parseFloat(tmp[1])+"**" + Float.parseFloat(tmp[2]));
		   			fl.add(Float.parseFloat(tmp[1]));
		   			float x=(float) (Float.parseFloat(tmp[2])-116);
		   			fl.add(x);
		   			line++;
		   		}
		   reader.close();
		   reader = new BufferedReader(new FileReader(file));
	   }catch (IOException e){
		   e.printStackTrace();
	   }finally{
		   if(reader!=null){
			   try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	   }
	   
	   return fl;
   }
}