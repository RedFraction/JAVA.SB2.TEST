package dev.xred.sb2test.model;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepo extends JpaRepository<ConvertHistory, Integer> {
	@Override
	List<ConvertHistory> findAll();

	@Override
	List<ConvertHistory> findAll(Sort sort);

	@Override
	List<ConvertHistory> findAllById(Iterable<Integer> iterable);

	@Override
	<S extends ConvertHistory> List<S> saveAll(Iterable<S> entities);

	@Override
	void flush();

	@Override
	<S extends ConvertHistory> S saveAndFlush(S entity);

	@Override
	void deleteInBatch(Iterable<ConvertHistory> entities);

	@Override
	void deleteAllInBatch();

	@Override
	ConvertHistory getOne(Integer integer);

	@Override
	<S extends ConvertHistory> List<S> findAll(Example<S> example);

	@Override
	<S extends ConvertHistory> List<S> findAll(Example<S> example, Sort sort);

	@Override
	Page<ConvertHistory> findAll(Pageable pageable);

	@Override
	<S extends ConvertHistory> S save(S entity);

	@Override
	Optional<ConvertHistory> findById(Integer integer);

	@Override
	boolean existsById(Integer integer);

	@Override
	long count();

	@Override
	void deleteById(Integer integer);

	@Override
	void delete(ConvertHistory entity);

	@Override
	void deleteAll(Iterable<? extends ConvertHistory> entities);

	@Override
	void deleteAll();

	@Override
	<S extends ConvertHistory> Optional<S> findOne(Example<S> example);

	@Override
	<S extends ConvertHistory> Page<S> findAll(Example<S> example, Pageable pageable);

	@Override
	<S extends ConvertHistory> long count(Example<S> example);

	@Override
	<S extends ConvertHistory> boolean exists(Example<S> example);

}
