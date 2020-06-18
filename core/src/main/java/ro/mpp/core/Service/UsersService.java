package ro.mpp.core.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.mpp.core.Domain.User;
import ro.mpp.core.Repository.UserRepository;

@Service
public class UsersService implements IUserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
