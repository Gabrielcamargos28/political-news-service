package com.political_news_service.political_news_service.domain;

import lombok.Getter;

@Getter
public enum PoliticalSource {
    // Esquerda
    BRASIL_247("Brasil 247", "https://www.brasil247.com/feed", "ESQUERDA"),
    CARTA_CAPITAL("CartaCapital", "https://www.cartacapital.com.br/feed/", "ESQUERDA"),
    REVISTA_FORUM("Revista Fórum", "https://revistaforum.com.br/feed/", "ESQUERDA"),
    
    // Centro / Tradicionais
    G1_POLITICA("G1 Política", "https://g1.globo.com/rss/g1/politica/", "CENTRO"),
    G1_TRIANGULO("G1 Triângulo Mineiro", "https://g1.globo.com/rss/g1/minas-gerais/triangulo-mineiro/", "CENTRO"),
    PODER360("Poder360", "https://www.poder360.com.br/feed/", "CENTRO"),
    FOLHA("Folha de S.Paulo", "https://feeds.folha.uol.com.br/poder/rss091.xml", "CENTRO-ESQUERDA"),
    ESTADAO("Estadão", "https://politica.estadao.com.br/rss", "CENTRO-DIREITA"),

    // Direita
    GAZETA_DO_POVO("Gazeta do Povo", "https://www.gazetadopovo.com.br/feed/", "DIREITA"),
    REVISTA_OESTE("Revista Oeste", "https://revistaoeste.com/feed/", "DIREITA"),
    JOVEM_PAN("Jovem Pan", "https://jovempan.com.br/feed", "DIREITA");

    private final String name;
    private final String rssUrl;
    private final String bias;

    PoliticalSource(String name, String rssUrl, String bias) {
        this.name = name;
        this.rssUrl = rssUrl;
        this.bias = bias;
    }
}
