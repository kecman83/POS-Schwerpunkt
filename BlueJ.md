# Mikrokontroling

## BlueJ Kapitel 8 - Klassenentwurf

- Wichtige Punkte am Anfang
  - Zunächst sollte man sich den vorhandenen Code anschauen vor wir mit Refactoring anfangen.
  - Dabei ist es wichtig, auf Code-Duplikate und Abhängigkeiten zwischen Klassen zu achten.
  - Nach Änderungen im Code sollte man immer testen, ob das Programm überall noch richtig funktioniert.
- **_Code Duplizierung_**
  - Duplikate im Code sollten unbedingt vermieden werden.
  - Vorteile: - Der Code bleibt übersichtlich und leichter lesbar. - Änderungen müssen nur an einer Stelle durchgeführt werden.
    Beispiel (schlechter Ansatz – duplizierter Code):

```java
public class Spiel
{
    ...
    private void wechsleRaum(Befehl befehl)
    {
        ...
        System.out.println("Sie sind " + aktuellerRaum.gibBeschreibung());
        System.out.print("Ausg?nge: ");
        if(aktuellerRaum.nordausgang != null) {
            System.out.print("north ");
        }
        if(aktuellerRaum.ostausgang != null) {
            System.out.print("east ");
        }
        if(aktuellerRaum.suedausgang != null) {
            System.out.print("south ");
        }
        if(aktuellerRaum.westausgang != null) {
            System.out.print("west ");
        }
        System.out.println();
    }
    ...
}
```

Dieser Code taucht in mehreren Methoden auf und sollte daher ausgelagert werden.

Verbesserung (eigene Methode erstellen):

```java
public class Spiel
{
    ...
    private void raumInfoAusgeben()
    {
        System.out.println("Sie sind " + aktuellerRaum.gibBeschreibung());
        System.out.print("Ausg?nge: ");
        if(aktuellerRaum.nordausgang != null) {
            System.out.print("north ");
        }
        if(aktuellerRaum.ostausgang != null) {
            System.out.print("east ");
        }
        if(aktuellerRaum.suedausgang != null) {
            System.out.print("south ");
        }
        if(aktuellerRaum.westausgang != null) {
            System.out.print("west ");
        }
        System.out.println();
    }
    private void willkommenstextAusgeben()
    {
        ...
        raumInfoAusgeben();
    }
    ...
}

```

Durch die Methode raumInfoAusgeben() wird der Code nur einmal definiert und mehrfach wiederverwendet.

- **_Koppelung_**

  - Klassen sollten möglichst lose gekoppelt sein, damit Änderungen in einer Klasse nicht sofort viele andere Klassen betreffen.
  - Ziel: Hohe Kohäsion (jede Klasse hat eine klare Aufgabe) und geringe Kopplung.

    Problem (öffentliche Datenfelder):

```java
public class Raum
{
    public String beschreibung;
    public Raum nordausgang;
    public Raum suedausgang;
    public Raum ostausgang;
    public Raum westausgang;
}
```

Jeder kann direkt auf die Attribute zugreifen → schlechte Kapselung.
Beispiel im Spiel:

```java
public class Spiel
{
    private void wechsleRaum(Befehl befehl)
    {
        ...
        Raum naechsterRaum = null;
        if(richtung.equals("north")) {
            naechsterRaum = aktuellerRaum.nordausgang;
        }
        ...
    }
}
```

Verbesserung: Datenfelder private machen + Zugriffsmethode erstellen

```java
public class Raum
{
    private String beschreibung;
    private Raum nordausgang;
    ...
    public Raum gibAusgang(String richtung){
        if(richtung.equals("nord")){
            return nordausgang;
        }
        ....
    }
}
```

Anpassung im Spiel:

```java
public class Spiel
{
    private void wechsleRaum(Befehl befehl)
    {
        ...
        Raum naechsterRaum = null;
        if(richtung.equals("north")) {
            naechsterRaum = aktuellerRaum.gibAusgang("nord");
        }
        ...
    }
}
```

