package mpp.socket.server.Repository;



import mpp.socket.server.Domain.BaseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    private Map<ID, T> entities;

    /**
     * Creates a new in memory repository
     */
    public InMemoryRepository() {
        entities = new HashMap<>();
    }

    /**
     * Finds one entity with the given id
     * @param id is the ID of the entity
     *            must be not null.
     * @return the entity with the given ID (student | problem | grade)
     */
    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    /**
     * Finds all the entities
     * @return a list with all the entities
     */
    @Override
    public Iterable<T> findAll() {
        Set<T> allEntities = entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return allEntities;
    }

    /**
     * Adds a new entity to the repository
     * @param entity is the entity to be added (student | problem | grade)
     *            must not be null.
     * @return
     */
    @Override
    public Optional<T> save(T entity){
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    /**
     * Deletes an entity from the repository
     * @param id is the ID of the entity to be removed
     *            must not be null.
     * @return
     */
    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates an entity from the repository
     * @param entity is the new entity
     *            must not be null.
     * @return
     */
    @Override
    public Optional<T> update(T entity){
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
