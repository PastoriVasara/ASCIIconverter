package picturetoascii;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author jussi
 */
public class MainThread
{

    //Picture Location
    private String fromPath;

    //ASCII art Location
    private String toPath;

    //Length of the ASCII Character Array
    private int shadeLength;
    private int pictureWidth;

    public void run(String aFromPath, String aToPath, int aPictureWidth)
    {
        pictureWidth = aPictureWidth;
        fromPath = aFromPath;
        toPath = aToPath;
        BufferedImage image = null;
        try
        {
            //read the Image
            image = ImageIO.read(new File(fromPath));
        } catch (IOException e)
        {
            System.out.println("Not Working!");
        }
        //start timer
        long start = System.currentTimeMillis();
        //Longer character shade Form
        String charString = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";

        //Shorter character shade Form
        //String charString = "@%#*+=-:. ";
        shadeLength = charString.length();
        char[] characters = new char[shadeLength];

        //fill ASCII shade array
        for (int i = 0; i < shadeLength; i++)
        {
            characters[i] = charString.charAt(i);
        }

        grayScaleIt(image, characters);
        System.out.println("Doned!");
        System.out.println(System.currentTimeMillis() - start + " MS");

    }

    public void grayScaleIt(BufferedImage rawImage, char[] characters)
    {
        //Scaled image size 
        int wantedSize = pictureWidth;
        //Multiplier for the height
        double multiplier = 1.0 * rawImage.getWidth() / wantedSize;
        //Make temporary image which will be scaled accordingly
        Image tmp = rawImage.getScaledInstance(wantedSize, (int) (rawImage.getHeight() / multiplier), Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(wantedSize, (int) (rawImage.getHeight() / multiplier), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        //get the new height and width
        int height = image.getHeight();
        int width = image.getWidth();

        Color colorValue = null;
        int grayValue = 0;

        //Create the output for the ASCII art
        String ASCIIpicture = "";

        //iterate through each pixel
        for (int i = 0; i < height; i++)
        {

            for (int j = 0; j < width; j++)
            {
                //get the color value of the pixel
                colorValue = new Color(image.getRGB(j, i));

                //make it grayscale according the amount of shades in ASCII character array
                grayValue = (((colorValue.getGreen() + colorValue.getRed() + colorValue.getBlue()) / 3) / (256 / (shadeLength - 1)));

                //failsafes if the grayValue goes above or under the array length
                if (grayValue > shadeLength - 1)
                {
                    grayValue = shadeLength - 1;
                }
                if (grayValue < 0)
                {
                    grayValue = 0;
                }
                //add the character to output according the grayscale value
                ASCIIpicture += characters[grayValue];

            }
            ASCIIpicture += "\n";
        }

        BufferedWriter bufferedWriter = null;

        //try to write to a text file
        try
        {

            File myFile = new File(toPath);
            // if there is no filed with that name, create a new file
            if (!myFile.exists())
            {
                myFile.createNewFile();
            }
            //write the String variable into the text file
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(ASCIIpicture);
            //catch any errors
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
