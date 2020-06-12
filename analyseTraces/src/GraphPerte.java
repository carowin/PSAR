
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 */
public class GraphPerte extends JFrame {

  private static final long serialVersionUID = 1L;

  
  public GraphPerte(String title) {
    super(title);
    // Create dataset
    DefaultCategoryDataset dataset = createDataset();
    // Create chart
    JFreeChart chart = ChartFactory.createLineChart(
        "Pertes de message du site", // Chart title
        "Numéro de séquence", // X-Axis Label
        "Nombre de messages", // Y-Axis Label
        dataset
        );

    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }
  

  
  
  private DefaultCategoryDataset createDataset() {
	  BufferedReader in = new BufferedReader(new InputStreamReader(
	    	    this.getClass().getResourceAsStream("configFile.txt")));
	  BufferedReader myBr = null;
	  List<BufferedReader> fileReader = new ArrayList<>();
	  Integer id = 2;
	  try {
		  int nbSite =0;
		  String data;
		  while((data=in.readLine()) != null){
			  String[] info = data.split(" ");
			  File file = new File("src/logFile"+info[0]+".txt");
			  if(file.getAbsoluteFile().exists()) {
				  if(Integer.valueOf(info[0])!=id) {
					  BufferedReader bur = new BufferedReader(new InputStreamReader(
							  this.getClass().getResourceAsStream("logFile"+info[0]+".txt")));
				  fileReader.add(bur);
				  }else {
					  myBr = new BufferedReader(new InputStreamReader(
							  this.getClass().getResourceAsStream("logFile"+info[0]+".txt")));
				   }
			  }
		  }
	   } catch (IOException e) {
		   e.printStackTrace();
	   } 
	   DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	   SiteData data1 = new SiteData(id);
	   try {
		   Map<Integer, Integer> result = data1.getEveryMessagesSend(fileReader);
	    	for(Entry e : result.entrySet()) {
	    		dataset.addValue((Number) e.getValue(), "site"+id, (Comparable) e.getKey());
	    	}
	} catch (IOException e) {
		e.printStackTrace();
	}
	   
    return dataset;
  
  }
  
  

  public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {
      GraphPerte example = new GraphPerte("Analyse Trace");
      example.setAlwaysOnTop(true);      
      example.pack();
      example.setSize(600, 400); 
      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      example.setVisible(true);
    });
  }
}