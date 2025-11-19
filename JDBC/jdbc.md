# JDBC

## AUFGABE 1: JDBC INTRO TEIL 1

- DB erstellen mit yaml

```yaml
version: "3.8"
services:
  db:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: 12345
      MYSQL_DATABASE: jdbcdemo
      MYSQL_USER: user
      MYSQL_PASSWORD: 12345
    ports:
      - "3306:3306"
    volumes:
      - db_data:/var/lib/mysql
volumes:
  db_data:
```

und dann mit CMD

```cmd
docker compose up
```

- Erstelen von Maven mysql dependency

```maven
    <dependencies>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.5.0</version>
        </dependency>
    </dependencies>
```

- MYSQL conection erstellen version 1

![mit InteliJ](mysqlCOnect.png)

- MYSQL conection erstellen version 2

![mysql workbentch](workBen.png)

- Tabele in DB erstellen

![](tabeleErstellen.png)

- Daten add in MYSQL

```mysql
INSERT INTO student (name, email) VALUES ('Max Mustermann', 'max@mustermann.de');
INSERT INTO student (name, email) VALUES ('Erika Musterfrau', 'erika@musterfrau.de');
```

![](add.png)

- Verbindung zur DB über java

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class JDBCdemo {

    public static void main(String[] args) {
        System.out.println("JDBC Demo!");
        selectAllDemo();
    }

    public static void selectAllDemo(){
        System.out.println("Select DEMO mit JDBC");
        //sql comand in eigene variable speicher
        //url für conection zu mysql db
        String connectionUrl ="jdbc:mysql://localhost:3306/jdbcdemo";
        //probiert sich zu DB zu verbinden
        try(Connection conn = DriverManager.getConnection(connectionUrl,"user","12345")){
            System.out.println("Verbindung zur DB herstellt!");
        } catch (SQLException e) {
            System.out.println("Fehler bei Aufbau der Verbindung zur DB: " + e.getMessage());
        }

    }
}
```

- _Select_

```java
try(Connection conn = DriverManager.getConnection(connectionUrl,"user","12345")){
           System.out.println("Verbindung zur DB herstellt!");
           String sqlSelectAllPersons ="SELECT * FROM `student`";
           //zufigen von vertigen sql statmenst schutzt von angrifen
           PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectAllPersons);//stat variable ist auch möglich ("SELECT * FROM `student`")
           //
           ResultSet rs = preparedStatement.executeQuery();
           //für jeden ergebnis ausgeben so lange was in tb gibt
           while(rs.next()){
               //hold das was sich in erste spalte befindet und speichert in int
               int id = rs.getInt("id");//an stelle von "id" können wir schreiben 1 spalten nummer
               String name = rs.getString("name");
               String email = rs.getString("email");
               System.out.println("Student aus der DB - ID: " + id +" - Name: " + name + ", Email: " + email);
           }
       } catch (SQLException e) {
           System.out.println("Fehler bei Aufbau der Verbindung zur DB: " + e.getMessage());
       }
