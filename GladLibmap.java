import edu.duke.*;
import java.util.*;

public class GladLibmap {
    private HashMap<String,ArrayList<String>> myMap;
 
    private ArrayList<String> usedList;
    private ArrayList<String> categoriesList;
    private Random myRandom;
    private int replacecount = 0;
    private int wordcount = 0;
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibmap(){
        myMap = new HashMap<String,ArrayList<String>>();
        categoriesList = new  ArrayList<String>();
         myRandom = new Random();
        initializeFromSource(dataSourceDirectory);
       
       
    }
    
    public GladLibmap(String source){
         myMap = new HashMap<String,ArrayList<String>>();
		 categoriesList = new  ArrayList<String>();
          myRandom = new Random();
       
        initializeFromSource(source);
       
    }
    
    private void initializeFromSource(String source) {
        String[] labels = {"country","adjective","noun","color","name","animal","timeframe","verb","fruit"};
        for(String s : labels)
        {
            ArrayList<String> list = readIt(source+"/"+s+".txt"); 
            
            myMap.put(s,list);
        }
   
        usedList = new ArrayList<String>();
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        if (!categoriesList.contains(label)){
				categoriesList.add(label);
			}
        
         return randomFrom(myMap.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        replacecount++;
        wordcount++;
        while(usedList.contains(sub))
            {
        sub = getSubstitute(w.substring(first+1,last));
        replacecount++;
            }
   
        
        usedList.add(sub);
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public void makeStory(){
        System.out.println("\n");
        usedList.clear();
        replacecount =0;
        wordcount =0;
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println();
System.out.println("Change how many times "+replacecount+"\n"+"how many word changes "+wordcount);
int total = totalWordsInMap();
System.out.println("total number of words in all the ArrayLists "+total);
int labeltotal = totalWordsConsidered();
System.out.println("total number of words in the ArrayLists of the categories "+labeltotal);
    }
    public int totalWordsInMap()
{
    int total = 0;
for(String w : myMap.keySet()){
			ArrayList<String> countList = myMap.get(w);
			total += countList.size();
		}
		return total;
}
public int totalWordsConsidered()
{
    int total = 0;
   for( String s :categoriesList)
   {
       ArrayList<String> countList = myMap.get(s);
       total += countList.size();
    }
    return total;
}


}
