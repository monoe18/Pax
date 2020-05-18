/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.ai;

import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.map.Node;
import java.util.ArrayList;

/**
 *
 * @author Karla
 */
public class AI_movement {
    
     ArrayList<Node> solutionPath = new ArrayList<> () ; 
        float remainingDistance = -1;
        boolean isHorizontal = false;
        Node thisNode = null;
        int updateFrequency =0;
        AI ai = new AI();
        

    
        
        
        
        
        
        
        
    public void getAIMovement(PositionPart enemyPosition, PositionPart playerPosition, MovingPart enemymov) {
        
        int solutionSize = 0;
        
        
        if (thisNode == null) {
            thisNode = new Node((int) (enemyPosition.getX() / 45), (int) (enemyPosition.getY() / 25));
            thisNode.isStart = true;
        }

        float distanceToNextTile = -1;
        System.out.println("Update Frequency: " + updateFrequency);

        if (updateFrequency % 70 == 0) {
            ai.newGridSetup(playerPosition, enemyPosition);
            ai.process();
            solutionPath = ai.getSolutionPath();
        }
        

       solutionSize = solutionPath.size();

        float larger;
        float lower;
        if (isHorizontal) {
            System.out.println("xMoved");

            larger = Math.max(remainingDistance, enemyPosition.getX());
            lower = Math.min(remainingDistance, enemyPosition.getX());
            distanceToNextTile = larger - lower;
            System.out.println("Larger - Lower  = Testy " + " " + larger + " - " + lower + " = " + distanceToNextTile);

        } else if (!isHorizontal && !(thisNode.isStart)) {

            larger = Math.max(remainingDistance, enemyPosition.getY());
            lower = Math.min(remainingDistance, enemyPosition.getY());
            System.out.println("Y moved");
            distanceToNextTile = larger - lower;

            System.out.println("Larger - Lower  = Testy " + " " + larger + " - " + lower + " = " + distanceToNextTile);
        }
        if (solutionSize < 1) {
            thisNode.direction = null;
        }

        if ((solutionSize > 0 && distanceToNextTile <= 4) || (updateFrequency % 70 == 0 && solutionSize > 0)) {

            System.out.println("Does it go inside?");
            System.out.println("counter inside " + solutionSize);
            Node previousNode = solutionPath.get(solutionSize - 1);
            if (solutionSize > 1) {
                System.out.println("pre first");
                thisNode = solutionPath.get(solutionSize - 2);
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
                isHorizontal = true;

                if (previousNode.x <= thisNode.x) {
                    thisNode.direction = "right";
                    remainingDistance = enemyPosition.getX() + 45;
                    System.out.println("right");

                } else if (previousNode.x > thisNode.x) {
                    thisNode.direction = "left";
                    remainingDistance = enemyPosition.getX() - 45;
                    System.out.println("left");

                }
            } else if (Math.abs(xDiff) < Math.abs(yDiff)) {

                isHorizontal = false;

                if (previousNode.y <= thisNode.y) {
                    thisNode.direction = "up";
                    remainingDistance = enemyPosition.getY() + 25;
                    System.out.println("up");

                } else if (previousNode.y > thisNode.y) {
                    thisNode.direction = "down";
                    remainingDistance = enemyPosition.getY() - 25;
                    System.out.println("down");

                }

            } else {
                System.out.println("Does this get nulllllllllllled");
                thisNode.direction = null;
                thisNode.isStart = true;

            }

            solutionPath.remove(solutionSize - 1);

            if (solutionSize > 1) {
                solutionPath.remove(solutionSize - 2);
            }

        }

        updateFrequency++;
        System.out.println("Returning node stats: " + thisNode.direction);
        enemymov.setDirection(thisNode.direction);
        //thisNode.direction = null;
    }
    
}
