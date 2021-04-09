package br.com.jogo;
import java.util.Scanner;
import java.util.ArrayList;
public class Jogadores{

    private Scanner tecladoJogador = new Scanner(System.in);

    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();

    public void criaListaJogadores(int qtdJogadores){
        //inserindo jogadores
        for(int i=0;i<qtdJogadores;i++){
            String nomeJogador="";
            System.out.println("Digite o nome do jogador " + (i+1) + ":");
            //Recuperando retorno do teclado sobre o nome do jogador atual
            nomeJogador = tecladoJogador.nextLine();
            this.jogadores.add(new Jogador(nomeJogador,null));
        }
        System.out.println("Jogadores Criados!");
    }

    public int ultimoIndex(){
        return this.jogadores.size() - 1;
    }

    public int getQtdJogadores(){
        return this.jogadores.size();
    }

    public void printJogadores(){
        System.out.println("Jogadores:");
		for (int i = 0; i < this.jogadores.size(); i++) {
			System.out.println("(" + i + ") " + this.jogadores.get(i).getNome());
        }
        System.out.println("----------------------------------------------");
    }

    public ArrayList<Jogador> getJogadores(){
        return this.jogadores;
    }
}