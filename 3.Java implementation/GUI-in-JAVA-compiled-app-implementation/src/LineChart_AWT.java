package app.home;
import org.jfree.chart.ChartPanel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame
{
	static String sql_t;
   public LineChart_AWT( String applicationTitle , String chartTitle )
   {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Date","Number of Tweets",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }
   
   DataAccess da= new DataAccess();
   ResultSet rs= da.getResultSet(sql_t);

   private DefaultCategoryDataset createDataset( )
   {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      try {
		while (rs.next()) {
			  dataset.addValue( rs.getInt("count") , "Tweets" , rs.getString("date") );
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      

      return dataset;
   }
   public static void myMain( String query ) 
   {
	  sql_t=query; 
      LineChart_AWT chart = new LineChart_AWT(
      "Tweets Vs Date" ,
      "Number of Tweets vs Date");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}