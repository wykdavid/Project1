package com.example.administrator.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Administrator on 2017/4/17.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable,android.view.View.OnTouchListener{
    private Bitmap background;
    private  Bitmap enemy;
    private Bitmap player;
    private  Bitmap bullet;
    private  Bitmap boom;
    private int screenwidth;
    private int screenheight;
    private Bitmap erjihuancun;
    private ArrayList<gameimage> gamepicture =new <gameimage>ArrayList();
    private ArrayList<gameimage> bulletarray =new <gameimage>ArrayList();

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        this.setOnTouchListener(this);
    }
    private void init(){
        background= BitmapFactory.decodeResource(getResources(),R.drawable.background);
        enemy=BitmapFactory.decodeResource(getResources(),R.drawable.boosbullet);
        player=BitmapFactory.decodeResource(getResources(),R.drawable.bullet);
        bullet=BitmapFactory.decodeResource(getResources(),R.drawable.bullet_enemy);
        boom=BitmapFactory.decodeResource(getResources(),R.drawable.boom);
        erjihuancun=Bitmap.createBitmap(screenwidth,screenheight, Bitmap.Config.ARGB_8888);
        gamepicture.add(new background(background));
        gamepicture.add(new enemy(enemy,boom));
        gamepicture.add(new player(player));
        //bulletarray.add(new bullet(bullet, new player(player)));


    }


    public interface gameimage{
        public Bitmap getBitmap();
        public float getx();
        public float gety();
    }
    private  class bullet implements gameimage{
        private Bitmap bullet;
        private player myplayer;
        //private Bitmap newbullet=null;
        private  float x;
        private  float y;
        //private  int w;
        //private  int h;
        private bullet(Bitmap bullet,player p){
            this.bullet=bullet;
            myplayer=p;
            //w=bullet.getWidth();
            //h=bullet.getHeight();
            //newbullet=Bitmap.createBitmap(bullet,0,0,w,h);
            x=p.getx()+(p.getW()/2)-10;
            y=p.gety()-bullet.getHeight();
        }


        public float getx() {
            return x;
        }


        public float gety() {
            return y;
        }
        public int getw(){
            return bullet.getWidth();
        }
        public int geth(){
            return bullet.getHeight();
        }


        public Bitmap getBitmap() {

            y=y-60;
            if(y<-bullet.getHeight()){
                bulletarray.remove(bullet);
            }
            return bullet;



        }
    }
    private class player implements gameimage{
        private Bitmap player;
        private Bitmap newplayer;
        private  float x;
        private  float y;
        private  int w;
        private  int h;
        private player(Bitmap player){
            this.player=player;
            newplayer=Bitmap.createBitmap(player,0,0,player.getWidth(),player.getHeight());
            x=screenwidth/2;
            y=screenheight-player.getHeight()-30;
            w=player.getWidth();
            h=player.getHeight();
        }


        public Bitmap getBitmap() {
            Bitmap bitmap =newplayer;
            return bitmap;
        }


        public float getx() {
            return x;
        }

        public float gety() {
            return y;
        }
        public void setX(float x){
            this.x=x;
        }
        public void setY(float y){
            this.y=y;
        }
        public boolean select(float a,float b){
            if(a>x&&b>y&&a<x+w&&b<y+h){
                return true;
            }
            else{
                return false;
            }
        }
        public int getW(){
            return w;
        }
        public  int getH(){
            return  h;
        }
    }


    private class enemy implements gameimage{
        private  Bitmap enemy;
        //private  Bitmap newenemy=null;
        private List<Bitmap> e=new ArrayList<Bitmap>();
        private List<Bitmap> booms=new ArrayList<Bitmap>();
        private int x;
        private int y;
        private int w;
        private int h;
        private boolean state=false;
        private enemy(Bitmap enemy,Bitmap boom){
            this.enemy=enemy;
            e.add(Bitmap.createBitmap(enemy,0,0,enemy.getWidth(),enemy.getHeight()));
            booms.add(Bitmap.createBitmap(boom,0,0,boom.getWidth()/7,boom.getHeight()));
            booms.add(Bitmap.createBitmap(boom,(boom.getWidth()/7)*1,0,boom.getWidth()/7,boom.getHeight()));
            booms.add(Bitmap.createBitmap(boom,(boom.getWidth()/7)*2,0,boom.getWidth()/7,boom.getHeight()));
            booms.add(Bitmap.createBitmap(boom,(boom.getWidth()/7)*3,0,boom.getWidth()/7,boom.getHeight()));
            booms.add(Bitmap.createBitmap(boom,(boom.getWidth()/7)*4,0,boom.getWidth()/7,boom.getHeight()));
            booms.add(Bitmap.createBitmap(boom,(boom.getWidth()/7)*5,0,boom.getWidth()/7,boom.getHeight()));
            booms.add(Bitmap.createBitmap(boom,(boom.getWidth()/7)*6,0,boom.getWidth()/7,boom.getHeight()));
            Random ran=new Random();
            y=-enemy.getHeight();
            x=ran.nextInt(screenwidth-enemy.getWidth());
            w=enemy.getWidth();
            h=enemy.getHeight();

        }




        int index=0;
        public Bitmap getBitmap() {


            Bitmap bitmaps=e.get(index);
            index++;
            if(index==7&&state){
                gamepicture.remove(this);
            }
            if(index==e.size()){
                index=0;
            }

            y=y+20;





            if(y>screenheight){
                gamepicture.remove(this);
            }



            return bitmaps;
        }


        public float getx() {
            return x;
        }


        public float gety() {
            return y;
        }
        public void gethit(ArrayList<gameimage> b){
            if(!state){
                for (gameimage image:(List<gameimage>)bulletarray.clone()){
                    if(image.getx()>x && image.gety()>y && image.getx()<x+w && image.gety()<y+h){
                        bulletarray.remove(image);

                        state=true;

                        e=booms;
                        break;


                    }



                }
            }



        }

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

        public float getx() {
            return 0;
        }


        public float gety() {
            return 0;
        }
    }
    private boolean state=false;
    private SurfaceHolder holder;



    public void run() {
        Paint p=new Paint();
        int enemy_number=0;
        int bullet_number=0;
        try {
            while(state){
                if(selectplayer!=null){
                    bullet_number++;
                    if(bullet_number==10){
                        bulletarray.add(new bullet(bullet,selectplayer));
                        bullet_number=0;
                    }

                }



                Canvas newcanvas=new Canvas(erjihuancun);
                for (gameimage image : (List<gameimage>)gamepicture.clone()){
                    if(image instanceof enemy){
                        ((enemy)image).gethit(bulletarray);
                    }
                    newcanvas.drawBitmap(image.getBitmap(),image.getx(),image.gety(),p);
                }
                for (gameimage image:(List<gameimage>)bulletarray.clone()){
                    newcanvas.drawBitmap(image.getBitmap(),image.getx(),image.gety(),p);
                }
                if(enemy_number==30){
                    gamepicture.add(new enemy(enemy,boom));
                    enemy_number=0;
                }
                enemy_number++;






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
    player selectplayer;


    public boolean onTouch(View v, MotionEvent event){
        Log.i("app tag","run to here");
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            for(gameimage playership:gamepicture){
                if(playership instanceof player){
                    player p=(player) playership;
                    if(p.select(event.getX(),event.getY())){
                        selectplayer=p;
                        Log.i("app.tag","select");
                    }
                    break;
                }else {
                    selectplayer=null;
                    Log.i("app.tag"," not select");
                }

            }

            }else if(event.getAction()==MotionEvent.ACTION_MOVE){
             if(selectplayer!=null){
                selectplayer.setX( event.getX()-((selectplayer.w)/2));
                selectplayer.setY( event.getY()-((selectplayer.h)/2));
            }

        }
        else if (event.getAction()==MotionEvent.ACTION_UP){
            selectplayer=null;
        }

        return true;

    }



}