Noch mehr Flexibilität: HashMap für Ausgänge
Statt für jede Richtung ein eigenes Attribut zu haben, kann man eine HashMap nutzen:

```java
public class Raum
{
    private String beschreibung;
    private HashMap<String, Raum> ausgaenge;
    ...
    public void setzeAusgang(String richtung, Raum nachbar){
        ausgaenge.put(richtung,nachbar);
    }
}
```

Schlechter Code (statisch mit 4 Richtungen):

```java
public class Spiel
{
    private void raeumeAnlegen()
    {
        ...
        labor = new Raum("in einem Rechnerraum");
        ...
        labor.setzeAusgaenge(draussen, buero, null, null);
    }
}
```

Verbesserung (HashMap mit beliebigen Richtungen):

```java
public class Spiel
{
    private void raeumeAnlegen()
    {
        ...
        labor = new Raum("in einem Rechnerraum");
        ...
        labor.setzeAusgaenge("nord",bar);
    }
}
```

Dadurch kann man beliebig viele Richtungen hinzufügen, nicht nur nord/ost/süd/west.

**_Kohäsion_**

> Grundgedanken von Kohäsion ist
> Eine Programmeinheit sollte immer nur für genau eine Aufgabe zuständig sein.

- Wenn wir über die **_Kohäsion von Methoden_** sprechen, dann wollen wir dass eine Methode nur für genau eine wohldefinierte Aufgabe zuständig ist.

```java
public void spielen()
{
    System.out.println();
    System.out.println("Wikommen zu Zuul!");
    ...
    System.out.println();
}
private void wilkommensTextAusgeben()
{
    System.out.println();
    System.out.println("Wikommen zu Zuul!");
    ...
}
```

So ist besser

```java
public void spielen()
{
    wilkommensTextAusgeben();
    ...
}
private void wilkommensTextAusgeben()
{
    System.out.println();
    System.out.println("Wikommen zu Zuul!");
}
```

- Regel für die **_Kohäsion von Klassen_** besagt, Eine Klasse mit hoher Kohäsion repräsentiert genau eine wohldefinierte Einheit.
  in Zuul spiel wenn wir in einigen räumen Gegestand zuviegen wollen nicht gute idee ist in Klasse Raum ad data feld Gegestand zu zufügen

```java
public class Raum
{
    private String gegstandBeschreibung;
    private double gegestandGewicht;
    ...
}
```

Bessere lösung ist neue Klasse Gegestand

```java
public class Gegestand
{
    private String beschreibung;
    private double gewicht;
    ...
}
```

**_Refactoring_**

> Refactoring wird die Aktivität genannt, bei der bestehende Klassen und Methoden restrukturiert werden, um sie geänderten Umständen und Anforderungen anzupassen.
> Es bedeutet wir sollen bei Spiel erweitern überlegen ob wir gewisene data felder aus eine in andere klasse zu verschieben und so Koppelung loser zu machen und Kohäsion zu stärken.

## BlueJ Kapitel 15 - Entwurf von Anwendungen

Der objektorientierte Entwurfsprozess gliedert sich in mehrere Phasen. Ziel ist es, von einer Beschreibung der Anforderungen zu einem funktionierenden Programm zu gelangen.

- Anforderungsanalyse
  - Man beschreibt das Systemverhalten aus Sicht des Benutzers.
  - Ziel: Verständnis was das System tun soll.
- Objektanalyse
  - Aus den Anwendungsfällen werden relevante Objekte und Klassen identifiziert.
  - Man sucht **SUBSTANTIVE** -> mögliche **KLASSEN** und **VERBEN** -> mögliche **METHODEN**.
    Beispiel aus dem Kinobuchungssystem:
    | Substantiv | Mögliche Klasse |
    | ----------- | --------------- |
    | Film | `Film` |
    | Vorstellung | `Vorstellung` |
    | Sitz | `Sitz` |
    | Kunde | `Kunde` |
    | Buchung | `Buchung` |
    | Kinosaal | `Kinosaal` |
