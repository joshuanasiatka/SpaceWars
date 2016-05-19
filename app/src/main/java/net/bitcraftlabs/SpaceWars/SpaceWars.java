package net.bitcraftlabs.SpaceWars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class SpaceWars extends View {
//    float cx = 0;  // x, y coordinates of center of Ship.
//    float cy = 0;
//    float r = 50; // pixel length of radius of Ship
//    int color = Color.RED;
//    int color = ContextCompat.getColor(context, R.color.dark);
//    int myColor = context.getResources().getColor(edu.fitchburgstate.staylor.R.color.white);

    //Canvas
    public static int w, h;

    Paint paint = new Paint();
    public static int vx = 0;
    public static int vy = 0;
    public static int cx = 10;
    public static int cy = 10;
    public static int vel = 10;
    int r  = 100;

    //Ship
    public static Direction dr = Direction.NORTH;
    boolean setup = true;
    ArrayList<Missile> ml = new ArrayList<Missile>();
    ArrayList<Missile> MTemp = new ArrayList<Missile>();

    boolean dualFire = true;

    //Missile
    int mx = 0;
    int my = 0;
    int mr = 0;
    int mv = 10;
    public static boolean missileFlag = false;

    public static Missile md = new Missile();

    public SpaceWars(Context context) {
        super(context);
    }

    public SpaceWars(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        w = getWidth();
        h = getHeight();

        if (setup) {
            cx = (w / 2) - (r / 2);
            cy = h;

            setup = false;
        }

        Point pt = new Point();
        pt.set(cx,cy);

        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.RED);
        Path path = tri(pt, r);
        c.drawPath(path, p);
        // play with the physics
        cx += vx;
        cy += vy;

        //Missile
        paint.setColor(Color.WHITE);
        fireAllDaThings();

        ml.removeAll(MTemp);

        for (Missile m: ml){
            if (m.destroyed) {
                MTemp.add(m);
            }
            paint.setColor(Color.WHITE);
            c.drawCircle(m.mx, m.my, m.mr, paint);

            if (dualFire) {
                c.drawCircle(m.mx+r, m.my, m.mr, paint);
            }

            m.w = w;
            m.h = h;
            m.missileFire();
        }
        //right bounce
        if (cx + r > w) {
            vx = 0;
            cx -= w - vel;
        }
        if (cx + r < r) {// left bounce
            vx = 0;
            cx += w - vel;
        }
        if (cy < r) { // top bounce
            vy = 0;
            cy += h - vel;
        }
        if (cy + r > h + r) { // bottom bounce
            vy = 0;
            cy -= h - vel;
        }
    }

    public enum Direction {
        NORTH, SOUTH, EAST, WEST, NONE;
    }

    public Path tri (Point p1, int width){

        Point p2 = null, p3 = null;

        if (dr == Direction.NORTH) {
            p2 = new Point(p1.x + width, p1.y);
            p3 = new Point(p1.x + (width / 2), p1.y - width);
        }
        else if (dr == Direction.SOUTH) {
            p2 = new Point(p1.x + width,p1.y);
            p3 = new Point(p1.x + (width / 2), p1.y + width);
        }
        else if (dr == Direction.EAST) {
            p2 = new Point(p1.x, p1.y + width);
            p3 = new Point(p1.x - width, p1.y + (width / 2));
        }
        else if (dr == Direction.WEST) {
            p2 = new Point(p1.x, p1.y + width);
            p3 = new Point(p1.x + width, p1.y + (width / 2));
        }

        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);

        return path;

    }

    public void fireAllDaThings() {
        if (missileFlag) {
            int oldR = r;
            if (dualFire) r = 0;
            Missile m = new Missile();
            switch (dr) {
                case NORTH:
                    m.mx = cx + r/2;
                    m.my = cy - r;
                    m.mr = 5;
                    m.mDr = Missile.Direction.NORTH;
                    missileFlag = false;
                    break;
                case SOUTH:
                    m.mx = cx + r/2;
                    m.my = cy + r;
                    m.mr = 5;
                    m.mDr = Missile.Direction.SOUTH;
                    missileFlag = false;
                    break;
                case EAST:
                    m.mx = cx - r;
                    m.my = cy + r/2;
                    m.mr = 5;
                    m.mDr = Missile.Direction.EAST;
                    missileFlag = false;
                    break;
                case WEST:
                    m.my = cy + r/2;
                    m.mx = cx + r;
                    m.mr = 5;
                    m.mDr = Missile.Direction.WEST;
                    missileFlag = false;
                    break;

            }
            ml.add(m);
            r = oldR;
        }
    }
}
