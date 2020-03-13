package GamePlayingRobot;


        import java.awt.*;
        import java.awt.event.InputEvent;
        import java.awt.event.KeyEvent;
        import java.awt.event.MouseEvent;
        import java.awt.image.BufferedImage;
        import javax.imageio.ImageIO;
        import java.io.File;
        import java.io.IOException;


public class bot extends Thread {

    public Rectangle rect;

    public bot(int x ,int y , int width , int height){
        rect = new Rectangle(x,y,width,height);

    }
    public void run(){
        try {
            Robot robot = new Robot();


            BufferedImage image = robot.createScreenCapture(rect);
            ImageIO.write(image , "png", new File("/Users/mirro/SS/screen.png")); // We're checking whether our workspace is correct or not.

            long endTime = System.currentTimeMillis()+40000;//after 40 seconds later bot stops.
            int count = 7;//We have a count , because since we have 7 bullets , when we aut of ammo our bot will press space and reload the gun.

            while(endTime >= System.currentTimeMillis()){ //loop

                if(count == 0){
                    robot.keyPress(KeyEvent.VK_SPACE); //reload
                    count =7;
                }

                BufferedImage img = robot.createScreenCapture(rect);
                search:
                for(int x2 = 0; x2 < rect.width;x2+=10){  //we check last 250 pixel of our rectangle, we could use all of the pixels but then our bot would work slowly.

                    for(int y2 = 0; y2 < rect.height;y2+=4){// we check every pixel of height because stickman move towards x line.



                        Color mycolor = new Color(img.getRGB(x2,y2)); // we used color class to get rgb value of any pixel.

                        if(mycolor.getBlue() >180 && mycolor.getGreen() >180 && mycolor.getRed() > 180){ //this rgb value means, if there is a color which is likely white(in our game
                            robot.mouseMove(rect.x + x2 , y2 + rect.y);                            //stickman is the only white thing.So our bot wont aim anything else.
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);//left mouse button click
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);//left mouse button release
                            robot.delay(250);
                            count--;

                            break search;//when we killed a stickman our loop starts from the beginning again.
                        }


                    }
                    robot.delay(0);
                }

            }

        }catch(AWTException | IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new bot(18,500,1162,350).start();
    } //This rectangle is where our bot will check if there is a stickman.

}

