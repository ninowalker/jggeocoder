package org.nnio.geocode;


public class Coordinate {
	public double x, y;
	public Projection proj;
	
	public String toString() {
		return "["+x+","+y+"]";
	}
}
