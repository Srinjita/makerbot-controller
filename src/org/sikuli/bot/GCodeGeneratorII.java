package org.sikuli.bot;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class GCodeGeneratorII {
	
	private int codeCount = 1; //Tells which gCode number is being generated is for naming purposes
	private Point origin;
	private int length;
	private int height;
	private int width;
	private String filePath;
	private final int ZBOTTOM = 140;
	private final int STYLUS_Y_OFFSET = -60;
	private final int STYLUS_X_OFFSET = 0;
	
	/**
	 * Constructor
	 * 
	 * @param origin Origin Point of the device (will probably be some corner of the screen)
	 * @param length Length of the screen in mm. Represents the x-axis.
	 * @param height Height of the screen in mm. Represents the y-axis.
	 * @param width Width of the device in mm. Represents the z-axis.
	 * @param filePath The file path where the generated gCode will be saved.
	 */
	public GCodeGeneratorII(Point origin, int length, int height, int width, String filePath){
		this.origin = origin;
		this.length = length;
		this.height = height;
		this.width = width;
		this.filePath = filePath;
	}
	
	/**
	 * Returns the physical coordinates of where the maker bot should click
	 * given the coordinates on the device screen.
	 * 
	 * @param coord A coordinate on device
	 * @return 		The physical coordinate on the maker bot
	 */
	private Point findCoord(Point coord){
		if (coord == null){
			return null;
		}
		//Assume that screen is 600x800 pixels 
		//double made to int (possible loss in precision)
		//coordinates switched since screen is on its side
		int x = (int)((coord.getX()/800)*length);
		int y = (int)((coord.getY()/600)*height);
		
		if(x >= origin.getX() + 280)
			x = (int)(280 - origin.getX());
		if(x < 0)
			x = 0;
		if(y >= origin.getY() + 150)
			y = (int)(150 - origin.getY());
		if(y < 0)
			y = 0;
		
		return new Point(x, y);
	}

	/**
	 * Given the coordinate on the screen where the device should click, gcode is generated 
	 * 
	 * @param clickPt The point on the device where the maker bot should click. 
	 */
	public Vector<String> createClickVector(Point clickPt){
		//Calculate the new coordinate
		clickPt = findCoord(clickPt);
		Vector<String> vect = new Vector<String>();
		//Write to vector
			
		//Set Up stuff
		vect.add("M73 P2");
        vect.add("M103");
        vect.add("M73 P5");
        vect.add("M73 P0");
		vect.add("G21");
        vect.add("G90");
		vect.add("G162 X Y F2500");
////
		vect.add("G92 X" + ((int)origin.getX() + STYLUS_X_OFFSET) + 
				" Y" + ((int)origin.getY() + STYLUS_Y_OFFSET) + " Z0");
//		
		//Moving
//		vect.add("G1 Z-" + (ZBOTTOM - 30 - width) + " F2300"); //adjust to right height
//
		vect.add("G1 X-" + ((int) clickPt.getX()) + " Y-" +
                ((int) clickPt.getY()) + " F3000"); //Move to pt
//		
//		vect.add("G162 X Y F2500");

//		vect.add("G1 Z-" + (ZBOTTOM - width - 1)); //Click
//		vect.add("G1 Z-3\" + (ZBOTTOM - 30 - width));
//		vect.add("G1 Z-30");
//		vect.add("G1 X0 Y0 Z0"); //Return home
		// vect.add("M72 P1"); //Done music 
		
		return vect;
	}
	
	public Vector<String> createClickVector(Point clickPt, Point prevPt, List<Point> points){
		//Calculate the new coordinate
		clickPt = findCoord(clickPt);
		prevPt = findCoord(prevPt);
		Vector<String> vect = new Vector<String>();
		//Write to vector
			
		//Set Up stuff
		vect.add("M73 P2");
        vect.add("M103");
        vect.add("M73 P5");
        vect.add("M73 P0");
		vect.add("G21");
        vect.add("G90");
        
        if (prevPt == null){
        	vect.add("G162 X Y F2500");
        	vect.add("G92 X" + ((int)origin.getX() + STYLUS_X_OFFSET) + 
    				" Y" + ((int)origin.getY() + STYLUS_Y_OFFSET) + " Z0");
        }else{
    		vect.add("G92 X-" + prevPt.getX() + 
     				" Y-" + prevPt.getY() + " Z0");
        }
		
		//Moving
//		vect.add("G1 Z-" + (ZBOTTOM - 30 - width) + " F2300"); //adjust to right height
//
		vect.add("G1 X-" + ((int) clickPt.getX()) + " Y-" +
                ((int) clickPt.getY()) + " F3000"); //Move to pt
//		
//		vect.add("G162 X Y F2500");

//		vect.add("G1 Z-" + (ZBOTTOM - width - 1)); //Click
		vect.add("G1 Z-22");//\" + (ZBOTTOM - 30 - width));
		
		for (Point p : points){
			p = findCoord(p);
			vect.add("G1 X-" + ((int) p.getX()) + " Y-" +
	                ((int) p.getY()) + " F3000"); //Move to pt
		}
		
	
		
		vect.add("G1 Z0");
//		vect.add("G1 X0 Y0 Z0"); //Return home
		// vect.add("M72 P1"); //Done music 
		
		return vect;
	}	
	
	public Vector<String> createClickVector(Point clickPt, Point prevPt){
		//Calculate the new coordinate
		clickPt = findCoord(clickPt);
		prevPt = findCoord(prevPt);
		Vector<String> vect = new Vector<String>();
		//Write to vector
			
		//Set Up stuff
		vect.add("M73 P2");
        vect.add("M103");
        vect.add("M73 P5");
        vect.add("M73 P0");
		vect.add("G21");
        vect.add("G90");
        
        if (prevPt == null){
        	vect.add("G162 X Y F2500");
        	vect.add("G92 X" + ((int)origin.getX() + STYLUS_X_OFFSET) + 
    				" Y" + ((int)origin.getY() + STYLUS_Y_OFFSET) + " Z0");
        }else{
    		vect.add("G92 X-" + prevPt.getX() + 
     				" Y-" + prevPt.getY() + " Z0");
        }
		
		//Moving
//		vect.add("G1 Z-" + (ZBOTTOM - 30 - width) + " F2300"); //adjust to right height
//
		vect.add("G1 X-" + ((int) clickPt.getX()) + " Y-" +
                ((int) clickPt.getY()) + " F3000"); //Move to pt
//		
//		vect.add("G162 X Y F2500");

//		vect.add("G1 Z-" + (ZBOTTOM - width - 1)); //Click
		vect.add("G1 Z-22");//\" + (ZBOTTOM - 30 - width));
				
		
		vect.add("G1 Z0");
//		vect.add("G1 X0 Y0 Z0"); //Return home
		// vect.add("M72 P1"); //Done music 
		
		return vect;
	}
	
		
	public Vector<String> createClickVector(Point clickPt, Point prevPt, Point dragPt){
		//Calculate the new coordinate
		clickPt = findCoord(clickPt);
		prevPt = findCoord(prevPt);
		dragPt = findCoord(dragPt);
		Vector<String> vect = new Vector<String>();
		//Write to vector
			
		//Set Up stuff
		vect.add("M73 P2");
        vect.add("M103");
        vect.add("M73 P5");
        vect.add("M73 P0");
		vect.add("G21");
        vect.add("G90");
        
        if (prevPt == null){
        	vect.add("G162 X Y F2500");
        	vect.add("G92 X" + ((int)origin.getX() + STYLUS_X_OFFSET) + 
    				" Y" + ((int)origin.getY() + STYLUS_Y_OFFSET) + " Z0");
        }else{
    		vect.add("G92 X-" + prevPt.getX() + 
     				" Y-" + prevPt.getY() + " Z0");
        }
		
		//Moving
//		vect.add("G1 Z-" + (ZBOTTOM - 30 - width) + " F2300"); //adjust to right height
//
		vect.add("G1 X-" + ((int) clickPt.getX()) + " Y-" +
                ((int) clickPt.getY()) + " F3000"); //Move to pt
//		
//		vect.add("G162 X Y F2500");

//		vect.add("G1 Z-" + (ZBOTTOM - width - 1)); //Click
		vect.add("G1 Z-22");//\" + (ZBOTTOM - 30 - width));
		
		if (dragPt != null){
			vect.add("G1 X-" + ((int) dragPt.getX()) + " Y-" +
	                ((int) dragPt.getY()) + " F3000"); //Move to pt
		}
		
		
		
		vect.add("G1 Z0");
//		vect.add("G1 X0 Y0 Z0"); //Return home
		// vect.add("M72 P1"); //Done music 
		
		return vect;
	}
	
	/**
	 * Given the coordinate on the screen where the device should click, gcode is generated 
	 * 
	 * @param clickPt The point on the device where the maker bot should click. 
	 */
	public void createClickCode(Point clickPt){
		//Calculate the new coordinate
		Vector<String> vect = createClickVector(clickPt);
		//Write to file
		try{
			File newCode = new File(filePath + "gCode" + codeCount +".gcode");
			newCode.createNewFile();
			
			FileWriter fw = new FileWriter(newCode.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(fw);
			
			for(String line: vect)
			{
				writer.write(line);
			}
			
			writer.close();
			
			System.out.println("Done with instruction set" + codeCount);
			codeCount++;
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		//Piece of paper is 205x268 mm
		GCodeGeneratorII test = new GCodeGeneratorII(new Point(60,60), 140, 80, 3, ".");
		test.createClickCode(new Point (114,375));
		test.createClickCode(new Point(0,0));
		test.createClickCode(new Point(571, 150));
	}
}
