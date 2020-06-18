package ro.mpp.core.Service;

import ro.mpp.core.Domain.User;

public interface IUserService {
    User getUserByUsername(String username);
}