- Grobentwurf mit hilfe von CRC karten

  - Man legt fest, wie das System aufgebaut ist:
    - Welche Klassen gibt es?
    - Wie kommunizieren sie miteinander?
    - Welche Daten werden gespeichert?
      - **_Class_** -- Name der Klasse, **_„hat ein“_**
      - **_Responsibilities_** -- Aufgaben / Verantwortlichkeiten (Was tut die Klasse), **_„besteht aus“_**
      - **_Collaborators_** -- Mit welchen anderen Klassen arbeitet sie zusammen,**_„ist ein“_**

- Feinentwurf
  - Detaillierte Festlegung der Methoden und Datenattribute.
  - Entwurf von neuen klassen wens notwendig, Schnittstellen und ggf. GUI-Interaktionen.
- Klassen aus dem Entwurf werden in Code umgesetzt.

**_Vergleich von Vorgehensmodellen_**

| Modell                                    | Beschreibung                                                                                                          | Vorteile                                                                         | Nachteile                                                |
| ----------------------------------------- | --------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------- | -------------------------------------------------------- |
| **Wasserfallmodell**                      | Lineares Modell mit klar getrennten Phasen (Analyse → Entwurf → Implementierung → Test → Wartung).                    | - Klare Struktur<br>- Einfach zu planen                                          | - Änderungen schwer einbaubar<br>- Späte Fehlererkennung |
| **Iteratives Modell / Agile Entwicklung** | Entwicklung erfolgt in Zyklen („Iterationen“) – Analyse, Entwurf, Implementierung und Test wiederholen sich mehrfach. | - Frühzeitige Rückmeldung<br>- Flexible Anpassung<br>- Besseres Risikomanagement | - Mehr Koordination nötig<br>- Schwerer zu planen        |
| **Prototyping-Modell**                    | Frühe, einfache Versionen werden erstellt, um Anforderungen zu testen.                                                | - Frühes Feedback<br>- Geringes Fehlerrisiko                                     | - Kann zu „Wegwerfcode“ führen                           |

**_Überblick über Entwurfsmuster_**
Entwurfsmuster sind bewährte Lösungen für wiederkehrende Probleme im Softwareentwurf.
| Muster | Zweck | Beispiel im Kinoprojekt |
| ------------------------------- | ------------------------------------------------------------ | ----------------------------------------------------------------------------- |
| **Singleton** | Stellt sicher, dass es nur _eine Instanz_ einer Klasse gibt. | `Buchungsmanager` oder `Kinoprogramm` (zentraler Zugriffspunkt) |
| **Fabrikmethode (Factory)** | Erzeugt Objekte, ohne die genaue Klasse anzugeben. | `TicketFactory` erstellt passende Ticketarten (z. B. Standard, 3D, Kinder) |
| **Beobachter (Observer)** | Mehrere Objekte reagieren auf Änderungen eines anderen. | `Anzeige` aktualisiert sich, wenn `Vorstellung` oder `Sitzplan` geändert wird |
| **Strategie (Strategy)** | Austauschbare Algorithmen zur Laufzeit. | Unterschiedliche Preisberechnungen (Rabatte, VIP, Studenten) |
| **MVC (Model-View-Controller)** | Trennung von Daten, Anzeige und Steuerung. | `Model`: Buchungslogik<br>`View`: GUI<br>`Controller`: Benutzerinteraktion |

## SOLID und sonstige designprinzipien

**_SOLID_**
SOLID ist ein Akronym und steht für:

> **S** – Single Responsibility Principle
> **O** – Open/Closed Principle
> **L** – Liskov Substitution Principle
> **I** – Interface Segregation Principle
> **D** – Dependency Inversion Principle

Alle zusammen sorgen für:

