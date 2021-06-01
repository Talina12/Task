package org.Dudnik.database.converters;

import lombok.SneakyThrows;
import org.Dudnik.config.GlobalProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordConverter implements AttributeConverter<String,String> {
    @Autowired
    GlobalProperties globalProperties;

    private Key key;
    private Cipher encriptCipher;
    private Cipher decryptCipher;

    PasswordConverter() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        /*
        Key key = new SecretKeySpec(globalProperties.getKey().getBytes(),
                                    globalProperties.getEncriptionAlgorithm());//actually i don't know where is the best place for save encryption algorithm and key
        encriptCipher = Cipher.getInstance(globalProperties.getEncriptionAlgorithm());
        encriptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance(globalProperties.getEncriptionAlgorithm());
        decryptCipher.init(Cipher.DECRYPT_MODE, key);*/

        Key key = new SecretKeySpec("keykeykeykeykeyk".getBytes(),
                "AES");// needs to fix creation of GobalProperties object
                               // actually i don't know where is the best place for saving encryption algorithm and key
        encriptCipher = Cipher.getInstance("AES");
        encriptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }
    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(String password) {
        return Base64.getEncoder().encodeToString(encriptCipher.doFinal(password.getBytes()));
    }

    @SneakyThrows
    @Override
    public String convertToEntityAttribute(String dbData) {
        return new String(decryptCipher.doFinal(Base64.getDecoder().decode(dbData)));
    }
}
