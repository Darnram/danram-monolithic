package com.danram.danram.repository;

import com.danram.danram.domain.Party;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    @Query("select p from Party p where p.deleted = false")
    Slice<Party> findParty(Pageable pageable);

    @Query("select p from Party p where p.partyType = :partyType and " +
            "p.deleted = false")
    Slice<Party> findPartyByPartyType(@Param("partyType") Long partyType,Pageable pageable);

    @Query("select p from Party p where p.title like %:query% and " +
            "p.deleted = false")
    Slice<Party> findPartyBySearch(@Param("query") String query,Pageable pageable);

    @Query("select p from Party p where p.title like %:query% and " +
            "p.partyType = :partyType and " +
            "p.deleted = false")
    Slice<Party> findPartyBySearchAndPartyType(@Param("query") String query,@Param("partyType") Long partyType,Pageable pageable);
}
