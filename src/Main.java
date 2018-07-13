
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
	static int chromossomeSize = 3;
	static int populationSize = 10;
	static int tournamentSize = 5;
	static BufferedImage originalImg;
	
	public static void main(String[] args) throws IOException {
		originalImg = ImageIO.read(new File("dog.jpg"));
		imgWidth =  300;
		imgHeight = 300;
		initializePopulation();
		//calculatePopulationFitness(); //calculate population fitness
		
		int i = 0;
		while(i < 10) {
			System.out.println(i);
			calculatePopulationFitness(); //calculate the population fitness
			ArrayList<Individual> offspring = new ArrayList<Individual>(); //generate parent's offspring
			
			//for(int k = 0; k < 3; k++) {
				ArrayList<Individual> parents =  new ArrayList<Individual>(parentSelection()); //select parents
				offspring.addAll(crossover(parents.get(0), parents.get(1)));
			//}
			
			
			
			//System.out.println("OFFSPRING");
			for(Individual j: offspring) {
			//	printIndividual(j);
				mutation(j);
			}
		
			sortIndividuals(population);
			int offCount = 0;
			for(int k = populationSize -1; k > 7; k--) {
				population.set(k, offspring.get(offCount));
				offCount++;
			}
			System.out.println("PRINTANDO POPULACAO");
			for(Individual opa: population)
				printIndividual(opa);
			
			//System.out.println(population.get(0).getFitness());
			
			
//			BufferedImage img = paintIndividual(population.get(0));
//			File outputfile = new File(i+ "   saved"+ i+ ".png");
//			ImageIO.write(img, "png", outputfile);
			
			i++;
		}
	
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
		System.out.println("Primeiro pai");
		System.out.println("id: " +first.getId());
		//printIndividual(first);
		System.out.println("Segundo pai");
		System.out.println("id: " +second.getId());
		//printIndividual(second);
		
		Individual firstCopy = new Individual(first);
		System.out.println("id copia: " +firstCopy.getId());
		Individual secondCopy = new Individual(second);
		System.out.println("id copia: " +secondCopy.getId());
		ArrayList<Circle> firstChromossome = firstCopy.getChromossome(); //retrieve first and second chromossomes
		ArrayList<Circle> secondChromossome = secondCopy.getChromossome(); 
		Circle auxCircle; //auxiliary circle for swap
		
		int breakingIndex = ThreadLocalRandom.current().nextInt(1, chromossomeSize); //breaking adress is randomly selected
		for(int i = 0; i < breakingIndex; i++) { //iterate until breaking index
			auxCircle = firstChromossome.get(i); // value of first circle is saved
			firstChromossome.set(i, secondChromossome.get(i));//swap first and second chromossomes
			secondChromossome.set(i, auxCircle);
		}
		ArrayList<Individual> offspring = new ArrayList<>();
//		System.out.println("Filho 1");
//		printIndividual(new Individual(firstChromossome));
//		//printIndividual(new Individual(firstChromossome));
//		System.out.println("Filho 2");
//		printIndividual(new Individual(secondChromossome));
		offspring.add(new Individual(firstChromossome));
		offspring.add(new Individual(secondChromossome));
		return offspring;
	}
	public static void mutation(Individual i) {
		ArrayList<Circle> chromossome = i.getChromossome();  //get chromossome
		for(int j = 0; j < 2; j++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(0, chromossomeSize);//select a random gene
						
		
				chromossome.get(randomIndex).setGrayScale(ThreadLocalRandom.current().nextInt(0, 256)); 
		
				chromossome.get(randomIndex).setSize(ThreadLocalRandom.current().nextInt(0, 40));
			
				chromossome.get(randomIndex).setX(ThreadLocalRandom.current().nextInt(0, imgWidth));
				chromossome.get(randomIndex).setY(ThreadLocalRandom.current().nextInt(0, imgHeight));
		
		}
		i.setChromossome(chromossome);
	}
	public static ArrayList<Individual> parentSelection() {
		System.out.println("Selecionados:");
		//TODO 
		ArrayList<Individual> kIndividuals = new ArrayList<Individual>();
		ArrayList<Individual> bestIndividuals = new ArrayList<Individual>();	
		
		for(int i = 0; i < tournamentSize; i++) {//select random k individuals
			int randIndex = ThreadLocalRandom.current().nextInt(0, populationSize);
			//System.out.println("Indice aleatorio" +randIndex);
			Individual newIndividual = new Individual(population.get(randIndex));
			if(!kIndividuals.contains(newIndividual)) {
				printIndividual(population.get(randIndex));
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
		System.out.println(i.getFitness() + " " + i.getId());
		//System.out.println("\n");
	}
}

