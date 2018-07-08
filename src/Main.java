
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import model.Circle;
import model.Individual;



public class Main {
	static ArrayList<Individual> population;
	static int imgWidth = 0;
	static int imgHeight = 0;
	static int chromossomeSize = 200;
	static int populationSize = 100;
	static int tournamentSize = 5;
	static BufferedImage originalImg;
	
	public static void main(String[] args) throws IOException {
		originalImg = ImageIO.read(new File("fit2.png"));
		imgWidth =  300;
		imgHeight = 300;
		initializePopulation();
		calculatePopulationFitness(); //calculate population fitness
		
		
		int i = 0;
		while(i < 500) {
			System.out.println(i);
			ArrayList<Individual> offspring = new ArrayList<Individual>(); //generate parent's offspring
			
			for(int k = 0; k < 37; k++) {
				ArrayList<Individual> parents =  new ArrayList<Individual>(parentSelection()); //select parents
				offspring.addAll(crossover(parents.get(0), parents.get(1)));
			}
			
			for(Individual j: offspring) {
				mutation(j);
			}
			
//			mutation(offspring.get(0)); //mutate offspring
//			mutation(offspring.get(1));
			sortIndividuals(population);
			int offCount = 0;
			for(int k = populationSize -1; k > 62; k--) {
				population.set(k, offspring.get(offCount));
				offCount++;
			}
//			population.set(population.size() - 1, offspring.get(0)); //replace old individuals with offspring
//			population.set(population.size() - 2, offspring.get(1));
			calculatePopulationFitness(); //calculate the population fitness
			
			BufferedImage img = paintIndividual(population.get(0));
			File outputfile = new File(i+ "   saved"+ i+ ".png");
			ImageIO.write(img, "png", outputfile);
			
			i++;
		}
//		BufferedImage img1 = ImageIO.read(new File("primeiro.png"));
//		BufferedImage img2 = ImageIO.read(new File("segundo.png"));
//		
//		double fitness = 0;
//		int subtraction;
//		byte[] imgPixels = ((DataBufferByte) img1.getRaster().getDataBuffer()).getData(); //get pixel arrays
//		byte[] originalPixels = ((DataBufferByte) img2.getRaster().getDataBuffer()).getData();
//		for(int i = 0; i < imgPixels.length; i++) { //iterate over arrays and subtract equivalent pixels and sum the 
//			subtraction = Math.abs(imgPixels[i] - originalPixels[i]);//get the absolute value of the subtraction 
//			fitness += subtraction;//keep the result into a variable 
//			System.out.println(fitness);
//		}
//		System.out.println("Fitness: " +fitness);
	
	}
	public static void initializePopulation() {
		population = new ArrayList<Individual>();
		for (int i = 0; i < populationSize; i++) { 
			Individual newIndividual = new Individual(); //create new individual
			newIndividual.addRandomly(chromossomeSize, imgWidth, imgHeight); //add random circles to the individual
			population.add(newIndividual); //add individual to population
		}
	}
	public static BufferedImage paintIndividual(Individual i) {
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_BYTE_GRAY); //create blank image
		Graphics2D g = image.createGraphics(); //create 2D java canvas
		for(Circle c: i.getChromossome()) {
			g.setColor(new Color(c.getGrayScale(), c.getGrayScale(), c.getGrayScale())); //set color and fill the canvas with a new circle
			g.fillOval(c.getX(), c.getY(), c.getSize(), c.getSize());
		}
		return image; //returns the painted image
	}
	
	public static  ArrayList<Individual> crossover(Individual first, Individual second) {
		ArrayList<Circle> firstChromossome = new ArrayList<Circle>(first.getChromossome()); //retrieve first and second chromossomes
		ArrayList<Circle> secondChromossome = new ArrayList<Circle>(second.getChromossome()); 
		Circle auxCircle; //auxiliary circle for swap
		
		int breakingIndex = ThreadLocalRandom.current().nextInt(1, chromossomeSize); //breaking adress is randomly selected
		for(int i = 0; i < breakingIndex; i++) { //iterate until breaking index
			auxCircle = firstChromossome.get(i); // value of first circle is saved
			firstChromossome.set(i, secondChromossome.get(i));//swap first and second chromossomes
			secondChromossome.set(i, auxCircle);
		}
		ArrayList<Individual> offspring = new ArrayList<>();
		offspring.add(new Individual(firstChromossome));
		offspring.add(new Individual(secondChromossome));
		return offspring;
	}
	public static void mutation(Individual i) {
		ArrayList<Circle> chromossome = i.getChromossome();  //get chromossome
		for(int j = 0; j < 2; j++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(0, chromossomeSize);//select a random gene
			int randomFeature = ThreadLocalRandom.current().nextInt(0, 3);//select a random characteristic
			//randomly reset the choosen characteristic's value
			if(randomFeature == 0) {
				chromossome.get(randomIndex).setGrayScale(ThreadLocalRandom.current().nextInt(0, 256)); 
			} else if (randomFeature == 1){
				chromossome.get(randomIndex).setSize(ThreadLocalRandom.current().nextInt(0, 40));
			} else if(randomFeature == 2){ 
				chromossome.get(randomIndex).setX(ThreadLocalRandom.current().nextInt(0, imgWidth));
				chromossome.get(randomIndex).setY(ThreadLocalRandom.current().nextInt(0, imgHeight));
			}
		}
		i.setChromossome(chromossome);
	}
	public static ArrayList<Individual> parentSelection() {
		//TODO 
		ArrayList<Individual> kIndividuals = new ArrayList<Individual>();
		ArrayList<Individual> bestIndividuals = new ArrayList<Individual>();	
		
		for(int i = 0; i < tournamentSize; i++) {//select random k individuals
			Individual newIndividual = population.get(ThreadLocalRandom.current().nextInt(0, populationSize));
			if(!kIndividuals.contains(newIndividual)) {
				kIndividuals.add(newIndividual);
			}
		}
		sortIndividuals(kIndividuals); //order them
		bestIndividuals.add(kIndividuals.get(0)); //return two best fitness
		bestIndividuals.add(kIndividuals.get(1));
		return bestIndividuals;
	}
	public static void sortIndividuals(ArrayList<Individual> list) {
		Collections.sort(list, new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				Individual ind1 = (Individual) o1;
				Individual ind2 = (Individual) o2;
				if(ind1.getFitness() < ind2.getFitness()) 
					return -1;
				else if(ind1.getFitness() == ind2.getFitness()) 
					return 0;
				else 
					return 1;
			}
		});
	}
	public static void calculatePopulationFitness() {
		int index = 0;
		for(Individual i : population) {
			BufferedImage img = paintIndividual(population.get(index));
			i.setFitness(img, originalImg);
			index++;
		}
	}
	public static void printIndividual(Individual i) {
		//TODO remover
		for (Circle c : i.getChromossome()) {
			System.out.print(c.toString());
		}
		System.out.println(i.getFitness());
		System.out.println("\n");
	}
}

