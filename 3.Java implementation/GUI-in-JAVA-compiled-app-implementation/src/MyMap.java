package app.home;

public class MyMap implements Comparable<MyMap> {

	int count;
	String status;
	public int getCount() {
		return count;
	}

	public String getStatus() {
		return status;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	MyMap(int count,String status){
		this.count=count;
		this.status=status;
	}

	@Override
	public int compareTo(MyMap o) {
		if (this.count==o.count)
			return 0;
		else if(this.count>o.count) {
			return 1;
		}else {
			return -1 ;
		}
	
	}

	
}
