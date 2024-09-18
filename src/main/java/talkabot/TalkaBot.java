package talkabot;

import java.io.IOException;
import java.time.DateTimeException;

import talkabot.exceptions.NoInputException;
import talkabot.exceptions.TalkaBotException;
import talkabot.exceptions.UnknownInputException;
import talkabot.task.TaskList;

/**
 * TalkaBot Class handles the running of Talk-a-Bot
 * and responds according to user input.
 */
public class TalkaBot {
    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    private Command command;

    /**
     * Constructs an instance of TalkaBot class.
     *
     * @param filePath path of file to be saved in hard drive.
     */
    public TalkaBot(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.command = new Command(this.ui, this.storage);
        try {
            this.taskList = storage.load();
        } catch (IOException e) {
            this.ui.error(e.getMessage());
        }
    }

    /**
     * Constructs an instance of TalkaBot class.
     *
     */
    public TalkaBot() {
        this("data/tasks.txt");
    }

    /**
     * Runs the Talk-a-Bot, performing tasks based on the type of user input, e.g. mark or delete.
     */
    private void run() {
        boolean isEnd = false;
        this.ui.printHello();
        ui.printDashedLine();
        while (!isEnd) {
            try {
                String input = this.ui.getLine();
                ui.printDashedLine();
                if (input == "") {
                    throw new NoInputException();
                } else if (input.equalsIgnoreCase("bye")) {
                    isEnd = true;
                } else if (input.equalsIgnoreCase("list")) {
                    System.out.println(this.command.handleList(this.taskList));
                } else if (input.toLowerCase().startsWith("mark")) {
                    System.out.println(this.command.handleMark(input, this.taskList));
                } else if (input.toLowerCase().startsWith("unmark")) {
                    System.out.println(this.command.handleUnmark(input, this.taskList));
                } else if (input.toLowerCase().startsWith("delete")) {
                    System.out.println(this.command.handleDelete(input, this.taskList));
                } else if (input.toLowerCase().startsWith("get day")) {
                    System.out.println(this.command.handleGetDay(input, this.taskList));
                } else if (input.toLowerCase().startsWith("find")) {
                    System.out.println(this.command.handleFind(input, this.taskList));
                } else if (input.toLowerCase().startsWith("todo")) {
                    System.out.println(this.command.handleToDo(input, this.taskList));
                } else if (input.toLowerCase().startsWith("deadline")) {
                    System.out.println(this.command.handleDeadline(input, this.taskList));
                } else if (input.toLowerCase().startsWith("event")) {
                    System.out.println(this.command.handleEvent(input, this.taskList));
                } else {
                    throw new UnknownInputException(input);
                }
            } catch (TalkaBotException e) {
                System.out.println(this.command.handleTalkaBotException(e));
            } catch (DateTimeException e) {
                System.out.println(this.command.handleDateTimeException(e));
            } catch (IOException e) {
                System.out.println(this.command.handleIOException(e));
            }
            if (!isEnd) {
                ui.printDashedLine();
            }
        }
        System.out.println(this.command.handleBye());
    }

    /**
     * Returns the output of Talk-a-Bot based on the type of user input, e.g. mark or delete.
     *
     * @param input User input.
     * @return Output of Talk-a-Bot.
     */
    public String getResponse(String input) {
        try {
            if (input == "") {
                throw new NoInputException();
            } else if (input.equalsIgnoreCase("bye")) {
                return this.command.handleBye();
            } else if (input.equalsIgnoreCase("list")) {
                return this.command.handleList(this.taskList);
            } else if (input.toLowerCase().startsWith("mark")) {
                return this.command.handleMark(input, this.taskList);
            } else if (input.toLowerCase().startsWith("unmark")) {
                return this.command.handleUnmark(input, this.taskList);
            } else if (input.toLowerCase().startsWith("delete")) {
                return this.command.handleDelete(input, this.taskList);
            } else if (input.toLowerCase().startsWith("get day")) {
                return this.command.handleGetDay(input, this.taskList);
            } else if (input.toLowerCase().startsWith("find")) {
                return this.command.handleFind(input, this.taskList);
            } else if (input.toLowerCase().startsWith("todo")) {
                return this.command.handleToDo(input, this.taskList);
            } else if (input.toLowerCase().startsWith("deadline")) {
                return this.command.handleDeadline(input, this.taskList);
            } else if (input.toLowerCase().startsWith("event")) {
                return this.command.handleEvent(input, this.taskList);
            } else {
                throw new UnknownInputException(input);
            }
        } catch (TalkaBotException e) {
            return this.command.handleTalkaBotException(e);
        } catch (DateTimeException e) {
            return this.command.handleDateTimeException(e);
        } catch (IOException e) {
            return this.command.handleIOException(e);
        }
    }

    /**
     * Returns the command type of the latest command.
     *
     * @return String representation of command type of latest command.
     */
    public String getCommandType() {
        return this.command.getCommandType();
    }

    public static void main(String[] args) {
        new TalkaBot("data/tasks.txt").run();
    }
}
