
package day11.shoot;

import javafx.scene.chart.StackedAreaChart;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel {

    //成员变量
    public static BufferedImage bg = null; //背景图片加载
    public static BufferedImage hero0 = null;//英雄机
    public static BufferedImage hero1 = null;
    public static BufferedImage bee = null;//小蜜蜂
    public static BufferedImage bullet = null; //子弹
    public static BufferedImage airplane = null; //小敌机
    public static BufferedImage bigplane = null; //大敌机
    // 状态
    public static BufferedImage start;
    public static BufferedImage pause;
    public static  BufferedImage gameover;

    //一次性加载使用static{代码块}
    static {
        try{
        bg = ImageIO.read(Main.class.getResourceAsStream("img/background.png"));
        hero0 = ImageIO.read(Main.class.getResourceAsStream("img/hero0.png"));
        hero1 = ImageIO.read(Main.class.getResourceAsStream("img/hero1.png"));
        bee = ImageIO.read(Main.class.getResourceAsStream("img/bee.png"));
        bullet = ImageIO.read(Main.class.getResourceAsStream("img/bullet.png"));
        airplane = ImageIO.read(Main.class.getResourceAsStream("img/airplane.png"));
        bigplane = ImageIO.read(Main.class.getResourceAsStream("img/bigplane.png"));

        start = ImageIO.read(Main.class.getResourceAsStream("img/start.png"));
        pause = ImageIO.read(Main.class.getResourceAsStream("img/pause.png"));
        gameover = ImageIO.read(Main.class.getResourceAsStream("img/gameover.png"));
        }catch (IOException  e){
            e.printStackTrace();
        }

    }
    //=========================集合========================
    // 定义一个集合：并初始化
    ArrayList<FlyObject> flyings = new ArrayList<>();
    // 子弹集合
    ArrayList<Bullet> bullets = new ArrayList<>();

    //构造方法
    Main(){


    }

    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 4;

    //定义一个初始状态
    private int state = START;

//==================添加定时器============================

    //定义计时器
    private Timer timer = new Timer();

    // 成员方法
    public void action(){
        // 加定时器
        // 匿名内部类的使用
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(state == RUNNING){

                hero.move();//英雄机两个图片hero0,hero1的刷新
                // 1. 生成很多飞行物
                arrayFlyObject();
                // 2. 飞行物的移动
                flyObjectStep();
                // 3.飞行物的越界问题
                outFlyObject();


                //与子弹有关
                //1.英雄机发射子弹
                shootAction();
                // 2. 子弹的移动
                bulletsStep();
                //3.判断子弹的越界


                //f飞行物与子弹的碰撞
                bangAction();



                repaint();//刷新页面
            }
            }
        },1000, 20);



//==============添加监听器=====================
        //方法中的类 局部内部类
        MouseAdapter adapter = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                if(state==START){
                    state=RUNNING;
                }else if(state == GAME_OVER){
                    state=START;
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //super.mouseEntered(e);
                if(state==PAUSE){
                    state = RUNNING;
                }
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //super.mouseExited(e);
                if(state==RUNNING){
                    state = PAUSE;
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //super.mouseMoved(e);
                if(state == RUNNING){

                    int mouse_x = e.getX()+10;
                    int mouse_y = e.getY()+20;
                    hero.setX(mouse_x);
                    hero.setY(mouse_y);
                    repaint();
                }
            }
        };

        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);

    }



    // 画所有状态的方法
    private void paintState(Graphics g) {
        switch (state){
            case START :
                g.drawImage(start, 0, 0, this);
                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, this);
                break;
            case GAME_OVER :
                g.drawImage(gameover,0 ,0, this);
                break;
        }
    }




    //===============飞行物=======================

    //1.生成很多飞行物
    int flyingIndex = 0;
    public void arrayFlyObject(){
        flyingIndex++;

        FlyObject fly;

        if(flyingIndex % 20 ==0){
            int ran = (int)(Math.random()*20);
            if(ran==0){
                fly = new Bee();
            }else if(ran ==1|| ran==2||ran==3){
                fly = new BigPlane();

            }else{
                fly=new AirPlane();
            }
            flyings.add(fly);
        }
    }

    //1.1 画飞行物
    private void paintFlyObject(Graphics g){
        for (int i = 0; i < flyings.size(); i++) {
            FlyObject fly =  flyings.get(i);
            g.drawImage(fly.getImg(), fly.getX(), fly.getY(), this);
        }
    }

    // 2. 飞行物的移动
    private void flyObjectStep(){
        for (int i = 0; i < flyings.size() ; i++) {
            FlyObject fly = flyings.get(i);
            fly.move();
        }
    }

    //3.飞行物的越界
    private void outFlyObject() {
        for (int i = 0; i <flyings.size() ; i++) {
            FlyObject fly = flyings.get(i);
            if(fly.getY() > Main.HEIGHT){
                flyings.remove(i);
                //注意元素漏删问题
                i--;
            }
       }
        // 子弹的越界
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bs = bullets.get(i);
            if(bs.getY() < -Main.HEIGHT){
                bullets.remove(i);
                i--;

            }

        }

    }
