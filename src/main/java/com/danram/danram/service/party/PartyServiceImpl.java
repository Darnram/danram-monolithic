package com.danram.danram.service.party;

import com.danram.danram.domain.Member;
import com.danram.danram.domain.Party;
import com.danram.danram.domain.PartyMember;
import com.danram.danram.dto.request.party.AddPartyRequestDto;
import com.danram.danram.dto.request.party.AddPartyWithoutImgRequestDto;
import com.danram.danram.dto.request.party.PartyEditRequestDto;
import com.danram.danram.dto.request.party.PartyJoinRequestDto;
import com.danram.danram.dto.response.party.*;
import com.danram.danram.exception.member.MemberIdNotFoundException;
import com.danram.danram.exception.party.*;
import com.danram.danram.repository.MemberRepository;
import com.danram.danram.repository.PartyMemberRepository;
import com.danram.danram.repository.PartyRepository;
import com.danram.danram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.danram.danram.config.MapperConfig.modelMapper;

@Slf4j
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;
    private final PartyMemberRepository partyMemberRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public AddPartyResponseDto addParty(AddPartyRequestDto dto, String imgUrl) {
        Long memberId = JwtUtil.getMemberId();

        Party party = Party.builder()
                .memberId(memberId)
                .memberEmail(dto.getMemberEmail())
                .img(imgUrl == null ? " " : imgUrl)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .password(null)
                .partyType(dto.getPartyType())
                .max(dto.getMax())
                .startedAt(dto.getStartedAt())
                .endAt(dto.getEndAt())
                .location(dto.getLocation())
                .currentCount(1L)
                .deleted(false)
                .build();

        final Party save = partyRepository.save(party);

        partyMemberRepository.save(
                PartyMember.builder()
                        .party(save)
                        .memberId(memberId)
                        .build()
        );

        return modelMapper.map(save, AddPartyResponseDto.class);
    }

    @Override
    @Transactional
    public AddPartyResponseDto addParty(AddPartyWithoutImgRequestDto dto) {
        Long memberId = JwtUtil.getMemberId();

        Party party = Party.builder()
                .memberId(memberId)
                .memberEmail(dto.getMemberEmail())
                .img("https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2022/10/04/75e7a815-bc3b-4eed-b343-bd7723eb5f9b.jpg")
                .title(dto.getTitle())
                .description(dto.getDescription())
                .password(null)
                .partyType(dto.getPartyType())
                .max(dto.getMax())
                .startedAt(dto.getStartedAt())
                .endAt(dto.getEndAt())
                .location(dto.getLocation())
                .currentCount(1L)
                .deleted(false)
                .build();

        final Party save = partyRepository.save(party);

        partyMemberRepository.save(
                PartyMember.builder()
                        .party(save)
                        .memberId(memberId)
                        .build()
        );

        return modelMapper.map(save, AddPartyResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartyMemberResponseDto> findPartyMember(Long partyId) {
        List<PartyMemberResponseDto> responseDtoList = new ArrayList<>();

        final Party party = partyRepository.findById(partyId).orElseThrow(
                () -> new PartyNotFoundException(partyId.toString())
        );

        List<PartyMember> partyMemberList = partyMemberRepository.findByParty(party);

        for (PartyMember partyMember : partyMemberList) {
            final Member member = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                    () -> new MemberIdNotFoundException(JwtUtil.getMemberId())
            );

            PartyMemberResponseDto responseDto = PartyMemberResponseDto.builder()
                    .memberId(partyMember.getMemberId())
                    .nickname(member.getNickname() == null ? "이름 없음" : member.getNickname())
                    .build();
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartyResponseDto> findParty(Long sortType, Integer pages) {
        Pageable pageable = getPageable(sortType, pages);
        Slice<Party> partyList = partyRepository.findParty(pageable);

        return convertPartySliceToList(partyList);
    }

    @Override
    @Transactional
    public List<PartyResponseDto> findParty(final Long sortType) {
        List<PartyResponseDto> responseDtoList = new ArrayList<>();
        for (Party party : partyRepository.findPartyBySortTypeTrue()) {
            responseDtoList.add(
                    modelMapper.map(party, PartyResponseDto.class)
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartyResponseDto> findPartyByPartyType(Long partyType, Long sortType, Integer pages) {
        Pageable pageable = getPageable(sortType, pages);
        Slice<Party> partyList = partyRepository.findPartyByPartyType(partyType,pageable);

        return convertPartySliceToList(partyList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartyResponseDto> findPartyBySearch(Long sortType, String query, Integer pages) {
        Pageable pageable = getPageable(sortType,pages);
        Slice<Party> partyList = partyRepository.findPartyBySearch(query,pageable);

        return convertPartySliceToList(partyList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartyResponseDto> findPartyBySearchAndPartyType(Long sortType, String query, Long partyType, Integer pages) {
        Pageable pageable = getPageable(sortType,pages);
        Slice<Party> partyList = partyRepository.findPartyBySearchAndPartyType(query,partyType,pageable);

        return convertPartySliceToList(partyList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartyResponseDto> findMyParty(Integer pages) {
        List<PartyResponseDto> responseDtoList = new ArrayList<>();

        Long memberId = JwtUtil.getMemberId();

        Pageable pageable = getPageable(0L,pages);
        Slice<PartyMember> partyMemberList = partyMemberRepository.findByMemberId(memberId,pageable);

        for (PartyMember partyMember : partyMemberList) {
            log.info("size");
            Party party = partyMember.getParty();
            PartyResponseDto responseDto = modelMapper.map(party, PartyResponseDto.class);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public PartyJoinResponseDto joinParty(PartyJoinRequestDto dto) {
        Long memberId = JwtUtil.getMemberId();

        Party party = partyRepository.findById(dto.getPartyId())
                .orElseThrow(() -> new PartyNotFoundException(dto.getPartyId().toString()));

        PartyMember partyMember;
        Optional<PartyMember> checkMember = partyMemberRepository.findByPartyAndMemberId(party, memberId);
        
        if (checkMember.isPresent() && checkMember.get().getDeletedAt() == null) { // 중복 가입 예외
            throw new PartyMemberDuplicatedException(memberId.toString());
        } else if (checkMember.isPresent() && checkMember.get().getDeletedAt() != null) { // 가입한 적 있음. 탈퇴 후 3일 경과 여부 체크
            Period period = Period.between(checkMember.get().getDeletedAt(),LocalDate.now());
            
            if (period.getDays() <= 3) { // 3일 안지남. 가입 불가
                throw new PartyJoinNotAllowException();
            } else { // 3일 경과 가입 가능
                partyMember = checkMember.get();
                partyMember.setDeletedAt(null);
            }
        } else { // 가입한 적 없는 유저.
            partyMember = PartyMember.builder()
                    .memberId(memberId)
                    .party(party)
                    .deletedAt(null)
                    .build();
        }

        party.plusCurrentCount();

        partyMemberRepository.save(partyMember);

        return modelMapper.map(partyMember, PartyJoinResponseDto.class);
    }

    @Override
    @Transactional
    public Boolean deleteParty(Long partyId) {
        Long memberId = JwtUtil.getMemberId();

        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyNotFoundException(partyId.toString()));

        if (!party.getMemberId().equals(memberId)) {
            throw new InvalidHostException(memberId.toString());
        }
        party.setDeleted(true);

        List<PartyMember> partyMemberList = partyMemberRepository.findByParty(party);

        for (PartyMember partyMember : partyMemberList) {
            partyMember.setDeletedAt(LocalDate.now());
        }

        return true;
    }

    @Override
    @Transactional
    public Boolean exitParty(Long partyId) {
        Long memberId = JwtUtil.getMemberId();

        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyNotFoundException(partyId.toString()));

        PartyMember partyMember = partyMemberRepository.findByMemberIdAndParty(memberId,party)
                .orElseThrow(() -> new PartyNotFoundException(partyId.toString()));

        if (party.getMemberId().equals(memberId)) {
            throw new PartyHostExitException(memberId.toString());
        }

        partyMember.setDeletedAt(LocalDate.now());
        party.minusCurrentCount();

        return true;
    }

    @Override
    @Transactional
    public Party getPartyInfo(final Long partyId) {
        return partyRepository.findById(partyId).orElseThrow(
                () -> new PartyNotFoundException(partyId.toString())
        );
    }

    @Override
    @Transactional
    public PartyResponseDto addImg(final Long partyId, final String imgUrl) {
        Party party = partyRepository.findById(partyId).orElseThrow(
                () -> new PartyNotFoundException(partyId.toString())
        );

        party.setImg(imgUrl);

        return modelMapper.map(partyRepository.save(party), PartyResponseDto.class);
    }

    @Override
    @Transactional
    public List<PartyResponseDto> findMyParty() {
        List<PartyResponseDto> responseDtoList = new ArrayList<>();

        Long memberId = JwtUtil.getMemberId();

        final List<PartyMember> byMemberId = partyMemberRepository.findByMemberId(memberId);

        for (PartyMember partyMember : byMemberId) {
            log.info("size");
            Party party = partyMember.getParty();
            PartyResponseDto responseDto = modelMapper.map(party, PartyResponseDto.class);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public PartyEditResponseDto editParty(PartyEditRequestDto dto, String imgUrl) {
        Long memberId = JwtUtil.getMemberId();

        Party party = partyRepository.findById(dto.getPartyId())
                .orElseThrow(() -> new PartyNotFoundException(dto.getPartyId().toString()));

        if (!party.getMemberId().equals(memberId)) {
            throw new NotPartyManagerException(memberId.toString());
        }

        party.updateParty(dto,imgUrl);
        partyRepository.save(party);

        return modelMapper.map(party, PartyEditResponseDto.class);
    }

    private Pageable getPageable(Long sortType, Integer pages) {
        Pageable pageable;
        Sort sort;

        if (sortType == 0L) {
            sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "viewCount");
        }

        pageable = PageRequest.of(pages,10,sort);

        return pageable;
    }

    private List<PartyResponseDto> convertPartySliceToList(Slice<Party> partyList) {
        List<PartyResponseDto> responseDtoList = new ArrayList<>();

        for (Party party : partyList) {
            PartyResponseDto dto = modelMapper.map(party, PartyResponseDto.class);
            responseDtoList.add(dto);
        }

        return responseDtoList;
    }
}
