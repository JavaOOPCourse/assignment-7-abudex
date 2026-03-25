import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() throws IOException, FileNotFoundException, NumberFormatException, InvalidScoreException {
        try (BufferedReader reader = new BufferedReader(new FileReader("input/students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("###")) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    try {
                        int score = Integer.parseInt(parts[1].trim());
                        Student student = new Student(name, score);
                        students.add(student);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("something goes wrong");
        } 
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь

        int points = 0;

        for (Student s: students){
            points += s.getScore();
        }

        averageScore = points/students.size();

        Comparator<Student> byScore = new Comparator<Student>(){
            public int compare(Student a, Student b){
                return Integer.compare(b.getScore(), a.getScore());
            }
        };
        students.sort(byScore);
        for (Student s: students){
            System.out.println(s.getName());
        }

        highestStudent = students.getFirst();
        System.out.println("average: " + averageScore);
        System.out.println("highest score: " + highestStudent.getScore());
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() throws IOException{
        // TODO: запись результата в файл здесь
        try( BufferedWriter bw = new BufferedWriter(new FileWriter("output/report.txt"))){
            bw.write("Average: " + averageScore);
            bw.newLine();
            bw.write("Highest: " + highestStudent.getName() + " " + highestStudent.getScore());
            bw.newLine();
            for (Student s: students) {
                bw.write(s.getName() + " " + s.getScore());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

class Student{
    private String name;
    private int score;
    
    public Student(String name, int score) throws InvalidScoreException{
        if (score < 0 || score > 100) {
            throw new InvalidScoreException("invalid score");
        }
        this.name = name;
        this.score = score;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getScore() {
        return this.score;
    }
}