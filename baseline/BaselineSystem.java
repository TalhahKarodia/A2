class UI {
    public void submitResearchOutput(SubmissionData data) {
        SubmissionController controller = new SubmissionController();
        controller.submit(data);
    }
}

class SubmissionController {
    public void submit(SubmissionData data) {
        Validator validator = new Validator();
        if (validator.validateFormat(data)) {
            Database db = new Database();
            db.saveSubmission(data);
            System.out.println("Confirmation sent to UI");
            ReviewerManager reviewerManager = new ReviewerManager();
            Reviewer[] filteredReviewers = reviewerManager.getAvailableReviewers();
            for (Reviewer reviewer : filteredReviewers) {
                reviewerManager.assignReview(reviewer);
            }
            EvaluationManager evalManager = new EvaluationManager();
            evalManager.startEvaluation(filteredReviewers);
        } else {
            System.out.println("Return error to UI");
        }
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
    public Reviewer[] getAvailableReviewers() {
        System.out.println("Fetching reviewers from database...");
        Reviewer[] reviewers = { new Reviewer("A"), new Reviewer("B"), new Reviewer("C") };
        reviewers = filterConflicts(reviewers);
        reviewers = checkWorkload(reviewers);
        return reviewers;
    }
    public Reviewer[] filterConflicts(Reviewer[] reviewers) {
        System.out.println("Filtering conflicts...");
        return reviewers; // No-op for baseline
    }
    public Reviewer[] checkWorkload(Reviewer[] reviewers) {
        System.out.println("Checking reviewer workload...");
        return reviewers; // No-op for baseline
    }
    public void assignReview(Reviewer reviewer) {
        System.out.println("Assigning review to Reviewer " + reviewer.getName());
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
    public void startEvaluation(Reviewer[] reviewers) {
        int[] scores = new int[reviewers.length];
        for (int i = 0; i < reviewers.length; i++) {
            int score = reviewers[i].submitScore(80); // Simulate score
            saveScore(score);
            scores[i] = score;
        }
        evaluate(scores);
    }
    public void saveScore(int score) {
        System.out.println("Saving score: " + score);
    }
    public void evaluate(int[] scores) {
        double avg = calculateAverage(scores);
        boolean consensus = checkConsensus(scores);
        applyRules(avg, consensus);
    }
    public double calculateAverage(int[] scores) {
        System.out.println("Calculating average score...");
        int sum = 0;
        for (int score : scores) sum += score;
        return (double) sum / scores.length;
    }
    public boolean checkConsensus(int[] scores) {
        System.out.println("Checking consensus...");
        return true; // Always consensus for baseline
    }
    public void applyRules(double avg, boolean consensus) {
        System.out.println("Applying rules...");
        if (avg > 70 && consensus) {
            notifyAcceptance();
        } else if (avg < 50) {
            notifyRejection();
        } else {
            notifyRevision();
        }
    }
    public void notifyAcceptance() {
        NotificationService ns = new NotificationService();
        ns.sendNotification("accepted");
    }
    public void notifyRejection() {
        NotificationService ns = new NotificationService();
        ns.sendNotification("rejected");
    }
    public void notifyRevision() {
        NotificationService ns = new NotificationService();
        ns.sendNotification("revision required");
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
