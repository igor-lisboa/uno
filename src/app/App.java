package app;

import br.com.jogo.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando jogo...");
        Jogo rodada = new Jogo();
        rodada.start();
        System.out.println("Finalizando jogo...");
        rodada = null;
    }
}