package br.com.jun.cm.visao;

import br.com.jun.cm.modelo.Tabuleiro;


// Classe apenas para teste
public class Temp {
    
    public static void main(String[] args) {
        
        Tabuleiro tabuleiro = new Tabuleiro(2, 2, 4);

        tabuleiro.registrarObservador(e -> {

            if(e.isGanhou()){

                System.out.println("Ganhou tela!");
            } else {

                System.out.println("Perdeu!!");
            }
        });

        tabuleiro.marcar(0, 0);
        tabuleiro.marcar(0, 1);
        tabuleiro.marcar(1, 0);
        tabuleiro.marcar(1, 1);
    }
}
