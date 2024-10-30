public class AssignmentType {
    private String name;
    private double weight; // e.g., 0.2 for 20%

    // Constructor
    public AssignmentType(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}