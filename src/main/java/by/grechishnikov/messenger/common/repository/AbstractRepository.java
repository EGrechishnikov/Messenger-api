package by.grechishnikov.messenger.common.repository;

import by.grechishnikov.messenger.common.entity.AbstractEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface AbstractRepository<T extends AbstractEntity> extends PagingAndSortingRepository<T, Integer> {
}