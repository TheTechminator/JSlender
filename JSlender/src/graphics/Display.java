package graphics;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Létrehoz egy ablakot amelyben majd grafikusan megjelenhetnek dolgok.
 * @author Terminator
 *
 */
public class Display {
    private JFrame jframe;
    private static Canvas canvas;
    private String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        initCanvas();
    }
    
    public void addKeyListener (JFrameKeyListener listener) {
    	jframe.addKeyListener(listener);
    }
    
    private void initCanvas() {
        jframe = new JFrame(title);
        jframe.setSize(width, height);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        jframe.add(canvas);
        jframe.pack();
    }
    
    public Canvas getCanvas() {

        if(canvas == null)
        {
            System.out.println("Canvas is null");
            return null;
        }

        return canvas;
    }
}