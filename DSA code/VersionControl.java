import java.util.*;
//This class implements ADTInterface procivig methods
//for a well functioning version control system.

public class VersionControl implements ADTInterface {
    // A map storing file version histories
    // the key is the file name(String)
    // the value is a list of file versions(List<String>)

    private Map<String, List<String>> fileVersions;
    // A map storing undo stacks for each file,when the new version control is
    // commited the current
    // version is pushed onto the stack while when a undo operation is performed the
    // top version is popped and restored
    private Map<String, Stack<String>> fileUndoStacks;
    // Variable for helping to manage branches in version control system */
    private BranchClass branchClass;
    // The name of the currently active branch */
    private String activeBranch;

    // The constructior of this class */
    public VersionControl() {
        fileVersions = new HashMap<>();
        fileUndoStacks = new HashMap<>();
        branchClass = new BranchClass();
        activeBranch = "Main Branch";
        branchClass.addBranch(activeBranch, new HashMap<>());
        System.out.println("Initialized with branch:" + activeBranch);
    }

    @Override
    public void commit(String fileName, String content) {
        // Firstly we ensure the file exiss in the versiom history */
        fileVersions.putIfAbsent(fileName, new ArrayList<>());
        fileUndoStacks.putIfAbsent(fileName, new Stack<>());
        // Pushes the current version onto the undo stack if it exists
        if (!fileVersions.get(fileName).isEmpty()) {
            String lastVersion = fileVersions.get(fileName).get(fileVersions.get(fileName).size() - 1);
            fileUndoStacks.get(fileName).push(lastVersion);
        }
        // Than we update the active branch with the new file */
        fileVersions.get(fileName).add(content);
        Map<String, List<String>> branch = branchClass.getBranch(activeBranch);
        branch.putIfAbsent(fileName, new ArrayList<>());
        branch.put(fileName, new ArrayList<>(fileVersions.get(fileName)));
        System.out.println("Commited to " + activeBranch + ":" + content);

    }

    // Reverts a file to a specific version on the active branch */
    @Override
    public String revert(String fileName, int versionNum) {
        if (!fileVersions.containsKey(fileName) || versionNum < 0 || versionNum >= fileVersions.get(fileName).size()) {
            return "Invalid version!";
        }
        System.out.println("Reverted to version" + versionNum + ":" + fileVersions.get(fileName).get(versionNum));
        return fileVersions.get(fileName).get(versionNum);
    }

    @Override
    // Creates a new branch by copying the state of active branch */
    public void createBranch(String branchName) {
        if (branchClass.branchExists(branchName)) {
            System.out.println("Branch already exists!" + branchName);
            return;
        }
        // Copies the active branch state to the new branch
        Map<String, List<String>> currentBranch = branchClass.getBranch(activeBranch);
        if (currentBranch == null) {
            currentBranch = new HashMap<>();
        }
        branchClass.addBranch(branchName, new HashMap<>(currentBranch));
        System.out.println("Created branch:" + branchName);
    }

    @Override
    // Sets the active branch to the specified branch name
    public void setActiveBranch(String branchName) {
        if (branchClass.branchExists(branchName)) {
            activeBranch = branchName;
            System.out.println("Switched to branch:" + branchName);
        } else {
            System.out.println("Branch doesnt exist:" + branchName);
        }
    }

    // Merges the contents of one branch into another
    @Override
    public void mergeBranches(String branchName1, String branchName2) {
        if (!branchClass.branchExists(branchName1) || !branchClass.branchExists(branchName2)) {
            System.out.println("One or both branches doesnt exist!");
            return;
        }

        Map<String, List<String>> branch1 = branchClass.getBranch(branchName1);
        Map<String, List<String>> branch2 = branchClass.getBranch(branchName2);
        // A queue used to handle file conflicts during branch merging
        // Files that exist in both branches are added to the queue then conflicts are
        // processed sequentally in the order they appear
        Queue<String> conflictQueue = new LinkedList<>();
        for (String file : branch2.keySet()) {
            if (branch1.containsKey(file)) {
                conflictQueue.add(file);
            } else {
                branch1.put(file, new ArrayList<>(branch2.get(file)));
            }
        }
        System.out.println("Processing...:");
        while ((!conflictQueue.isEmpty())) {
            String conflictingFile = conflictQueue.poll();
            System.out.println("Resolving conflict for file:" + conflictingFile);
            // Resolves conflicts by adding unique versions
            List<String> branch1Versions = branch1.get(conflictingFile);
            for (String version : branch2.get(conflictingFile)) {
                if (!branch1Versions.contains(version)) {
                    branch1Versions.add(version);
                }
            }
        }
        System.out.println("Merge is completed from" + branchName2 + "into" + branchName1);

    }
}
