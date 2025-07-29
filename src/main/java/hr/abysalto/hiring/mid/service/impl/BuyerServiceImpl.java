package hr.abysalto.hiring.mid.service.impl;

import hr.abysalto.hiring.mid.dto.BuyerRequest;
import hr.abysalto.hiring.mid.model.Buyer;
import hr.abysalto.hiring.mid.repository.BuyerRepository;
import hr.abysalto.hiring.mid.service.BuyerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerServiceImpl implements BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerServiceImpl(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public List<Buyer> getAllBuyers() {
        return buyerRepository.findAll();
    }

    public Optional<Buyer> getBuyerById(Integer id) {
        return buyerRepository.findById(id);
    }

    public Buyer createBuyer(BuyerRequest request) {
        Buyer buyer = new Buyer();
        buyer.setFirstName(request.getFirstName());
        buyer.setLastName(request.getLastName());
        buyer.setTitle(request.getTitle());
        return buyerRepository.save(buyer);
    }

    public Optional<Buyer> updateBuyer(Integer id, BuyerRequest request) {
        Optional<Buyer> existingBuyer = buyerRepository.findById(id);
        if (existingBuyer.isPresent()) {
            Buyer buyer = existingBuyer.get();
            buyer.setFirstName(request.getFirstName());
            buyer.setLastName(request.getLastName());
            buyer.setTitle(request.getTitle());
            return Optional.of(buyerRepository.save(buyer));
        }
        return Optional.empty();
    }

    public boolean deleteBuyer(Integer id) {
        return buyerRepository.deleteById(id);
    }
}