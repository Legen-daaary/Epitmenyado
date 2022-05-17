public class Telek {
    private int tulajdonosAdoszam;
    private String utcaNeve;
    private String hazszam;
    private char adosav;
    private int alapterulet;

    public Telek(int tulajdonosAdoszam, String utcaNeve, String hazszam, char adosav, int alapterulet) {
        this.tulajdonosAdoszam = tulajdonosAdoszam;
        this.utcaNeve = utcaNeve;
        this.hazszam = hazszam;
        this.adosav = adosav;
        this.alapterulet = alapterulet;
    }

    @Override
    public String toString() {
        return "Telek{" +
                "tulajdonosAdoszam=" + tulajdonosAdoszam +
                ", utcaNeve='" + utcaNeve + '\'' +
                ", hazszam='" + hazszam + '\'' +
                ", adosav=" + adosav +
                ", alapterulet=" + alapterulet +
                '}';
    }

    public int getTulajdonosAdoszam() {
        return tulajdonosAdoszam;
    }

    public String getUtcaNeve() {
        return utcaNeve;
    }

    public String getHazszam() {
        return hazszam;
    }

    public char getAdosav() {
        return adosav;
    }

    public int getAlapterulet() {
        return alapterulet;
    }
}
