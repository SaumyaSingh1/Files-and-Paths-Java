import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class SimpleFileVisitorExample extends SimpleFileVisitor<Path> {
    Path resource= Paths.get("/home/sausingh/Documents/Test");
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if(file.getFileName().toString().equals("Saumya.txt")){
            System.out.println("This file named " + file.getFileName() +" exist in the given directory");
        }else{
            System.out.println("File doesn't exist");
        }
        System.out.println("File Name: " + file.getFileName());
        System.out.println("Parent: " + file.getParent());
        System.out.println("Relativize Method: " + resource.relativize(file));
        System.out.println("Relativize Parent: " + resource.relativize(file).getParent());
        // getFileSystem and getNameCount and getRoot don't give much useful information
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws Exception{
        SimpleFileVisitorExample simpleFileVisitorExample= new SimpleFileVisitorExample();

        Files.walkFileTree(Paths.get("/home/sausingh/Documents/Test"), simpleFileVisitorExample);
    }
}
