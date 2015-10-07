package com.larat.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by cory on 10/6/15.
 */
public class BouncingBall extends View {
    private String text;

    public BouncingBall(Context context) {
        super(context);
        this.text = text;
        this.setWillNotDraw(false);
    }

    private float x = new Random().nextFloat();
    private float y = 11;
    private float dx = 10.0f;
    private float dy = 10.0f;
    public float maxX = 1000;
    public float maxY = 1000;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (x + dx > maxX || x + dx < 0)
            dx = -dx;
        if (y + dy > maxY || y + dy < 0)
            dy = -dy;

        x += dx;
        y += dy;

        final Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, 40, paint);
        this.invalidate();
    }



}
