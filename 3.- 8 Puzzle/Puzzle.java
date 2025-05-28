package puzzle;

import java.util.*;

public class Puzzle {

    public int dimension = 3;

    // Movimientos posibles: Abajo, Izquierda, Arriba, Derecha
    int[] fila = {1, 0, -1, 0};
    int[] columna = {0, -1, 0, 1};

    // Implementación del algoritmo DFS
    public void depthFirstSearch(int[][] inicial, int[][] objetivo, int x, int y) {
        Stack<Nodo> stack = new Stack<>();
        Set<String> visitados = new HashSet<>();
        Nodo raiz = new Nodo(inicial, x, y, x, y, 0, null);
        stack.push(raiz);
        visitados.add(Arrays.deepToString(inicial));

        while (!stack.isEmpty()) {
            Nodo nodo = stack.pop();
            if (calcularCosto(nodo.matriz, objetivo) == 0) {
                imprimirCamino(nodo);
                System.out.println("Solución encontrada en " + nodo.nivel + " movimientos!");
                return;
            }
            for (int i = 0; i < 4; i++) { 
                int nuevoX = nodo.x + fila[i];
                int nuevoY = nodo.y + columna[i];
                if (esSeguro(nuevoX, nuevoY)) {
                    Nodo hijo = new Nodo(nodo.matriz, nodo.x, nodo.y, nuevoX, nuevoY, nodo.nivel + 1, nodo);
                    String estado = Arrays.deepToString(hijo.matriz);
                    if (!visitados.contains(estado)) {
                        stack.push(hijo);
                        visitados.add(estado);
                    }
                }
            }
        }
        System.out.println("No se encontró una solución.");
    }

    // Implementación del algoritmo BFS
    public void breadthFirstSearch(int[][] inicial, int[][] objetivo, int x, int y) {
        Queue<Nodo> queue = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        Nodo raiz = new Nodo(inicial, x, y, x, y, 0, null);
        queue.add(raiz);
        visitados.add(Arrays.deepToString(inicial));

        // Mientras la cola no esté vacía
        while (!queue.isEmpty()) {
            // Sacar el primer nodo de la cola
            Nodo nodo = queue.poll();
            // Si el costo del nodo actual es 0, se ha encontrado la solución
            if (calcularCosto(nodo.matriz, objetivo) == 0) {
            // Imprimir el camino desde el nodo raíz hasta el nodo actual
            imprimirCamino(nodo);
            System.out.println("Solución encontrada en " + nodo.nivel + " movimientos!");
            return;
            }
            // Explorar los 4 movimientos posibles (Abajo, Izquierda, Arriba, Derecha)
            for (int i = 0; i < 4; i++) {
            int nuevoX = nodo.x + fila[i];
            int nuevoY = nodo.y + columna[i];
            // Verificar si la nueva posición es segura (dentro de los límites del tablero)
            if (esSeguro(nuevoX, nuevoY)) {
                // Crear un nuevo nodo hijo con la nueva posición
                Nodo hijo = new Nodo(nodo.matriz, nodo.x, nodo.y, nuevoX, nuevoY, nodo.nivel + 1, nodo);
                String estado = Arrays.deepToString(hijo.matriz);
                // Si el estado del hijo no ha sido visitado, agregarlo a la cola y marcarlo como visitado
                if (!visitados.contains(estado)) {
                queue.add(hijo);
                visitados.add(estado);
                }
            }
            }
        }
        System.out.println("No se encontró una solución.");
    }

    // Implementación del algoritmo de Costo Uniforme
    public void uniformCostSearch(int[][] inicial, int[][] objetivo, int x, int y) {
        PriorityQueue<Nodo> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.nivel));
        Set<String> visitados = new HashSet<>();
        Nodo raiz = new Nodo(inicial, x, y, x, y, 0, null);
        pq.add(raiz);
        visitados.add(Arrays.deepToString(inicial));

        // Mientras la cola de prioridad no esté vacía
        while (!pq.isEmpty()) {
            // Sacar el nodo con el menor costo (nivel) de la cola de prioridad
            Nodo nodo = pq.poll();
            // Si el costo del nodo actual es 0, se ha encontrado la solución
            if (calcularCosto(nodo.matriz, objetivo) == 0) {
            // Imprimir el camino desde el nodo raíz hasta el nodo actual
            imprimirCamino(nodo);
            System.out.println("Solución encontrada en " + nodo.nivel + " movimientos!");
            return;
            }
            // Explorar los 4 movimientos posibles (Abajo, Izquierda, Arriba, Derecha)
            for (int i = 0; i < 4; i++) {
            int nuevoX = nodo.x + fila[i];
            int nuevoY = nodo.y + columna[i];
            // Verificar si la nueva posición es segura (dentro de los límites del tablero)
            if (esSeguro(nuevoX, nuevoY)) {
                // Crear un nuevo nodo hijo con la nueva posición
                Nodo hijo = new Nodo(nodo.matriz, nodo.x, nodo.y, nuevoX, nuevoY, nodo.nivel + 1, nodo);
                String estado = Arrays.deepToString(hijo.matriz);
                // Si el estado del hijo no ha sido visitado, agregarlo a la cola de prioridad y marcarlo como visitado
                if (!visitados.contains(estado)) {
                pq.add(hijo);
                visitados.add(estado);
                }
            }
            }
        }
        System.out.println("No se encontró una solución.");
    }

    public boolean esSeguro(int x, int y) {
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }

    public int calcularCosto(int[][] inicial, int[][] objetivo) {
        int contador = 0;
        for (int i = 0; i < inicial.length; i++) {
            for (int j = 0; j < inicial.length; j++) {
                if (inicial[i][j] != 0 && inicial[i][j] != objetivo[i][j]) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public void imprimirCamino(Nodo raiz) {
        if (raiz == null) {
            return;
        }
        imprimirCamino(raiz.padre);
        imprimirMatriz(raiz.matriz);
        System.out.println();
    }

    public void imprimirMatriz(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int valor : fila) {
                System.out.print(valor + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] inicial = {
            {1, 8, 2},
            {0, 4, 3},
            {7, 6, 5}
        };
        int[][] objetivo = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        int x = 1, y = 0;
        
        Puzzle puzzle = new Puzzle();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Seleccione el algoritmo de búsqueda:");
        System.out.println("1. DFS");
        System.out.println("2. BFS");
        System.out.println("3. Costo Uniforme");
        System.out.print("Opción: ");
        int opcion = scanner.nextInt();
        
        switch (opcion) {
            case 1 -> puzzle.depthFirstSearch(inicial, objetivo, x, y);
            case 2 -> puzzle.breadthFirstSearch(inicial, objetivo, x, y);
            case 3 -> puzzle.uniformCostSearch(inicial, objetivo, x, y);
            default -> System.out.println("Opción no válida");
        }
    }
}