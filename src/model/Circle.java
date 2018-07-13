package model;

import java.util.concurrent.ThreadLocalRandom;

public class Circle implements Cloneable{
	private int grayScale;
	private int size;
	private int x;
	
	public int getGrayScale() {
		return grayScale;
	}

	public void setGrayScale(int grayScale) {
		this.grayScale = grayScale;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int y;
	
	public Circle(int imgWidth, int imgHeight) {
		grayScale = ThreadLocalRandom.current().nextInt(0, 256);
		size = ThreadLocalRandom.current().nextInt(0, 200);
		x = ThreadLocalRandom.current().nextInt(0, imgWidth);
		y = ThreadLocalRandom.current().nextInt(0, imgHeight);
	}
	
	public void setRandomCoordinates(int width, int height) {
		
	}
	
	@Override
	public String toString() {
		return "Gray: " + grayScale + ", size: " + size + ", x: " + x + ", y: " + y + " | ";
		
	}
	 protected Object clone() throws CloneNotSupportedException {
	        return super.clone();
	    }
}
