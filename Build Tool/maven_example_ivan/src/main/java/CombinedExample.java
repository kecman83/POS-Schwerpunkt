import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.base.Preconditions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;

public class CombinedExample {
    private static final Log log = LogFactory.getLog(CombinedExample.class);

    public static void main(String[] args) {
        // --- Teil 1: Guava Multimap ---
        System.out.println("=== Guava Multimap Beispiel ===");
        Multimap<String, String> studenten = ArrayListMultimap.create();
        studenten.put("Mathe", "Alice");
        studenten.put("Mathe", "Bob");
        studenten.put("Informatik", "Charlie");
        System.out.println(studenten);
        // Ausgabe: {Mathe=[Alice, Bob], Informatik=[Charlie]}

        // --- Teil 2: Apache Logging ---
        System.out.println("\n=== Apache Logging Beispiel ===");
        log.info("Das ist eine INFO-Nachricht");
        log.warn("Das ist eine WARNUNG");
        log.error("Das ist eine FEHLERMELDUNG");
        try {
            int x = 10 / 0;
        } catch (Exception e) {
            log.fatal("Ein fataler Fehler ist aufgetreten!", e);
        }

        // --- Teil 3: Apache Commons Text Escaping ---
        System.out.println("\n=== Apache Commons Text Beispiel ===");
        String input = "<h1>Hello</h1>";
        String escaped = StringEscapeUtils.escapeHtml4(input);
        System.out.println("Escaped: " + escaped);
        String unescaped = StringEscapeUtils.unescapeHtml4(escaped);
        System.out.println("Unescaped: " + unescaped);

        // --- Teil 4: Guava Preconditions ---
        System.out.println("\n=== Guava Preconditions Beispiel ===");
        int alter = 17;
        try {
            Preconditions.checkArgument(alter >= 18, "Muss mindestens 18 sein!");
            System.out.println("Willkommen!");
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}
