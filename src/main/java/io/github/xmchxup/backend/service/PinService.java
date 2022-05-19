package io.github.xmchxup.backend.service;

import io.github.xmchxup.backend.core.security.CurrentUserUtils;
import io.github.xmchxup.backend.dto.CreatePinDTO;
import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.Category;
import io.github.xmchxup.backend.model.Pin;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.CategoryRepository;
import io.github.xmchxup.backend.repository.PinRepository;
import io.github.xmchxup.backend.vo.PinPureVo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createPin(CreatePinDTO pinDTO) {
        Category category = categoryRepository.findById(pinDTO.getCategoryId())
                .orElseThrow(() -> new ParameterException(40001));

        User user = getCurrentUser(pinDTO.getUserId());

        pinRepository.save(Pin.builder()
                .title(pinDTO.getTitle())
                .about(pinDTO.getAbout())
                .destination(pinDTO.getDestination())
                .image(pinDTO.getImage())
                .owner(user)
                .category(category)
                .build());
    }

    @Transactional
    public void deletePin(Long pid) {
        Pin pin = pinRepository.findById(pid).orElseThrow(() -> new ParameterException(40000));
        getCurrentUser(pin.getOwner().getId());
        pinRepository.deleteById(pin.getId(), new Date());
    }


    private User getCurrentUser(Long userId) {
        User user = currentUserUtils.getCurrentUser().orElseThrow(() -> new ParameterException(20004));
        if (!user.getId().equals(userId)) {
            throw new ParameterException(40002);
        }
        return user;
    }

    public List<PinPureVo> getAllPins() {
        return pinRepository.findAll()
                .stream()
                .map(PinPureVo::new)
                .collect(Collectors.toList());
    }

    public Pin getPinById(Long pinId) {
        return pinRepository.findById(pinId)
                .orElseThrow(() -> new ParameterException(40000));
    }
}
