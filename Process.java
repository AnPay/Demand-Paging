import java.util.*;

public class Process {
	private int processNum;
	
	private double a;
	
	private double b;
	
	private double c;
	
	private int pageFault;
	
	private int size;
	
	private int alreadyReferenced;
	
	private int totalreferenceNum;
	
	private int currentReference;
	
	private int evictionSum;
	
	private int runningSum;
	
	private ArrayList<Page> pageList;
	
	public Process(int i, double a, double b, double c, int size,int referenceNum,int pageNum){
		this.processNum=i;
		this.a=a;
		this.b=b;
		this.c=c;
		this.size=size;
		this.totalreferenceNum=referenceNum;
		this.pageFault=0;
		this.alreadyReferenced=0;
		this.currentReference=(111*processNum+this.size)%this.size;
		this.pageList=new ArrayList<Page>();
		for (int j=0;j<pageNum;j++){
			pageList.add(new Page(j,processNum));
		}
		this.evictionSum=0;
		this.runningSum=0;
	}

	public int getProcessNum() {
		return processNum;
	}

	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}

	public int getPageFault() {
		return pageFault;
	}

	public void setPageFault(int pageFault) {
		this.pageFault = pageFault;
	}

	public int getAlreadyReferenced() {
		return alreadyReferenced;
	}

	public void setAlreadyReferenced(int alreadyReferenced) {
		this.alreadyReferenced = alreadyReferenced;
	}

	public int getTotalreferenceNum() {
		return totalreferenceNum;
	}

	public void setTotalreferenceNum(int totalreferenceNum) {
		this.totalreferenceNum = totalreferenceNum;
	}

	public int getCurrentReference() {
		return currentReference;
	}

	public void setCurrentReference(int r, Scanner in, int debugLevel) {
		//set the next reference used the algorithm in the requirement
		double y=r/(Integer.MAX_VALUE+1d);
		if (y<a) currentReference=(currentReference+1+size)%size;
		else if (y<a+b) currentReference=(currentReference-5+size)%size;
		else if (y<a+b+c) currentReference=(currentReference+4+size)%size;
		else{
			int random=in.nextInt();
			if (debugLevel==11) System.out.println(processNum+" uses random number: "+random);
			currentReference=random % size;
		}
	}
	
	public Page getPage(int pageNum){
		return pageList.get(pageNum);
	}
	
	public void calculate(){
		for (Page p:pageList){
			evictionSum+=p.getEvictNum();
			runningSum+=p.getResidencyTime();
		}
	}

	public int getEvictionSum() {
		return evictionSum;
	}

	public void setEvictionSum(int evictionSum) {
		this.evictionSum = evictionSum;
	}

	public int getRunningSum() {
		return runningSum;
	}

	public void setRunningSum(int runningSum) {
		this.runningSum = runningSum;
	}
}
