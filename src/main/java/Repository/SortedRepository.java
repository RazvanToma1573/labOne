package Repository;

import Domain.BaseEntity;

public interface SortedRepository<ID, T extends BaseEntity<ID>>
    extends Repository<ID, T>{
    Iterable<T> findAll(Sort sort);
}
