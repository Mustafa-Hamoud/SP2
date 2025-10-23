import java.util.ArrayList;

// ---------------------- SUPERKLASSE ----------------------
abstract class Title {
    protected String title;
    protected String literatureType;
    protected static final double RATE = 0.067574; // fast sats

    public Title(String title, String literatureType) {
        this.title = title;
        this.literatureType = literatureType;
    }

    // skal implementeres forskelligt i underklasser
    public abstract double calculatePoints();

    // konverterer litteraturtype til et tal
    public double convertLiteratureType() {
        if (literatureType.equalsIgnoreCase("BI")) {
            return 3.0;
        } else if (literatureType.equalsIgnoreCase("TE")) {
            return 3.0;
        } else if (literatureType.equalsIgnoreCase("LYRIK")) {
            return 6.0;
        } else if (literatureType.equalsIgnoreCase("SKØN")) {
            return 1.7;
        } else if (literatureType.equalsIgnoreCase("FAG")) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    // beregner beløbet ud fra point og rate
    public double calculateRoyalty() {
        return calculatePoints() * RATE;
    }
}

// ---------------------- PHYSICAL BOOK ----------------------
abstract class PhysicalBook extends Title {
    protected int copies; // antal eksemplarer

    public PhysicalBook(String title, String literatureType, int copies) {
        super(title, literatureType);
        this.copies = copies;
    }
}

// ---- PRINTED BOOK ----
class PrintedBook extends PhysicalBook {
    private int pages;

    public PrintedBook(String title, String literatureType, int copies, int pages) {
        super(title, literatureType, copies);
        this.pages = pages;
    }

    public double calculatePoints() {
        return pages * convertLiteratureType() * copies;
    }
}

// ---- AUDIO BOOK ----
class AudioBook extends PhysicalBook {
    private int durationInMinutes;

    public AudioBook(String title, String literatureType, int copies, int durationInMinutes) {
        super(title, literatureType, copies);
        this.durationInMinutes = durationInMinutes;
    }

    public double calculatePoints() {
        return (durationInMinutes / 2.0) * convertLiteratureType() * copies;
    }
}

// ---------------------- AUTHOR ----------------------
class Author {
    private String name;
    private ArrayList<Title> titles;

    public Author(String name) {
        this.name = name;
        this.titles = new ArrayList<>();
    }

    public void addTitle(Title title) {
        titles.add(title);
    }

    public double calculateTotalPay() {
        double total = 0;
        for (Title t : titles) {
            total = total + t.calculateRoyalty();
        }
        return total;
    }

    public String getName() {
        return name;
    }
}

// ---------------------- MAIN TEST ----------------------
public class LibraryRoyaltyCalculator {
    public static void main(String[] args) {
        Author olga = new Author("Olga Ravn");

        // Opret titler
        PrintedBook celestinePrint = new PrintedBook("Celestine", "SKØN", 140, 166);
        AudioBook celestineAudio = new AudioBook("Celestine", "SKØN", 140, 192);

        // Tilføj til forfatter
        olga.addTitle(celestinePrint);
        olga.addTitle(celestineAudio);

        // Beregn total og print resultat
        double total = olga.calculateTotalPay();
        System.out.printf("%s: %.2f kr%n", olga.getName(), total);
    }
}