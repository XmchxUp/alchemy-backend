package io.github.xmchxup.backend.service;

import io.github.xmchxup.backend.core.security.CurrentUserUtils;
import io.github.xmchxup.backend.dto.CreatePinDTO;
import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.Pin;
import io.github.xmchxup.backend.model.PinSaved;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.CategoryRepository;
import io.github.xmchxup.backend.repository.PinRepository;
import io.github.xmchxup.backend.repository.PinSavedRepository;
import io.github.xmchxup.backend.vo.PinPureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Service
public class PinService {
    @Autowired
    private PinRepository pinRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CurrentUserUtils currentUserUtils;

    @Autowired
    private PinSavedRepository pinSavedRepository;

    public void createPin(CreatePinDTO pinDTO) {
        checkCategoryExist(pinDTO.getCategoryId());

        User user = checkUser(pinDTO.getUserId());

        pinRepository.save(Pin.builder()
                .title(pinDTO.getTitle())
                .about(pinDTO.getAbout())
                .destination(pinDTO.getDestination())
                .image(pinDTO.getImage())
                .owner(user)
                .categoryId(pinDTO.getCategoryId())
                .build());
    }

    private void checkCategoryExist(Long cid) {
        categoryRepository.findById(cid).orElseThrow(() -> new ParameterException(40001));
    }

    @Transactional
    public void deletePin(Long pid) {
        Pin pin = pinRepository.findById(pid)
                .orElseThrow(() -> new ParameterException(40000));
        checkUser(pin.getOwner().getId());
        pinRepository.deleteById(pin.getId(), new Date());
    }

    private User checkUser(Long userId) {
        User user = getCurrentUser();
        if (!user.getId().equals(userId)) {
            throw new ParameterException(40002);
        }
        return user;
    }

    private User getCurrentUser() {
        User user = currentUserUtils.getCurrentUser()
                .orElseThrow(() -> new ParameterException(20004));
        return user;
    }

    public List<PinPureVo> getAllPins() {
        return pinRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"))
                .stream()
                .map(PinPureVo::new)
                .collect(Collectors.toList());
    }

    public Pin getPinById(Long pinId) {
        return pinRepository.findById(pinId)
                .orElseThrow(() -> new ParameterException(40000));
    }

    public void userSavedPin(Long pid) {
        User user = getCurrentUser();
        pinSavedRepository.save(new PinSaved(null, pid, user.getId()));
    }

    private List<PinPureVo> pinConvertToPinPureVo(List<Pin> pins) {
        return pins.stream()
                .map(PinPureVo::new)
                .collect(Collectors.toList());
    }

    public List<PinPureVo> getUserCreatedPins(Long uid) {
        return pinConvertToPinPureVo(pinRepository.findAllCreatedByUserId(uid));
    }

    public List<PinPureVo> getUserSavedPins(Long uid) {
        return pinConvertToPinPureVo(pinRepository.findAllSavedByUserId(uid));

    }

    public List<PinPureVo> getPinsByCategoryId(Long cid) {
        return pinConvertToPinPureVo(pinRepository.findAllByCategoryId(cid));
    }

    public List<PinPureVo> searchPinByAboutOrTitle(String searchTerm) {
        return pinConvertToPinPureVo(pinRepository.findByAboutOrTitle(searchTerm));
    }
}
