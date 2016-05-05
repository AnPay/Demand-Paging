import java.io.FileNotFoundException;
import java.util.*;

public class Paging {
	public static void main(String[] args) throws FileNotFoundException{
		//Get input info in command line
		int machineSize=Integer.parseInt(args[0]);
		int pageSize=Integer.parseInt(args[1]);
		int processSize=Integer.parseInt(args[2]);
		int jobMix=Integer.parseInt(args[3]);
		int referenceNum=Integer.parseInt(args[4]);
		String replacement=args[5];
		int debugLevel=Integer.parseInt(args[6]);
		int frameNum=machineSize/pageSize;
		FrameTable frameTable=new FrameTable(frameNum,replacement,debugLevel);
		int processPageNum=processSize/pageSize;
		ArrayList<Process> processList=new ArrayList<Process>();
		LinkedList<Process> rrQueue=new LinkedList<Process>();
		//With different job mix number, create different number of processes with different A,B,C.
		if (jobMix==1){
			Process process=new Process(1,1,0,0,processSize,referenceNum,processPageNum);
			processList.add(process);
			rrQueue.add(process);
		}
		else if (jobMix==2){
			for (int i=0;i<4;i++){
				Process process=new Process((i+1),1,0,0,processSize,referenceNum,processPageNum);
				processList.add(process);
				rrQueue.add(process);
			}
		}
		else if (jobMix==3){
			for (int i=0;i<4;i++){
				Process process=new Process((i+1),1,0,0,processSize,referenceNum,processPageNum);
				processList.add(process);
				rrQueue.add(process);
			}
		}
		else{
			Process process=new Process(1,0.75,0.25,0,processSize,referenceNum,processPageNum);
			processList.add(process);
			rrQueue.add(process);
			process=new Process(2,0.75,0,0.25,processSize,referenceNum,processPageNum);
			processList.add(process);
			rrQueue.add(process);
			process=new Process(3,0.75,0.125,0.125,processSize,referenceNum,processPageNum);
			processList.add(process);
			rrQueue.add(process);
			process=new Process(4,0.5,0.125,0.125,processSize,referenceNum,processPageNum);
			processList.add(process);
			rrQueue.add(process);
		}
		System.out.println("The machine size is "+machineSize+".");
		System.out.println("The page size is "+pageSize+".");
		System.out.println("The process size is "+processSize+".");
		System.out.println("The job mix number is "+jobMix+".");
		System.out.println("The number of references per process is "+referenceNum+".");
		System.out.println("The replacement algorithm is "+replacement+".");
		System.out.println("The level of debugging output is "+debugLevel+".\n");
		
		//Begin running
		Machine machine=new Machine(frameTable,processList,rrQueue,debugLevel);
		machine.runRefer(pageSize);
	}
}
