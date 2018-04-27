package com.tcsx.studentinfo.studentinformationsystem.dao;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.tcsx.studentinfo.studentinformationsystem.model.Program;


@EnableScan
public interface ProgramRepository extends CrudRepository<Program, String> {

}
