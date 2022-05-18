import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Epitmenyado {
    static List<Telek> telkek = null;
    static int[] adosavok;

    public static void main(String[] args) {
        //1. feladat
        String forrasNeve = "utca.txt";
        List<String> sorok = feltolt(forrasNeve);
        adosavok = getAdoSavok(sorok.remove(0));
        telkek = getTelkek(sorok);

        System.out.print("2. feladat. ");
        System.out.println("A mintában " + telkek.size() + " telek szerepel.");

        System.out.print("3. feladat. ");
        System.out.print("Egy tulajdonos adószáma: ");
        Scanner sc = new Scanner(System.in);
        int tulajdonosAdoszam = sc.nextInt();
        sc.close();
        epitmenyek(tulajdonosAdoszam);

        System.out.print("5. feladat");
        osszAdozas();

        System.out.println("6. feladat");
        felulvizsgalas();

        System.out.println("7. feladat");
        adoKiiras();
    }

    private static void adoKiiras() {
        HashMap<Integer, Integer> adozasTulajdonosonkent = new HashMap<>();

        for (Telek telek : telkek) {
            if (!adozasTulajdonosonkent.containsKey(telek.getTulajdonosAdoszam())) {
                adozasTulajdonosonkent.put(telek.getTulajdonosAdoszam(), 0);
            }
            int adoOsszeg = ado(telek.getAdosav(), telek.getAlapterulet());
            adozasTulajdonosonkent.put(telek.getTulajdonosAdoszam(),
                    adozasTulajdonosonkent.get(telek.getTulajdonosAdoszam()) + adoOsszeg);
        }

        List<String> adozoEsAdoja = new ArrayList<>();

        for (int adozo : adozasTulajdonosonkent.keySet()) {
            int osszesAdo = adozasTulajdonosonkent.get(adozo);

            adozoEsAdoja.add(adozo + " " + osszesAdo);
        }

        try {
            Files.write(Paths.get("fizetendo.txt"), adozoEsAdoja, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Hiba: " + e.getMessage());
        }
    }

    private static void felulvizsgalas() {
        TreeSet<String> felulVizsgalandoUtcak = new TreeSet<>();
        HashMap<String, Character> utcak = new HashMap<>();

        for (Telek telek : telkek) {

            if (utcak.containsKey(telek.getUtcaNeve())) {

                if (telek.getAdosav() != utcak.get(telek.getUtcaNeve())) {
                    felulVizsgalandoUtcak.add(telek.getUtcaNeve());
                }

            } else {
                utcak.put(telek.getUtcaNeve(), telek.getAdosav());
            }
        }

        felulVizsgalandoUtcak.forEach(System.out::println);
    }

    private static void osszAdozas() {
        TreeMap<Character, HashMap<String, Integer>> adozas = new TreeMap<>();
        String telkekSzama = "telkekSzama";
        String osszesAdo = "osszesAdo";

        for (Telek telek : telkek) {

            if (!adozas.containsKey(telek.getAdosav())) {
                adozas.put(telek.getAdosav(), new HashMap<String, Integer>());
            }

            HashMap<String, Integer> aktualisAdoSav = adozas.get(telek.getAdosav());
            if (!aktualisAdoSav.containsKey(telkekSzama)) {
                aktualisAdoSav.put(telkekSzama, 0);
            }
            aktualisAdoSav.put(telkekSzama, aktualisAdoSav.get(telkekSzama) + 1);

            if (!aktualisAdoSav.containsKey(osszesAdo)) {
                aktualisAdoSav.put(osszesAdo, 0);
            }
            int adoOsszeg = ado(telek.getAdosav(), telek.getAlapterulet());
            aktualisAdoSav.put(osszesAdo, aktualisAdoSav.get(osszesAdo) + adoOsszeg);
        }

        for (char savJel : adozas.keySet()) {
            HashMap<String, Integer> aktualisAdoSav = adozas.get(savJel);
            int telkekSzamaErtek = aktualisAdoSav.get(telkekSzama);
            int osszesAdoErtek = aktualisAdoSav.get(osszesAdo);

            System.out.println(savJel + " sávba " + telkekSzamaErtek + " esik, az adó " + osszesAdoErtek + " Ft.");
        }
    }

    //4. feladat
    private static int ado(char adosav, int alapterulet) {
        int adoOsszeg = 0;

        switch(adosav) {
            case 'A':
                adoOsszeg = adosavok[0] * alapterulet;
                break;
            case 'B':
                adoOsszeg = adosavok[1] * alapterulet;
                break;
            case 'C':
                adoOsszeg = adosavok[2] * alapterulet;
                break;
        }

        if (adoOsszeg < 10000) {
            adoOsszeg = 0;
        }

        return adoOsszeg;
    }

    private static void epitmenyek(int tulajdonosAdoszam) {
        List<String> tulajdonosEpitmenyei = new ArrayList<>();

        for (Telek telek : telkek) {
            if (tulajdonosAdoszam == telek.getTulajdonosAdoszam()) {
                tulajdonosEpitmenyei.add(telek.getUtcaNeve() + " utca " + telek.getHazszam());
            }
        }

        if (tulajdonosEpitmenyei.isEmpty()) {
            System.out.println("Nem szerepel az adatállományban.");
            return;
        }

        tulajdonosEpitmenyei.forEach(System.out::println);
    }

    private static List<Telek> getTelkek(List<String> sorok) {
        List<Telek> telkek = new ArrayList<>();

        for (String sor : sorok) {
            String[] darabok = sor.split(" ");
            int tulajdonosAdoszam = Integer.parseInt(darabok[0]);
            String utcaNev = darabok[1];
            String hazszam = darabok[2];
            char adosav = darabok[3].charAt(0);
            int alapterulet = Integer.parseInt(darabok[4]);
            Telek telek = new Telek(tulajdonosAdoszam, utcaNev, hazszam, adosav, alapterulet);

            telkek.add(telek);
        }

        return telkek;
    }

    private static List<String> feltolt(String forrasNeve) {
        List<String> sorok = null;

        try {
            sorok = Files.readAllLines(Paths.get(forrasNeve), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Hiba: " + e.getMessage());
        }

        return sorok;
    }

    private static int[] getAdoSavok(String sor) {
        int[] adosavok = new int[3];

        List<Integer> adosavokList = Arrays.stream(sor.split(" ")).map(Integer::parseInt).toList();

        for (int i = 0; i < adosavok.length; i++) {
            adosavok[i] = adosavokList.get(i);
        }

        return adosavok;
    }
}
