package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

public class Individual implements Comparable<Individual>{
	static int id = 0;
	private ArrayList<Circle> chromossome;
	private double fitness;
	
	public Individual(){
		chromossome = new ArrayList<Circle>();
	}
	public Individual(ArrayList<Circle> chromossome) {
		this.chromossome = chromossome;
	}
	
	public void addRandomly(int numberOfCircles, int imgWidth, int imgHeight) {
		for(int i = 0; i < numberOfCircles; i++) {
			chromossome.add(new Circle(imgWidth, imgHeight));
		}
	}
	
	/*public void setFitness(BufferedImage img, BufferedImage originalImg) {
		double fitness = 0;
		int subtraction;
		byte[] imgPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); //get pixel arrays
		
		byte[] originalPixels = ((DataBufferByte) originalImg.getRaster().getDataBuffer()).getData();
		for(int i = 0; i < imgPixels.length; i++) { //iterate over arrays and subtract equivalent pixels and sum the 
			subtraction = Math.abs(imgPixels[i] - originalPixels[i]);//get the absolute value of the subtraction 
			fitness += subtraction;//keep the result into a variable 
		}
		this.fitness = fitness/(imgPixels.length*300);
	} */
	
	public void setFitness(BufferedImage biA, BufferedImage biB) {        
	    double fitness = 0;
	    int sizeA = biA.getWidth(), sizeB = biA.getHeight();
	    
	    for(int x=0; x<sizeA; x++) {
	    	for(int y=0; y<sizeB; y++) {
	    		Color c1 = new Color(biA.getRGB(x, y));
	    		Color c2 = new Color(biB.getRGB(x, y));
	    		fitness += Math.abs(c1.getBlue() - c2.getBlue());
	    		fitness += Math.abs(c1.getRed() - c2.getRed());
	    		fitness += Math.abs(c1.getGreen() - c2.getGreen());
	    	}
	    }
		this.fitness = fitness/sizeA;
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
	

	@Override
	public int compareTo(Individual o) {
		Individual ind2 = (Individual) o;
		if(fitness < ind2.getFitness()) 
			return -1;
		else if(fitness == ind2.getFitness()) 
			return 0;
		else 
			return 1;
	}
	
	
}
