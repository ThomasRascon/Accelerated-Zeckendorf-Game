import java.util.*;
import GenerateGraph.*;

public class WinningPlayer{

  static HashMap<Double, GameState> stateMap = new HashMap<>();
  static Queue<GameState> q = new LinkedList<>();
  static List<GameState> stateList = new ArrayList<>();
  static List<Integer> indexList = new ArrayList<>();
  static int minTerms = 2147483647;
  static int numPaths = 0;

  public static void main(String[] args){
    
    Scanner reader = new Scanner(System.in);
    System.out.printf("Please enter the starting value: ");
    int input1 = reader.nextInt();
    System.out.printf("Please enter the ending value: ");
    int input2 = reader.nextInt();
    System.out.printf("Enter 2 to see number of paths, 1 to see the states, enter 0 to see the moves: ");
    int response = reader.nextInt();
    
    //Checks that the input is valid (i.e, a non-negative integer)
    if(input1 < 1 || input2 < 1){
      System.out.println("Invalid input");
      System.exit(1);
    }
    
    for(int h = input1; h <= input2; h++){

        GameState firstState = new GameState(h);
        stateList.add(firstState);
        indexList.add(stateList.size()-1);
        q.add(firstState);

        //Creates the map
        while(q.size() != 0){
            GameState currState = q.remove();
            findChildren(currState);
        }
        indexList.add(stateList.size());
        q.clear();
        
        for(int i = 0; i < stateList.size(); i++){
          GameState currState = stateList.get(i);
          doubleConnect(currState);
        }

        //Colors the map
        for(int i = indexList.size()-1; i > 0; i--){
            for(int j = indexList.get(i-1); j < indexList.get(i); j++){
                GameState currState = stateList.get(j);
                for(int k = 0; k < currState.getNumChildren(); k++){
                    GameState childState = currState.getChild(k);
                    if(childState.getWinningState() == false){
                        currState.setWinningState();
                        k = currState.getNumChildren();
                    }
                }
            }
        }
   
        if(firstState.getWinningState()){
            winner = 1;
        }
        System.out.println(h+": "+"player "+winner);
    
        stateMap.clear();
        stateList.clear();
        indexList.clear();
        q.clear();
        minTerms = 2147483647;
        numPaths = 0;
    }//EOF for
    
    reader.close();
  }//EOF main
  
}//EOF WinningPlayer class
