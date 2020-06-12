import javax.swing.JFrame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
public class GraphPanne2 {
	private ChartFrame frame = null;
	private void displayChart() {
	    if (this.frame == null) {
	        final String title = "Pannes";
	        final String xAxisLabel = "Tasks";
	        final String yAxisLabel = "Amplitude";
	        final XYDataset data = createStepXYDataset();
	        final JFreeChart chart = ChartFactory.createXYStepChart(
	            title,
	            xAxisLabel, yAxisLabel,
	            data,
	            PlotOrientation.VERTICAL,
	            true,   // legend
	            true,   // tooltips
	            false   // urls
	        );
	        chart.setBackgroundPaint(new Color(216, 216, 216));
	        final XYPlot plot = chart.getXYPlot();
	        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
	        plot.getRenderer().setSeriesStroke(1, new BasicStroke(2.0f));
	        this.frame = new ChartFrame("Plan Comparison", chart);
	        this.frame.pack();
	        RefineryUtilities.positionFrameRandomly(this.frame);
	        this.frame.setVisible(true);
	    }
	    else {
	        this.frame.setVisible(true);
	        this.frame.requestFocus();
	    }
	   }

	 public XYDataset createStepXYDataset() {
	  BufferedReader in = new BufferedReader(new InputStreamReader(
	    	    this.getClass().getResourceAsStream("configFile.txt")));
	  Map<Integer,BufferedReader> fileReader =new HashMap<>();
	  List<Integer> sites = new ArrayList<>();
	  try {
		  int nbSite =0;
		  String data;
		  while((data=in.readLine()) != null){
			  String[] info = data.split(" ");
			  File file = new File("src/logFile"+info[0]+".txt");
			  if(file.getAbsoluteFile().exists()) {
				  BufferedReader bur = new BufferedReader(new InputStreamReader(
					  this.getClass().getResourceAsStream("logFile"+info[0]+".txt")));
				  fileReader.put(Integer.valueOf(info[0]), bur);
				  sites.add(Integer.valueOf(info[0]));
			  }
		  }
			  
	  } catch (IOException e) {
		  e.printStackTrace();
	  } 
	  
	 final XYSeries series1 = new XYSeries("Series 2");
	 series1.add(0, 5);
	 series1.add(1, 0);
	 series1.add(2, 0);
	 series1.add(3, 0);
	 series1.add(4, 0);
	 series1.add(5, 5);
	 series1.add(6, 0);
	 series1.add(7, 0);
	 series1.add(8, 0);
	 final XYSeriesCollection dataset = new XYSeriesCollection();
	 dataset.addSeries(series1);
	 return dataset;
	  }
	 
	 
	  public static void main(final String[] args) {
	    final GraphPanne2 demo = new GraphPanne2();
	    demo.displayChart();

	   }

}
