package cdac.in.chart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

import java.awt.Color;
import java.awt.BasicStroke;
import org.jfree.chart.plot.CategoryPlot;

public class Payment extends ApplicationFrame {

	public Payment( String applicationTitle , String chartTitle ) {
		super(applicationTitle);
		boolean showLegend = true;
		boolean createTooltip = true;
		boolean createURL = false;
 
		JFreeChart chart = ChartFactory.createLineChart(chartTitle, "Days", "No of Candidats", createDataset(), PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);

		customizeChart( chart );

		ChartPanel chartPanel = new ChartPanel( chart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 760 , 467 ) );
		setContentPane( chartPanel );
	}

	private void customizeChart(JFreeChart chart) {

		CategoryPlot plot = chart.getCategoryPlot();
		LineAndShapeRenderer renderer = new LineAndShapeRenderer();

		// sets paint color for each series
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(1, Color.GREEN);
		renderer.setSeriesPaint(2, Color.BLUE);

		// sets thickness for series (using strokes)
		renderer.setSeriesStroke(0, new BasicStroke(3.0f));
		renderer.setSeriesStroke(1, new BasicStroke(3.0f));
		renderer.setSeriesStroke(2, new BasicStroke(3.0f));
		
		// sets paint color for plot outlines
		plot.setOutlinePaint(Color.BLUE);
		plot.setOutlineStroke(new BasicStroke(1.0f));
		
		// sets renderer for lines
		plot.setRenderer(renderer);
		
		// sets plot background
		plot.setBackgroundPaint(Color.CYAN);
		
		// sets paint color for the grid lines
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);
		
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
	}


	int addDataSet(String filename, DefaultCategoryDataset dataset, int nofdays){

		try{
			BufferedReader br = new BufferedReader(new FileReader(new File( filename )));	

			String year = filename.substring(0,4);

			String line = null;
			boolean head = true;
			int total = 0;
			int days = 1;

			while( (line = br.readLine()) != null ){
				if( head ){
					head = false;
					continue;
				}	
				String[] token = line.split(",");

				if( token.length < 2)
					token = line.split("\\|");
				if( token.length < 2)
					continue;

				total += Integer.parseInt( token[1].trim() );
				dataset.addValue( total , year , ""+days );
				days++;	
				if( days >= nofdays)
					break;
			}

			return days;

		}catch(Exception e){
			e.printStackTrace();
		}		
		return 0;
	}

	private DefaultCategoryDataset createDataset( ) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

		try{
			int count = addDataSet( "2021P.csv", dataset, 100);
			addDataSet( "2020P.csv", dataset, count);
			addDataSet( "2019P.csv", dataset, count);

		}catch(Exception e){
			e.printStackTrace();
		}
		return dataset;			
	}

	public static void main( String[ ] args ) {

		Payment chart = new Payment( "Payment Vs Days" , "Progress");
		chart.pack();
		RefineryUtilities.centerFrameOnScreen( chart );
		chart.setVisible( true );
	}
}
