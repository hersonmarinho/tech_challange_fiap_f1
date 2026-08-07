package br.com.fiap.horadoprato.horadoprato.infra.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CriptografiaUtil {

    private static final String CHAVE_16_LETRAS = "MinhaChaveDe16Ch";
    private static final SecretKeySpec CHAVE = new SecretKeySpec(CHAVE_16_LETRAS.getBytes(), "AES");

    // ENCRIPTAR (Capturando a exceção para não sujar a Service)
    public static String encriptar(String texto) {
        if (texto == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, CHAVE);
            return Base64.getEncoder().encodeToString(cipher.doFinal(texto.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao encriptar a senha", e);
        }
    }

    public static String decriptar(String textoCriptografado) {
        if (textoCriptografado == null) return null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, CHAVE);
            return new String(cipher.doFinal(Base64.getDecoder().decode(textoCriptografado)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao decriptar a senha", e);
        }
    }
}
