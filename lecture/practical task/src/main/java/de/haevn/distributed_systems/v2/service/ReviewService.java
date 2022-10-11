package de.haevn.distributed_systems.v2.service;

import de.haevn.distributed_systems.v2.model.Review;
import de.haevn.distributed_systems.v2.repository.ReviewRepository;
import de.haevn.distributed_systems.v2.service.interfaces.IReviewService;
import de.haevn.distributed_systems.v2.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private ReviewRepository repository;


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Review> findAll() {
        return repository.findAll();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(Review obj) {
        repository.save(obj);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(List<Review> objs) {
        repository.saveAll(objs);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> update(Review obj) {
        return updateInternal(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long update(List<Review> objs){
        long counter;
        counter = objs.stream()
                .filter(obj -> obj.getId() != null)
                .filter(obj -> repository.findById(obj.getId()).isPresent())
                .filter(obj -> updateInternal(obj).isPresent())
                .count();
        return counter;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete() {
        repository.deleteAll();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> updateInternal(Review input) {
        var repositoryResult = repository.findById(input.getId());
        if(repositoryResult.isEmpty()){
            return Optional.empty();
        }

        var publisher = input.getPublisher();
        var rating = input.getStarRating();
        var text = input.getText();
        if(AppUtils.isStringNeitherNullNorEmpty(text)){
            repositoryResult.get().setText(text);
        }
        if(AppUtils.isStringNeitherNullNorEmpty(publisher)){
            repositoryResult.get().setPublisher(publisher);
        }
        if(null != rating){
            repositoryResult.get().setStarRating(rating);
        }
        repository.save(repositoryResult.get());
        return repositoryResult;
    }
}
