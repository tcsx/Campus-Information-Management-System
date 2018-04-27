package com.tcsx.studentinfo.studentinformationsystem.dao;

import org.springframework.data.repository.CrudRepository;

import com.tcsx.studentinfo.studentinformationsystem.model.Lecture;

public interface LectureRepository extends CrudRepository<Lecture, String> {

}
