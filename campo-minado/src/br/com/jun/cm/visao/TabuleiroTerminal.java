package br.com.jun.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.jun.cm.excecao.ExplosaoException;
import br.com.jun.cm.excecao.SairException;
import br.com.jun.cm.modelo.Tabuleiro;

public class TabuleiroTerminal {
    
    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroTerminal(Tabuleiro tabuleiro){

        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    
    private void executarJogo() {

        try {

            boolean continuar = true;

            while (continuar){

                cicloDoJogo();

                System.out.println("Outra partida? (S/n) ");
                String resposta = entrada.nextLine();

                if("n".equalsIgnoreCase(resposta)){

                    continuar = false;
                } else {

                    tabuleiro.reiniciar();
                }
            }
        } catch (SairException e){

            System.out.println("Fim do jogo!");
        } finally {

            entrada.close();
        }
    }


    private void cicloDoJogo() {

        try {

            while(!tabuleiro.objetivoAlcancado()){

                System.out.println(tabuleiro);

                String digitado = receberValorDigitado("Digite as coordenadas x e y: ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                    .map(valor -> Integer.parseInt(valor.trim()))
                    .iterator();

                digitado = receberValorDigitado("1 - abrir ou 2 - (Des)marcar");

                if (digitado.equals("1")) {

                    tabuleiro.abrir(xy.next(), xy.next());
                } else if (digitado.equals("2")){

                    tabuleiro.marcar(xy.next(), xy.next());
                }
            }
            
            System.out.println("Ganhou o jogo!");
        } catch (ExplosaoException e) {
            
            System.out.println("Perdeu o jogo!");
        }
    }

    private String receberValorDigitado(String texto){

        System.out.println(texto);
        String digitado = entrada.nextLine();

        if (digitado.equalsIgnoreCase("sair")){

            throw new SairException();
        }

        return digitado;
    }
}