- Hohe Kohäsion
- Geringe Kopplung
- Einfach erweiterbaren Code

_S_
Jede Klasse soll nur eine Aufgabe haben. Wenn wir merken, dass eine Klasse viele verschiedene Dinge tut bitte aufteilen! Mehr als eine Verantwortung für eine Klasse führt zu mehreren Bereichen, in denen zukünftige Änderungen notwendig werden können.
Schlechtes Beispiel:

```java
public class Buchung {
    public void speichereBuchung() { ... }
    public void druckeTicket() { ... }
    public void sendeBestaetigung() { ... }
}
```

Besser:

```java
public class Buchung { ... }
public class TicketDrucker { ... }
public class MailService { ... }

```

_O_
Neue Funktionalität hinzufügen können, ohne bestehenden Code zu ändern. Das erreichen wir mit Vererbung oder Interfaces. dass Software-Einheiten Erweiterungen möglich machen sollen, aber ohne dabei ihr Verhalten zu ändern. Überschriebene Methoden verändern auch nicht das Verhalten der Basisklasse, sondern nur das der abgeleiteten Klasse

Schlechtes Beispiel:

```java
public class RabattRechner {
    public double berechne(double betrag, String typ) {
        if (typ.equals("STUDENT")) return betrag * 0.8;
        if (typ.equals("VIP")) return betrag * 0.7;
        return betrag;
    }
}
```

Besser:

```java
interface RabattStrategie {
    double berechne(double betrag);
}

class StudentenRabatt implements RabattStrategie {
    public double berechne(double betrag) { return betrag * 0.8; }
}

class VIPRabatt implements RabattStrategie {
    public double berechne(double betrag) { return betrag * 0.7; }
}
```

_L_
oder Ersetzbarkeitsprinzip fordert dass eine Instanz sich so verhalten muss, eine Unterklasse darf die Funktionalität der Oberklasse nicht verletzen oder verändern.

Schlechter Beispiel:

```java
class Vogel {
    void fliegen() { ... }
}
class Pinguin extends Vogel {
    void fliegen() { throw new UnsupportedOperationException(); }
}
```

Code, der einen „Vogel“ erwartet, könnte abstürzen.

Besser:
Nur flugfähige Vögel implementieren „fliegen()“.

```java
class Vogel { ... }
class FlugfaehigerVogel extends Vogel {
    void fliegen() { ... }
}
class Pinguin extends Vogel { ... }

```

_I_
Klassen sollen nicht gezwungen sein, Methoden zu implementieren, die sie nicht brauchen. Es dient dazu, zu große Interfaces aufzuteilen. Die Aufteilung soll gemäß den Anforderungen passen. Ist es möglich eine Software derart in Klassen aufzuteilen, dass zukünftige fachliche oder technische Anforderungen an die Software nur geringe Änderungen an der Software selbst benötigen.

Schlechtes Beispiel:

```java
interface Drucker {
    void drucken();
    void scannen();
    void faxen();
}

class EinfacheDrucker implements Drucker {
    public void drucken() { ... }
    public void scannen() { throw new UnsupportedOperationException(); }
    public void faxen() { throw new UnsupportedOperationException(); }
}

```

Besser:

```java
interface Drucker { void drucken(); }
interface Scanner { void scannen(); }
interface Fax { void faxen(); }

class EinfacheDrucker implements Drucker { ... }
class Multifunktionsgeraet implements Drucker, Scanner, Fax { ... }

```

_D_
Hohe Module sollen sich nicht direkt auf konkrete Implementierungen stützen, sondern auf Interfaces oder Abstraktionen. Reduktion der Kopplung von Modulen dass Abhängigkeiten immer von konkreteren Modulen niedriger Ebenen zu abstrakten Modulen höherer Ebenen gerichtet sein sollten.

**_DRY_**

> _DRY_ = Don’t Repeat Yourself
> „Wiederhole dich nicht.“
> _Ziel_: Jede Information oder Logik sollte nur einmal im Code existieren.

