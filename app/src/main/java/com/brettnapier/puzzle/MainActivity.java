//
//
//Brett Napier
//Puzzle Assignment
//CSC309 at EKU
//3-29-2016
//
//

package com.brettnapier.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int x; //x coordinate of touch
    int y; //y coordinate of touch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //use bitmap factory to get the picture
        Bitmap picInit = BitmapFactory.decodeResource(getResources(), R.drawable.shannon);
        Bitmap pic = picInit.copy(Bitmap.Config.ARGB_8888, true); //make a copy of the picture that we can edit

        //should scale the image to the size of the screen

        //set image in the view
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        //iv.setImageBitmap(pic);

        //get basic information about the picture
        final int width = pic.getWidth();
        final int height = pic.getHeight();
        final int quarterWidth = width / 4;
        final int quarterHeight = height / 4;

        /*
        //declare arrays to hold blocks of the picture
        int [] p1 = new int[quarterWidth*quarterHeight];
        int [] p2 = new int[quarterWidth*quarterHeight];
        int [] p3 = new int[quarterWidth*quarterHeight];
        int [] p4 = new int[quarterWidth*quarterHeight];
        int [] p5 = new int[quarterWidth*quarterHeight];
        int [] p6 = new int[quarterWidth*quarterHeight];
        int [] p7 = new int[quarterWidth*quarterHeight];
        int [] p8 = new int[quarterWidth*quarterHeight];
        int [] p9 = new int[quarterWidth*quarterHeight];
        int [] p10 = new int[quarterWidth*quarterHeight];
        int [] p11 = new int[quarterWidth*quarterHeight];
        int [] p12 = new int[quarterWidth*quarterHeight];
        int [] p13 = new int[quarterWidth*quarterHeight];
        int [] p14 = new int[quarterWidth*quarterHeight];
        int [] p15 = new int[quarterWidth*quarterHeight];
        int [] p16 = new int[quarterWidth*quarterHeight];

        //copy pieces from picture
        //1st row
        pic.getPixels(p1, 0, quarterWidth, 0, 0, quarterWidth, quarterHeight);
        pic.getPixels(p2, 0, quarterWidth, quarterWidth, 0, quarterWidth, quarterHeight);
        pic.getPixels(p3, 0, quarterWidth, quarterWidth*2, 0, quarterWidth, quarterHeight);
        pic.getPixels(p4, 0, quarterWidth, quarterWidth*3, 0, quarterWidth, quarterHeight);
        //2nd row
        pic.getPixels(p5, 0, quarterWidth, 0, quarterHeight, quarterWidth, quarterHeight);
        pic.getPixels(p6, 0, quarterWidth, quarterWidth, quarterHeight, quarterWidth, quarterHeight);
        pic.getPixels(p7, 0, quarterWidth, quarterWidth*2, quarterHeight, quarterWidth, quarterHeight);
        pic.getPixels(p8, 0, quarterWidth, quarterWidth*3, quarterHeight, quarterWidth, quarterHeight);
        //3rd row
        pic.getPixels(p9, 0, quarterWidth, 0, quarterHeight*2, quarterWidth, quarterHeight);
        pic.getPixels(p10, 0, quarterWidth, quarterWidth, quarterHeight*2, quarterWidth, quarterHeight);
        pic.getPixels(p11, 0, quarterWidth, quarterWidth*2, quarterHeight*2, quarterWidth, quarterHeight);
        pic.getPixels(p12, 0, quarterWidth, quarterWidth*3, quarterHeight*2, quarterWidth, quarterHeight);
        //4th row
        pic.getPixels(p13, 0, quarterWidth, 0, quarterHeight*3, quarterWidth, quarterHeight);
        pic.getPixels(p14, 0, quarterWidth, quarterWidth, quarterHeight*3, quarterWidth, quarterHeight);
        pic.getPixels(p15, 0, quarterWidth, quarterWidth*2, quarterHeight*3, quarterWidth, quarterHeight);
        pic.getPixels(p16, 0, quarterWidth, quarterWidth*3, quarterHeight*3, quarterWidth, quarterHeight);

        //move the pieces we copied
        //pic.setPixels(p16, 0, quarterWidth, quarterWidth*3, quarterHeight*1, quarterWidth, quarterHeight);
        */



        /*
        Here is how the pictures is divided
        |-----------------------|
        | p1  | p2  | p3  | p4  |
        |-----------------------|
        | p5  | p6  | p7  | p8  |
        |-----------------------|
        | p9  | p10 | p11 | p12 |
        |-----------------------|
        | p13 | p14 | p15 | p16 |
        |-----------------------|
        */

        //create new images from 16 pieces of the original picture
        //row1
        Bitmap p1 = Bitmap.createBitmap(pic,0, 0, quarterWidth, quarterHeight);
        Bitmap p2 = Bitmap.createBitmap(pic,quarterWidth, 0, quarterWidth, quarterHeight);
        Bitmap p3 = Bitmap.createBitmap(pic,quarterWidth*2, 0, quarterWidth, quarterHeight);
        Bitmap p4 = Bitmap.createBitmap(pic,quarterWidth*3, 0, quarterWidth, quarterHeight);
        //row2
        Bitmap p5 = Bitmap.createBitmap(pic,0, quarterHeight, quarterWidth, quarterHeight);
        Bitmap p6 = Bitmap.createBitmap(pic,quarterWidth, quarterHeight, quarterWidth, quarterHeight);
        Bitmap p7 = Bitmap.createBitmap(pic,quarterWidth*2, quarterHeight, quarterWidth, quarterHeight);
        Bitmap p8 = Bitmap.createBitmap(pic,quarterWidth*3, quarterHeight, quarterWidth, quarterHeight);
        //row3
        Bitmap p9 = Bitmap.createBitmap(pic,0, quarterHeight*2, quarterWidth, quarterHeight);
        Bitmap p10 = Bitmap.createBitmap(pic,quarterWidth, quarterHeight*2, quarterWidth, quarterHeight);
        Bitmap p11 = Bitmap.createBitmap(pic,quarterWidth*2, quarterHeight*2, quarterWidth, quarterHeight);
        Bitmap p12 = Bitmap.createBitmap(pic,quarterWidth*3, quarterHeight*2, quarterWidth, quarterHeight);
        //row4
        Bitmap p13 = Bitmap.createBitmap(pic,0, quarterHeight*3, quarterWidth, quarterHeight);
        Bitmap p14 = Bitmap.createBitmap(pic,quarterWidth, quarterHeight*3, quarterWidth, quarterHeight);
        Bitmap p15 = Bitmap.createBitmap(pic,quarterWidth*2, quarterHeight*3, quarterWidth, quarterHeight);
        Bitmap p16 = Bitmap.createBitmap(pic,quarterWidth*3, quarterHeight*3, quarterWidth, quarterHeight);

        //draw multiple images to a new bitmap using canvas
        Bitmap b = Bitmap.createBitmap(height,width, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
       // c.drawBitmap(p1, 200, 200, null);
        //c.drawBitmap(p2, 200+quarterWidth, 200, null);
        iv.setImageBitmap(b); //draw the bitmap that contains all the other images


        Bitmap[] bArray  = new Bitmap[]{p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16}; //array to hold all pieces of picture
        //draw 1st row
        for(int i=0; i<5; i++){
            int offsetX = 0 + quarterWidth*i;
            int offsetY = 0 + quarterHeight*i;
            c.drawBitmap(bArray[i], offsetX, 0, null );
        }
        //draw 2nd row
        for(int i=4; i<9; i++){
            int offsetX = 0 + quarterWidth*(i-4);
            int offsetY = 0 + quarterHeight*(i-4);
            c.drawBitmap(bArray[i], offsetX, quarterHeight, null );
        }
        //draw 3rd row
        for(int i=8; i<13; i++){
            int offsetX = 0 + quarterWidth*(i-8);
            int offsetY = 0 + quarterHeight*(i-8);
            c.drawBitmap(bArray[i], offsetX, quarterHeight*2, null );
        }
        //draw 4th row
        for(int i=12; i<16; i++){
            int offsetX = 0 + quarterWidth*(i-12);
            int offsetY = 0 + quarterHeight*(i-12);
            c.drawBitmap(bArray[i], offsetX, quarterHeight*3, null );
        }
        iv.setImageBitmap(b);

    }

    //detects touch and release coordinates
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                Toast.makeText(getBaseContext(), "Released at x=" + x + " y=" + y, Toast.LENGTH_SHORT).show();
                return true;}
            case MotionEvent.ACTION_DOWN: {
                Toast.makeText(getBaseContext(), "Pressed at x=" + x + " y=" + y, Toast.LENGTH_SHORT).show();
            }
            default:{
                return false;
            }
        }
    }

    //find quadrant that x,y coordinate is located in
    public int findTileQuadrant(int quarterHeight, int quarterWidth, int xCoord, int yCoord){
        int quadrant;
        int row=0;
        int col=0;

        //determine column of x coordinate
        if(xCoord>0 && xCoord<quarterWidth){
            //in first column
            col=1;
        }
        else if(xCoord>quarterWidth && xCoord<quarterWidth*2){
            //in second column
            col=2;
        }
        else if(xCoord>quarterWidth*2 && xCoord<quarterWidth*3) {
            //in third column
            col=3;
        }
        else if(xCoord>quarterWidth*3 && xCoord<quarterWidth*4){
            //in fourth column
            col=4;
        }

        //determine row of y coordinate
        if(yCoord>0 && yCoord<quarterHeight){
            //in first row
            row=1;
        }
        else if(yCoord>quarterHeight && yCoord<quarterHeight*2){
            //in second row
            row=2;
        }
        else if(yCoord>quarterHeight*2 && yCoord<quarterHeight*3) {
            //in third row
            row=3;
        }
        else if(yCoord>quarterHeight*3 && yCoord<quarterHeight*4){
            //in fourth row
            row=4;
        }

        //determine quadrant from column and row
        int [][] quadMatrix = new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}
        };

        quadrant = quadMatrix[row][col];

        return quadrant;
    }

}
