package com.meta.metadataserv.security;

import java.io.IOException;
import java.math.BigInteger;

import com.meta.metadataserv.utils.TransferUtil;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

public class SM2Utils {
    private static String PUBLIC_KEY = "04BB8B5B03C294516F210B9B32F2EB05EF48F6C13297D0D3B8EBACED26E69BE58E83536760DA9E124E88B5B221BFA9221D9175677A1A6F88D5A89B73397AB81979";
    private static String PRIVATE_KEY = "00AD357535672109AA739D8E98AA159A70727EF4B93B6CE2FFA57789E408000AF1";

    public static String getPublicKey() {
        return PUBLIC_KEY;
    }

    public static String getPrivateKey() {
        return PRIVATE_KEY;
    }

    // 生成随机秘钥对
    public static void generateKeyPair() {
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator
                .generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key
                .getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();

        System.out.println("公钥: " + TransferUtil.byteToHex(publicKey.getEncoded()));
        System.out.println("私钥: " + TransferUtil.byteToHex(privateKey.toByteArray()));
    }

    // 数据加密
    public static String encrypt(byte[] publicKey, byte[] data)
            throws IOException {
        if (publicKey == null || publicKey.length == 0) {
            return null;
        }

        if (data == null || data.length == 0) {
            return null;
        }

        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);

        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.Instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

        ECPoint c1 = cipher.Init_enc(sm2, userKey);
        cipher.Encrypt(source);
        byte[] c3 = new byte[32];
        cipher.Dofinal(c3);

        // System.out.println("C1 " + Util.byteToHex(c1.getEncoded()));
        // System.out.println("C2 " + Util.byteToHex(source));
        // System.out.println("C3 " + Util.byteToHex(c3));
        // C1 C2 C3拼装成加密字串
        return TransferUtil.byteToHex(c1.getEncoded()) + TransferUtil.byteToHex(source)
                + TransferUtil.byteToHex(c3);

    }

    // 数据解密
    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData)
            throws IOException {
        if (privateKey == null || privateKey.length == 0) {
            return null;
        }

        if (encryptedData == null || encryptedData.length == 0) {
            return null;
        }
        // 加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
        String data = TransferUtil.byteToHex(encryptedData);
        /***
         * 分解加密字串 （C1 = C1标志位2位 + C1实体部分128位 = 130） （C3 = C3实体部分64位 = 64） （C2 =
         * encryptedData.length * 2 - C1长度 - C2长度）
         */
        byte[] c1Bytes = TransferUtil.hexToByte(data.substring(0, 130));
        int c2Len = encryptedData.length - 97;
        byte[] c2 = TransferUtil.hexToByte(data.substring(130, 130 + 2 * c2Len));
        byte[] c3 = TransferUtil
                .hexToByte(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));

        SM2 sm2 = SM2.Instance();
        BigInteger userD = new BigInteger(1, privateKey);

        // 通过C1实体字节来生成ECPoint
        ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.Init_dec(userD, c1);
        cipher.Decrypt(c2);
        cipher.Dofinal(c3);

        // 返回解密结果
        return c2;
    }

    public static String encrypt(String data)
            throws IOException {
        return encrypt(TransferUtil.hexToByte(getPublicKey()), data.getBytes());
    }

    // 数据解密
    public static String decrypt(String encryptedData)
            throws IOException {
        return new String(SM2Utils.decrypt(TransferUtil.hexToByte(getPrivateKey()),
                TransferUtil.hexToByte(encryptedData)));
    }

    public static void main(String[] args) throws Exception {
        // 生成密钥对
        generateKeyPair();

        String plainText = "ererfeiisgodytytytyt";

        // 下面的秘钥可以使用generateKeyPair()生成的秘钥内容
        // 国密规范正式私钥
        String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        // 国密规范正式公钥
        String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";

        System.out.println("加密: ");
        String cipherText = SM2Utils.encrypt(plainText);
        System.out.println(cipherText);
        System.out.println("解密: ");
        plainText = SM2Utils.decrypt(cipherText);
        System.out.println(plainText);

    }
}
