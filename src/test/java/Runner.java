import com.intuit.karate.Results;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Runner {

    @Test
    public void testParallel() {
        Results results = com.intuit.karate.Runner.path("classpath:features/fnLambda.feature")
                .outputCucumberJson(true)
                //.tags("@ListObjectS3")
                //.tags("@FindObjectS3")
                .tags("@ConvertTXTtoJson ")
                .parallel(4);
        generateReport(results.getReportDir());
        Assertions.assertTrue(results.getFailCount() == 0, results.getErrorMessages());
    }

    public static void generateReport(String karateOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "pruebas lambda");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}



