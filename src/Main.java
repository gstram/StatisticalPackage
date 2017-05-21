import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main {

	// Create linked list
	static MyLinkedList numbersList = new MyLinkedList();

	public static void main(String[] args) 
	{		
		// Prompt user to select file
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter file name: ");
		File file = new File(userInput.next());
		inputListToLinkedList(file);
		
		// Prompt user to enter what he or she wants to calculate
		int userSelection = 5;
		System.out.println("Please enter the number for the calculation you want to make: ");
		System.out.println("For calculating the mean, enter 1");
		System.out.println("For calculating the variance and standard deviation, enter 2");
		System.out.println("For calculating the pearson R correlation coefficient, enter 3");
		System.out.println("For calculating the z-scores and relative probability, enter 4");
		System.out.println("For display the regresssion coefficients and displaying them, enter 5");
		System.out.println("For display the regresssion line on a scatterplot, enter 6");
		System.out.println("For calculating the result needed to achieve significance, enter 7");
		userSelection = userInput.nextInt();
		
		// Calculate the correct data
		if(userSelection == 1)
		{
			inputListToLinkedList(file);
			meanCalculator(numbersList);
		}
		else if (userSelection == 2)
		{
			inputListToLinkedList(file);
			varAndStdCalculator(numbersList);
		}
		else if (userSelection == 3)
		{
			inputListToLinkedList(file);
			int proxySize = 0;
			System.out.println("Please enter a proxy size");
			proxySize = userInput.nextInt();
			correlationCalculator(numbersList, proxySize);
		}
		else if (userSelection == 4)
		{
			inputListToLinkedList(file);
			zScoreCalculator(numbersList);
		}
		else if (userSelection == 5)
		{
			inputListToLinkedList(file);
			int proxySize = 0;
			System.out.println("Please enter a proxy size");
			proxySize = userInput.nextInt();
			
			JPanel panel = graphRegression(numbersList, proxySize);
			JFrame frame = new JFrame("graph");
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(panel);
			frame.setSize(panel.getSize().width +30, panel.getSize().height +30);
		}
		else if (userSelection == 6)
		{
			inputListToLinkedList(file);
			int proxySize = 0;
			System.out.println("Please enter a proxy size");
			proxySize = userInput.nextInt();
			
			JPanel panel = graphRegression(numbersList, proxySize);
			JFrame frame = new JFrame("graph");
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(panel);
			frame.setSize(panel.getSize().width +30, panel.getSize().height +30);
		}
		else if (userSelection == 7)
		{
			inputListToLinkedList(file);
			significanceFinder(numbersList);
		}
		else
		{
			System.out.println("Invalid Selection.");
		}
		userInput.close();
	}

	// Method for taking numbers from file and putting them into a linked list
	public static void inputListToLinkedList(File userFile)
	{	
		// Input numbers from file to linkedlist
	    try 
	    {
	        Scanner importNumbers = new Scanner(userFile);
	        
	        while (importNumbers.hasNextLine()) 
	        {
	        	numbersList.add(importNumbers.nextDouble());
	        }
	        importNumbers.close();
	    } 
	    catch (FileNotFoundException exception) 
	    {
	        exception.printStackTrace();
	    }
	}
	
	// Method for calculating the mean
	public static void meanCalculator(MyLinkedList linkedList)
	{
		// find mean
	    double mean = 0; 
	    for(int counter = 1; counter <= linkedList.size(); counter++)
	    {
		    mean = mean + linkedList.get(counter);
	    }
	    mean = mean/(linkedList.size());
	    System.out.println("Mean: " + mean);
	}
	
	// Method for calculating the variance and standard deviation
	public static void varAndStdCalculator(MyLinkedList linkedList)
	{
		// find mean
	    double mean = 0; 
	    for(int counter = 1; counter <= linkedList.size(); counter++)
	    {
		    mean = mean + linkedList.get(counter);
	    }
	    mean = mean/(linkedList.size());

		// find variuance and std Deviation
	    double stdDev = 0; 
	    for(int counter = 1; counter <= linkedList.size(); counter++)
	    {
		    stdDev = stdDev + Math.pow((linkedList.get(counter) - mean), 2);
	    }
	    stdDev = stdDev/(linkedList.size());
	    System.out.println("Variance: " + stdDev);
	    stdDev = Math.sqrt(stdDev);
	    System.out.println("StdDev: " + stdDev);
	}
	
	// Method for calculating the correlation between pair of numbers
	public static double[] correlationCalculator(MyLinkedList linkedList, double proxySize)
	{
		double[] correlationCoefficients = new double[2];
		
		// initialize variables
	    double firstValueSquared = 0; 
	    double secondValueSquared = 0;
	    double bothValuesMultiplied = 0;
	    double xValuesAdded = 0;
	    double yValuesAdded = 0;
	    double tempX = 0;
	    double tempY = 0;
	    
	    // find mean of x and y
	    for(int counter = 1; counter <= linkedList.size(); counter++)
	    {
	    	// Assign x and y values to temp holders
	    	tempX = linkedList.get(counter);
	    	counter++;
	    	tempY = linkedList.get(counter);
		    
	    	// add values to existing holders
	    	xValuesAdded = xValuesAdded + tempX;
	    	yValuesAdded = yValuesAdded + tempY;
	    }
	    xValuesAdded = xValuesAdded / (linkedList.size()/2);
	    yValuesAdded = yValuesAdded / (linkedList.size()/2);
	    
	    //find first, second, and third value of formula
	    for(int counter = 1; counter <= linkedList.size(); counter++)
	    {
	    	// Assign x and y values to temp holders
	    	tempX = linkedList.get(counter);
	    	counter++;
	    	tempY = linkedList.get(counter);
	    	
	    	firstValueSquared = firstValueSquared + Math.pow((tempX),2);
	    	secondValueSquared = secondValueSquared + Math.pow((tempY),2);
	    	bothValuesMultiplied = bothValuesMultiplied + ((tempX) * (tempY));
	    }

	    // Calculate beta one 	    
	    double betaOne = ((bothValuesMultiplied) - ((linkedList.size()/2) * (xValuesAdded) * 
	    		(yValuesAdded)));
	    betaOne = betaOne / ((firstValueSquared) - ((linkedList.size()/2) * (Math.pow(xValuesAdded,2))));
	    
	    // calculate r subset x and y
	    double rOfXY = (((linkedList.size()/2)* bothValuesMultiplied));
	    rOfXY = rOfXY - ((xValuesAdded * (linkedList.size()/2)) * (yValuesAdded * (linkedList.size()/2)));
	    rOfXY = rOfXY / (Math.sqrt((((linkedList.size()/2) * firstValueSquared - (Math.pow(xValuesAdded * (linkedList.size()/2),2)))) *
	    	    (((linkedList.size()/2) * secondValueSquared - (Math.pow(yValuesAdded * (linkedList.size()/2),2)))))); 
	    
	    // Calculate r squared
	    double rSquared = rOfXY * rOfXY;
	    
	    // Calculate beta zero
	    double betaZero = (yValuesAdded) - ((betaOne * (xValuesAdded)));
	    
	    // calculate y subset j
	    double yOfJ = betaZero + (betaOne * proxySize);
	    
	    correlationCoefficients[0] = betaZero;
	    correlationCoefficients[0] = betaOne;
	    
	    //Print values
	    System.out.println("r subset xy: " + rOfXY);
	    System.out.println("r squared: " + rSquared);
	    System.out.println("beta one: " + betaOne);
	    System.out.println("beta zero: " + betaZero);
	    System.out.println("y subset j: " + yOfJ);
	    
	    return correlationCoefficients;
	}
	
	public static void zScoreCalculator(MyLinkedList linkedList)
	{
		System.out.println("Z Score ----- Probability");
		
		for(int counter = 1; counter <= linkedList.size(); counter++)
		{
			// Print the Z score
			System.out.print(linkedList.get(counter) + "    ");
		
			//Set up variables to be used in formulas
			boolean satisfied = true;
			double tempP = 0;
			//Riemann sum formula
			double xTriangle = 0;
			double b = linkedList.get(counter);
			double sum = 0;
			double normal = 0;
			double total = 0;
			double num = 100;
			double dof = .00001;
    	
			while(satisfied)
			{
				//Riemann sum formula
				xTriangle = (b-0)/num;
				
				for(int n = 1; n <= num; n++)
				{
					sum = (((((2*n-1)*xTriangle) / 2)));
					normal = 1 / (Math.sqrt(2*Math.PI));
					normal = normal * (Math.pow(2.71828, -((sum*sum)/2)));
					total = total + normal;
				}
				total = (total * xTriangle);
	    	
				if(tempP == 0)
				{
					tempP = total;
					num = num *2;
				}
				else if(Math.abs((tempP - total)) < dof)
				{
					System.out.println(total+.50);
					satisfied = false;
				}else
				{
					tempP = total;
					num = num *2;
				}
			}
	    }
	}

	public static void significanceFinder(MyLinkedList linkedList)
	{		
		System.out.print("p-value      Degrees Of Freedom           x    " );
		
		for(int counter = 1; counter <= linkedList.size()/2; counter++)
		{
		System.out.println();
		
		// Initialize all variables needed
		double upperIntegral = 1;
		double accurate = linkedList.get(counter);
		System.out.print(accurate + "        ");
		counter++;
		double dof = linkedList.get(counter);
		System.out.print(dof + "                  ");
		double num = 100;
		double triangle;
		double p = 0;
		double sum;
		double top;
		double bottom;
		double exp;
		double side;
		double value;
		double d = .5;
		boolean satisfied = true;
		double error;
		double state = -100;
		
		// Begin loop
		while(satisfied)
		{
			// Set the delta triangle value
			triangle = (upperIntegral)/num;
			
			// Initialize the middle riemman sum loop
			for(int counter2 = 1; counter2 <= num; counter2++)
			{
				// Calculate the value for x
				sum = ((((2*counter2) - 1) * triangle)/2);
				
				// Calculate the value of the top portion of the t distribution formula
				top = la_gamma((dof + 1) / 2);
				
				// Calculate the value of the bottom portion of the t distribution formula
				bottom = Math.sqrt((dof*Math.PI)) * la_gamma(dof/2);
				
				// Calculate the value of the side portion of the t distribution formula
				side = 1 + ((sum * sum)/dof);
				exp = (1/2 * (dof+1));
				side = 1 / Math.pow(side, exp);
				
				// Calculate the p value
				value = top * side / bottom;
				p = p + (value);
			}
			// Multiply the p value to the delta triangle value
			p = p * triangle;
			
			//Calculate the error of the theoretical value and experimental value
			error = (p - accurate) / accurate * 100;
			
			// This IF statement is for determining if it is the first p value calculated
			if(state == -100)
			{
				state = Math.signum(error);
			}
			
			
			//This IF statement determines if the P value is too low, too high, or correct
			//It also determines if the d value that is added to the trial value needs to be adjusted
			if(Math.abs(p - accurate) <= .001)
			{
				System.out.print(upperIntegral);
				satisfied = false;
			}
			else if((p > accurate) && (Math.signum(error) == state ))
			{
				upperIntegral = upperIntegral - d;
			}
			else if(p > accurate && (Math.signum(error) != state ))
			{
				d = d / 2;
				upperIntegral = upperIntegral - d;
				state = Math.signum(error);
			}
			else if(p < accurate && (Math.signum(error) == state ))
			{
				upperIntegral = upperIntegral + d;
			}
			else if(p < accurate && (Math.signum(error) != state ))
			{
				d = d / 2;
				upperIntegral = upperIntegral + d;
				state = Math.signum(error);
			}
		}
		}
	}
 
	public static double la_gamma(double x){
		double[] p = {0.99999999999980993, 676.5203681218851, -1259.1392167224028,
			     	  771.32342877765313, -176.61502916214059, 12.507343278686905,
			     	  -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7};
		int g = 7;
		if(x < 0.5) return Math.PI / (Math.sin(Math.PI * x)*la_gamma(1-x));
 
		x -= 1;
		double a = p[0];
		double t = x+g+0.5;
		for(int i = 1; i < p.length; i++){
			a += p[i]/(x+i);
		}
 
		return Math.sqrt(2*Math.PI)*Math.pow(t, x+0.5)*Math.exp(-t)*a;
	}

public static JPanel graphRegression(final MyLinkedList linkedList, double proxySize) {
	
	JPanel graph;
	
	final double[] B = correlationCalculator(linkedList, proxySize);
	
	int size = linkedList.size()/2;
	long axisLength = 500;
	double scale = axisLength / size;
	
	double max[] = max(linkedList);
	
	final double x_scale = max[0] / size;// System.out.println("xscale " +x_scale);
	final double y_scale = max[1] / size;
	
	// below are ratios designed to offset
	// the difference between a scale based
	// upon x or y values, and a screen size scale
	// This is needed to scale the graph
	final double x_scale_to_scale = (x_scale) / scale;
	final double y_scale_to_scale = (y_scale) / scale;
	
	// Use anonymous class to override paint method
	// so it draws the graph on the JComponent panel
	graph = new JPanel() {

		@Override
		public void paintComponent(Graphics g) {
			
			double x_value = 0;
			double y_value = 0;
			
			double x_coordinate = 0;
			double y_coordinate = 0;
			
			double xAxis = 0;
			double yAxis = 0;
			double xAxis_point = 0;
			double yAxis_point = 0;
			
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			FontMetrics font = g.getFontMetrics();
			
			g.drawString("0", 25, 625);
			
		    for(int i = 1; i <= linkedList.size(); i++)
		    {
		    	x_value = linkedList.get(i);
		    	x_coordinate = (x_value/x_scale_to_scale) + 25; // 25 is extra length used as a margin
		    	
		    	xAxis = x_scale*((i+1) /2);
		    	xAxis_point = (xAxis) / x_scale_to_scale;// System.out.println("x axis point " +i +" : " +xAxis_point);
		    	
		    	i++;
		    	
		    	y_value = linkedList.get(i);
		    	y_coordinate = (y_value/y_scale_to_scale) + 25; // 25 is extra length used as a margin
		    	
		    	yAxis = y_scale*((i) /2);
		    	yAxis_point = (yAxis) / y_scale_to_scale;
		    	
		    	// draw onto JPanel
		    	g.drawString(""+(int)xAxis, (int)xAxis_point+25, 525 + 12);
		    	g.drawString(""+(int)yAxis, 13, 550-(int)(yAxis_point+25));
		    	
		    	g.drawOval((int)x_coordinate + 1, 550-(int)y_coordinate + 1, 2, 2);
		    }
		    
		    g.drawLine((int)((x_scale*1)/x_scale_to_scale), 550-(int)((y_scale*1)/y_scale_to_scale), (int)((x_scale*10)/x_scale_to_scale), 550-(int)((y_scale*10)/y_scale_to_scale));
		}
	};
	
	graph.setSize((int)axisLength+50, (int)axisLength+50);
	
	return graph;
	}

public static double[] max(MyLinkedList linkedList) {
	
	double[] x_y = new double[2]; // temporary storage for max by current node
	x_y[0] = linkedList.get(0);
	x_y[1] = linkedList.get(1);
	
    for(int i = 1; i <= linkedList.size(); i++)
    {
    	if (linkedList.get(i) > x_y[0]) {
    		x_y[0] = linkedList.get(i);
    	}
    	
    	
    	i++;
    	
    	if (linkedList.get(i) > x_y[0]) {
    		x_y[1] = linkedList.get(i);
    	}	    	
    }
    
    return x_y;
}
}

