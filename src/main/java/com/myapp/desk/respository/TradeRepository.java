package com.myapp.desk.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myapp.desk.domain.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}