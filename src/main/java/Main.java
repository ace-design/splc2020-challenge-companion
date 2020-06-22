import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Main {
    static final String logFolder = "./gitlogs/";
    static final String csvFolder = "./csv/";

    public static void main(String[] args) throws IOException {
        new GoogleCloudPlatformMicroservicesDemo().analyze("GoogleCloudPlatform_microservices-demo.gitlog", new String[]{"currencyservice", "paymentservice", "adservice", "checkoutservice", "shippingservice", "productcatalogservice", "recommendationservice", "emailservice", "cartservice"}, "./codev-GoogleCloudPlatformMicroservicesDemo.csv");

        new DotnetArchitectureEShopOnContainers().analyze("dotnet-architecture_eShopOnContainers.log", new String[]{"Payment", "Ordering", "Webhooks", "Basket", "Identity", "Catalog", "Marketing"}, "./codev-DotnetArchitectureEShopOnContainers.csv");

        new InstanaRobotShop().analyze("instana_robot-shop.log", new String[]{"cart", "catalogue", "payment", "ratings", "user", "shipping"}, "./codev-InstanaRobotShop.csv");

        new Sczyh30VertxBlueprintMicroservice().analyze("sczyh30_vertx-blueprint-microservice.log", new String[]{"shoppingCart", "users", "consul", "order", "product", "store", "inventory", "recommendation", "payment", "account"}, "./codev-Sczyh30VertxBlueprintMicroservice.csv");

        new ThangchungShoppingCartDemo().analyze("thangchung_ShoppingCartDemo.log", new String[]{"IdentityServer", "CustomerService", "PaymentService", "CheckoutProcess", "CatalogService", "OrderService", "AuditService"}, "./codev-ThangchungShoppingCartDemo.csv");

    }

    private static List<String> readLogFile(String file) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(logFolder + file));

        } catch (IOException e) {

            // do something
            e.printStackTrace();
        }
        return lines;
    }

    abstract Commit keepCommitsThatPerformModificationsOnDifferentServices(Commit commit);

    List<Commit> extractCommits(List<String> lines) {
        List<Commit> commitsList = new ArrayList<>();

        Function<String, Boolean> isACommitHash = s -> s.contains(" ") && s.split(" ")[0].length() == 8;

        ListIterator<String> iterator = lines.listIterator();


        while (iterator.hasNext()) {
            String s = iterator.next();
            if (isACommitHash.apply(s)) {
                String commitHash = s.split(" ")[0];
                Commit currentCommit = new Commit(commitHash);

                while (iterator.hasNext()) {
                    // if the next element is a new commit
                    String modifiedFile = iterator.next();

                    if (isACommitHash.apply(modifiedFile)) {
                        iterator.previous();
                        break;
                    }
                    currentCommit.addModifiedFile(modifiedFile);
                }
                commitsList.add(currentCommit);
            }
        }
        return commitsList;
    }

    public void analyze(String file, String[] services, String resultFile) throws IOException {
        List<String> lines = readLogFile(file);
        System.out.printf("%s lines in the log file\n", lines.size());

        List<Commit> commitList = extractCommits(lines);
        Stream<Commit> commitStream = commitList.stream();

        // Handle merge commit and commits that modify one file only
        List<Commit> filteredCommits = commitStream.filter((c) -> c.getModifiedFiles().size() >= 2).collect(Collectors.toList());

        StringBuilder resultBuilder = new StringBuilder();

        System.out.printf("CommitID,");
        resultBuilder.append("CommitID,");
        for (String service : services) {
            System.out.printf("%s,", service);
            resultBuilder.append(service).append(",");
        }

        resultBuilder.append("\n");
        System.out.printf("\n");

        for (Commit c : filteredCommits) {
            Commit commit = keepCommitsThatPerformModificationsOnDifferentServices(c);
            if (commit != null) {
                System.out.printf("%s,", commit.getHash());
                resultBuilder.append(commit.getHash()).append(",");
                List<String> modifiedFiles = commit.getModifiedFiles();
                for (String service : services) {
                    if (modifiedFiles.contains(service)) {
                        System.out.printf("Y");
                        resultBuilder.append("Y");
                    } else {
                        System.out.printf("N");
                        resultBuilder.append("N");
                    }
                    System.out.printf(",");
                    resultBuilder.append(",");
                }
                System.out.printf("\n");
                resultBuilder.append("\n");
            }
        }

        FileWriter fileWriter = new FileWriter(new File(csvFolder + resultFile), false);
        fileWriter.append(resultBuilder.toString());
        fileWriter.close();
    }
}
