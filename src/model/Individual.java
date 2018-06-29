package model;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Comparator;

public class Individual{
	private ArrayList<Circle> chromossome;
	private double fitness;
	
	public Individual() {
		chromossome = new ArrayList<Circle>();
	}
	public void addRandomly(int numberOfCircles, int imgWidth, int imgHeight) {
		for(int i = 0; i < numberOfCircles; i++) {
			chromossome.add(new Circle(imgWidth, imgHeight));
		}
	}
	
	public void setFitness(BufferedImage img, BufferedImage originalImg) {
		int fitness = 0, subtraction;
		byte[] imgPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); //get pixel arrays
		byte[] originalPixels = ((DataBufferByte) originalImg.getRaster().getDataBuffer()).getData();
		for(int i = 0; i < imgPixels.length; i++) { //iterate over arrays and subtract equivalent pixels and sum the 
			subtraction = Math.abs(imgPixels[i] - originalPixels[i]);//get the absolute value of the subtraction 
			fitness += subtraction;//keep the result into a variable 
		}
		this.fitness = fitness/imgPixels.length;
	}
	
	public ArrayList<Circle> getChromossome() {
		return chromossome;
	}
	public void setChromossome(ArrayList<Circle> chromossome) {
		this.chromossome = chromossome;
	}
	public double getFitness() {
		return this.fitness;
	}
	
	
}
