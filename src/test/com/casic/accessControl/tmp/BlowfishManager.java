package com.casic.accessControl.tmp;

/**
 * Created by lenovo on 2016/12/22.
 */
public class BlowfishManager {

    public static BlowfishManager BRIDGELI_CN =  new BlowfishManager("bridgeli_cn!@#$abc123_");

    private BlowfishManager(String secret) {
        this.blowfish = new BlowFish(secret);
    }

    private BlowFish blowfish;

    public BlowFish getBlowfish() {
        return blowfish;
    }

    /**
     * 解密
     * @param sCipherText
     * @return
     */
    public String decryptString(String sCipherText){
        return this.getBlowfish().decryptString(sCipherText);
    }

    /**
     * 加密
     * @param sPlainText
     * @return
     */
    public String encryptString(String sPlainText){
        return this.getBlowfish().encryptString(sPlainText);
    }
    //f79142a6b602c9fac2a3757e014b3bd6ac909e271bc5c924ac26b8145f065e63
//2a6ceb7c9d1f00cb209fbf7ff762d14f1958c22e1a2de25e7ea273e6c22ec2b1
    public static void main(String[] args) {
        String encryptString = BlowfishManager.BRIDGELI_CN.encryptString("人无千日好，花无百日红");
        String encryptString2 = BlowfishManager.BRIDGELI_CN.encryptString("人无千日好，花无百日红");
        System.out.println(encryptString);
        System.out.println(encryptString2);
        String decryptString = BlowfishManager.BRIDGELI_CN.decryptString(encryptString);
        String decryptString2 = BlowfishManager.BRIDGELI_CN.decryptString(encryptString2);
        String decryptString3 = BlowfishManager.BRIDGELI_CN.decryptString("f79142a6b602c9fac2a3757e014b3bd6ac909e271bc5c924ac26b8145f065e63");
        System.out.println(decryptString);
        System.out.println(decryptString2);
        System.out.println(decryptString3);
    }
}
