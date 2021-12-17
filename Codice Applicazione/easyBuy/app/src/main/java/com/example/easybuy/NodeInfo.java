package com.example.easybuy;
import java.util.ArrayList;

/*
 * NodeInfo class creates an object for each node, 
 * Storing the node name, pathCost to reach the node, and an arrayList of its ancestors
 */
public class NodeInfo {
    String nodeName;

    //Path is the path ancestor List  from source node
    ArrayList<String> path = new ArrayList<String>();

    // g is the pathcost from initial node to the current node
    double g;

    /*h is the heuristics cost which in case of A* could be straight line distance or minimum spanning tree cost
     *  But in case of greedy search heuristics cost is zero.
     */
    double h;

    double f;

    String parentName;

    /*
     * This constructor is invoked when the heuristic cost is not used
     * @param nodeName name of the node
     * @param path is the ancestor list of the node
     * @param g is the path cost of the node
     */
    public NodeInfo(String nodeName, ArrayList<String>path, double g){
        this.nodeName = nodeName;
        this.g = g;
        for(String s:path){
            this.path.add(s);
        }
        this.h = 0.0;
    }

    /*
     * This constructor is invoked when the heuristic cost is specified
     * @param h : heuristic cost
     */
    public NodeInfo(String nodeName, ArrayList<String>path, double g, double h){

        this.nodeName = nodeName;
        this.g = g;
        for(String s:path){
            this.path.add(s);
        }
        this.h = h;
    }

    /*
     * This constructor is used to create the initialState of the problem
     * @param nodeName
     */
    public NodeInfo(String nodeName){
        this.nodeName = nodeName;
        this.g = 0.0;
        this.h = 0.0;
    }

    /*
     * This constructor is specifically used for creating NodeInfo objects,
     * which will be used in Minimum Spanning Tree
     * @param nodeName
     * @param g: straight line distance of node from the parent
     */
    public NodeInfo(String nodeName, double g){
        this.nodeName = nodeName;
        this.g = g;
        this.h = 0.0;
        this.f= 0.0;
    }

}