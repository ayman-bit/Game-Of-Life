import java.awt.Color;
import java.util.Objects;

/**
  * This code is written by Ayman Abu Awad (Student #: 201808672)
  * Nature Inspired Computing
  */
public class Sim247 {	
	
  /**
	* Initializing variables
	*/
	private int number;
	private int size;
	private int magnification = 5;
	private Picture picture;
	static Color white = new Color(255,255,255);
    static Color black = new Color(0,0,0);
	static int current [][];
    
	public Sim247(int number, int size) 
	{
		this.number = number;
		this.size = size;
		current = new int [size * magnification][size * magnification];
		picture = new Picture(size * magnification, size * magnification);
	};
	
   /**
     * Initializing configurations for the Mazing Oscillator and Turtle glider.
	 * MOP = Mazing Oscillator Pattern
	 * TGP = Turtle glider Pattern
     */
	static int[][] MOP = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 1, 1, 0, 0, 0},
			{ 0, 0, 1, 0, 1, 0, 0, 0, 0},
			{ 0, 1, 0, 0, 0, 0, 0, 1, 0},
			{ 0, 0, 1, 0, 0, 0, 1, 1, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 1, 0, 1, 0, 0},
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0}};

	static int[][] TGP = { 
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,1,1,0,0,0,0,0,0,0,1,0},
			{0,0,1,1,0,0,1,0,1,1,0,1,1,0},
			{0,0,0,0,1,1,1,0,0,0,0,1,0,0},
			{0,0,1,0,0,1,0,1,0,0,0,1,0,0},
			{0,1,0,0,0,0,1,0,0,0,0,1,0,0},
			{0,1,0,0,0,0,1,0,0,0,0,1,0,0},
			{0,0,1,0,0,1,0,1,0,0,0,1,0,0},
			{0,0,0,0,1,1,1,0,0,0,0,1,0,0},
			{0,0,1,1,0,0,1,0,1,1,0,1,1,0},
			{0,0,1,1,1,0,0,0,0,0,0,0,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
	
	// fill a current with a random color (Taken from the PictureDemo.java class)
	private void drawCell(int i, int j, Color color)
	{
        for (int offsetX = 0; offsetX < magnification; offsetX++)
        {
            for (int offsetY = 0; offsetY < magnification; offsetY++)
            {
                // set() colors an individual pixel
                picture.set((i*magnification)+offsetX,
                        (j*magnification)+offsetY, color);
            }
        }
    }
	
   /**
     * Displays the picture in a window on the screen, 
	 * using the PictureDemo.java class.
     */
    public void display()
    {
        picture.show();
    }
    
   /**
     * Initialize Layouts grid behaviours based on user input,
	 * M = Mazing Osciallator Pattern
	 * T = Turtle Glider Pattern
	 * R = Random with 50% chance of each cell starting alive or dead
     */
	void Grid (String type) {
		if (Objects.equals(type, "M")) {
	        for (int y = 0; y < MOP.length; y++) {
	            for (int x = 0; x < MOP[0].length; x++) {
	                current[y][x] = MOP[y][x];
	            }
	        }
	        
		}
			
		if (Objects.equals(type, "T")) {
				
				for (int y = 0; y < TGP.length; y++) {
		            for (int x = 0; x < TGP[0].length; x++) {
		                current[y][x] = TGP[y][x];
		            }
		        }
		}
		
		if (Objects.equals(type, "R")) {
			for (int i = 0; i < current.length; i++) {
				for (int k = 0; k < current.length; k++) {
					int x = (int) (Math.random()*100);
					if (x % 2 == 0)
					{current[i][k] = 0;}
					else
					{current[i][k] = 1;}
				}       
		    }
		}
	}
    
   /**
     * This method create the cells with black or white colour pattern
     */
	void draw (int current[][]) {
		for (int i = 0; i < current.length; i++) {
			for (int k = 0; k < current.length; k++) {
				if (current[i][k] == 1){
					drawCell(k,i,black);
				}
				else {
					drawCell(k,i,white);
				}
			}
		}
		display();
	}
   /**
     * This method checking the GOL rules and neighbors and 
	 * updates the current array
     */
	void update(int size)
    {
		int[][] next = new int[size][size];
  
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                
                int aliveCell = 0;
                
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                    	aliveCell += current[(i+y+size)%size][(j+x+size)%size];
                    }
                }
                
                aliveCell -= current[y][x];

				/** ========= Rules of Game Of Life =========
				 * If the cell is alive, then it stays alive if it has either 2 or 3 live neighbors
				 * If the cell is dead, then it springs to life only in the case that it has 3 live neighbors
				 */


                if ((current[y][x] == 1) && (aliveCell < 2)) {
                    next[y][x] = 0;
                }

                else if ((current[y][x] == 1) && (aliveCell > 3)) {
                    next[y][x] = 0;
                }

                else if ((current[y][x] == 0) && (aliveCell == 3)) {
                    next[y][x] = 1;
                }

                else
                    next[y][x] = current[y][x];
                
          }
        }
        current = next; 
       }
    
    
	public static void main(String[] args) {

		int number = Integer.parseInt(args[0]);
		String type = args[1];
		int size = 100;

		Sim247 gameOfLife = new Sim247(number, size);
		
		gameOfLife.Grid(type);
		
		try {
				for (int e = 0; e < number; e++) {
					gameOfLife.update(size);
					gameOfLife.draw(current);
					Thread.sleep(250);
				}
			
		} 
		catch (InterruptedException error) {
			error.printStackTrace();
		}

	}
	
}