import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThangchungShoppingCartDemo extends Main {

    static Function<String, Boolean> targetAService = ((s -> {
        return s.contains("/") && s.split("/")[1].equals("Services");
    }
    ));

   Commit keepCommitsThatPerformModificationsOnDifferentServices(Commit commit) {
        List<String> modifiedFiles = commit.getModifiedFiles();

        Set<String> targetedServices = new HashSet<>();
        Set<String> modifiedFilesInServices = new HashSet<>();
        for (String file : modifiedFiles) {
            String[] split = file.split("\t");

            String fileModified = split[1];

            if (targetAService.apply(fileModified)) {
                String microServiceFolderName = fileModified.split("/")[2];
                String[] decomposedMicroServiceFolderName = microServiceFolderName.split("\\.");

                String serviceName = decomposedMicroServiceFolderName[1];

                targetedServices.add(serviceName);
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
