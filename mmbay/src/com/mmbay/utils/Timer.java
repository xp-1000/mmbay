package com.mmbay.utils;

import java.util.ArrayList;
import java.util.Calendar;

public class Timer extends Thread implements Observable{
	  private Calendar currentDate, dueDate;
	  private ArrayList<Observer> listObservers;
	  
	  public Timer(Calendar dueDate) {
		  this.dueDate = dueDate;
		  listObservers = new ArrayList<Observer>();
	  }
		
	  public void run() {
	    while(true){
	      this.currentDate = Calendar.getInstance();
	      /*int year       = currentDate.get(Calendar.YEAR);
	      int month      = currentDate.get(Calendar.MONTH); 
	      int dayOfWeek  = currentDate.get(Calendar.DAY_OF_WEEK);
	      int hourOfDay  = currentDate.get(Calendar.HOUR_OF_DAY); // 24 hour clock
	      int minute     = currentDate.get(Calendar.MINUTE);
	      int second     = currentDate.get(Calendar.SECOND);
	      System.out.println("Current Date : " + year+"/"+month+"/"+dayOfWeek+" "+hourOfDay+":"+minute+":"+second);
	      year       = dueDate.get(Calendar.YEAR);
	      month      = dueDate.get(Calendar.MONTH); 
	      dayOfWeek  = dueDate.get(Calendar.DAY_OF_WEEK);
	      hourOfDay  = dueDate.get(Calendar.HOUR_OF_DAY); // 24 hour clock
	      minute     = dueDate.get(Calendar.MINUTE);
	      second     = dueDate.get(Calendar.SECOND);
	      System.out.println("Due Date     : " + year+"/"+month+"/"+dayOfWeek+" "+hourOfDay+":"+minute+":"+second);*/
	      if (currentDate.after(dueDate))
	    	  this.updateObserver();
				
	      try {
	        Thread.sleep(1000);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	  }

	  //Ajoute un observateur à la liste
	  public void addObserver(Observer obs) {
	    this.listObservers.add(obs);
	  }
	  //Retire tous les observateurs de la liste
	  public void delObserver() {
	    this.listObservers = new ArrayList<Observer>();
	  }
	  //Avertit les observateurs que l'objet observable a changé 
	  //et invoque la méthode update() de chaque observateur
	  public void updateObserver() {
	    for(Observer obs : this.listObservers )
	      obs.update();
	  }
	}
