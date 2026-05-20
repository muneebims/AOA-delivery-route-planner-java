public class DeliveryRoutePLanner{
    static int minDistance(int[] dist,boolean[] visited, int total){
        int min =Integer.MAX_VALUE;
        int minindex=-1;
        for(int i=0;i<total;i++){
            if(!visited[i]&& dist[i]<=min){
                min =dist[i];
                minindex=i;
            }
        }
        
        return minindex;

    }

    static String buildPath(int []parent,int dest,String[] locations){
        if(parent[dest]==-1) return "no path found";
        String path="";
        int current=dest;
        while(current!=-1){
            path=locations[current]+(path.equals("") ? "" : " ->" + path);
            current = parent[current];
        }
            return path;
    }

            
    static void Dijkstra(int [][] graph,int source,String[] locations){
        int total=locations.length;
        int dist[]=new int[total];
        boolean[] visited=new boolean[total];
        int parent[]=new int[total];
        for(int i=0;i<total;i++){
            dist[i]=Integer.MAX_VALUE;
            visited[i]=false;
            parent[i]=-1;
        }
        dist [source]=0;    
        for(int count=0;count<total;count++){
            int u=minDistance(dist, visited, total);
            visited[u]=true;
            for(int v=0;v<total;v++){
                if(!visited[v] && graph[u][v]!=0
                    && dist[u] != Integer.MAX_VALUE
                    && dist[u]+graph[u][v]< dist[v]
                ){
                    dist[v]=dist[u]+graph[u][v];
                    parent[v]=u;
                }
            }
        }
        System.out.println("\nShortest distance from: "+locations[source]);
        System.out.println("");
        for (int i=0;i<total;i++){
            if(i != source){
                System.out.println(locations[source] + " to " + locations[i] + " = " + dist[i]
                                   + " km  | Path: " + buildPath(parent, i, locations)
                                  );
                
            }
        }
    }


    public static void main(String args[]){
        System.out.println("Deliveruy Route Planner");
        String [] locations={
            "WareHouse","Area A","Area B","Area C"
            ,"Area D","Customer"
        };

        int [][] graph={
            {0,4,0,2,0,0},
            {4,0,3,0,6,0},
            {0,3,0,0,0,5},
            {2,0,0,0,0,3},
            {0,6,0,0,0,2},
            {0,0,5,3,2,0}
        };
        System.out.println("Map loaded with "+locations.length+" locattions");
        Dijkstra(graph, 0, locations);
    }
}
