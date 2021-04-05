package com.pavogt.javaisland;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends Frame {

    public MainWindow() {
        System.out.println("inicio da janela");

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        this.setTitle("Minha Primeira Janela");

        // Define o layout manager
        GridLayout gl = new GridLayout(4,2);
        this.setLayout( gl );

        //Cria os diversos elementos visuais e coloca no frame

        Label fixoTexto = new Label("texto fixo");
        this.add( fixoTexto);

        var botao = new Button("meu botao");
        this.add( botao );

        var areaTexto = new TextArea("area para colocar texto\nvarias linhas\n");
        areaTexto.setEditable(false);
        areaTexto.append("\nMais uma linha extra\n");
        this.add( areaTexto );

        var campoTexto = new TextField("valor inicial");
        this.add( campoTexto );

        var escolha = new Choice();
        escolha.addItem("fiat");
        escolha.addItem("volks");
        escolha.addItem("gm");
        escolha.addItem("ford");
        escolha.addItem("renault");
        escolha.addItem("crysler");
        this.add( escolha);

        var lista = new List( 3, false);
        lista.add("preto");
        lista.add("azul");
        lista.add("vermelho");
        lista.add("amarelo");
        lista.add("verde");
        lista.add("roxo");
        this.add( lista);

        var caixa = new Checkbox("caixa para marcar", false);
        this.add( caixa);

        var barra = new Scrollbar( Scrollbar.HORIZONTAL, 50, 0, 0, 100);
        this.add( barra);

        System.out.println("terminou criacao dos componentes");

        // Mostra
        this.pack();
        this.setVisible(true);	// "this" eh opcional
    }

}
