package com.ustu.erdbsystem.tasks.service;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;

import java.util.List;
import java.util.Optional;

public interface DenormalizeModelService {

    DenormalizeModel create(Model model);

    Optional<DenormalizeModel> getByModelWithTasks(Model model);
}
