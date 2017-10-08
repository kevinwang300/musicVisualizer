package MusVisPack;

/**
 * Created by ryanleung on 10/7/17.
 */

public class SquareSpiral
{
    public static void main(String[] args)
    {
        Turtle bob = new Turtle(0.5,0.5,0);
        for(int i=0;i<10000;i++)
        {
            bob.goForward(i * 0.000001);
            bob.turnLeft(1);
        }
    }
}