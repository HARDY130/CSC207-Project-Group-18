package use_case.info_collection;

import entity.User;

public interface InfoCollectionUserDataAccessInterface {
    void save(User user);
    User get(String username);
    boolean existsByName(String username);
}





