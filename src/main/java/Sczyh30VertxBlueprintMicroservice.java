import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Sczyh30VertxBlueprintMicroservice extends Main {
    Commit keepCommitsThatPerformModificationsOnDifferentServices(Commit commit) {
        List<String> modifiedFiles = commit.getModifiedFiles();

        Function<String, Boolean> targetAService = ((s -> {
            if (!(s.contains("-") && s.contains("/"))) {
                return false;
            }
            String microServiceFolderName = s.split("/")[0];
            String[] decomposedMicroServiceFolderName = microServiceFolderName.split("-");
            return decomposedMicroServiceFolderName[decomposedMicroServiceFolderName.length - 1].contains("microservice");
        }
        ));

        Set<String> targetedServices = new HashSet<>();
        Set<String> modifiedFilesInServices = new HashSet<>();
        for (String file : modifiedFiles) {
            String[] split = file.split("\t");

            String fileModified = split[1];

            if (targetAService.apply(fileModified)) {
                String microServiceFolderName = fileModified.split("/")[0];
                String[] decomposedMicroServiceFolderName = microServiceFolderName.split("-");

                String serviceName = "";
                for (int i = 0; i < decomposedMicroServiceFolderName.length - 1; i++) {
                    serviceName = serviceName.concat(decomposedMicroServiceFolderName[i]);
                }

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
