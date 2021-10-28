package test; 

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import net.sf.clipsrules.jni.*;

public class ClipsAgent extends Agent {

  Environment clips;
  protected void setup() {
    System.out.println("Hola soy "+getLocalName());
    try{
        clips = new Environment(); //En nuevas versiones el try-catch deberia ir aquí
    }catch(Exception e){}
    addBehaviour(new TellBehaviour());
    addBehaviour(new AskBehaviour());
  } 

  private class TellBehaviour extends Behaviour {
      
    boolean tellDone = false; 

    public void action() {
        //En nuevas versiones el try-catch deberia ir aquí
        try{
            clips.eval("(clear)");
            
            clips.load("/Users/gdlmo/Desktop/CLIPSJNI/market/load-templates.clp");
            System.out.println("Archivo templates listo");

            clips.load("/Users/gdlmo/Desktop/CLIPSJNI/market/load-facts.clp");
            System.out.println("Archivo facts listo");

            clips.load("/Users/gdlmo/Desktop/CLIPSJNI/market/load-rules.clp");
            System.out.println("Archivo rules listo");
            
            /*clips.load("/Users/gdlmo/Desktop/CLIPSJNI/work/resources/rules.clp");
            System.out.println("Archivo listo");*/
                       
            clips.reset(); 
        }catch(Exception e){}
        tellDone = true;
    } 
    
    public boolean done() {
      if (tellDone)
        return true;
      else
	return false;
    } 
  }
  
  private class AskBehaviour extends Behaviour {
    boolean askDone = false; 

    public void action() {
        
        //En nuevas versiones el try-catch deberia ir aquí
        try{
            clips.eval("(facts)"); 

            clips.eval("(rules)"); 
            
            clips.eval("(run)");
        }catch(Exception e){}
        askDone = true;
    } 
    
    public boolean done() {
      if (askDone)
        return true;
      else
	return false;
    }
   
    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    } 
  }
}

//javac -cp C:\jade\lib\jade.jar;lib\CLIPSJNI.jar src\ClipsAgent.java -d classes\    
//java -cp C:\jade\lib\jade.jar;lib\CLIPSJNI.jar;classes\ jade.Boot -gui clipsAgent:test.ClipsAgent