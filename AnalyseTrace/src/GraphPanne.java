import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
public class GraphPanne extends JFrame {

  private static final long serialVersionUID = 1L;

  
  public GraphPanne(String title) {
    super(title);
    // Create dataset
    DefaultCategoryDataset dataset = createDataset();
    // Create chart
    JFreeChart chart = ChartFactory.createLineChart(
        "Panne des sites", // Chart title
        "Temps(ms)", // X-Axis Label
        "Nombre de messages", // Y-Axis Label
        dataset
        );

    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }

  
  
  private DefaultCategoryDataset createDataset() {
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
	  
	  for(int i=0; i<sites.size();i++) {
		  System.out.println("SITES : "+ i +"= "+sites.get(i));
	  }
	  
	  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	  
	  for(int i=0; i<sites.size(); i++) {
		  BufferedReader siteReader = null;
		  if(i == sites.size()-1) {
			  siteReader = fileReader.get(sites.get(0)); 
		  }else {
			  siteReader = fileReader.get(sites.get(i+1));
		  }
		  try {
			  
			  SiteData data1 = new SiteData(sites.get(i));
			  Map<Integer, Integer> result = data1.getListMessage(siteReader);
		      for(Entry<Integer, Integer> e : result.entrySet()) {
		    	System.out.println(e.getValue() + " : "+ e.getKey() + "sur le site "+sites.get(i));
		    	dataset.addValue((Number) e.getKey(), "site "+sites.get(i), (Comparable) e.getValue());
		      }
		  } catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		  } 
	  }
	return dataset;
  }
  
  

  public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {
      GraphPanne example = new GraphPanne("Analyse Trace");
      example.setAlwaysOnTop(true);      
      example.pack();
      example.setSize(600, 400); 
      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      example.setVisible(true);
    });
  }
}