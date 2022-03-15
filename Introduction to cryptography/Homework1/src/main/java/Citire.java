import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Citire {


    public String citire() {
        try {
            String fisier = "";
            File myObj = new File("filename.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fisier += data;
            }
            myReader.close();
            return fisier;
        } catch (FileNotFoundException e) {
            return ("An error occurred.");
        }
    }

    public String filtrare(String k) {
        String m = "";
        for (int i = 0; i < k.length(); ++i) {
            if (k.charAt(i) >= 'A' && k.charAt(i) <= 'Z')
                m += Character.toLowerCase(k.charAt(i));
            else if (k.charAt(i) >= 'a' && k.charAt(i) <= 'z')
                m += k.charAt(i);

        }
        return m;
    }

    public String create(int p) {
        String lettersArray = "abcdefghijklmnopqrstuvwxyz";
        String sb = "";
        for (int i = 0; i < p; i++) {
            int index = (int) (lettersArray.length() * Math.random());
            sb += lettersArray.charAt(index);
        }
        return sb;
    }


    public double miniSir(String k, int lg) {
        double sumaDeIc = 0.0;
        int len = k.length();
        for (int j = 0; j < lg; j++) {
            String sirFinal = "";
            for (int i = j; i < len; i = i + lg) {
                sirFinal += k.charAt(i);
            }
            sumaDeIc += calculIndexDeCoincidenta(sirFinal);
        }
        double medieDeIc = 0.0;
        medieDeIc = sumaDeIc / lg;
        return (medieDeIc);
    }

    public String miniSirDinNou(String text, int lgCheie, int j) {
        String sirFinal = "";
        for (int i = j; i < text.length(); i = i + lgCheie) {
            sirFinal += text.charAt(i);
        }
        return sirFinal;
    }

    public double calculIndexDeCoincidenta(String k) {
        int i;
        double suma = 0.0;
        //initializam vectorul frecventa pt a numara frecventa fiecarei litere
        int[] frecventa = new int[26];
        for (i = 0; i < 26; i++) {
            frecventa[i] = 0;
        }

        //calculam frecventa fiecarei litere
        int ch;
        for (i = 0; i < k.length(); i++) {
            ch = k.charAt(i) - 97;
            if (ch >= 0 && ch < 26) {
                frecventa[ch]++;
            }
        }

        //calculam suma pt fiecare litera
        for (i = 0; i < 26; i++) {
            ch = frecventa[i];
            suma = suma + (ch * (ch - 1));
        }
        double ic = 0.0;
        //aplicam formula
        ic = suma / (k.length() * (k.length() - 1));
        return ic;

    }

    public String genereazaCheia(String k, String cheie) {
        int x = k.length();

        for (int i = 0; ; i++) {
            if (x == i)
                i = 0;
            if (cheie.length() == k.length())
                break;
            cheie += (cheie.charAt(i));
        }
        return cheie;
    }

    public String criptare(String str, String key) {
        String criptotext = "";

        for (int i = 0; i < str.length(); i++) {
            int x = (str.charAt(i) + key.charAt(i)) % 26;
            x += 'a';
            criptotext += (char) (x);
        }
        return criptotext;
    }

    public int gasireLungimeCheie(String k) {
        int index = 2;
        while (miniSir(k, index) < 0.06 || miniSir(k, index) > 0.08) {
            index++;
        }
        System.out.println("Indicele de coincidenta este " + miniSir(k, index));
        return index;
    }

    public String shiftSir(String sirIntermediar) {
        String nou = "";
        for (int i = 0; i < sirIntermediar.length(); i++) {
            if (sirIntermediar.charAt(i) == 'z')
                nou += 'a';
            else
                nou += (char) (sirIntermediar.charAt(i) + 1);
        }
        return nou;
    }

    public String gasireCheie(String mesajInitial, String criptotext, int lgCheie) {
        String cheieFinala = "";
        String sirIntermediar = "";
        for (int j = 0; j < lgCheie; j++) {
            int s = -1;
            do {
                if (s == -1) {
                    sirIntermediar = miniSirDinNou(criptotext, lgCheie, j);
                }
                s = s + 1;
                sirIntermediar = shiftSir(sirIntermediar);
            }
            while (!(miniSirDinNou(mesajInitial, lgCheie, j).equals(shiftSir(sirIntermediar))) && (s <= 25));
            if (s >= 26) {
                System.out.println("eroare, lungime cheie gasita eronat");
                System.exit(-1);
            }
            int x;
            if (s < 13)
                x = (26 - s) % 26 - 14;
            else
                x = (26 - s) % 26 + 12;
            if (s == 0)
                x = 12;
            cheieFinala += (char) (97 + x);
        }
        return cheieFinala;
    }

    public double indiceDeCoincidentaMutuala(String a, String b) {
        int i;
        double suma = 0.0;
        //initializam vectorul frecventa pt a numara frecventa fiecarei litere
        int[] frecventa1 = new int[26];
        int[] frecventa2 = new int[26];
        for (i = 0; i < 26; i++) {
            frecventa1[i] = 0;
            frecventa2[i] = 0;
        }

        //calculam frecventa fiecarei litere
        int ch;
        for (i = 0; i < a.length(); i++) {
            ch = a.charAt(i) - 97;
            if (ch >= 0 && ch < 26) {
                frecventa1[ch]++;
            }
        }
        for (i = 0; i < b.length(); i++) {
            ch = b.charAt(i) - 97;
            if (ch >= 0 && ch < 26) {
                frecventa2[ch]++;
            }
        }

        //calculam suma pt fiecare litera
        for (i = 0; i < 26; i++) {
            suma = suma + (double) (frecventa1[i] * frecventa2[i]) / (a.length() * b.length());
        }
        return suma;
    }
}

