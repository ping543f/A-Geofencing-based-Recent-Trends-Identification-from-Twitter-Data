package app.home;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class BarChart_AWT extends ApplicationFrame
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public BarChart_AWT( String applicationTitle , String chartTitle )
   {
      super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Tweets",            
         "Frequency",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
   }
   private CategoryDataset createDataset( )
   {
      final String fiat = "TWEETS";          
       //String speed = "Speeds";      
       String sql= "SELECT * FROM frequency ORDER BY count desc LIMIT 10";
       DataAccess da= new DataAccess();
       ResultSet rs=da.getResultSet(sql);
       
             
       DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  

      try {
		while (rs.next()) {
			  dataset.addValue( rs.getInt("count") , fiat , rs.getString("text") );
			  System.out.println(rs.getInt("count")+"   "+rs.getString("text"));
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
      
          

                  

      return dataset; 
   }
   public static void myMain( )
   {
      BarChart_AWT chart = new BarChart_AWT("DIFFERENT TWEETS FREQUENCY", " DIFFERENT TWEETS FREQUENCY");
      chart.pack( );        
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true ); 
   }
}