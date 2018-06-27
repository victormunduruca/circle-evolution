import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Main {
	public static void main(String[] args) throws IOException {
		
		BufferedImage image = new BufferedImage(600, 400, BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage image2 = ImageIO.read(new File("dog.jpg"));
		
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillOval(8, 8, 40, 40);
		
		byte[] pixels = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();
		System.out.println(pixels[0]);
		
		
		File outputfile = new File("saved.png");
		ImageIO.write(image, "png", outputfile);
	}
}
