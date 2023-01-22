package graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Képek betöltését teszi lehetõvé.
 * @author Terminator
 *
 */
public class ImageLoader {
	//Képeket  lehet betöltenni, amely majd megjelenik grafikusan
	
	public static BufferedImage loadImage(String fileName) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(fileName)); 

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image could not be read");
            System.exit(1);
        }

        return bi;
    }
	
	public static BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
	    double rads = Math.toRadians(angle);
	    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
	    int w = img.getWidth();
	    int h = img.getHeight();
	    int newWidth = (int) Math.floor(w * cos + h * sin);
	    int newHeight = (int) Math.floor(h * cos + w * sin);

	    BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = rotated.createGraphics();
	    AffineTransform at = new AffineTransform();
	    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

	    int x = w / 2;
	    int y = h / 2;

	    at.rotate(rads, x, y);
	    g2d.setTransform(at);
	    g2d.drawImage(img, 0, 0, null);
	    g2d.dispose();

	    return rotated;
	}
}
