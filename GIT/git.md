# Alles rund um GIT

**_Git Command Cheat-Sheet _**

**Basic Setup**

```cmd
git init
```

- Create a new local Git repository in the current folder.

```cmd
git clone <url>
```

- Copy (clone) an existing remote repository to your machine.

**Checking Repository State**

```cmd
git status
```

- Show the status of changed, staged, and untracked files.

```cmd
git diff
```

- Show unstaged changes.

```cmd
git diff --staged
```

- Show staged changes.

**Adding & Committing**

```cmd
git add <file>
```

- Stage a file.

```cmd
git add .
```

- Stage all changes.

```cmd
git commit -m "message"
```

- Commit staged changes with a message.

**Working With Remotes**

```cmd
git remote add origin <url>
```

- Connect your local repo to a remote named origin.

```cmd
git push origin <branch>
```

- Push commits to the remote repository.

```cmd
git pull
```

- Fetch and merge changes from the remote.

**Branches**

```cmd
git branch
```

- List branches.

```cmd
git branch <name>
```

- Create a branch.

```cmd
git checkout <branch>
```

- Switch to a branch.

```cmd
git checkout -b <name>
```

- Create and switch.

```cmd
git merge <branch>
```

- Merge <branch> into the current branch.

```cmd
git rebase <branch>
```

- Replay your commits on top of <branch> (cleaner history).

#### **cheat sheet 2**

- download a repository on GitHub to our machine
- Replace `owner/repo` with the owner and name of the repository to clone

```cmd
git clone https://github.com/owner/repo.git
```

- change into the `repo` directory

```cmd
cd repo
```

- create a new branch to store any new changes

```cmd
git branch my-branch
```

- switch to that branch (line of development)

```cmd
git checkout my-branch
```

- make changes, for example, edit `file1.md` and `file2.md` using the text editor
- stage the changed files

```cmd
git add file1.md file2.md
```

