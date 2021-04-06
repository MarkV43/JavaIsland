package com.pavogt.javaisland;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends Frame {

    public MainWindow() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        var fixoTexto = new Label("texto fixo");
        fixoTexto.setBounds(30, 100, 80, 30);
        add(fixoTexto);

        var botao = new Button("meu botao");
        botao.setBounds(30, 135, 80, 30);
        add(botao);

        var areaTexto = new TextArea("area para colocar texto\nvarias linhas\n");
        areaTexto.setEditable(false);
        areaTexto.append("\nMais uma linha extra\n");
        areaTexto.setBounds(200, 100, 150, 60);
        add(areaTexto);

        var campoTexto = new TextField("valor inicial");
        campoTexto.setBounds(200, 170, 80, 30);
        add(campoTexto);

        var escolha = new Choice();
        escolha.addItem("fiat");
        escolha.addItem("volks");
        escolha.addItem("gm");
        escolha.addItem("ford");
        escolha.addItem("renault");
        escolha.addItem("crysler");
        escolha.setBounds(500, 100, 100, 30);
        add(escolha);

        var lista = new List(3, false);
        lista.add("preto");
        lista.add("azul");
        lista.add("vermelho");
        lista.add("amarelo");
        lista.add("verde");
        lista.add("roxo");
        lista.setBounds(100, 500, 100, 100);
        add(lista);

        var caixa = new Checkbox("caixa para marcar", false);
        caixa.setBounds(300, 500, 100, 30);
        add(caixa);

        var s = new Scrollbar(Scrollbar.HORIZONTAL, 50, 10, 0, 100);
        s.setBounds(500, 600, 300, 50);
        add(s);

        setTitle("Minha Primeira Janela");
        setLayout(null);
        setVisible(true);
        setSize(1280, 720);
    }
}
