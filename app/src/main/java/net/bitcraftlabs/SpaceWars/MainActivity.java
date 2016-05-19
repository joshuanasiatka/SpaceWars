package net.bitcraftlabs.SpaceWars;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.content.*;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    Context context = this;
    MediaPlayer pewpew;

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
        Button fire = (Button) findViewById(R.id.fireBtn);
        fire.setOnTouchListener(this);

        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

        pewpew = MediaPlayer.create(context, R.raw.pewpew);

        t.start();
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
                    Thread.sleep(30);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Do dah button stuff
                            switch (btn) {
                                case 1: //up
                                    SpaceWars.cy -= 20;
                                    SpaceWars.dr = SpaceWars.Direction.NORTH;
                                    break;
                                case 2: //down
                                    SpaceWars.cy += 20;
                                    SpaceWars.dr = SpaceWars.Direction.SOUTH;
                                    break;
                                case 3: //left
                                    SpaceWars.cx -= 20;
                                    SpaceWars.dr = SpaceWars.Direction.EAST;
                                    break;
                                case 4: //right
                                    SpaceWars.cx += 20;
                                    SpaceWars.dr = SpaceWars.Direction.WEST;
                                    break;
                            }
                            regenerate();
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
                case R.id.fireBtn:
                    Log.d("ACTION", "FIRE BUTTON");
                    SpaceWars.missileFlag = true;
                    pewpew.start();
                    break;
            }
        } else {
            btn = 0;
        }
        return false;
    }

    private boolean misPlaying = true;
    private boolean mIsBound = false;
    private static MusicService mServ = new MusicService();

    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (misPlaying) {
            mServ.pauseMusic();
            misPlaying = false;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (!misPlaying) {
            mServ.resumeMusic();
            misPlaying = true;
        }
    }
}
