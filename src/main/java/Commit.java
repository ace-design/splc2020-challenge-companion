import java.util.ArrayList;
import java.util.List;

public class Commit {
    private String hash;
    private List<String> modifiedFiles = new ArrayList<>();

    public Commit(String hash) {
        this.hash = hash;
    }

    public void addModifiedFile(String modifiedFile) {
        this.modifiedFiles.add(modifiedFile);
    }

    public String getHash() {
        return hash;
    }

    public List<String> getModifiedFiles() {
        return modifiedFiles;
    }

    @Override
    public String toString() {
        return hash;
    }
}
