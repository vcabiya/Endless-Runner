import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;


public class runnerCode extends JPanel implements KeyListener  {  
	
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage canvas;
	
	
	public static void main(String[] args) 
	{
	int width = 1440;
	int height = 850;
	JFrame frame = new JFrame("Aversion"); 
	runnerCode panel = new runnerCode (width, height);
	frame.add(panel);
	frame.pack();
	frame.setVisible(true);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	
	public runnerCode (int width, int height)
	{
	canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	addKeyListener(this);
	setFocusable(true);
	}
	
	public Dimension getPreferredSize()
	{
	return new Dimension(canvas.getWidth(), canvas.getHeight());
	}
	// All default values used in later paintComponent section.
    Walker player = new Walker(200,450,0,0,90,150);
    Walker pSprite = new Walker(190,440,0,0,110,160);
    ArrayList <Walker> enemies = new ArrayList<Walker>();
    ArrayList <Walker> enemySprite = new ArrayList<Walker>();
    int yLimit = player.y;
    double grav = 1.3;
    int fall = 0;
    int crouch = 0;
    int genEnemies=0;
	double increaseSpeed= 0;
	double obsSpeedIncrease = 15;
	int obsGap = 500;
	int end=0;
	int warn=0;
	int displayWarn=100;
	int score = 0;
	int scoreInc= 100;
	int upScore=0;
	int startupSprite =0;
	int drawbutton=0;
	Font death = new Font("", Font.BOLD,100);
	Font deathsmall = new Font("", Font.PLAIN,60);
	Font point = new Font("", Font.BOLD,20);
	

	BufferedImage player1 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); 
	BufferedImage top = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); 
	BufferedImage bot = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); 

	// All of the following image files were created by me.
    String pl1 = "/Users/diegocabiya/eclipse-workspace/422/src/1.png"; 
    String pl2 = "/Users/diegocabiya/eclipse-workspace/422/src/2.png";
    String pl3 = "/Users/diegocabiya/eclipse-workspace/422/src/3.png";
    String pl4 = "/Users/diegocabiya/eclipse-workspace/422/src/4.png";
    String cro = "/Users/diegocabiya/eclipse-workspace/422/src/cr.png";
    String bird = "/Users/diegocabiya/eclipse-workspace/422/src/bird?.png";
    String bush = "/Users/diegocabiya/eclipse-workspace/422/src/bush.png";



    
	public void paintComponent(Graphics page)
	{

	Graphics2D g2 = (Graphics2D) page;
	g2.setStroke(new BasicStroke(10));
	
	if (crouch==0){
		if (startupSprite==0) 
			player1 = setImage(pl1);
		if (startupSprite==8) 
		player1 = setImage(pl2);
		if (startupSprite==16) 
			player1 = setImage(pl3);
		if (startupSprite==24) 
			player1 = setImage(pl4);
	}
	if (crouch==1) {
	player1 = setImage(cro);
	}
	page.setColor(Color.white);
	page.fillRect(0, 0, 1440, 850);
	page.drawImage(player1, pSprite.x, pSprite.y, null);
	page.setColor(Color.black);
	page.drawLine(0, 595, 1440, 595);
	top = setImage(bird);
	bot = setImage(bush);
	
	if (drawbutton==1) {
		page.setColor(Color.red);
		}
	page.fillRect(1315, 10, 50, 50);
	page.setColor(Color.black);
	if (drawbutton==2) {
		page.setColor(Color.red);
		}
	page.fillRect(1315, 70, 50, 50);
	
	page.setColor(Color.black);
	page.fillRect(1375, 70, 50, 50);
	page.fillRect(1255, 70, 50, 50);


	page.setFont(point);
	page.setColor(Color.white);
	page.drawString("↑", 1333, 42);
	page.drawString("↓", 1333, 102);
	page.drawString("←", 1268, 102);
	page.drawString("→	", 1388, 102);
	
	page.setColor(Color.black);
	page.drawString("↑ - Jump", 1290, 150);
	page.drawString("↓ - Slide", 1290, 175);
	


// Draws all enemy images.
	for (int k=0; k<enemies.size(); k++)
	{
		if (enemySprite.get(k).x<1440) {
			if (enemySprite.get(k).y==410) {
		page.drawImage(top, enemySprite.get(k).x, enemySprite.get(k).y, null);
			}
			if (enemySprite.get(k).y==530) {
				page.drawImage(bot, enemySprite.get(k).x, enemySprite.get(k).y, null);
					}
			
		}

	}
	
	if (end<2) {
		
// Generates new random enemy position and quantity
	if (genEnemies==0) {
		Random r = new Random();
		int enemyNum =r.nextInt(6);
		enemies = setObs(enemyNum, obsSpeedIncrease, obsGap, 0); 
		enemySprite= setSprite(enemies);
		if (obsSpeedIncrease<21)
		obsSpeedIncrease+=1;
		if (obsGap<800)
		obsGap+=50;
		genEnemies+=1;
	}
	
	
// Moves enemies and their images
	for (int moveE=0; moveE<enemies.size(); moveE++)
	{
	enemies.get(moveE).x-=enemies.get(moveE).xC;
	enemySprite.get(moveE).x= (enemies.get(moveE).x-10);
	if (enemies.get(moveE).x<-100) {
		enemies.remove(moveE);
		enemySprite.remove(moveE);
		}
	}
	
	if (enemies.size()==0) {
	genEnemies=0;
	}
	
// Player movement and jump code.
	player.y-=player.yC;
	pSprite.y=player.y-10;
	startupSprite+=1;
	
	if (startupSprite==32)
		startupSprite=0;
	
	if (fall==1) {
		player.yC -= grav;
	}
	
	if (crouch == 0){
	if (player.y>yLimit) {
		player.y=yLimit;
		fall=0;
		player.yC=0;
	}
	}
	
	
// Used for point booster / game over
	end = collision(pSprite,enemySprite);

	if (end==1) {
	scoreInc= 500;
	page.setFont(death);
	page.setColor(Color.red);
	page.drawString("!",player.x+30,player.y-60);
	}

	if (end==0)
	scoreInc= 100;

	upScore+=1;
	if (upScore==10) {
	score+=scoreInc;
	upScore=0;
	}
	}
	
	if (end==0) {
		page.setColor(Color.black);}
		if (end==1) {
		page.setColor(Color.red);}
		page.setFont(point);	
		page.drawString(""+score, 10, 24);
	
	end = collision(player,enemies);

	if (end==2) {
		page.setFont(death);
		page.setColor(Color.black);
		page.drawString("Game Over", 430, 250);
	}
	

	try
	{
	Thread.sleep(10); //Causes a delay between repaints and repeats above code
	repaint();
	}
	catch (InterruptedException e) 
	{
	System.out.println("error");
	System.exit(1);
	}
	
}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
	 
	if (end==0) {
		if (code==38) { // 38 = Up arrow. This causes the player to jump
			drawbutton=1;
			if (player.y==yLimit) {
				player.yC=30;
				fall=1;
			}
		}
		if (code==40) { // 40 = Down arrow. This causes the player to slide
			drawbutton=2;
		if (fall==0) {
			crouch = 1;
			player.s2=110;
			pSprite.s2=120;
			player.y=485;
		}
		}
	}
		
		
	}
