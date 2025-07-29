package hr.abysalto.hiring.mid.service;

import hr.abysalto.hiring.mid.dto.BuyerRequest;
import hr.abysalto.hiring.mid.model.Buyer;

import java.util.List;
import java.util.Optional;

public interface BuyerService {

    List<Buyer> getAllBuyers();

    Optional<Buyer> getBuyerById(Integer id) ;

    Buyer createBuyer(BuyerRequest request) ;

    Optional<Buyer> updateBuyer(Integer id, BuyerRequest request);

    boolean deleteBuyer(Integer id);
}