
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.ArrayList;
public class CharactersInPlay {
    private ArrayList<String> character;
    private ArrayList<Integer> myFreqs;
    public CharactersInPlay() {
        character = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    public void update(String person){
        person = person.toLowerCase();
        int index = character.indexOf(person);
        
            if (index == -1){
                character.add(person);
                myFreqs.add(1);
            }
            else {
                int freq = myFreqs.get(index);
                myFreqs.set(index,freq+1);
            }
        }
    public void findAllCharacters()
    {
        character.clear();
        myFreqs.clear();
        FileResource resource = new FileResource();
        for(String s : resource.lines()){
        int index = s.indexOf(".");
        if( index != -1)
        {
           
            String person = s.substring(0,index);
            
            update(person);
        }
    }
    }
    public void tester()
    {
        findAllCharacters();
        for( int index =0; index <character.size();index++)
        {
            //System.out.println("Character = "+character.get(index)+"   and freq is "+ myFreqs.get(index));
        }
        int max = findMax();
        System.out.println("Main character is " +character.get(max) +" and freq is "+ myFreqs.get(max));
        charactersWithNumParts(10,15);
    }
    public int findMax(){
        int max = myFreqs.get(0);
        int maxIndex = 0;
        for(int k=0; k < myFreqs.size(); k++){
            if (myFreqs.get(k) > max){
                max = myFreqs.get(k);
                maxIndex = k;
            }
            
        }
        
        return maxIndex;
    }
    public void charactersWithNumParts(int num1, int num2)
    {
        System.out.println("freq is between "+num1+" and "+num2);
        for(int i = 0 ; i < myFreqs.size();i++)
        {
            if(myFreqs.get(i) >= num1 && myFreqs.get(i) <=num2)
            System.out.println("Character ="+character.get(i)+"   and freq is "+ myFreqs.get(i));
        }
    }
}
