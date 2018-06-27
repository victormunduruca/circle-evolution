import java.io.IOException;
import java.util.ArrayList;

import model.Circle;
import model.Individual;



public class Main {
	static ArrayList<Individual> population;
	static int imgWidth = 0;
	static int imgHeight = 0;
	public static void main(String[] args) throws IOException {
		/*
		BufferedImage image = new BufferedImage(600, 400, BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage image2 = ImageIO.read(new File("dog.jpg"));
		
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillOval(8, 8, 40, 40);
		
		byte[] pixels = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();
		System.out.println(pixels[0]);
		
		
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
		imgWidth =  100;
		imgHeight = 100;
		initializePopulation();
		
		for (Individual i: population) {
			for(Circle c: i.getChromossome()) {
				System.out.println(c.getGrayScale() + "-" +c.getX() + "-" + c.getY() + "\n");
			}
		}
		
	}
	public static void initializePopulation() {
		population = new ArrayList<Individual>();
		for (int i = 0; i < 50; i++) { 
			Individual newIndividual = new Individual(); //create new indvidual
			newIndividual.addRandomly(5, imgWidth, imgHeight); //add random circles to the individual
			population.add(newIndividual); //add individual to population
		}
	}

}