Anti Beispiel:

```java
public void zeigeRaumInfo() {
    System.out.println("Sie sind " + aktuellerRaum.gibBeschreibung());
    System.out.print("Ausgänge: ");
    if (aktuellerRaum.nordausgang != null) System.out.print("north ");
    ...
}

public void willkommenstext() {
    ...
    System.out.println("Sie sind " + aktuellerRaum.gibBeschreibung());
    System.out.print("Ausgänge: ");
    if (aktuellerRaum.nordausgang != null) System.out.print("north ");
    ...
}
```

Beispiel mit DRY Prinzipien:

```java
private void raumInfoAusgeben() {
    System.out.println("Sie sind " + aktuellerRaum.gibBeschreibung());
    System.out.print("Ausgänge: ");
    if (aktuellerRaum.nordausgang != null) System.out.print("north ");
    ...
}

public void zeigeRaumInfo() {
    raumInfoAusgeben();
}

public void willkommenstext() {
    ...
    raumInfoAusgeben();
}
```

- Warum ist DRY so wichtig?
  - Weniger Fehlerquellen, Änderungen müssen nur an einer Stelle vorgenommen werden.
  - Besser wartbar, Der Code bleibt übersichtlich und konsistent.
  - Bessere Verständlichkeit, Logik ist klarer.
- Arten von Duplizierung, die DRY vermeiden will
  - Code-Duplizierung: Mehrfach kopierter Code, Extrahiere Methode oder Klasse
  - Daten-Duplizierung: Gleiche Daten in mehreren Klassen, Gemeinsame Datenquelle oder Modell
- Best Practices zur Umsetzung von DRY:
  - Refaktoriere wiederholten Code in eigene Methoden, wie raumInfoAusgeben().
  - Vererbung oder Interfaces nutzen
  - Design Patterns anwenden, wie Factory, Singleton, Strategy etc. helfen, redundante Code-Strukturen zu vermeiden
  - Früh testen und iterativ verbessern.

**_KISS_**

> KISS steht für Keep It Simple, Stupid
> oder höflicher: Keep It Short and Simple.
> Das bedeutet: Mach es so einfach wie möglich – aber nicht einfacher.

- Idee
  - einfach, klar und verständlich aufgebaut sein,
  - nicht unnötig komplex,
  - und leicht zu warten und zu erweitern sein.

Kompliziert

```java
if(befehl.equals("go") && raum.equals("north") && hatSchluessel == true && !monsterIstDa) {
}
```

Einfach

```java
if (befehl.equals("go")) {
    wechsleRaum(richtung);
}
```

- Best Practices für KISS

  - Kleine, überschaubare Methoden, Eine Methode = eine Aufgabe
  - Klassen nach Verantwortlichkeiten trennen, Eine Klasse = ein Zweck
  - Einfache Datentypen & Strukturen
  - chrittweise entwickeln

KISS-Motto für Entwickler:

> _"Simplicity is the ultimate sophistication."_ – **_Leonardo da Vinci_**

oder

> _"Wenn du deinen eigenen Code in zwei Wochen nicht mehr verstehst – ist er nicht KISS."_

**_YAGNI_**

> YAGNI bedeutet:
> **_„You Aren’t Gonna Need It“_** > **„Du wirst es nicht brauchen.“**
> „Implementiere nur das, was jetzt benötigt wird – nicht das, was vielleicht irgendwann gebraucht werden könnte.“

- Ziel von YAGNI

  - Fokus auf aktuelle Anforderungen
  - Vermeidung unnötiger Komplexität
  - Schnellere, einfachere Implementierung
  - Bessere Lesbarkeit & Wartbarkeit

- Verstöße gegen YAGNI
  - Unnötige Komplexität
  - Over-Engineering
  - Unnötige Abstraktion

Schlechter beispiel

