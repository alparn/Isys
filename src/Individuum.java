
import java.util.Random;

/**
 * Klasse die ein Individuum repraesentiert
 * @author Ali Parnan , Martin Dolacinski
 */
public class Individuum {

    private int id;
    public boolean empfaenglich;
    private Individuum meinungVon;
    private boolean meinungA;
    public static double p = 0.0199;
    private int Tage = 5;
    private Integer empfaenglichkeitsdauer = Tage;

    /**
     * Konstruktor der klasse Individuum
     * @param id id vom Individuum
     * @param empfaenglich Empfaenglichkeit der Individuum
     * @param meinungA Boolean wert de
     */
    public Individuum(int id, boolean empfaenglich, boolean meinungA) {
        this.id = id;
        this.empfaenglich = empfaenglich;
        this.meinungA = meinungA;


    }

    /**
     * In der Methode übernehmen Personen mit der Wahrscheinlichkeit p die Meinung A
     */

    public void konvertiert() {
        Random ran = new Random();
        if (ran.nextDouble() < p) {
            this.meinungA = true;
        }
    }

    /**
     *  Aktualiesert der Empfaenglichkeit der Individuen
     * @return gibt die Aktuelle Empfaengloichkeit status zurueck
     */

    public boolean zeitZurMeinungsbildung() {

        if (this.empfaenglichkeitsdauer != 1) {
            empfaenglichkeitsdauer--;
        } else {
            empfaenglichkeitZurucksetzen();
        }
        return this.empfaenglich;
       
    }

    /**
     * setzt die Empfaenglichkeit zurueck
     */

    private void empfaenglichkeitZurucksetzen() {
        empfaenglichkeitsdauer = 5;
        empfaenglich = false;
        meinungVon=null;
    }

    /**
     * Zwei Individuen treffen sich und beeinflussen sich gegenseitig bei jedem Meinungs- und Empfaengniszustand.
     * @param other andere Person, die diese Person trifft.
     */
    public void treffen(Individuum other) {

                zeitZurMeinungsbildung();

                if (this.meinungA == true || other.meinungA == false) {
                    return;
                }

                if (other.meinungA == true && this.empfaenglich==false && this.meinungA==false ) {
                    this.empfaenglich = true;
                    this.meinungVon = other;

                } else if (other.meinungA == true && this.empfaenglich==true&& this.meinungVon!=other) {
                    this.meinungA = true;
                    this.meinungVon = other;
                }

    }

    /**
     * gibt den boolschen Wert des aktuellen Individuum zurück.
     * @return
     */
    public boolean isMeinungA() {

        return this.meinungA;
    }


}
