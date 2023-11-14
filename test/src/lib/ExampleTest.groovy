package lib

import com.lesfurets.jenkins.unit.cps.BasePipelineTestCPS
import org.junit.jupiter.api.*

import java.util.concurrent.TimeUnit

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.ProjectSource.projectSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExampleTest extends BasePipelineTestCPS {
    @Override
    @BeforeAll
    void setUp() throws Exception {
        super.setUp()
        helper.libLoader.preloadLibraryClasses = false
        helper.registerSharedLibrary(library().name('self')
                .defaultVersion('HEAD')
                .allowOverride(true)
                .implicit(false)
                .targetPath('<notNeeded>')
                .retriever(projectSource())
                .build())
    }

    @Test
    @Timeout(value = 10L, unit = TimeUnit.SECONDS)
    void classicLoopCPS() {
        Script script = loadInlineScript('''
@Library("self")
import lib.Example
Example.classicLoopCPS()
''')
        def result = runScript(script)
        assertJobStatusSuccess()
        Assertions.assertEquals([
                "Begin CPS",
                "End CPS",
        ].join("\n"), result.join("\n"));
    }

    @Test
    @Timeout(value = 10L, unit = TimeUnit.SECONDS)
    void classicLoopNonCPS() {
        Script script = loadInlineScript('''
@Library("self")
import lib.Example
Example.classicLoopNonCPS()
''')
        def result = runScript(script)
        assertJobStatusSuccess()
        Assertions.assertEquals([
                "Begin NonCPS",
                "Inside classic loop",
                "End NonCPS",
        ].join("\n"), result.join("\n"));
    }

    @Test
    @Timeout(value = 10L, unit = TimeUnit.SECONDS)
    void customStep() {
        def messages = []
        helper.registerAllowedMethod("echo", [String], { message ->
            messages += message
            println(message)
        })
        Script script = loadInlineScript('''
@Library("self")
import lib.Example
customStep(env.message)
''')
        script.env.message = "Hello, world!!!"
        runScript(script)
        assertJobStatusSuccess()
        Assertions.assertEquals([
                "Begin custom step",
                "Hello, world!!!",
                "End custom step",
        ].join("\n"), messages.join("\n"));
    }
}
