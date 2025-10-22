## Marven in 5 minutes

Vor Marven installation habe ich erst Scoop instalier

```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression
```

dann ist Marven dran

```cmd
scoop install main/maven
```

![install](maven_install.png)

version zeigen lassen.

```powershell
mvn --version
```

![version](maven_version.png)

    ***Creating Project***

![folder](folder.png)

```cmd
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.5 -DinteractiveMode=false
```

```powershell
mvn archetype:generate
```

- Startet das Maven-Plugin Archetype, das ein neues Projekt aus einer Vorlage erzeugt.

```powershell
-DgroupId=com.mycompany.app
```

- Legt den Group-Identifier fest.
  - Das ist wie ein eindeutiger Paketname (meist in umgekehrter Domainform).
  - Wird z. B. als package com.mycompany.app; im Quellcode verwendet.

```powershell
-DartifactId=my-app
```

- Wählt die Vorlage, die verwendet werden soll.
  - maven-archetype-quickstart ist eine einfache Vorlage für ein Standard-Java-Projekt mit:
    - src/main/java und src/test/java Verzeichnissen
    - einer Beispiel-Java-Klasse
    - einer Beispiel-JUnit-Testklasse

```powershell
-DarchetypeVersion=1.5
```

- Gibt die Version der Vorlage an (hier Version 1.5 des maven-archetype-quickstart).

```powershell
-DinteractiveMode=false
```

- Sorgt dafür, dass Maven keine Fragen mehr stellt (z. B. nach Projektname, Package, Version).
  Alles wird automatisch mit den angegebenen Parametern erstellt.

![build](build_gemacht.png)

dann ins folder my-app wechseln und mit

```powershell
tree
```

![tree](tree.png)

    *** Build the Project ***

```powershell
mvn package
```

- Anders als der erste ausgeführte Befehl (archetype:generate) besteht der zweite lediglich aus einem einzigen Wort – Paket. Statt eines Ziels handelt es sich hier um eine Phase. Eine Phase ist ein Schritt im Build-Lebenszyklus, der eine geordnete Abfolge von Phasen darstellt. Wenn eine Phase angegeben ist, führt Maven jede Phase der Sequenz bis einschließlich der definierten Phase aus. Wenn Sie beispielsweise die Kompilierphase ausführen, werden die folgenden Phasen tatsächlich ausgeführt:

![lifeccycle](lifecycle.png)

und testen

```powershell
java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App
```

![test](test_jar.png)

### Maven Phases

Obwohl dies keine vollständige Liste ist, handelt es sich hier um die am häufigsten ausgeführten Standardphasen des Lebenszyklus.

Validieren: Überprüfen, ob das Projekt korrekt ist und alle erforderlichen Informationen verfügbar sind.
Kompilieren: Kompilieren des Quellcodes des Projekts.
Testen: Testen des kompilierten Quellcodes mit einem geeigneten Unit-Test-Framework. Für diese Tests sollte der Code nicht paketiert oder bereitgestellt werden müssen.
Paketieren: Verpacken des kompilierten Codes in ein verteilbares Format, z. B. eine JAR-Datei.
Integrationstest: Verarbeiten und Bereitstellen des Pakets in einer Umgebung, in der Integrationstests ausgeführt werden können.
Verifizieren: Überprüfen, ob das Paket gültig ist und die Qualitätskriterien erfüllt.
Installieren: Installieren des Pakets im lokalen Repository zur lokalen Verwendung als Abhängigkeit in anderen Projekten.
Bereitstellen: Wird in einer Integrations- oder Release-Umgebung ausgeführt und kopiert das fertige Paket in das Remote-Repository, um es mit anderen Entwicklern und Projekten zu teilen.

Neben der oben aufgeführten Standardliste gibt es noch zwei weitere wichtige Maven-Lebenszyklen. Diese sind:

Clean: Bereinigen von Artefakten, die durch vorherige Builds entstanden sind.
Site: Erstellen der Site-Dokumentation für dieses Projekt.

Phasen sind den zugrunde liegenden Zielen zugeordnet. Die spezifischen Ziele, die pro Phase ausgeführt werden, hängen vom Pakettyp des Projekts ab. Beispielsweise führt das Paket jar:jar aus, wenn der Projekttyp ein JAR ist, und war:war, wenn der Projekttyp – Sie haben es erraten – ein WAR ist.

Interessant ist, dass Phasen und Ziele nacheinander ausgeführt werden können.

```powershell
mvn clean dependency:copy-dependencies package
```

![](sequence.png)

### Generating the Site

```powershell
mvn site
```

In dieser Phase wird eine Site basierend auf Informationen zum POM des Projekts generiert. Sie können die generierte Dokumentation unter target/site einsehen.
