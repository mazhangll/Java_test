package day11.shoot;

import java.awt.image.BufferedImage;

public class FlyObject {
    //成员变量
    private int x;
    private int y;
    private BufferedImage img;
    private int width;
    private int height;



    private int life;
    //构造方法
    FlyObject(){

    }
    FlyObject(int x, int y, BufferedImage img, int life){
        this.x = x;
        this.y =y;
        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.life = life;
    }
    //成员方法
    public void move(){

    }


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }


    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


}
