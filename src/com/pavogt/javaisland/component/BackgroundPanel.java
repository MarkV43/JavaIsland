package com.pavogt.javaisland.component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundPanel extends Panel
{
    // The Image to store the background image in.
    BufferedImage img;
    public BackgroundPanel()
    {
        // Loads the background image and stores in img object.
        try {
            img = ImageIO.read(new File("image/Shutup.png"));
        } catch (IOException x){}
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0,0,this);
    }
}