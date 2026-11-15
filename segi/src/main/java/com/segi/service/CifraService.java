package com.segi.service;

import java.nio.charset.StandardCharsets;

public class CifraService {

    public String cifrar(String textoClaro, String chave) {

        // validação do tamanho da chave
        if (textoClaro.length() != chave.length()) {
            throw new IllegalArgumentException("O texto claro e a chave devem ter o mesmo tamanho!");
        }

        // getBytes converte cada caractere da String em um byte correspondente ao código ASCII
        byte[] textoBytes = textoClaro.getBytes(StandardCharsets.US_ASCII);
        byte[] chaveBytes = chave.getBytes(StandardCharsets.US_ASCII);

        // acumula os resultados de cada iteração do for, até formar o texto cifrado completo
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < textoBytes.length; i++) {
            byte cifrado = (byte) (textoBytes[i] ^ chaveBytes[i]); // XOR

            // binario armazena o resultado individual de cada XOR
            // e converte o resultado do XOR para binário de 8 bits
            String binario = String.format("%8s", Integer.toBinaryString(cifrado & 0xFF))
                    .replace(' ', '0');

            resultado.append(binario);
        }

        return resultado.toString();
    }
    public String decifrar(String textoCifrado, String chave) {

        // verifica se o texto cifrado que é do mesmo tamanho que a chave
        if (textoCifrado.length() != chave.length() * 8) {
            throw new IllegalArgumentException("O texto cifrado deve ter 8 bits para cada caractere da chave!");
        }
        if (!textoCifrado.matches("[01]+")) {
            throw new IllegalArgumentException("Texto com caracteres especiais");
        }
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < chave.length(); i++) {
            String grupo = textoCifrado.substring(i * 8, (i*8) + 8);
            int binParaDecimal = Integer.parseInt(grupo, 2);
            int xorDecChave = binParaDecimal ^ chave.charAt(i);
            char letra = (char)xorDecChave;
            resultado.append(letra);
        }
        return resultado.toString();
    }

}
