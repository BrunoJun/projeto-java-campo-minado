package br.com.jun.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador{
    
    private int linhas;
    private int colunas;
    private int quantidadeMinas;

    private final List<Campo> campos = new ArrayList<>();
    private List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int quantidadeMinas) {

        this.linhas = linhas;
        this.colunas = colunas;
        this.quantidadeMinas = quantidadeMinas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }
    
    private void gerarCampos() {
     
        for (int linha = 0; linha < linhas; linha++) {
           for (int coluna = 0; coluna < colunas; coluna++) {
                
                Campo campo = new Campo(linha, coluna);
                campo.registrarObservador(this);
                campos.add(campo);
            }
        }

    }

    private void mostrarMinas() {

        campos.stream()
        .filter(c -> c.isMinado())
        .filter(c -> !c.isMarcado())
        .forEach(campo -> campo.setAberto(true));
    }

    private void associarVizinhos() {
        
        for (Campo campo1 : campos) {
            for (Campo campo2 : campos) {
            
                campo1.adicionarVizinho(campo2);
            }
        }

    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = campo -> campo.isMinado();

        while (minasArmadas < this.quantidadeMinas){

            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        }
    }

    private void notificarObservadores(Boolean resultado){

        observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
    }

    public void registrarObservador(Consumer<ResultadoEvento> observador){

        observadores.add(observador);
    }

    public boolean objetivoAlcancado() {

        return campos.stream().allMatch(campo -> campo.objetivoAlcancado());
    }

    public void reiniciar(){

        campos.stream().forEach(campo -> campo.reiniciar());
        sortearMinas();
    }

    public void abrir(int linha, int coluna){
   
        campos.parallelStream().filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
        .findFirst()
        .ifPresent(c -> c.abrir());
    }

    public void marcar(int linha, int coluna){

        campos.parallelStream().filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
        .findFirst()
        .ifPresent(c -> c.alternarMarcacao());
    }

    //Getters and Setters
    public List<Campo> getCampos() {

        return campos;
    }

    public int getQuantidadeMinas() {

        return quantidadeMinas;
    }
    public int getLinhas() {

        return linhas;
    }

    public int getColunas() {
        
        return colunas;
    }

    @Override
    public void eventoOcorreu(Campo c, CampoEvento e) {
        
        if(e == CampoEvento.EXPLODIR) {

            mostrarMinas();
            notificarObservadores(false);
        } else if (objetivoAlcancado()){

            notificarObservadores(true);
        }
    }
}
