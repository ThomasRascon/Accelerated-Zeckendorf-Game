package GenerateGraph;

import java.util.*;
import state.*;

public class GenerateGraph
  static void findChildren(GameState parent){
    
    //Split largest first
    for(int i = parent.getLargestBin(); i > 0; i--){        
      if(parent.getEntry(i) >= 2){
        addChild(parent, 'S', i, 1);
        parent.addMove("S", i, 1);
      }
    }
    
    //Combine largest second
    for(int i = parent.getLargestBin(); i > 0; i--){
      if(parent.getEntry(i) >= 1 && parent.getEntry(i-1) >= 1){
        addChild(parent, 'C', i, 1);
        parent.addMove("C", i, 1);
      }
    }
    
    //Combine 1's third
    if(parent.getEntry(0) >= 2){
      addChild(parent, 'C', 0, 1);
      parent.addMove("C", 0, 1);
    }

  }//EOF findChildren
  
  static void doubleConnect(GameState currState){
    int numChildren = currState.getNumChildren();
    for(int i = 0; i < numChildren; i++){
      boolean valid = true;
      String move = currState.getMove(i);
      int star = move.indexOf("*");
      char type = move.charAt(star+1);
      int number = Integer.parseInt(move.substring(star+2,move.length()));
      int m = 2;
      while(valid){
        valid = addChild(currState, type, number, m);
        if(valid){
          currState.addMove(String.valueOf(type), number, m);
        }
        m++;
      }
    }//EOF for
  }//EPF doubleConnect

  static boolean addChild(GameState parent, char move, int number, int m){

    int[] childBins = parent.getBins().clone();
    if(move == 'C'){
      childBins = combine(number, childBins, m);
    }
    else{
      childBins = split(number, childBins, m);
    }
    
    if(childBins==null){
      return false;
    }
    
    Double key = getKey(childBins);
    GameState child = stateMap.get(key);
      
    if(child == null){
      child = new GameState(parent, childBins);
      stateMap.put(key, child);
      if(move == 'C'){
        child.setNumTerms();
      }
      if(parent.getLargestBin() == number){
        child.setLargestBin();
      }
      stateList.add(child);
      q.add(child);
    }
    
    parent.connectToChild(child);

    if(child.getNumTerms() < minTerms){
      minTerms = child.getNumTerms();
      indexList.add(stateList.size()-1);
    }

    return true;
  }//EOF addChild
