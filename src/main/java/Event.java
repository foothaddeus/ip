public class Event extends Task {
    private String from;
    private String to;

    public Event(String description) {
        super(description.substring(0, description.indexOf("/from")));
        this.from = description.substring(description.indexOf("/from") + 6,
                description.indexOf("/to"));
        this.to = description.substring(description.indexOf("/to") + 4);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + "to: " + this.to + ")";
    }
}
