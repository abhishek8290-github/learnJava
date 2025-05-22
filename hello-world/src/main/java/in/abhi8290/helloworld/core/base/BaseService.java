package in.abhi8290.helloworld.core.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public T update(ID id, T updatedEntity) {
        if (!getRepository().existsById(id)) {
            throw new RuntimeException("Entity not found with ID: " + id);
        }
        return getRepository().save(updatedEntity);
    }

    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }
}
