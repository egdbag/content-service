package com.egdbag.article.core.service;

import com.egdbag.article.core.interfaces.ITextComponentService;
import com.egdbag.article.core.model.TextComponent;
import com.egdbag.article.core.storage.repository.ITextComponentRepository;
import com.egdbag.article.core.storage.schema.TextComponentSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TextComponentService implements ITextComponentService {
    @Autowired
    private ITextComponentRepository textComponentRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public Mono<TextComponent> createComponent(TextComponent component, Integer articleId) {
        TextComponentSchema componentSchema = modelMapper.toSchema(component, articleId);
        return textComponentRepository.save(componentSchema).map(modelMapper::toDto);
    }

    @Override
    public Mono<TextComponent> findById(Integer componentId) {
        return textComponentRepository.findById(componentId).map(modelMapper::toDto);
    }

    @Override
    public Mono<TextComponent> updateComponent(Integer componentId, TextComponent component) {
        return textComponentRepository.findById(componentId)
                .flatMap(dbComponent -> {
                    dbComponent.setText(component.getText());
                    return textComponentRepository.save(dbComponent);
                })
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<TextComponent> deleteComponent(Integer componentId) {
        return textComponentRepository.findById(componentId)
                .flatMap(existingComponent -> textComponentRepository.delete(existingComponent)
                        .then(Mono.just(modelMapper.toDto(existingComponent))));
    }

    @Override
    public Flux<TextComponent> getComponentsByArticleId(Integer articleId) {
        return textComponentRepository.findByArticleId(articleId).map(modelMapper::toDto);
    }

    //
//    private Mono<Department> getDepartmentByUserId(Integer userId) {
//        return departmentRepository.findByUserId(userId);
//    }
//
//    public Mono<UserDepartmentDTO> fetchUserAndDepartment(Integer userId) {
//        Mono<User> user = findById(userId).subscribeOn(Schedulers.elastic());
//        Mono<Department> department = getDepartmentByUserId(userId).subscribeOn(Schedulers.elastic());
//        return Mono.zip(user, department, userDepartmentDTOBiFunction);
//    }
//
//    private BiFunction<User, Department, UserDepartmentDTO> userDepartmentDTOBiFunction = (x1, x2) -> UserDepartmentDTO.builder()
//            .age(x1.getAge())
//            .departmentId(x2.getId())
//            .departmentName(x2.getName())
//            .userName(x1.getName())
//            .userId(x1.getId())
//            .loc(x2.getLoc())
//            .salary(x1.getSalary()).build();
}
