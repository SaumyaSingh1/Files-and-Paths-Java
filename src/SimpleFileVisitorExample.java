import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class SimpleFileVisitorExample extends SimpleFileVisitor<Path> {
    Path resource= Paths.get("/home/sausingh/Documents/Resources/");

    // Map< Parent Folder, list of child either folder or file>
    // Map<String, List<String>> resourcesMap= new TreeMap<>();

    // Map <Static File, Parent Path>
    public static Map<Path, StaticFile> resourcesMap= new HashMap<>();
    Stack<Path> currentFolder= new Stack<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        currentFolder.push(dir.getFileName());
        System.out.println(" About to visit  " + dir);
        String relativePathOfDir= resource.relativize(dir).toString();
        Path relativeParent= resource.relativize(dir).getParent();
        System.out.println(" Relative Path of Directory : " + relativePathOfDir);
        System.out.println(" Parent of Dir : " + dir.getParent());
        System.out.println(" Relative Parent of Dir : " + relativeParent);
        System.out.println(" PARENT : " + dir.getParent().getFileName());
        Path directoryName= dir.getFileName();
        System.out.println(" Directory Name : " + directoryName);
        System.out.println(" Current Folder : " + currentFolder.peek());
        if(!resourcesMap.containsKey(directoryName)){
            // if not create a new StaticFile for the directory and save it in your map.
            StaticFile staticFile= new StaticFile(directoryName.toString(), attrs.isDirectory(), null);
            resourcesMap.put(directoryName, staticFile);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        String fileName= file.getFileName().toString();
        String completeParentPath= file.getParent().toString();
        String relatedFileName= resource.relativize(file).toString();
        Path relatedParentName= resource.relativize(file).getParent();
        Path nameOfParent= file.getParent().getFileName();
        System.out.println("File Name: " + fileName);
        System.out.println("Whole Parent Path: " + completeParentPath);
        System.out.println("Related File Name: " + relatedFileName);
        System.out.println("Related Parent Directory Name: " + relatedParentName);
        System.out.println("Parent Of File : " + nameOfParent);
        // getFileSystem and getNameCount and getRoot don't give much useful information
        List<StaticFile> childrenList= new ArrayList<>();
        if(resourcesMap.containsKey(relatedParentName)){
            StaticFile childStaticFile= new StaticFile(fileName, attrs.isDirectory(), null);
            childrenList.add(childStaticFile);
            resourcesMap.put(relatedParentName, new StaticFile(relatedParentName.toString(), true, childrenList));
        }
//        StaticFile staticFile= new StaticFile(fileName, attrs.isDirectory(), null);
//        System.out.println("STATIC FILE : " + staticFile.children)
//        resourcesMap.put(staticFile, resource.relativize(file).getParent());
        return FileVisitResult.CONTINUE;
    }

    //return back to previous folder you were visiting
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println(" Just Visited " + dir);
        System.out.println(" POP RESULT : " + currentFolder.pop());
        return FileVisitResult.CONTINUE;
    }



    public static void main(String[] args) throws Exception{
        SimpleFileVisitorExample simpleFileVisitorExample= new SimpleFileVisitorExample();

        Files.walkFileTree(Paths.get("/home/sausingh/Documents/Resources/"), simpleFileVisitorExample);

//        for(StaticFile key: resourcesMap.keySet()){
//            System.out.println(" Keys in ResourceMap :" + key.name);
//        }
//        System.out.println(" Values Inside ResourceMap " + resourcesMap.values());
//        System.out.println(" Resource Map : " + resourcesMap);

        for(Map.Entry<Path, StaticFile> m: resourcesMap.entrySet()){
            System.out.println(" Key : " + m.getKey() + ", Value : " + m.getValue());
        }
        for(StaticFile value: resourcesMap.values()){
            List<StaticFile> childList= value.children;
            System.out.println(" CHILDLIST : " + childList);
        }


        System.out.println("ResourcePath Map : "+ resourcesMap);

    }
}
