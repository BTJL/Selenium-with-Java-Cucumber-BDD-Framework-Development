package testRunner;


import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
//        features = "src\\Features\\Login.feature",
        features= ".//Features/",
        glue = "stepDefinitions",
        monochrome=true,

        // Quickly scan features without actually implementing them
        // Makes sure that every Step have the corresponding method available in the StepDefinition file
        dryRun = false,

        // "pretty" - generate steps clearly in output console
        // "html:test-output" - generate report in the test-output folder
        plugin = {"pretty", "html:test-output"},

//        tags = {"@sanity"} // Only the scenarios with these tags, within the specified feature files by features  will be executed
        tags = {"@sanity, @regression"} // tags with either @sanity OR @regression
//        tags = {"@sanity", "@regression"} // tags with BOTH @sanity AND @regression
        )

public class TestRun {
}
