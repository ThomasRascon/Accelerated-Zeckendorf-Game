package state;
import java.util.*;

public class GameState{
  //states to reach from the current state
  List<GameState> children = new ArrayList<GameState>();
  List<String> moves = new ArrayList<String>();
  int[] bins = new int[45];  //Underlying data structure
  boolean queued = false;
  boolean winningState = false;
  boolean connected = false;
  int largestBin;    //Index of the largest (non-empty) bin
  int numTerms = 0;
  int numChildren = 0;
  float numOptions = -1.0f;

  //Constructor
  public GameState(int n){
    bins[0] = n;
    largestBin = 0;
    numTerms = n;
  }

  //Overrise constructor
  public GameState(GameState parent, int[] bins){
    numTerms = parent.numTerms;
    largestBin = parent.largestBin;
    this.bins = bins;
  }
  
  //Returns entry at n from the nextStates list
  public void connectToChild(GameState child){
    children.add(child);
    numChildren++;
  }

  public void addMove(String s, int i, int m){
    String move = String.valueOf(m)+"*"+s+String.valueOf(i);
    moves.add(move);
  }

  //Returns entry at n from the bins array
  public int getEntry(int n){
    return bins[n];
  }
  
  public boolean getConnected(){
    return connected;
  }
  
  public void setConnected(){
    connected = true;
  }
  
  public boolean equals(GameState otherState){
    return Arrays.equals(bins, otherState.getBins());
  }

  public String getMove(int i){
    return moves.get(i);
  }

  public int[] getBins(){
    return bins;
  }
  
  public List<GameState> getChildren(){
    return children;
  }
  
  public GameState getChild(int i){
    return children.get(i);
  }

  public void removeChild(int i){
    children.remove(i);
    numChildren--;
  }
  
  public int moveIDX(String move){
    return moves.indexOf(move);
  }
  
  public float getNumOptions(){
    return numOptions;
  }
  
  public void setNumOptions(float n){
    numOptions = n;
  }

  public int getNumChildren(){
    return numChildren;
  }

  public int getLargestBin(){
    return largestBin;
  }

  public void setLargestBin(){
    largestBin++;
  }

  public boolean getWinningState(){
    return winningState;
  }
  
  public void setQueded(){
    queued = true;
  }
  
  public boolean getQueded(){
    return queued;
  }

  public void setWinningState(){
    winningState = true;
  }

  public int getNumTerms(){
    return numTerms;
  }

  public void setNumTerms(){
    numTerms--;
  }

  public void removeMove(int i){
    moves.remove(i);
  }
}//EOF GameState


class Moves{
  static Double getKey(int[] bins){
    Double key = 0.0;
    for(int i = 0; i < 45; i++){
      key += 2100000000*bins[i]*Math.sqrt(i+1);
    }
    return key;
  }//EOF getKey
  
  public static int[] split(int n, int[] bins, int m){
    if(n == 1){
      bins[0] += m;
    }
    else{
      bins[n-2] += m;
    }
    bins[n+1] += m;
    bins[n] += -2*m;
    if(bins[n] < 0){
      return null;
    }
    return bins;
  }//EOF split

  public static int[] combine(int n, int[] bins, int m){
    if(n == 0){
      bins[0] += -2*m;
      if(bins[0]<0){
        return null;
      }
    }
    else{
      bins[n-1] -= m;
      bins[n] -= m;
      if(bins[n-1]<0 || bins[n]<0){
        return null;
      }
    }
    bins[n+1] += m;
    return bins;
  }//EOF combine
}//EOF Moves class
