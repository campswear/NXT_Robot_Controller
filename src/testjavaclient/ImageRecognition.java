package testjavaclient;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.neuroph.contrib.imgrec.ImageRecognitionPlugin;
import org.neuroph.core.NeuralNetwork;

public class ImageRecognition {

	String filename;
	
	public ImageRecognition(String filename)
	{
		this.filename = filename;
	}
	
	void recognize()
	{
	    // load trained neural network saved with easyNeurons (specify existing neural network file here)
	    NeuralNetwork nnet = NeuralNetwork.load("/home/linda/Desktop/images/trained.nnet"); // load trained neural network saved with easyNeurons
	    // get the image recognition plugin from neural network
	    ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.IMG_REC_PLUGIN_NAME); // get the image recognition plugin from neural network
	    String[] strArr=new String[20];
	    double[] doubleArr=new double[20];
	    try {
	         // image recognition is done here (specify some existing image file)
	        HashMap<String, Double> output = imageRecognition.recognizeImage(new File(filename));
	        //System.out.println(output.toString());
	       // strArr[i]=output.toString()
	        output=sortHashMapByValuesD(output);
	        //System.out.println(output.toString());


	    } catch(IOException ioe) {
	        ioe.printStackTrace();
	    }
	}
	

 
 //SORT THE HASHMAP
 public static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
	    List mapKeys = new ArrayList(passedMap.keySet());
	    List mapValues = new ArrayList(passedMap.values());
	    Collections.sort(mapValues);
	    Collections.sort(mapKeys);

        System.out.println("Matching rate from lowest to highest:");
	    LinkedHashMap sortedMap = 
	        new LinkedHashMap();
	    
	    Iterator valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        Object val = valueIt.next();
	        Iterator keyIt = mapKeys.iterator();
	        
	        while (keyIt.hasNext()) {
	            Object key = keyIt.next();
	            String comp1 = passedMap.get(key).toString();
	            String comp2 = val.toString();
	            
	            if (comp1.equals(comp2)){
	                passedMap.remove(key);
	                mapKeys.remove(key);
	                sortedMap.put((String)key, (Double)val);
	                System.out.println((String)key +" " + (Double)val);
	                break;
	            }

	        }

	    }
	    return sortedMap;
	}
/*
	public static void main(String[] args) {
		ImageRecognition imagerecog = new ImageRecognition("/home/linda/Desktop/images/testImage/testimage.JPG");
		imagerecog.recognize();
	}
*/
}