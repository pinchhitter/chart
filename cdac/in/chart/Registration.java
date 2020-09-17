package cdac.in.chart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class Registration extends ApplicationFrame {

	public Registration( String applicationTitle , String chartTitle ) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createLineChart( chartTitle, "Days","Number of Candidats", createDataset(), PlotOrientation.VERTICAL, true,true,false);

		ChartPanel chartPanel = new ChartPanel( lineChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );
	}

	private DefaultCategoryDataset createDataset( ) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		try{	
			BufferedReader br = new BufferedReader(new FileReader(new File("2020R.csv")));	
			String line = null;
			boolean head = true;
			int total = 0;
			int day = 1;
			while( (line = br.readLine()) != null ){
				if( head ){
					head = false;
					continue;
				}	
				String[] token = line.split(",");
				total += Integer.parseInt( token[1].trim() );
				dataset.addValue( total , "2020" , ""+day );
				day++;	
			}

			br = new BufferedReader(new FileReader(new File("2019R.csv")));	
			head = true;
			total = 0;
			day = 1;
			while( (line = br.readLine()) != null ){
				if( head ){
					head = false;
					continue;
				}	
				String[] token = line.split(",");
				total += Integer.parseInt( token[1].trim() );
				dataset.addValue( total , "2019" , ""+day );
				day++;	
			}

			br = new BufferedReader(new FileReader(new File("2021R.csv")));	
			head = true;
			total = 0;
			day = 1;
			while( (line = br.readLine()) != null ){
				if( head ){
					head = false;
					continue;
				}	
				String[] token = line.split(",");
				total += Integer.parseInt( token[1].trim() );
				dataset.addValue( total , "2021" , ""+day );
				day++;	
			}

		}catch(Exception e){
			e.printStackTrace();
		}		
		/*
		dataset.addValue( 15 , "schools" , "1970" );
		dataset.addValue( 30 , "schools" , "1980" );
		dataset.addValue( 60 , "schools" ,  "1990" );
		dataset.addValue( 120 , "schools" , "2000" );
		dataset.addValue( 240 , "schools" , "2010" );
		dataset.addValue( 300 , "schools" , "2014" );
		*/
		return dataset;
	}

	public static void main( String[ ] args ) {
		Registration chart = new Registration(
				"Day vs Regisration" ,
				"Numer of Registration vs Day");
		chart.pack( );
		RefineryUtilities.centerFrameOnScreen( chart );
		chart.setVisible( true );
	}
}
