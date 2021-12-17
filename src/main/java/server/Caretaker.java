package server;

import java.util.*;


public class Caretaker {

	ArrayList<Memento> statesList;
	
	public Caretaker(){
		statesList = new ArrayList<Memento>();
	}
	
	public void add(Memento state){
		statesList.add(state);
	}
	
	public Memento get(int index){
		Memento restoreState = statesList.get(index);
		statesList.remove(index);
		return restoreState;
	}
	
	public Memento undo()
	{
		//popping last state
		int index = statesList.size() -1;
		Memento restoreState = statesList.get(index);
		statesList.remove(index);
		return restoreState;
	}
		
	
	public int size(){
		return statesList.size();
	}
	
}
