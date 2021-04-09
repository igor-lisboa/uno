package br.com.jogo;

public class Jogador{
    private String nome;
    private Cartas mao;

    public Jogador(String nomeJogador, Cartas maoJogador){
        this.nome=nomeJogador;
        this.mao=maoJogador;
    }

    public Jogador(Jogador pessoa){
        this.nome=pessoa.nome;
        this.mao=pessoa.mao;
    }

    public void setNome(String nomeJogador){
        this.nome=nomeJogador;
    }

    public void setCartas(Cartas maoJogador){
        this.mao=maoJogador;
    }


    public String getNome(){
        return this.nome;
    }

    public Cartas getCartasMao(){
        return this.mao;
    }
}