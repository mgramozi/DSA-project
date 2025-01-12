public class Tester {
    // The tester class demonstrates the functionality of the Version Control class
    public static void main(String[] args) {
        // Creates an instance of the VersionControl system
        VersionControl vc = new VersionControl();
        // Initializes and switches to the default branch
        vc.setActiveBranch("Main Branch");
        // Commits file versions to the Main Branch
        vc.commit("File1", "Version1");
        vc.commit("File1", "Version2");
        vc.commit("File2", "Initial Version");
        // Reverts File1 to version1
        vc.revert("File1", 0);
        // Creates a new branch and commit to iy
        vc.createBranch("Future Branch");
        vc.setActiveBranch("Future Branch");
        vc.commit("File1", "Feature Version");
        vc.commit("File3", "New Feature File");
        vc.setActiveBranch("Main Branch");
        // Merges new branch into main branch
        vc.mergeBranches("MainBranch", "Future branch");

    }

}
