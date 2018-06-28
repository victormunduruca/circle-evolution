
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


import model.Circle;
import model.Individual;



public class Main {
	static ArrayList<Individual> population;
	static int imgWidth = 0;
	static int imgHeight = 0;
	static BufferedImage originalImg;
	public static void main(String[] args) throws IOException {

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
		//Population initiaization
		//loop
		//fitness function calculation
			//paint circles 
		//crossover
		//mutation
		//survivor selecton
		//endloop
		//terminate and return best
		
//		imgWidth =  300;
//		imgHeight = 300;
//		initializePopulation();
//		int o = 0;
//		for (Individual i: population) {
//			o++;
//			BufferedImage img = paintIndividual(i);
//			File outputfile = new File("saved"+ o+ ".png");
//			ImageIO.write(img, "png", outputfile);
//		}
		originalImg = ImageIO.read(new File("dog.jpg"));
		BufferedImage img = ImageIO.read(new File("dog copy.jpg"));
		System.out.println(fitness(img));
		
	}
	public static void initializePopulation() {
		population = new ArrayList<Individual>();
		for (int i = 0; i < 50; i++) { 
			Individual newIndividual = new Individual(); //create new indvidual
			newIndividual.addRandomly(15, imgWidth, imgHeight); //add random circles to the individual
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
	public static int fitness(BufferedImage img) {
		int fitness = 0, subtraction;
		byte[] imgPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); //get pixel arrays
		byte[] originalPixels = ((DataBufferByte) originalImg.getRaster().getDataBuffer()).getData();
		for(int i = 0; i < imgPixels.length; i++) { //iterate over arrays and subtract equivalent pixels and sum the 
			subtraction = Math.abs(imgPixels[i] - originalPixels[i]);//get the absolute value of the subtraction 
			fitness += subtraction;//keep the result into a variable 
		}
		return fitness;
	}
}
