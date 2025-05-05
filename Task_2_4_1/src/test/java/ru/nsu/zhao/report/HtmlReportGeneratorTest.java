package ru.nsu.zhao.report;

import ru.nsu.zhao.config.CourseConfig;
import ru.nsu.zhao.repos.*;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * HTML报告生成器测试类 / HTML Report Generator Test Class
 * 测试整个处理流程和HTML报告生成 / Tests the full processing pipeline and HTML report generation
 */
public class HtmlReportGeneratorTest {

    private static final String REPO_ROOT = "repos";          // 代码库存储目录 / Repository storage directory
    private static final String BUILD_CLASSES = "build/classes"; // 编译输出目录 / Compiled classes directory
    private static final String BUILD_DOCS = "build/docs";    // 文档输出目录 / Documentation output directory
    private static final String REPORT_HTML = "report.html";  // 报告输出文件 / Report output file

    private CourseConfig courseConfig;  // 课程配置 / Course configuration

    /**
     * 测试前初始化 / Setup before each test
     * @throws IOException 如果配置文件读取失败 / If config file reading fails
     */
    @BeforeEach
    public void setup() throws IOException {
        // 初始化课程配置 / Initialize course configuration
        courseConfig = new CourseConfig();
        Binding binding = new Binding();
        binding.setVariable("course", courseConfig);
        GroovyShell shell = new GroovyShell(binding);

        // 加载Groovy配置文件 / Load Groovy config file
        shell.evaluate(new File("CourseConfig.groovy"));

        // 清理之前的报告文件 / Clean up previous report file
        deleteFileOrDirectory(new File(REPORT_HTML));
    }

    /**
     * 测试完整流程和HTML报告生成 / Test full pipeline and HTML report generation
     * @throws Exception 如果任何步骤失败 / If any step fails
     */
    @Test
    public void testFullPipelineAndHtmlReportCreation() throws Exception {
        // 1. 克隆代码库 / Clone repositories
        RepoLoader.loadRepos(courseConfig);

        // 2. 编译检查 / Compilation check
        CompilationChecker compilationChecker = new CompilationChecker(
                courseConfig, REPO_ROOT, BUILD_CLASSES);
        List<String> successfulCompiledTasks = compilationChecker.checkAll();

        // 3. 生成文档 / Generate documentation
        JavadocGenerator javadocGenerator = new JavadocGenerator(
                REPO_ROOT, BUILD_DOCS, successfulCompiledTasks);
        List<String> successfulDocs = javadocGenerator.generateAll();

        // 4. 代码风格检查 / Code style check
        StyleChecker styleChecker = new StyleChecker(REPO_ROOT, successfulCompiledTasks);
        List<String> successfulStyle = styleChecker.checkAll();

        // 5. 运行测试 / Run tests
        TestRunner testRunner = new TestRunner(
                courseConfig, REPO_ROOT, BUILD_CLASSES, successfulCompiledTasks);
        Map<String, List<Integer>> testResults = testRunner.runAllTests();

        // 6. 构建报告数据 / Build report data
        HtmlReportDataBuilder builder = new HtmlReportDataBuilder(
                courseConfig, successfulCompiledTasks,
                successfulDocs, successfulStyle, testResults);
        Map<String, Map<String, List<String>>> reportData = builder.buildDetailedReportData();
        Map<String, List<String>> summaryTable = builder.buildGroupSummaryData(reportData);

        // 7. 生成HTML报告 / Generate HTML report
        HtmlReportGenerator.generateReport(REPORT_HTML, reportData, summaryTable);

        // 8. 验证报告文件存在 / Verify report file exists
        File reportFile = new File(REPORT_HTML);
        assertTrue(reportFile.exists(),
                "HTML报告应该被生成: " + REPORT_HTML + " / HTML report should be generated: " + REPORT_HTML);
    }

    /**
     * 递归删除文件或目录 / Recursively delete file or directory
     * @param file 要删除的文件或目录 / File or directory to delete
     */
    private void deleteFileOrDirectory(File file) {
        if (!file.exists()) return;

        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteFileOrDirectory(f);
                }
            }
        }
        file.delete();
    }
}