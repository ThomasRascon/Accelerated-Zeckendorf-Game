import java.util.*;
import GenerateGraph.*;

public class AverageLength{

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
    System.out.printf("Enter number of simulations: ");
    int input3 = reader.nextInt();
    
    //Checks that the input is valid (i.e, a non-negative integer)
    if(input1 < 1 || input2 < 1 || input3 < 1){
      System.out.println("Invalid input");
      System.exit(1);
    }
    
    for(int h = input1; h <= input2; h++){

        GameState firstState = new GameState(h);
        stateList.add(firstState);
        indexList.add(stateList.size()-1);
        q.add(firstState);
        int total = 0;

        //Creates the Zeck map
        while(q.size() != 0){
            GameState currState = q.remove();
            findChildren(currState);
        }
        
        //Creates multi Zeck map
        for(int i = 0; i < stateList.size(); i++){
          GameState currState = stateList.get(i);
          doubleConnect(currState);
        }
        
        //Random game play
        for(int i = 0; i < input3; i++){
          GameState currState = firstState;
          Random random = new Random();
          int numMoves = 0;
          int randMove = 0;
          int gameLength = 0;
          
          while(currState.getNumChildren() > 0){
            numMoves = currState.getNumChildren();
            randMove = random.nextInt(numMoves);
            currState = currState.getChild(randMove);
            gameLength++;
          }//EOF while
          
          total += gameLength;
        }//EOF for
        
        
        double average = (double)total/(double)input3;
        System.out.printf("%d %f\n", h, average);

        stateMap.clear();
        stateList.clear();
        indexList.clear();
        q.clear();
        minTerms = 2147483647;
        numPaths = 0;
    }//EOF for
    
    reader.close();
  }//EOF main

}//EOF AverageLength class