```java
public class Buchung {
    private String sitzplatz;
    private String bezahlMethode; // aktuell immer "bar"
    private String rabattCode; // (noch nicht verwendet)
    private boolean istOnline; // (vielleicht später)
    ...
}
```

- YAGNI richtig umsetzen:
  - Entwickle nur, was aktuell im Use Case gefordert ist.
  - Wenn sich Anforderungen ändern, dann erweitern.

richtig umgesetzter YAGNI

```java
public class Buchung {
    private Sitz sitz;
    private Vorstellung vorstellung;
    private double preis;
}
```

Prinzipien vergleich:

| Prinzip   | Bedeutung                | Fokus                               |
| --------- | ------------------------ | ----------------------------------- |
| **KISS**  | Keep It Simple, Stupid   | Einfachheit und Verständlichkeit    |
| **DRY**   | Don’t Repeat Yourself    | Keine Code-Redundanz                |
| **YAGNI** | You Aren’t Gonna Need It | Kein unnötiger Funktionsumfang      |
| **SOLID** | 5 OO-Design-Prinzipien   | Strukturierte, flexible Architektur |

![alt text](image.png)

## ENTWURFSMUSTER

Muster sind ein Werkzeugkasten mit Lösungen für häufige Probleme im Softwaredesign. Sie definieren eine gemeinsame Sprache, die Ihrem Team hilft, effizienter zu kommunizieren.
Designmuster unterscheiden sich in ihrer Komplexität, ihrem Detaillierungsgrad und ihrem Anwendungsbereich. Darüber hinaus können sie nach ihrer Absicht kategorisiert und in drei Gruppen unterteilt werden:

Verschiedene Typen von Entwurfsmustern:

    - Erzeugungsmuster (Creational Patterns)
    - Strukturmuster (Structural Patterns)
    - Verhaltensmuster (Behavioral Patterns)

_Erzeugungsmuster_

Stellen Sie Mechanismen zur Objekterstellung bereit, die die Flexibilität und Wiederverwendung vorhandenen Codes erhöhen. Implementierung gemäß der Regel: **_„Programmiere auf die Schnittstelle, nicht auf die Implementierung!“_**

    - Builder
    - ...

**Builder**

Das Builder-Pattern wird genutzt, wenn die Konstruktion eines komplexen Objektes von seiner Repräsentation getrennt werden soll. Das Objekt (**Salat**) konstruiert sich also nicht selber, sondern wird von einem anderen Objekt (**SalatMachen**) erbaut.

```java
public class Main {
    public static void main(String[] args) {
        //neuen objekt erstellen von typ CesarSalat
        SalatMachen hersteller = new CesarSalat();
        //neuen objekt erstellen von typ SalatHersteller und als parametar CesarSalat eingeben
        SalatHersteller macher = new SalatHersteller(hersteller);
        //aufruf von Objekt klasse
        macher.salatRichten();
        //Objekt wert zuweisen über methode
        Salat salat = macher.getSalat();
        System.out.println(salat);
    }
}

public class Salat {
    //daten felder
    private String gemuese;
    private String dressing;
    private String gewuerze;
    private String beilage;

    //set methoden für data felder
    public void setGemuese(String gemuese) {
        this.gemuese = gemuese;
    }
    public void setDressing(String dressing){
        this.dressing = dressing;
    }
    public void setGewuerze(String gewuerze){
        this.gewuerze = gewuerze;
    }
    public void setBeilage(String beilage){
        this.beilage = beilage;
    }
    //ausgabe als String überschreiben
    @Override
    public String toString() {
        return "Salat: salat ist: " + this.gemuese + ", dressing ist: "+ this.dressing + ", gewuerze sind: "+this.gewuerze + " und beilage sind: "+this.beilage;
    }
}
//interface erstellen mit methoden die wir brauchen um Objecte zu BUILDEN
public interface SalatMachen {
    void gemueseSchneiden();
    void dressingVorbereiten();
    void gewuerzeRichten();
    void beilageReintun();
    //gib ergebni wieder zurick
    Salat getSalat();
}
//Spetziefisches object implements interface
public class CesarSalat implements SalatMachen{
    private Salat salat;
    //Konstruktor um neue Objekt zu erzeugen
    public CesarSalat(){
        this.salat = new Salat();
    }
    public void gemueseSchneiden(){
        salat.setGemuese("Gruene Salat");
    }
    public void dressingVorbereiten(){
        salat.setDressing("Cieser dressing");
    }
    public void gewuerzeRichten(){
        salat.setGewuerze("Saltz und Pepper");
    }
    public void beilageReintun(){
        salat.setBeilage("Hendel");
    }
    public Salat getSalat(){
        return this.salat;
    }
}
//Objekt gib komand für herstellen
public class SalatHersteller {
    private SalatMachen hersteller;

    public SalatHersteller(SalatMachen hersteller) {
        this.hersteller = hersteller;
    }
    //ruft intarace methoden
    public void salatRichten() {
        hersteller.gemueseSchneiden();
        hersteller.dressingVorbereiten();
        hersteller.gewuerzeRichten();
        hersteller.beilageReintun();
    }
    public Salat getSalat() {
        return hersteller.getSalat();
    }
}

```

