package com.laptopshopResful.service;

import org.springframework.stereotype.Service;

import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.response.user.ResCreateUserDTO;
import com.laptopshopResful.domain.response.user.ResUpdateUserDTO;
import com.laptopshopResful.repository.UserRepository;
import com.laptopshopResful.utils.convert.user.ConvertUserToRes;

@Service
public class UserService {

    private final UserRepository userRepository;

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

    public ResUpdateUserDTO update(User user) {
        User currentUSer = this.userRepository.findById(user.getId()).get();
        currentUSer.setName(user.getName());
        currentUSer.setAddress(user.getAddress());
        currentUSer.setAge(user.getAge());
        currentUSer.setGender(user.getGender());
        this.userRepository.save(currentUSer);
        return ConvertUserToRes.convertToUpdateRes(currentUSer);

    }
}
