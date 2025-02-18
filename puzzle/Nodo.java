package puzzle;

public class Nodo {

	public Nodo padre;
	public int[][] matriz;
	
	// Blank tile cordinates
	public int x, y;
	
	// Number of misplaced tiles
	public int costo;
	
	// The number of moves so far
	public int nivel;
	
	public Nodo(int[][] matrix, int x, int y, int newX, int newY, int level, Nodo parent) {
		this.padre = parent;
		this.matriz = new int[matrix.length][];
		for (int i = 0; i < matrix.length; i++) {
			this.matriz[i] = matrix[i].clone();
		}
		
		// Swap value
		this.matriz[x][y]       = this.matriz[x][y] + this.matriz[newX][newY];
		this.matriz[newX][newY] = this.matriz[x][y] - this.matriz[newX][newY];
		this.matriz[x][y]       = this.matriz[x][y] - this.matriz[newX][newY];
		
		this.costo = Integer.MAX_VALUE;
		this.nivel = level;
		this.x = newX;
		this.y = newY;
	}
	
}