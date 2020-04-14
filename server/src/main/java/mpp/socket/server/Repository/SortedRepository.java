package mpp.socket.server.Repository;


import mpp.socket.common.Domain.BaseEntity;
import mpp.socket.common.Domain.Student;

import java.util.List;

public interface SortedRepository<ID, T extends BaseEntity<ID>>
    extends Repository<ID, T> {
    Iterable<T> findAll(Sort sort);

}
