package anagrams;

import jobsched.Jobscheduler;


public class AnagramsMain {

    public static void main(String[] args){
        AskUserEmitStrategy askStrat = new AskUserEmitStrategy();
        TxtOutputStrategy txtStrat = new TxtOutputStrategy();

        Jobscheduler myScheduler = new Jobscheduler<String, String>(askStrat, txtStrat);
        myScheduler.runPipeline(); // starts the framework by executing the whole pipeline
    }
    
}
