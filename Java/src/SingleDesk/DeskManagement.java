package SingleDesk;
public interface DeskManagement {
	int enterPriorityQueue(int timeout);
	int enterNormalQueue(int timeout);
	void exitQueue(int desk);
}
