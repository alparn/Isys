import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class main {
    private static int zeitraum = 200;
    public static double p = 0.0035;
    public static List <Individuum> individuen;


    /**
     * Aufruf der Simulation
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        abhaengig();
        unabhaengig();

    }

    /**
     * Methode zur initialiesierung der Exprimente
     * @param days Anzahl der Tagen
     * @param iteration Anzahl der Iteration
     * @return
     */
    public static List <List <Long>> initExpriment(final int days, final int iteration) {
        return IntStream.range(0, days).mapToObj(d -> new ArrayList <Long>(iteration)).collect(Collectors.toList());
    }

    /**
     * Methode zur Berechnung der durchgschnittliche Wert
     * @param expHistory Liste, die Tagen und Anzahl  von Iterationen enthaelt
     * @return Durchnittliche Wert der Iterationen pro Tag
     */
    public static List <Double> avgExpriment(final List <List <Long>> expHistory) {
        return expHistory.stream().map(day -> day.stream().mapToDouble(i -> i).average().getAsDouble()).collect(Collectors.toList());
    }

    /**
     * Initialieserung der Unabaengigen Individuen
     */

    public static void initUnabhaengig() {
        individuen = new ArrayList <>(50);
        for (int i = 0; i < 50; i++) {
            individuen.add(new Individuum(i, false, false));
        }
    }

    /** Initialieserung der abaengigen Individuen
     *
     */

    public static void initabhaengig() {
        individuen = new ArrayList <>(50);
        for (int i = 0; i < 50; i++) {
            if (i < 3) {

                individuen.add(new Individuum(i, false, true));

            } else {
                individuen.add(new Individuum(i, false, false));

            }
        }
    }

    /**
     * Simulieren der Expriment fuer abhaengig und unabhaengige Individuen und Speichern der Daten in CSV Format
     * @param historyExpriment Gespeicherten Werten pro Iteration
     * @param unabhaengig Boolean zu unterscheidung der CSV datei
     * @throws IOException
     */
    public static void simulator(List <List <Long>> historyExpriment, boolean unabhaengig) throws IOException {
        Map <Integer, Double> simulatorList = new HashMap <>();
        int count = 0;
        for (Double s : avgExpriment(historyExpriment)) {
            count++;
            System.out.println("Mittelwert der Meinung A am Tag: " + count + " :" + s);
            simulatorList.put(count, s);
        }

        File file;
        file = unabhaengig == true ? new File("abhaengigResult.csv") : new File("unabhaengigResult.csv");
        FileWriter writer = new FileWriter(file);
        String fileHeader ="Tage,MeinungA";
        String lineSeperator="\n";
        writer.append(fileHeader.toString());
        writer.append(lineSeperator);
        StringBuilder sb = new StringBuilder();


        for (int i = 1; i <= count; i++) {
            sb.append(i);
            sb.append(",");
            sb.append(simulatorList.get(i));
            sb.append(lineSeperator);
        }
        writer.write(sb.toString());
        writer.close();
    }

    /**
     *  Simuliert die abhängige Meinungsbildung A in dieser Gruppe mit Wahrscheinlichkeit P.
     *  Jede Person trifft sich genau einmal am Tag durch Zufall mit einer anderen Person (P).
     * @throws IOException
     */
    public static void abhaengig() throws IOException {
        List <List <Long>> historyExpriment = initExpriment(zeitraum, 1000);
        Random random = new Random();
        initabhaengig();

        for (int a = 0; a < 1000; a++) {
            for (int day = 0; day < zeitraum; day++) {

                for (int l = 0; l < individuen.size(); l++) {
                    for (int k = l + 1; k < individuen.size(); k++) {

                        if (random.nextDouble() < p) {
                            individuen.get(l).treffen(individuen.get(k));
                            individuen.get(k).treffen(individuen.get(l));

                        }
                    }
                }

                historyExpriment.get(day).add(individuen.stream().filter(person -> person.isMeinungA()).count());
            }
            initabhaengig();
        }
        simulator(historyExpriment, true);
    }

    /**Simuliert die unabhängige Meinungsbildung A in dieser Gruppe mit Wahrscheinlichkeit P
     *
     * @throws IOException
     */
    public static void unabhaengig() throws IOException {

        List <List <Long>> historyExpriment = initExpriment(zeitraum, 1000);

        initUnabhaengig();
        for (int a = 0; a < 1000; a++) {
            for (int days = 0; days < zeitraum; days++) {
                individuen.forEach(Individuum::konvertiert);
                historyExpriment.get(days).add(individuen.stream().filter(person -> person.isMeinungA()).count());
            }
            initUnabhaengig();
        }
        simulator(historyExpriment, false);
    }

}


