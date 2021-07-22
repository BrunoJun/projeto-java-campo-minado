package br.com.jun.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.jun.cm.excecao.ExplosaoException;

public class Campo {
    
    private boolean minado;
    private boolean aberto;
    private boolean marcado;

    private final int coluna;
    private final int linha;

    private List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna){

        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarVizinho(Campo vizinho){

        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaSoma = deltaColuna + deltaLinha;

        if(deltaSoma == 1 && !diagonal){

            vizinhos.add(vizinho);
            return true;
        } else if (deltaSoma == 2 && diagonal){

            vizinhos.add(vizinho);
            return true;
        }

        return false;
    }

    void alternarMarcacao(){

        if (!aberto) marcado = !marcado;
    }

    boolean abrir(){
        if(!aberto && !marcado){
            aberto = true;

            if(minado){

                throw new ExplosaoException();
            }

            if(vizinhancaSegura()){

                vizinhos.forEach(vizinho -> vizinho.abrir());
            }

            return true;
            
        } else {

            return false;
        }

    }

    boolean vizinhancaSegura(){

        return vizinhos.stream()
                .noneMatch(vizinho -> vizinho.minado);
    }

    void minar(){

        this.minado = true;
    }

    public boolean isMarcado(){

        return this.marcado;
    }

    public boolean isAberto(){

        return this.aberto;
    }

    public boolean isMinado(){

        return this.minado;
    }

    public int getColuna() {

        return coluna;
    }

    public int getLinha() {

        return linha;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado & aberto;
        boolean protegido = minado & marcado;

        return desvendado || protegido; 
    }

    long minasNaVizinhanca(){

        return vizinhos.stream().filter(vizinho -> vizinho.minado).count();
    }

    void reiniciar(){

        aberto = false;
        minado = false;
        marcado = false;
    }

    @Override
    public String toString() {

        if(marcado){

            return "x";
        } else if (aberto && minado){

            return "*";
        } else if (aberto && minasNaVizinhanca() > 0){

            return Long.toString(minasNaVizinhanca());
        } else if (aberto) {

            return " ";
        } else {

            return "?";
        }
    }
}

