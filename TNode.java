package transit;


public class TNode {
	private int location;
	private TNode next;
	private TNode down;
	
	public TNode(int l, TNode n, TNode d) {
		location = l;
		next = n;
		down = d;
	}

	public TNode() {
		
		this(0, null, null);
	}

	public TNode(int l){
		
		this(l, null, null);
	}

	public int getLocation() { return location; }
	public void setLocation(int l) { location = l; }

	public TNode getNext() { return next; }
	public void setNext(TNode n) { next = n; }

	public TNode getDown() { return down; }
	public void setDown(TNode d) { down = d; }
}
