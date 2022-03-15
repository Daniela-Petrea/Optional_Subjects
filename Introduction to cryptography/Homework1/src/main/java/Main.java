import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Citire obiect = new Citire();
        String mesaj = obiect.citire();
        System.out.println(obiect.filtrare(mesaj));
        mesaj = obiect.filtrare(mesaj);
        Random lungimeRandom = new Random();
        int lungime = lungimeRandom.nextInt(2, 10);
        String cuvantulCheie = obiect.create(lungime);
        //String cuvantulCheie="abcabcabcabd";
        // String cuvantulCheie="abababb";
        System.out.println("Cuvantul cheie este " + cuvantulCheie);
        String cheie = obiect.genereazaCheia(mesaj, cuvantulCheie);
        String criptotext = obiect.criptare(mesaj, cheie);
        System.out.println(criptotext);
        System.out.println("Lungimea cheii este " + obiect.gasireLungimeCheie(criptotext));
        int lg=obiect.gasireLungimeCheie(criptotext);
        System.out.println("Cheia gasita este " + obiect.gasireCheie(mesaj, criptotext, lg));
    }
}