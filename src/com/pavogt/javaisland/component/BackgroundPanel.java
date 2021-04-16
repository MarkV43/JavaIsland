package com.pavogt.javaisland.component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class BackgroundPanel extends Panel
{
    // The Image to store the background image in.
    BufferedImage img;
    public BackgroundPanel()
    {
        // Loads the background image and stores in img object.
        try {
            URL url = getClass().getResource("/Wood.png");
            if (url == null) throw new IOException("Image not found");
            img = ImageIO.read(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0,0,this);
    }
}