package group7.ai;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import java.util.ArrayList;
import group7.common.map.Node;
import group7.common.services.IAIProcessing;
import group7.common.services.IArtificialIntelligence;
import group7.common.services.IGamePluginService;
import group7.commonplayer.PlayerCharacter;
import java.util.Collections;

public class AI implements IArtificialIntelligence {

    ArrayList<Node> solutionPath = new ArrayList();

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

    private float remainingDistance = -1;
    private boolean isHorizontal;
    private Node thisNode;

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

    private PositionPart getPlayerPosition(World world) {
        PositionPart playerPos = null;
        for (Entity entity : world.getEntities(PlayerCharacter.class)) {
            playerPos = entity.getPart(PositionPart.class);
        }
        return playerPos;
    }

    @Override
    public void solutionPathCalculation(PositionPart enemyPosition, World world, GameData game) {
        newGridSetup(enemyPosition, getPlayerPosition(world));
        process();
        moveEnemy(getSolutionPath(), enemyPosition);

    }

    public void moveEnemy(ArrayList<Node> solutionPath, PositionPart enemyPosition) {
        System.out.println("Hello i have been called");
        System.out.println("This is the array i got" + solutionPath.toString());
        Node node = solutionPath.get(solutionPath.size() - 2);
        System.out.println("En pos before:" + enemyPosition.getX());
        enemyPosition.setX(node.x * 32);
        System.out.println("En pos after:" + enemyPosition.getX());
        enemyPosition.setY(node.y * 32);
    }

//        if (playerPosition != null) {
//            newGridSetup(playerPosition, enemyPosition);
//            process();
//            solutionPath = getSolutionPath();
//            // }
//            System.out.println("Solution" + solutionPath.toString());
//
//            if (solutionPath.size() != 1 || !solutionPath.isEmpty()) {
//                Node node = solutionPath.get(solutionPath.size() - 2);
//                System.out.println("En pos before:" + enemyPosition.getX());
//
//                enemyPosition.setX(node.x * 32);
//                System.out.println("En pos after:" + enemyPosition.getX());
//                enemyPosition.setY(node.y * 32);
//            }
//        } else {
//            System.out.println("it was null");
//        }
//        counter = solution.size();
//        if (counter > 0 && update % 20 == 0) {
//            Node node = solution.get(counter - 1);
//            enemyPosition.setX(node.x * 32);
//            enemyPosition.setY(node.y * 32);
//            solution.remove(counter - 1);
//        }
//        updateFrequency++;
//    }
}
//
//        if (thisNode == null) {
//            thisNode = new Node((int) (enemyPosition.getX() / 45), (int) (enemyPosition.getY() / 25));
//            thisNode.isStart = true;
//        }
//
//        float distanceToNextTile = -1;
//
//        if (updateFrequency % 70 == 0) {
//            newGridSetup(playerPosition, enemyPosition);
//            process();
//            solutionPath = getSolutionPath();
//        }
//
//        solutionSize = solutionPath.size();
//
//        float larger;
//        float lower;
//        if (isHorizontal) {
//            System.out.println("xMoved");
//
//            larger = Math.max(remainingDistance, enemyPosition.getX());
//            lower = Math.min(remainingDistance, enemyPosition.getX());
//            distanceToNextTile = larger - lower;
//            System.out.println("Larger - Lower  = Testy " + " " + larger + " - " + lower + " = " + distanceToNextTile);
//
//        } else if (!isHorizontal && !(thisNode.isStart)) {
//
//            larger = Math.max(remainingDistance, enemyPosition.getY());
//            lower = Math.min(remainingDistance, enemyPosition.getY());
//            System.out.println("Y moved");
//            distanceToNextTile = larger - lower;
//
//            System.out.println("Larger - Lower  = Testy " + " " + larger + " - " + lower + " = " + distanceToNextTile);
//        }
//        if (solutionSize < 1) {
//            thisNode.direction = null;
//        }
//
//        if ((solutionSize > 0 && distanceToNextTile <= 4) || (updateFrequency % 71 == 0 && solutionSize > 0)) {
//
//            System.out.println("Does it go inside?");
//            System.out.println("counter inside " + solutionSize);
//            Node previousNode = solutionPath.get(solutionSize - 1);
//            if (solutionSize > 1) {
//                System.out.println("pre first");
//                thisNode = solutionPath.get(solutionSize - 2);
//                System.out.println("post first");
//            }
//            // prev = where Enmey currently is
//            // current = where it wants to go
//            int xDiff = previousNode.getX() - thisNode.getX();
//            int yDiff = previousNode.getY() - thisNode.getY();
//
//            System.out.println("xDiff: " + xDiff);
//            System.out.println("yDiff" + yDiff);
//            if (Math.abs(xDiff) > Math.abs(yDiff)) {
//
//                System.out.println("Math.abs(xDiff) >= Math.abs(yDiff) is = " + (Math.abs(xDiff) >= Math.abs(yDiff)));
//                isHorizontal = true;
//
//                if (previousNode.x <= thisNode.x) {
//                    thisNode.direction = "right";
//                    remainingDistance = enemyPosition.getX() + 45;
//                    System.out.println("right");
//
//                } else if (previousNode.x > thisNode.x) {
//                    thisNode.direction = "left";
//                    remainingDistance = enemyPosition.getX() - 45;
//                    System.out.println("left");
//
//                }
//            } else if (Math.abs(xDiff) < Math.abs(yDiff)) {
//
//                isHorizontal = false;
//
//                if (previousNode.y <= thisNode.y) {
//                    thisNode.direction = "up";
//                    remainingDistance = enemyPosition.getY() + 25;
//                    System.out.println("up");
//
//                } else if (previousNode.y > thisNode.y) {
//                    thisNode.direction = "down";
//                    remainingDistance = enemyPosition.getY() - 25;
//                    System.out.println("down");
//
//                }
//
//            } else {
//                thisNode.direction = null;
//                thisNode.isStart = true;
//
//            }
//
//            solutionPath.remove(solutionSize - 1);
//
//            if (solutionSize > 1) {
//                solutionPath.remove(solutionSize - 2);
//            }
//
//        }
//
//        updateFrequency++;
//        System.out.println("Returning node stats: " + thisNode.direction);
//        enemymov.setDirection(thisNode.direction);
//        //thisNode.direction = null;
//    }

