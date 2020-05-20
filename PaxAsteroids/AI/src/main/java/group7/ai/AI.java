package group7.ai;

import group7.common.data.Entity;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import java.util.ArrayList;
import group7.common.services.IArtificialIntelligence;
import group7.commonenemy.Enemy;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
public class AI implements IArtificialIntelligence {

    ArrayList<Node> solutionPath;

    int solutionSize;
    int updateFrequency = 50;

    public static final int diagonalCost = 14;
    public static final int stepCost = 10;

    private Node[][] grid = new Node[45][25];
    private ArrayList<Node> fringe;
    private Node startTile;
    private Node goalTile;
    private int gridFactorX = 32; //grid offset num x
    private int gridFactorY = 32; //grid offset num y
    private boolean visitedTiles[][];
    
    private static final ConcurrentHashMap<Entity, AI_movement> aiHashMap = new ConcurrentHashMap();
    
    private CopyOnWriteArrayList<Entity> removeEntities= new CopyOnWriteArrayList<>();
  
    @Override
    public void AddEntities(World world){
        
            for (Entity e : world.getEntities(Enemy.class)) {   // Also delete old ones
                
                removeEntities.add(e); // used to remove from hashmap
                
                if(aiHashMap.containsKey(e)){
                    continue;
                }else{
                    
                    
                    for (Entity en : removeEntities) {
                        if (!(aiHashMap.containsKey(e))){
                            aiHashMap.remove(en);
                            removeEntities.remove(en);
                        
                    }
                    aiHashMap.put(e, new AI_movement());
                    
                        
                    }
                }
        }
    }
    
    
    @Override
    public void processAI(PositionPart enemyPosition, PositionPart playerPosition, MovingPart enemymov, Entity entity) {
        
        
        try {
            for (Map.Entry<Entity, AI_movement> entry : aiHashMap.entrySet()) {
                    
                // We get the specific AI_Movement via getValue()
                if (entry.getKey().getID().equals(entity.getID())) {
//                          solutionPath = getNewPathCalculation(playerPosition, enemyPosition);
                        entry.getValue().getAIMovement(enemyPosition, playerPosition, enemymov);
                
                    
                }

            }
        } catch (Exception e) {
        }

        updateFrequency++;
    }
    
    
    
    public ArrayList<Node> getNewPathCalculation(PositionPart playerPosition, PositionPart enemyPosition) {
        newGridSetup(playerPosition, enemyPosition);
        process();
        return getSolutionPath();
    }

    
    
    
    

    
    
    
    
    
    
    
    
    
    
    public AI() {
    }

    // Gives each Tile in grid a heuristic
    // Called each fifth second to recalclulate where the ai should go
    public void newGridSetup(PositionPart player, PositionPart enemy) {
        //maybe here we see   this.grid = new Node[45][25];
        this.goalTile = null;
        this.startTile = null;
        getNewPositions(player, enemy);
        this.visitedTiles = new boolean[45][25];

        //  System.out.println("grid length " + grid.length);
        for (int x = 5; x < grid.length - 5; x++) {
            for (int y = 5; y < grid[x].length - 5; y++) {
                grid[x][y] = new Node(x, y);
                grid[x][y].heuristic = (int) Math.sqrt(Math.pow(x - goalTile.x, 2) + Math.pow(y - goalTile.y, 2));
                grid[x][y].solution = false;
            }
        }
        startTile.finalCost = 0;
    }

    public void getNewPositions(PositionPart player, PositionPart enemy) {
        if (player != null) {
            this.goalTile = new Node((int) player.getX() / this.gridFactorX, (int) player.getY() / this.gridFactorY);
            this.startTile = new Node((int) enemy.getX() / this.gridFactorX, (int) enemy.getY() / this.gridFactorY);
        } else {
            this.goalTile = new Node(500 / this.gridFactorX, (int) 500 / this.gridFactorY);
            this.startTile = new Node((int) enemy.getX() / this.gridFactorX, (int) enemy.getY() / this.gridFactorY);
        }

    }

    public void updateCostIfNeeded(Node currentNode, Node temporaryNode, int cost, String directionTo) {

        if (temporaryNode == null || visitedTiles[temporaryNode.x][temporaryNode.y]) {
            return;
        }

        int temporaryFinalCost = temporaryNode.heuristic + cost;

        // If TemporaryNode is already in Fringe  set True
        boolean isOpen = fringe.contains(temporaryNode);

        // If OpenlistContains the temporary node OR if it's the new final cost is more than his we update it
        if (!isOpen || temporaryFinalCost < temporaryNode.finalCost) {
            temporaryNode.finalCost = temporaryFinalCost;
            temporaryNode.parent = currentNode;

            temporaryNode.direction = directionTo;

            // If not already explored then add it
            if (!isOpen) {
                fringe.add(temporaryNode);
            }
        }
    }

    public void process() {

        fringe = new ArrayList();
        fringe.add(startTile);
        Node current;

        while (true) {
            current = fringe.get(0);
            fringe.remove(0);
            Collections.sort(fringe);

            if (current == null) {
                break;
            }

            // Sets it to visited
            visitedTiles[current.x][current.y] = true;

            if (current.x == goalTile.x && current.y == goalTile.y) {
                return;
            }

        getChildren(current);

        }
    }
    
    public void getChildren ( Node current){
        
            Node temporaryNode;

            //West
            if (current.x - 1 >= 0) {
                temporaryNode = grid[current.x - 1][current.y];
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost, "left");

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
                updateCostIfNeeded(current, temporaryNode, current.finalCost + stepCost, "right");

            }
        
    }

    public ArrayList<Node> getSolutionPath() {
        ArrayList<Node> path = new ArrayList();

        if (visitedTiles[goalTile.x][goalTile.y]) {
            Node currentNode = grid[goalTile.x][goalTile.y];
            path.add(currentNode);
            grid[currentNode.x][currentNode.y].solution = true;

            while (currentNode.parent != null) {
                grid[currentNode.parent.x][currentNode.parent.y].solution = true;
                currentNode = currentNode.parent;
                path.add(currentNode);
            }
        }
        return path;
    }



 
}
