package fr.unice.polytech.ogl.islbd.memory;

import fr.unice.polytech.ogl.islbd.action.Scout;

public class ScoutTileInfo implements Storable {
    Scout scoutInfo;

    public ScoutTileInfo(Scout scoutInfo) {
        this.scoutInfo = scoutInfo;
    }

    public Scout getScout() {
        return scoutInfo;
    }

    @Override
    public String getName() {
        return "scout_tile_info";
    }
}
