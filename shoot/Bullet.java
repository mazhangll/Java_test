package day11.shoot;

public class Bullet extends FlyObject{
    private int speed;

    //构造方法
    Bullet(int x, int y){
        super(x, y, Main.bullet,1);
        speed = 3;
    }

    public void move(){
        //y -= speed; proteceted
        this.setY(this.getY()-speed);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
