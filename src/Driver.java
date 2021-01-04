import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Driver extends Panel{
	private static final int IMAGE_WIDTH = 400;
	private static final int IMAGE_HEIGHT = 252;
	private static final int NUM_HOR_IMAGES = 3;
	private static final int NUM_VERT_IMAGES = 1;
	private static final double SIDE_PROP = 2.0 / 9;
	private static final double TOP_PROP = 1.0 / 2;
	private static final double HOR_MID_PROP = 5.0 / 18;
	private static final int TICKET_CIRCLE_DIAMETER = 12;
	ArrayList<Route> routeList = new ArrayList<Route>();

	public static void main(String[] args) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Driver m = new Driver();
		JFrame f = new JFrame();
		f.add(m);
		f.setSize((int)dim.getWidth(), (int)dim.getHeight());
		f.setVisible(true);
	}

	private static ArrayList<Destination> loadData(){
		ArrayList<Destination> list = new ArrayList<>();
		try{
			Scanner input = new Scanner(new File ("DestinationList.txt"));
			while(input.hasNextLine()){
				String name = input.nextLine();
				int xCoord = input.nextInt();
				int yCoord = input.nextInt();
				int tickX = input.nextInt() * 248/1869;
				int tickY = input.nextInt() * 248/1869;
				input.nextLine();
				Destination dest = new Destination(name, xCoord, yCoord, tickX, tickY);
				list.add(dest);
			}
			input.close();
		}
		catch(FileNotFoundException exc){
			System.out.println("Error reading " + "DestinationList.txt" + "\n" + exc.getMessage());
			exc.printStackTrace();
			System.exit(0);
		}
		return list;
	}

	public void paint(Graphics g){
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = null;
		try{
			File f = new File ("TTR Ticket.png");
			img = ImageIO.read(f);
		}
		catch(Exception e){
			System.out.println("Could not find image.");
			System.exit(0);
		}
		Dimension dim = t.getScreenSize();
		if(dim.getWidth() <= IMAGE_WIDTH * NUM_HOR_IMAGES || dim.getHeight() <= IMAGE_HEIGHT * NUM_VERT_IMAGES){
			System.out.println("\nThis program is not compatible with your current screen resolution. Try using a larger screen.");
			System.exit(0);
		}
		else{
			int horSpacing = (int) ((dim.getWidth() - (NUM_HOR_IMAGES * IMAGE_WIDTH)) / (NUM_HOR_IMAGES + 1));
			int topSpacing = (int) ((dim.getHeight() - (NUM_VERT_IMAGES * IMAGE_HEIGHT)) / (NUM_VERT_IMAGES + 1));
			g.fillRect(0, 0, (int)dim.getWidth(), (int)dim.getHeight());

			DestinationList dlist = new DestinationList(loadData());
			for(int i = 1; i <= NUM_VERT_IMAGES; i++){
				for(int j = 1; j <= NUM_HOR_IMAGES; j++){
					int hor = j * (horSpacing + IMAGE_WIDTH) - IMAGE_WIDTH;
					int vert = i * (topSpacing + IMAGE_HEIGHT) - IMAGE_HEIGHT;
					g.drawImage(img, hor, vert, this);
					Route r = dlist.generateRoute();
					while(routeList.contains(r)){
						r = dlist.generateRoute();
					}
					routeList.add(r);
					displayRoute(g, r, hor, vert);
				}
			}
			try{
				//FONT
				Font font = Font.createFont(Font.TRUETYPE_FONT, new File("SHANLNC_.TTF"));
				font = font.deriveFont(36f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(font);
				g.setFont(font);

				//TEXT
				String message = "TYPE \"ENTER\" IN CONSOLE TO CLOSE";
				int stringWidth = g.getFontMetrics(font).stringWidth(message);
				g.drawString(message, (int) (dim.getWidth() / 2 - stringWidth / 2), 50);
			}
			catch(Exception e){
				System.out.println("Could not create font");
			}
			Scanner input = new Scanner(System.in);
			String in = input.nextLine();
			System.exit(0);
		}
	}

	public void displayRoute(Graphics g, Route route, int horSpacing, int vertSpacing){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int d1x = route.getDest1().getTickX() + horSpacing,
					d2x = route.getDest2().getTickX() + horSpacing,
					d1y = route.getDest1().getTickY() + vertSpacing,
					d2y = route.getDest2().getTickY() + vertSpacing;
		try{
			//FONT
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("SHANLNC_.TTF"));
			font = font.deriveFont(18f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
			g.setFont(font);

			//TEXT
			int stringWidth = g.getFontMetrics(font).stringWidth(route.toString());
			int textTopSpace = (int)(29 + 18f / 2);
			g.drawString(route.toString(), horSpacing + IMAGE_WIDTH / 2 - stringWidth / 2, vertSpacing + textTopSpace);
		}
		catch(Exception e){
			System.out.println("Could not create font");
		}
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = null;
		try{
			img = ImageIO.read(new File("Ticket Circle 2.png"));
			g.setColor(Color.BLACK);
			g.drawLine(d1x, d1y, d2x, d2y);
			g.drawImage(img, d1x - TICKET_CIRCLE_DIAMETER / 2, d1y - TICKET_CIRCLE_DIAMETER / 2, this);
			g.drawImage(img, d2x - TICKET_CIRCLE_DIAMETER / 2, d2y - TICKET_CIRCLE_DIAMETER / 2, this);
		}
		catch(Exception e){
			System.out.println("Could not find image.");
		}
	}
}