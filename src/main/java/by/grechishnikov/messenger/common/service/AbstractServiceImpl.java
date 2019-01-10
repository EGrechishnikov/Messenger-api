package by.grechishnikov.messenger.common.service;

import by.grechishnikov.messenger.common.entity.AbstractEntity;
import by.grechishnikov.messenger.common.exception.ResourceNotFoundException;
import by.grechishnikov.messenger.common.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class AbstractServiceImpl<T extends AbstractEntity> implements AbstractService<T> {

    private AbstractRepository<T> abstractRepository;

    @Autowired
    public AbstractServiceImpl(AbstractRepository<T> abstractRepository) {
        this.abstractRepository = abstractRepository;
    }

    @Override
    public T saveOrUpdate(T t) {
        if (t.getId() != 0) {
            t.setUpdated();
        }
        return abstractRepository.save(t);
    }

    @Override
    public T findById(int id) {
        return abstractRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Resource with id:%s not found", id)));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return abstractRepository.findAll(pageable);
    }

    @Override
    public void deleteById(int id) {
        abstractRepository.deleteById(id);
    }

}