//=====================子弹====================

    //1.英雄机发射子弹
    int shootIndex = 0;
    private void shootAction() {

        shootIndex++;
        if(shootIndex % 10 == 0){
            Bullet[] bs = hero.shoot();
            for (int i = 0; i < bs.length; i++) {
                bullets.add(bs[i]);
            }
        }


    }
    // 1.1 画子弹
    private void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bs1 = bullets.get(i);
            g.drawImage(bs1.getImg(), bs1.getX(), bs1.getY(), this);
        }

    }

    // 2. 子弹的移动
    private void bulletsStep() {

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bs = bullets.get(i);
            bs.move();
        }
    }

    // 3.子弹的越界问题 这个跟飞行物的越界问题写在一起


//====================碰撞问题======================
    private void bangAction() {
        for (int i = 0; i < flyings.size(); i++) {
            for (int j = 0; j < bullets.size() ; j++) {
                Bullet bs = bullets.get(j);
                FlyObject fly = flyings.get(i);

                if(bs.getX()>= fly.getX() &&
                    bs.getX()<=fly.getX()+fly.getWidth()&&
                    bs.getY()>= fly.getY() &&
                    bs.getY()<= fly.getY()+fly.getHeight()){

                    fly.setLife(fly.getLife()-1);//当飞行物的生命值为0时消失
                    if(fly.getLife()==0){
                        if(fly instanceof AirPlane){

                            AirPlane fly1 = (AirPlane) fly;
                            hero.setScore(hero.getScore()+fly1.getScore());
                        }
                        if(fly instanceof BigPlane){
                            BigPlane fly2 = (BigPlane) fly;
                            hero.setScore(hero.getScore()+fly2.getScore());
                            hero.setLife(hero.getLife()+1);
                        }
                        if(fly instanceof  Bee){
                            Bee fly1 = (Bee) fly;
                            hero.setLife(hero.getLife()+1);
                        }
                        flyings.remove(i);
                    }
                    bullets.remove(j);

                    i--;
                    break;
                }


            }

        }

    }




    //画奖励方法

    private void paintAward(Graphics g) {
        g.drawString("积分"+ hero.getScore(),0 , 10);
        g.drawString("生命值"+hero.getLife(),0,20);
    }


    //=============画图==========================
    Hero  hero = new  Hero();
//    Bee b= new Bee();
//    AirPlane air = new AirPlane();
//    BigPlane big = new BigPlane();
    // 画图片方法
    public void paint(Graphics g){
        super.paint(g);// 消除运动轨迹的
        g.drawImage(bg, 0, 0, this);
        g.drawImage(hero.getImg(), hero.getX(), hero.getY(), this);
//        g.drawImage(b.getImg(), b.getX(), b.getY(), this);
//        g.drawImage(air.getImg(), air.getX(), air.getY(), this);
//        g.drawImage(big.getImg(), big.getX(), big.getY(), this);
        // 画飞行物
        paintFlyObject(g);


        // 画子弹
        paintBullets(g);

        //画状态
        paintState(g);


        // 画积分和奖励
        paintAward(g);
    }




//==================主方法========================

    // 静态方法直接使用类来调用
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    // 主方法
    public static void main(String[] args) {

        // 主窗口
        JFrame window = new JFrame();
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(3);
        Main main = new Main();
        window.add(main);
        window.setVisible(true);


        main.action();
    }



}
