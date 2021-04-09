package br.com.jogo;

import java.util.Scanner;

public class Jogo {
    private Mesa jogo;
    private Jogador vencedor;
    private String corSelecionada;
    private int addCartas;
    private boolean finalizaJogo;
    private int codEsp = 0;
    private int sentidoJogo = 1;
    public static String[] cartaCores = { "Vermelho", "Amarelo", "Verde", "Azul" };
    public static String[] cartaValores = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "Bloquear", "Inverter", "+2" };
    public static String[] cartaEspecial = { "Curinga", "+4", "0" };
    public static String[] comandoEsp = { "Pular Jogada", "Comprar Cartas", "Finalizar Jogo" };

    // Declarando tecladoJogo
    private Scanner tecladoJogo = new Scanner(System.in);

    public String getVencedorNome() {
        if (this.vencedor != null) {
            return this.vencedor.getNome();
        }
        return "";
    }

    public boolean confirmJogada(Jogador jogadorAtual, int indexCartaSel, Carta cartaRodadaAtual, Carta cartaSel) {
        String retorno = "";
        boolean valido = false;

        while (valido == false) {
            System.out.println(jogadorAtual.getNome() + ", confirma a jogada? (S/N)");

            // Recuperando String de confirmacao da jogada
            String resposta = tecladoJogo.next();

            retorno = resposta.toString().toUpperCase();

            if (retorno.equals("S") || retorno.equals("UNO")) {

                if (retorno.equals("S")) {
                    if (jogadorAtual.getCartasMao().getQtdCartas() == 2) {
                        System.out.println(jogadorAtual.getNome()
                                + ", falar UNO na confirmacao quando se vai ter apenas 1 carta e OBRIGATORIO, serao adicionadas 2 cartas no seu baralho.");
                        // adiciona 2 cartas por nao ter falado UNO
                        this.addCartas += 2;
                        addCartas(cartaRodadaAtual, null, jogadorAtual, "");
                    }
                } else if (retorno.equals("UNO")) {
                    if (jogadorAtual.getCartasMao().getQtdCartas() != 2) {
                        System.out.println(jogadorAtual.getNome()
                                + ", UNO foi dito no momento errado, serao adicionadas 2 cartas no seu baralho.");
                        // adiciona 2 cartas por ter falado UNO
                        this.addCartas += 2;
                        addCartas(cartaRodadaAtual, null, jogadorAtual, "");
                    }
                }

                this.jogo.getBaralhoJogo().recebeCarta(jogadorAtual.getCartasMao().descarta(indexCartaSel));
                System.out.println("Jogada realizada com sucesso.");

                // verificacao +2
                addCartas(cartaRodadaAtual, cartaSel, jogadorAtual, "+2");
                // verificacao +4
                addCartas(cartaRodadaAtual, cartaSel, jogadorAtual, "+4");
                return true;
            } else if (retorno.equals("N")) {
                System.out.println("Jogada cancelada.");
                return false;
            } else {
                System.out.println("Resposta invalida, jogada cancelada.");
                return false;
            }
        }
        return false;
    }

    public void addCartas(Carta cartaRodadaAtual, Carta cartaSel, Jogador jogadorAtual, String valor) {
        if ((cartaRodadaAtual.getValor() == valor && cartaSel.getValor() != valor) || valor == "") {
            for (int i = 0; i < this.addCartas; i++) {
                compraCarta(jogadorAtual);
            }
            System.out.println(this.addCartas + " adicionadas ao baralho de " + jogadorAtual.getNome());
            this.addCartas = 0;
        }
    }

    public boolean compraCarta(Jogador jogadorAtual) {
        boolean valido = false;
        try {
            while (valido == false) {
                int qtdCartasComprar = this.jogo.getBaralhoComprar().getQtdCartas();
                // se qtd de cartas em comprar for 0, adiciona itens do baralho do jogo
                if (qtdCartasComprar == 0) {
                    // this.jogo.getQtdCartas()-2 p/ ir ate o penultimo, mantendo a ultima carta
                    int qtdCartasJogo = this.jogo.getBaralhoJogo().getQtdCartas();
                    qtdCartasJogo -= 1;
                    if (qtdCartasJogo == 0) {
                        throw new Exception(
                                "Não tem cartas no baralho de comprar e não tem cartas no baralho do jogo para serem extraídas e tranferidas para o baralho de compra.");
                    }
                    for (int j = 0; j < qtdCartasJogo; j++) {
                        // SMP DESCARTA DO 0 P/ PEGAR A PRIMEIRA CARTA, OU SEJA A Q ESTA POR BAIXO
                        this.jogo.getBaralhoComprar().recebeCarta(this.jogo.getBaralhoJogo().descarta(0));
                    }
                    this.jogo.getBaralhoComprar().embaralha();
                    System.out.println("Baralho de compra recebeu cartas do baralho do jogo e foi embaralhado.");
                } else {
                    jogadorAtual.getCartasMao()
                            .recebeCarta(this.jogo.getBaralhoComprar().descarta(qtdCartasComprar - 1));
                    valido = true;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void printComandosEsp() {
        System.out.println("");
        System.out.println("Use um dos comandos especiais:");
        for (int i = 0; i < comandoEsp.length; i++) {
            System.out.println("(" + (i + 500) + ") " + comandoEsp[i].toString());
        }
        System.out.println("----------------------------------------------");
    }

    public void printCores() {
        System.out.println("Cores:");
        for (int i = 0; i < cartaCores.length; i++) {
            System.out.println("(" + i + ") " + cartaCores[i].toString());
        }
        System.out.println("----------------------------------------------");
    }

    public int indexCartaSel(Jogador jogadorAtual, Carta cartaRodadaAtual) {
        int indexSel = 0;
        boolean valido = false;
        while (valido == false) {
            // pulando linhas
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("Carta na mesa:");
            printCarta(cartaRodadaAtual);
            System.out.println("");
            System.out.println(jogadorAtual.getNome());
            System.out.println("Por favor selecione uma carta, digite o index, ele esta entre ():");
            System.out.println("");
            jogadorAtual.getCartasMao().printCartas();
            System.out.println("----------------------OU----------------------");
            printComandosEsp();

            // Recuperando int do index da carta seleciona pelo jogador
            String retorno = tecladoJogo.next();
            try {
                indexSel = Integer.parseInt(retorno);
                // tratando comandos
                if (indexSel == 500) {
                    valido = true;
                } else if (indexSel == 501) {
                    if (compraCarta(jogadorAtual)) {
                        System.out.println("Compra de carta realizada.");
                    } else {
                        System.out.println("Falha na compra de cartas");
                    }
                } else if (indexSel == 502) {
                    valido = true;
                } else {
                    if (indexSel < 0 || indexSel > jogadorAtual.getCartasMao().getQtdCartas() - 1) {
                        System.out.println("Valor invalido, por favor selecione uma carta valida:");
                    } else {
                        valido = true;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Valor invalido, por favor selecione uma carta valida:");
            }
        }
        return indexSel;
    }

    public void printCarta(Carta carta) {
        System.out.println("");
        System.out.println(carta.getCartaString(this.corSelecionada));
        System.out.println("");
    }

    public int funcoesEsp(Carta cartaRodadaAtual, int indexJogador) {
        // apenas executar funcoes se nao tiver pulo de rodada por um jogador
        if (this.codEsp != 500) {
            // invertendo sentido do jogo
            if (cartaRodadaAtual.getValor() == "Inverter") {
                if (this.sentidoJogo == 1) {
                    this.sentidoJogo = 0;
                } else {
                    this.sentidoJogo = 1;
                }
            }

            // adicionando 2 cartas (+2)
            if (cartaRodadaAtual.getValor() == "+2") {
                this.addCartas += 2;
            }

            // adicionando 4 cartas (+4)
            if (cartaRodadaAtual.getValor() == "+4") {
                this.addCartas += 4;
            }

            // pulando 1 jogador
            if (cartaRodadaAtual.getValor() == "Bloquear") {
                indexJogador = defineIndexProxJogador(indexJogador, sentidoJogo);
            }
        }
        this.codEsp = 0;
        return indexJogador;
    }

    public void realizaJogada(Carta cartaRodadaAtual, Jogador jogadorAtual) {
        boolean valido = false;
        while (valido == false) {
            // pega index da carta selecionada
            int indexCartaSel = indexCartaSel(jogadorAtual, cartaRodadaAtual);
            if (indexCartaSel == 500) {
                System.out.println(jogadorAtual.getNome() + " optou por Pular Jogada.");
                this.codEsp = 500;
                addCartas(cartaRodadaAtual, null, jogadorAtual, "");
                valido = true;
            } else if (indexCartaSel == 502) {
                System.out.println("Finalizando jogo...");
                this.finalizaJogo = true;
                valido = true;
            } else {
                // pega carta selecionada
                Carta cartaSel = new Carta(jogadorAtual.getCartasMao().getCartas().get(indexCartaSel));
                // compara cartas
                if (comparaCartas(cartaRodadaAtual, cartaSel)) {
                    valido = confirmJogada(jogadorAtual, indexCartaSel, cartaRodadaAtual, cartaSel);
                } else {
                    System.out.println(jogadorAtual.getNome()
                            + ", por favor selecione uma carta valida para a jogada, digite o index, ele esta entre ():");
                }
            }
        }
    }

    public void selecionaCor() {
        boolean valido = false;
        int indexCorSel = 0;
        while (valido == false) {
            System.out.println("");
            System.out.println("Selecione uma cor:");
            printCores();

            // Recuperando int do index da cor seleciona pelo jogador
            String retorno = tecladoJogo.next();
            try {
                indexCorSel = Integer.parseInt(retorno);
                if (indexCorSel < 0 || indexCorSel > cartaCores.length - 1) {
                    System.out.println("Valor invalido, por favor selecione uma cor valida:");
                } else {
                    System.out.println("Cor " + cartaCores[indexCorSel].toString() + " selecionada!");
                    this.corSelecionada = cartaCores[indexCorSel].toString();
                    valido = true;
                }
            } catch (Exception ex) {
                System.out.println("Valor invalido, por favor selecione uma cor valida:");
            }
        }
    }

    // destino=carta que ja esta no baralho
    // transf=carta que deseja entrar no baralho
    public boolean comparaCartas(Carta destino, Carta transf) {
        String destinoCor = "";
        if (destino.getCor() == "Especial") {
            destinoCor = this.corSelecionada;
            // se no inicio, cair a primeira carta multicolorida, nao vai ter cor
            // selecionada
            if (destinoCor == "" || destinoCor == null) {
                return true;
            }
        } else {
            destinoCor = destino.getCor();
        }
        if (destinoCor == transf.getCor() || destino.getValor() == transf.getValor()) {
            return true;
        } else if (transf.getCor() == "Especial") {
            selecionaCor();
            return true;
        }
        return false;
    }

    // i ==> 1 = soma | 0 = subtracao
    public int defineIndexProxJogador(int indexAtual, int i) {
        int indexUltimo = this.jogo.getPessoas().ultimoIndex();
        int indexProx = 0;
        if (i == 0) {
            if (indexAtual != indexUltimo) {
                indexProx = indexAtual += 1;
            }
        } else {
            if (indexAtual != 0) {
                indexProx = indexAtual -= 1;
            } else {
                indexProx = indexUltimo;
            }
        }
        return indexProx;
    }

    public void printRegras() {
        System.out.println("Regras:");
        System.out.println("Todo conteudo devera ser inserido no programa utilizando CAPS LOCK!");
        System.out.println(
                "Na jogada onde voce passara a ter apenas 1 carta, voce deve confirmar escrevendo UNO ao inves de S!");
        System.out.println("");
    }

    public void start() {
        int indexJogador = 0;

        printRegras();
        
        this.jogo = new Mesa();

        this.finalizaJogo = false;
        while (getVencedorNome() == "" && this.finalizaJogo == false) {

            Carta cartaRodadaAtual = new Carta(
                    this.jogo.getBaralhoJogo().getCartas().get(this.jogo.getBaralhoJogo().ultimoIndex()));

            System.out.println("Carta na mesa:");
            printCarta(cartaRodadaAtual);

            // executa funcoes e altera index do jogador atual caso o comando for p bloquear
            indexJogador = funcoesEsp(cartaRodadaAtual, indexJogador);

            // retorna novo index do prox jogador analisando a variavel de sentidoJogo
            int indexProxJogador = defineIndexProxJogador(indexJogador, sentidoJogo);

            Jogador jogadorAtual = new Jogador(this.jogo.getPessoas().getJogadores().get(indexProxJogador));

            realizaJogada(cartaRodadaAtual, jogadorAtual);

            // definindo vencedor
            if (jogadorAtual.getCartasMao().getQtdCartas() == 0) {
                this.vencedor = jogadorAtual;
            }

            indexJogador = indexProxJogador;
        }

        System.out.println(
                (getVencedorNome() == "" ? "Sem vencedor" : "Parabens " + getVencedorNome() + " voce venceu!"));

    }

}