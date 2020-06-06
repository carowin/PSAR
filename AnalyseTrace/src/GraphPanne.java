import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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

  private int nbSite;
  
  
  public GraphPanne(String title) {
    super(title);
    // Create dataset
    DefaultCategoryDataset dataset = createDataset();
    // Create chart
    JFreeChart chart = ChartFactory.createLineChart(
        "Panne des sites", // Chart title
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
	  try {
		  int nbSite =0;
		  while(in.readLine() != null){
			  this.nbSite ++;
		  }
	  } catch (IOException e) {
		  e.printStackTrace();
	  } 
	  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	  for(int i=0; i<nbSite; i++) {
		  File file = new File("src/logFile"+(i+1)%nbSite+".txt");
		  System.out.println(file.getAbsoluteFile().exists());
		  int idFile = i;
		  if(file.getAbsoluteFile().exists()) {
			  BufferedReader reader = new BufferedReader(new InputStreamReader(
					  this.getClass().getResourceAsStream("logFile"+(i+1)%nbSite+".txt")));
			  MyData data1 = new MyData(reader, i);
			  try {
				data1.siteGraph(dataset, "site "+i);
			  } catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			  }
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