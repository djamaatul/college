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

        public void DFS(int start, int find) {
            boolean[] visited = new boolean[size];
            DFSUtil(start, visited, find);
        }

        private void DFSUtil(int row, boolean[] visited, int find) {
            visited[row] = true;

            if (row == find) {
                System.out.println("FOUND: " + row);
                System.exit(0);
                return;
            }

            System.out.println(row);

            for (int child : nodes[row]) {
                if (!visited[child]) {
                    DFSUtil(child, visited, find);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph dfs = new Graph(7);
        dfs.addEdge(0, 1);
        dfs.addEdge(0, 2);
        dfs.addEdge(1, 3);
        dfs.addEdge(1, 4);
        dfs.addEdge(2, 5);
        dfs.addEdge(2, 6);

        dfs.DFS(0, 4);
    }
}
