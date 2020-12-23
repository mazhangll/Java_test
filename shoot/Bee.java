package day11.shoot;

public class Bee extends FlyObject{
    //成员变量
    private int speed_x;
    private int speed_y;
    //构造方法
    Bee(){
        super((int)(Math.random()*Main.WIDTH), 30, Main.bee,1);
        speed_x = 1;
        speed_y = 1;
    }

    //成员方法
    // 小蜜蜂的移动
    public void move(){
        this.setX(this.getX() + speed_x);
        this.setY(this.getY() + speed_y);

        if(this.getX()==Main.WIDTH-Main.bee.getWidth()){
            speed_x = -1;
        }
        if(this.getX()==0){
            speed_y = 1;
        }
    }

    public int getSpeed_x() {
        return speed_x;
    }

    public void setSpeed_x(int speed_x) {
        this.speed_x = speed_x;
    }

    public int getSpeed_y() {
        return speed_y;
    }

    public void setSpeed_y(int speed_y) {
        this.speed_y = speed_y;
    }
}
