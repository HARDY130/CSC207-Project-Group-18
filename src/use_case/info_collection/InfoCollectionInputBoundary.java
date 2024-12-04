package use_case.info_collection;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface InfoCollectionInputBoundary {

    /**
     * Executes the Logout use case.
     * @param infoCollectionInputData the input data
     */
    void execute(InfoCollectionInputData infoCollectionInputData);
}
