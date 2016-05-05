import java.util.*;

public class FrameTable {
	private Page[] pageLoad;
	
	private int frameNum;
	
	private String replaceAlgorithm;
	
	//The largest empty frame number. Begin with the last frame.
	private int largestFreeFrame;
	
	//This implements stack for lifo replacement algorithm
	Deque<Page> pageStack = new ArrayDeque<Page>();
	
	private int debugLevel;
	
	public FrameTable(int frameNum, String replaceAlgorithm, int debugLevel){
		this.pageLoad=new Page[frameNum];
		this.frameNum=frameNum;
		this.largestFreeFrame=frameNum-1;
		this.replaceAlgorithm=replaceAlgorithm;
		this.debugLevel=debugLevel;
	}
	
	//Load a page when a page fault happens
	public void loadPage(Page page, int cycle, Scanner in, int reference){
		//When there are still empty frames, put the page into the last empty frame
		if (largestFreeFrame!=-1){
			pageLoad[largestFreeFrame]=page;
			pageStack.addFirst(page);
			page.setStartTime(cycle);
			page.setLoadedFrame(largestFreeFrame);
			if (debugLevel==1 || debugLevel==11){
				System.out.print(page.getProcessNum()+" references word "+reference+
						" (page "+page.getPageNumber()+") at time "+cycle+": ");
				System.out.println("Fault, using free frame "+largestFreeFrame);
			}

			largestFreeFrame-=1;
		}
		
		//If no frame available, use replacement algorithm in input to choose evict page.
		else{
			int evictIndex;
			if (replaceAlgorithm.equals("lru")){
				evictIndex=lru();
			}
			else if (replaceAlgorithm.equals("random")){
				evictIndex=random(page, in);
			}
			else{
				evictIndex=lifo();
			}
			//Evict the page with the frame number
			Page evictPage=pageLoad[evictIndex];
			if (debugLevel==1 || debugLevel==11){
				System.out.print(page.getProcessNum()+" references word "+reference+
						" (page "+page.getPageNumber()+") at time "+cycle+": ");
				System.out.println("Fault, evicting page "+evictPage.getPageNumber()+" of "+evictPage.getProcessNum()+" from frame "+evictPage.getLoadedFrame());
			}

			evictPage.setResidencyTime(evictPage.getResidencyTime()+cycle-evictPage.getStartTime());
			evictPage.setEvictNum(evictPage.getEvictNum()+1);
			evictPage.setLoadedFrame(-1);		
			pageLoad[evictIndex]=page;
			pageStack.addFirst(page);
			page.setStartTime(cycle);
			page.setLoadedFrame(evictIndex);
		}
		
	}
	
	//LIFO: just pop the first one on the stack
	public int lifo(){
		Page page=pageStack.pop();
		return page.getLoadedFrame();
	}
	
	//Random: get a random number and use it to generate a random frame number
	public int random(Page page,Scanner in){
		int random=in.nextInt();
		if (debugLevel==11) System.out.println(page.getProcessNum()+" uses random number: "+random);
		return random % frameNum;
	}
	
	//Use the last reference variable in each process to get which page is the least recently used.
	public int lru(){
		int leastRecentlyUsed=-1;
		int useTime=1000000000;
		for (Page p:pageLoad){
			if (useTime>p.getLastReference()){
				useTime=p.getLastReference();
				leastRecentlyUsed=p.getLoadedFrame();
			}
		}
		return leastRecentlyUsed;
	}
	
}
