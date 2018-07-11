/* Final Project- Cat Chaos
 * The goal of the game is to reach the cat's owner house without being trapped in the moving obstacles
 * By: Nicole Streltsov
 * January 2018
 */

//Import the classes needed 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*; 

//Imports neeeded for image files
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class CatChaosGameFinal{ 
    
    // Game Window properties
    static JFrame gameWindow;
    static GraphicsPanel canvas;
    static final int WIDTH = 900;
    static final int HEIGHT = 567;
    static final int TOP = 0;
    static final int BOTTOM = 500;
    static final int LEFT = 0;
    static final int RIGHT = 860;
    // key listener
    static MyKeyListener keyListener = new MyKeyListener();    
    // mouse listeners
    static MyMouseListener mouseListener = new MyMouseListener();
    
    //--------------------------------------------------------------------------
    // Properties of Game Objects
    //--------------------------------------------------------------------------
    
    //Main menu
    static BufferedImage logo;
    static BufferedImage instructions;
    static int playButtonX= 365;
    static int playButtonY= 310;
    static int playButtonW= 150;
    static int playButtonH= 40;  
    static int instructionX= 320;
    static int instructionY= 370;
    static int instructionW= 255;
    static int instructionH= 40;
    static int scoreButtonX=340;
    static int scoreButtonY= 490;
    static int scoreButtonW= 195;
    static int scoreButtonH= 40;
             
    //Color choosing screen
    static BufferedImage[] colors = new BufferedImage[4];
    static String catColor="";
    static int orangeX=200;
    static int orangeY=90;
    static int yellowX=550;
    static int yellowY=90;
    static int blackX=200;
    static int blackY=310;
    static int redX=550;
    static int redY=310;
    static int colorW=150;
    static int colorH=129;
    
    static String username=null;
    
    //Background properties
    static int backgroundStep = 5;
    static BufferedImage background1;
    static int background1X = 0;
    static int background1Y = 0;
    static int background1W = 1000;
    
    static BufferedImage background2;
    static int background2X = background1W;
    static int background2Y = 0;
    static int background2W = 1000;  
    
    static BufferedImage background3;
    static int background3X = background1X+background1W;
    static int background3Y = 0;
    static int background3W = 1000; 
    
    //Cat properties
    static final int GROUND_LEVEL = 470;
    static final int RUN_SPEED = 7;
    static final int JUMP_SPEED = -25;
    static final int GRAVITY = 2;
    
    static int catH=60;
    static int catW=60;
    static int catX=50;
    static int catY=GROUND_LEVEL;
    static int catVx = 0;
    static int catVy = 0;
    static int catPicNum = 1;
    static String catState = "standing right";
    static BufferedImage[] catPic = new BufferedImage[10];
    static int [] nextLeftPic= {7, 6, 7, 8, 7, 7, 8, 6, 6, 7}; 
    static int [] nextRightPic = {2, 3, 1, 1, 2, 2, 1, 2, 3, 2};
    static Rectangle catBox = new Rectangle(catX, catY, catW, catH);
    static boolean collided=false;
    
    //--------------------------Level properties-------------------------
    
    //Pause/play button
    static boolean pause=true;
    static int pauseX=30;
    static int pauseY=30;
    static int pauseW=20;
    static int pauseH=15;
    
    //Platform properties
    static int platformH=20;
    static int numPlatforms=25;
    static int [] platformWidth= new int [numPlatforms];
    static int [] platformXValues= new int[numPlatforms];
    static int [] platformYValues= new int [numPlatforms];
    static TexturePaint platformWood;
    static BufferedImage wood;
    
    //Coin properties
    static BufferedImage coin;
    static int coinH=60;
    static int coinW=72;
    static int numCoins=100;
    static int [] coinsX= new int [numCoins];
    static int [] coinsY= new int [numCoins];
    static boolean [] coinDisplay= new boolean [numCoins];
    static int coinCollect=0;
    
    //Puddle properties
    static BufferedImage puddle;
    static int puddleW=159;
    static int puddleH=100;
    static int puddleY=GROUND_LEVEL-(puddleH/2);
    static int numPuddles=8;
    static int [] puddleXValues= new int[numPuddles];
    static Rectangle puddleBox;
    
    //Dog properties
    static final int JUMP_SPEED2 = -30;
    static final int GRAVITY2 =1;
    static BufferedImage dog;
    static int dogW=72;
    static int dogH=60;
    static int numDogs=5;
    static int dogVy=JUMP_SPEED;
    static int [] dogXValues= new int[numDogs];
    static int [] dogYValues= new int[numDogs];
    static int [] platformLevel= new int [numDogs];
    static Rectangle dogBox;
    static int count=0;
    
    //Switching screens
    static int screenNum=1;
    static boolean inPlay=false;
   
    //End of Game properties
    static BufferedImage confetti;
    static int topTen= 10;
    static int smallestScore=0;
    static String [] topPlayers= new String [topTen];
    static int [] topScores= new int [topTen];
    
//----------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args)throws IOException{
        
        gameWindow = new JFrame("Cat Chaos Game");
        gameWindow.setSize(WIDTH, HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new GraphicsPanel();
        canvas.addMouseListener(mouseListener);
        canvas.addKeyListener(keyListener);
        gameWindow.add(canvas); 
        
        //Read high scores saved in text file
        readScores();

        //-------------------------Load images needed---------------------
        try {                
            logo = ImageIO.read(new File("Other Images/CatChaos_Logo.png"));
        } catch (IOException ex){} 
        try {                
            instructions = ImageIO.read(new File("Other Images/instructions.png"));
        } catch (IOException ex){} 
        try {                
            background1 = ImageIO.read(new File("Other Images/backyard_1.png"));
        } catch (IOException ex){} 
        try {                
            background2 = ImageIO.read(new File("Other Images/backyard_2.png"));
        } catch (IOException ex){} 
        try {                
            background3 = ImageIO.read(new File("Other Images/backyard_3.png"));
        } catch (IOException ex){}
        try {                
            coin = ImageIO.read(new File("Other Images/coin.png"));
        } catch (IOException ex){} 
        try {                
            puddle = ImageIO.read(new File("Other Images/waterPuddle.png"));
        } catch (IOException ex){} 
        try {                
            dog = ImageIO.read(new File("Other Images/dog.png"));
        } catch (IOException ex){} 
        try {                
            wood = ImageIO.read(new File("Other Images/wood.png"));
        } catch (IOException ex){} 
        try {                
            confetti = ImageIO.read(new File("Other Images/confetti.jpg"));
        } catch (IOException ex){} 
        
        //cat colours for cat choosing screen
        for (int i=0; i<4; i++){
             try {                
                colors[i] = ImageIO.read(new File("Cat Colors/Color" + Integer.toString(i)+ ".png"));
            } catch (IOException ex){} 
        }
        //------------------------------------------------------------------
        
        gameWindow.setVisible(true);
        runGameLoop();
        
    } // main method
//---------------------------------------------------------------------------------------------------------------------- 
    public static void runGameLoop()throws IOException{
        
        while (true) {
            gameWindow.repaint();
            try  {Thread.sleep(20);} catch(Exception e){}
            if (inPlay){
                moveBackround();
                moveLevel();
                moveCat();
                restrictMovement();
                collectCoins();
                detectCollison();
                detectPlatformGone();
                //move dogs in veritical direction evrey 3 rotations (slow)
                if (count==3){
                    moveDogsVertical();
                    count=0;
                }
                count++;
            }
        }
    } // runGameLoop method
//----------------------------------------------------------------------------------------------------------------------
    public static void moveBackround()throws IOException{
         
         //background1
         background1X = background1X - backgroundStep;
         if (background1X + background1W - backgroundStep < LEFT){
             background1X = background1W;
         }
        
         //background2
         background2X = background2X - backgroundStep;
         if (background2X + background1W - backgroundStep < LEFT){
             background2X = background2W;
         } 
         
         //background 3
         int platformGone = detectPlatformGone();
         int coinCollect=countCoins();
         //*only show and start moving when game has ended
         if (platformGone==numPlatforms){
             background3X = background3X - backgroundStep;
             //detect end of background
             if (background3X+background3W-backgroundStep<=WIDTH){
                 inPlay=false;
                 saveScores(coinCollect, username);
             }
         }
    } // moveBackround method
//----------------------------------------------------------------------------------------------------------------------
    public static void detectCollison(){
        
        //PUDDLES
        for (int p=0; p<numPuddles; p++){
             puddleBox = new Rectangle(puddleXValues[p]+40, puddleY+ 30, puddleW-95, puddleH-10);
             if (catBox.intersects(puddleBox)){
                  collided=true;
             }
        }
        //DOGS
         for (int d=0; d<numDogs; d++){
             dogBox = new Rectangle(dogXValues[d]+25, dogYValues[d]+ 30, dogW-55, dogH-55);
              if (catBox.intersects(dogBox)){
                   collided=true;
              }
         }  
    } //detectCollison method
//----------------------------------------------------------------------------------------------------------------------
    public static void moveCat(){
        
        //move cat in horizontal and vertical direction
        catX = catX + catVx;
        catVy = catVy + GRAVITY;
        catY = catY + catVy;
        catBox.setLocation(catX,catY);
        
        //restrict cat from moving past ground level 
        catY = Math.min(GROUND_LEVEL - catH, catY);
        if(catY == GROUND_LEVEL - catH){
           catVy = 0;
           changeState();
        }
    
        //Select cat's picture 
            if (catState == "sitting right"){
                catPicNum = 0;
            } else if (catState == "standing right"){
                catPicNum = 2;                              
            } else if (catState == "walking right"){
                catPicNum = nextRightPic[catPicNum]; 
            } else if (catState == "walking left"){                    
                catPicNum = nextLeftPic[catPicNum];  
            } else if (catState == "jumping right"){
                catPicNum = 4;
            } else if (catState == "sitting left"){
                catPicNum = 5; 
            } else if (catState == "standing left"){
                catPicNum = 7;
            } else if (catState == "jumping left"){
                catPicNum = 9;
            }            
    } // moveCat method
//----------------------------------------------------------------------------------------------------------------------
    public static void changeState(){
        
        //Changes state automatically based on position
        if (catState == "jumping left"){
            if (catVx == 0)
                catState = "standing left";
            else
                catState = "walking left";
            
        } else if (catState == "jumping right"){
            if (catVx == 0)
                catState = "standing right";
            else
                catState = "walking right";        
        }
    } // changeState method
//----------------------------------------------------------------------------------------------------------------------
    public static void generateLevel(){ 
        
        //declare variables needed
        final int START_PLATX=700;
        final int START_PLATY=400;
        final int PLATFORM_SPACE=130;
        final int PLATFORM_DISTANCE=150; 
        final int PUDDLE_DISTANCE=200;
        final int MAX_WIDTH=200;
        final int MIN_WIDTH=100;
        final int MAX_COIN=10300;
        final int MAX_PUDDLE=8700;
        final int MIN_VALUE=800;
        int randomY, yCoordinate, xCoordinate, randomWidth, randX;
        int randomGenerate, index, randomIndex, xSearch,  foundIndex;
        int coinYValue=0, coinXValue=0;
        int [] previousPuddles= new int [numPuddles];
        int [] previousIndexs= new int [numDogs];
        
        //~ ~ ~ ~ ~ GENERATE PLATFORMS ~ ~ ~ ~ ~
        for (int i=0; i<numPlatforms; i++){
            if(i == 0){
                //first platform (fixed)
                platformYValues[i]=START_PLATY;
                platformXValues[i]=START_PLATX;
            } else {
                //Y and X coordinates
                randomY=(int)((2*PLATFORM_SPACE+1)*Math.random()-PLATFORM_SPACE);
                yCoordinate= platformYValues[i-1]+randomY;
                
                //generate according to location of previous platform
                if (yCoordinate<TOP+catH+platformH){
                    yCoordinate= platformYValues[i-1]-randomY;
                    xCoordinate= platformXValues[i-1]+platformWidth[i-1]+PLATFORM_DISTANCE;
                }
                else if (yCoordinate+PLATFORM_SPACE<=GROUND_LEVEL){
                    xCoordinate= platformXValues[i-1]+platformWidth[i-1]+PLATFORM_DISTANCE;
                }
                else {
                    yCoordinate= platformYValues[i-1];
                    xCoordinate= platformXValues[i-1]+platformWidth[i-1]+(PLATFORM_DISTANCE+100);
                }
                platformYValues[i]=yCoordinate;
                platformXValues[i]=xCoordinate;
            }
            //platform width
            randomWidth= (int)((MAX_WIDTH+1)*Math.random()+MIN_WIDTH);    
            platformWidth[i]=randomWidth;
        } 
 
        //~ ~ ~ ~ ~ GENERATE COINS ~ ~ ~ ~ ~ 
        //show all generated coins
        Arrays.fill(coinDisplay, true);
        
        for (int c=0; c<numCoins; c++){
            randomGenerate=(int)(2*Math.random()+1);
            if (randomGenerate==1){
                //on platforms
                index=(int)((numPlatforms-1)*Math.random());
                coinXValue=(int)((platformWidth[index]-coinW+1)*Math.random()+platformXValues[index]);
                coinYValue= platformYValues[index]-coinH;
            }else{
                //on ground
                coinXValue=(int)(MAX_COIN*Math.random()+MIN_VALUE);
                coinYValue=GROUND_LEVEL-coinH;
            }
            coinsY[c]=coinYValue;
            coinsX[c]=coinXValue; 
        }
       
       //~ ~ ~ ~ ~ GENERATE PUDDLES ~ ~ ~ ~ ~ 
        for (int p=0; p<numPuddles; p++){
            Arrays.sort(previousPuddles);
            int randomX = 0;
            //if new puddle is the same as other puddle
            do{
                randomX=(int)(MAX_PUDDLE*Math.random()+MIN_VALUE);
                xSearch = 0;
                if( p!= 0 && randomX < (previousPuddles[p-1]+puddleW+PUDDLE_DISTANCE)){
                   xSearch = 1;
                }
            }while(xSearch>0);
            
            puddleXValues[p]=randomX;
            previousPuddles[p]=randomX;
        }
        Arrays.sort(puddleXValues);
       
       //~ ~ ~ ~ ~ GENERATE DOGS ~ ~ ~ ~ ~
        for (int d=0; d<numDogs; d++){
            Arrays.sort(previousIndexs);
            
            //generate a different platform for dog to spawn on
            do{
            randomIndex= (int)(numPlatforms*Math.random());
            foundIndex=Arrays.binarySearch(previousIndexs,randomIndex);
            }while(foundIndex>=0);

            previousIndexs[d]=randomIndex;
            platformLevel[d]=platformYValues[randomIndex];
            
            //x and y values
            randX=(int)((platformWidth[randomIndex]-dogW+1)*Math.random()+platformXValues[randomIndex]);
            dogXValues[d]=randX;
            dogYValues[d]=platformLevel[d]-dogH-10;
        }
    }// generateLevel method
//----------------------------------------------------------------------------------------------------------------------
    public static void moveLevel(){ 
        
        //platforms
        for (int i=0; i<numPlatforms; i++){
            platformXValues[i]=platformXValues[i]-backgroundStep; 
        }
        //coins
        for (int c=0; c<numCoins; c++){
            coinsX[c]=coinsX[c]-backgroundStep; 
        }
        //puddles
        for (int p=0; p<numPuddles; p++){
            puddleXValues[p]=puddleXValues[p]-backgroundStep;
            puddleBox = new Rectangle(puddleXValues[p]+40, puddleY+ 30, puddleW-95, puddleH-10);
        }
        //dogs
        for (int d=0; d<numDogs; d++){
            dogXValues[d]=dogXValues[d]-backgroundStep;
            dogBox = new Rectangle(dogXValues[d]+25, dogYValues[d]+ 30, dogW-55, dogH-55);
        }   
    }// moveLevel method
//----------------------------------------------------------------------------------------------------------------------
    public static void moveDogsVertical(){
        
        for (int d=0; d<numDogs; d++){
            //move dog in vertical direction (jump)
           if (dogYValues[d] +dogH+10 >= platformLevel[d]){
                dogVy=JUMP_SPEED2;
           }
           dogVy = dogVy + GRAVITY2;
           dogYValues[d] = dogYValues[d] + dogVy;
           dogBox = new Rectangle(dogXValues[d]+25, dogYValues[d]+ 30, dogW-55, dogH-55);
        }
    }// moveDogsVertical method
//----------------------------------------------------------------------------------------------------------------------
    public static void collectCoins(){
        
        //Detect when cat passes a coin
        for (int j=0; j<numCoins; j++){
            if (catX+catW>=coinsX[j] && catX<=coinsX[j]+coinW && catY+catH>=coinsY[j] && catY<=coinsY[j]+coinH){
                coinDisplay[j]=false;
            }
        }     
    }  // collectCoins method      
//----------------------------------------------------------------------------------------------------------------------
    public static int countCoins(){
        
        int coinCollect = 0;
        for (int j=0; j<numCoins; j++){
            if (coinDisplay[j]==false){
                coinCollect++;
            }
        }     
        return coinCollect;
    } //  countCoins method    
//----------------------------------------------------------------------------------------------------------------------
    public static void restrictMovement(){
        
        //Out of Screen
        if (catX+catVx<=LEFT){
            catState= "standing right";
            catVx = 0;
        }else if (catX+catVx>=RIGHT){
            catState= "standing left";
            catVx = 0;
        }else if (catY<=TOP){
            catVy= 0;
        }
        
        //Platforms
        for (int i=0; i<numPlatforms; i++){
            if (catX+catW>platformXValues[i] && catX<platformXValues[i]+platformWidth[i] && catY<=platformYValues[i] && catVy>0){
                //restrict cat from going past top of platform
                catY = Math.min(platformYValues[i] - catH,catY);
                if(catY == platformYValues[i] - catH){
                   //change the cat's state the same as the ground when on a platform
                   catVy = 0;
                   changeState();
                }
                break;
            }     
        }
    } // restrictMovement method
//----------------------------------------------------------------------------------------------------------------------
    public static int detectPlatformGone(){
        
        int platformGone = 0;
        for (int i=0; i<numPlatforms; i++){
            if (platformXValues[i] + platformWidth[i] < LEFT){
                platformGone++;
            } 
            else{
                //break loop from counting unnecessary platforms
                break;
            }
        }
        return platformGone;
    } // detectPlatformGone method
//----------------------------------------------------------------------------------------------------------------------
    public static void resetValues(){
        
        //put cat back into starting position
        catX=50;
        catY=GROUND_LEVEL;
        catVx = 0;
        catVy = 0;
        catState = "standing right";
        
        //other values that need to be resetted
        inPlay=false;        
        collided=false;
        background1X = 0;
        background2X = background1W;
        background3X = background1X+background1W;
    } // resetValues method
//----------------------------------------------------------------------------------------------------------------------
    public static void readScores()throws IOException{
        //declare variables
        int score;
        String name;
        
        //Read previous scores and names
        File fileIn = new File("highScores.txt");
        Scanner textIn = new Scanner(fileIn);
        
        for (int s=0; s<topTen; s++){
            if(textIn.hasNext()){   
                String playerLine = textIn.nextLine();
                name = playerLine.substring(0, playerLine.indexOf('\t')); 
                score=Integer.parseInt(playerLine.substring(playerLine.indexOf('\t')+1));
                //save scores and names in arrays
                topScores[s]=score;
                topPlayers[s]=name;
                //find smallest score in array
                if (topScores[s]>smallestScore){
                    smallestScore=topScores[s];
                }
           }
        }
        sortScores();    
        textIn.close();
    } // readScores method    
//----------------------------------------------------------------------------------------------------------------------
    public static void sortScores(){ //sort scores in reverse
        
        //Declare variables
        int s;
        
        //Compare each pair of adjacent items in score array
        for (int j = topScores.length; j >= 0; j--) {
            for (int i = 0; i < topScores.length - 1; i++) {
                s = i + 1;
                
                //Swap items in name and score arrays if they are in the wrong order
                if (topScores[i] < topScores[s]) {
                    int tempScore = topScores[i];
                    topScores[i] = topScores[s];
                    topScores[s] = tempScore;
                    
                    String tempName = topPlayers[i];
                    topPlayers[i] = topPlayers[s];
                    topPlayers[s] = tempName;
                }
            }
        }
    } // sortScores method
//----------------------------------------------------------------------------------------------------------------------
    public static void saveScores(int coinsCollect, String playerName) throws IOException{

        //Copy scores and name arrays with a longer length
        topScores = Arrays.copyOf(topScores, topTen+1);
        topPlayers = Arrays.copyOf(topPlayers, topTen+1);
        
        //Add new score in modified array
        topScores[topTen] = coinsCollect;
        topPlayers[topTen] = playerName;
        
        //Sort the scores from highest to lowest and reset arrays
        sortScores();
        topScores = Arrays.copyOf(topScores, topTen);
        topPlayers = Arrays.copyOf(topPlayers, topTen);
        
        //Write each high score and name in text file
        FileWriter scores = new FileWriter("highScores.txt");
        PrintWriter fileOut = new PrintWriter(scores); 
        
        for (int s=0; s<topTen; s++){
             fileOut.println(topPlayers[s]+"\t"+topScores[s]);
        }
        fileOut.close();
            
    }// saveScores method
//----------------------------------------------------------------------------------------------------------------------
    static class GraphicsPanel extends JPanel{
        public GraphicsPanel(){
            setFocusable(true);
            requestFocusInWindow();
        }
        public void paintComponent(Graphics g){ 
            super.paintComponent(g); 

            //--------------------------main menu-------------------------------
            if (screenNum==1){
                //background
                g.setColor (Color.white);
                g.fillRect(0,0,900,567);
                g.setColor (Color.black);
                g.fillRect(100, 0, 20, 567);
                g.fillRect(760, 0, 20, 567);
                //logo
                g.drawImage(logo,150,-20,this); 
                //buttons
                g.setColor (Color.gray);
                g.drawRect(playButtonX,playButtonY,playButtonW,playButtonH);
                g.drawRect(instructionX,instructionY,instructionW,instructionH);
                int smallSize= 27;
                Font smallFont= new Font("Copperplate Gothic Light", Font.BOLD, smallSize);
                g.setFont(smallFont); 
                g.drawString("PLAY", playButtonX+35, playButtonY+30);
                g.drawString("INSTRUCTIONS", instructionX+10, instructionY+30);
                g.setColor (Color.red);
                g.drawRect (scoreButtonX, scoreButtonY, scoreButtonW, scoreButtonH);
                int smallestSize= 24;
                Font smallestFont= new Font("Copperplate Gothic Light", Font.PLAIN, smallestSize);
                g.setFont(smallestFont);
                g.drawString("Leaderboard", scoreButtonX+12, scoreButtonY+30);
                
            //-------------------------------instructions------------------------------------
            }else if (screenNum==0){
                 g.drawImage(instructions,0,0,this); 
                 
            //--------------------------------leaderboard----------------------------------
            }else if (screenNum==2){
                //background
                g.setColor (Color.white);
                g.fillRect(0,0,900,567);
                g.setColor (Color.black);
                g.fillRect(100, 0, 20, 567);
                g.fillRect(760, 0, 20, 567);
                //title
                g.setColor (Color.red);
                int sizeLarge= 50;
                Font fontLarge= new Font("Copperplate Gothic Light", Font.BOLD, sizeLarge);
                g.setFont(fontLarge);
                g.drawString("Leaderboard", 280 , 70);
                //scores
                g.setColor (Color.black);
                int sizeSmall= 24;
                Font fontSmall= new Font("Copperplate Gothic Light", Font.PLAIN, sizeSmall);
                g.setFont(fontSmall);
                for (int s=0; s<topTen; s++){
                    if (topScores[s]!=0){
                        g.drawString(topPlayers[s]+"     --->     "+topScores[s]+ "  coins", 235, 120+ (s*40));
                    }
                }
                
           //----------------------------cat choosing screen--------------------------------
            }else if (screenNum==3){
                //background
                 g.setColor (Color.white);
                 g.fillRect(0,0,900,567);
                 //instruction
                 int size=35;
                 Font font= new Font("Copperplate Gothic Light", Font.BOLD, size);
                 g.setFont(font); 
                 g.setColor (Color.black);
                 g.drawString("Choose the Color of your Cat", 130, 280);
                 //all the cat colors
                 g.drawImage(colors[0],orangeX,orangeY,this);
                 g.drawImage(colors[1],yellowX,yellowY,this);
                 g.drawImage(colors[2],blackX,blackY,this);
                 g.drawImage(colors[3],redX, redY, this);
                 
            //----------------------------------playing screen-------------------------------    
            } else{
                //background images
                g.drawImage(background1,background1X,background1Y,this);
                g.drawImage(background2,background2X,background2Y,this);
                int platformGone = detectPlatformGone();
                if (platformGone==numPlatforms){
                    g.drawImage(background3,background3X,background3Y,this);
                }
                //current cat picture
                g.drawImage(catPic[catPicNum],catX,catY,this);
                //platforms
                Graphics2D g2d = (Graphics2D) g;
                g.setColor (Color.white);
                for (int i=0; i<numPlatforms; i++){
                    platformWood= new TexturePaint (wood,new Rectangle(platformXValues[i],platformYValues[i], 100, 20));
                    g2d.setPaint(platformWood);
                    g2d.fillRect(platformXValues[i],platformYValues[i],platformWidth[i],platformH);
                }
                //coins
                for (int c=0; c<numCoins; c++){
                    if (coinDisplay[c]==true){
                        g.drawImage(coin,coinsX[c],coinsY[c],this);
                    }       
                }
                //coin display board
                int size2=15;
                Font font2= new Font("Copperplate Gothic Light", Font.BOLD, size2);
                g.setFont(font2);
                g.setColor (Color.white);
                int coinCollect=countCoins();
                g.drawString("Coins: "+coinCollect, 790, 40);
                //pause/play button 
                g.setColor (Color.white);
                if (pause){
                    g.fillRect(pauseX,pauseY,5, pauseH);
                    g.fillRect(pauseX+10,pauseY,5, pauseH);
                } 
                else if (pause==false){
                    int [ ] xPoints = {pauseX,pauseX,pauseX+pauseW}; 
                    int [ ] yPoints = {pauseY, pauseY+pauseH, pauseY+(pauseH/2)}; 
                    g.fillPolygon (xPoints,yPoints, 3);
                }
                //puddles
                for (int p=0; p<numPuddles; p++){
                    g.drawImage(puddle,puddleXValues[p],puddleY,this);
                }
                //dogs
                for (int d=0; d<numDogs; d++){
                    g.drawImage(dog,dogXValues[d],dogYValues[d],this);
                }
                //collison
                if (collided){
                    inPlay=false;
                    g.setColor (Color.black);
                    g.fillRect(220,100,450,250);
                    g.setColor (Color.white);
                    g.fillRect(240,120,410,210);
                    int size3=40;
                    Font font3= new Font("Copperplate Gothic Light", Font.BOLD, size3);
                    g.setFont(font3);
                    g.setColor (Color.black);
                    g.drawString ("OOPS!", 380, 200);
                    g.drawString ("You Lost :(", 340, 250);
                    g.setFont(font2);
                    g.drawString ("(Press 'R' to try again)", 350, 300);
                }
                //Finished game
                if (inPlay==false && platformGone==numPlatforms){
                    g.drawImage(confetti,220,100,this); 
                    g.setColor (Color.black);
                    int size3=40;
                    Font font3= new Font("Copperplate Gothic Light", Font.BOLD, size3);
                    g.setFont(font3);
                    g.drawString ("YOU WON!!", 325, 260);
                    int size4=22;
                    Font font4= new Font("Copperplate Gothic Light", Font.PLAIN, size4);
                    g.setFont(font4);
                    g.drawString ("You collected "+coinCollect+ " coins!", 305, 300);
                    g.setFont(font2);
                    g.drawString ("(Press ESC to go back to the main menu)", 275, 360);
                }    
            }
        }
    } // paint method    
//----------------------------------------------------------------------------------------------------------------------   
    static class MyKeyListener implements KeyListener{   
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            
            //Exiting to main menu
            if (key == KeyEvent.VK_ESCAPE){
                if (screenNum==4){
                    resetValues();
                }
                screenNum=1;    
            }
            
            //Mario movements
            if (key == KeyEvent.VK_LEFT){
                if (catState == "standing left"){
                    catState = "walking left";
                    catVx = -RUN_SPEED-backgroundStep;
                } else if (catState == "standing right"){
                    catState = "standing left";
                } else if (catState == "walking left"){
                    // no action
                } else if (catState == "walking right"){
                    catState = "standing left";
                    catVx = 0;
                } else if (catState == "jumping left"){
                    catState="walking left";
                    catVx = -RUN_SPEED-backgroundStep;
                    catVy=0;
                } else if (catState == "jumping right"){
                    catState = "standing left";
                    catVx = 0;
                } else if (catState == "sitting left"){
                    catState = "walking left";
                    catVx = -RUN_SPEED-backgroundStep;
                } else if (catState == "sitting right"){
                    catState = "sitting left";
                }
                
            } else if (key == KeyEvent.VK_RIGHT){
                if (catState == "standing left"){
                    catState = "standing right";
                } else if (catState == "standing right"){
                    catState = "walking right";
                    catVx = RUN_SPEED;
                } else if (catState == "walking left"){
                    catState = "standing right";
                    catVx = 0;
                } else if (catState == "walking right"){
                    // no action
                } else if (catState == "jumping left"){
                    catState = "standing right";
                    catVx = 0;
                } else if (catState == "jumping right"){
                    catState = "walking right";
                    catVx = RUN_SPEED;
                    catVy=0;
                } else if (catState == "sitting left"){
                    catState = "sitting right";
                } else if (catState == "sitting right"){
                    catState = "walking right";
                    catVx = RUN_SPEED;
                }
            }
            if (key == KeyEvent.VK_UP){
                if (catState == "standing left"){
                    catState = "jumping left";
                    catVy = JUMP_SPEED;
                } else if (catState == "standing right"){
                    catState = "jumping right";
                    catVy = JUMP_SPEED;
                } else if (catState == "walking left"){
                    catState = "jumping left";
                    catVy = JUMP_SPEED;
                } else if (catState == "walking right"){
                    catState = "jumping right";
                    catVy = JUMP_SPEED;
                } else if (catState == "jumping left"){
                    // no action
                } else if (catState == "jumping right"){
                    // no action
                } else if (catState == "sitting left"){
                    catState = "standing left";
                } else if (catState == "sitting right"){
                    catState = "standing right";
                } 
                
            } else if (key == KeyEvent.VK_DOWN){
                if (catState == "standing left"){
                    catState = "sitting left";
                } else if (catState == "standing right"){
                    catState = "sitting right";
                } else if (catState == "walking left"){
                    catState = "sitting left";
                    catVx = 0;
                } else if (catState == "walking right"){
                    catState = "sitting right";
                    catVx = 0;
                } else if (catState == "jumping left"){
                     // no action
                } else if (catState == "jumping right"){
                     // no action
                } else if (catState == "sitting left"){
                    // no action
                } else if (catState == "sitting right"){
                    // no action
                } 
            }               
        }
        public void keyReleased(KeyEvent e){ 
            
        }   
        public void keyTyped(KeyEvent e){
            char keyChar = e.getKeyChar();
            if (keyChar == 'r'){
                if (screenNum==4 && collided==true){
                    resetValues();
                    screenNum=3;
                }
            }
        }           
    } // MyKeyListener class    
