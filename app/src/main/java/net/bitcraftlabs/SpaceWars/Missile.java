package net.bitcraftlabs.SpaceWars;

import android.util.Log;

/**
 * Created by joshuanasiatka on 5/18/16.
 */
public class Missile {
    int mx = 0; // x coord
    int my = 0; // y coord
    int mr = 0; // radius
    int mv = 10;// velocity
    boolean destroyed = false; //destroy status
    int w = 0;  // screen width
    int h = 0;  // screen height

    Direction mDr = Direction.NONE;

    public enum Direction {
        NORTH, SOUTH, EAST, WEST, NONE;
    }

    public void missileFire() {
        switch (mDr) {
            case NORTH:
                Log.d("MISSILE", "FIRING NORTHBOUND");
                my -= mv;
                if (my < mr) {
                    mDr = Direction.NONE;
                    mr = 0;
                    destroyed = true;
                    Log.d("MISSILE", "DESTROYED!");
                }
                break;
            case SOUTH:
                Log.d("MISSILE", "FIRING SOUTHBOUND");
                my += mv;
                if (my + mr > h) {
                    mDr = Direction.NONE;
                    mr = 0;
                    destroyed = true;
                    Log.d("MISSILE", "DESTROYED!");
                }
                break;
            case EAST:
                Log.d("MISSILE", "FIRING EASTBOUND");
                mx -= mv;
                if (mx < mr) {
                    mDr = Direction.NONE;
                    mr = 0;
                    destroyed = true;
                    Log.d("MISSILE", "DESTROYED!");
                }
                break;
            case WEST:
                Log.d("MISSILE", "FIRING WESTBOUND");
                mx += mv;
                if (mx + mr > w) {
                    mDr = Direction.NONE;
                    mr = 0;
                    destroyed = true;
                    Log.d("MISSILE", "DESTROYED!");
                }
                break;
        }
    }
}
