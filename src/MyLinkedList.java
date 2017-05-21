public class MyLinkedList
{
	private DoubleNode headNode;
	private int listCount; // keeps note of size of linkedlist
	
	// constructor
	public MyLinkedList()
	{
		headNode = new DoubleNode(0);
		listCount = 0;
	}
	
	// Add to linked list
	public void add(double data)
	{
		DoubleNode tempNode = new DoubleNode(data);
		DoubleNode currentNode = headNode;
		
		
		while(currentNode.getNext() != null)
		{
			currentNode = currentNode.getNext();
		}
		
		currentNode.setNext(tempNode);
		listCount++;
	}
	
	// returns the value at the index
	public double get(int index)
	{	
		DoubleNode currentNode = headNode.getNext();
		for(int i = 1; i < index; i++)
		{
			if(currentNode.getNext() == null)
				return 0;
			
			currentNode = currentNode.getNext();
		}
		return currentNode.getData();
	}
	
	// returns the size of the linked list
	public int size()
	{
		return listCount;
	}
}