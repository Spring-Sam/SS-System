package com.example.mapper;

import com.example.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.annotation.Resource;
import java.util.List;

public interface CourseMapper {


    @Select("select * from course t where t.name like concat('%',#{name},'%') and t.course_no like concat('%',#{courseNo},'%') order by t.id desc")
    List<Course> selectAll(Course course);

    @Insert("INSERT INTO course (name, course_no, description, times, teacher) VALUES (#{name},#{courseNo},#{description},#{times},#{teacher} )")
    void insertCourse(Course course);

    @Update("Update course  set name=#{name},course_no=#{courseNo},description=#{description}, times=#{times},teacher=#{teacher} where id =#{id} ")
    Integer updateCourseById(Course course);

    @Delete("delete from course where id=#{id}")
    Integer deleteCourseById(Integer id);
}
