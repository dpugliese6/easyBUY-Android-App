package com.example.easybuy;
class Node {

    protected String name;

    //Distance of the node from the node in the key of HashMap.
    protected double sld = 0;
    public Node(String name, double sld){
        this.name = name;
        this.sld = sld;
    }

}