```

- _Insert_

```java
public static void insertStudentDemo(){
        System.out.println("Insert DEMO mit JDBC");
        //url für conection zu mysql db
        String connectionUrl ="jdbc:mysql://localhost:3306/jdbcdemo";
        //probiert sich zu DB zu verbinden
        try(Connection conn = DriverManager.getConnection(connectionUrl,"user","12345")){
            System.out.println("Verbindung zur DB herstellt!");
            //zufigen von vertigen sql statmenst schutzt von angrifen
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `student` (name,email) VALUES ( ?,?)");
            try {
                preparedStatement.setString(1,"Peter Zeck");
                preparedStatement.setString(2,"p.zeck@hotmail.com");
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println(rowAffected + " Datensätze eingefügt");
                //catch nur für insert
            }catch (SQLException ex){
                System.out.println("Fehle in SQL - Insert "+ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Fehler bei Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
```

- _Update_

```java
public static void updateStudentDemo(){
        System.out.println("Insert DEMO mit JDBC");
        //url für conection zu mysql db
        String connectionUrl ="jdbc:mysql://localhost:3306/jdbcdemo";
        //probiert sich zu DB zu verbinden
        try(Connection conn = DriverManager.getConnection(connectionUrl,"user","12345")){
            System.out.println("Verbindung zur DB herstellt!");
            //zufigen von vertigen sql statmenst schutzt von angrifen
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE `student` SET `name` = ? WHERE `student`.`id` = 2");
            try {
                preparedStatement.setString(1,"Hans Zimmer");
                int affectedRows  = preparedStatement.executeUpdate();
                System.out.println(affectedRows + " Datensätze eingefügt");
                //catch nur für insert
            }catch (SQLException ex){
                System.out.println("Fehle in SQL - Update "+ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Fehler bei Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
```

- _Delete_

```java
public static void deleteStudentDemo(int studentID){
        System.out.println("Delet DEMO mit JDBC");
        //url für conection zu mysql db
        String connectionUrl ="jdbc:mysql://localhost:3306/jdbcdemo";
        //probiert sich zu DB zu verbinden
        try(Connection conn = DriverManager.getConnection(connectionUrl,"user","12345")){
            System.out.println("Verbindung zur DB herstellt!");
            //zufigen von vertigen sql statmenst schutzt von angrifen
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `student` WHERE `student`.`id` = ?");
            try {
                preparedStatement.setInt(1,studentID);
                int affectedRows  = preparedStatement.executeUpdate();
                System.out.println(affectedRows + " Datensätze die gelöst sind");
                //catch nur für insert
            }catch (SQLException ex){
                System.out.println("Fehle in SQL - Delete "+ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Fehler bei Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
```

- _Like_

```java
private static void findAllByNameLike(String byName) {
        System.out.println("Finde all by Name DEMO mit JDBC");
        //url für conection zu mysql db
        String connectionUrl ="jdbc:mysql://localhost:3306/jdbcdemo";

        //probiert sich zu DB zu verbinden
        try(Connection conn = DriverManager.getConnection(connectionUrl,"user","12345")){
            System.out.println("Verbindung zur DB herstellt!");
            //zufigen von vertigen sql statmenst schutzt von angrifen
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `student` WHERE `student`.`name` LIKE ?");
            preparedStatement.setString(1,"%"+byName+"%");
            //abfrage an db
            ResultSet rs = preparedStatement.executeQuery();
            //für jeden ergebnis ausgeben so lange was in tb gibt
            while(rs.next()){
                //hold das was sich in erste spalte befindet und speichert in int
                int id = rs.getInt("id");//an stelle von "id" können wir schreiben 1 spalten nummer
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB - ID: " + id +" - Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Fehler bei Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
```

## AUFGABE 2: JDBC INTRO TEIL 2

![](city.png)

```java
import java.sql.*;
public class CityTabele {

    public static void main(String[] args) {
        System.out.println("Welcome to City Tabele");
        selectAllCity();
        insertCity("Nico Wollinger","Hall in Tirol", 6420);
    }
    public static void selectAllCity(){
        System.out.println("Select all from City");
        //prepar stament
        String sqlSelectAllCity ="SELECT * FROM city";
        //url connect db
        String connectionSQLUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        try(Connection conn = DriverManager.getConnection(connectionSQLUrl,"user","12345")){
            System.out.println("Verbindung zu DB herstellt");
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectAllCity);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("namePerson");
                String city = rs.getString("city");
                int plz = rs.getInt("plz");
                System.out.println("Hello, ID: "+id+", deine Name ist: " +name + ", du komst aus: "+plz+" "+city);
            }
        }catch (SQLException e){
            System.out.println("Fehler bei Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
    public static void insertCity(String name, String city,int plz){
        System.out.println("Insert City");
        String prepareCity = "INNSERT INTO `city` (`namePerson`,`city`,`plz`) VALUES (?,?,?)";
        String connectionSQLUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        try(Connection conn = DriverManager.getConnection(connectionSQLUrl,"user","12345")){
            System.out.println("Verbindung zu DB herstellt");
            PreparedStatement preparedStatement = conn.prepareStatement(prepareCity);
            try{
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,city);
                preparedStatement.setInt(3,plz);
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println(rowAffected + " Datensätze eingefügt");
            }catch(SQLException ex){
                System.out.println("Fehler bei Insert in DB: " + ex.getMessage());
            }
        } catch (SQLException e){
            System.out.println("Fehler bei Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
}

```

## AUFGABE 3: JDBC UND DAO – KURSE

```mysql
CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    hours INT,
    begindate DATE,
    enddate DATE,
    coursetype VARCHAR(50)
);

INSERT INTO courses (name, description, hours, begindate, enddate, coursetype) VALUES
('Introduction to SQL', 'Learn the basics of SQL and database management.', 9, '2025-01-15', '2026-02-15', 'OE'),
('Advanced Java Programming', 'Deep dive into advanced Java concepts and frameworks.', 2, '2024-03-01', '2024-04-30', 'ZA'),
('Web Development Bootcamp', 'Comprehensive course on front-end and back-end web development.', 1, '2025-05-10', '2025-07-10', 'FF'),
('Data Science with Python', 'Explore data analysis, visualization, and machine learning using Python.', 4, '2024-08-01', '2024-09-30', 'FF'),
('Project Management Fundamentals', 'Learn essential project management skills and methodologies.', 3, '2026-10-05', '2026-11-05', 'OE');

select * from courses;
```

![](courses.png)

## DAO JDBC Aufgabe 3 und 4

```java
import dataaccess.MySqlCourseRepository;
import dataaccess.MySqlStudentsRepository;
import dataaccess.MysqlDatabaseConnection;
import ui.Cli;
import dataaccess.MySqlBookingRepository;
import service.BookingService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        try{
            MySqlCourseRepository courseRepo = new MySqlCourseRepository();
            MySqlStudentsRepository studentRepo = new MySqlStudentsRepository();
            MySqlBookingRepository bookingRepo = new MySqlBookingRepository();
            BookingService bookingService = new BookingService(bookingRepo, courseRepo, studentRepo);
            Cli myCli = new Cli(courseRepo, studentRepo, bookingService);
            myCli.start();
            /*Connection myConnection = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/jdbcdemo","user","12345");
            System.out.println("Verbindung aufgebaut");*/
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

package ui;
import dataaccess.DatabaseException;
import dataaccess.MySqlCourseRepository;
import dataaccess.MySqlStudentsRepository;
import dataaccess.MyStudentsRepository;
import domain.Course;
import domain.CourseType;
import domain.InvalidValueException;
import domain.Students;

import java.util.*;
import java.sql.Date;

import static java.util.Date.*;

import service.BookingService;
import domain.BookingInfo;

//MENUE
public class Cli {
    Scanner scan;
    MySqlCourseRepository repo;
    MySqlStudentsRepository srepo;
    BookingService bookingService;
    public Cli(MySqlCourseRepository repo,MySqlStudentsRepository srepo, BookingService bookingService){
        this.scan = new Scanner(System.in);
        this.repo = repo;
        this.srepo = srepo;
        this.bookingService = bookingService;
    }
    public void start(){
        String input = "-";
        while(!input.equals("x")){
            showMenue();
            input = scan.nextLine();
            switch (input){
                case "1":
                    System.out.println("Kurseeingabe");
                    addCours();
                    break;
                case "2":
                    System.out.println("Alle Kurse anzeigen");
                    showAllCourses();
                    break;
                case "3":
                    System.out.println("Alle Kurse anzeigen");
                    showCoursesDetails();
                    break;
                case "4":
                    System.out.println("Update Kursdetails");
                    updateCourseDetails();
                    break;
                case "5":
                    System.out.println("Kurs Löschen");
                    deleteCours();
                    break;
                case "6":
                    System.out.println("Kurs suchen");
                    courseSuchen();
                    break;
                case "7":
                    System.out.println("Laufende Kurse suchen");
                    runningCourse();
                    break;
                case "8":
                    System.out.println("Kurs buchen");
                    bookCourse();
                    break;
                    //STUDENTS
                case "11":
                    System.out.println("Student eingeben");
                    addStudent();
                    break;
                case "12":
                    System.out.println("Alle studenten anzeigen");
                    showAllStudent();
                    break;
                case "13":
                    System.out.println("Studentdetails anzeigen");
                    showStudentDetails();
                    break;
                case "14":
                    System.out.println("Student updaten");
                    updateStudent();
                    break;
                case "15":
                    System.out.println("Student löschen");
                    deleteStudent();
                    break;
                case "22":
                    System.out.println("Alle Bookings anzeigen");
                    showAllBookings();
                    break;
                    //BOOKING
                case "23":
                    System.out.println("Cours buchen");
                    bookCourse();
                    break;
                    //ENDE
                case "x":
                    System.out.println("Auf Wiedersehen");
                    break;
                default:
                    inputError();
            }
        }
        scan.close();
    }

    private void showAllBookings() {

    }

    private void deleteStudent() {
        System.out.println("Welchen Students möchten sie Löschen");
        Long studentIdToDelet = Long.parseLong(scan.nextLine());
        try{
            srepo.deleteById(studentIdToDelet);
        } catch (DatabaseException e) {
            System.out.println("Fehler beim Löschen des Kurses: " + e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateStudent() {
        System.out.println("Für welches Students-ID möchten Sie die Details ändern?");
        Long studentsID = Long.parseLong(scan.nextLine());
        try{
            Optional<Students> studentsOptional = srepo.getById(studentsID);
            if (studentsOptional.isEmpty()){
                System.out.println("Kurs mit der ID " + studentsID + " nicht gefunden.");
            }else{
                Students students = studentsOptional.get();
                System.out.println("Enderungen für Students");
                System.out.println(students);
                String vorname,lastname;
                Date birthDate;

                System.out.println("Bitte alle neuen Kurs daten eingeben:");
                System.out.println("Vorname:" );
                vorname = scan.nextLine();
                System.out.println("Nachname:");
                lastname = scan.nextLine();
                System.out.println("BirthDate (yyyy-mm-dd):");
                birthDate = Date.valueOf(scan.nextLine());

                Optional<Students> optionalStudentsUpdatet = srepo.update(
                        new Students(
                                students.getId(),
                                vorname.equals("") ? students.getFirstName() : vorname,
                                lastname.equals("") ? students.getLastName() : lastname,
                                birthDate == null ? students.getBirthDay() : birthDate
                        )
                );
                if(optionalStudentsUpdatet.isPresent()){
                    System.out.println("Kurs erfolgreich geandert: " + optionalStudentsUpdatet.get());
                } else {
                    System.out.println("Kurs konnte nicht geandert werden");
                }
            }
        } catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Fehler bei der Eingabe: " + illegalArgumentException.getMessage());
        }catch (InvalidValueException invalidValueException){
            System.out.println("Ungültiger Wert: " + invalidValueException.getMessage());
        }catch (DatabaseException databaseException){
            System.out.println("Fehler bei der Datenbankabfrage: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    private void showStudentDetails() {
        System.out.println("Für welche Student-ID möchten Sie die Details anzeigen?");
        Long studentID = Long.parseLong(scan.nextLine());
        try{
            Optional<Students> student = srepo.getById(studentID);
            if(student.isPresent()){
                System.out.println(student.get());
            } else {
                System.out.println("Kein Student mit der ID " + studentID + " vorhanden");
            }
    }catch (DatabaseException databaseException){
        System.out.println("Fehler bei der Datenbankabfrage: " + databaseException.getMessage());
    } catch (Exception e) {
        System.out.println("Allgemeiner Fehler: " + e.getMessage());
    }}

    private void showAllStudent() {
        List<Students> list = null;
        try{
            list = srepo.getAll();
            if(list.size()>0){
                for(Students s : list){
                    System.out.println(s);
                }
            } else {
                System.out.println("Keine Studente vorhanden");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Fehler bei der Datenbankabfrage: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    private void addStudent() {
        String vorname, nachname;
        Date geburtsdatum;
        try {
            System.out.println("Bitte alle Studenten daten eingeben:");
            System.out.println("Vorname:");
            vorname = scan.nextLine();
            if (vorname.equals("")) {
                throw new IllegalArgumentException("Vorname darf nicht leer sein");
            }
            System.out.println("Nachname:");
            nachname = scan.nextLine();
            if (nachname.equals("")) {
                throw new IllegalArgumentException("Nachname darf nicht leer sein");
            }
            System.out.println("Geburtsdatum (yyyy-mm-dd):");
            geburtsdatum = Date.valueOf(scan.nextLine());
            Optional<Students> optionalStudents = srepo.insert(new Students(vorname, nachname, geburtsdatum));
            if (optionalStudents.isPresent()) {
                System.out.println("Student erfolgreich angelegt: " + optionalStudents.get());
            } else {
                System.out.println("Student konnte nicht angelegt werden");
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Fehler bei der Eingabe: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Ungültiger Wert: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Fehler bei der Datenbankabfrage: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    private void runningCourse() {
        System.out.println("Laufende Kurse:");
        List<Course> lista= new ArrayList<>();
        try{
            lista = repo.findAllRunningCourses();
            for(Course course : lista){
                System.out.println(course);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void courseSuchen() {
        System.out.println("Suchtext eingeben (Name oder Beschreibung):");
        String search = scan.nextLine();
        List<Course> lista;
        try{
            lista = repo.findAllCourserByNameOrDescription(search);
            for(Course course : lista){
                System.out.println(course);
            }
        }catch (DatabaseException e){
            System.out.println("Fehler bei der Suche: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCours() {
        System.out.println("Welchen Kurs möchten sie Löschen");
        Long coursIdToDelet = Long.parseLong(scan.nextLine());

        try{
            repo.deleteById(coursIdToDelet);
        } catch (DatabaseException e) {
            System.out.println("Fehler beim Löschen des Kurses: " + e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateCourseDetails() {
        System.out.println("Für welches Kurs-ID möchten Sie die Details ändern?");
        Long courseID = Long.parseLong(scan.nextLine());
        try{
            Optional<Course> courseOptional = repo.getById(courseID);
            if (courseOptional.isEmpty()){
                System.out.println("Kurs mit der ID " + courseID + " nicht gefunden.");
            }else{
                Course course = courseOptional.get();
                System.out.println("Enderungen für Kurs");
                System.out.println(course);
                String name,description;
                int hours;
                Date beginDate,endDate;
                CourseType courseType;

                System.out.println("Bitte alle neuen Kurs daten eingeben:");
                System.out.println("Name:" );
                name = scan.nextLine();
                System.out.println("Description:");
                description = scan.nextLine();
                System.out.println("Hours:");
                hours = Integer.parseInt(scan.nextLine());
                System.out.println("BeginDate (yyyy-mm-dd):");
                beginDate = Date.valueOf(scan.nextLine());
                System.out.println("EndDate (yyyy-mm-dd):");
                endDate = Date.valueOf(scan.nextLine());
                System.out.println("CourseType (ZA/BF/FF/DE):");
                courseType = CourseType.valueOf(scan.nextLine());

                Optional<Course> optionalCourseUpdatet = repo.update(
                        new Course(
                                course.getId(),
                                name.equals("") ? course.getName() : name,
                                description.equals("") ? course.getDescription() : description,
                                hours == 0 ? course.getHours() : Integer.parseInt(String.valueOf(hours)),
                                beginDate == null ? course.getBeginDate() : beginDate,
                                endDate == null ? course.getEndDate() : endDate,
                                courseType == null ? (CourseType) course.getCourseType() : courseType
                        )
                );
                if(optionalCourseUpdatet.isPresent()){
                    System.out.println("Kurs erfolgreich geandert: " + optionalCourseUpdatet.get());
                } else {
                    System.out.println("Kurs konnte nicht geandert werden");
                }
            }
        } catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Fehler bei der Eingabe: " + illegalArgumentException.getMessage());
        }catch (InvalidValueException invalidValueException){
            System.out.println("Ungültiger Wert: " + invalidValueException.getMessage());
        }catch (DatabaseException databaseException){
            System.out.println("Fehler bei der Datenbankabfrage: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    private void addCours() {
        String name,description;
        int hours;
        Date beginDate,endDate;
        CourseType courseType;
        try{
            System.out.println("Bitte alle Kurs daten eingeben:");
            System.out.println("Name:");
            name = scan.nextLine();
            if(name.equals("")){
                throw new IllegalArgumentException("Name darf nicht leer sein");
            }
            System.out.println("Description:");
            description = scan.nextLine();
            if(description.equals("")){
                throw new IllegalArgumentException("Description darf nicht leer sein");
            }
            System.out.println("Hours:");
            hours = Integer.parseInt(scan.nextLine());
            System.out.println("BeginDate (yyyy-mm-dd):");
            beginDate = Date.valueOf(scan.nextLine());
            System.out.println("EndDate (yyyy-mm-dd):");
            endDate = Date.valueOf(scan.nextLine());
            System.out.println("CourseType (ZA/BF/FF/DE):");
            courseType = CourseType.valueOf(scan.nextLine());
            Optional<Course> optionalCourse = repo.insert(new Course(name,description,hours,beginDate,endDate,courseType));

            if(optionalCourse.isPresent()){
                System.out.println("Kurs erfolgreich angelegt: " + optionalCourse.get());
            } else {
                System.out.println("Kurs konnte nicht angelegt werden");
            }

        }catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Fehler bei der Eingabe: " + illegalArgumentException.getMessage());
        }catch (InvalidValueException invalidValueException){
            System.out.println("Ungültiger Wert: " + invalidValueException.getMessage());
        }catch (DatabaseException databaseException){
            System.out.println("Fehler bei der Datenbankabfrage: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    private void showCoursesDetails() {
        System.out.println("Für welche Kurs-ID möchten Sie die Details anzeigen?");
        Long courseID = Long.parseLong(scan.nextLine());
        try{
            Optional<Course> course = repo.getById(courseID);
            if(course.isPresent()){
                System.out.println(course.get());
            } else {
                System.out.println("Kein Kurs mit der ID " + courseID + " vorhanden");
            }
        }catch (DatabaseException databaseException){
            System.out.println("Fehler bei der Datenbankabfrage: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    private void showAllCourses() {
        List<Course> list = null;
        try{
            list = repo.getAll();
            if(list.size()>0){
                for(Course c : list){
                    System.out.println(c);
                }
            } else {
                System.out.println("Keine Kurse vorhanden");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Fehler bei der Datenbankabfrage: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    private void bookCourse(){
        try{
            System.out.println("Student-ID:");
            Long studentId = Long.parseLong(scan.nextLine());
            System.out.println("Course-ID:");
            Long courseId = Long.parseLong(scan.nextLine());

            BookingInfo info = bookingService.createBooking(studentId, courseId);
            System.out.println("Buchung erfolgreich: " + info);
        } catch (IllegalArgumentException ia){
            System.out.println("Fehler bei der Eingabe: " + ia.getMessage());
        } catch (InvalidValueException ive){
            System.out.println("Ungültiger Wert: " + ive.getMessage());
        } catch (DatabaseException de){
            System.out.println("Datenbankfehler: " + de.getMessage());
        } catch (Exception e){
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    private void showMenue(){
        System.out.println("------------------------KURSMANAGEMENT--------------------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t"+"(3) Kursdetails anzeigen");
        System.out.println("(4) Kursdetails endern \t (5) Kurs Löschen \t (6) Course suchen");
        System.out.println("(7) Kurse die Laufen \t (8) Kurs buchen \t (-) XXXX");
        System.out.println("-----------------------------STUDENT-----------------------------");
        System.out.println("(11) Student eingeben \t (12) Alle Studente anzeigen \t"+"(13) Studentdetails anzeigen");
        System.out.println("(14) Studentdetails endern \t (15) Student Löschen \t (16) XXXX");
        System.out.println("(17) XXXXXXXXX \t (-) XXXX \t (-) XXXX");
        System.out.println("----------------------------BOOKING-----------------------------");
        System.out.println("(21) XXXXX \t (22) XXXX \t"+"(23) Kurs Buchen");
        System.out.println("(24) XXXX \t (25) xxxx \t (26) XXXX");
        System.out.println("(27) XXXXXXXXX \t (-) XXXX \t (-) XXXX");
        System.out.println("(x) ENDE");
    }
    private void inputError(){
        System.out.println("Bitte nur die Zahlen der Menüaswahl eingeben");
    }
}

package util;

public class Assert {
    public static void notNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Greska");
        }
    }
}

package domain;

import java.sql.Date;

public class Students extends BaseEntity{
    private String firstName, lastName;
    private Date dateOfBirth;

    public Students(Long id,String firstName, String lastName, Date dateOfBirth) {
        super(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setDateOfBirth(dateOfBirth);
    }
    public Students(String firstName, String lastName, Date dateOfBirth) {
        super(null);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setDateOfBirth(dateOfBirth);
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) throws IllegalArgumentException {
        if(firstName != null || !firstName.equals("")) {
            this.firstName = firstName;
        }else{
            throw new IllegalArgumentException("firstName is null");
        }
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) throws IllegalArgumentException {
        if (lastName != null || !lastName.equals("")) {
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("lastName is null");
        }
    }
    public Date getBirthDay() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) throws IllegalArgumentException {
        if(dateOfBirth != null) {
            this.dateOfBirth = dateOfBirth;
        }else {
            throw new IllegalArgumentException("dateOfBirth is null");
        }
    }
    public String toString() {
        return "Students{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                "} " + super.toString();
    }
}
package domain;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
}
package domain;

public enum CourseType {
    OE, BF, ZA, FF
}
package domain;

import java.sql.Date;
//OBJECT zum speicher daten aus DB oder von Object ins DB
public class Course extends BaseEntity {
    private String name, description;
    private int hours;
    private Date beginDate, endDate;
    private CourseType coursType;

    public Course(Long id, String name, String description, int hours, Date beginDate, Date endDate, CourseType coursType) throws InvalidValueException {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseType(coursType);
    }
    public Course(String name, String description, int hours, Date beginDate, Date endDate, CourseType coursType) throws InvalidValueException {
        super(null);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseType(coursType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValueException {
        if(name!=null && !name.isEmpty()){
            this.name = name;
        }else{
            throw new InvalidValueException("Name muss min 2 zeichen lang sein");
        }

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidValueException {
        if(description!=null && !description.isEmpty()){
            this.description = description;
        }else {
            throw new InvalidValueException("Description muss min 2 zeichen lang sein");
        }

    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) throws InvalidValueException {
        if(hours>0 || hours<10){
            this.hours = hours;
        }else{
            throw new  InvalidValueException("Hours muss min 1 zeichen lang");
        }
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) throws InvalidValueException {
        if(beginDate!=null) {
            if (this.endDate != null) {
                if (beginDate.before(this.endDate)) {
                    this.beginDate = beginDate;
                } else {
                    throw new InvalidValueException("Begin date muss vor end date sein");
                }
            } else{
                this.beginDate = beginDate;
            }
        }else {
            throw new InvalidValueException("Startdatum darf nicht null / leer sein");
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) throws InvalidValueException {
        if(endDate!=null) {
            if (this.beginDate != null) {
                if (endDate.after(this.beginDate)) {
                    this.endDate = endDate;
                } else {
                    throw new InvalidValueException("End date muss nach Begindate sein");
                }
            } else{
                this.endDate = endDate;
            }
        }else {
            throw new InvalidValueException("Enddatum darf nicht null / leer sein");
        }
    }

    public CourseType getCoursType() {
        return coursType;
    }

    public void setCourseType(CourseType coursType)throws InvalidValueException {
        if(coursType!=null){
            this.coursType = coursType;
        }
        else{
            throw new InvalidValueException("Cours Type darf nicht null sein");
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" +this.getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", coursType=" + coursType +
                '}';
    }

    public Object getCourseType() {
        return coursType;
    }
}
package domain;

//separat ID weil es offt vorkommt
public abstract class BaseEntity {
    private Long id;

    public BaseEntity(Long id) {
        setId(id);
    }

    public void setId(Long id) {
        if(id==null || id >= 0){
            this.id = id;
        }else{
            throw new InvalidValueException("Kurs-ID muss größer gleich 0 sein");
        }
    }
    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
package dataaccess;

import domain.Students;

import java.sql.Date;
import java.util.List;

public interface MyStudentsRepository extends BaseRepository<Students, Long> {
    List<Students> findAllStudentsByFirstName(String name);
    List<Students> findAllStudentsByLastName(String name);
    List<Students> findAllStudentsByBirthDay(Date birthDay);
}
package dataaccess;

import domain.Students;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlStudentsRepository implements MyStudentsRepository{

    Connection conn;

    public MySqlStudentsRepository()throws ClassNotFoundException, SQLException {
        this.conn = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/jdbcdemo","user","12345");
    }

    @Override
    public List<Students> findAllStudentsByFirstName(String name) {
        return List.of();
    }

    @Override
    public List<Students> findAllStudentsByLastName(String name) {
        return List.of();
    }

    @Override
    public List<Students> findAllStudentsByBirthDay(Date birthDay) {
        return List.of();
    }

    @Override
    public Optional<Students> insert(Students entity) {
        Assert.notNull(entity);
        try{
            String insertSql = "insert into students (firstname, lastname, birthday) values (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDate(3, entity.getBirthDay());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0){
                return Optional.empty();
            }
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                return this.getById(generatedKey.getLong(1));
            }else{
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Students> getById(Long id) throws SQLException {
        Assert.notNull(id);
        if (countStudentsInDbWithId(id) == 0) {
            return Optional.empty();
        } else {
            try{
                String selectSql = "select * from students where id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(selectSql);
                preparedStatement.setLong(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                Students student = new Students(
                        rs.getLong("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birthday")
                );
                return Optional.of(student);
            }catch (SQLException e){
            throw new DatabaseException("Fehler bei der Abfrage des Kurses mit der ID: " + id);
            }
        }
    }
    private int countStudentsInDbWithId(Long id) throws SQLException {
        try{
            String countSql = "select count(*) as count from students where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(countSql);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int studentsCount = rs.getInt("count");
            return studentsCount;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Students> getAll() {
        String sql = "select * from students";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Students> studentList = new ArrayList<>();
            while (rs.next()) {
                studentList.add(new Students(
                        rs.getLong("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birthday")
                ));
            }
            return  studentList;
        } catch (SQLException e) {
            throw new DatabaseException("Fehler bei der Abfrage der Kurse");
        }
    }

    @Override
    public Optional<Students> update(Students entity) throws SQLException {
        Assert.notNull(entity);
        String sql = "update students set firstname = ?, lastname = ?, birthday where id = ?";
        if(countStudentsInDbWithId(entity.getId()) == 0) {
            return Optional.empty();} else {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getLastName());
                preparedStatement.setDate(3, entity.getBirthDay());
                preparedStatement.setLong(4, entity.getId());


                int affectedRows = preparedStatement.executeUpdate();

                if(affectedRows == 0){
                    return Optional.empty();
                }else {
                    return this.getById(entity.getId());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id);
        String sql = "delete from students where id = ?";
        try{
            if(countStudentsInDbWithId(id) == 1) {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            throw new DatabaseException("Fehler beim Löschen des Kurses mit der ID: " + id);
        }
    }
}
package dataaccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//VERBINDUN ZU DB
public class MysqlDatabaseConnection {
    private static Connection con = null;

    private MysqlDatabaseConnection() {

    }
    public static Connection getConnection(String url, String user, String pwd) throws SQLException, ClassNotFoundException {
        if(con != null) {
            return con;
        }else{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pwd);
            return con;
        }
    }
}
package dataaccess;

import domain.Course;
import domain.CourseType;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlCourseRepository implements MyCourseRepository{
    private Connection conn;

    public MySqlCourseRepository()throws SQLException,ClassNotFoundException {
        this.conn = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/jdbcdemo","user","12345");
    }
    @Override
    public List<Course> findAllCoutsesByName(String name) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoutsesByDescription(String description) {
        return List.of();
    }

    @Override
    public List<Course> findAllCourserByNameOrDescription(String searchText) {
        try{
            String sql = "select * from courses where lower(name) like lower(?) or lower(description) like lower(?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            String searchPattern = "%" + searchText + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while (resultSet.next()){
                courseList.add(new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("begindate"),
                        resultSet.getDate("enddate"),
                        CourseType.valueOf(resultSet.getString("coursetype"))
                ));
            }
            return courseList;
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Course> findAllCourserByStartDate(Date startDate) {
        return List.of();
    }

    @Override
    public List<Course> findAllRunningCourses() {
        try{
        String sql = "select * from courses where now()<enddate";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Course> courseList = new ArrayList<>();
        while (rs.next()){
            courseList.add(new Course(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("hours"),
                    rs.getDate("begindate"),
                    rs.getDate("enddate"),
                    CourseType.valueOf(rs.getString("coursetype"))
            ));
        }
        return courseList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findAllCourserByCourseType(int courseType) {
        return List.of();
    }

    @Override
    public Optional<Course> insert(Course entity) {

        Assert.notNull(entity);

        try{
            String query = "insert into courses (name, description, hours, begindate, enddate, coursetype) values (?,?,?,?,?,?)";
            PreparedStatement preparedStatement= conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);;
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getHours());
            preparedStatement.setDate(4, entity.getBeginDate());
            preparedStatement.setDate(5, entity.getEndDate());
            preparedStatement.setString(6, entity.getCourseType().toString());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0){
                return Optional.empty();
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                return  this.getById(generatedKeys.getLong(1));
            }else{
                return Optional.empty();
            }
        }catch (SQLException sqlException){
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public Optional<Course> getById(Long id) throws SQLException {
        Assert.notNull(id);
        if(countCoursesInObWithId(id) == 0) {
            return Optional.empty();
        }
        else{
            try{
                String query = "select * from courses where id = ?";
                PreparedStatement preparedStatement =conn.prepareStatement(query);
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                Course course = new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("begindate"),
                        resultSet.getDate("enddate"),
                        CourseType.valueOf(resultSet.getString("coursetype"))
                );
                return Optional.of(course);
            }catch (SQLException e){
                throw new DatabaseException("Fehler bei der Abfrage des Kurses mit der ID: " + id);
            }
        }
    }
    private int countCoursesInObWithId(Long id) {
        try {
            String sql = "select count(*) from courses where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int courseCount = rs.getInt(1);
            return courseCount;
        } catch (SQLException e) {
            throw new DatabaseException("Fehler bei der Abfrage der Kursanzahl mit der ID: " + id);
        }
    }

    @Override
    public List<Course> getAll() {
        String sql = "select * from courses";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while (rs.next()) {
                courseList.add(new Course(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("hours"),
                        rs.getDate("begindate"),
                        rs.getDate("enddate"),
                        CourseType.valueOf(rs.getString("coursetype"))
                ));
            }
            return  courseList;
        } catch (SQLException e) {
            throw new DatabaseException("Fehler bei der Abfrage der Kurse");
        }
    }

    @Override
    public Optional<Course> update(Course entity) {
        Assert.notNull(entity);
        String sql = "update courses set name = ?, description = ?, hours = ?, begindate = ?, enddate = ?, coursetype = ? where id = ?";
        if(countCoursesInObWithId(entity.getId()) == 0) {
        return Optional.empty();} else {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setInt(3, entity.getHours());
                preparedStatement.setDate(4, entity.getBeginDate());
                preparedStatement.setDate(5, entity.getEndDate());
                preparedStatement.setString(6, entity.getCourseType().toString());
                preparedStatement.setLong(7, entity.getId());


                int affectedRows = preparedStatement.executeUpdate();

                if(affectedRows == 0){
                    return Optional.empty();
                }else {
                    return this.getById(entity.getId());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


        @Override
    public void deleteById(Long id) {
        Assert.notNull(id);
        String sql = "delete from courses where id = ?";
        try{
        if(countCoursesInObWithId(id) == 1) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        }catch (SQLException e){
            throw new DatabaseException("Fehler beim Löschen des Kurses mit der ID: " + id);
        }
    }
}
package dataaccess;

import domain.Course;

import java.sql.Date;
import java.util.List;

//Zusatz 2 stufe DAO mit Spezifischen sachen für unsere DB

public interface MyCourseRepository extends BaseRepository<Course, Long> {
    List<Course> findAllCoutsesByName(String name);
    List<Course> findAllCoutsesByDescription(String description);
    List<Course> findAllCourserByNameOrDescription(String searchText);
    List<Course> findAllCourserByStartDate(Date startDate);
    List<Course> findAllRunningCourses();
    List<Course> findAllCourserByCourseType(int courseType);
}
package dataaccess;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
package dataaccess;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
//BASIS 1 stufe DAO was für alle DB notwendig ist
public interface BaseRepository<T,I> {
    Optional<T> insert(T entity);
    Optional<T> getById(I id) throws SQLException;
    List<T> getAll();
    Optional<T> update(T entity) throws SQLException;
    void deleteById(I id);
}
```

## DAO Aufgabe 5 - Booking

Leider hat Coopilot das nicht zu ende gebracht da ich nur begrentzte zugang habe

Das hat er gemacht:

```java
package dataaccess;

import domain.Booking;
import domain.BookingInfo;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Optional<Booking> findById(Long id) throws DatabaseException;
    List<Booking> findByStudentId(Long studentId) throws DatabaseException;
    Optional<Booking> findByStudentAndCourse(Long studentId, Long courseId) throws DatabaseException;
    Long create(Long studentId, Long courseId) throws DatabaseException;
    Optional<BookingInfo> findBookingInfoById(Long bookingId) throws DatabaseException;
}

package dataaccess;

import domain.Booking;
import domain.BookingInfo;
import domain.Course;
import domain.Students;
import domain.InvalidValueException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlBookingRepository implements BookingRepository{
    private Connection conn;

    public MySqlBookingRepository() throws SQLException, ClassNotFoundException {
        this.conn = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/jdbcdemo","user","12345");
    }

    @Override
    public Optional<Booking> findById(Long id) throws DatabaseException {
        try{
            String sql = "select * from bookings where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return Optional.of(new Booking(
                        rs.getLong("id"),
                        rs.getLong("student_id"),
                        rs.getLong("course_id"),
                        rs.getTimestamp("booked_at")
                ));
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Booking> findByStudentId(Long studentId) throws DatabaseException {
        try{
            String sql = "select * from bookings where student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, studentId);
            ResultSet rs = ps.executeQuery();
            ArrayList<Booking> list = new ArrayList<>();
            while(rs.next()){
                list.add(new Booking(
                        rs.getLong("id"),
                        rs.getLong("student_id"),
                        rs.getLong("course_id"),
                        rs.getTimestamp("booked_at")
                ));
            }
            return list;
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Booking> findByStudentAndCourse(Long studentId, Long courseId) throws DatabaseException {
        try{
            String sql = "select * from bookings where student_id = ? and course_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, studentId);
            ps.setLong(2, courseId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return Optional.of(new Booking(
                        rs.getLong("id"),
                        rs.getLong("student_id"),
                        rs.getLong("course_id"),
                        rs.getTimestamp("booked_at")
                ));
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Long create(Long studentId, Long courseId) throws DatabaseException {
        try{
            String sql = "insert into bookings (student_id, course_id, booked_at) values (?,?,CURRENT_TIMESTAMP)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, studentId);
            ps.setLong(2, courseId);
            int affected = ps.executeUpdate();
            if(affected==0) throw new DatabaseException("Booking konnte nicht erstellt werden");
            ResultSet gen = ps.getGeneratedKeys();
            if(gen.next()) return gen.getLong(1);
            throw new DatabaseException("Kein generierter Key nach Insert");
        }catch (SQLException e){
            // Map integrity constraint violations (duplicate booking / FK) to domain exception
            String msg = e.getMessage() == null ? "" : e.getMessage().toLowerCase();
            int errorCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            boolean integrityViolation = e instanceof SQLIntegrityConstraintViolationException
                    || (sqlState != null && sqlState.startsWith("23"))
                    || msg.contains("duplicate") || msg.contains("unique")
                    || errorCode == 1062; // MySQL duplicate entry

            if(integrityViolation){
                // If it's a duplicate entry on (student_id, course_id) we translate to a friendly message
                if(msg.contains("duplicate") || errorCode == 1062 || msg.contains("unique")){
                    throw new InvalidValueException("Student hat diesen Kurs bereits gebucht");
                }
                // FK violation or other integrity error
                throw new InvalidValueException("Integritätsfehler beim Erstellen der Buchung: " + e.getMessage());
            }

            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<BookingInfo> findBookingInfoById(Long bookingId) throws DatabaseException {
        try{
            String sql = "select b.id as booking_id, b.booked_at, s.id as student_id, s.firstname, s.lastname, s.birthday, c.id as course_id, c.name, c.description, c.hours, c.begindate, c.enddate, c.coursetype from bookings b join students s on b.student_id = s.id join courses c on b.course_id = c.id where b.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Students s = new Students(
                        rs.getLong("student_id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birthday")
                );
                Course c = new Course(
                        rs.getLong("course_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("hours"),
                        rs.getDate("begindate"),
                        rs.getDate("enddate"),
                        domain.CourseType.valueOf(rs.getString("coursetype"))
                );
                BookingInfo info = new BookingInfo(
                        rs.getLong("booking_id"),
                        rs.getTimestamp("booked_at"),
                        s,
                        c
                );
                return Optional.of(info);
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
    }
}

package domain;

import java.sql.Timestamp;

public class Booking extends BaseEntity {
    private Long studentId;
    private Long courseId;
    private Timestamp bookedAt;

    public Booking(Long id, Long studentId, Long courseId, Timestamp bookedAt) {
        super(id);
        this.studentId = studentId;
        this.courseId = courseId;
        this.bookedAt = bookedAt;
    }

    public Booking(Long studentId, Long courseId, Timestamp bookedAt) {
        super(null);
        this.studentId = studentId;
        this.courseId = courseId;
        this.bookedAt = bookedAt;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Timestamp getBookedAt() {
        return bookedAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + this.getId() +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", bookedAt=" + bookedAt +
                '}';
    }
}

package domain;

import java.sql.Timestamp;

public class BookingInfo {
    private Long bookingId;
    private Timestamp bookedAt;
    private Students student;
    private Course course;

    public BookingInfo(Long bookingId, Timestamp bookedAt, Students student, Course course) {
        this.bookingId = bookingId;
        this.bookedAt = bookedAt;
        this.student = student;
        this.course = course;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Timestamp getBookedAt() {
        return bookedAt;
    }

    public Students getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "BookingInfo{" +
                "bookingId=" + bookingId +
                ", bookedAt=" + bookedAt +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}

package service;

import dataaccess.BookingRepository;
import dataaccess.MySqlBookingRepository;
import dataaccess.MyCourseRepository;
import dataaccess.MySqlCourseRepository;
import dataaccess.MyStudentsRepository;
import dataaccess.MySqlStudentsRepository;
import dataaccess.DatabaseException;
import domain.BookingInfo;
import domain.Booking;
import domain.Course;
import domain.Students;
import domain.InvalidValueException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class BookingService {
    private BookingRepository bookingRepository;
    private MyCourseRepository courseRepository;
    private MyStudentsRepository studentsRepository;

    public BookingService(BookingRepository bookingRepository, MyCourseRepository courseRepository, MyStudentsRepository studentsRepository){
        this.bookingRepository = bookingRepository;
        this.courseRepository = courseRepository;
        this.studentsRepository = studentsRepository;
    }

    public BookingInfo createBooking(Long studentId, Long courseId) throws InvalidValueException, DatabaseException {
        if(studentId == null || studentId <= 0) throw new InvalidValueException("Ungültige Student-ID");
        if(courseId == null || courseId <= 0) throw new InvalidValueException("Ungültige Course-ID");

        try{
            // Prüfen ob Student existiert
            Optional<Students> sOpt = studentsRepository.getById(studentId);
            if(sOpt.isEmpty()) throw new InvalidValueException("Student nicht gefunden");

            // Prüfen ob Course existiert
            Optional<Course> cOpt = courseRepository.getById(courseId);
            if(cOpt.isEmpty()) throw new InvalidValueException("Course nicht gefunden");

            // Prüfen ob bereits gebucht
            Optional<Booking> existing = bookingRepository.findByStudentAndCourse(studentId, courseId);
            if(existing.isPresent()) throw new InvalidValueException("Student hat diesen Kurs bereits gebucht");

            // Versuch Booking anzulegen (Repository wirft DatabaseException bei Problemen)
            Long bookingId = bookingRepository.create(studentId, courseId);
            Optional<BookingInfo> info = bookingRepository.findBookingInfoById(bookingId);
            if(info.isPresent()) return info.get();
            throw new DatabaseException("Booking erstellt, aber konnte nicht gelesen werden");
        } catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
    }
}

```

Habe bisschen mitgemacht und
![](dao_mit_git.png)

```java
//BOOKING
    case "23":
        System.out.println("Cours buchen");
        bookCourse();
        break;

private void bookCourse(){
        try{
            System.out.println("Student-ID:");
            Long studentId = Long.parseLong(scan.nextLine());
            System.out.println("Course-ID:");
            Long courseId = Long.parseLong(scan.nextLine());

            BookingInfo info = bookingService.createBooking(studentId, courseId);
            System.out.println("Buchung erfolgreich: " + info);
        } catch (IllegalArgumentException ia){
            System.out.println("Fehler bei der Eingabe: " + ia.getMessage());
        } catch (InvalidValueException ive){
            System.out.println("Ungültiger Wert: " + ive.getMessage());
        } catch (DatabaseException de){
            System.out.println("Datenbankfehler: " + de.getMessage());
        } catch (Exception e){
            System.out.println("Allgemeiner Fehler: " + e.getMessage());
        }
    }

    System.out.println("----------------------------BOOKING-----------------------------");
        System.out.println("(21) XXXXX \t (22) XXXX \t"+"(23) Kurs Buchen");
        System.out.println("(24) XXXX \t (25) xxxx \t (26) XXXX");
        System.out.println("(27) XXXXXXXXX \t (-) XXXX \t (-) XXXX");
```
