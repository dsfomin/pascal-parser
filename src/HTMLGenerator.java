import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HTMLGenerator {

    public static void generateHtml(String path, List<Token> tokenList) {
        StringBuilder defaultHtml = new StringBuilder("<html><head>"
                + "<link href =\"style.css\" rel=\"stylesheet\" type=\"text/css\">"
                + "</head><body><div>");
        for (Token token : tokenList) {
            if (token.getTokenType() == Token.TokenType.PUNCTUATION && token.getContent().equals("\\n"))
                defaultHtml.append("</div>\n<div>");
            else
                defaultHtml.append(String.format("<span class=\"%s\">%s</span>", token.getTokenType(), token.getContent()));
        }
        defaultHtml.append("</div></body></html>");

        File file = new File(path);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(defaultHtml.toString());
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("File error:" + e);
        }
    }
}
