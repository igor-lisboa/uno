package br.com.jogo;

import java.util.Scanner;

public class Mesa {
    private Cartas comprar;
    private Cartas jogo;
    private Jogadores pessoas;

    // Declarando tecladoMesa
    private Scanner tecladoMesa = new Scanner(System.in);

    public void distribuiCartas(Cartas baralho, boolean printCartasJogadores) {
        // para cada jogador... inserir 7cartas na mao
        for (Jogador pessoa : this.pessoas.getJogadores()) {
            Cartas baralhoPessoa = new Cartas();
            for (int i = 0; i < 7; i++) {
                baralhoPessoa.recebeCarta(baralho.descarta(baralho.ultimoIndex()));
            }
            pessoa.setCartas(baralhoPessoa);
            if (printCartasJogadores) {
                System.out.println(pessoa.getNome());
                pessoa.getCartasMao().printCartas();
                System.out.println("------------------------------");
            }
        }
        System.out.println("Cartas distribuidas");
        System.out.println("------------------------------");
    }

    public void defineJogadores(int qtdJogadores, boolean printJogadores) {
        // Declarando e definindo Jogadores
        Jogadores jogadores = new Jogadores();
        jogadores.criaListaJogadores(qtdJogadores);
        if (printJogadores) {
            jogadores.printJogadores();
        }
        this.pessoas = jogadores;
    }

    public void preencheBaralhos(Cartas baralho) {
        // joga todas as cartas no baralho de comprar ate sobrar apenas 1 carta
        this.comprar = new Cartas();
        while (baralho.getQtdCartas() != 1) {
            this.comprar.recebeCarta(baralho.descarta(baralho.ultimoIndex()));
        }
        System.out.println(this.comprar.getQtdCartas() + " Cartas disponiveis para comprar...");
        System.out.println("------------------------------");

        this.jogo = new Cartas();
        // coloca carta que sobrou no baralho para jogo
        this.jogo.recebeCarta(baralho.descarta(baralho.ultimoIndex()));
    }

    public Mesa() {
        // funcao para retornar numero de jogadores que ira jogar
        int qtdJogadores = validaQtdJogadores();

        defineJogadores(qtdJogadores, true);

        // cria e embaralha o baralho
        Cartas baralho = new Cartas();
        baralho.criaCartas();
        baralho.embaralha();

        // mostra todas as cartas do baralho
        baralho.printCartas();

        distribuiCartas(baralho, true);

        preencheBaralhos(baralho);
    }

    public int validaQtdJogadores() {
        boolean valido = false;
        int qtdJogadores = 5;
        while (valido == false) {
            // Pedindo numero de jogadores que irao jogar
            System.out.println("Digite a quantidade de jogadores que irao jogar(NO MiNIMO 2 e NO MaXIMO 10):");

            // Recuperando retorno do tecladoMesa sobre o numero de jogadores que irao jogar
            // e salvando em uma variavel int
            String retorno = tecladoMesa.next();
            try {
                qtdJogadores = Integer.parseInt(retorno);

                // Informando quantidade de jogadores que irao jogar ou retornando erro de
                // numero invalido
                if (qtdJogadores < 2) {
                    System.out.println("Quantidade de jogadores nao pode ser menor que 2!");
                } else if (qtdJogadores > 10) {
                    System.out.println("Quantidade de jogadores nao pode ser maior que 10!");
                } else {
                    System.out.println(qtdJogadores + " jogadores irao jogar.");
                    valido = true;
                }
            } catch (Exception ex) {
                System.out.println("Valor invalido, por favor selecione uma quantidade valida:");
            }
        }
        return qtdJogadores;
    }

    public Cartas getBaralhoJogo() {
        return this.jogo;
    }

    public Cartas getBaralhoComprar() {
        return this.comprar;
    }

    public Jogadores getPessoas() {
        return this.pessoas;
    }

}