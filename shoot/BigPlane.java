
package day11.shoot;

public class BigPlane extends FlyObject {
    //成员变量 - 血量
    //private int blood;
    private int speed;
    private int score;

    //构造方法
    BigPlane(){
        /*super((int) (Math.random() * (Main.WIDTH-Main.bigplane.getWidth())),
                - Main.bigplane.getHeight(),
                Main.bigplane);*/
        //测试:
        super((int) (Math.random() * (Main.WIDTH- Main.bigplane.getWidth())),
                30,
                Main.bigplane,5);
        //blood = 5;
        speed = 2;
        score  = 20;
    }

    @Override//成员方法
    public void move() {
        //y坐标变大
        setY(getY() + speed);
    }
//
//    public int getBlood() {
//        return blood;
//    }
//
//    public void setBlood(int blood) {
//        this.blood = blood;
//    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}