package net.bitcraftlabs.SpaceWars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
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
    public static int vx = 0;
    public static int vy = 0;
    public static int cx = 10;
    public static int cy = 10;
    int r  = 100;

    //Ship
    public static Direction dr = Direction.SOUTH;
    
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
//        int w = getWidth();
//        int h = getHeight();
        Point pt = new Point();
        pt.set(cx,cy);

        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.RED);
        Path path = tri(pt, r, dr);
        c.drawPath(path, p);
//        // play with the physics
//        cx += vx;
//        cy += vy;
//
//        //right bounce
//        if (cx + r > w) {
////            vx = -Math.abs(vx);
//            vx = 0;
//            cx -= 10;
//        }
//        if (cx < r) {// left bounce
////            vx = Math.abs(vx);
//            vx = 0;
//            cx += 10;
//        }
//        if (cy < r) { // top bounce
////            vy = Math.abs(vy);
//            vy = 0;
//            cy += 10;
//        }
//        if (cy + r > h) { // bottom bounce
////            vy = -Math.abs(vy);
//            vy = 0;
//            cy -= 10;
//        }

//        paint.setColor(Color.RED);
//        c.drawCircle(cx, cy, r, paint);
    }

    public enum Direction {
        NORTH, SOUTH, EAST, WEST;
    }

    public Path tri (Point p1, int width, Direction direction){

        Point p2 = null, p3 = null;

        if (direction == Direction.NORTH) {
            p2 = new Point(p1.x + width, p1.y);
            p3 = new Point(p1.x + (width / 2), p1.y - width);
        }
        else if (direction == Direction.SOUTH) {
            p2 = new Point(p1.x + width,p1.y);
            p3 = new Point(p1.x + (width / 2), p1.y + width);
        }
        else if (direction == Direction.EAST) {
            p2 = new Point(p1.x, p1.y + width);
            p3 = new Point(p1.x - width, p1.y + (width / 2));
        }
        else if (direction == Direction.WEST) {
            p2 = new Point(p1.x, p1.y + width);
            p3 = new Point(p1.x + width, p1.y + (width / 2));
        }

        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);

        return path;

    }
}
