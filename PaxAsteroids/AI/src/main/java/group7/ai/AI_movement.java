/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.ai;

import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import java.util.ArrayList;

/**
 *
 * @author Karla
 */
public class AI_movement {
    
     ArrayList<Node> solutionPath = new ArrayList<> () ; 
        float remainingDistance = -1;
        boolean isHorizontal;
        Node thisNode;
        int updateFrequency =50;
        AI ai = new AI();
        int solutionSize;
         float distanceToNextTile = -1;
        

    
         
         
    public void moveAxis(PositionPart enemyPosition){
    // metode : CheckIfShouldMoveVertOrHorizontal
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
    
}       // Checks which way it should go next 
        public void getNextNode (PositionPart enemyPosition){
                        
            
            Node previousNode = solutionPath.get(solutionSize - 1);
            if (solutionSize > 1) {
                thisNode = solutionPath.get(solutionSize - 2);
            }
            // prev = where Enmey currently is
            // current = where it wants to go
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
    
    
    
        public void getAIMovement(PositionPart enemyPosition, PositionPart playerPosition, MovingPart enemymov) {

        // Initialize first node
        if (thisNode == null) {
            thisNode = new Node((int) (enemyPosition.getX() / 45), (int) (enemyPosition.getY() / 25));
            thisNode.isStart = true;
        }
        if(updateFrequency %70 == 0){
        ai.newGridSetup(playerPosition, enemyPosition);
        ai.process();
        solutionPath =  ai.getSolutionPath();
        }
        
        
        solutionSize = solutionPath.size();

        // Checks if the ai should move horizontally or vertically
        moveAxis(enemyPosition);

        // New method - GetNextMove
        if ((solutionSize > 0 && distanceToNextTile <= 4) || (updateFrequency % 70 == 0 && solutionSize > 0)) {
            // Checks which way it should go next 
            getNextNode(enemyPosition);

        }

        updateFrequency++;

        enemymov.setDirection(thisNode.direction);
    }

}
