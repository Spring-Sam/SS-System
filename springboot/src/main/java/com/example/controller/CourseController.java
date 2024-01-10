package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.service.CourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/course")
public class CourseController {


    @Resource
    private CourseService courseService;

    @GetMapping("/courseList")
    public Result selectCourseList(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "2") Integer pageSize,
                                    Course course ){
        PageInfo<Course> courseList = courseService.selectPage(pageNum,pageSize,course);
        return Result.success(courseList);
    }

    @PostMapping("/saveCourse")
    public Result saveCourse(@RequestBody Course course){
        Integer result =  courseService.insertCourse(course);
        return Result.success(result);
    }

    @PutMapping("/updateCourse")
    public Result updateCourse(@RequestBody Course course){
        Integer result =  courseService.updateCourse(course);
        return Result.success(result);
    }

    @DeleteMapping("/deleteCourse/{id}")
    public Result deleteCourse(@PathVariable Integer id){
        Integer result =  courseService.deleteCourse(id);
        return Result.success(result);
    }

}
