package group7.common.entityparts;

import group7.common.data.Entity;
import group7.common.data.GameData;
import java.util.ArrayList;
import group7.common.map.Node;
import java.util.Collections;

public class AIPart implements EntityPart {

    ArrayList<Node> solution = new ArrayList();
    int counter;
    int update = 50;
    int startNode = 0;

    String directionString;

    public static final int diagonalCost = 14;
    public static final int stepCost = 10;

    private Node[][] grid;
    private ArrayList<Node> openList;
    private Node start;
    private Node goal;
    private ArrayList<Node> closedList;
    private int gridFactorX = 32; //grid offset num x
    private int gridFactorY = 32; //grid offset num y
    private boolean closedCells[][];

    private float difference = -1;
    private float oldMovDif = 0;
    float lastPos = 0;
    boolean isX;
    float difCheck = -1;
    float prevDiff =0;
    boolean isStart = true;
    
    Node thisNode;


    // Constructor - Created once.
    public AIPart(int gridWidth, int gridHeight) {
        this.grid = new Node[gridWidth][gridHeight];
    }

    // Gives each cell in grid a heuristic
    // Called each fifth second to recalclulate where the ai should go
    public void newGridSetup(PositionPart player, PositionPart enemy) {
        this.goal = null;
        this.start = null;
        getNewPositions(player, enemy);
        this.closedCells = new boolean[45][25];

        System.out.println("grid length " + grid.length);
        for (int x = 5; x < grid.length - 5; x++) {
            for (int y = 5; y < grid[x].length - 5; y++) {
                grid[x][y] = new Node(x, y);
                grid[x][y].heuristic = (int) Math.sqrt(Math.pow(x - goal.x, 2) + Math.pow(y - goal.y, 2));
                grid[x][y].solution = false;
            }
        }

        start.finalCost = 0;

    }

    public void getNewPositions(PositionPart player, PositionPart enemy) {
       
        if (player != null)  {
             this.goal = new Node((int) player.getX() / this.gridFactorX, (int) player.getY() / this.gridFactorY);
             this.start = new Node((int) enemy.getX() / this.gridFactorX, (int) enemy.getY() / this.gridFactorY);
        } else {
            this.goal = new Node(500 / this.gridFactorX, (int) 500/ this.gridFactorY);
             this.start = new Node((int) enemy.getX() / this.gridFactorX, (int) enemy.getY() / this.gridFactorY);
        } 
       
    }

    public void updateCostIfNeeded(Node currentNode, Node temporaryNode, int cost, String directionTo) {

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
            
            temporaryNode.direction = directionTo;

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

                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost, "left");
               
//                if (current.y - 1 >= 0) {
//                    temporaryNode = grid[current.x - 1][current.y - 1];
//                    updateCostIfNeeded(current, temporaryNode, current.finalCost + diagonalCost, "left");
//
//
//                }
//                if (current.y + 1 < grid[0].length) {
//                    temporaryNode = grid[current.x - 1][current.y + 1];
//                    updateCostIfNeeded(current, temporaryNode, current.finalCost + diagonalCost, "left");
//                    
//                }

            }
            // South
            if (current.y - 1 >= 0) {
                temporaryNode = grid[current.x][current.y - 1];
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost, "down");

            }

            // North
            if (current.y + 1 < grid[0].length) {
                temporaryNode = grid[current.x][current.y + 1];
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost, "up");

            }

            //East
            if (current.x + 1 < grid.length) {
                temporaryNode = grid[current.x + 1][current.y];
                // Calculates it here instead
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost, "right");
   
//                if (current.y - 1 >= 0) {
//                    temporaryNode = grid[current.x + 1][current.y - 1];
//                    updateCostIfNeeded(current, temporaryNode, current.finalCost + diagonalCost, "right");
//
//                }
//                if (current.y + 1 < grid[0].length) {
//                    temporaryNode = grid[current.x + 1][current.y + 1];
//                    updateCostIfNeeded(current, temporaryNode, current.finalCost + diagonalCost, "right");
// 
//                }

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