//---------------------------------------------------------------------------------------------------------------------- 
    static class MyMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e){
            int xValue= e.getX();
            int yValue= e.getY();
            
            //~ ~ ~ ~ ~ MAIN MENU ~ ~ ~ ~ ~
            if (screenNum==1){
                //Select play button
                if (xValue>=playButtonX && xValue<=playButtonX+playButtonW && yValue>=playButtonY && yValue<=playButtonY+playButtonH){
                    //Open an input dialog for username info
                    do{
                        username = JOptionPane.showInputDialog(gameWindow, "Enter your username", "Cat Chaos Game", JOptionPane.PLAIN_MESSAGE); 
                    }while (username==null || username.trim().length() == 0); 
                    screenNum=3;
                } 
                //Select instruction button
                else if (xValue>=instructionX && xValue<=instructionX+instructionW && yValue>=instructionY && yValue<=instructionY+instructionH){
                    screenNum=0;
                }
                //Select leaderboard button
                else if (xValue>=scoreButtonX && xValue<=scoreButtonX+scoreButtonW && yValue>=scoreButtonY && yValue<=scoreButtonY+scoreButtonH){
                    screenNum=2;
                }
            }
            
            //~ ~ ~ ~ ~ COLOR CHOOSING SCREEN ~ ~ ~ ~ ~ 
            else if (screenNum==3){
                if (xValue>=orangeX && xValue<=orangeX+colorW && yValue>=orangeY && yValue<=orangeY+colorH){
                    catColor= "Orange";
                } else if (xValue>=yellowX && xValue<=yellowX+colorW && yValue>=yellowY && yValue<=yellowY+colorH){
                    catColor= "Yellow";
                } else if (xValue>=blackX && xValue<=blackX+colorW && yValue>=blackY && yValue<=blackY+colorH){
                    catColor= "Black";
                }else if (xValue>=redX && xValue<=redX+colorW && yValue>=redY && yValue<=redY+colorH){
                    catColor= "Red";
                }else {
                    //do nothing
                }

                //load cat images from file 
                if (catColor!=""){
                    for (int i=0; i<10; i++){
                        try {                
                            catPic[i] = ImageIO.read(new File(catColor+ " Cats/Cat" + Integer.toString(i)+ ".png"));
                        }catch (IOException ex){} 
                    }
                    screenNum=4;
                    inPlay=true;
                    generateLevel();
                }
            }
            //~ ~ ~ ~ ~ PLAYING SCREEN ~ ~ ~ ~ ~
            else if (screenNum==4){
                //Choose pause button
                if (xValue>=pauseX && xValue<=pauseX+pauseW && yValue>=pauseY && yValue<=pauseY+pauseH && pause==true && inPlay==true){
                    inPlay=false;
                    pause=false;
                }
                //Choose play button
                else if (xValue>=pauseX && xValue<=pauseX+pauseW && yValue>=pauseY && yValue<=pauseY+pauseH && pause==false){
                    inPlay=true;
                    pause=true;
                }
            }
        }
        public void mousePressed(MouseEvent e){
        }
        public void mouseReleased(MouseEvent e){
        }
        public void mouseEntered(MouseEvent e){
        }
        public void mouseExited(MouseEvent e){
        }
    } // MyMouseListener class      
} // CatChaosGameFinal class