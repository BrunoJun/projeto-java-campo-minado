package br.com.jun.cm;

import br.com.jun.cm.modelo.Tabuleiro;

public class Aplicacao {
    
    public static void main(String[] args) {
        
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);

        tabuleiro.abrir(2, 1);
        tabuleiro.marcar(4, 5);

        System.out.println(tabuleiro);
    }
}
