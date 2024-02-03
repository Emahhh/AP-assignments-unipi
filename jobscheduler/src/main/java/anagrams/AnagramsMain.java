package anagrams;

import jobsched.Jobscheduler;


public class AnagramsMain {

    public static void main(String[] args){

        // 1: TXT COUNT STRATEGY
        // running the framework using TxtCountOutputStrategy (outputs the results in a .txt file, only counting the anagrams)
        AskUserEmitStrategy askStrat = new AskUserEmitStrategy();
        TxtCountOutputStrategy txtCountStrat = new TxtCountOutputStrategy();
        Jobscheduler myScheduler = new Jobscheduler<String, String>(askStrat, txtCountStrat);
        myScheduler.runPipeline(); // starts the framework by executing the whole pipeline
        System.out.println("Done!");



        // 2: CSV COUNT STRATEGY
        // running the framework using CSVCountOutputStrategy (outputs the results in a .csv file, only counting the anagrams)
        System.out.println("Now running the second strategy...");
        AskUserEmitStrategy askStrat2 = new AskUserEmitStrategy();
        CsvCountOutputStrategy csvCountStrat = new CsvCountOutputStrategy();
        Jobscheduler myScheduler2 = new Jobscheduler<String, String>(askStrat2, csvCountStrat);
        myScheduler2.runPipeline();


        // 3: TXT ANAGRAMS STRATEGY
        // running the framework using TxtAnagramsOutputStrategy (outputs the results in a .txt file, only counting the anagrams)
        System.out.println("Now running the third strategy...");
        AskUserEmitStrategy askStrat3 = new AskUserEmitStrategy();
        TxtAnagramsOutputStrategy txtAnagramsStrat = new TxtAnagramsOutputStrategy();
        Jobscheduler myScheduler3 = new Jobscheduler<String, String>(askStrat3, txtAnagramsStrat);
        myScheduler3.runPipeline();

    }
    
}