public void keyReleased(KeyEvent e)
	{
		int code2 = e.getKeyCode(); 
		drawbutton=0;


		if (code2==40) {
			if (fall==0) {
				crouch = 0;
				pSprite.y=440;
				player.s2=150;
				pSprite.s2=160;
				player.y=450;
			}
		}
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public Walker jump (Walker w) {
		
		return w;
	}
	
	static BufferedImage setImage (String s) { // Assigns value to BufferedImage from a file which is referenced from a string.
		BufferedImage ima;
		ima = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
	try {
	ima = ImageIO.read(new File(s)); 
	}
	catch(Exception ex) {
		System.out.println("Error");
		ex.printStackTrace();
		}
	return ima;
	}
	
	static ArrayList<Walker> setObs (int r, double speed, int gap, int type){
		ArrayList <Walker> w = new ArrayList<Walker>();
		for (int k=0; k<r; k++)
		{
			Walker wInsert = new Walker(0,0,speed,0,100,40);

			Random yPlace = new Random();
			int rLocation =yPlace.nextInt(2);	
				if (rLocation==1) { // 1 high 0 low
					wInsert.y=420;
					}
				if (rLocation==0) { 
					wInsert.y=540;
					}
				Random xPlace = new Random();
				int rDistance =xPlace.nextInt(2000);	
				rDistance+=gap;
			if (w.size()==0) {
			wInsert.x=2000;
			}
			else {
			wInsert.x=	w.get(k-1).x+rDistance;
			}
			
			w.add(wInsert);
		}
		
	return w;
	}
	
	static ArrayList<Walker> setSprite (ArrayList<Walker> baseE){
		ArrayList <Walker> w2 = new ArrayList<Walker>();
		for (int r2=0; r2<baseE.size(); r2++)
		{
		Walker w2Insert = new Walker(0,0,0,0,120,60);
		w2Insert.x= baseE.get(r2).x-10;
		w2Insert.y= baseE.get(r2).y-10;
		w2Insert.xC= baseE.get(r2).xC;
		w2.add(w2Insert);
		}
		return w2;
	}
	
	static int collision (Walker play, ArrayList<Walker> obs){
	int check = 0;
	for (int c=0; c<obs.size(); c++)
	{
		if (play.s1==90) {
			if (play.x>(obs.get(c).x-play.s1)){
			if (play.x<(obs.get(c).x+obs.get(c).s1)) {
			if (play.y>(obs.get(c).y-play.s2)) {
			if (play.y<(obs.get(c).y+obs.get(c).s2)) {
				check=2;	
			}
			}
			}
			}
		}

		if (play.s1==110) {
			if (play.x>(obs.get(c).x-play.s1-150)){
				if (play.x<(obs.get(c).x+obs.get(c).s1+150)) {
				if (play.y>(obs.get(c).y-play.s2-150)) {
				if (play.y<(obs.get(c).y+obs.get(c).s2)) {
						check=1;
				}
				}
				}
				}
		}
	}
	return check;
	}
}

class Walker{
int x;
int y;
double xC;
double yC;
int s1;
int s2;
public Walker(int x0, int y0, double xC0, double yC0, int s10, int s20) {
x=x0;
y=y0;
xC=xC0;
yC=yC0;
s1=s10;
s2=s20;
}
}