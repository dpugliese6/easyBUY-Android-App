package com.example.easybuy;
import java.util.List;
import java.util.PriorityQueue;

class Astar {

    PriorityQueue<NodeInfo> frontier = new PriorityQueue<NodeInfo>(1,new Compare() {
        @Override
        public int compare(NodeInfo o1, NodeInfo o2) {
            return Double.valueOf(o1.f).compareTo(o2.f);
        }
    });


    Node child;
    List<Node> childList;
    NodeInfo childData, endTour;
    NodeInfo node;
    Double gcost, hcost;

    /*
     * heuristicCost takes in a string value child, and finds its straight line
     * distance from the goal node
     *
     * @param child : a variable of type String which represents a child name.
     * @return: returns the straight line distance of child from the goal node
     */
    private double heuristicCost(String child) {

        double h = 0.0;
        for (Node c : tsp.graph.get(tsp.initialState)) {
            if (c.name.equals(child)) {
                h = c.sld;
                break;
            }
        }
        return h;
    }

    private void addChildren(List<Node> children, boolean last) {
        int i = 0;

        if (last == false) {
            while (i < children.size()) {
                child = children.get(i);

                if (!(node.path.contains(child.name))) {

                    gcost = child.sld + node.g;

                    hcost = heuristicCost(child.name);

                    /*
                     * getting the ancestorList of parent and putting it in the
                     * child
                     */

                    childData = new NodeInfo(child.name, node.path, gcost,hcost);
                    childData.path.add(node.nodeName);
                    childData.f = gcost + hcost;
                    frontier.add(childData);
                }
                i++;
            }
        } else {

            int j = 0;
            while (j < children.size()) {
                double gcost = 0.0;
                child = children.get(j);
                if (child.name.equals(tsp.initialState)) {
                    gcost = child.sld + node.g;
                    endTour = new NodeInfo(child.name, node.path, gcost);
                    endTour.path.add(node.nodeName);
                    endTour.path.add(child.name);
                    endTour.f = endTour.g + endTour.h;
                    break;
                }
                j++;
            }
        }
    }

    public void search() {

        node = new NodeInfo(tsp.initialState);

        frontier.add(node);

        while (true) {

            if (frontier.peek() == null) {
                return;
            }

            //Removing the front of the queue frontier

            node = frontier.remove();

            if (node.path.size() == (tsp.numNodes - 1)) {

                childList = tsp.graph.get(node.nodeName);
                addChildren(childList, true);

                //Writing the optimal path
                tsp.output(endTour);
                return;
            }
            childList = tsp.graph.get(node.nodeName);
            addChildren(childList, false);
        }
    }

}