package br.com.jun.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    
    private boolean minado;
    private boolean aberto;
    private boolean marcado;

    private final int coluna;
    private final int linha;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();

    private void notificarObservadores(CampoEvento evento){

        observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
    }

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

    public void alternarMarcacao(){

        if (!aberto) {
            
            marcado = !marcado;

            if (marcado){

                notificarObservadores(CampoEvento.MARCAR);
            } else {

                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public boolean abrir(){
        if(!aberto && !marcado){
        
            if(minado){

                notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }

            setAberto(true);

            notificarObservadores(CampoEvento.ABRIR);

            if(vizinhancaSegura()){

                vizinhos.forEach(vizinho -> vizinho.abrir());
            }

            return true;
            
        } else {

            return false;
        }

    }

    public boolean vizinhancaSegura(){

        return vizinhos.stream()
                .noneMatch(vizinho -> vizinho.minado);
    }

    void minar(){

        this.minado = true;
    }

    void setAberto(boolean aberto) {

        this.aberto = aberto;

        if(aberto){

            notificarObservadores(CampoEvento.ABRIR);
        }
    }

    public void registrarObservador(CampoObservador observador){

        observadores.add(observador);
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

    public List<Campo> getVizinhos() {
        
        return vizinhos;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado & aberto;
        boolean protegido = minado & marcado;

        return desvendado || protegido; 
    }

    public int minasNaVizinhanca(){

        return (int) vizinhos.stream().filter(vizinho -> vizinho.minado).count();
    }

    void reiniciar(){

        aberto = false;
        minado = false;
        marcado = false;
        notificarObservadores(CampoEvento.REINICIAR);
    }
}

