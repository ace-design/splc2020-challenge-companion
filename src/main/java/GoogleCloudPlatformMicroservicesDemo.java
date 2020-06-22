import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class GoogleCloudPlatformMicroservicesDemo extends Main {

    Commit keepCommitsThatPerformModificationsOnDifferentServices(Commit commit) {
        List<String> modifiedFiles = commit.getModifiedFiles();

        Function<String, Boolean> targetAService = ((s -> s.contains("/") && s.split("/")[1].endsWith("service")));

        Set<String> targetedServices = new HashSet<>();
        Set<String> modifiedFilesInServices = new HashSet<>();
        for (String file : modifiedFiles) {
            String[] split = file.split("\t");

            String fileModified = split[1];

            if (targetAService.apply(fileModified)) {
                targetedServices.add(fileModified.split("/")[1]);
                modifiedFilesInServices.add(file);
            }
        }

        if (targetedServices.size() >= 2) {
            Commit clone = new Commit(commit.getHash());

            for (String modifiedService : targetedServices) {
                clone.addModifiedFile(modifiedService);
            }
            return clone;
        }

        return null;
    }


}
