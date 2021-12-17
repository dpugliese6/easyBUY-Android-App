package com.example.easybuy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class tsp {
    protected static int numNodes;
    protected static String initialState="a";
    protected static HashMap<String, List<Node>> graph;
    protected static ArrayList<String> outputPath;
    public ArrayList<Point> points= new ArrayList<Point>();
    private static List<Node> value;








    public void tsp(){}

    public static void createMap(List<Point> list) {
        graph = new HashMap<String, List<Node>>();

        double sdistance = 0;

        for (Point p : list) {

            for (Point q : list) {

                //repeat for each point in the graph except for itself.
                if (!p.lettera.equals(q.lettera)) {

                    //calculating straight line distance between the points
                    sdistance = Math.sqrt(Math.pow(p.x - q.x, 2.0) + Math.pow(p.y - q.y, 2.0));

                    //creating a node which is adjacent to current node p
                    Node n = new Node(q.lettera, sdistance);

                    value = com.example.easybuy.tsp.graph.get(p.lettera);

                    //checking if key has already been created in the hashMap or not
                    if (value == null) {
                        value = new ArrayList<Node>();
                        value.add(n);
                        com.example.easybuy.tsp.graph.put(p.lettera, value);
                    } else {
                        value.add(n);
                    }
                }
            }
        }
    }

    protected static void output(NodeInfo endTour) {
        outputPath= new ArrayList<>();
        for (String s : endTour.path) {
            outputPath.add(s);
        }

    }

    protected void setNumNodes(int i){
        this.numNodes=i;
    }

    protected void setPoints(ArrayList<Point> po){
        this.points=po;
    }

    public ArrayList<String> getOutputPath(){
        return outputPath;
    }


}
