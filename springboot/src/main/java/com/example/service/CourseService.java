package com.example.service;

import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;


    public PageInfo<Course> selectPage(Integer pageNum,Integer pageSize, Course course){
        PageHelper.startPage(pageNum,pageSize);
        List<Course> courseList = courseMapper.selectAll(course);
        return PageInfo.of(courseList);
    }

    public int insertCourse(Course course){
        courseMapper.insertCourse(course);
        return 1;
    }


    public int updateCourse(Course course) {
        return courseMapper.updateCourseById(course);
    }

    public Integer deleteCourse(Integer id) {
        return courseMapper.deleteCourseById(id);
    }
}
