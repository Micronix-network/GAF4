package it.micronixnetwork.gaf.test;

import it.micronixnetwork.gaf.service.task.GAFTask;


public class TestTask extends GAFTask {
    
    private TestRunner[] runners=new TestRunner[]{};
    
    private boolean detail=false;
    
    public void setDetail(boolean detail) {
	this.detail = detail;
    }
    
    public void setRunners(TestRunner[] runners) {
	this.runners = runners;
    }
    
    public TestRunner[] getRunners() {
	return runners;
    }

    @Override
    public void run() {
	boolean result=true;

	if(runners==null || runners.length==0){
	    info("No test defined");
	}else{
	    for (TestRunner runner : runners) {
		runner.setDetail(detail);
		boolean rtest=runner.runTest();
		info(runner.getDescription()+": "+rtest);
		result=result&&rtest;
	    }
	}
	
	info("######### TOTAL TEST RESULT: "+result);

    }

}
