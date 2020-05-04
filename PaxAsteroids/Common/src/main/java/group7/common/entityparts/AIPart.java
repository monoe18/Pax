package group7.common.entityparts;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import java.util.ArrayList;
import group7.common.map.Node;
import java.util.Collections;

public class AIPart implements EntityPart {

    ArrayList<Node> solution = new ArrayList();
    int counter;
    int update = 50;

    public static final int diagonalCost = 14;
    public static final int stepCost = 10;

    private Node[][] grid;
    private ArrayList<Node> openList;
    private Node start;
    private Node goal;
    private ArrayList<Node> closedList;
    private int gridFactorX = 45; //grid offset num x
    private int gridFactorY = 25; //grid offset num y
    private boolean closedCells[][];

    // new
    private int gridWidth, gridHeight = 32;

    // Constructor - Created once.
    public AIPart(int gridWidth, int gridHeight) {
        this.grid = new Node[gridWidth][gridHeight];

    }

    // It gives each tile in grid a heuristic
    // Called each fifth second to recalclulate where the ai should go
    public void newGridSetup(PositionPart player, PositionPart enemy) {
        this.goal = null;
        this.start = null;
        getNewPositions(player, enemy);
        this.closedCells = new boolean[32][32];

        System.out.println("grid length " + grid.length);
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y] = new Node(x, y);
                grid[x][y].heuristic = (int) Math.sqrt(Math.pow(x - goal.x, 2) + Math.pow(y - goal.y, 2));
                grid[x][y].solution = false;
            }
        }

        start.finalCost = 0;

    }

    public void getNewPositions(PositionPart player, PositionPart enemy) {

        this.goal = new Node((int) player.getX() / this.gridFactorX, (int) player.getY() / this.gridFactorY);
        this.start = new Node((int) enemy.getX() / this.gridFactorX, (int) enemy.getY() / this.gridFactorY);

    }

    public void updateCostIfNeeded(Node currentNode, Node temporaryNode, int cost) {

        // not sure what this does maybe for non existance ones?
        if (temporaryNode == null || closedCells[temporaryNode.x][temporaryNode.y]) {
            return;
        }

        int temporaryFinalCost = temporaryNode.heuristic + cost;

        // If TemporaryNode is already in Fringe  set True
        boolean isOpen = openList.contains(temporaryNode);

        // If OpenlistContains the temporary node OR if it's the new final cost is more than his we update it
        if (!isOpen || temporaryFinalCost < temporaryNode.finalCost) {
            temporaryNode.finalCost = temporaryFinalCost;
            temporaryNode.parent = currentNode;

            // If not already explored then add it
            if (!isOpen) {
                openList.add(temporaryNode);
            }
        }
    }

    public void process() {

        openList = new ArrayList();
        openList.add(start);
        Node current;

        while (true) {

            current = openList.get(0);
            openList.remove(0);
            Collections.sort(openList);

            if (current == null) {
                break;
            }

            // Sets it to visited
            closedCells[current.x][current.y] = true;

            if (current.x == goal.x && current.y == goal.y) {

                return;
            }

            Node temporaryNode;

            //West
            if (current.x - 1 >= 0) {
                temporaryNode = grid[current.x - 1][current.y];
                // Calculates it here instead
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost);

                if (current.y - 1 >= 0) {
                    temporaryNode = grid[current.x - 1][current.y - 1];
                    updateCostIfNeeded(current, temporaryNode, current.finalCost + diagonalCost);

                }
                if (current.y + 1 < grid[0].length) {
                    temporaryNode = grid[current.x - 1][current.y + 1];
                    updateCostIfNeeded(current, temporaryNode, current.finalCost + diagonalCost);

                }

            }
            // South
            if (current.y - 1 >= 0) {
                temporaryNode = grid[current.x][current.y - 1];
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost);
            }

            // North
            if (current.y + 1 < grid[0].length) {
                temporaryNode = grid[current.x][current.y + 1];
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost);
            }

            //East
            if (current.x + 1 < grid.length) {
                temporaryNode = grid[current.x + 1][current.y];
                // Calculates it here instead
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost);

                if (current.y - 1 >= 0) {
                    temporaryNode = grid[current.x + 1][current.y - 1];
                    updateCostIfNeeded(current, temporaryNode, current.finalCost + diagonalCost);

                }
                if (current.y + 1 < grid[0].length) {
                    temporaryNode = grid[current.x + 1][current.y + 1];
                    updateCostIfNeeded(current, temporaryNode, current.finalCost + diagonalCost);

                }

            }

        }
    }

//    public void display() {
//        System.out.println("grid: ");
//        for (int x = 0; x < grid.length; x++) {
//            for (int y = 0; y < grid[x].length; y++) {
//                if (x == start.x && y == start.y) {
//                    System.out.print("SO  "); // Source Cell
//                } else if (x == goal.x && y == goal.y) {
//                    System.out.print("DE  "); // Destination Cell
//                } else if (grid[x][y] != null) {
//                    System.out.printf("%-3d ", 0);
//                } else {
//                    System.out.print("BL  "); // Block Cell /// not used
//                }
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//    public void displayScores() {
//        System.out.println("\nScores for cells : ");
//
//        for (int x = 0; x < grid.length; x++) {
//            for (int y = 0; y < grid[x].length; y++) {
//                if (grid[x][y] != null) {
//                    System.out.printf("%-3d ", grid[x][y].finalCost);
//                } else {
//                    System.out.println("BL  ");
//                }
//
//            }
//
//            System.out.println();
//        }
//
//        System.out.println();
//
//    }
    public ArrayList<Node> getSolutionPath() {
        ArrayList<Node> path = new ArrayList();
        if (closedCells[goal.x][goal.y]) {
            //We track back the path
            System.out.print("Path: ");

            Node currentNode = grid[goal.x][goal.y];
            path.add(currentNode);
            System.out.print(currentNode); // Change to println ?
            grid[currentNode.x][currentNode.y].solution = true;

            while (currentNode.parent != null) {
                System.out.print(" -> " + currentNode.parent);
                grid[currentNode.parent.x][currentNode.parent.y].solution = true;
                currentNode = currentNode.parent;
                path.add(currentNode);
            }

            System.out.println("\n");

//            for (int x = 0; x < grid.length; x++) {
//                for (int y = 0; y < grid[x].length; y++) {
//                    if (x == start.x && y == start.y) {
//                        System.out.print("SO  "); // Source Cell (Start Node)
//                    } else if (x == goal.x && y == goal.y) {
//                        System.out.print("DE  "); // Destination Cell // Goal state
//                    } else if (grid[x][y] != null) {
//                        System.out.printf("%-3s ", grid[x][y].solution ? "X" : "0");    // Error might be here
//                    } else {
//                        System.out.print("BL  "); // Block Cell /// not used
//                    }
//                }
//                System.out.println();
//            }
//            System.out.println();
//        } else {
//            System.out.println("No possible Path");
//        }
        }
        return path;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }

    public void processAi(PositionPart playerPosition, PositionPart enemyPosition) {

        if (update % 70 == 0) {
            newGridSetup(playerPosition, enemyPosition);
            process();
            // display();
            // displayScores();
            solution = getSolutionPath();
        }

        counter = solution.size();
        if (counter > 0 && update % 20 == 0) {
            Node node = solution.get(counter - 1);
            enemyPosition.setX(node.x * 45);
            enemyPosition.setY(node.y * 25);
            solution.remove(counter - 1);
        }
        update++;
    }

}