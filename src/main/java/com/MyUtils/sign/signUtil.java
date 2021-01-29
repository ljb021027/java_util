package com.MyUtils.sign;

import org.bouncycastle.util.encoders.Base64;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * @author ljb
 * @since 2019/10/16
 */
public class signUtil {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {

        String encrypted = new String(Files.readAllBytes(Paths.get("E:\\IDE\\idea_workspace\\MyUtils\\src\\test\\privatekey-encrypted.pkcs8")));
        String unencrypted = new String(Files.readAllBytes(Paths.get("E:\\IDE\\idea_workspace\\MyUtils\\src\\test\\privatekey-unencrypted.pkcs8")));

        //Create object from unencrypted private key
        unencrypted = unencrypted.replace("-----BEGIN PRIVATE KEY-----", "");
        unencrypted = unencrypted.replace("-----END PRIVATE KEY-----", "");

//        unencrypted = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCPukrsOPCmzOHV/99an414Ls1joT7C9N/IX6yyAbhe09O7sMkbjSa7drlwpSL1fsX88wVDL9LLDH5EgaqoJt/sxhO2EGHATOlTzsPtgFLtEibT9PdI4sHoPpzKl89vK1yURnjRbNmxY4oDsqhDL9JF1fwDSqEUJ6OOMHlECgD4xClLZfiBlnMT7wHvsr6nz4Gqjsk+cYbdxYdlVicag1PxP2XDMLRXLeRmpEjBxO/D7AzcCYQ51M2fg9aQOD+k5JzQYibMv1FRePZMvDNQm82TwvYMD3D7WPvERoCHsKi1V8jgppn5cE6RBccWDNqRTlUWkmnuu353a+8muU8IHGwhAgMBAAECggEAFzxTznqqFR08SV9zWXA/67UNSVURE22JHBizj5eNme7+5PIAoS6hm17nPdnEnBGhqnITYLhDiMX9R8/6qfKW8u1W1HEzjGYitoTP0f6T6XWsx0lsDOFz3br5mBsQ0vUco2/9KkZBr1bbQhvI7gu2H8onUtTm055Q57TYdfJ0E8NMgpfb3dzMO9ii0veKAYwl1QzR8lnUrZp0mgt2dI3wrsuRSs2/HabEdIJOlAp9m6tKNe3SOENuJLOcOkv432vGs3abKtbZU+55f0zFr4z0fCLe/JmuRy13pInOXLX+h+YH2s8uuBbkeJq5h/0OfuldpCPJF0PMQO8xyBuM3AwIkQKBgQDO7qQkkbXhPRP4xbMs5eElOCfu7WwaM+QRtuxr6SG5kIoVAI+YE9yh21+PiR/f6lrFYbK4oi0XZX+ZyhCNz16etAqtvqQoSBwVAGLNDfgFDkiP1wQtJdfJ6sjUYDkYKLqBCk3bsXODc1G8UXJFgugVXG7MqT1Npm5x1Ym/gj14vQKBgQCxzvavGJ9/iGdUj2YdcB0bJ8v/FmJy4xHaFoC4wIiYZyEgL1vjIjGUnedC8C4ksreH9j4lZtsMBh8uHBh+EZ+ma3dHiSPtXGOu+TloqzLrjfmDrIK5/cPpSji3LxHPqfVC/An8RnNSAJtq2Ck/ycHugVjpMDVmtHGSgA1+FmNxNQKBgCcvNVHfbat7H18KQMWNujaMDbPGraPAk71vABHnEYXMAvm7I3XOvQbBPbU2aBzEie+6cldYDmXRyHncs4nG7MnsmeEQqpEeJoMrkYn2zTJX5BGoy5epBNWtad4dYnJatAZfZyLk1VTc72tTmOnkwgHZgPmsSnl7vIo9v1nOVcmZAoGACYaT8qd4DAYLuxz/lH4oIQdNx3m0hoEhuLRR6xPtfXSw8p925cE0NMkM1ao2XqLwcClCBRDwlUFStE6vBTn+epTxS/TN5u53LK2PZMsfLD1r66D3U+2DbGEcjrvH0X9Cc3c7Nbe0f+Umbl/sPc2aRSPK1ptNkRYDq5yOnH2okC0CgYBBXeqw7vmRobr0uxaPjBQu1tfG9SqArH8VHgVkK28RBJKjvogvxuc8FIlugdWirCwam5znZOnDfKCZ/UPHVkR6MIEs55yywyqyto3IwjXkXV2JMJFfIgAXaulszHNK4yuw4Ipw2DtFWg+JfremuEOhWwyqHKS31YzXnPSjqT7tZw==";
         byte[] encoded = Base64.decode(unencrypted);
        System.out.println(new String(encoded));
        PKCS8EncodedKeySpec kspec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey unencryptedPrivateKey = kf.generatePrivate(kspec);

        //Create object from encrypted private key
        encrypted = encrypted.replace("-----BEGIN ENCRYPTED PRIVATE KEY-----", "");
        encrypted = encrypted.replace("-----END ENCRYPTED PRIVATE KEY-----", "");
        EncryptedPrivateKeyInfo pkInfo = new EncryptedPrivateKeyInfo(Base64.decode(encrypted));
        PBEKeySpec keySpec = new PBEKeySpec("channel".toCharArray()); // password
        SecretKeyFactory pbeKeyFactory = SecretKeyFactory.getInstance(pkInfo.getAlgName());
        PKCS8EncodedKeySpec encodedKeySpec = pkInfo.getKeySpec(pbeKeyFactory.generateSecret(keySpec));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey encryptedPrivateKey = keyFactory.generatePrivate(encodedKeySpec);

        //comparing both private key for equality
        System.out.println(unencryptedPrivateKey.equals(encryptedPrivateKey));
    }
}
