package day11.shoot;

public class Hero extends FlyObject{
    //成员变量
    private int life;
    private int score;

    // 构造方法
    Hero(){
        super(100, 400, Main.hero0,1);
    }
    //成员方法

    private int count = 0;
    @Override
    // 图片的刷新，不是动作上的移动
    public void move() {
        count++;
        if(count % 2 ==0){
            this.setImg(Main.hero0);
        }else {
            this.setImg(Main.hero1);
        }

    }

    //英雄机发射子弹
    public Bullet[] shoot(){
        Bullet[] bullet = new Bullet[1];
        bullet[0] = new Bullet(this.getX() + this.getWidth()/2, this.getY());

        return bullet;
    }



    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
