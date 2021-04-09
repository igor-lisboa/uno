package br.com.jogo;

import java.util.ArrayList;
import java.util.Random;

public class Cartas {
    private ArrayList<Carta> lista = new ArrayList<Carta>();

    public void criaCartas() {

        for (int c = 0; c < Jogo.cartaCores.length; ++c) {
            // primeira parte de repeticao das cartas padrao
            for (int v = 0; v < Jogo.cartaValores.length; ++v) {
                Carta nova = new Carta(Jogo.cartaCores[c].toString(), Jogo.cartaValores[v].toString());
                this.lista.add(nova);
            }
            // segunda parte de repeticao das cartas padrao
            for (int v2 = 0; v2 < Jogo.cartaValores.length; ++v2) {
                Carta nova2 = new Carta(Jogo.cartaCores[c], Jogo.cartaValores[v2]);
                this.lista.add(nova2);
            }
            // incluindo cartas especiais
            for (int e = 0; e < Jogo.cartaEspecial.length; ++e) {
                Carta esp = new Carta((Jogo.cartaEspecial[e] == "0" ? Jogo.cartaCores[c] : "Especial"),
                        Jogo.cartaEspecial[e]);
                this.lista.add(esp);
            }
        }
        System.out.println("Cartas Criadas!");
    }

    public synchronized Carta descarta(int index) {
        Carta selecionada = lista.get(index);
        lista.remove(index);
        return selecionada;
    }

    public synchronized void recebeCarta(Carta entrada) {
        lista.add(entrada);
    }

    public void printCartas() {
        System.out.println("Cartas:");
        for (int i = 0; i < this.lista.size(); i++) {
            System.out.println("(" + i + ") " + this.lista.get(i).getCartaString(""));
        }
        System.out.println("----------------------------------------------");
    }

    public ArrayList<Carta> getCartas() {
        return this.lista;
    }

    public int ultimoIndex() {
        return this.lista.size() - 1;
    }

    public int getQtdCartas() {
        return this.lista.size();
    }

    public void embaralha() {
        Random rand = new Random();
        // Inicia a troca do ultimo elemento e vai ate o inicio
        for (int i = this.lista.size() - 1; i > 0; i--) {
            // Pega um index aleatOrio de 0 ate i
            int j = rand.nextInt(i + 1);
            // troca o array da posicao i com o elemento do index aleatOrio
            Carta temp = this.lista.get(i);
            this.lista.set(i, this.lista.get(j));
            this.lista.set(j, temp);
        }
        System.out.println("Cartas Embaralhadas!");
    }

}