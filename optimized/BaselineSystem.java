class UI {
    public void submitResearchOutput(SubmissionData data) {
        SubmissionController controller = new SubmissionController();
        controller.submit(data, this);
    }
    public void showError() {
        System.out.println("Error: Invalid submission format.");
    }
}

class SubmissionController {
    public void submit(SubmissionData data, UI ui) {
        Validator validator = new Validator();
        if (!validator.validateFormat(data)) {
            ui.showError();
            return;
        }
        Database db = new Database();
        db.saveSubmission(data);
        System.out.println("Confirmation sent to UI");
        ReviewerManager reviewerManager = new ReviewerManager();
        Reviewer[] filteredReviewers = reviewerManager.getFilteredReviewers();
        reviewerManager.assignReviews(filteredReviewers);
        EvaluationManager evalManager = new EvaluationManager();
        evalManager.evaluate(filteredReviewers);
    }
}

class Validator {
    public boolean validateFormat(SubmissionData data) {
        System.out.println("Validating data format...");
        return data.isValid();
    }
}

class Database {
    public void saveSubmission(SubmissionData data) {
        System.out.println("Saving submission to database...");
    }
}

class ReviewerManager {
    public Reviewer[] getFilteredReviewers() {
        System.out.println("Fetching reviewers from database...");
        Reviewer[] reviewers = { new Reviewer("A"), new Reviewer("B"), new Reviewer("C") };
        reviewers = filterAndCheck(reviewers);
        return reviewers;
    }
    public Reviewer[] filterAndCheck(Reviewer[] reviewers) {
        System.out.println("Filtering conflicts and checking workload...");
        return reviewers; // No-op for baseline
    }
    public void assignReviews(Reviewer[] reviewers) {
        for (Reviewer reviewer : reviewers) {
            System.out.println("Assigning review to Reviewer " + reviewer.getName());
        }
    }
}

class Reviewer {
    private String name;
    public Reviewer(String name) { this.name = name; }
    public String getName() { return name; }
    public int submitScore(int score) {
        System.out.println("Reviewer " + name + " submits score: " + score);
        return score;
    }
}

class EvaluationManager {
    public void evaluate(Reviewer[] reviewers) {
        int[] scores = collectScores(reviewers);
        String outcome = DecisionEngine.decide(scores);
        NotificationService ns = new NotificationService();
        ns.sendNotification(outcome);
    }
    private int[] collectScores(Reviewer[] reviewers) {
        int[] scores = new int[reviewers.length];
        for (int i = 0; i < reviewers.length; i++) {
            int score = reviewers[i].submitScore(80); // Simulate score
            saveScore(score);
            scores[i] = score;
        }
        return scores;
    }
    private void saveScore(int score) {
        System.out.println("Saving score: " + score);
    }
}

class DecisionEngine {
    public static String decide(int[] scores) {
        double avg = calculateAverage(scores);
        boolean consensus = checkConsensus(scores);
        if (avg > 70 && consensus) {
            return "accepted";
        } else if (avg < 50) {
            return "rejected";
        } else {
            return "revision required";
        }
    }
    private static double calculateAverage(int[] scores) {
        System.out.println("Calculating average score...");
        int sum = 0;
        for (int score : scores) sum += score;
        return (double) sum / scores.length;
    }
    private static boolean checkConsensus(int[] scores) {
        System.out.println("Checking consensus...");
        return true; // Always consensus for baseline
    }
}

class NotificationService {
    public void sendNotification(String status) {
        System.out.println("Notification sent to researcher: " + status);
    }
}

class SubmissionData {
    private boolean valid;
    public SubmissionData(boolean valid) { this.valid = valid; }
    public boolean isValid() { return valid; }
}

public class BaselineSystem {
    public static void main(String[] args) {
        UI ui = new UI();
        ui.submitResearchOutput(new SubmissionData(true));
    }
}
