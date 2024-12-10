package ru.nsu.zhao;

import java.util.List;

/**
 * 程序入口，展示 Markdown 生成器的功能.
 */
public class Main {
    public static void main(String[] args) {
        // 示例 1: 简单文本及其格式
        Text plainText = new Text("This is plain text.");
        Text.Bold boldText = new Text.Bold("This is bold text.");
        Text.Italic italicText = new Text.Italic("This is italic text.");
        Text.Strikethrough strikethroughText = new Text.Strikethrough("This is strikethrough text.");
        Text.InlineCode inlineCode = new Text.InlineCode("System.out.println();");

        System.out.println("### Text Examples:");
        System.out.println(plainText.toMarkdown());
        System.out.println(boldText.toMarkdown());
        System.out.println(italicText.toMarkdown());
        System.out.println(strikethroughText.toMarkdown());
        System.out.println(inlineCode.toMarkdown());
        System.out.println();

        // 示例 2: 标题
        System.out.println("### Header Examples:");
        for (int i = 1; i <= 6; i++) {
            Header header = new Header(i, "Header Level " + i);
            System.out.println(header.toMarkdown());
        }
        System.out.println();

        // 示例 3: 列表
        ListElement unorderedList = new ListElement(false, List.of("Item 1", "Item 2", "Item 3"));
        ListElement orderedList = new ListElement(true, List.of("Step 1", "Step 2", "Step 3"));

        System.out.println("### List Examples:");
        System.out.println("Unordered List:");
        System.out.println(unorderedList.toMarkdown());
        System.out.println("Ordered List:");
        System.out.println(orderedList.toMarkdown());
        System.out.println();

        // 示例 4: 引用
        Blockquote simpleQuote = new Blockquote(List.of(new Text("This is a simple quote.")));
        Blockquote nestedQuote = new Blockquote(List.of(
                new Text("This is a level 1 quote."),
                new Blockquote(List.of(new Text("This is a nested level 2 quote.")))
        ));

        System.out.println("### Blockquote Examples:");
        System.out.println(simpleQuote.toMarkdown());
        System.out.println(nestedQuote.toMarkdown());
        System.out.println();

        // 示例 5: 链接和图片
        Link link = new Link("Google", "https://www.google.com");
        Image image = new Image("Example Image", "https://example.com/image.png");

        System.out.println("### Link and Image Examples:");
        System.out.println("Link:");
        System.out.println(link.toMarkdown());
        System.out.println("Image:");
        System.out.println(image.toMarkdown());
        System.out.println();

        // 示例 6: 任务列表
        TaskList taskList = new TaskList(List.of(
                new TaskList.Task(false, "Task 1: Incomplete"),
                new TaskList.Task(true, "Task 2: Completed")
        ));

        System.out.println("### Task List Example:");
        System.out.println(taskList.toMarkdown());
        System.out.println();

        // 示例 7: 表格
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Name", "Score")
                .addRow("Alice", "95")
                .addRow("Bob", "85")
                .build();

        System.out.println("### Table Example:");
        System.out.println(table.toMarkdown());
        System.out.println();

        // 示例 8: 代码块
        CodeBlock javaCodeBlock = new CodeBlock("java", "System.out.println(\"Hello, world!\");");
        CodeBlock noLanguageCodeBlock = new CodeBlock("", "echo 'Hello, world!'");

        System.out.println("### Code Block Examples:");
        System.out.println(javaCodeBlock.toMarkdown());
        System.out.println(noLanguageCodeBlock.toMarkdown());
        System.out.println();

        // 综合示例: 生成完整 Markdown 文档
        System.out.println("### Full Markdown Document Example:");
        String fullMarkdown = String.join("\n\n",
                "# Full Markdown Document",
                boldText.toMarkdown(),
                italicText.toMarkdown(),
                unorderedList.toMarkdown(),
                nestedQuote.toMarkdown(),
                link.toMarkdown(),
                image.toMarkdown(),
                taskList.toMarkdown(),
                table.toMarkdown(),
                javaCodeBlock.toMarkdown()
        );
        System.out.println(fullMarkdown);
    }
}
