
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import javax.crypto.*;
import java.util.*;
import java.io.*;

public class passenc {

     String FileName = "encryptedtext.txt"; //save encrypted text
        String FileName2 = "decryptedtext.txt"; //save decrypted text
	
	Cipher AesCipher ;
	byte[] byteText ;  
    public void enc(String tobe,String enctype)
    {
            
        try{
    	   //convert message to bytestream
	
        //AES cipher
                AesCipher= Cipher.getInstance(enctype);
		byteText=tobe.getBytes();
                KeyGenerator KeyGen = KeyGenerator.getInstance(enctype);  //define key  generator
		KeyGen.init(128);                                           //initalize key generator

		SecretKey SecKey = KeyGen.generateKey();                //private key algorithm AES only one key used

		AesCipher.init(Cipher.ENCRYPT_MODE, SecKey);            //initialize AES encryption
		byte[] byteCipherText = AesCipher.doFinal(byteText);    //perform encryption
		Files.write(Paths.get(FileName), byteCipherText);       //write result to file

		byte[] cipherText = Files.readAllBytes(Paths.get(FileName));

		AesCipher.init(Cipher.DECRYPT_MODE, SecKey);            //initialize AES decryption
		byte[] bytePlainText = AesCipher.doFinal(cipherText);   //perform decryption
		Files.write(Paths.get(FileName2), bytePlainText);       //write result to file

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }	
    public void end(String tobe)
    {
        try{
                AesCipher =Cipher.getInstance("RSA");
                byteText=tobe.getBytes();
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		KeyPair myPair = kpg.generateKeyPair();             
		
		AesCipher.init(Cipher.ENCRYPT_MODE, myPair.getPublic());        //get public key tp encrypt
		byte[] byteCipherText = AesCipher.doFinal(byteText);            //perform encryption
		Files.write(Paths.get(FileName), byteCipherText);               //write to file
		
		byte[] cipherText = Files.readAllBytes(Paths.get(FileName));   
		
		AesCipher.init(Cipher.DECRYPT_MODE, myPair.getPrivate());        //get private key to decrypt
		byte[] bytePlainText = AesCipher.doFinal(cipherText);           //perform decryption
		Files.write(Paths.get(FileName2), bytePlainText);
    }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        	passenc obj=new passenc();
        	System.out.println("Enter encryption type : 1 - AES 2 - RSA");
        	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                String enctype=br.readLine();
                
                System.out.println("Enter message :");
                String tobe=br.readLine();
                if(tobe.equals("AES")){
                obj.enc(tobe, enctype);
                }
                else{
                obj.end(tobe);
                }
    }
}