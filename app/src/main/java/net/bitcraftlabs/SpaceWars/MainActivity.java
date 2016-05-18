package net.bitcraftlabs.SpaceWars;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    private Thread timingThread;
    private boolean StillWanted;

    int btn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button up = (Button) findViewById(R.id.upBtn);
        up.setOnTouchListener(this);
        Button down = (Button) findViewById(R.id.downBtn);
        down.setOnTouchListener(this);
        Button left = (Button) findViewById(R.id.leftBtn);
        left.setOnTouchListener(this);
        Button right = (Button) findViewById(R.id.rightBtn);
        right.setOnTouchListener(this);

        t.start();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("STATUS","in onResume");
        // create timing thread
        timingThread = new Thread() {
            public void run() {
                try {
                    while (StillWanted) {
                        sleep(60);
                        View v = findViewById(R.id.table);
                        //System.out.println("Timing thread "+getId()+ " View "+v.toString());
                        v.postInvalidate();
                    }
                } catch (InterruptedException e) {
                    Log.d("STATUS","Thread "+getId()+ "interrupted");
                    e.printStackTrace();
                }

            }


        };
        StillWanted = true;
        timingThread.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("STATUS","in onPause ");
        StillWanted = false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void regenerate() {
        View v = findViewById(R.id.table);
        v.postInvalidate();
    }

    Thread t = new Thread() {

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(80);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Do dah button stuff
                            switch (btn) {
                                case 1: //up
                                    SpaceWars.cy -= 20;
                                    SpaceWars.dr = SpaceWars.Direction.NORTH;
                                    regenerate();
                                    break;
                                case 2: //down
                                    SpaceWars.cy += 20;
                                    SpaceWars.dr = SpaceWars.Direction.SOUTH;
                                    regenerate();
                                    break;
                                case 3: //left
                                    SpaceWars.cx -= 20;
                                    SpaceWars.dr = SpaceWars.Direction.EAST;
                                    regenerate();
                                    break;
                                case 4: //right
                                    SpaceWars.cx += 20;
                                    SpaceWars.dr = SpaceWars.Direction.WEST;
                                    regenerate();
                                    break;
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {

            }
        }
    };

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.upBtn:
                    Log.d("ACTION", "UP BUTTON");
                    btn = 1;
                    break;
                case R.id.downBtn:
                    Log.d("ACTION", "DOWN BUTTON");
                    btn = 2;
                    break;
                case R.id.leftBtn:
                    Log.d("ACTION", "LEFT BUTTON");
                    btn = 3;
                    break;
                case R.id.rightBtn:
                    Log.d("ACTION", "RIGHT BUTTON");
                    btn = 4;
                    break;
            }
        } else {
            btn = 0;
        }
        return true;
    }
}
