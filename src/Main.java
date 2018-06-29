
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
	static int chromossomeSize = 4;
	static BufferedImage originalImg;
	public static void main(String[] args) throws IOException {
		originalImg = ImageIO.read(new File("dog.jpg"));
		imgWidth =  300;
		imgHeight = 300;
		initializePopulation();
		int i = 0;
		while(i < 2) {
			calculatePopulationFitness();
			ArrayList<Individual> parents =  parentSelection();
			ArrayList<Individual> offspring = crossover(parents.get(0), parents.get(1));
			mutation(offspring.get(0));
			mutation(offspring.get(1));
			population.set(population.size() - 1, offspring.get(0));
			population.set(population.size() - 2, offspring.get(1));
			i++;
		}
		//Population initiaization
		//loop
		//fitness function calculation
			//paint circles 
		//crossover
		//mutation
		//survivor selecton
		//endloop
		//terminate and return best
		
		
//		initializePopulation();
//		int o = 0;
//		for (Individual i: population) {
//			o++;
//			BufferedImage img = paintIndividual(i);
//			File outputfile = new File("saved"+ o+ ".png");
//			ImageIO.write(img, "png", outputfile);
//		}
		

		
	}
	public static void initializePopulation() {
		population = new ArrayList<Individual>();
		for (int i = 0; i < 10; i++) { 
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
		ArrayList<Circle> firstChromossome = first.getChromossome(); //retrieve first and second chromossomes
		ArrayList<Circle> secondChromossome = second.getChromossome();
		Circle auxCircle; //auxiliary circle for swap
		
		int breakingIndex = ThreadLocalRandom.current().nextInt(1, chromossomeSize); //breaking adress is randomly selected
		System.out.println("BreakingIndex: " +breakingIndex);
		for(int i = 0; i < breakingIndex; i++) { //iterate until breaking index
			auxCircle = firstChromossome.get(i); // value of first circle is saved
			firstChromossome.set(i, secondChromossome.get(i));//swap first and second chromossomes
			secondChromossome.set(i, auxCircle);
		}
		ArrayList<Individual> offspring = new ArrayList<>();
		offspring.add(first);
		offspring.add(second);
		return offspring;
	}
	public static void mutation(Individual i) {
		ArrayList<Circle> chromossome = i.getChromossome();  //get chromossome
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
	public static ArrayList<Individual> parentSelection() {
		//TODo
		sortPopulation();
		ArrayList<Individual> bestIndividuals = new ArrayList<Individual>();
		bestIndividuals.add(population.get(0));
		bestIndividuals.add(population.get(1));
		return bestIndividuals;
	}
	public static void sortPopulation() {
		Collections.sort(population, new Comparator<Individual>() {
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
		System.out.println("\n");
	}
}
/*BufferedImage image = new BufferedImage(600, 400, BufferedImage.TYPE_BYTE_GRAY);
//BufferedImage image2 = ImageIO.read(new File("dog.jpg"));

Graphics2D g = image.createGraphics();
g.setColor(new Color(255, 255, 255));
g.fillOval(8, 8, 40, 40);

//byte[] pixels = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();
//System.out.println(pixels[0]);


File outputfile = new File("saved.png");
ImageIO.write(image, "png", outputfile);
*/
