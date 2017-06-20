/**
 * Created by brandonderbidge on 5/3/17.
 */
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageEditor {


    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        if(args.length < 3)
            return;

        Pixel[][] imageList;
        ImageEditor ie = new ImageEditor();

        File file = new File(args[0]);
        String outputFile = args[1];



            FileReader f = new FileReader(file);
            Scanner S = new Scanner(f);
            S.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");

            String P3 = S.next();
            int width = Integer.parseInt(S.next());
            int height = Integer.parseInt(S.next());
            String Max = S.next();

            Image newImage = new Image(height, width);

            imageList = new Pixel[height][width];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Pixel newPixel = new Pixel();
                    newPixel.setRed(Integer.parseInt(S.next()));
                    newPixel.setGreen(Integer.parseInt(S.next()));
                    newPixel.setBlue(Integer.parseInt(S.next()));
                    imageList[i][j] = newPixel;
                }
            }

            S.close();
            newImage.setPixel(imageList);


            if (args[2].equalsIgnoreCase("motionblur") && args.length > 3)
                newImage = ie.blurImage(newImage, Integer.parseInt(args[3]));
            else if (args[2].equalsIgnoreCase("invert"))
                newImage = ie.invertImage(newImage);
            else if (args[2].equalsIgnoreCase("grayscale"))
                newImage = ie.grayScaleImage(newImage);
            else if (args[2].equalsIgnoreCase("emboss"))
                newImage = ie.embossImage(newImage);
            else
                return;


            PrintWriter print = new PrintWriter(outputFile, "UTF-8");
            StringBuilder str = new StringBuilder();
            str.append("P3\n");
            str.append(width);

            str.append(" ");
            str.append(height);
            str.append("\n255\n");
            String photo = str.toString();
            print.print(photo);

            for(int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    print.print(newImage.getPixel()[i][j].getPixel());

                }
            }

            print.close();

    }



    public Image blurImage(Image currentImage, int scale) { //need to redo this function to take scale into account.

        if(scale == 1)
            return currentImage;


        for (int i = 0; i < currentImage.getHeight(); i++){
            for (int j = 0; j < currentImage.getWidth(); j++){

                Pixel currentPixel = currentImage.getPixel()[i][j];
                int count = scale - 1;
                int position = j+1;
                int average = 1;

                while (position < currentImage.getWidth() && count != 0 ){
                    currentPixel.setRed(currentPixel.getRed() + currentImage.getPixel()[i][position].getRed());
                    currentPixel.setGreen(currentPixel.getGreen() + currentImage.getPixel()[i][position].getGreen());
                    currentPixel.setBlue(currentPixel.getBlue() + currentImage.getPixel()[i][position].getBlue());
                    count--;
                    position++;
                    average++;
                }

                currentPixel.setRed(currentPixel.getRed()/average);
                currentPixel.setGreen(currentPixel.getGreen()/average);
                currentPixel.setBlue(currentPixel.getBlue()/average);

                currentImage.getPixel()[i][j] = currentPixel;
            }
        }

        return currentImage;
    }

    public Image grayScaleImage(Image currentImage) {

        for (int i = 0; i < currentImage.getHeight(); i++){
            for (int j = 0; j < currentImage.getWidth(); j++){

                int average;

                Pixel currentPixel = currentImage.getPixel()[i][j];
                average = (currentPixel.getBlue() + currentPixel.getRed() + currentPixel.getGreen())/3;

                currentPixel.setBlue(average);
                currentPixel.setRed(average);
                currentPixel.setGreen(average);

                currentImage.getPixel()[i][j] = currentPixel;
            }
        }

        return currentImage;
    }

    public Image embossImage(Image currentImage) {

        for (int i = currentImage.getHeight() - 1; i >= 0 ; i--){
            for (int j = currentImage.getWidth() - 1; j >= 0 ; j--) {

                Pixel currentPixel, cornerPixel;

                if(i == 0 || j == 0 ){
                    currentImage.getPixel()[i][j].setRed(128);
                    currentImage.getPixel()[i][j].setGreen(128);
                    currentImage.getPixel()[i][j].setBlue(128);
                }
                else {
                    currentPixel = currentImage.getPixel()[i][j];
                    cornerPixel = currentImage.getPixel()[i - 1][j - 1];
                    int RedDiff = currentPixel.getRed() - cornerPixel.getRed();
                    int GreenDiff = currentPixel.getGreen() - cornerPixel.getGreen();
                    int BlueDiff = currentPixel.getBlue() - cornerPixel.getBlue();
                    int MaxDiff = 0;

                    if((Math.abs(RedDiff) >= Math.abs(GreenDiff) && Math.abs(RedDiff) >= Math.abs(BlueDiff)) ){

                        MaxDiff = RedDiff +128;
                    }
                    else if( (Math.abs(GreenDiff) >= Math.abs(RedDiff) && Math.abs(GreenDiff) >= Math.abs(BlueDiff)) ){

                        MaxDiff = GreenDiff + 128;
                    }
                    else if((Math.abs(BlueDiff) >= Math.abs(RedDiff) && Math.abs(BlueDiff) >= Math.abs(GreenDiff)) ) {

                        MaxDiff = BlueDiff + 128;
                    }

                    if(MaxDiff > 255){
                        MaxDiff = 255;
                    }
                    else if(MaxDiff < 0) {
                        MaxDiff = 0;
                    }

                    currentPixel.setRed(MaxDiff);
                    currentPixel.setGreen(MaxDiff);
                    currentPixel.setBlue(MaxDiff);

                    currentImage.getPixel()[i][j] = currentPixel;
                }
            }
        }

        return currentImage;
    }

    public Image invertImage(Image currentImage) {

        Pixel currentPixel;

        for (int i = 0; i < currentImage.getHeight(); i++) {
            for (int j = 0; j < currentImage.getWidth(); j++) {

                currentPixel = currentImage.getPixel()[i][j];

                currentPixel.setRed(255 - currentPixel.getRed());
                currentPixel.setGreen(255 - currentPixel.getGreen());
                currentPixel.setBlue(255 - currentPixel.getBlue());

                currentImage.getPixel()[i][j] = currentPixel;
            }
        }


        return currentImage;
    }
}

