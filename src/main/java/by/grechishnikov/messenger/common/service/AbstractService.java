package by.grechishnikov.messenger.common.service;

import by.grechishnikov.messenger.common.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface AbstractService<T extends AbstractEntity> {

    T saveOrUpdate(T t);

    T findById(int id);

    Page<T> findAll(Pageable pageable);

    void deleteById(int id);

}
