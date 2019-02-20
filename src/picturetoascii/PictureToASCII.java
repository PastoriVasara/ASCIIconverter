/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturetoascii;

/**
 *
 * @author jussi
 */
public class PictureToASCII
{

    /* This program creates ASCII art
     * First parameter of the args is from what picture the art is made of
     * The second parameter is where the ascii art will be saved
     * The third parameter is what size the ascii art should be
     */
    public static void main(String[] args)
    {
        MainThread mt = new MainThread();

        if (args[0] != null && args[1] != null && args[2] != null)
        {
            System.out.println(args[0]);
            System.out.println(args[1]);
            System.out.println(args[2]);
            int size = Integer.parseInt(args[2]);
            if (size != 0)
            {
                mt.run(args[0], args[1], size);
            }

        } else
        {
            System.out.println("Not Working!");
        }

    }

}
