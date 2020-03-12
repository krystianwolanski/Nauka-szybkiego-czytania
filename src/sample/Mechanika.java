package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mechanika {

    static Random generator;
    static int losowy_index;
    static int dlugosc_slowa;
    static List<Integer> lista_losowych_indexow;

    static int zwroc_losowy_index() {

        losowy_index = generator.nextInt(dlugosc_slowa);

        if (!lista_losowych_indexow.contains(losowy_index)) {
            lista_losowych_indexow.add(losowy_index);
            return losowy_index;
        } else
            return zwroc_losowy_index();
    }

    static int ile_tej_samej(int x, int y) {
        Random generator = new Random();

        return generator.nextInt(y - x + 1) + x;
    }

    static int max;
        static String generuj_ciag_znakow() {
        generator = new Random();

        dlugosc_slowa = generator.nextInt(Controller.do_ilu_liter_slowo - Controller.od_ilu_liter_slowo + 1) + Controller.od_ilu_liter_slowo;

        if(dlugosc_slowa>max)
            max=dlugosc_slowa;

        char tablica_znakow[] = new char[dlugosc_slowa];
        String znaki_w_string[] = new String[dlugosc_slowa];

        int w_ile_miejsc_wstawic_losowa = dlugosc_slowa - ile_tej_samej(Controller.od_ilu_tych_samych_liter, Controller.do_ilu_tych_samych_liter);

            lista_losowych_indexow = new ArrayList<>();

            for (int j = 0; j < w_ile_miejsc_wstawic_losowa; j++) {
                int nr_ascii;
                do {
                    nr_ascii = generator.nextInt(26) + 65;
                } while (nr_ascii == Controller.litera);

                tablica_znakow[zwroc_losowy_index()] = (char) nr_ascii;
            }

            for (int j = 0; j < dlugosc_slowa; j++)
                if (tablica_znakow[j] == '\u0000'){
                    tablica_znakow[j] = Controller.litera;
                    znaki_w_string[j]=Controller.litera.toString();
                }


            String slowo = String.copyValueOf(tablica_znakow);

        return slowo;
    }

    static List<String> lista_slow = new ArrayList<>();
    static void zapisz_slowo_do_tablicy(){
        lista_slow.add(generuj_ciag_znakow());
    }
    static boolean inicjalizacja = false;
    static Character litera;
    static int wyrazy=0;
    static File ciagi_znakow_file;

    static FileWriter fileWriter;
    static BufferedWriter buffer;
    static  PrintWriter printWriter;

    static void create_file() throws IOException {
        ciagi_znakow_file = new File(System.getProperty("user.home")+"\\Desktop\\Ciagi_znakow.txt");

        fileWriter = new FileWriter(ciagi_znakow_file,true);
        buffer = new BufferedWriter(fileWriter);
        printWriter = new PrintWriter(buffer);
    }

    static void zapisz_do_pliku() throws IOException {

        Character litera_po_uru = Controller.litera;

        if(!inicjalizacja){
            printWriter.print("Skreśl literę "+Controller.litera+":");
            printWriter.println();
            printWriter.println();
            litera=Controller.litera;
            inicjalizacja=true;
        }
        if(litera_po_uru!=litera){
            wyrazy=0;
            printWriter.println();
            printWriter.println();
            printWriter.print("Skreśl literę "+Controller.litera+":");
            printWriter.println();
            printWriter.println();
            litera=Controller.litera;
        }

        for (String slowo : lista_slow){

            for(int i=0;i<max-slowo.length();i++)
                if(wyrazy==0)
                printWriter.print(" ");

            printWriter.print(slowo);
            wyrazy++;
            if(wyrazy==2){
                printWriter.println();
                wyrazy=0;
            }else
                printWriter.print(" "+(char)183+" ");
        }
        lista_slow.clear();

        printWriter.close();
        buffer.close();
        fileWriter.close();


    }
}