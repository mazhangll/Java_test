package day11.shoot;


    public class AirPlane extends FlyObject {
        //成员变量
        private int speed;
        private int score;
        //构造方法
        AirPlane(){
            super((int) (Math.random() *
                            (Main.WIDTH - Main.airplane.getWidth())),
                    30,
                    Main.airplane,1);
            speed = 5;
            score = 10;
        }
        //成员方法

        public void move(){
            this.setY(this.getY()+speed);

        }
        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }


