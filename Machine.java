import java.util.*;
import java.io.*;

public class Machine {
	//Machine frames
	FrameTable frameTable;
	
	//A list containing all processes in the order of their process number
	ArrayList<Process> processList=new ArrayList<Process>();
	
	//Used for Round Robin algorithm as a queue
	LinkedList<Process> rrQueue;
	
	private int debugLevel;
	
	public Machine(FrameTable frameTable, ArrayList<Process> processList, LinkedList<Process> rrQueue, int debugLevel){
		this.frameTable=frameTable;
		this.processList=processList;
		this.rrQueue=rrQueue;
		this.debugLevel=debugLevel;
	}
	
	public void runRefer(int pageSize) throws FileNotFoundException{
		int q=3;
		//Get the first process to run
		Process runningProcess=rrQueue.removeFirst();
		File file=new File("./src/random-numbers.txt");
		Scanner in=new Scanner(file);
		int randomNum;
		int reference;
		int cycle=1;
		while (true){
			//run one process till the quantum of 3 cycles
			for (int ref=0;ref<q;ref++){
				//if finish all the references for this process, stop referencing it
				if (runningProcess.getAlreadyReferenced()>=runningProcess.getTotalreferenceNum()) break;
				//simulate reference for this process
				reference=runningProcess.getCurrentReference();
				Page page=runningProcess.getPage(reference/pageSize);
				//Used for LRU algorithm
				page.setLastReference(cycle);
				//Page Fault
				if (page.getLoadedFrame()==-1){
					runningProcess.setPageFault(runningProcess.getPageFault()+1);
					frameTable.loadPage(page, cycle, in, reference);
				}
				//Hit in the frame
				else{
					if (debugLevel==1 || debugLevel==11){
						System.out.print(page.getProcessNum()+" references word "+reference+
								" (page "+page.getPageNumber()+") at time "+cycle+": ");
						System.out.println("Hit in frame "+page.getLoadedFrame());
					}
				}
				runningProcess.setAlreadyReferenced(runningProcess.getAlreadyReferenced()+1);
				
				//calculate the next reference for this process
				randomNum=in.nextInt();
				if (debugLevel==11) System.out.println(runningProcess.getProcessNum()+" uses random number: "+randomNum);
				runningProcess.setCurrentReference(randomNum, in, debugLevel);
				cycle++;
			}
			//If the process doesn't finish all the references, append it to the round robin queue.
			if (runningProcess.getAlreadyReferenced()<runningProcess.getTotalreferenceNum()) 
				rrQueue.addLast(runningProcess);
			//If round robin queue is empty, which means all processes finish all references, stop the machine.
			if (rrQueue.size()==0) break;
			//Swift process
			runningProcess=rrQueue.removeFirst();
		}
		in.close();
		int totalPageFault=0;
		int totalEvictSum=0;
		int totalRunningSum=0;
		System.out.println();
		/*Loop the process list to calculate each of its page faults and average residency time
		and add the running sum for total*/
		for (Process p:processList){
			p.calculate();
			totalPageFault+=p.getPageFault();
			totalEvictSum+=p.getEvictionSum();
			totalRunningSum+=p.getRunningSum();
			System.out.print("Process "+p.getProcessNum()+" had "+p.getPageFault()+" faults and ");
			if (p.getEvictionSum()!=0) System.out.println(((double)p.getRunningSum()/(double)p.getEvictionSum())+" average residency.");
			else {
				System.out.println("\n\tWith no evictions, the average residence is undefined.");
			}
		}
		//Total page faults and average residency time
		System.out.print("\nThe total number of faults is "+totalPageFault);
		if (totalEvictSum!=0){
			System.out.println(" and the overall average residency is "+((double)totalRunningSum/(double)totalEvictSum)+".");
		}
		else{
			System.out.println("\n\tWith no evictions, the overall average residence is undefined.");
		}
	}
}
