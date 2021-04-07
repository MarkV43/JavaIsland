package com.pavogt.javaisland;

import com.pavogt.javaisland.data.Client;

import java.io.*;

public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client[] clients = new Client[2];
        clients[0] = new Client(0, "Marcelo", "marcelo.vogt@grad.ufsc.br", 0, 99999);
        clients[1] = new Client(2, "Luís Eduardo", "luis.e.parise@grad.ufsc.br", 0, 69);

        var fileOutputStream = new FileOutputStream("yourfile.txt");
        var objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(clients);
        objectOutputStream.flush();
        objectOutputStream.close();

        var fileInputStream = new FileInputStream("yourfile.txt");
        var objectInputStream = new ObjectInputStream(fileInputStream);
        Client[] cs = (Client[]) objectInputStream.readObject();
        objectInputStream.close();

        for (Client c: cs) {
            System.out.println(c);
        }
    }
}
