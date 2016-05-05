
public class Page {
	private int pageNumber;
	
	private int loadedFrame;
	
	private int processNum;
	
	private int evictNum;
	
	private int startTime;
	
	private int residencyTime;
	
	private int lastReference;
	
	public Page(int pageNumber, int processNum){
		this.pageNumber=pageNumber;
		this.loadedFrame=-1;
		this.processNum=processNum;
		this.evictNum=0;
		this.residencyTime=0;
	}

	public int getEvictNum() {
		return evictNum;
	}

	public void setEvictNum(int evictNum) {
		this.evictNum = evictNum;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getLoadedFrame() {
		return loadedFrame;
	}

	public void setLoadedFrame(int loadedFrame) {
		this.loadedFrame = loadedFrame;
	}

	public int getProcessNum() {
		return processNum;
	}

	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}

	public int getResidencyTime() {
		return residencyTime;
	}

	public void setResidencyTime(int residencyTime) {
		this.residencyTime = residencyTime;
	}

	public int getLastReference() {
		return lastReference;
	}

	public void setLastReference(int lastReference) {
		this.lastReference = lastReference;
	}
	
	
}
