package br.com.jogo;

public class Carta {
    private String cor;
    private String valor;

    public Carta(String corCarta, String valorCarta) {
        this.cor = corCarta;
        this.valor = valorCarta;
    }

    public Carta(Carta cartaAtual) {
        this.cor = cartaAtual.cor;
        this.valor = cartaAtual.valor;
    }

    public String getCartaString(String corEspecial) {
        return "Cor: " + (this.cor == "Especial" ? "Especial" + (corEspecial.isEmpty() ? "" : " (" + corEspecial + ")")
                : this.cor) + " | Valor: " + this.valor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCor() {
        return this.cor;
    }

    public String getValor() {
        return this.valor;
    }
}