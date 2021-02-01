import java.util.List;

public class StaticFile {
    String name;
    boolean isFolder;
    List<StaticFile> children;

    public StaticFile(String name, boolean isFolder, List<StaticFile> children) {
        this.name = name;
        this.isFolder = isFolder;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public List<StaticFile> getChildren() {
        return children;
    }
}
