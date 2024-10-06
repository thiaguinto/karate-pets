package examples;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExamplesTest {

    @Test
    void testParallel() {
        // Limpiar reportes antiguos si existen
        File reportDir = new File("target/cucumber-html-reports/");
        if (reportDir.isDirectory()) {
            String[] oldReports = reportDir.list();
            if (oldReports != null) {
                for (String oldReport : oldReports) {
                    File reportFile = new File(reportDir, oldReport);
                    System.out.println("Deleting old report file: " + reportFile);
                    reportFile.delete();
                }
            }
        }

        // Configurar el entorno y ejecutar pruebas en paralelo
        System.setProperty("karate.env", System.getProperty("karate.env", "staging")); // Establecer el entorno
        Results results = Runner.path("classpath:examples/pets/search_pet.feature")


                // Cambia el path a tu ubicación de pruebas
                .outputCucumberJson(true)
                .parallel(1); // Ajusta el número de hilos para la ejecución en paralelo

        // Generar el reporte de Cucumber en HTML
        generateReport(results.getReportDir());

        // Asegurar que no hubo fallos
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    // Método para generar el reporte Cucumber HTML
    public static void generateReport(String karateOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(
                new File(karateOutputPath), new String[]{"json"}, true);

        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));

        Configuration config = new Configuration(new File("target"), "Pets API Tests");

        // Agregar clasificaciones opcionales al reporte
        config.addClassifications("Environment", System.getProperty("karate.env"));
        config.addClassifications("Platform", System.getProperty("os.name"));

        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}
