import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.data.category.DefaultCategoryDataset;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;


public class MyData {
	private BufferedReader in;
	private int id;
	private  Map<Integer, Integer> result;
	
	public MyData(BufferedReader in, int id) {
		this.in = in;
		this.id = id;
		this.result = new HashMap<>();
	}
	
	public void getListMessage() throws NumberFormatException, IOException{
		String data;
		int nbSeq = -1, nbMsg = 0;
		while((data = in.readLine()) != null){
			String[] info = data.split(" ");
			if(Integer.valueOf(info[0]) == id) {
				if(Integer.valueOf(info[2])==nbSeq+1) {
					nbMsg++;
					result.put(Integer.valueOf(info[2]),nbMsg);
					nbSeq ++;
				}else {
					while(nbSeq < Integer.valueOf(info[2])) {
						result.put(nbSeq,nbMsg);
						nbSeq ++;
					}nbMsg++;
					result.put(Integer.valueOf(info[2]),nbMsg);
					nbSeq++;
				}
			}
		}
	}
	
	public void siteGraph(DefaultCategoryDataset dataset, String line) throws NumberFormatException, IOException {
    	this.getListMessage();
    	for(Entry e : result.entrySet()) {
    		System.out.println((Number) e.getValue()+" "+ (Comparable) e.getKey());
    		dataset.addValue((Number) e.getValue(), line, (Comparable) e.getKey());
    	}
	}
	
	public void afficheMap() {
		System.out.println("affichage");
    	for(Entry e : result.entrySet()) {
    		System.out.println(e.getKey() +"  "+ e.getValue());
    	}
	}
	
    public ObservableList<XYChart.Data>dataFromFile() throws IOException{
    	ObservableList<XYChart.Data> listPoint  =  FXCollections.observableArrayList();
    	this.getListMessage();
    	for(Entry e : result.entrySet()) {
    		listPoint.add(new XYChart.Data(e.getKey(), e.getValue()));
    	}
    	
    	return listPoint;
    }
}
