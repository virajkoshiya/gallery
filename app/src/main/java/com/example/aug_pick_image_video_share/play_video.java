package com.example.aug_pick_image_video_share;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class play_video extends AppCompatActivity {

    static int add;
    ImageView restart;
    private RelativeLayout mainvid;
    private SeekBar seekvolume;
    AudioManager audioManager;
    private SeekBar seekbrightness;
    MediaController med;
    int pos;
    static int frd;
    private TextView current, total;
    private SeekBar seek;
    private ImageView play, forwad, backward;
    String pathsinglevideo;
    VideoView exoPlayerView;
    double current_pos, total_duration;
    private int videoTotalTime;
    ArrayList<V_sub_fold_facer> change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        restart=(ImageView)findViewById(R.id.restart);

        mainvid = (RelativeLayout) findViewById(R.id.mainvid);

        exoPlayerView = findViewById(R.id.video_view);
        //   seek.setClickable(false);
        forwad = (ImageView) findViewById(R.id.forwad);
        backward = (ImageView) findViewById(R.id.backward);

        seekvolume = (SeekBar) findViewById(R.id.seekvolume);

        seekbrightness = (SeekBar) findViewById(R.id.seekbrightness);

        current = (TextView) findViewById(R.id.current);
        total = (TextView) findViewById(R.id.total);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentvol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        // v_sub_fold_adapter mathi lidheli tya static declare kreli che
        change = v_sub_fold_adapter.sub_videos;

        String s = change.get(0).getV_s_f_Path();
        Log.e("pathrrr", "onCreate: " + s);


        med = new MediaController(this);

        // intent come from v_sub_fold_adapter

        pathsinglevideo = getIntent().getStringExtra("play");
        pos = getIntent().getIntExtra("position", 0);
        // old method

        //  exoPlayerView.setVideoPath(pathsinglevideo);
        //   exoPlayerView.setMediaController(med);
        //  med.setAnchorView(exoPlayerView);
        //  exoPlayerView.start();


        seek = (SeekBar) findViewById(R.id.seek);

        play = (ImageView) findViewById(R.id.play);


        PlayVideo(pos);


        forwad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pos < (change.size() - 1)) {
                    pos++;
                    PlayVideo(pos);
                } else {
                    pos = 0;
                    PlayVideo(pos);
                }


            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos > 0) {
                    pos--;
                    PlayVideo(pos);
                } else {
                    pos = change.size() - 1;
                    PlayVideo(pos);
                }
            }
        });


        exoPlayerView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                // seek.setMax(exoPlayerView.getDuration());
                setVideoProgress();

            }
        });

        int brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
        seekbrightness.setProgress(brightness);

        seekbrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Context c = getApplicationContext();
                boolean canwrite = Settings.System.canWrite(c);
                if (canwrite) {
                    int sbrightness = progress * 255 / 255;
                    Settings.System.putInt(c.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.
                            System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    Settings.System.putInt(c.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, sbrightness);

                    //   Toast.makeText(c, "if", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    startActivity(intent);
                    // Toast.makeText(c, "else", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekvolume.setMax(maxvol);
        seekvolume.setProgress(currentvol);
        seekvolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//


        mainvid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add=add+1;
                if ( add % 2 == 0){
                    visiblity();
                }else {
                    visiblity();
                }
            }
        });


    }

    public void visiblity() {

        seekbrightness.setVisibility(View.VISIBLE);
        seekvolume.setVisibility(View.VISIBLE);
        seek.setVisibility(View.VISIBLE);
        forwad.setVisibility(View.VISIBLE);
        backward.setVisibility(View.VISIBLE);
        restart.setVisibility(View.VISIBLE);
        play.setVisibility(View.VISIBLE);
        total.setVisibility(View.VISIBLE);
        current.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                seekbrightness.setVisibility(View.GONE);
                seekvolume.setVisibility(View.GONE);
                seek.setVisibility(View.GONE);
                forwad.setVisibility(View.GONE);
                backward.setVisibility(View.GONE);
                restart.setVisibility(View.GONE);
                play.setVisibility(View.GONE);
                total.setVisibility(View.GONE);
                current.setVisibility(View.GONE);
            }
        }, 3000);
    }


    public void PlayVideo(int position) {
        try {
            exoPlayerView.setVideoURI(Uri.parse(change.get(position).getV_s_f_Path()));

            exoPlayerView.requestFocus();
            exoPlayerView.start();
            pos = position;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // seek bar

    public void setVideoProgress() {
        current_pos = exoPlayerView.getCurrentPosition();
        total_duration = exoPlayerView.getDuration();

        try {
            videoTotalTime = Integer.parseInt(Utils.timeConversion((long) total_duration));
        } catch (Exception e) {
            e.printStackTrace();
        }
        total.setText("" + Utils.timeConversion((long) total_duration));
        current.setText("" + Utils.timeConversion((long) current_pos));
        seek.setMax((int) total_duration);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = exoPlayerView.getCurrentPosition();
                    current.setText(Utils.timeConversion((long) current_pos));
                    seek.setProgress((int) current_pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                current_pos = seekBar.getProgress();
                exoPlayerView.seekTo((int) current_pos);

            }
        });

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void play(View view) {

        if (!exoPlayerView.isPlaying()) {
            exoPlayerView.start();
            play.setImageResource(R.drawable.ply);

            new CountDownTimer(exoPlayerView.getDuration(), exoPlayerView.getCurrentPosition()) {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onTick(long millisUntilFinished) {

                    seek.setProgress(exoPlayerView.getCurrentPosition(), true);
                    seek.setProgress(exoPlayerView.getCurrentPosition());

                }

                @Override
                public void onFinish() {

                }

            };
        } else {
            exoPlayerView.pause();
            play.setImageResource(R.drawable.pause);

        }

        //   exoPlayerView.start();

    }

    public void pause(View view) {

        exoPlayerView.pause();

    }

    public void restart(View view) {
        exoPlayerView.stopPlayback();
        exoPlayerView.setVideoPath(pathsinglevideo);
        exoPlayerView.start();


    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    public void play(View view){
//
//        if (exoPlayerView.isPlaying()){
//            exoPlayerView.resume();
//        }
//        else {
//            exoPlayerView.start();
//        }
//
//     //   exoPlayerView.start();
//
////        new CountDownTimer(exoPlayerView.getDuration(), 1) {
////            @RequiresApi(api = Build.VERSION_CODES.N)
////            @Override
////            public void onTick(long millisUntilFinished) {
////
////                seek.setProgress(exoPlayerView.getCurrentPosition(),true);
////
////            }
////
////            @Override
////            public void onFinish() {
////
////
////
////            }
////        }.start();
//
//    }
//
//    public void pause(View view){
//
//        exoPlayerView.pause();
//
//    }
//
//    public void restart(View view){
//        exoPlayerView.stopPlayback();
//        exoPlayerView.setVideoPath(pathsinglevideo);
//        exoPlayerView.start();
//
//
//    }


}
////////////////////////////////////////////////////////////////////////////////////////////////////////////

// own method for forwad and backward

//    int increse ;
//
//                        for (int i = 1; i <change.size() ; i++) {
//
//        String s = change.get(i).getV_s_f_Path();
//
//        Log.e("fromi", "onClick: "+s );
//
//        String ply=pathsinglevideo;
//
//        Log.e("oldpath", "onClick: "+ply );
//
//        if (s == ply){
//
//        int  iincrese=i;
//
//        Log.e("increase", "onClick: "+iincrese );
//
//        //  int kk = increse+1;
//
//        String next = change.get(iincrese).getV_s_f_Path();
//
//        // Log.e("next", "onClick: "+next);
//
//        exoPlayerView.setVideoPath(next);
//        //   exoPlayerView.setMediaController(med);
//        //  med.setAnchorView(exoPlayerView);
//        exoPlayerView.start();
//
//        }
//
//
//
//        }
//
//        Toast.makeText(v.getContext(), "FORWAD", Toast.LENGTH_SHORT).show();