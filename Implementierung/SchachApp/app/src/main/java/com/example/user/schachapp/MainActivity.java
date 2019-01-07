package com.example.user.schachapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView im = (ImageView) findViewById(R.id.chess);


        im.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                TextView txt = (TextView) findViewById(R.id.textView);

                int totalX = (int) event.getX();
                int totalY = (int) event.getY();
                int[] viewCords = new int[2];
                im.getLocationOnScreen(viewCords);
                int chessX = (int) (((totalX - viewCords[0]) * 8) / 1000);
                int chessY = 8 - ((int) (((totalY - viewCords[1]) * 8) / 1000));
                String chessChar = "Ausserhalb";
                if (chessY > 0 && chessY < 9) {
                    switch (chessX) {
                        case 0:
                            chessChar = "a" + chessY;
                            break;
                        case 1:
                            chessChar = "b" + chessY;
                            break;
                        case 2:
                            chessChar = "c" + chessY;
                            break;
                        case 3:
                            chessChar = "d" + chessY;
                            break;
                        case 4:
                            chessChar = "e" + chessY;
                            break;
                        case 5:
                            chessChar = "f" + chessY;
                            break;
                        case 6:
                            chessChar = "g" + chessY;
                            break;
                        case 7:
                            chessChar = "h" + chessY;
                            break;
                        default:
                            chessChar = "Ausserhalb";
                    }
                }
                txt.setText(chessChar);
                return true;
            }
        });

    }
}
