import java.util.*;

/*BranchClass manages branches,each of them maintains a mapping
 * of file names to their version history.This class creates branches ,does the retrieveal and checks if a branch exists
 */
public class BranchClass {
    /*
     * A map storing all the branches
     * the key is the branch name(String)
     * the value is a map of file names(String) to version histories(List<String>)
     */
    private Map<String, Map<String, List<String>>> branches;

    /*
     * The constructor initializes the BranchClass
     * Also creates an empty map for storing branches
     */
    public BranchClass() {
        branches = new HashMap<>();

    }

    /* Adds a new branch to version control system */
    public void addBranch(String branchName, Map<String, List<String>> branchState) {
        branches.put(branchName, new HashMap<>(branchState));

    }

    /* Returns the state of a specific branch */
    public Map<String, List<String>> getBranch(String branchName) {
        Map<String, List<String>> branch = branches.get(branchName);
        return branch;
    }

    /* Checks if a branch exists */
    public boolean branchExists(String branchName) {
        boolean exists = branches.containsKey(branchName);
        return exists;
    }

}
