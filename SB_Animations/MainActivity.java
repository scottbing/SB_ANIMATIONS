package drawable;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AnimationDrawable frameAnimation;
    private ImageView imageView;
    private TextView textView;
    private ViewGroup contentView;
    private Button startAnimationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = new LinearLayout( this);

        // show start animattion button
        startAnimationButton = new Button( this);
        startAnimationButton.setVisibility(View.VISIBLE);
        startAnimationButton.setText("Start the Game");
        startAnimationButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT));
        //make this button add a new item when clicked
        startAnimationButton.setOnClickListener(startClickedListener);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(startAnimationButton);

        setContentView(linearLayout);

    }

    private View.OnClickListener startClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("addClick Listener", "add clicked");

            // hide the start button
            startAnimationButton.setVisibility(View.GONE);

            // setup the animation view
            contentView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content))
                    .getChildAt(0);
            imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(R.drawable.smoke_animation);
            imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(R.drawable.bug_animation);

            frameAnimation = (AnimationDrawable) imageView.getDrawable();
            textView = new TextView(MainActivity.this);
            textView.setText("0");

            contentView.addView(textView);
            contentView.addView(imageView);

        }
    };

    private View.OnTouchListener imageViewTouched = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if(frameAnimation.isRunning())
                    frameAnimation.stop();
                else
                    frameAnimation.start();
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //start  animation when finger  down
                if (frameAnimation.isRunning())
                    frameAnimation.stop();
                else {
                    ObjectAnimator bugMover = ObjectAnimator.ofFloat(imageView, "translationY", 0f);
                    bugMover.setDuration(2000);
                    bugMover.start();
                    frameAnimation.start();
                }
                //count from  0 to  100
                ValueAnimator animator = ValueAnimator.ofInt(0,30);
                //do it 1000  milliseconds
                animator.setDuration(1000);
                animator.addUpdateListener(animatorUpdated);
                animator.start();

                return true;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }


    private ValueAnimator.AnimatorUpdateListener animatorUpdated =
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    textView.setText(valueAnimator.getAnimatedValue().toString());
                }

            };
}
