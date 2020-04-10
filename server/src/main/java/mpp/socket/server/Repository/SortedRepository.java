package mpp.socket.server.Repository;


import mpp.socket.server.Domain.BaseEntity;

public interface SortedRepository<ID, T extends BaseEntity<ID>>
    extends Repository<ID, T> {
    Iterable<T> findAll(Sort sort);

}
