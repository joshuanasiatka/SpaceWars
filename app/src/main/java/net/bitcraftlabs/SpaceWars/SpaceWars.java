package net.bitcraftlabs.SpaceWars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SpaceWars extends View {
//    float cx = 0;  // x, y coordinates of center of Ship.
//    float cy = 0;
//    float r = 50; // pixel length of radius of Ship
//    int color = Color.RED;
//    int color = ContextCompat.getColor(context, R.color.dark);
//    int myColor = context.getResources().getColor(edu.fitchburgstate.staylor.R.color.white);
    Paint paint = new Paint();
    public static float vx = 0;
    public static float vy = 0;
    public static float cx = 10;
    public static float cy = 10;
    float r  = 50;
    
//    public Ship b = new Ship();
//    private Ship[] ShipList = {new Ship(), new Ship(), new Ship()};

    public SpaceWars(Context context) {
        super(context);
    }

    public SpaceWars(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        int w = getWidth();
        int h = getHeight();

        // play with the physics
        cx += vx;
        cy += vy;

        //right bounce
        if (cx + r > w) {
//            vx = -Math.abs(vx);
            vx = 0;
            cx -= 10;
        }
        if (cx < r) {// left bounce
//            vx = Math.abs(vx);
            vx = 0;
            cx += 10;
        }
        if (cy < r) { // top bounce
//            vy = Math.abs(vy);
            vy = 0;
            cy += 10;
        }
        if (cy + r > h) { // bottom bounce
//            vy = -Math.abs(vy);
            vy = 0;
            cy -= 10;
        }

        paint.setColor(Color.RED);
        c.drawCircle(cx, cy, r, paint);
    }
}