![alt text](POS-Schwerpunkt.png)
_Strukturmuster_

Erleichtern den Entwurf von Software durch vorgefertigte Schablonen für Beziehungen zwischen Klassen. Erklären Sie, wie Sie Objekte und Klassen zu größeren Strukturen zusammenfügen und dabei die Flexibilität und Effizienz dieser Strukturen wahren.

    - Dekodierer
    - Adapter
    - ...

**Dekodierer**

Das Muster ist eine flexible Alternative zur Unterklassenbildung, um eine Klasse um zusätzliche Funktionalitäten zu erweitern.

```java
public class Main {
    public static void main(String[] args) {
        //Dekoratoer erzugt neuen Objekt der supdekorator ist und hat als parameter normale message
        AlarmDekorator a = new AmokAlarmDekorator(new StandardAlarm("Amokkkkkkk"));
        System.out.println(a.alarm());
    }
}

public interface Alarm {
    //Notifier
    String alarm();
}

public class StandardAlarm implements Alarm{

    private String alarmName;

    /**
     * Konstructor
     * @param alarmName
     */
    public StandardAlarm(String alarmName){
        this.alarmName = alarmName;
    }
    /**
     *
     * @return diese nachricht zurück
     */
    @Override
    public String alarm() {
        return this.alarmName;
    }
}
public class AlarmDekorator implements Alarm{
    /**
     * Data feld von Object Alarm als wraper
     */
    private Alarm wrappee;
    public AlarmDekorator(Alarm wrappee){
        this.wrappee = wrappee;
    }

    /**
     *
     * @return wrappee alarm
     */
    @Override
    public String alarm() {
        return wrappee.alarm();
    }
}

public class AmokAlarmDekorator extends AlarmDekorator{

    public AmokAlarmDekorator(Alarm wrappee){
        super(wrappee);
    }

    /**
     *
     * @return wraper alarm
     */

    @Override
    public String alarm() {
        return "AMOK ALARM: " + super.alarm().toUpperCase() + " 🚨";
    }
}

public class FuererAlarmDekorator extends AlarmDekorator{

    public FuererAlarmDekorator(Alarm wrappee){
        super(wrappee);
    }

    @Override
    public String alarm() {
        return " FUERER ALARM "+super.alarm();
    }
}


```

![alt text](Dekorator.png)

**Adapter**

_Verhaltensmuster_

Modellieren komplexes Verhalten der Software. Kümmern sich um eine effektive Kommunikation, erhöhen damit die Flexibilität und die Zuweisung von Verantwortlichkeiten zwischen Objekten.

    - Chain of Responsibility
    - TemplateMethod
    - Observer
    - ...

**Chain of Responsibility**

**TemplateMethod**

**Observer**
