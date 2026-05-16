package GUI;
// import java.lang.classfile.Label;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;



public class DeliveryRouteGUI  extends Application{
    static int minDistance(int[ ] dist,boolean[] visited, int total){
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

    static int[] Dijkstra(int[][] graph, int source, int dest, int total){
        int dist[] = new int[total];
        boolean[] visited = new boolean[total];
        int parent[] = new int[total];

        for(int i = 0; i < total; i++){
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
            parent[i] = -1;
            }

        dist[source] = 0;

    for(int count = 0; count < total; count++){
        int u = minDistance(dist, visited, total);
        visited[u] = true;

        for(int v = 0; v < total; v++){
            if(!visited[v] && graph[u][v] != 0
                && dist[u] != Integer.MAX_VALUE
                && dist[u] + graph[u][v] < dist[v]){
                dist[v] = dist[u] + graph[u][v];
                parent[v] = u;
            }
        }
        }

            return dist;
    }



    public void start(Stage stage){
        String[] locations = {"WareHouse","Area A","Area B","Area C","Area D","Customer"};
        int[][] graph = {
            {0,4,0,2,0,0},
            {4,0,3,0,6,0},
            {0,3,0,0,0,5},
            {2,0,0,0,0,3},
            {0,6,0,0,0,2},
            {0,0,5,3,2,0}
        };

    javafx.scene.control.Label title = new javafx.scene.control.Label("Delivery Route Planner");
    javafx.scene.control.Label algoLabel = new javafx.scene.control.Label("Graph (Dijkstra's Algorithm)");
    algoLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
    javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(420, 370);
    javafx.scene.canvas.GraphicsContext gc = canvas.getGraphicsContext2D();


    double[][] pos = {
    {200, 40}, // WareHouse
    {80,130}, // Area A
    {80,250},// Area B
    {320,130},// Area C
    {320,250},// Area D
    {200,330} // Custome
};




    gc.setStroke(javafx.scene.paint.Color.BLACK);
    for (int i = 0; i < 6; i++)
        for (int j = i+1; j < 6; j++)
            if (graph[i][j] != 0) {
                gc.strokeLine(pos[i][0], pos[i][1], pos[j][0], pos[j][1]);
                double mx = (pos[i][0]+pos[j][0])/2;
                double my = (pos[i][1]+pos[j][1])/2;
                gc.strokeText(graph[i][j]+"km", mx, my);
            }

   
        for (int i = 0; i < 6; i++) {
            gc.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            gc.fillOval(pos[i][0]-15, pos[i][1]-15, 30, 30);
            gc.setStroke(javafx.scene.paint.Color.DARKBLUE);
            gc.strokeOval(pos[i][0]-15, pos[i][1]-15, 30, 30);
            gc.setFill(javafx.scene.paint.Color.BLACK);
            gc.fillText(locations[i], pos[i][0]-20, pos[i][1]+30);
        }
    javafx.scene.control.Label srcLabel = new javafx.scene.control.Label("Select Source:");
    javafx.scene.control.ComboBox<String> sourceBox = new javafx.scene.control.ComboBox<>();
    sourceBox.getItems().addAll("WareHouse","Area A","Area B","Area C","Area D","Customer");
    sourceBox.setValue("WareHouse");

    javafx.scene.control.Label destLabel = new javafx.scene.control.Label("Select Destination:");
    javafx.scene.control.ComboBox<String> destBox = new javafx.scene.control.ComboBox<>();
    destBox.getItems().addAll("WareHouse","Area A","Area B","Area C","Area D","Customer");
    destBox.setValue("Customer");

    javafx.scene.control.Button findBtn = new javafx.scene.control.Button("Find Shortest Route");

    javafx.scene.control.TextArea resultArea = new javafx.scene.control.TextArea();
    resultArea.setEditable(false);
    resultArea.setPrefHeight(150);

    findBtn.setOnAction(e -> {
    String src = sourceBox.getValue();
    String dest = destBox.getValue();

    if (src.equals(dest)) {
        resultArea.setText("Source and destination cannot be the same!");
        return;
    }

    

    int srcIndex = 0, destIndex = 0;
    for (int i = 0; i < locations.length; i++) {
        if (locations[i].equals(src)) srcIndex = i;
        if (locations[i].equals(dest)) destIndex = i;
    }

    
    int[] result = Dijkstra(graph, srcIndex, destIndex, locations.length);

        resultArea.setText(
        "From     : " + src + "\n" +
        "To       : " + dest + "\n" +
        "Distance : " + result[destIndex] + " km"
            );
        });

    VBox layout = new VBox(10);
        layout.widthProperty().addListener((obs, oldVal, newVal) -> {
        canvas.setWidth(newVal.doubleValue());
            });
        layout.setPadding(new javafx.geometry.Insets(10));

        layout.getChildren().addAll(title, algoLabel, canvas, srcLabel, sourceBox, destLabel, destBox, 
        findBtn, resultArea);

    Scene scene = new Scene(layout, 450,780);
    stage.setTitle("Delivery Route Planner");
    stage.setScene(scene);
    stage.show();
}
    public static void main(String args[]){
        launch(args);
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

                        Dijkstra(graph, 0, 5, locations.length);
    }
}