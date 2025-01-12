public interface ADTInterface {
    /*
     * This interface defines the ADTs for version control system.
     * It has the most important methods required for commiting creating etc
     */
    void commit(String fileName, String content);

    /* Commits a new version of a file */
    String revert(String fileName, int versionNum);

    /* Reverts a file to the old version */
    void createBranch(String branchName);

    /* creates a new branch in the version control system */
    void setActiveBranch(String branchName);

    /* sets active a specific branch */

    void mergeBranches(String branchName1, String branchName2);
    /* Merges the content of one branch to another */
}