//            System.out.println("\n");
//
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

    public void processAi(PositionPart playerPosition, PositionPart enemyPosition, MovingPart enemymov, MovingPart playermov) {
        
        if(thisNode ==null){
            thisNode = new Node((int) (enemyPosition.getX()/45), (int) (enemyPosition.getY()/25));
            thisNode.isStart = true;
        }
        
        float testy = -1;
        System.out.println("update = " + update);
        if (update % 70 == 0) {
            newGridSetup(playerPosition, enemyPosition);
            process();
            solution = getSolutionPath();
        }

        counter = solution.size();
//        System.out.println("counter size:" + counter);
        
       
       float larger;
       float lower;
        
        
        
        
        if (isX) {
            System.out.println("xMoved");
            
            
            larger = java.lang.Math.max(difference, enemyPosition.getX());
            lower = java.lang.Math.min(difference, enemyPosition.getX());
            testy = larger - lower;
                    System.out.println("Larger - Lower  = Testy " +  " " + larger + " - " + lower + " = " + testy);    

        } else if(!isX && !(thisNode.isStart)) {
          
              
            larger = java.lang.Math.max(difference, enemyPosition.getY());
            lower = java.lang.Math.min(difference, enemyPosition.getY());
            System.out.println("Y moved");
            testy = larger - lower;
            
          System.out.println("Larger - Lower  = Testy " +  " " + larger + " - " + lower + " = " + testy);    
     

        }
        
//        try{
//        System.out.println("Larger - Lower  = Testy " +  " " + larger + " - " + lower + " = " + testy);    
//        } catch(Exception e ){
//            
//        }

        
    

        if ((counter > 0 && testy <= 4) || (update %71 ==0 && counter >0)) {
            
            System.out.println("Does it go inside?");
            System.out.println("counter inside " + counter );
            
            
            Node previousNode = solution.get(counter - 1);
            if(counter>1){
                System.out.println("pre first");
            thisNode = solution.get(counter - 2);
                System.out.println("post first");
            }
                        // prev = where Enmey currently is
                        // current = where it wants to go
            int xDiff = previousNode.getX() - thisNode.getX();
            int yDiff = previousNode.getY() - thisNode.getY();


            System.out.println("xDiff: " + xDiff);
            System.out.println("yDiff" + yDiff);

            
            if (Math.abs(xDiff) > Math.abs(yDiff)) {

                System.out.println("Math.abs(xDiff) >= Math.abs(yDiff) is = " + (Math.abs(xDiff) >= Math.abs(yDiff)));
                isX = true;

                if (previousNode.x <= thisNode.x) {
                    thisNode.direction = "right";
                    difference = enemyPosition.getX() + 45;
                    System.out.println("right");
                    

                } else if (previousNode.x > thisNode.x) {
                    thisNode.direction = "left";
                    difference = enemyPosition.getX() - 45;
                    System.out.println("left");

                } 
            } else if (Math.abs(xDiff) < Math.abs(yDiff)) {

                isX = false;

                if (previousNode.y <= thisNode.y) {
                    thisNode.direction = "up";
                    difference = enemyPosition.getY() + 25;
                    System.out.println("up");

                } else if (previousNode.y > thisNode.y) {
                    thisNode.direction = "down";
                    difference = enemyPosition.getY() - 25;
                    System.out.println("down");
                    
                }
                

            } else{
                thisNode.direction = null;
                isStart = true;
            }

             solution.remove(counter - 1);
            
             if(counter>1){
           solution.remove(counter - 2);
 }
             










//
//            
//            float xDif = (enemyPosition.getX() / 45) - currentNode.x;
//            float yDif = (enemyPosition.getY() / 25) - currentNode.y;
//            float lastPost = enemyPosition.getX();
//
//            System.out.println("enemy pos: " + enemyPosition.getX() + ", " + enemyPosition.getY());
//            System.out.println("enemy pos 25 & 45 : " + enemyPosition.getX() / 45 + " , " + enemyPosition.getY() / 25);
//
//            System.out.println("xDif =  " + xDif);
//            System.out.println("yDif =  " + yDif);
//
//            
//
//            System.out.println("Current Node Direction: " + currentNode.direction);
//
////            enemymov.setDirection(node.direction);
////            enemyPosition.setX(node.x * 45);
////            enemyPosition.setY(node.y * 25);
           
        }
        
        
        
        
        
        
        update++;
        System.out.println("Returning node stats: " + thisNode.direction);
        enemymov.setDirection(thisNode.direction);

    }

}
