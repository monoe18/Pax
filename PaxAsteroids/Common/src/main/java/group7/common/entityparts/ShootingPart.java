package group7.common.entityparts;

import group7.common.data.Entity;
import group7.common.data.GameData;

public class ShootingPart implements EntityPart {

    boolean shooting = false;
    private String type;
    
    public ShootingPart(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
    
    public void engageShooting(){
        shooting = true;
    }
    
    public void disengageShooting(){
        shooting = false;
    }

    public boolean isShooting(){
        return shooting;
    }
}
