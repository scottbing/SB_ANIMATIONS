package com.here.android.example.sb_animations;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AnimationDrawable bugAnimation, mothAnimation;
    private ObjectAnimator bugMover, mothMover;
    private ImageView bugView, mothView;
    private TextView textView;
    private ViewGroup contentView;
    private Button startAnimationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = new LinearLayout(this);

        // show start animation button
        startAnimationButton = new Button(this);
        startAnimationButton.setVisibility(View.VISIBLE);
        startAnimationButton.setText("Start the Game");
        startAnimationButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //make this button add a new item when clicked
        startAnimationButton.setOnClickListener(startClickedListener);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(startAnimationButton);

        setContentView(linearLayout);

    }

        private View.OnClickListener startClickedListener = new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {
                Log.d("addClick Listener", "add clicked");

                // hide the start button
                startAnimationButton.setVisibility(View.GONE);

                contentView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content))
                        .getChildAt(0);

                // make the bug view
                bugView = new ImageView(MainActivity.this);
                bugView.setImageResource(R.drawable.bug_animation);
                //bugView.setBackgroundResource(R.drawable.bug_animation);
                bugView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        Log.i("imageviewandontouchlistener", "imageView1 onTouch");
                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            if(bugAnimation.isRunning()) {
                                bugMover.cancel();
                                bugMover.end();
                                //bugAnimation.stop();
                            }
                            else {
                                bugMover.cancel();
                                bugMover.end();
                                //bugAnimation.stop();
                            }
                            return true;
                        }
                        return false;
                    }
                });

                // make the moth view
                mothView = new ImageView(MainActivity.this);
                mothView.setImageResource(R.drawable.moth_animation);
                //mothView.setBackgroundResource(R.drawable.moth_animation);
                mothView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        Log.i("imageviewandontouchlistener", "imageView1 onTouch");
                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            if(mothAnimation.isRunning()) {
                                mothMover.cancel();
                                mothMover.end();
                                //mothAnimation.stop();
                            }
                            else {
                                mothMover.cancel();
                                mothMover.end();
                                //mothAnimation.stop();
                            }
                            return true;
                        }
                        return false;
                    }
                });

                bugAnimation = (AnimationDrawable) bugView.getDrawable();
                mothAnimation = (AnimationDrawable) mothView.getDrawable();

                textView = new TextView(MainActivity.this);
                textView.setText("0");

                contentView.addView(textView);
                contentView.addView(bugView);
                contentView.addView(mothView);

                // get screen dimensions
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                Log.e("Width", "" + width);
                Log.e("height", "" + height);

                //start bug animation
                if (bugAnimation.isRunning())
                    bugAnimation.stop();
                else {
                    bugMover = ObjectAnimator.ofFloat(bugView, "translationY", 1100f);
                    bugMover.setDuration(1000);
                    bugMover.setRepeatCount(ValueAnimator.RESTART);
                    bugMover.setRepeatMode(ValueAnimator.REVERSE);
                    //bugMover.start();
                    //bugAnimation.start();
                }

                //start moth animation
                if (mothAnimation.isRunning())
                    mothAnimation.stop();
                else {
                    mothMover = ObjectAnimator.ofFloat(mothView, "translationX", 500f);
                    mothMover.setDuration(1000);
                    mothMover.setRepeatCount(ValueAnimator.RESTART);
                    mothMover.setRepeatMode(ValueAnimator.REVERSE);
                    //mothMover.start();
                   // mothAnimation.start();
                }

                // group the animators inot an AnimationSet
                AnimatorSet set = new AnimatorSet();
                set.playTogether(bugMover, mothMover);
                set.start();

                //count from  0 to  100
                ValueAnimator animator = ValueAnimator.ofInt(3330,0);
                animator.reverse();  // count down
                //do it 1000  milliseconds
                animator.setDuration(1000);
                animator.addUpdateListener(animatorUpdated);
                animator.start();
            }
        };


    private ValueAnimator.AnimatorUpdateListener animatorUpdated =
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    textView.setText(valueAnimator.getAnimatedValue().toString());

                    // when timer equals zero, stop the game
                    if (Integer.parseInt(valueAnimator.getAnimatedValue().toString())== 0) {
                        mothAnimation.stop();
                        bugAnimation.stop();
                        if(!mothAnimation.isRunning() && !bugAnimation.isRunning()) {
                            Toast.makeText(MainActivity.this, "Game Over - You Won!", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Game Over - You Lost", Toast.LENGTH_LONG).show();
                        }


                    }
                }

            };

}


