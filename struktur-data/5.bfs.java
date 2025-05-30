import java.util.LinkedList;

class pratikum3 {

    static class Graph {

        int size;
        LinkedList<Integer>[] nodes;

        Graph(int size) {
            this.size = size;
            this.nodes = new LinkedList[size];
            for (int i = 0; i < size; i++) {
                this.nodes[i] = new LinkedList<Integer>();
            }
        }

        public void addEdge(int row, int node) {
            this.nodes[row].add(node);
        }

        public void BFS(int start, int find) {
            boolean[] visited = new boolean[size];
            LinkedList<Integer> queue = new LinkedList<>();

            visited[start] = true;
            queue.add(start);

            while (!queue.isEmpty()) {
                int current = queue.poll();
                
                if (current == find) {
                    System.out.println("FOUND: " + current);
                    System.exit(0);
                    return;
                }

                System.out.println(current);

                for (int child : nodes[current]) {
                    if (!visited[child]) {
                        visited[start] = true;
                        queue.add(child);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph bfs = new Graph(7);
        bfs.addEdge(0, 1);
        bfs.addEdge(0, 2);
        bfs.addEdge(1, 3);
        bfs.addEdge(1, 4);
        bfs.addEdge(2, 5);
        bfs.addEdge(2, 6);
        
        bfs.BFS(0, 1);
    }
}
