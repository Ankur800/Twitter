package tech.codingclub.helix.encryptionDecryption;

public class Encryption {
    private String password;
    private int encryptionID;

    public Encryption(String password, int encryptionID){
        this.password=password;
        this.encryptionID=encryptionID;
    }

    public String encrypt(){
        String encryptedPassword = "";
        int len = password.length();
        for(int i=0;i<len;i++){
            char temp = password.charAt(i);
            temp = (char) ((int)temp + encryptionID);
            encryptedPassword += temp;
        }
        return encryptedPassword;
    }
}
