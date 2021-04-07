package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.data.Client;

import java.awt.*;

public class Clients extends Panel {

    private Client[] clients;

    public Clients() {
        Button bAdd = new Button("+");
        bAdd.setBounds(20, 20, 30, 30);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 28);
        bAdd.setFont(font);
        add(bAdd);

        TextArea search = new TextArea("", 1, 30, TextArea.SCROLLBARS_NONE);
        search.setBounds(60, 20, 300, 30);
        search.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(search);

        clients = new Client[100];
        clients[0] = new Client("Marcelo", "marcelo.vogt@grad.ufsc.br", 0, 99999);
        clients[1] = new Client("Gaby", "gabycalzone@gmail.com", 0, 69420);
        clients[2] = new Client("Luís Eduardo", "luis.e.parise@grad.ufsc.br", 0, 69);
        clients[3] = new Client("Eduardo", "eduardo@gmail.com", 0, 0);
        clients[4] = new Client("Gabriel", "gabriel@gmail.com", 0, 10);
        clients[5] = new Client("Marechal Luciolo", "luciolo@marechal.gov.br", 0, 1000000);

        List list = new List(100, false);
        for (int i = 0; i < 100; i++) {
            if (clients[i] == null) break;
            list.add(clients[i].getName());
        }
        list.setBounds(20, 60, 340, 580);
        add(list);

        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }
}
