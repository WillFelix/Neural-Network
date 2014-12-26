package project.kmeans;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import project.brain.Point;

@SuppressWarnings("serial")
public class GraphingData extends JPanel {
	private Map< List<Double>, Color > x = new HashMap< List<Double>, Color >();
	private Map< List<Double>, Color > y = new HashMap< List<Double>, Color >();
	private final int PAD = 20;
	private final Color[] colors = {Color.blue, Color.red, Color.green};

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int w = getWidth();
		int h = getHeight();
		double xInc = (double)(w - 2 * PAD) / (x.size() - 1);
		double scale = (double)(h - 2 * PAD) / getMax();
		
		g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));					// Draw ordinate
		g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));			// Draw abcissa
		
		for (Entry<List<Double>, Color> entry : x.entrySet()) {
			List<Double> db = entry.getKey();
			Color color = entry.getValue();
			
			g2.setPaint(color);												// Mark data points
			for (int i = 0; i < db.size(); i++) {
				double x = (PAD + i * xInc) + db.get(i);
				double y = (h - PAD - scale) * db.get(i);
				g2.fill(new Ellipse2D.Double(x, y, 5, 5));
			}
		}
	}
	
	private int getMax() {
        int max = -Integer.MAX_VALUE;
        
        for (int i = 0; i < x.keySet().iterator().next().size(); i++) {
            if (x.keySet().iterator().next().get(i) > max)
                max = x.keySet().iterator().next().get(i).intValue();
        }
        
        return max;
    }
	
	public void start() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.setVisible(true);
		f.add(this);
	}
	
	public void addData(List<Point> points) {
		Random r = new Random();
		List<Double> newX = new ArrayList<Double>();
		List<Double> newY = new ArrayList<Double>();
		
		for (Point p : points) {
			newX.add(p.getInput().get(0));
			newY.add(p.getInput().get(1));
		}
		
		int n = r.nextInt(2);
		x.put(newX, colors[n]);
		y.put(newY, colors[n]);
	}
	
}