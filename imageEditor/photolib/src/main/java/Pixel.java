/**
 * Created by brandonderbidge on 5/3/17.
 */

public class Pixel {

    private int Red;
    private int Green;
    private int Blue;


    public Pixel(){
        Red = 0;
        Green = 0;
        Blue = 0;
    }
    public void setRed(int value){
        Red = value;
    }

    public void setGreen(int value){
        Green = value;
    }

    public void setBlue(int value){
        Blue = value;
    }

    public int getBlue() {
        return Blue;
    }

    public int getRed(){
        return Red;
    }

    public int getGreen(){
        return Green;
    }

    public String getPixel(){
        String pixel;
        pixel = Integer.toString(getRed()) + "\n" + Integer.toString(getGreen()) + "\n" + Integer.toString(getBlue()) +
                "\n";
        return pixel;

    }
}
