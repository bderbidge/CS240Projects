import java.util.ArrayList;

/**
 * Created by brandonderbidge on 5/3/17.
 */

public class Image {

    private Pixel[][] pixellist;
    private int height;
    private int width;

    Image(int height, int width){

        this.width = width;
        this.height = height;
        pixellist = new Pixel[height][width];

    }

    public void setPixel(Pixel[][] setlist){
        pixellist = setlist;
    }

    public Pixel[][] getPixel(){
        return pixellist;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

}
