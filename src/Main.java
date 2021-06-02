public class Main {

    public static void main(String[] args) {
        String input = "C:\\Users\\Дмитрий1\\Desktop\\SEM6\\Компил\\pascal-parser\\input\\input.txt";
        String output = "C:\\Users\\Дмитрий1\\Desktop\\SEM6\\Компил\\pascal-parser\\output\\output.html";
        Lexer lexer = new Lexer(input, output);
        lexer.lexer();
    }
}
