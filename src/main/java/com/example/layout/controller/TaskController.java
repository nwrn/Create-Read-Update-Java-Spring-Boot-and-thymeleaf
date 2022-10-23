package com.example.layout.controller;

import com.example.layout.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    List<Task> dataTasks = new ArrayList<>();

    @GetMapping("/create")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("create");
        mv.addObject("task", new Task());
        return mv;
    }

    @PostMapping("/create")
    public String create(Task task){
        if(task.getId() != null){
            Long id = task.getId();
            Task taskFind = dataTasks
                    .stream()
                    .filter( taskItem -> id
                    .equals(taskItem
                            .getId()))
                    .findFirst()
                    .get();
            dataTasks.set(dataTasks.indexOf(taskFind), task);
        }else{
            Long id = dataTasks.size() + 1L;
            dataTasks.add(new Task(id, task.getName(), task.getDate()));
        }
        return "redirect:/list";
    }

    @GetMapping("/list")
    public ModelAndView list(Task task){
        ModelAndView mv = new ModelAndView("list");
        mv.addObject("dataTasks", dataTasks);

        return mv;
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("create");
        Task taskFind = dataTasks
                .stream()
                .filter( task -> id.equals(task.getId()))
                .findFirst()
                .get();
        mv.addObject("task", taskFind);
        return mv;
    }

}
