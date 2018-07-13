package model;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

public class Individual implements Comparable<Individual>{
	static int id = 0;
	private int objId;
	private ArrayList<Circle> chromossome;
	private double fitness;
	
	public Individual(){
		objId = id;
		id++;
		chromossome = new ArrayList<Circle>();
	}
	public Individual(Individual i) {
		objId = id;
		id++;
		chromossome = new ArrayList<Circle>();
		for(Circle c : i.getChromossome()) {
			try {
				chromossome.add((Circle) c.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.fitness = i.getFitness();
	}
	public Individual(ArrayList<Circle> chromossome) {
		objId = id;
		id++;
		this.chromossome = chromossome;
	}
	
	public void addRandomly(int numberOfCircles, int imgWidth, int imgHeight) {
		for(int i = 0; i < numberOfCircles; i++) {
			chromossome.add(new Circle(imgWidth, imgHeight));
		}
	}
	
	public void setFitness(BufferedImage img, BufferedImage originalImg) {
		double fitness = 0;
		int subtraction;
		byte[] imgPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); //get pixel arrays
		byte[] originalPixels = ((DataBufferByte) originalImg.getRaster().getDataBuffer()).getData();
		for(int i = 0; i < imgPixels.length; i++) { //iterate over arrays and subtract equivalent pixels and sum the 
			subtraction = Math.abs(imgPixels[i] - originalPixels[i]);//get the absolute value of the subtraction 
			fitness += subtraction;//keep the result into a variable 
		}
		this.fitness = fitness/(imgPixels.length*300);
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
	public int getId() {
		return objId;
	}
	
	
}
