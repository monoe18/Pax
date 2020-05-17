package group7.common.map;

import java.util.ArrayList;
import java.util.Comparator;

public class Node implements Comparable<Node> {

    public int x, y;
    public Node parent = null;  // Nodes connecting to this node that offers shortest route
    public int heuristic;   // Euclidian distance from node     // Estimates cheapest path from this node to goal node
    public int costSoFar = 0;   // The accumulated path cost from start node to this node
    public int finalCost = 0;  // Heuristic + costSoFar
    public boolean solution; // If Node is part of the solution path
    public String direction; // new  --- Used to move the AI
    public boolean isStart = true;

    public int getNode;

    public boolean isVisited; // Have we searched this node before?
    public ArrayList<Node> neighbours = new ArrayList<Node>();

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int compareTo(Node node) {
        return this.finalCost - node.finalCost;
    }

}
