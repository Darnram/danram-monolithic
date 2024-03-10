package com.danram.danram.service.party;

import com.danram.danram.domain.Party;
import com.danram.danram.dto.request.party.AddPartyRequestDto;
import com.danram.danram.dto.request.party.AddPartyWithoutImgRequestDto;
import com.danram.danram.dto.request.party.PartyEditRequestDto;
import com.danram.danram.dto.request.party.PartyJoinRequestDto;
import com.danram.danram.dto.response.party.*;

import java.util.List;

public interface PartyService {
    public AddPartyResponseDto addParty(AddPartyRequestDto dto, String imgUrl);
    public AddPartyResponseDto addParty(AddPartyWithoutImgRequestDto dto);
    public List<PartyResponseDto> findParty(Long sortType, Integer pages);
    public List<PartyResponseDto> findParty(Long sortType);
    public List<PartyResponseDto> findMyParty(Integer pages);
    public List<PartyResponseDto> findPartyByPartyType(Long partyType,Long sortType,Integer pages);
    public List<PartyResponseDto> findPartyBySearch(Long sortType,String query,Integer pages);
    public List<PartyResponseDto> findPartyBySearchAndPartyType(Long sortType,String query,Long partyType,Integer pages);
    public List<PartyMemberResponseDto> findPartyMember(Long partyId);
    public PartyJoinResponseDto joinParty(PartyJoinRequestDto dto);
    public PartyEditResponseDto editParty(PartyEditRequestDto dto, String imgUrl);
    public Boolean deleteParty(Long partyId);
    public Boolean exitParty(Long partyId);
    public Party getPartyInfo(Long partyId);
    public PartyResponseDto addImg(Long partyId, String imgUrl);
    public List<PartyResponseDto> findMyParty();
}
