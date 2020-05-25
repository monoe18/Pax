package group7.ai;

import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import java.util.ArrayList;

public class AI_movement {

    private ArrayList<Node> solutionPath = new ArrayList<>();
    private float remainingDistance = -1;
    private boolean isHorizontal;
    private Node thisNode;
    private int updateFrequency = 50;
    private AI ai = new AI();
    private int solutionSize;
    private float distanceToNextTile = -1;

    //This method checks if the movement should be horizantal or vertical
    public void moveAxis(PositionPart enemyPosition) {
        float larger;
        float lower;
        if (isHorizontal) {
            larger = Math.max(remainingDistance, enemyPosition.getX());
            lower = Math.min(remainingDistance, enemyPosition.getX());
            distanceToNextTile = larger - lower;
        } else if (!isHorizontal && !(thisNode.isStart)) {
            larger = Math.max(remainingDistance, enemyPosition.getY());
            lower = Math.min(remainingDistance, enemyPosition.getY());
            distanceToNextTile = larger - lower;
        }

        if (solutionSize < 1) {
            thisNode.direction = null;
        }

    }

    //Sets the direction for the next node
    public void setNextNode(PositionPart enemyPosition) {

        Node previousNode = solutionPath.get(solutionSize - 1);
        if (solutionSize > 1) {
            thisNode = solutionPath.get(solutionSize - 2);
        }
        int xDiff = previousNode.getX() - thisNode.getX();
        int yDiff = previousNode.getY() - thisNode.getY();

        if (Math.abs(xDiff) > Math.abs(yDiff)) {

            isHorizontal = true;

            if (previousNode.x <= thisNode.x) {
                thisNode.direction = "right";
                remainingDistance = enemyPosition.getX() + 45;

            } else if (previousNode.x > thisNode.x) {
                thisNode.direction = "left";
                remainingDistance = enemyPosition.getX() - 45;

            }
        } else if (Math.abs(xDiff) < Math.abs(yDiff)) {
            isHorizontal = false;

            if (previousNode.y <= thisNode.y) {
                thisNode.direction = "up";
                remainingDistance = enemyPosition.getY() + 25;

            } else if (previousNode.y > thisNode.y) {
                thisNode.direction = "down";
                remainingDistance = enemyPosition.getY() - 25;

            }

        } else {
            thisNode.direction = null;

        }

        solutionPath.remove(solutionSize - 1);

        if (solutionSize > 1) {
            solutionPath.remove(solutionSize - 2);
        }
    }

    public int getUpdateFrequency() {
        return this.updateFrequency;
    }

    public void processAIMovement(PositionPart enemyPosition, PositionPart playerPosition, MovingPart enemymov) {

        // Initialize first node
        if (thisNode == null) {
            thisNode = new Node((int) (enemyPosition.getX() / 45), (int) (enemyPosition.getY() / 25));
            thisNode.isStart = true;
        }

        solutionSize = solutionPath.size();

        moveAxis(enemyPosition);

        //sets next node
        if ((solutionSize > 0 && distanceToNextTile <= 4) || (updateFrequency % 70 == 0 && solutionSize > 0)) {
            // Checks which way it should go next
            setNextNode(enemyPosition);

        }
        updateFrequency++;
        enemymov.setDirection(thisNode.direction);
    }

    public void setPath(ArrayList<Node> solutionPath) {
        this.solutionPath = solutionPath;
    }

}
