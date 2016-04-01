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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int pressX; //x coordinate of touch
    int pressY; //y coordinate of touch
    int releaseX; //x coordinate of release
    int releaseY; //y coordinate of release
    int width;
    int height;
    int quarterWidth;
    int quarterHeight;
    ImageView iv;
    Canvas c;
    Bitmap b;
    Bitmap[] bArray;
    int[] bArrayInt = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    int[] solvedArrayInt = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    TextView solvedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get text view to display if the puzzle was solved
        solvedText = (TextView) findViewById( R.id.txtview_solved );

        //create shuffle button
        Button btnShuffle = (Button) findViewById( R.id.btn_shuffle );
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffle();
                drawBitmapArray();
            }
        });

        //use bitmap factory to get the picture
        Bitmap picInit = BitmapFactory.decodeResource(getResources(), R.drawable.shannon);
        Bitmap picLarge = picInit.copy(Bitmap.Config.ARGB_8888, true); //make a copy of the picture that we can edit
        Bitmap pic = picLarge.createScaledBitmap(picLarge, 1000, 1000, false);

        //should scale the image to the size of the screen

        //set image in the view
        iv = (ImageView) findViewById(R.id.imageView);
        //iv.setImageBitmap(pic);

        //get basic information about the picture
        width = pic.getWidth();
        height = pic.getHeight();
        quarterWidth = width / 4;
        quarterHeight = height / 4;

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
        b = Bitmap.createBitmap(height,width, Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
       // c.drawBitmap(p1, 200, 200, null);
        //c.drawBitmap(p2, 200+quarterWidth, 200, null);
        iv.setImageBitmap(b); //draw the bitmap that contains all the other images


        //array to hold all pieces of picture
        bArray  = new Bitmap[]{p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16};

        shuffle();
        drawBitmapArray();

        //iv.getLocationOnScreen();
        int[] coords = {0,0};

        iv.getLocationOnScreen(coords);
        //bArray[i].getLocationOnScreen(coords);
        //Toast.makeText(getBaseContext(), "Coords of imageView are x:" +coords[0] + " y:" + coords[1], Toast.LENGTH_LONG).show();

    }

    //detects touch and release coordinates
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                pressX = x;
                pressY = y;
                //Toast.makeText(getBaseContext(), "Pressed at x=" + pressX + " y=" + pressY, Toast.LENGTH_SHORT).show();

                break;
            } case MotionEvent.ACTION_UP: {
                releaseX = x;
                releaseY = y;
                //Toast.makeText(getBaseContext(), "Released at x=" + releaseX + " y=" + releaseY, Toast.LENGTH_SHORT).show();
                break;
            }
            default:{
                break;
            }

        }
        int firstTile = findTileQuadrant(pressX, pressY); //find first tile clicked
        int secondTile = findTileQuadrant(releaseX, releaseY);

        switchTile(firstTile, secondTile);
        return true;
    }

    //swap touched tile on released tile
    public void switchTile(int tile1, int tile2){
        //switch the tiles in the picture array
        tile1 = tile1 - 1;
        tile2 = tile2 -1;

        Bitmap temp = bArray[tile1];
        bArray[tile1] = bArray[tile2];
        bArray[tile2] = temp;

        //switch the tiles in the integer array (used to know if other array is sorted)
        int tempInt = bArrayInt[tile1];
        bArrayInt[tile1] = bArrayInt[tile2];
        bArrayInt[tile2] = tempInt;

        drawBitmapArray();// redraw the array after swapping the pictures
    }

    //find quadrant that x,y coordinate is located in
    public int findTileQuadrant(int xCoord, int yCoord){
        int quadrant;
        int row=1;
        int col=1;

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
        //errorOffset is used to make the click be in the correct spot
        int errorOffsetY = 200;
        if(yCoord>0 && yCoord<quarterHeight +errorOffsetY){
            //in first row
            row=1;
        }
        else if(yCoord>quarterHeight +errorOffsetY && yCoord<quarterHeight*2 +errorOffsetY){
            //in second row
            row=2;
        }
        else if(yCoord>quarterHeight*2 +errorOffsetY && yCoord<quarterHeight*3 +errorOffsetY) {
            //in third row
            row=3;
        }
        else if(yCoord>quarterHeight*3 +errorOffsetY && yCoord<quarterHeight*4 +errorOffsetY){
            //in fourth row
            row=4;
        }

        //determine quadrant from column and row
        int [][] quadMatrix = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}
        };

        quadrant = quadMatrix[row-1][col-1]; //row-1 and col-1 because the 2D array starts at 0

        return quadrant;
    }

    //shuffle array
    public void shuffle(){
        int numElements = bArray.length;

        for (int i=0; i< numElements; i++){
            int s = i + (int)(Math.random() * ( numElements -i) );

            //shuffle the tiles in the bitmap array
            Bitmap temp = bArray[s];
            bArray[s] = bArray[i];
            bArray[i] = temp;

            //shuffle the tiles in the integer array (used to know if other array is sorted)
            int tempInt = bArrayInt[s];
            bArrayInt[s] = bArrayInt[i];
            bArrayInt[i] = tempInt;
        }

    }

    //draw bitmaps from array
    public void drawBitmapArray(){
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
            c.drawBitmap(bArray[i], offsetX, quarterHeight * 3, null);
        }
        iv.setImageBitmap(b);
        isSolved(); //check to see if the puzzle was solved after drawing
    }

    //check bitmap array order to see if puzzle has been solved
    public void isSolved(){
        boolean solved = true;

        for(int i=0; i<bArrayInt.length; i++){
            if( bArrayInt[i] != solvedArrayInt[i]){
                solved = false;
            }
        }
        if(solved){
            solvedText.setText("You solved the puzzle!");
        }
    }

}
