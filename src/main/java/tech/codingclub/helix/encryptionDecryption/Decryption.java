package tech.codingclub.helix.encryptionDecryption;

public class Decryption {
    private String encryptedPassword;
    private int decryptionID;

    public Decryption(String encryptedPassword, int decryptionID) {
        this.encryptedPassword=encryptedPassword;
        this.decryptionID=decryptionID;
    }

    public String decrypt(){
        String password = "";
        int n = encryptedPassword.length();
        for(int i=0;i<n;i++){
            char ch = encryptedPassword.charAt(i);
            ch = (char) ((int)ch - decryptionID);
            password += ch;
        }
        return password;
    }
}