- take a snapshot of the staging area (anything that's been added)

```cmd
git commit -m "my snapshot"
```

- push changes to github

```cmd
git push --set-upstream origin my-branch
```

---

- create a new directory, and initialize it with git-specific functions

```cmd
git init my-repo
```

- change into the `my-repo` directory

```cmd
cd my-repo
```

- create the first file in the project

```cmd
touch README.md
```

- git isn't aware of the file, stage it

```cmd
git add README.md
```

- take a snapshot of the staging area

```cmd
git commit -m "add README to initial commit"
```

- provide the path for the repository you created on github

```cmd
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPOSITORY-NAME.git
```

- push changes to github

```cmd
git push --set-upstream origin main
```

---

- change into the `repo` directory

```cmd
cd repo
```

- update all remote tracking branches, and the currently checked out branch

```cmd
git pull
```

- change into the existing branch called `feature-a`

```cmd
git checkout feature-a
```

- make changes, for example, edit `file1.md` using the text editor

- stage the changed file

```cmd
git add file1.md
```

- take a snapshot of the staging area

```cmd
git commit -m "edit file1"
```

- push changes to github

```cmd
git push
```

## Commit

- Ein Commit in einem Git-Repository speichert eine Abbildung aller Dateien in deinem Projektverzeichnis. Es ist wie ein riesiges Kopieren und Einfügen, nur besser.

```cmd
git commit -m "Nachricht"
```

![alt text](image-3.png)
![alt text](image-5.png)
![alt text](image-4.png)

## Branch

- Neue Branch erstellen

```cmd
git branch nameVonBranch
```

oder

```cmd
git checkout -b nameVonBranch
```

- Hier wird Branch erstellt und gleich ausgewehlt.

## Branch aussuchen

- Branch aussuchen

```cmd
git checkout nameVonBranch
```

- dann commit...

![alt text](image-7.png)
![alt text](image-8.png)
![alt text](image-6.png)

## Merge

- Wichtig ist in branch zu seit in welche wir MERGEN

```cmd
git merge nameVonBranchDieWirMergen
```

![alt text](image-10.png)
![alt text](image-11.png)
![alt text](image-9.png)

## Rebase

- Der zweite Weg um Inhalte aus verschiedenen Branches zu kombinieren. Rebasen nimmt im Prinzip eine Menge von Commits, "kopiert" sie und packt sie auf etwas anderes drauf.

```cmd
git rebase nameVonBransch
```

![alt text](image-2.png)
![alt text](image-1.png)
![alt text](image.png)

## Head

![](image-13.png)
![alt text](image-14.png)
![alt text](image-12.png)

### ^

![alt text](image-16.png)
![alt text](image-17.png)
![alt text](image-15.png)

### ~ und -

![alt text](image-19.png)
![alt text](image-20.png)
![alt text](image-18.png)

## Reset and Revert

- nimmt Änderungen zurück, indem es eine Branch-Referenz auf einen anderen Commit setzt.

Obwohl git reset super im lokalen Kontext funktioniert, ist der Ansatz vom "Umschreiben" der Commit-Geschichte nicht geeignet für Branches, die auf einem Server liegen und auch von anderen benutzt werden.

![alt text](image-22.png)
![alt text](image-23.png)
![alt text](image-21.png)

## Cherry pick

![alt text](image-26.png)
![alt text](image-27.png)
![alt text](image-25.png)

## Rebase mit -i

![alt text](image-29.png)
![alt text](image-30.png)
![alt text](image-28.png)

# Remote

## SSH KEY herstellen

```cmd
ssh-keygen -t rsa -b 4096 -C "kecman83@live.com"
```

![](ssh.png)

## Clone ordner auf locale Comüp

```cmd
git clone git@gitlab.com:ivan2205137/ivan.git
```

![alt text](image-32.png)
![alt text](image-33.png)
![alt text](image-31.png)

![alt text](image-35.png)
![alt text](image-36.png)
![alt text](image-34.png)

## Fetch

- In Git mit entfernten Repositorys zu arbeiten lässt sich wirklich auf das Hin- und Zurückübertragen von Daten reduzieren. Solange wir Commits hin und her schicken können, können wir jede Art Update teilen, das von Git getrackt wird (und somit Arbeit, neue Dateien, neue Ideen, Liebesbriefe etc. teilen).

![alt text](image-38.png)
![alt text](image-39.png)
![alt text](image-37.png)

## Pull

![alt text](image-41.png)
![alt text](image-42.png)
![alt text](image-40.png)

![alt text](image-44.png)
![alt text](image-45.png)
![alt text](image-43.png)

## Push

![alt text](image-47.png)
![alt text](image-46.png)

![alt text](image-49.png)
![alt text](image-50.png)
![alt text](image-48.png)

# Yes i Made it

![alt text](image-24.png)

# Aufgabe 3

![alt text](image-51.png)

**_GitHub Flow_**
![alt text](image-53.png)

- Ein einfacher, moderner Workflow, der für kontinuierliche Auslieferung (Continuous Deployment) optimiert ist.
  • Es gibt nur eine langlebige Hauptbranch: main
  • Jede Änderung geschieht in einem kurzlebigen Feature-Branch
  • Änderungen werden per Pull Request wieder in main gemerged
  • Deployment erfolgt meist direkt nach dem Merge

-- Pro
• Sehr einfach und leicht zu verstehen
• Ideal für häufige Deployments
• Weniger Merge-Konflikte durch kleine Änderungen
• Gut für kleine und schnelle Teams

-- Kontra
• Keine klare Struktur für komplexe Release-Zyklen
• Aufwändig, wenn mehrere Versionen parallel unterstützt werden müssen
• Nicht optimal für Apps, die nur selten releast werden

**_Git Flow_**
![alt text](image-54.png)

- Ein klassischer, sehr strukturierter Workflow, besonders geeignet für Software mit klaren Versionen und Releases.

- Es gibt mehrere langlebige Branches:
  • main (Produktiv-Code)
  • develop (aktueller Entwicklungsstand)
- Zusätzliche Branches:
  • feature
  • release
  • hotfix
- Ablauf
  • Features werden aus `develop` erstellt → feature
  • Release-Vorbereitung über release
  • Notfall-Fixes über `hotfix` direkt von `main`

-- Pro
• Sehr klare Struktur für Versionierung
• Gut für große Teams und Produkte mit festen Release-Zyklen
• Saubere Trennung von Entwicklung, Test und Produktion
• Ideal für Software, die ausgeliefert/installiert wird (z. B. Desktop-Apps, Embedded)

-- Kontra
• Viel Overhead, viele Branches
• Kann Entwicklung verlangsamen
• Für Continuous Delivery eher ungeeignet
• Konflikte zwischen main und develop sind häufig

**_ONE FLOW_**
![alt text](image-55.png)

- Ein moderner und schlanker Workflow – entwickelt als Antwort auf die Komplexität von Git Flow.

  • Keine develop-Branch
  • Nur eine Hauptbranch (main) und Feature-Branches
  • Release-Branches werden nur dann angelegt, wenn wirklich nötig
  • Fokus auf einfachen und linearen Verlauf

-- Pro
• Weniger komplex als Git Flow
• Weniger Merge-Konflikte
• Klare, lineare Git-Historie
• Geeignet für Teams, die etwas Struktur wollen, aber Git Flow zu schwer finden

-- Kontra
• Nicht so minimal wie GitHub Flow
• Weniger Struktur als Git Flow für große Release-Prozesse
• Erfordert Disziplin beim Mergen, damit die Historie sauber bleibt

## Aufgabe 4

- Clone Repository

![](git1.png)

- Merge Ivan Branch ins Main ohne Conflict

![](merge1.png)

- Merge Benedikt Branch ins Main mit Conflict

![](conGitBasc.png)
![](confflict.png)
![](conflict2.png)
![](connflict3.png)

- Merge Confilcte gelöst

![](conflic_solwed.png)

- Repository Graf

![](repositorygraf.png)
![](grafVScode.png)
