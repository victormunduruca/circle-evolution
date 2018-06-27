package model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Individual {
	private ArrayList<Circle> chromossome;
	
	public Individual() {
		chromossome = new ArrayList<Circle>();
	}
	public void addRandomly(int numberOfCircles, int imgWidth, int imgHeight) {
		for(int i = 0; i < numberOfCircles; i++) {
			chromossome.add(new Circle(imgWidth, imgHeight));
		}
	}
	
	
	public ArrayList<Circle> getChromossome() {
		return chromossome;
	}
	public void setChromossome(ArrayList<Circle> chromossome) {
		this.chromossome = chromossome;
	}
	
	
}
