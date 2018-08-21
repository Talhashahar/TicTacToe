package com.games.tictactoe.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {
    final String CROSS_WON = "The X won the game";
    final String CIRCLE_WON = "The O won the game";
    final String TIE = "The game end with a tie";
    final int CROSS_CHOSE = 1;
    final int CIRCLE_CHOSE = 11;
    final int CROSS_WON_MATCH = 1;
    final int CIRCLE_WON_MATCH = 0;
    final int NO_ONE_WON_MATCH = -1;
    final int GAME_END_WITH_TIE = 2;

    private boolean isAnyoneWon = false;
    private boolean isAgainstPc = true;

    private int mNowTurn = 1; //1 - x turn
    private int mNumberOfPlays = 0;
    private int[] mWinList = new int[9];

    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImage4;
    private ImageView mImage5;
    private ImageView mImage6;
    private ImageView mImage7;
    private ImageView mImage8;
    private ImageView mImage9;
    private ImageView mNowTurnImage;

    private DrawerLayout mDrawerLayout;
    private ImageButton mMenuButton;
    private AppCompatSeekBar mVolumeSeekBar;
    private AudioManager mAudioManager;
    private Switch mSwitchFxSounde;
    private MediaPlayer mClickSound;
    private MediaPlayer mBackgroundMusic;


    private TextView mCrossScore;
    private TextView mCircleScore;

    private Random mRandom = new Random();
    private ArrayList<ImageView> mButtonsImages = new ArrayList<>();

    SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.main_layout);

        mSharedPref = getPreferences(Context.MODE_PRIVATE);
        mBackgroundMusic = MediaPlayer.create(this, R.raw.game_music);

        initVolumeSeekBar();


        Intent i = getIntent();
        isAgainstPc = i.getBooleanExtra("isAgainstPc", false);

        Arrays.fill(mWinList, 0);

        mImage1 = (ImageView) findViewById(R.id.image1);
        mImage2 = (ImageView) findViewById(R.id.image2);
        mImage3 = (ImageView) findViewById(R.id.image3);
        mImage4 = (ImageView) findViewById(R.id.image4);
        mImage5 = (ImageView) findViewById(R.id.image5);
        mImage6 = (ImageView) findViewById(R.id.image6);
        mImage7 = (ImageView) findViewById(R.id.image7);
        mImage8 = (ImageView) findViewById(R.id.image8);
        mImage9 = (ImageView) findViewById(R.id.image9);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenuButton = (ImageButton) findViewById(R.id.menu_button);
        mSwitchFxSounde = (Switch) findViewById(R.id.on_and_off_music_button);

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START, true);
            }
        });


        mButtonsImages.add(mImage1);
        mButtonsImages.add(mImage2);
        mButtonsImages.add(mImage3);
        mButtonsImages.add(mImage4);
        mButtonsImages.add(mImage5);
        mButtonsImages.add(mImage6);
        mButtonsImages.add(mImage7);
        mButtonsImages.add(mImage8);
        mButtonsImages.add(mImage9);

        mNowTurnImage = (ImageView) findViewById(R.id.now_turn_image);

        mCircleScore = (TextView) findViewById(R.id.circle_score);
        mCrossScore = (TextView) findViewById(R.id.cross_score);

        ((TextView)findViewById(R.id.circle_legacy_score)).setText(String.valueOf(mSharedPref.getInt(CIRCLE_WON, 0)));
        ((TextView)findViewById(R.id.cross_legacy_score)).setText(String.valueOf(mSharedPref.getInt(CROSS_WON, 0)));

        mImage1.setOnClickListener(this);
        mImage2.setOnClickListener(this);
        mImage3.setOnClickListener(this);
        mImage4.setOnClickListener(this);
        mImage5.setOnClickListener(this);
        mImage6.setOnClickListener(this);
        mImage7.setOnClickListener(this);
        mImage8.setOnClickListener(this);
        mImage9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mNumberOfPlays++;
        try {
            if(mSwitchFxSounde.isChecked()) {
                mClickSound = MediaPlayer.create(this, R.raw.click_effect);
                mClickSound.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        playNowTurn(v);
        mButtonsImages.remove(v); // for against pc using
        v.setClickable(false);
        if (!isAnyoneWon && isAgainstPc) {
            Handler handler = new Handler();
            setImagesClickable(false);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerPlay();
                    setImagesClickable(true);
                }
            }, 1000);

        }

    }

    private void initVolumeSeekBar() {
        try {
            mBackgroundMusic.setLooping(true);
            mBackgroundMusic.start();
            mVolumeSeekBar = (AppCompatSeekBar) findViewById(R.id.seekbar);
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            mVolumeSeekBar.setMax(mAudioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            mVolumeSeekBar.setProgress(mAudioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            mVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playNowTurn(View v) {
        if (mNowTurn == 1) {
            ((ImageView) v).setImageDrawable(getDrawable(R.drawable.x));
            mNowTurnImage.setImageDrawable(getDrawable(R.drawable.o));
            isAnyPlayerWin(v);
            mNowTurn = 0;
        } else {
            ((ImageView) v).setImageDrawable(getDrawable(R.drawable.o));
            mNowTurnImage.setImageDrawable(getDrawable(R.drawable.x));
            isAnyPlayerWin(v);
            mNowTurn = 1;
        }
    }

    private void isAnyPlayerWin(View v) {
        String spots = (String) v.getTag();
        for (int i = 0; i < spots.length() && !isAnyoneWon; i++) {
            int spot = Character.getNumericValue(spots.charAt(i));
            if (mNowTurn == 1) {
                mWinList[spot] += CROSS_CHOSE;
                if (mWinList[spot] == 3) {
                    openFinishDialog(CROSS_WON, CROSS_WON_MATCH);
                }
            } else {
                mWinList[spot] += CIRCLE_CHOSE;
                if (mWinList[spot] == 33) {
                    openFinishDialog(CIRCLE_WON, CIRCLE_WON_MATCH);
                }
            }
        }
        if (mNumberOfPlays == 9 && !isAnyoneWon) {
            openFinishDialog(TIE, GAME_END_WITH_TIE);
        }

    }

    private void openFinishDialog(final String winnerText, final int winner) {
        isAnyoneWon = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(winnerText)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int value = mSharedPref.getInt(winnerText, 0);
                        mSharedPref.edit().putInt(winnerText, value  + 1).apply();
                        resetTheGame(winner);
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void computerPlay() {
        int index = mRandom.nextInt(mButtonsImages.size());
        ImageView image = mButtonsImages.get(index);
        mNumberOfPlays++;
        playNowTurn(image);
        image.setClickable(false);
        mButtonsImages.remove(index);
    }

    private void resetTheGame(int winner) {
        mImage1.setImageResource(android.R.color.transparent);
        mImage2.setImageResource(android.R.color.transparent);
        mImage3.setImageResource(android.R.color.transparent);
        mImage4.setImageResource(android.R.color.transparent);
        mImage5.setImageResource(android.R.color.transparent);
        mImage6.setImageResource(android.R.color.transparent);
        mImage7.setImageResource(android.R.color.transparent);
        mImage8.setImageResource(android.R.color.transparent);
        mImage9.setImageResource(android.R.color.transparent);

        mNowTurnImage.setImageDrawable(getDrawable(R.drawable.x));

        mButtonsImages.clear(); //using for against pc

        mButtonsImages.add(mImage1); //using for against pc
        mButtonsImages.add(mImage2); //using for against pc
        mButtonsImages.add(mImage3); //using for against pc
        mButtonsImages.add(mImage4); //using for against pc
        mButtonsImages.add(mImage5); //using for against pc
        mButtonsImages.add(mImage6); //using for against pc
        mButtonsImages.add(mImage7); //using for against pc
        mButtonsImages.add(mImage8); //using for against pc
        mButtonsImages.add(mImage9); //using for against pc

        mImage1.setClickable(true);
        mImage2.setClickable(true);
        mImage3.setClickable(true);
        mImage4.setClickable(true);
        mImage5.setClickable(true);
        mImage6.setClickable(true);
        mImage7.setClickable(true);
        mImage8.setClickable(true);
        mImage9.setClickable(true);

        mNowTurn = 1;
        Arrays.fill(mWinList, 0);

        mNumberOfPlays = 0;

        if (winner == CROSS_WON_MATCH) {
            int score = Integer.valueOf(mCrossScore.getText().toString());
            score++;
            mCrossScore.setText(String.valueOf(score));
        } else if (winner == CIRCLE_WON_MATCH) {
            int score = Integer.valueOf(mCircleScore.getText().toString());
            score++;
            mCircleScore.setText(String.valueOf(score));
        }
        isAnyoneWon = false;
    }

    //for against pc using
    private void setImagesClickable(boolean clickable) {
        for (ImageView image : mButtonsImages) {
            image.setClickable(clickable);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBackgroundMusic.stop();
    }

   @Override
    protected void onResume() {
       mBackgroundMusic = MediaPlayer.create(this, R.raw.game_music);
       mBackgroundMusic.start();
       super.onResume();
    }

    @Override
    public void onBackPressed() {
        new ExitGameDialog.Builder()
                .addBackToMenuButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, PreMainActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addQuitGameButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .addResetMatchButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetTheGame(NO_ONE_WON_MATCH);
                    }
                })
                .show(MainActivity.this);
    }
}
