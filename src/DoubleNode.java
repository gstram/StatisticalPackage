public class DoubleNode 
{
    private double data;          
    private DoubleNode next;      

    // first node constructor 
    public DoubleNode()
    {
    	data = -999;
    	next = null;
    }

    // second node constructor
    public DoubleNode(double dataValue)
    {
    	data = dataValue;
    	next = null;
    }

    // set data in node
    public void setData(double dataValue)
    {
    	data = dataValue;
    }

    // move to next node
    public void setNext(DoubleNode newNext)
    {
    	next = newNext;
    }

    // return data in node
    public double getData()
    {
    	return data;
    }

    // return next node
    public DoubleNode getNext()
    {
    	return next;
    }
}