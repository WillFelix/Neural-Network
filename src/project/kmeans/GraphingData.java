package project.kmeans;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;

import project.brain.Point;

@SuppressWarnings("serial")
public class GraphingData extends JPanel {
	private Map< List<Point>, Color > groups = new LinkedHashMap< List<Point>, Color >();
	private final int PAD = 10;
	private int n = 0, var = 100, index = 0;
	private final Color[] colors = {Color.blue, Color.red, Color.green};

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int w = getWidth();
		int h = getHeight();
		
		g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));					// Draw ordinate
		g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));			// Draw abcissa
		
		for (Entry< List<Point>, Color > entry : groups.entrySet()) {
			Color color = entry.getValue();
			g2.setPaint(color);
			
			List<Point> points = entry.getKey();
			for (Point p : points) {
				double x = (p.getInput().get(0) * 130) + 350;
				double y = p.getInput().get(1) * 130;
				
				g2.fill(new Ellipse2D.Double(x, y, 5, 5));
			}
			
			g2.draw(new Rectangle2D.Double(w - 100, h - var, 5, 5));
			g2.drawString("Group " + index, w - 90, h - (var - 7));
			var -= 15;
			index++;
		}
		
	}
	
	public void start() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());
		f.setSize(xSize,ySize);
		
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setTitle("Groups [" + groups.size() + "]");
		f.add(this);
	}
	
	public void addData(List<Point> points) {
		groups.put(points, colors[n++]);
	}
	
}