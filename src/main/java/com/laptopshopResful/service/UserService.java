package com.laptopshopResful.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.user.ResCreateUserDTO;
import com.laptopshopResful.domain.response.user.ResFetchUserDTO;
import com.laptopshopResful.domain.response.user.ResUpdateUserDTO;
import com.laptopshopResful.repository.UserRepository;
import com.laptopshopResful.utils.UpdateNotNull;
import com.laptopshopResful.utils.convert.user.ConvertUserToRes;

@Service
public class UserService {

    private final UserRepository userRepository;

    public User handleGetUserByUsername(String email) {
        return this.userRepository.findByEmail(email);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email) ? true : false;
    }

    public boolean existsById(long id) {
        return this.userRepository.existsById(id) ? true : false;
    }

    public ResCreateUserDTO create(User user) {
        User currentUser = this.userRepository.save(user);
        return ConvertUserToRes.convertToCreateRes(currentUser);

    }

    public User findByRefreshTokenAndEmail(String re, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(re, email);
    }

    public ResUpdateUserDTO update(User user) {
        Optional<User> currentUSer = this.userRepository.findById(user.getId());
        UpdateNotNull.handle(user, currentUSer.get());
        this.userRepository.save(currentUSer.get());
        return ConvertUserToRes.convertToUpdateRes(currentUSer.get());
    }

    public ResFetchUserDTO fecth(Long id) {
        User user = this.userRepository.findById(id).get();
        return ConvertUserToRes.convertToFetchRes(user);
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    public ResultPaginationDTO fetchAllWithPagination(Pageable pageable, Specification<User> spec) {
        Page<User> page = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(page.getTotalPages());
        mt.setTotlal(page.getTotalElements());
        rs.setMeta(mt);
        System.out.println(page.getContent());
        List<ResFetchUserDTO> listUser = page.getContent()
                .stream()
                .map(x -> ConvertUserToRes.convertToFetchRes(x))
                .collect(Collectors.toList());
        rs.setResult(listUser);
        return rs;
    }

    public ResultPaginationDTO fetch2(Pageable pageable, Specification<User> spec) {
        Page<User> page = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(page.getTotalPages());
        mt.setTotlal(page.getTotalElements());
        rs.setMeta(mt);
        rs.setResult(page.getContent());
        return rs;
    }

    // token
    public void updateUserToken(String token, String email) {
        User user = this.userRepository.findByEmail(email);
        if (user != null) {
            user.setRefreshToken(token);
            this.userRepository.save(user);
        }
    }
}
