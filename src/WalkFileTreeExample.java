import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class WalkFileTreeExample extends SimpleFileVisitor<Path> {

    /* First preVisit is called before visiting any directory
    Then visit file is called (for every file)
    Finally postVisit is called for the directories
    * */
    Path resource= Paths.get("/home/sausingh/Documents/Test");

    // This is called just before visiting the directory
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("About to visit " + dir);
        return FileVisitResult.CONTINUE;
    }

    // This method is called for every file in the given directory structure
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.print(" File Name : " + file.getFileName().toString());
        System.out.print(" Parent : "+ file.getParent());
        System.out.print(" Relativize File : " + resource.relativize(file));
        System.out.print(" Relativize Parent : " + resource.relativize(file).getParent());
        return FileVisitResult.CONTINUE;
    }
    // This is called if any file cannot be visited due to some reason
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return super.visitFileFailed(file, exc);
    }

    // This is called just after visiting a particular directory
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("Just visited : " + dir);
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws Exception{
        WalkFileTreeExample walkFileTreeExample= new WalkFileTreeExample();

        // First param of walkFileTree method is imp- it is the file path on which we want to loop through
        Files.walkFileTree(Paths.get("/home/sausingh/Documents/Test"), walkFileTreeExample);
    }
}
