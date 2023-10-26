package edu.project1.input_and_output;

public class InputHandler {
    private DataInputer inputer;

    public InputHandler() {
    }

    public String next() {
        inputer = new DataInputer();
        return checkInput(inputer.getInputData());
    }

    private String checkInput(String data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("input string could not be empty!");
        } else if (data.length() > 1 && !data.equals("exit")) {
            return "Error: Invalid command! Enter a letter or the command \"exit\"!";
        } else if (data.length() == 1 && !Character.isLetter(data.charAt(0))) {
            throw new IllegalArgumentException("Error: unsupported symbol \"" + data + "\"");
        }

        return data;
    }
}
