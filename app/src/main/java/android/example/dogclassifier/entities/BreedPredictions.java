package android.example.dogclassifier.entities;

public class BreedPredictions {
    private String breed_1;
    private String breed_2;
    private String breed_3;
    private float probability_1;
    private float probability_2;
    private float probability_3;

    public String getBreed_1() {
        return breed_1.replaceAll("_", " ");
    }

    public void setBreed_1(String breed_1) {
        this.breed_1 = breed_1;
    }

    public String getBreed_2() {
        return breed_2.replaceAll("_", " ");
    }

    public void setBreed_2(String breed_2) {
        this.breed_2 = breed_2;
    }

    public String getBreed_3() {
        return breed_3.replaceAll("_", " ");
    }

    public void setBreed_3(String breed_3) {
        this.breed_3 = breed_3;
    }

    public float getProbability_1() {
        return probability_1;
    }

    public void setProbability_1(float probability_1) {
        this.probability_1 = probability_1;
    }

    public float getProbability_2() {
        return probability_2;
    }

    public void setProbability_2(float probability_2) {
        this.probability_2 = probability_2;
    }

    public float getProbability_3() {
        return probability_3;
    }

    public void setProbability_3(float probability_3) {
        this.probability_3 = probability_3;
    }

    @Override
    public String toString() {
        return "BreedPredictions{" +
                "breed_1='" + breed_1 + '\'' +
                ", breed_2='" + breed_2 + '\'' +
                ", breed_3='" + breed_3 + '\'' +
                ", probability_1=" + probability_1 +
                ", probability_2=" + probability_2 +
                ", probability_3=" + probability_3 +
                '}';
    }
}
