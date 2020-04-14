package mpp.socket.common.Repository;


import mpp.socket.common.Domain.BaseEntity;

public interface SortedRepository<ID, T extends BaseEntity<ID>>
    extends Repository<ID, T> {
    Iterable<T> findAll(Sort sort);

}
