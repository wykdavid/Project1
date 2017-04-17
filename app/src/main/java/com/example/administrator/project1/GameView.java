package com.example.administrator.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/4/17.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    private Bitmap background;
    private int screenwidth;
    private int screenheight;
    private Bitmap erjihuancun;
    private List<gameimage> gamepicture =new <gameimage>ArrayList();

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
    }
    private void init(){
        background= BitmapFactory.decodeResource(getResources(),R.drawable.background);
        erjihuancun=Bitmap.createBitmap(screenwidth,screenheight, Bitmap.Config.ARGB_8888);
        gamepicture.add(new background(background));


    }


    public interface gameimage{
        public Bitmap getBitmap();
        public int getx();
        public int gety();
    }
    private  class background implements gameimage{
        private Bitmap background;
        private Bitmap newbitmap=null;
        private background(Bitmap background){
            this.background=background;
            newbitmap=Bitmap.createBitmap(screenwidth,screenheight,Bitmap.Config.ARGB_8888);
        }

        private int height=0;
        public Bitmap getBitmap(){
            Paint p=new Paint();

            Canvas canvas=new Canvas(newbitmap);
            canvas.drawBitmap(background,new Rect(0,0,background.getWidth(),background.getHeight()),new Rect(0,height++,screenwidth,screenheight+height),p);
            canvas.drawBitmap(background,new Rect(0,0,background.getWidth(),background.getHeight()),new Rect(0,-screenheight+height,screenwidth,height),p);
            height++;
            if(height==screenheight){
                height=0;
            }
            return newbitmap;
        }

        public int getx() {
            return 0;
        }


        public int gety() {
            return 0;
        }
    }
    private boolean state=false;
    private SurfaceHolder holder;



    public void run() {
        Paint p=new Paint();
        try {
            while(state){
                Canvas newcanvas=new Canvas(erjihuancun);
                for (gameimage image : gamepicture){
                    newcanvas.drawBitmap(image.getBitmap(),image.getx(),image.gety(),p);
                }



                Canvas canvas= holder.lockCanvas();
                canvas.drawBitmap(erjihuancun,0,0,p);
                holder.unlockCanvasAndPost(canvas);
                Thread.sleep(50);
            }
            
        }catch (Exception e){

        }

    }
    public void surfaceCreated(SurfaceHolder holder){


        
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        state=false;
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenwidth=width;
        screenheight=height;
        init();
        this.holder=holder;
        state=true;
        new Thread(this).start();

        
    }



}
