package puzzle;

public class Nodo {

	public Nodo padre;
	public int[][] matriz;
	
	public int x, y;
	public int costo;
	public int nivel;
	
	public Nodo(int[][] matrix, int x, int y, int newX, int newY, int nivel, Nodo padre) {
		this.padre = padre;
		this.matriz = new int[matrix.length][];
		for (int i = 0; i < matrix.length; i++) {
			this.matriz[i] = matrix[i].clone();
		}
		
		// Swap value
		this.matriz[x][y]       = this.matriz[x][y] + this.matriz[newX][newY];
		this.matriz[newX][newY] = this.matriz[x][y] - this.matriz[newX][newY];
		this.matriz[x][y]       = this.matriz[x][y] - this.matriz[newX][newY];
		
		this.costo = Integer.MAX_VALUE;
		this.nivel = nivel;
		this.x = newX;
		this.y = newY;
	}
	
}