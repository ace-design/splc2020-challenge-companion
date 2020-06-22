import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InstanaRobotShop extends Main {
    public static String[] services = new String[]{"cart", "catalogue", "payment","ratings", "user", "shipping"};

    static Function<String, Boolean> targetAService = ((s -> {
        return s.contains("/") && Arrays.asList(services).contains(s.split("/")[0]);
    }
    ));



     Commit keepCommitsThatPerformModificationsOnDifferentServices(Commit commit) {
        List<String> modifiedFiles = commit.getModifiedFiles();


        Set<String> targetedServices = new HashSet<>();
        Set<String> modifiedFilesInServices = new HashSet<>();
        for (String file : modifiedFiles) {
            String[] split = file.split("\t");
            if (commit.getHash().equals("4681320f")) {
                String typeOfModification = split[0];
            }
            String fileModified = split[1];

            if (targetAService.apply(fileModified)) {
                String serviceName = fileModified.split("/")[0];

                targetedServices.add(serviceName);
                modifiedFilesInServices.add(file);
            }
        }

        if (targetedServices.size() >= 2) {
//            System.out.printf("-- Commit %s targeted services %s ; %s\n", commit.getHash(), targetedServices, modifiedFilesInServices);
            Commit clone = new Commit(commit.getHash());
//            for (String modifiedFilesInService : modifiedFilesInServices) {
//                clone.addModifiedFile(modifiedFilesInService);
//            }

            for (String modifiedService : targetedServices) {
                clone.addModifiedFile(modifiedService);
            }
            return clone;
        }

        return null;
    }


}
